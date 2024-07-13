package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.model.OrganisationDtlsModel;

public interface OrganisationDtlsService {
	
	List<OrganisationDtlsModel> lstGetOfficeDtls(String userName);

	int SaveorgInstituteInfo(@Valid OrganisationDtlsModel organisationDtlsModel);


}
