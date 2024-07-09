package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.HrPayOfficepostMpg;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;

public interface EntryOfPostsService {

	List<MstDesignationEntity> getActiveDesig(Long lLngFieldDept);

	List getAllBillsFromLocation(Long locId);

	List<OrgDdoMst> getDDOCodeByLoggedInlocId(long locId);

	List getAllBranchList(long l);

	List<HrPayOrderMst> getAllOrderData(long locId);

	List getAllOfficesFromDDO(String ddoCode);

	List<Object[]> getSubjectList();

	List getSubOfficesFromDDONew(Long postId);

	String districtName(String ddoCode);

	List allTaluka(String districtID);

	List getSubDDOsOffc(long loggedInPostId, String talukaId, String ddoSelected);

	Long savePost(OrgPostMst orgPostMst);

	Long savePostDetails(OrgPostDetailsRlt orgPostDetailsRlt);

	Long saveHrPayOfficepostMpg(HrPayOfficepostMpg hrPayOfficepostMpg);

}
