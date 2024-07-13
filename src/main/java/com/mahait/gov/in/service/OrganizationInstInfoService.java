package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.model.OrgDdoMstModel;


public interface OrganizationInstInfoService {
	

	public OrgDdoMstModel findDDOInfo(String userName);

	public List<InstituteType> lstInstType();

	public List<Object[]> getBankBranch(String valueOf);

	public int SaveorgInstituteInfo(@Valid OrgDdoMstModel orgDdoMstModel);

	public int updateorgInstituteInfo(OrgDdoMstModel orgDdoMstModel);
	 
}
