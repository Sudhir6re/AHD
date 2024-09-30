package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;
import com.mahait.gov.in.model.MstSchemeModel;

public interface OnlineContributionService {

	List<CmnLookupMst> getPaymentTypeLst();

	Boolean checkIfBillAlreadyGenerated(Long billGroupId, Integer monthId, Integer finYearId);

	List<DcpContributionModel> getEmpListForContribution(DcpContributionModel dcpContributionModel,
			OrgUserMst messages, String startDate);

	Long saveOrUpdate(DcpContributionModel dcpContributionModel);

	List<MstSchemeModel> getSchemeCodeByBillGroupId(String billGroupId);


	
}
