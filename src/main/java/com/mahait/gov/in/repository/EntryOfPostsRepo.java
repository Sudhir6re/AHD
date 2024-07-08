package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgDesignationMst;

public interface EntryOfPostsRepo {

	List<OrgDesignationMst> getActiveDesig(Long lLngFieldDept);

	List getAllBillsFromLocation(Long locId);

	List<OrgDdoMst> getDDOCodeByLoggedInlocId(long locId);

	List getAllBranchList(long l);

	List<HrPayOrderMst> getAllOrderData(long locId);

	List getAllOfficesFromDDO(String ddoCode);

	List getSubjectList();

	List getSubOfficesFromDDONew(Long postId);

	String districtName(String ddoCode);

	List allTaluka(String districtID);

	List getSubDDOsOffc(long loggedInPostId, String talukaId, String ddoSelected);

}
