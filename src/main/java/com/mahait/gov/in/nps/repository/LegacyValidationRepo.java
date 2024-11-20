package com.mahait.gov.in.nps.repository;

import java.util.List;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;

public interface LegacyValidationRepo {

	List<Object[]> findNsdlLegacyList(DcpsLegacyModel dcpsLegacyModel, OrgUserMst messages, Long locId);

}
