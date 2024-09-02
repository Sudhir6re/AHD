package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;

public interface OnlineContributionRepo {

	List<CmnLookupMst> getPaymentTypeLst();

	Boolean checkIfBillAlreadyGenerated(Long billGroupId, Long monthId, Long finYearId);

	List<Object[]> getEmpListForContribution(DcpContributionModel dcpContributionModel, OrgUserMst messages, String startDate);

}
