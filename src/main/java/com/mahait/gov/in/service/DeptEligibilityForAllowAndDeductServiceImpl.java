package com.mahait.gov.in.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.model.DeptEligibilityForAllowAndDeductModel;
import com.mahait.gov.in.repository.DeptEligibilityForAllowAndDeductRepo;

@Service
@Transactional
public class DeptEligibilityForAllowAndDeductServiceImpl implements DeptEligibilityForAllowAndDeductService{
	
	@Autowired
	DeptEligibilityForAllowAndDeductRepo deptEligibilityForAllowAndDeductRepo;
	
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptEligibilityForAllowAndDeductList() {
		return deptEligibilityForAllowAndDeductRepo.findDeptEligibilityForAllowAndDeductList();
	}
	
	@Override
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter(int id,String ddoCode)
	{
		List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findMpgSchemeBillGroupBySchemeBillGroupId(id,ddoCode);
		return deptEligibilityForAllowAndDeductEntity;
	}
	@Override
	public void deleteMappedComponent(int ddo_code)
	{
	  deptEligibilityForAllowAndDeductRepo.deleteMappedComponent(ddo_code);
	}
	
	@Override
	public List<Object[]> empEligibilityForAllowAndDeductCheckBoxId(String id)
	{
		List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.empEligibilityForAllowAndDeductCheckBoxId(id);
		return deptEligibilityForAllowAndDeductEntity;
	}
	
	@Override
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter1(int id)
	{
		List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findMpgSchemeBillGroupBySchemeBillGroupId1(id);
		return deptEligibilityForAllowAndDeductEntity;
	}
	
	@Override
	public List<Object[]> findAllMpgSchemeBillGroupbyParameter2(int emp_id,int ddo_code)
	{
		List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findMpgSchemeBillGroupBySchemeBillGroupId2(emp_id,ddo_code);
		return deptEligibilityForAllowAndDeductEntity;
	}

	/*@Override
	public int saveAllowDeductionMst(DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel, UserInfo messages) {
		DeptEligibilityForAllowAndDeductEntity mstDeptEligibilityForAllowAndDeductEntity = new DeptEligibilityForAllowAndDeductEntity();
		
		mstDeptEligibilityForAllowAndDeductEntity.setDepartmentAllowdeducCode(deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducCode());
		mstDeptEligibilityForAllowAndDeductEntity.setIsActive('1');
		mstDeptEligibilityForAllowAndDeductEntity.setIsType(deptEligibilityForAllowAndDeductModel.getIsType());
		mstDeptEligibilityForAllowAndDeductEntity.setDepartmentAllowdeducName(deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducName());
		mstDeptEligibilityForAllowAndDeductEntity.setCreatedUserId(messages.getUser_id());
		mstDeptEligibilityForAllowAndDeductEntity.setCreatedDate(new Date());
		mstDeptEligibilityForAllowAndDeductEntity.setDepartmentAllowdeducColNm(deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducName());
		

		int saveId = deptEligibilityForAllowAndDeductRepo.saveAllowDeductionMst(mstDeptEligibilityForAllowAndDeductEntity);
		
		return saveId;
	}*/
	
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptAllowAndDeductList()
	{
		return deptEligibilityForAllowAndDeductRepo.findDeptAllowAndDeductList();
	}

	public List<Object[]> getEmployeeAgainstId(int allowDeducComponentId, String ddoCode,String sevaarthId) {
		
		return deptEligibilityForAllowAndDeductRepo.getEmployeeAgainstId(allowDeducComponentId,ddoCode,sevaarthId);
	}
	
	@Override
	 public List<Object[]> isPaybillIsInProcess(String sevaarthId){
		return deptEligibilityForAllowAndDeductRepo.isPaybillIsInProcess(sevaarthId);
	}
	 //Added for Non Gov Dueduction
	 @Override
		public List<DeptEligibilityForAllowAndDeductEntity> findDeptNonGovDeductList()
		{
			return deptEligibilityForAllowAndDeductRepo.findDeptNonGovDeductList();
		}
	 //Added for saving non Gov Deduction 	 public  ;
	 
