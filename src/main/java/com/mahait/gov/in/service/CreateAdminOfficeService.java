package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.model.ZpRltDdoMapModel;

public interface CreateAdminOfficeService {

	List<ZpRltDdoMapModel> findAllDdoMappedlist(OrgUserMst messages);

	List<ZpAdminNameMst> fetchAllOfficeList(OrgUserMst messages);

	List<CmnTalukaMst> findAllTalukaList(OrgUserMst messages);

	List<CmnDistrictMst> findAllDistrictList(OrgUserMst messages);

	List<Object[]> findZpRltDtls(OrgUserMst messages, String districtName, String talukaNametName, String adminType);

	void saveCreateAdminOffice(ZpRltDdoMapModel zpRltDdoMapModel, OrgUserMst messages);

}
