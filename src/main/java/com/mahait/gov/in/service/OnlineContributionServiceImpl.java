package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.CmnLookupMst;
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
	public Boolean checkIfBillAlreadyGenerated(Long billGroupId, Long monthId, Long finYearId) {
		return onlineContributionRepo.checkIfBillAlreadyGenerated(billGroupId,monthId,finYearId);
	}


	@Override
	public List<DcpContributionModel> getEmpListForContribution(DcpContributionModel dcpContributionModel,
			OrgUserMst messages, String startDate) {
		
		List<DcpContributionModel> dcpContributionModelLst=new ArrayList<>();
		
		List<Object[]> lst=onlineContributionRepo.getEmpListForContribution(dcpContributionModel,messages,startDate);
		for(Object object[]:lst) {
			DcpContributionModel dcpContributionModel1=new DcpContributionModel();
			dcpContributionModel1.setDcpEmpId(StringHelperUtils.isNullLong(object[0]));
			dcpContributionModel1.setDcpsNO(StringHelperUtils.isNullString(object[1]));
			dcpContributionModel1.setEmployeeName(String.valueOf(object[2]));
			dcpContributionModel1.setPayCommission(StringHelperUtils.isNullString(object[3]));
			dcpContributionModel1.setBasicPay(StringHelperUtils.isNullDouble(object[4]));
			dcpContributionModel1.setDcpContributionId(StringHelperUtils.isNullLong(object[5]));
			dcpContributionModel1.setTypeOfPayment(StringHelperUtils.isNullString(object[6]));
			dcpContributionModel1.setMonthId(StringHelperUtils.isNullInt(object[7]));
			dcpContributionModel1.setFinYearId(StringHelperUtils.isNullInt(object[8]));
			dcpContributionModel1.setDaRate(StringHelperUtils.isNullDouble(object[9]));
			dcpContributionModel1.setRegStatus(StringHelperUtils.isNullInt(object[10]));
			dcpContributionModel1.setDoj(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setDa(StringHelperUtils.isNullDouble(object[12]));
			dcpContributionModel1.setDp(StringHelperUtils.isNullDouble(object[13]));
			dcpContributionModel1.setContribution(StringHelperUtils.isNullDouble(object[14]));
			dcpContributionModel1.setStartDate(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setEndDate(StringHelperUtils.isNullDate(object[11]));
			dcpContributionModel1.setEmprContribution(StringHelperUtils.isNullDouble(object[14]));
			
			dcpContributionModelLst.add(dcpContributionModel1);
		}
		return dcpContributionModelLst;
	}


}
