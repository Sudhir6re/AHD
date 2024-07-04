package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.model.AnnualIncrementModel;

public interface EmployeeIncrementService {
///	public List<PayCommssionEntity> findAppPayCommssion();
	
	public List<AnnualIncrementModel> getEmpDataForIncrementApproval(String userName);

	///public List<MpgSchemeBillGroupEntity> findAllMpgSchemeBillGroupByDDOCode(String DDOCode,int roleid);
	
	public String officeName(String userName);
	
	public List<AnnualIncrementModel> lstEmpforMTR21(String orderNo, String orderDate, int levelRoleVal,String ddoCode);


}
