package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.model.EmpWiseCityClassModel;
import com.mahait.gov.in.model.OrgDdoMst;

public interface DDOSchemeService {

	List<EmpWiseCityClassModel> findAllEmployee(String userName);

	String getDdoCodeForDDO(OrgPostMst createdByPost);

	List<OrgDdoMst> getDDOCodeByLoggedInlocId(long l);

	List getAllSchemesForDDO(String lStrDDOCode);

	String districtName(String ddoCode);

	List allTaluka(String districtID);
	
	public List getSubDDOs(Long locId, String talukaId, String ddoSelected);

	List getpostRole(OrgPostMst createdByPost);


	
}
