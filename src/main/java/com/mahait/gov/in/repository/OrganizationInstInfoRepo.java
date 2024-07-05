package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.OrgDdoMst;

public interface OrganizationInstInfoRepo {


	public List<OrgDdoMst> findDDOInfo(String userName);

	public List<InstituteType> lstInstType();

	public List<Object[]> getBankBranch(String valueOf);
	
	

}
