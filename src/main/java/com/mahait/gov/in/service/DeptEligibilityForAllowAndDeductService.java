package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.model.DeptEligibilityForAllowAndDeductModel;

public interface DeptEligibilityForAllowAndDeductService {

	public List<DeptEligibilityForAllowAndDeductEntity> findDeptEligibilityForAllowAndDeductList();
	
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter(int id, String ddoCode);
	public List<Object[]> empEligibilityForAllowAndDeductCheckBoxId(String id);
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter1(int id);
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter2(int emp_id,int ddo_code);
	

	///public int saveAllowDeductionMst(DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel, UserInfo messages);
	
	
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptAllowAndDeductList();
	
	
	public List<Object[]> getEmployeeAgainstId(int allowDeducComponentId,String ddoCode,String sevaarthId);
	
	 //check is paybill in process
	 public List<Object[]> isPaybillIsInProcess(String sevaarthId);
	 
	 public void deleteMappedComponent(int ddo_code);
	 
	 //Added for Non Gov Deduction
	 
	 
	 public List<DeptEligibilityForAllowAndDeductEntity> findDeptNonGovDeductList();
	 //Added for saving non gov deductions
	 
	 ///public int saveEmployeeNonGovDuesDeduct(EmployeeAllowDeducComponentAmtModel employeeAllowDeducComponentAmtModel, UserInfo messages);
	 
	/* public int deleteMpgDdoAllowDeduc(int input,int action, String ddo_code);*/
	 
	 public int deleteMpgDdoAllowDeduc(int action, Object object);

	/*public int  saveMpgDdoAllowDeduc(Object object, int input, int action, Object[] serialid, String effectiveDate,
			String ddo_code);*/
	 
	 public int  saveMpgDdoAllowDeduc(Object object,int action, Object[] serialid, String effectiveDate,
				Object object2);

	public List<Object[]> findAllMpgSchemeBillGroupbyParameterDDOWise(String input);
	
	public List<Object[]> findallowDeductLevel2(String ddoCode2);

	public List<Object[]> findallDDOLevel2AgaintDept(int deptCode);

	public List<Object[]> findlevel1DDOAgaintlevel2(String userName);

	public List<Object[]> getAllowDeductComponentByDDO(String ddoCode);

	


	
	 
}
