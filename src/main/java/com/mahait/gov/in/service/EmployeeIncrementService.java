package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.model.AnnualIncrementModel;

public interface EmployeeIncrementService {
///	public List<PayCommssionEntity> findAppPayCommssion();
	
	
	public String officeName(String userName);
	
	public List<AnnualIncrementModel> lstEmpforMTR21(String orderNo,int roleId, String ddoCode);//, String locId

	public List<AnnualIncrementModel> getIncrementDataForReptDDO(String userName, String currYear);


}
