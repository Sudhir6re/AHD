package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.OrgPostMst;

public interface DDOSchemeRepo {

	List<Object[]> findAllEmployee(String userName);

	String getDdoCodeForDDO(OrgPostMst createdByPost);

	List<Object[]> getDDOCodeByLoggedInlocId(int i);

	List getAllSchemesForDDO(String lStrDDOCode);

	String districtName(String ddoCode);

	List allTaluka(String districtID);

	List getSubDDOs(Long locId, String talukaId, String ddoSelected);

	List getpostRole(OrgPostMst createdByPost);

}
