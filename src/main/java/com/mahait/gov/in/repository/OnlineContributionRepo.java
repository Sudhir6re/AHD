package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.DcpsContributionEntity;
import com.mahait.gov.in.entity.MstDcpsContriVoucherDtlEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;

public interface OnlineContributionRepo {

	List<CmnLookupMst> getPaymentTypeLst();

	Boolean checkIfBillAlreadyGenerated(Long billGroupId, Integer monthId, Integer finYearId);

	List<Object[]> getEmpListForContribution(DcpContributionModel dcpContributionModel, OrgUserMst messages, String startDate);

	Long saveMstDcpsContriVoucherDtlEntity(MstDcpsContriVoucherDtlEntity mstDcpsContriVoucherDtlEntity);

	void saveDcpsContributionEntity(DcpsContributionEntity dcpsContributionEntity);

	List<Object[]> getSchemeCodeByBillGroupId(String billGroupId);

	MstEmployeeEntity findEmpDtlBySevaarthId(String sevaarthId);

	List<Object[]> findTreasuryList(OrgUserMst messages);

}
