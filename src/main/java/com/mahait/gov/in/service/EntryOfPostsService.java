package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgDesignationMst;

public interface EntryOfPostsService {

	List<OrgDesignationMst> getActiveDesig(Long lLngFieldDept);

	List getAllBillsFromLocation(Long locId);

}
