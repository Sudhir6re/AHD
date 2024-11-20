package com.mahait.gov.in.nps.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;

public interface LegacyValidationService {

	List<DcpsLegacyModel> findNsdlLegacyList(DcpsLegacyModel dcpsLegacyModel, OrgUserMst messages);

}
