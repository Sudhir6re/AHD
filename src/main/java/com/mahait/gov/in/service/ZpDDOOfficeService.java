package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpRltDdoMap;
import com.mahait.gov.in.model.EmpWiseCityClassModel;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.OrgDdoMst;

public interface ZpDDOOfficeService {

	List<ZpRltDdoMap> getAllDDOOfficeDtlsDataByPostID(String string);

	NewRegDDOModel getDDOinfo(String zpDdoCode);

	String getOfficeName(String zpDdoCode);

	OrgUserMst approveddoDtls(String zpDdoCode, int flag);


	

	
}