	/*	@SuppressWarnings("unchecked")
		@Override
		public int saveEmployeeNonGovDuesDeduct(EmployeeAllowDeducComponentAmtModel employeeAllowDeducComponentAmtModel, UserInfo messages) {
//			EmployeeAllowDeducComponentAmtEntity[] employeeAllowDeducComponentAmtEntityarr=new EmployeeAllowDeducComponentAmtEntity[]();
			int id = 0;
			EmployeeAllowDeducComponentAmtEntity[] lArrempAllDedCompEntity = new EmployeeAllowDeducComponentAmtEntity[employeeAllowDeducComponentAmtModel.getNetAmt().length];
			
		for (int i = 0; i < employeeAllowDeducComponentAmtModel.getNetAmt().length; i++) {
			if (employeeAllowDeducComponentAmtModel.getNetAmt()[i]>= 0.0) {
				EmployeeAllowDeducComponentAmtEntity empAllDedCompEntity =new EmployeeAllowDeducComponentAmtEntity();
				EmployeeAllowDeducComponentAmtEntity empdata = deptEligibilityForAllowAndDeductRepo.findMstDeptByDeptId(
						employeeAllowDeducComponentAmtModel.getSevaarthId()[i],
						employeeAllowDeducComponentAmtModel.getDeptallowcode());
				if (empdata != null) {
					empdata.setExistingAmt(empdata.getNetAmt());
					empdata.setNetAmt(employeeAllowDeducComponentAmtModel.getNetAmt()[i]);
					empdata.setUpdatedDate(new Date());
					empdata.setUpdatedUserId(messages.getUser_id());
					deptEligibilityForAllowAndDeductRepo.updateComponent(empdata);
					id = 1;
					
				} else {
					empAllDedCompEntity
							.setEmpName(employeeAllowDeducComponentAmtModel.getEmpName()[i]);
					empAllDedCompEntity
							.setEmployeeId(employeeAllowDeducComponentAmtModel.getEmployeeId());
					empAllDedCompEntity
							.setSevaarthId(employeeAllowDeducComponentAmtModel.getSevaarthId()[i]);
					empAllDedCompEntity.setIsType(employeeAllowDeducComponentAmtModel.getIsType());
					empAllDedCompEntity.setNetAmt(employeeAllowDeducComponentAmtModel.getNetAmt()[i]);
					empAllDedCompEntity.setIsActive('1');
					empAllDedCompEntity.setDdoCode(employeeAllowDeducComponentAmtModel.getDdoCode());
					empAllDedCompEntity
							.setDepartmentAllowDeducId(employeeAllowDeducComponentAmtModel.getDepartmentAllowDeducId());
					empAllDedCompEntity
							.setDeptallowcode(employeeAllowDeducComponentAmtModel.getDeptallowcode());
					empAllDedCompEntity.setDeptcode(employeeAllowDeducComponentAmtModel.getDeptcode());
					empAllDedCompEntity.setCreatedDate(new Date());
					empAllDedCompEntity.setCreatedUserId(messages.getUser_id());
					
					lArrempAllDedCompEntity[i]=empAllDedCompEntity;
					
					Serializable  saveEmployeeNonGovDuesDeduct= deptEligibilityForAllowAndDeductRepo.saveEmployeeNonGovDuesDeduct(empAllDedCompEntity);
					id = (int) saveEmployeeNonGovDuesDeduct;
						
				}
			}

		}
			return  id;
		}
*/
		/*@Override
		public int deleteMpgDdoAllowDeduc(int input, int action, String ddo_code) {
			deptEligibilityForAllowAndDeductRepo.deleteMpgDdoAllowDeduc(input,action,ddo_code);
			return 0;
		}*/

		/*@Override
		public int saveMpgDdoAllowDeduc(Object object, int input, int action, Object[] serialid, String effectiveDate,
				String ddo_code) {
			deptEligibilityForAllowAndDeductRepo.saveEmpMpgDdoAllowDeduc(object,input, action,serialid,effectiveDate,ddo_code);
			return 0;
		}*/

		@Override
		public int deleteMpgDdoAllowDeduc( int action, Object object) {
			deptEligibilityForAllowAndDeductRepo.deleteMpgDdoAllowDeduc(action,object);
			return 0;
		}

		@Override
		public int saveMpgDdoAllowDeduc(Object object, int action, Object[] serialid, String effectiveDate,
				Object object2) {
			deptEligibilityForAllowAndDeductRepo.saveEmpMpgDdoAllowDeduc(object,action,serialid,effectiveDate,object2);
			return 0;
		}
		
		@Override
		public List<Object[]> findAllMpgSchemeBillGroupbyParameterDDOWise(String input) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findAllMpgSchemeBillGroupbyParameterDDOWise(input);
			return deptEligibilityForAllowAndDeductEntity;
		}
		
		@Override
		public List<Object[]> findallowDeductLevel2(String ddoCode2) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findallowDeductLevel2(ddoCode2);
			return deptEligibilityForAllowAndDeductEntity;
		}
		
		@Override
		public List<Object[]> findallDDOLevel2AgaintDept(int deptCode) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findallDDOLevel2AgaintDept(deptCode);
			return deptEligibilityForAllowAndDeductEntity;
		}

		@Override
		public List<Object[]> findlevel1DDOAgaintlevel2(String userName) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findlevel1DDOAgaintlevel2(userName);
			return deptEligibilityForAllowAndDeductEntity;
		}

		@Override
		public List<Object[]> getAllowDeductComponentByDDO(String ddoCode) {
			// TODO Auto-generated method stub
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.getAllowDeductComponentByDDO(ddoCode);
			return deptEligibilityForAllowAndDeductEntity;
		}
	
	
}
