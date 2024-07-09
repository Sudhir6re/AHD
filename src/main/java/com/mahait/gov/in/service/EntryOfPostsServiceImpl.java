package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.HrPayOfficepostMpg;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.repository.EntryOfPostsRepo;


@Transactional
@Service
public class EntryOfPostsServiceImpl implements EntryOfPostsService{
	
	
	
	EntryOfPostsRepo entryOfPostsRepo;

	@Override
	public List<MstDesignationEntity> getActiveDesig(Long lLngFieldDept) {
		return entryOfPostsRepo.getActiveDesig(lLngFieldDept);
	}

	@Override
	public List getAllBillsFromLocation(Long locId) {
		return entryOfPostsRepo.getAllBillsFromLocation(locId);
	}

	@Override
	public List<OrgDdoMst> getDDOCodeByLoggedInlocId(long locId) {
		return entryOfPostsRepo.getDDOCodeByLoggedInlocId(locId);
	}

	@Override
	public List getAllBranchList(long l) {
		// TODO Auto-generated method stub
		return entryOfPostsRepo.getAllBranchList(l) ;
	}

	@Override
	public List<HrPayOrderMst> getAllOrderData(long locId) {
		return entryOfPostsRepo.getAllOrderData(locId) ;
	}

	@Override
	public List getAllOfficesFromDDO(String ddoCode) {
		return entryOfPostsRepo.getAllOfficesFromDDO(ddoCode) ;
	}

	@Override
	public List<Object[]> getSubjectList() {
		return entryOfPostsRepo.getSubjectList() ;
	}

	@Override
	public List getSubOfficesFromDDONew(Long postId) {
		return entryOfPostsRepo.getSubOfficesFromDDONew(postId) ;
	}

	@Override
	public String districtName(String ddoCode) {
		return entryOfPostsRepo.districtName(ddoCode) ;
	}

	@Override
	public List allTaluka(String districtID) {
		return entryOfPostsRepo.allTaluka(districtID) ;
	}

	@Override
	public List getSubDDOsOffc(long loggedInPostId, String talukaId, String ddoSelected) {
		return entryOfPostsRepo.getSubDDOsOffc(loggedInPostId,talukaId,ddoSelected) ;
	}

	@Override
	public Long savePost(OrgPostMst orgPostMst) {
		return entryOfPostsRepo.savePost(orgPostMst) ;
	}

	@Override
	public Long savePostDetails(OrgPostDetailsRlt orgPostDetailsRlt) {
		return entryOfPostsRepo.savePostDetails(orgPostDetailsRlt) ;
	}



	@Override
	public Long saveHrPayOfficepostMpg(HrPayOfficepostMpg hrPayOfficepostMpg) {
		return entryOfPostsRepo.savePostDetails(hrPayOfficepostMpg) ;
	}

}


