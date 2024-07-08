package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.OrgDesignationMst;

public interface EntryOfPostsRepo {

	List<OrgDesignationMst> getActiveDesig(Long lLngFieldDept);

	List getAllBillsFromLocation(Long locId);

}
