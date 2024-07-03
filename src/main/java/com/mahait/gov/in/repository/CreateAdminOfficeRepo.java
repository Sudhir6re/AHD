package com.mahait.gov.in.repository;

import java.util.List;

public interface CreateAdminOfficeRepo {

	List<Object[]> getAllDDOOfficeDtlsData(String districtName, String talukaNametName, String adminType);

}
