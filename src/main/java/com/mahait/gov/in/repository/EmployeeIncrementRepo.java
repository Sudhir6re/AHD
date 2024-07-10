package com.mahait.gov.in.repository;

import java.util.List;


public interface EmployeeIncrementRepo {


	String officeName(String userName);

	List<Object[]> lstEmpforMTR21(String orderNo, int levelRoleVal, String ddoCode);//,String locId

	List<Object[]> getIncrementDataForReptDDO(String userName, String currYear);

}
