package com.mahait.gov.in.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.AllowanceDeductionRuleMstEntity;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.PaybillGenerationTrnDetails;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.entity.PaybillStatusEntity;
import com.mahait.gov.in.model.AbstractReportModel;
import com.mahait.gov.in.model.PaybillHeadMpgModel;
import com.mahait.gov.in.repository.PaybillGenerationTrnRepo;

@Service
@Transactional
public class PaybillGenerationTrnServiceImpl implements PaybillGenerationTrnService {

	@Autowired
	PaybillGenerationTrnRepo paybillHeadMpgRepo;

	@Autowired
	MstEmployeeService mstEmployeeService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@SuppressWarnings("unused")
	@Override
	public Long savePaybillHeadMpg(PaybillHeadMpgModel paybillHeadMpgModel) {
		PaybillGenerationTrnEntity objEntity = new PaybillGenerationTrnEntity();
		List<PaybillGenerationTrnDetails> lstPaybillGenerationTrnDetails = new ArrayList<>();
		PaybillGenerationTrnDetails hr = new PaybillGenerationTrnDetails();
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		String citygroup = null;
		String ddoCode = null;
		Double svnDA = 0d;
		Double da = 0d;
		Double cla = 0d;
		Double SevenPcBasic = 0d;
		Double sixPcBasic = 0d;
		Double grossAmount = 0d;
		Double totaldeduc = 0d;
		Double grossAmt = 0d;
		Double netAmount = 0d;
		Double dedByAG = 0d;
		Double dedByTreasury = 0d;
		Double payslipDeduc = 0d;
		Double payslipNet = 0d;
		Double paySlipTotalDeduc = 0d;
		Double dedByOthr = 0d;
		Double netAmt = 0d;
		Double dearnessPay = 0d;
		Double famAllow = 0d;
		Double hillStatAllow = 0d;
		Double ta = 0d;
		Double tribalAllow = 0d;
		Double hra = 0d;
		Double DaOnTA = 0d;
		Double hrr = 0d;
		Double pt = 0d;
		Double gisAmount = 0d;
		Double lic = 0d;
		Double CopBank = 0d;
		Double mtrCoSoc = 0d;
		Double mis = 0d;
		Double recurringDep = 0d;
		Double misc = 0d;
		Double atsInct50 = 0d;
		Double atsInct30 = 0d;
		Double force1Inct100 = 0d;
		Double force1Inct25 = 0d;
		Double pgAllow = 0d;
		Double groupAccPolicy = 0d;
		int isNonGovernment = 0;

		Double DaArr = 0d;
		Double npsEmprAllow = 0d;
		Double npsEmpContri = 0d;
		Double npsEmprContri = 0d;

		ddoCode = paybillHeadMpgModel.getDdoCode();

		DdoOffice ddoScreenEntity = mstEmployeeService.findAllGroup(ddoCode.trim());
		citygroup = ddoScreenEntity.getDcpsDdoOfficeCityClass();
		String spilt[] = ddoScreenEntity.getDcpsDdoOfficeCityClass().split("-");

		citygroup = spilt[1];
		if (citygroup != null) {
			citygroup = citygroup.trim();
		}

		String cadre = null;

		System.out.println("CityGroup" + citygroup);
		objEntity.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
		objEntity.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
		objEntity.setBillTypeId(paybillHeadMpgModel.getBillTypeId());
		objEntity.setCreatedUserId(paybillHeadMpgModel.getCreatedUserId());
		objEntity.setSchemeBillgroupId(paybillHeadMpgModel.getSchemeBillgroupId());
		objEntity.setCreatedDate(new Date());
		objEntity.setBillcreationDate(new Date());
		objEntity.setIsActive(5);
		objEntity.setDdoCode(ddoCode);
		objEntity.setNoOfEmployee(paybillHeadMpgModel.getNoOfEmployee());
		objEntity.setRemark("Testing");

		List<MstEmployeeEntity> mstEmployeeEntity = null;
		mstEmployeeEntity = mstEmployeeService.findAllWorkingEmployeeByDDOCodeAndBillGroup(
				paybillHeadMpgModel.getDdoCode(), paybillHeadMpgModel.getSchemeBillgroupId(),
				paybillHeadMpgModel.getPaybillMonth(), paybillHeadMpgModel.getPaybillYear());

		Long val = paybillHeadMpgRepo.getPaybillGenerationTrnId() + 1;
		objEntity.setPaybillGenerationTrnId(val);

		
		System.out.println("Hiiii checking----");
		paybillStatusEntity.setBillNo(val);
		paybillStatusEntity.setCreatedDate(new Date());
		paybillStatusEntity.setIsActive(1);
		paybillStatusEntity.setUserId(paybillHeadMpgModel.getCreatedUserId());
		paybillStatusEntity.setMacId(paybillHeadMpgModel.getMacId());

		String startDate = null;

		int month2 = paybillHeadMpgModel.getPaybillMonth();
		int year2 = paybillHeadMpgModel.getPaybillYear();
		if (month2 < 10) {
			startDate = String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
		} else {
			startDate = String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
		}

		int percentageRate[] = new int[3];

		percentageRate[0] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
				CommonConstants.PAYBILLDETAILS.SVNPC_ALLOW_DEDUC_CODE);
		percentageRate[1] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
				CommonConstants.PAYBILLDETAILS.SIXPC_ALLOW_DEDUC_CODE);
		percentageRate[2] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC, 0);

		int centralpercentageRate[] = new int[3];
		centralpercentageRate[0] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
		centralpercentageRate[1] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
		centralpercentageRate[2] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
				CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC);

		for (MstEmployeeEntity mstEmployeeEntity2 : mstEmployeeEntity) {

			// To Check Broken Period Exist or not
			Long payCommission = mstEmployeeEntity2.getPayCommissionCode();
			int percentage = 0;
			String percentageHRA = null;
			String cityClassStr = mstEmployeeEntity2.getCityClass().toString();
			String split1[] = cityClassStr.split("-");

			Double basic = 0d;
			String cityClass = split1[0];
			if (cityClass != null) {
				cityClass = cityClass.trim();
			}
			grossAmount = 0d;
			dedByAG = 0d;
			dedByOthr = 0d;
			dedByTreasury = 0d;
			netAmount = 0d;

			/*if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				percentage = percentageRate[0];
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC, cityClass);
			} else {
				percentage = percentageRate[1];
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, cityClass);
			}
*/
			int count = paybillHeadMpgRepo.isBrokenPeriodEmpty(mstEmployeeEntity2.getSevaarthId().trim(),
					String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
					String.valueOf(paybillHeadMpgModel.getPaybillYear()));
			PaybillGenerationTrnDetails paybillGenerationTrnDetails = new PaybillGenerationTrnDetails();
			paybillGenerationTrnDetails.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
			paybillGenerationTrnDetails.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
			paybillGenerationTrnDetails.setDesgCode(mstEmployeeEntity2.getDesignationCode());
			paybillGenerationTrnDetails.setSevaarthId(mstEmployeeEntity2.getSevaarthId().trim());
			paybillGenerationTrnDetails.setBankAccNo(mstEmployeeEntity2.getBankAcntNo());
			paybillGenerationTrnDetails.setBankId(mstEmployeeEntity2.getBankCode());
			paybillGenerationTrnDetails.setBankBranchId(mstEmployeeEntity2.getBankBranchCode());
			if(payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				paybillGenerationTrnDetails.setDaPercent(percentageRate[0]);
			}else if(payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
				paybillGenerationTrnDetails.setDaPercent(percentageRate[1]);
			}else {
				paybillGenerationTrnDetails.setDaPercent(percentageRate[2]);
			}
			/////paybillGenerationTrnDetails.setHraPercent(Integer.parseInt(percentageHRA));
			paybillGenerationTrnDetails.setRemark("Testing");
			paybillGenerationTrnDetails.setPaybillGenerationTrnId(val);
			objEntity.setPaybillGenerationTrnId(val);

			cadre = paybillHeadMpgRepo.getEmpCadre(mstEmployeeEntity2.getSevaarthId(),
					mstEmployeeEntity2.getEmpClass());

			npsEmprAllow = 0d;
			npsEmpContri = 0d;
			npsEmprContri = 0d;
			payslipDeduc = 0d;
			if (count > 0) {

				Map hmAllowDeducCodeAndValues = new HashMap();
				int basicpay = 0;
				int netpay = 0;
				int allowdedcode = 0;
				int isType = 0;
				String brokenMethodName = null;
				List<Object[]> lstBrokenPeriodData = paybillHeadMpgRepo.getBrokenPeriodData(
						mstEmployeeEntity2.getSevaarthId().toString().trim(),
						String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
						String.valueOf(paybillHeadMpgModel.getPaybillYear()), paybillHeadMpgModel.getDdoCode().trim());
				for (Object[] objects : lstBrokenPeriodData) {
					allowdedcode = (int) objects[4];
					hmAllowDeducCodeAndValues.put(objects[4], objects[5]); // 4 code,5 amount,6 Name
					basicpay = ((BigInteger) objects[2]).intValue();
					netpay = ((BigInteger) objects[3]).intValue();
					brokenMethodName = (String) objects[7];
					isType = (int) objects[8];

					if (brokenMethodName != null) {

						Double temp = 0d;
						if (objects[5] != null) {
							temp = new BigDecimal(objects[5].toString()).doubleValue();
						} else {
							temp = 0d;
						}
						switch (isType) {
						case 1:
							grossAmount += temp;
							break;
						case 2:
							dedByAG += temp;
							break;
						case 3:
							dedByTreasury += temp;
							break;
						case 4:
							dedByOthr += temp;
							break;

						}

						Field fieldName = null;
						try {
							fieldName = paybillGenerationTrnDetails.getClass().getDeclaredField(brokenMethodName);
							fieldName.setAccessible(true);
							try {

								fieldName.set(paybillGenerationTrnDetails, temp == null ? 0 : temp);
							} catch (IllegalArgumentException e) {
								System.out.println("field name" + fieldName);
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								System.out.println("field name" + fieldName);
								e.printStackTrace();
							}
						} catch (NoSuchFieldException e) {
							System.out.println("field name" + fieldName);
							e.printStackTrace();
						} catch (SecurityException e) {
							System.out.println("field name" + fieldName);
							e.printStackTrace();
						}

					}
				}

				// END:Fetch Broken Period Allowance and Deduction Data

				// Fetching Broken Period Data

				paybillGenerationTrnDetails.setBasicPay((double) basicpay);
				grossAmount = grossAmount + paybillGenerationTrnDetails.getBasicPay();

				totaldeduc = dedByAG + dedByTreasury + dedByOthr;
				netAmount = grossAmount - totaldeduc;

				paybillGenerationTrnDetails.setGrossAmt(grossAmount);
				paybillGenerationTrnDetails.setDedAdjAg(dedByAG);

				paybillGenerationTrnDetails.setOtherDedTry(dedByTreasury);
				/// deduct_adj_otr
				paybillGenerationTrnDetails.setDedAdjOtr(dedByOthr);

				paybillGenerationTrnDetails.setTotalDed(totaldeduc);

				paybillGenerationTrnDetails.setNetTotal(netAmount);
				paybillGenerationTrnDetails.setPayCommissionCode(mstEmployeeEntity2.getPayCommissionCode());
				if (mstEmployeeEntity2.getPayCommissionCode() == 700005) {
					paybillGenerationTrnDetails.setSevenPcLevel(mstEmployeeEntity2.getSevenPcLevel());
				} else {
					if (mstEmployeeEntity2.getPayInPayBand() != null)
						paybillGenerationTrnDetails.setPayBand(mstEmployeeEntity2.getPayInPayBand());
				}

				
				Serializable id12 = paybillHeadMpgRepo.saveHrPayPaybill(paybillGenerationTrnDetails);

				lstPaybillGenerationTrnDetails.add(paybillGenerationTrnDetails);

				grossAmt += grossAmount;

				netAmt += grossAmount - totaldeduc;

				objEntity.setBillGrossAmt((double) Math.round(grossAmt));
				objEntity.setBillNetAmount((double) Math.round(netAmt));
				

				// Serializable id = paybillHeadMpgRepo.savePaybillHeadMpg(objEntity);
				// Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);

				// return (Long) id;
			} else {

				int gradePaynew = 0;
				List<Object[]> object = mstEmployeeService.employeeAllowDeduction(mstEmployeeEntity2.getSevaarthId());
				paybillGenerationTrnDetails.setDesgCode(mstEmployeeEntity2.getDesignationCode());
				Double gpfpercentage = 0d;

				Double hraBasic = 0d;
				if (mstEmployeeEntity2.getSevenPcBasic() != null) {

					if (mstEmployeeEntity2.getRevisedBasic() != null) {

						basic = mstEmployeeEntity2.getRevisedBasic();
					} else {
						basic = mstEmployeeEntity2.getSevenPcBasic();
					}

					basic = basic.doubleValue();
				} else {
					SevenPcBasic = 0d;
				}
				if (mstEmployeeEntity2.getBasicPay() != null
						&& !mstEmployeeEntity2.getPayCommissionCode().equals(700005)) {
					basic = mstEmployeeEntity2.getBasicPay();
				}

				for (Object[] object12 : object) {
					int isTypeforSum = 0;
					int a = (int) object12[5];
					String str = (String) object12[6];
					String gisgroup = (String) object12[11];
					String methodName = (String) object12[12];
					if (object12[13] != null)
						isTypeforSum = (int) object12[13];
					String groupname = (String) object12[7];
					// gisAmount = (double) object12[8];
					if (object12[9] != null)
						gradePaynew = (int) object12[9];
					int allowDeducCode = (int) object12[2];
					String physicalhand = (String) object12[10];
					int isRuleBased = 0;
					if (object12[14] != null)
						isRuleBased = (int) object12[14];

					int isNonComputation = 0;
					if (object12[15] != null)
						isNonComputation = (int) object12[15];
					isNonGovernment = 0;
					if (object12[16] != null)
						isNonGovernment = (int) object12[16];

					Double svnPcBasic = 0d;

					if (allowDeducCode == 37) {
						System.out.println("------");
					}

					// SVN DA
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_PC_DA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
							&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC)) {

						svnDA = (double) ((basic * percentage)
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						grossAmount += svnDA;

						paybillGenerationTrnDetails.setSvnpcDa(svnDA);
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
							&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {

						da = (double) (Math.round(
								(basic * percentage) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						paybillGenerationTrnDetails.setDa((double) Math.round((da)));

						grossAmount += da;
					}
					// CLA

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& employeeAllowDeducComponentAmtEntity.getNetAmt() > 0d
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) {

							paybillGenerationTrnDetails
									.setCla((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							cla = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							grossAmount += cla;
						} else {

							Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();
							int claamt = 0;

							List<AllowanceDeductionRuleMstEntity> lstCla = paybillHeadMpgRepo.getClaAmaountDtls(
									mstEmployeeEntity2.getSevenPcLevel(), basic, citygroup,
									mstEmployeeEntity2.getPayCommissionCode(), allowDeducCode);
							if (lstCla.size() > 0)
								cla = lstCla.get(0).getAmount();
							paybillGenerationTrnDetails.setCla((double) cla);
							grossAmount += cla;
						}
					}
					// FAMILY_PLAN_ALLOW
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FAMILY_PLAN_ALLOW)) {

						if (payCommission == 700005) {
							famAllow = paybillHeadMpgRepo.calculateFamilyAllow(payCommission,
									mstEmployeeEntity2.getSevenPcLevel(), allowDeducCode);
						} else {
							famAllow = paybillHeadMpgRepo.calculateFamilyAllow(payCommission,
									mstEmployeeEntity2.getGradePay(), allowDeducCode);
						}
						paybillGenerationTrnDetails.setFamilyPalnning(famAllow);
						grossAmount += famAllow;
					}

					// Start Travels Allowances for 6PC
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						ta = paybillHeadMpgRepo.fetchtaDtls(basic, payCommission, allowDeducCode,
								mstEmployeeEntity2.getGradePay(), cityClass,
								mstEmployeeEntity2.getPhysicallyHandicapped());
						Long percentBasic = mstEmployeeEntity2.getPercentageOfBasic() == null ? 100
								: mstEmployeeEntity2.getPercentageOfBasic();
						Double ratio = (double) (percentBasic / 100);
						ta = ratio * ta;
						paybillGenerationTrnDetails.setTaa(ta);
						grossAmount += ta;
					}
					// Start Travels Allowances for 7PC
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();
						// double precision, bigint, integer, bigint, character varying, character
						// varying
						ta = paybillHeadMpgRepo.fetchtaDtls(basic, payCommission, allowDeducCode, gradelevel, cityClass,
								mstEmployeeEntity2.getPhysicallyHandicapped());
						paybillGenerationTrnDetails.setTaa(ta);
						ta = paybillGenerationTrnDetails.getTaa();
						grossAmount += ta;
					}
					/* HRA component */
					//
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						hra = paybillHeadMpgRepo.fetchHraDtls(basic, startDate, cityClass, allowDeducCode);
						paybillGenerationTrnDetails.setHra((double) Math.round(hra));
						grossAmount += hra;

					}
					/* End of HRA Component */

					// Professional Tax//
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						pt = paybillHeadMpgRepo.calculatePt(basic, paybillHeadMpgModel.getPaybillMonth());// paybillHeadMpgRepo
						paybillGenerationTrnDetails.setPt(pt);
						dedByAG += pt;
					} else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Accidential_Policy)) {

						groupAccPolicy = paybillHeadMpgRepo.fetchAccidentialPilocyDtls(startDate, cadre,
								allowDeducCode);

						paybillGenerationTrnDetails.setAccPolicy((double) Math.round(groupAccPolicy));
						dedByTreasury += groupAccPolicy;
					}

					// Start GIS Component
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						gisAmount = paybillHeadMpgRepo.findGisComponentValue(groupname, mstEmployeeEntity2.getDoj(),
								"20" + startDate, allowDeducCode);
						paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						dedByTreasury += gisAmount;
					}

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)) {
						if ((year2 == 24 && month2 >= 8) || (year2 >= 25 && month2 >= 1)) {

							npsEmprAllow = (double) (Math.round((basic + svnDA + DaArr) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

						} else {
							npsEmprAllow = (double) (Math.round((basic + svnDA + DaArr) * 10
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}

						grossAmount += npsEmprAllow;

						paybillGenerationTrnDetails.setNpsEmplr(npsEmprAllow);

					}

					/*
					 * // NPS Emp Contri else if
					 * (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_NPS_EMP_CONTRI)) { npsEmpContri = (double) (Math.ceil(
					 * (basic + svnDA + DaArr) * 10 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * paybillGenerationTrnDetails.setNpsEmployeeContri(npsEmpContri);
					 * dedByTreasury+=npsEmpContri; }
					 */

					// NPS Employer Contri
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)) {

						if (year2 == 24 && month2 >= 8 || year2 >= 25 && month2 >= 1) {
							npsEmprContri = (double) (Math.round((basic + svnDA + DaArr) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						} else {
							npsEmprContri = (double) (Math.round((basic + svnDA + DaArr) * 10
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}
						paybillGenerationTrnDetails.setNpsEmplrContriDed(npsEmprContri);

						dedByTreasury += npsEmprContri;
					}

					// End GIS Component
					if (methodName != null) {
						if (isNonComputation == 1) {
							EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
									.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(), allowDeducCode);

							if (employeeAllowDeducComponentAmtEntity != null) {

								Double temp = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

								switch (isTypeforSum) {
								case 1:
									grossAmount += temp;
									break;
								case 2:
									dedByAG += temp;
									break;
								case 3:
									if (isNonGovernment == 1) {
										payslipDeduc += temp;
									} else {
										dedByTreasury += temp;
									}

									break;
								case 4:
									dedByOthr += temp;
									break;
								}

								Field fieldName = null;
								try {
									fieldName = paybillGenerationTrnDetails.getClass().getDeclaredField(methodName);
									fieldName.setAccessible(true);
									try {

										/// fieldName.getType();

										fieldName.set(paybillGenerationTrnDetails, temp == null ? 0 : temp);
									} catch (IllegalArgumentException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									} catch (IllegalAccessException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									}
								} catch (NoSuchFieldException e) {
									System.out.println("fieldName" + fieldName);
									e.printStackTrace();
								} catch (SecurityException e) {
									System.out.println("fieldName" + fieldName);
									e.printStackTrace();
								}

							}

						}
						if (isRuleBased == 1) {

							List<AllowanceDeductionRuleMstEntity> lst = paybillHeadMpgRepo
									.fetchComponentDtlsByCompoId(allowDeducCode);

							Double tempVal = 0d;

							if (lst.size() > 0) {
								if (lst.get(0).getPercentage() != null) {

									tempVal = (double) ((Math.round(basic) * lst.get(0).getPercentage()
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

									switch (isTypeforSum) {
									case 1:
										grossAmount += tempVal;
										break;
									case 2:
										dedByAG += +tempVal;
										break;
									case 3:
										dedByTreasury += tempVal;
										break;
									case 4:
										dedByOthr += tempVal;
										break;

									}

									Field fieldName = null;
									try {
										fieldName = paybillGenerationTrnDetails.getClass().getDeclaredField(methodName);
										fieldName.setAccessible(true);
										try {
											fieldName.set(paybillGenerationTrnDetails, tempVal == null ? 0 : tempVal);
										} catch (IllegalArgumentException e) {
											System.out.println("fieldName" + fieldName);
											e.printStackTrace();
										} catch (IllegalAccessException e) {
											System.out.println("fieldName" + fieldName);
											e.printStackTrace();
										}
									} catch (NoSuchFieldException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									} catch (SecurityException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									}

								} else {
									tempVal = (double) (Math.round(lst.get(0).getAmount()));

									switch (isTypeforSum) {
									case 1:
										grossAmount += tempVal;

										break;
									case 2:

										dedByAG += tempVal;
										break;
									case 3:
										dedByTreasury += tempVal;
										break;
									case 4:
										dedByOthr += tempVal;
										break;

									}

									Field fieldName = null;
									try {
										fieldName = paybillGenerationTrnDetails.getClass().getDeclaredField(methodName);
										fieldName.setAccessible(true);
										try {
											fieldName.set(paybillGenerationTrnDetails, tempVal == null ? 0 : tempVal);
										} catch (IllegalArgumentException e) {
											System.out.println("fieldName" + fieldName);
											e.printStackTrace();
										} catch (IllegalAccessException e) {
											System.out.println("fieldName" + fieldName);
											e.printStackTrace();
										}
									} catch (NoSuchFieldException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									} catch (SecurityException e) {
										System.out.println("fieldName" + fieldName);
										e.printStackTrace();
									}
								}
							}
						}
					}

				}

				totaldeduc = dedByAG + dedByTreasury + dedByOthr;

				grossAmount += basic;
				netAmt = grossAmount - totaldeduc;
				paybillGenerationTrnDetails.setBasicPay(basic);
				paySlipTotalDeduc = totaldeduc + payslipDeduc;
				payslipNet = grossAmount - paySlipTotalDeduc;

				paybillGenerationTrnDetails.setGrossAmt(grossAmount);
				paybillGenerationTrnDetails.setDedAdjAg(dedByAG);
				paybillGenerationTrnDetails.setPayslipDeduc(payslipDeduc);
				paybillGenerationTrnDetails.setPayslipNet(payslipNet);
				paybillGenerationTrnDetails.setPaysliptotalDeduc(paySlipTotalDeduc);

				paybillGenerationTrnDetails.setOtherDedTry(dedByTreasury);
				/// deduct_adj_otr
				paybillGenerationTrnDetails.setDedAdjOtr(dedByOthr);

				paybillGenerationTrnDetails.setTotalDed(totaldeduc);

				paybillGenerationTrnDetails.setNetTotal(netAmt);

				paybillGenerationTrnDetails.setPayCommissionCode(mstEmployeeEntity2.getPayCommissionCode());
				if (mstEmployeeEntity2.getPayCommissionCode() == 700005) {
					paybillGenerationTrnDetails.setSevenPcLevel(mstEmployeeEntity2.getSevenPcLevel());
				} else {
					if (mstEmployeeEntity2.getPayInPayBand() != null)
						paybillGenerationTrnDetails.setPayBand(mstEmployeeEntity2.getPayInPayBand());
				}


				lstPaybillGenerationTrnDetails.add(paybillGenerationTrnDetails);

				// Serializable id12 =
				// paybillHeadMpgRepo.saveHrPayPaybill(paybillGenerationTrnDetails);

			}

		}
		grossAmt += grossAmount;

		netAmt += grossAmount - totaldeduc;

		objEntity.setBillGrossAmt((double) Math.round(grossAmt));
		objEntity.setBillNetAmount((double) Math.round(netAmt));

		Long saveId = paybillHeadMpgRepo.saveBulkPaybillDetail(lstPaybillGenerationTrnDetails);

		Serializable id = paybillHeadMpgRepo.savePaybillHeadMpg(objEntity);
		Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		return val;
	}

	private double valueOf(Object object) {

		return 0;
	}

	@Override
	public PaybillGenerationTrnEntity findForwardChangeStatementById(Long paybillGenerationTrnId, Long userId,
			String ip) {

		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(2); // Change Statement Forwarded
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(2);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);
			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findApproveChangeStatementById(Long paybillGenerationTrnId, Long userId,
			String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(3); // Change Statement Approved
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(3);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findRejectChangeStatementById(Long paybillGenerationTrnId, Long userId,
			String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(4); // Change Statement Rejected
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(4);
			//// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findDeleteBillById(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(8); // Deleted
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(8);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findForwardBillById(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(5); // Paybill Generated
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(5);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity forwardPayBillToLevel2(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.forwardPayBillToLevel2(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(6); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(6);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	/* Added by Brijoy 24-12-2020 by Generate Paybill */
	@Override
	public PaybillGenerationTrnEntity generatePaybill(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.forwardPayBillToLevel2(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(5); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(5);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public int getpaybilldata(BigInteger billGroup, int paybillMonth, int paybillYear) {
		// TODO Auto-generated method stub
		int isDataExists = paybillHeadMpgRepo.isPaybillExists(billGroup, paybillMonth, paybillYear);
		return isDataExists;
	}

	public List<Object[]> findpaybill(BigInteger billGroup, int paybillMonth, int paybillYear, String ddo) {
		//
		return paybillHeadMpgRepo.findpaybill(billGroup, paybillMonth, paybillYear, ddo);

	}

	@Override
	public List<Object[]> getChangeStatementReport(String paybillGenerationTrnId) {
		List<Object[]> obj = paybillHeadMpgRepo.getChangeStatementReport(paybillGenerationTrnId);
		return obj;
	}

	@Override
	public List<Object[]> getChangeStatementReportFromPreviousMonth(String paybillGenerationTrnId) {
		List<Object[]> obj = paybillHeadMpgRepo.getChangeStatementReportFromPreviousMonth(paybillGenerationTrnId);
		return obj;
	}

	// @Override
	// public int getpaybilldata(String billGroup, String paybillMonth, String
	// paybillYear) {
	// // TODO Auto-generated method stub
	// return 0;
	// }

	public PaybillGenerationTrnEntity consolidatedPaybill(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.consolidatedPaybill(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(9); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(9);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);
			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public List<Object[]> findDDOinfo(String userName) {
		List<Object[]> obj = paybillHeadMpgRepo.findDDOinfo(userName);
		return obj;
	}

	@Override
	public List<Object[]> findregIdinfo(Long regid) {
		List<Object[]> obj = paybillHeadMpgRepo.findregIdinfo(regid);
		return obj;
	}

	@Override
	public List<Object[]> findmonthinfo(BigInteger currmonth) {
		List<Object[]> obj = paybillHeadMpgRepo.findmonthinfo(currmonth);
		return obj;
	}

	@Override
	public List<Object[]> findofcIdinfo(Long ofcid) {
		List<Object[]> obj = paybillHeadMpgRepo.findofcIdinfo(ofcid);
		return obj;
	}

	@Override
	public List<AbstractReportModel> getAbstractReport(String paybillGenerationTrnId) {

		List<Object[]> lstprop = paybillHeadMpgRepo.getAbstractReport(paybillGenerationTrnId);
		List<AbstractReportModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				AbstractReportModel obj = new AbstractReportModel();

				obj.setInstName(StringHelperUtils.isNullString(objLst[0]));
				obj.setBillName(StringHelperUtils.isNullString(objLst[1]));
				obj.setGrossSalary(StringHelperUtils.isNullDouble(objLst[2]));
				obj.setFa(StringHelperUtils.isNullDouble(objLst[3]));
				obj.setRecovery(StringHelperUtils.isNullDouble(objLst[4]));
				obj.setGPF(StringHelperUtils.isNullDouble(objLst[5]));
				obj.setDcpsreg(StringHelperUtils.isNullDouble(objLst[6]));
				obj.setDcpsdel(StringHelperUtils.isNullDouble(objLst[7]));
				obj.setDcpsPay(StringHelperUtils.isNullDouble(objLst[8]));
				obj.setDcpsda(StringHelperUtils.isNullDouble(objLst[9]));
				obj.setNpsEmprDeduc(StringHelperUtils.isNullDouble(objLst[10]));
				obj.setIt(StringHelperUtils.isNullDouble(objLst[11]));
				obj.setPt(StringHelperUtils.isNullDouble(objLst[12]));
				obj.setCompadv(StringHelperUtils.isNullDouble(objLst[13]));
				obj.setOtherded(StringHelperUtils.isNullDouble(objLst[14]));
				obj.setPli(StringHelperUtils.isNullDouble(objLst[15]));
				obj.setGis(StringHelperUtils.isNullDouble(objLst[16]));
				obj.setAccPolicy(StringHelperUtils.isNullDouble(objLst[17]));
				obj.setRevenueStamp(StringHelperUtils.isNullDouble(objLst[18]));
				obj.setTotdedction(StringHelperUtils.isNullDouble(objLst[19]));
				obj.setNetpay(StringHelperUtils.isNullDouble(objLst[20]));
				obj.setRecurringDeposit(StringHelperUtils.isNullDouble(objLst[21]));
				obj.setNgrlic(StringHelperUtils.isNullDouble(objLst[22]));
				obj.setNgrmisc(StringHelperUtils.isNullDouble(objLst[23]));
				obj.setNgrbankloan(0d);/// StringHelperUtils.isNullDouble(objLst[24])
				obj.setNgrsocloan(0d);// StringHelperUtils.isNullDouble(objLst[25])
				obj.setNgrtotded(StringHelperUtils.isNullDouble(objLst[26]));
				obj.setTotalSalary(StringHelperUtils.isNullDouble(objLst[27]));

				lstObj.add(obj);
			}
		}
		return lstObj;

		/*
		 * // TODO Auto-generated method stub ///return
		 * ddoInfoRepo.getLevel1DDOList(lStrDdoCode);
		 * 
		 * 
		 * 
		 * List<Object[]> obj =
		 * paybillHeadMpgRepo.getAbstractReport(paybillGenerationTrnId); return obj;
		 */
	}

	@Override
	public List<Object[]> findyearinfo(BigInteger yearcurr) {
		List<Object[]> obj = paybillHeadMpgRepo.findyearinfo(yearcurr);
		return obj;
	}

	@Override
	public List<Object[]> getViewDetialsReport(Integer consolidatedId) {
		List<Object[]> obj = paybillHeadMpgRepo.getViewDetialsReport(consolidatedId);
		return obj;
	}

	@Override
	public Object[] getSevaarthIdMappedWithBill(String ddocode, int noofemp, Long schemeBillGroupId, int month,
			int year) {
		boolean flag = false;
		/*
		 * MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
		 * .findAllMpgSchemeBillGroupbyParameter(schemeBillGroupId);
		 */
		List<Object[]> obj = new ArrayList<Object[]>();
		List<MstEmployeeEntity> mstEmployeeEntity = mstEmployeeService
				.findAllWorkingEmployeeByDDOCodeAndBillGroup(ddocode, schemeBillGroupId, month, year);

		Long val = paybillHeadMpgRepo.getPaybillGenerationTrnId() + 1;
		StringBuffer sb = new StringBuffer();
		for (MstEmployeeEntity mstEmployeeEntity2 : mstEmployeeEntity) {

			// To Check Broken Period Exist or not
			int count = paybillHeadMpgRepo.isBrokenPeriodEmpty(mstEmployeeEntity2.getSevaarthId(),
					String.valueOf(month), String.valueOf(year));
			if (count == 0) {
				sb.append(mstEmployeeEntity2.getSevaarthId() + ",");
				flag = true;
			}
		}
		Object[] res = new Object[2];
		res[0] = flag;
		if (sb.length() != 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		res[1] = sb.toString().trim();
		return res;
	}

	@Override
	public Integer isPaybillExists(Long schemeBillgroupId, int paybillMonth, int paybillYear) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.isPaybillExistsForCurrentMonth(schemeBillgroupId, paybillMonth, paybillYear);
	}

	@Override
	public int getCheckIsBillInProcess(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.getCheckIsBillInProcess(monthName, yearName, schemeBillGroupId, paybillType);
	}

	@Override
	public String isEmpRetired(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType,
			String userName) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.isEmpRetired(monthName, yearName, schemeBillGroupId, paybillType, userName);
	}

	@Override
	public PaybillGenerationTrnEntity findPaybillById(Long paybillGenerationTrnId, String voucherNo, Date voucherDate,
			String chqNo, Date chequeDate, String accNo, String ifscCode, Long userId) {
		// TODO Auto-generated method stub

		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findPaybillById(paybillGenerationTrnId);
		if (objPaybillGeberationTrnEntity != null) {
			/*
			 * String Date=voucherDate; SimpleDateFormat formatter1=new
			 * SimpleDateFormat("dd/MM/yyyy"); Date date1=null; try { date1 =
			 * formatter1.parse(Date); } catch (ParseException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 */
			objPaybillGeberationTrnEntity.setVoucherNo(voucherNo);
			objPaybillGeberationTrnEntity.setVoucherDate(voucherDate);
			objPaybillGeberationTrnEntity.setChequeDate(chequeDate);
			objPaybillGeberationTrnEntity.setChequeNo(chqNo);
			objPaybillGeberationTrnEntity.setAccountNo(accNo);
			objPaybillGeberationTrnEntity.setIfscCode(ifscCode);
			objPaybillGeberationTrnEntity.setIsActive(14);
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);
			paybillHeadMpgRepo.updateVoucherEntry(objPaybillGeberationTrnEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public List<MstEmployeeEntity> checkedBgisAndGisCatNull(String schemeBillGroupId, String userName) {
		// TODO Auto-generated method stub
		List<MstEmployeeEntity> lstMstEmployeeEntity = paybillHeadMpgRepo.checkedBgisAndGisCatNull(schemeBillGroupId,
				userName);
		return lstMstEmployeeEntity;
	}

}
