package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;

public interface OnlineContributionService {

	List<CmnLookupMst> getPaymentTypeLst();

	Boolean checkIfBillAlreadyGenerated(Long billGroupId, Long monthId, Long finYearId);

	List<DcpContributionModel> getEmpListForContribution(DcpContributionModel dcpContributionModel,
			OrgUserMst messages, String startDate);

	Long saveOrUpdate(DcpContributionModel dcpContributionModel);


	
}
