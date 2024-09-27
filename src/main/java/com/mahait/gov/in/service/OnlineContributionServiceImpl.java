package com.mahait.gov.in.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.DcpsContributionEntity;
import com.mahait.gov.in.entity.MstDcpsContriVoucherDtlEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;
import com.mahait.gov.in.repository.OnlineContributionRepo;

@Transactional
@Service
public class OnlineContributionServiceImpl implements OnlineContributionService{

	
	@Autowired
	OnlineContributionRepo onlineContributionRepo;
	
	
	@Override
	public List<CmnLookupMst> getPaymentTypeLst() {
		// TODO Auto-generated method stub
		return onlineContributionRepo.getPaymentTypeLst();
	}


	@Override
	public Boolean checkIfBillAlreadyGenerated(Long billGroupId, Integer monthId, Integer finYearId) {
		return onlineContributionRepo.checkIfBillAlreadyGenerated(billGroupId,monthId,finYearId);
	}


	@Override
	public List<DcpContributionModel> getEmpListForContribution(DcpContributionModel dcpContributionModel,
			OrgUserMst messages, String startDate) {
		
		//List<DcpContributionModel> dcpContributionModelLst=new ArrayList<>();
		 //  0    1         2                   3       4     5    6     7  8   9   10     11         12    13    14    15    16   17   18    19     20   21  
		//[34, d1235555, PALLAVI RAJ THAKRE, 700005, 39600, 0, 700047, 0, 0, 46, null, 2017-01-01, null, null, null, null, null, 0, 18216, 5782, 0.46, null]
		List<Object[]> lstobject=onlineContributionRepo.getEmpListForContribution(dcpContributionModel,messages,startDate);
		List<DcpContributionModel> dcpContributionModelLst = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Date date = null;
		try {
			date = sdf.parse(startDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		Date newStartDate = calendar.getTime();

		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); // Set to the last day of
																								// the month
		Date newEndDate = calendar.getTime();
		String endDate = sdf.format(newEndDate);

		if (dcpContributionModel.getTypeOfPayment() != null) {
			if (dcpContributionModel.getTypeOfPayment().equals("700048")) {
				startDate = sdf.format(dcpContributionModel.getDAArrearStartDate());
			} else if (dcpContributionModel.getTypeOfPayment().equals("700049")) {
				startDate = sdf.format(dcpContributionModel.getPayArrearStartDate());
			}
		}
		
		
		for (Object object[]:lstobject) {
			
			Integer lInt2 = 0;
			Double BasicPay = 0d;
			Double DP = 0D;
			String lStrDP = "";
			Double DARate = 0d;
			String lStrTypeOfPayment = "";
			Double DA = 0D;
			Double employeeContribution = 0D;
			String lStrDA = "";
			Double emplrContribution = 0D; 
			
		//	Object[] object = (Object[]) empList.get(lInt1);
			DcpContributionModel dcpContributionModel1 = new DcpContributionModel();

			dcpContributionModel1.setDcpEmpId(StringHelperUtils.isNullBigInteger(object[0]).longValue());
			dcpContributionModel1.setDcpsNO(StringHelperUtils.isNullString(object[1]));
			dcpContributionModel1.setEmployeeName(String.valueOf(object[2]));
			dcpContributionModel1.setPayCommission(StringHelperUtils.isNullBigInteger(object[3]).longValue());
			
			
			if(object[4] instanceof Double) {
				dcpContributionModel1.setBasicPay(StringHelperUtils.isNullDouble(object[4]));
			}else if(object[4] instanceof Integer){
				dcpContributionModel1.setBasicPay(StringHelperUtils.isNullBigInteger(object[4]).doubleValue());
			}
			
			BasicPay=dcpContributionModel1.getBasicPay();
			
			dcpContributionModel1.setDcpContributionId(StringHelperUtils.isNullBigInteger(object[5]).longValue());
			dcpContributionModel1.setTypeOfPayment(StringHelperUtils.isNullString(object[6]));

			
			
			
			if (object[7] instanceof BigInteger) {
				dcpContributionModel1.setMonthId(StringHelperUtils.isNullBigInteger(object[7]).intValue());
			} else {
				dcpContributionModel1.setMonthId(StringHelperUtils.isNullInt(object[7]));
			}

			if (object[8] instanceof BigInteger) {
				dcpContributionModel1.setFinYearId(StringHelperUtils.isNullBigInteger(object[8]).intValue());
			} else {
				dcpContributionModel1.setFinYearId(StringHelperUtils.isNullInt(object[8]));
			}

			if (object[9] instanceof BigDecimal) {
				dcpContributionModel1.setDaRate(StringHelperUtils.isNullBigDecimal(object[9]).intValue());
			} else {
				dcpContributionModel1.setDaRate(StringHelperUtils.isNullInt(object[9]));
			}
			
			

			if(object[10]!=null) {
				dcpContributionModel1.setRegStatus(StringHelperUtils.isNullInt(object[10]));
			}else {
				dcpContributionModel1.setRegStatus(0);
			}
			
			
			
			dcpContributionModel1.setDoj(StringHelperUtils.isNullDate(object[11]));
			
			
			if(object[11]!=null) {
				dcpContributionModel1.setStartDate(StringHelperUtils.isNullDate(object[11]));
			}else{
				dcpContributionModel1.setStartDate(newStartDate);
			}
			
			if(object[12]!=null) {
				dcpContributionModel1.setEndDate(StringHelperUtils.isNullDate(object[12]));
			}else {
				dcpContributionModel1.setEndDate(newEndDate);
			}
			
			
			
			
			dcpContributionModel1.setSevaarthId(StringHelperUtils.isNullString(object[13]));
			
			DP = 0D;
			lStrDP = "";
			if (null != object[13]) {
				lStrDP = object[13].toString();
			}
			if (object[3].toString().equals("700015") || object[3].toString().equals("700345")) { // paycommision
				if (null != lStrDP && !"".equals(lStrDP)) {
					DP = Double.parseDouble(lStrDP);
				} else {
					DP = BasicPay / 2;
				}
			}

			DARate = 0.01 * Double.parseDouble(object[9].toString());
			lStrTypeOfPayment = object[6].toString();
			DA = 0D;
			employeeContribution = 0D;
			emplrContribution = 0D;

			lStrDA = "";

			if (null != object[12]) {
				lStrDA = object[12].toString(); // da aMOUNT
			}

			if (lStrTypeOfPayment.equals("700048")) {
				if (object[12] != null) {
					DA = Double.parseDouble(object[12].toString());
				}
				if (object[14] != null) {
					employeeContribution = Double.parseDouble(object[14].toString());
				}
				if (object[17] != null) {
					emplrContribution = Double.parseDouble(object[17].toString());
				}
			} else if (lStrTypeOfPayment.equals("700049")) {
				DA = 0D;
				if (object[14] != null) {
					employeeContribution = Double.parseDouble(object[14].toString());
				}
				if (object[17] != null) {
					emplrContribution = Double.parseDouble(object[17].toString());
				}
			} else {
				if (null != lStrDA && !"".equals(lStrDA)) {
					DA = Double.parseDouble(lStrDA);
				} else {
					DA = ((BasicPay + DP) * DARate);
				}

				if (null != object[14]) {
					employeeContribution = Double.parseDouble(object[14].toString());
				} else {
					if (object[3].toString().equals("700015")) {
						employeeContribution = ((double) Math.ceil(BasicPay) + Math.ceil(DP) + Math.round(DA))
								* 0.10;
					} else {
						employeeContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.10;
					}
				}

				if (!object[17].toString().equals("0.0") && !object[17].toString().equals("0")) {
					emplrContribution = Double.parseDouble(object[17].toString());
				} else {
					if ((dcpContributionModel.getFinYearId() <= 20 && dcpContributionModel.getMonthId() <= 3) || dcpContributionModel.getFinYearId() < 20) { // 2019(Old 32 if ((finYearId <= 32
						
						if (object[3].toString().equals("700015")) {
							emplrContribution = ((double) Math.ceil(BasicPay) + Math.ceil(DP) + Math.round(DA))
									* 0.10;
						} else {
							emplrContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.10;
						}
					} else {
						if (object[3].toString().equals("700015")) {
							emplrContribution = ((double) Math.ceil(BasicPay) + Math.ceil(DP) + Math.round(DA))
									* 0.14;
						} else {
							emplrContribution = ((double) Math.ceil(BasicPay) + Math.round(DA)) * 0.14;
						}

					}
				}
			}
			DA = (double) Math.round(DA);

			employeeContribution = (double) Math.round(employeeContribution);

			dcpContributionModel1.setDp(DP);
			dcpContributionModel1.setDa(DA);
			dcpContributionModel1.setEmprContribution(emplrContribution);
			dcpContributionModel1.setContribution(employeeContribution);

			dcpContributionModelLst.add(dcpContributionModel1);
		}

		return dcpContributionModelLst;
	}


	@Override
	public Long saveOrUpdate(DcpContributionModel dcpContributionModel) {
       MstDcpsContriVoucherDtlEntity  mstDcpsContriVoucherDtlEntity=new MstDcpsContriVoucherDtlEntity();
       mstDcpsContriVoucherDtlEntity.setBillGroupId(dcpContributionModel.getBillGroupId());
       mstDcpsContriVoucherDtlEntity.setCreatedDate(new Timestamp((new Date().getTime())));
       mstDcpsContriVoucherDtlEntity.setCreatedPostId(dcpContributionModel.getCreatedPostId());
       mstDcpsContriVoucherDtlEntity.setDdoCode(dcpContributionModel.getDdoCode());
       mstDcpsContriVoucherDtlEntity.setMonthId(dcpContributionModel.getMonthId());
       mstDcpsContriVoucherDtlEntity.setYearId(dcpContributionModel.getFinYearId());
       mstDcpsContriVoucherDtlEntity.setStatus('1');
       mstDcpsContriVoucherDtlEntity.setTreasuryCode(dcpContributionModel.getTreasuryCode());
       
      Long save= onlineContributionRepo.saveMstDcpsContriVoucherDtlEntity(mstDcpsContriVoucherDtlEntity);
       
       for(DcpContributionModel dcpContributionModel1:dcpContributionModel.getLstDcpContributionModel()) {
    	   DcpsContributionEntity dcpsContributionEntity=new DcpsContributionEntity();
    	   dcpsContributionEntity.setBasicPay(dcpContributionModel1.getBasicPay());
    	   dcpsContributionEntity.setBillGroupId(dcpContributionModel1.getBillGroupId());
    	   dcpsContributionEntity.setDa(dcpContributionModel1.getDa());
    	   dcpsContributionEntity.setDp(dcpContributionModel1.getDp());
    	   dcpsContributionEntity.setTypeOfPayment(dcpContributionModel1.getTypeOfPayment());
    	   dcpsContributionEntity.setDcpEmpId(dcpContributionModel1.getDcpEmpId());
    	   dcpsContributionEntity.setDdoCode(dcpContributionModel1.getDdoCode());
    	   dcpsContributionEntity.setDelayedFinYearId(dcpContributionModel.getDelayedFinYearId());
    	   dcpsContributionEntity.setDelayedMonthId(dcpContributionModel.getDelayedMonthId());
    	   dcpsContributionEntity.setStartDate(dcpContributionModel1.getStartDate());
    	   dcpsContributionEntity.setEndDate(dcpContributionModel1.getEndDate());
    	   dcpsContributionEntity.setFinYearId(dcpContributionModel.getFinYearId());
    	   dcpsContributionEntity.setMonthId(dcpContributionModel.getMonthId());
    	   dcpsContributionEntity.setPayCommission(dcpContributionModel.getPayCommission().toString());
    	   dcpsContributionEntity.setContribution(dcpContributionModel.getContribution());
    	   dcpsContributionEntity.setContributionEmpr(dcpContributionModel.getEmprContribution().floatValue());
    	   onlineContributionRepo.saveDcpsContributionEntity(dcpsContributionEntity);
    	   
       }
		return null;
	}


}
