package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.model.ZpRltDdoMapModel;

public interface CreateAdminOfficeService {

	List<ZpRltDdoMapModel> findAllDdoMappedlist(OrgUserMst messages);

	List<ZpAdminNameMst> fetchAllOfficeList(OrgUserMst messages);

}
