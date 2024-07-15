package com.mahait.gov.in.service;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.model.OrganisationDtlsModel;

public interface OrganisationDtlsService {
	

	Long SaveorgInstituteInfo(@Valid OrganisationDtlsModel organisationDtlsModel, OrgUserMst messages);

	@Valid OrganisationDtlsModel lstOfficeDetails(String ddoCode);

	Map<String, Object> findDataByDistrict(String districtId);

	int updateddoOfficeDetails(OrganisationDtlsModel organisationDtlsModel,OrgUserMst messages);


}
