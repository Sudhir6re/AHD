package com.mahait.gov.in.repository;

import java.util.List;


public interface EmployeeIncrementRepo {


	///List<PayCommssionEntity> findAppPayCommssion();

	List<Object[]> getEmpDataForIncrementApproval(String userName);

///	List<MpgSchemeBillGroupEntity> findMpgSchemeBillGroupByDDOCode(String dDOCode, int roleid);

	String officeName(String userName);

	List<Object[]> lstEmpforMTR21(String orderNo, String orderDate, int levelRoleVal, String ddoCode);

}
