package com.mahait.gov.in.service;

import java.util.List;
import java.util.Map;

import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;
import com.mahait.gov.in.model.MstSchemeModel;

public interface OnlineContributionService {

	List<CmnLookupMst> getPaymentTypeLst();

	Boolean checkIfBillAlreadyGenerated(Long billGroupId, Integer monthId, Integer finYearId);

	List<DcpContributionModel> getEmpListForContribution(DcpContributionModel dcpContributionModel,
			OrgUserMst messages, String startDate);

	Long saveOrUpdate(DcpContributionModel dcpContributionModel, OrgUserMst messages);

	List<MstSchemeModel> getSchemeCodeByBillGroupId(String billGroupId);


	DcpContributionModel calculateDcpsArrear(Map<String, String> formData);

	double findSumContribution(String sevaarthId, String paymentType, Integer monthId, Integer yearId,String componentName);

	List<CmnLocationMst> findTreasuryList(OrgUserMst messages);

	List<MstDcpsBillGroup> findBillgroupList(OrgUserMst messages, Integer regStatus);





	
}
