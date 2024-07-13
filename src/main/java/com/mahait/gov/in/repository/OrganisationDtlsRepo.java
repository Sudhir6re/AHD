package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.DdoOffice;

public interface OrganisationDtlsRepo {
	
	List<Object[]> lstGetOfficeDtls(String userName);

	int saveorgInstInfo(DdoOffice objForSave);

	void updateorgInstituteInfo(DdoOffice objForSave);


}
