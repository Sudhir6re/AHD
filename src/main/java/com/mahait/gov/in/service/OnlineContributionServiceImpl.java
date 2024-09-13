package com.mahait.gov.in.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
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
		
		List<DcpContributionModel> dcpContributionModelLst=new ArrayList<>();
		
		
		
		 //  0    1         2                   3       4     5    6     7  8   9   10     11         12    13    14    15    16   17   18    19     20   21  
		//[34, d1235555, PALLAVI RAJ THAKRE, 700005, 39600, 0, 700047, 0, 0, 46, null, 2017-01-01, null, null, null, null, null, 0, 18216, 5782, 0.46, null]
		List<Object[]> lst=onlineContributionRepo.getEmpListForContribution(dcpContributionModel,messages,startDate);
		for(Object object[]:lst) {
			DcpContributionModel dcpContributionModel1=new DcpContributionModel();
			dcpContributionModel1.setDcpEmpId(StringHelperUtils.isNullBigInteger(object[0]).longValue());
			dcpContributionModel1.setDcpsNO(StringHelperUtils.isNullString(object[1]));
			dcpContributionModel1.setEmployeeName(String.valueOf(object[2]));
			dcpContributionModel1.setPayCommission(StringHelperUtils.isNullBigInteger(object[3]).longValue());
			
			if(object[4] instanceof Double) {
				dcpContributionModel1.setBasicPay(StringHelperUtils.isNullDouble(object[4]));
			}else if(object[4] instanceof Integer){
				dcpContributionModel1.setBasicPay(StringHelperUtils.isNullBigInteger(object[4]).doubleValue());
			}
			
			
			dcpContributionModel1.setDcpContributionId(StringHelperUtils.isNullBigInteger(object[5]).longValue());
			dcpContributionModel1.setTypeOfPayment(StringHelperUtils.isNullString(object[6]));
			
			dcpContributionModel1.setMonthId(StringHelperUtils.isNullInt(object[7]));
			dcpContributionModel1.setFinYearId(StringHelperUtils.isNullInt(object[8]));
			
			
			if(object[9] instanceof BigDecimal) {
				dcpContributionModel1.setDaRate(StringHelperUtils.isNullBigDecimal(object[9]).intValue());
			}else {
				dcpContributionModel1.setDaRate(StringHelperUtils.isNullInt(object[9]));
			}
			
			
			dcpContributionModel1.setRegStatus(StringHelperUtils.isNullInt(object[10]));
			dcpContributionModel1.setDoj(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setDa(StringHelperUtils.isNullDouble(object[12]));
			dcpContributionModel1.setDp(StringHelperUtils.isNullDouble(object[17]));
			dcpContributionModel1.setContribution(StringHelperUtils.isNullDouble(object[14]));
			dcpContributionModel1.setStartDate(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setEndDate(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setEmprContribution(StringHelperUtils.isNullDouble(object[14]));
			
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
