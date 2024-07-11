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
		public List<Object[]> findallowDeductLevel2(String ddoCode2) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findallowDeductLevel2(ddoCode2);
			return deptEligibilityForAllowAndDeductEntity;
		}
		
		@Override
		public List<Object[]> findlevel1DDOAgaintlevel2(String userName) {
			List<Object[]> deptEligibilityForAllowAndDeductEntity = deptEligibilityForAllowAndDeductRepo.findlevel1DDOAgaintlevel2(userName);
			return deptEligibilityForAllowAndDeductEntity;
		}

}
