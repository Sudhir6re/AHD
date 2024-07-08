package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgDesignationMst;
import com.mahait.gov.in.repository.EntryOfPostsRepo;


@Transactional
@Service
public class EntryOfPostsServiceImpl implements EntryOfPostsService{
	
	
	
	EntryOfPostsRepo entryOfPostsRepo;

	@Override
	public List<OrgDesignationMst> getActiveDesig(Long lLngFieldDept) {
		return entryOfPostsRepo.getActiveDesig(lLngFieldDept);
	}

	@Override
	public List getAllBillsFromLocation(Long locId) {
		return entryOfPostsRepo.getAllBillsFromLocation(locId);
	}

}
