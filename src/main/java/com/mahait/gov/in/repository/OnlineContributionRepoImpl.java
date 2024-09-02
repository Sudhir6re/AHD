package com.mahait.gov.in.repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.model.DcpContributionModel;

@Repository
public class OnlineContributionRepoImpl implements OnlineContributionRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<CmnLookupMst> getPaymentTypeLst() {
		String HQL = "FROM CmnLookupMst as t  where lookupId in ('700047','700048','700049')  ORDER BY t.lookupId";
		return (List<CmnLookupMst>) entityManager.createQuery(HQL).getResultList();
	}

	@Override
	public Boolean checkIfBillAlreadyGenerated(Long billGroupId, Long monthId, Long finYearId) {
		Session ghibSession = entityManager.unwrap(Session.class);
		StringBuilder lSBQuery = new StringBuilder();
		List<PaybillGenerationTrnEntity> PaybillGenerationTrnEntityLst = new ArrayList<PaybillGenerationTrnEntity>();
		Boolean flag = false;

		lSBQuery.append("FROM PaybillGenerationTrnEntity where schemeBillgroupId = :billNo and paybillMonth = :month and paybillYear = :year and isActive in (5,14) ");

		Query lQuery = ghibSession.createSQLQuery(lSBQuery.toString());
		lQuery.setParameter("billNo", billGroupId);
		lQuery.setParameter("month", monthId);
		lQuery.setParameter("year", finYearId);

		PaybillGenerationTrnEntityLst = lQuery.getResultList();
			if (PaybillGenerationTrnEntityLst.size() != 0) {
				flag = true;
			}
		return flag;
	}
	
	@Override
	public List<Object[]> getEmpListForContribution(DcpContributionModel dcpContributionModel, OrgUserMst messages,String startDate) {
		Session ghibSession = entityManager.unwrap(Session.class);	
		Integer roleId = messages.getMstRoleEntity().getRoleId(); //2 DDO 3 DDOAST
		String useType = dcpContributionModel.getUseType();
		String lStrTypeOfPaymentMaster = dcpContributionModel.getTypeOfPayment();
		int delayedFinYearId = dcpContributionModel.getDelayedFinYearId();
		int delayedMonthId = dcpContributionModel.getDelayedMonthId();
		int finYearId = dcpContributionModel.getFinYearId();
		int monthId = dcpContributionModel.getMonthId();
		Long billGroupId = dcpContributionModel.getBillGroupId();
		String ddoCode=messages.getDdoCode();
		StringBuilder SBQuery = new StringBuilder();
		Double lDoubleDefaultDArateForNon5th6thPC = 58d;
		
		List empList = null;
		List finalList = new ArrayList();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date date=null;
        try {
            date = sdf.parse(startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
      
        Date newStartDate = calendar.getTime();

        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to the last day of the month
        Date newEndDate = calendar.getTime();
        String endDate = sdf.format(newEndDate);
        
     
        if(dcpContributionModel.getTypeOfPayment()!=null) {
        	if(dcpContributionModel.getTypeOfPayment().equals("700048")) {
            	startDate=sdf.format(dcpContributionModel.getDAArrearStartDate());
            }else if(dcpContributionModel.getTypeOfPayment().equals("700049"))  {
            	startDate=sdf.format(dcpContributionModel.getPayArrearStartDate());
            }
        }
        
        try {
				SBQuery.append("select Em.employee_id,Em.dcps_no,Em.employee_full_name_en,Em.pay_commission_code,"
					//	+ "COALESCE(CO.BASIC_PAY,EM.BASIC_PAY) as BASIC_PAY,"
						+ " CASE " + 
						"        WHEN Em.pay_commission_code = '700005' THEN COALESCE(CO.BASIC_PAY, EM.seven_pc_basic)\r\n" + 
						"        ELSE COALESCE(CO.BASIC_PAY, EM.BASIC_PAY)\r\n" + 
						"    END AS BASIC_PAY,"
						+ "COALESCE(CO.DCPS_CONTRIBUTION_ID,0) as DCPS_CONTRIBUTION_ID,COALESCE(CO.TYPE_OF_PAYMENT,'"
						+ dcpContributionModel.getTypeOfPayment()
						+ "') as TYPE_OF_PAYMENT,COALESCE(CO.MONTH_ID,0) as MONTH_ID,COALESCE(CO.FIN_YEAR_ID,0) as FIN_YEAR_ID,COALESCE(DA.percentage,"+ lDoubleDefaultDArateForNon5th6thPC + ") as percentage,"
								+ "CO.REG_STATUS,EM.DOJ,CO.DA,CO.DP,CO.CONTRIBUTION," );

				SBQuery.append(" CO.startDate StartDate");
				SBQuery.append(",CO.endDate,COALESCE(CO.NPS_EMPLR_CONTRI_DED,0) as NPS_EMPLR_CONTRI_DED FROM employee_mst EM " );

				SBQuery.append(" LEFT OUTER JOIN TRN_DCPS_CONTRIBUTION CO ON EM.employee_id=CO.DCPS_EMP_ID AND CO.MONTH_ID="+ monthId+ ""
						+ " AND CO.FIN_YEAR_ID=" + finYearId + " AND CO.DDO_CODE = '" + ddoCode + "'" );


			if((roleId==3 && (useType.equals("ViewAll")) && lStrTypeOfPaymentMaster.equals("700047") ))
			{
				if(delayedMonthId != 0 && delayedFinYearId != 0)
				{
					SBQuery.append(" AND CO.DELAYED_MONTH_ID = " + delayedMonthId);
					SBQuery.append(" AND CO.DELAYED_FIN_YEAR_ID = " + delayedFinYearId);
				}
			}

			SBQuery
			.append(" LEFT OUTER JOIN mst_dcps_contri_voucher_dtls CV ON CV.treasury_code=CO.TREASURY_CODE AND CV.month_id=CO.MONTH_ID "
					+ " AND CV.year_id = CO.FIN_YEAR_ID AND CV.bill_group_id=CO.BILL_GROUP_ID AND CV.ddo_code = CO.ddo_code");

			SBQuery
			.append(" LEFT JOIN ALLOWANCE_DEDUCTION_WISE_RULE_MST DA ON DA.PAY_COMMISSION_CODE = EM.PAY_COMMISSION_CODE AND  (('"
					+ startDate
					+ "' BETWEEN DA.start_date AND DA.end_date) OR ('"
					+ startDate
					+ "' >= DA.start_date and DA.end_date IS NULL)) ");
			
		
			

			// Code Added to show employees of valid post and service only for first online contribution entry
			if(roleId==3 && (useType.equals("ViewAll")))
			{
				SBQuery.append(" join org_post_mst OP on OP.post_id = EM.post_detail_id and OP.ACTIVATE_FLAG = 1");
			}

			if((roleId==3 && (useType.equals("ViewAll"))))
			{
				SBQuery.append(" join employee_allowdeduc_mpg HRCGM on HRCGM.employee_id = EM.employee_id and HRCGM.IS_ACTIVE = '1' ");
				SBQuery.append(" join department_allowdeduc_mst HPDT on HPDT.department_allowdeduc_code = HRCGM.department_allowdeduc_code ");

				
				if(lStrTypeOfPaymentMaster.equals("700046"))  // Regular 
				{
					SBQuery.append(" AND HPDT.department_allowdeduc_code = 50 ");  //DCPS  59
				}
				if(lStrTypeOfPaymentMaster.equals("700047"))  // Delayed 
				{
					SBQuery.append(" AND HPDT.department_allowdeduc_code = 52 ");  //120 DCPS Delayed
				}
				if(lStrTypeOfPaymentMaster.equals("700048"))  // DA Arrear
				{
					SBQuery.append(" AND HPDT.department_allowdeduc_code = 51 "); //122 DCPS DA
				}
				if(lStrTypeOfPaymentMaster.equals("700049"))  // Pay Arrear
				{
					SBQuery.append(" AND HPDT.department_allowdeduc_code = 46 ");  // 121 DCPS Pay
				}
			}


			if((roleId==3 && (useType.equals("ViewAll"))))
			{
				SBQuery.append(" WHERE EM.DDO_CODE='" + ddoCode + "'");
			}
			else
			{
				SBQuery.append(" WHERE CO.DDO_CODE='" + ddoCode + "'");
			}

			
			
		
		/*	if((!(lLongbillGroupId.toString().equals(gObjRsrcBndle.getString("DCPS.BGIdForContriThruChallan"))))
					&&
					(lStrUser.equals("DDOAsst") || lStrUser.equals("ATO")) && (lStrUse.equals("ViewAll"))	
			)
			{
				SBQuery.append(" AND EM.BILLGROUP_ID=" + lLongbillGroupId);
			}
			else
			{
				SBQuery.append(" AND CO.BILL_GROUP_ID=" + lLongbillGroupId);
			}*/

			// Code Added to show employees of valid post and service and date of joining and DCPS employee only for first online contribution entry
			if((roleId==3 && (useType.equals("ViewAll"))))
			{
				SBQuery.append(" AND EM.is_active=1 AND EM.dcps_gpf_flag = 'Y' AND EM.DOJ <'" + startDate+ "'");
				SBQuery.append(" AND ( EM.EMP_SERVICE_END_DATE is null or EM.EMP_SERVICE_END_DATE > '" + startDate+ "' )");
				SBQuery.append(" AND (EM.SUPER_ANN_DATE is null or EM.SUPER_ANN_DATE >'" + startDate+ "' )");
				SBQuery.append(" AND (OP.END_DATE is null or OP.END_DATE > '" + startDate+ "' )");
			}

			if (!((useType.equals("ATO") || roleId==2  || roleId==3) && useType.equals("ViewAll"))) {

				if (roleId==2 && useType.equals("ViewForwarded")) {
					SBQuery.append(" AND CO.REG_STATUS = 2"); // for Online
				}

				if (roleId==2 && useType.equals("ViewApproved")) {
					SBQuery.append(" AND CO.REG_STATUS in (1,3)"); // for Online
				}

			
				if (roleId==3 &&  useType.equals("ViewRejected")) {
					SBQuery.append(" AND CO.REG_STATUS = -3"); // 3 for Online
				}


			} else {
				SBQuery.append(" AND (CO.REG_STATUS IS NULL OR CO.REG_STATUS = 0) ");
			}

			SBQuery.append(" Order By \n");
			
				SBQuery.append(" EM.employee_full_name_en ASC");

			Query stQuery = ghibSession.createSQLQuery(SBQuery.toString());

			empList = stQuery.list();
			Integer lInt2 = 0;
			Double BasicPay = 0d;
			Double DP = 0D;
			String lStrDP = "";
			Double DARate = 0d;
			String lStrTypeOfPayment ="";
			Double DA = 0D;
			Double employeeContribution = 0D;
			String lStrDA="";
			Double emplrContribution=0D;
			for (Integer lInt1 = 0; lInt1 < empList.size(); lInt1++) {
				Object[] tempObjectList = (Object[]) empList.get(lInt1);
				Object[] newList = new Object[tempObjectList.length + 4];
				lInt2 = 0;
				emplrContribution=   Math.ceil(Double.parseDouble(tempObjectList[17].toString()));
				lInt2 = tempObjectList.length;
				for (lInt2 = 0; lInt2 < tempObjectList.length-1; lInt2++) {

					// Changes Basic pay to Integer value
					if(lInt2 == 4)
					{
						if(tempObjectList[lInt2] == null)
						{
							tempObjectList[lInt2] = 0;
						}
						else
						{

							tempObjectList[lInt2] = (int) Math.ceil(Double.parseDouble(tempObjectList[lInt2].toString()));
						}
					}

					newList[lInt2] = tempObjectList[lInt2];

				}
				BasicPay = Double.parseDouble(tempObjectList[4].toString());

				DP = 0D;
				lStrDP = "";
				if (null != tempObjectList[13]) {
					lStrDP = tempObjectList[13].toString();
				}
				if (newList[3].toString().equals("700015") || newList[3].toString().equals("700345")) {
					if (null != lStrDP && !"".equals(lStrDP)) {
						DP = Double.parseDouble(lStrDP);
					} else {
						DP = BasicPay / 2;
					}
				}
				DARate = 0.01 * Double.parseDouble(tempObjectList[9]
				                                                  .toString());
				lStrTypeOfPayment = tempObjectList[6].toString();
				DA = 0D;
				employeeContribution = 0D;
				emplrContribution = 0D;

				lStrDA = "";

				if (null != tempObjectList[12]) {
					lStrDA = tempObjectList[12].toString();
				}
				if (lStrTypeOfPayment.equals("700048")) {
					if (tempObjectList[12] != null) {
						DA = Double.parseDouble(tempObjectList[12].toString());
					}
					if (tempObjectList[14] != null) {
						employeeContribution = Double.parseDouble(tempObjectList[14].toString());
					}
					if (tempObjectList[17] != null) {
						emplrContribution = Double.parseDouble(tempObjectList[17].toString());
					}
				} else if (lStrTypeOfPayment.equals("700049")) {
					DA = 0D;
					if (tempObjectList[14] != null) {
						employeeContribution = Double.parseDouble(tempObjectList[14].toString());
					}
					if (tempObjectList[17] != null) {
						emplrContribution = Double.parseDouble(tempObjectList[17].toString());
					}
				} else {
					if (null != lStrDA && !"".equals(lStrDA)) {
						DA = Double.parseDouble(lStrDA);
					} else {
						DA = ((BasicPay + DP) * DARate);
					}

					if (null != tempObjectList[14]) {
						employeeContribution = Double.parseDouble(tempObjectList[14].toString());
					} else {
						if (newList[3].toString().equals("700015")) {
							employeeContribution = ((double) Math.ceil(BasicPay)
									+ Math.ceil(DP) + Math.round(DA)) * 0.10;
						} else {
							employeeContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.10;
						}
					}
					
					if (!tempObjectList[17].toString().equals("0")) {
						emplrContribution = Double.parseDouble(tempObjectList[17].toString());
					} else {
						if((finYearId<=32 && finYearId<=3) || finYearId<32  ) {
							if (newList[3].toString().equals("700015")) {
								emplrContribution = ((double) Math.ceil(BasicPay)+ Math.ceil(DP) + Math.round(DA)) * 0.10;
							} else {
								emplrContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.10;
							}
						}else
						{
							if (newList[3].toString().equals("700015")) {
								emplrContribution = ((double) Math.ceil(BasicPay)+ Math.ceil(DP) + Math.round(DA)) * 0.14;
							} else {
								emplrContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.14;
							}
							
						}
					}
					
				}
				DA = (double) Math.round(DA);

				employeeContribution = (double) Math.round(employeeContribution);
				newList[lInt2] = (int) Math.ceil(DP);
				newList[lInt2 +1 ] = (int) Math.round(DA);
				newList[lInt2 + 2] = (int) Math.round(employeeContribution);
				newList[lInt2 + 3] = DARate;
				newList[lInt2 + 4] =  (int) Math.round(emplrContribution);
				finalList.add(newList);
			}
 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finalList;
		}

	}

