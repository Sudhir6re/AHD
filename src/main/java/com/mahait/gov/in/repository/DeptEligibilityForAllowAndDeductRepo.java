package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;

public interface DeptEligibilityForAllowAndDeductRepo {

	public List<DeptEligibilityForAllowAndDeductEntity> findDeptEligibilityForAllowAndDeductList();
	
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId(int id, String ddoCode);
	public List<Object[]> empEligibilityForAllowAndDeductCheckBoxId(String id);
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId1(int id);
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId2(int emp_id,int ddo_code);
	
	public int saveAllowDeductionMst(DeptEligibilityForAllowAndDeductEntity mstDeptEligibilityForAllowAndDeductEntity);
	
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptAllowAndDeductList();
	
	public List<Object[]> getEmployeeAgainstId(int allowDeducComponentId,String ddoCode,String sevaarthId);
	
	public List<Object[]> isPaybillIsInProcess(String sevaarthId);
	
	//Added for Non Gov deduction
	
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptNonGovDeductList();
	
	//For saving Non gov deduct
	
/*	public int saveEmployeeNonGovDuesDeduct(EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity);
	
	public EmployeeAllowDeducComponentAmtEntity findMstDeptByDeptId(String sevaarthId,int deptCode);
	
	public void updateComponent(EmployeeAllowDeducComponentAmtEntity updateComponent);*/
	
	public void deleteMappedComponent(int ddo_code);

	/*public int deleteMpgDdoAllowDeduc(int input, int action, String ddo_code);*/
	
	public int deleteMpgDdoAllowDeduc(int action, Object object);

	/*public int saveEmpMpgDdoAllowDeduc(Object object, int input, int action, Object[] serialid, String effectiveDate,
			String ddo_code);*/
	
	public int saveEmpMpgDdoAllowDeduc(Object object, int action, Object[] serialid, String effectiveDate,
			Object object2);

	public List<Object[]> findAllMpgSchemeBillGroupbyParameterDDOWise(String input);
	
	public List<Object[]> findallowDeductLevel2(String ddoCode2);

	public List<Object[]> findallDDOLevel2AgaintDept(int deptCode);

	public List<Object[]> findlevel1DDOAgaintlevel2(String userName);

	public List<Object[]> getAllowDeductComponentByDDO(String ddoCode);

	


	
	/*public int saveEmployeeAllowDeducComponentAmt(EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity);*/
}
