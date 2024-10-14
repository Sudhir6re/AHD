package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.DeptEligibilityForAllowAndDeductModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.repository.AllowDeduBulkEmpRepo;

@Transactional
@Service
public class AllowDeduBulkEmpServiceImpl implements AllowDeduBulkEmpService {
	
	@Autowired
	AllowDeduBulkEmpRepo allowDeduBulkEmpRepo;
	

	@Override
	public int updateAllowDeductBulkEmplComp(
			DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel,String ddoCode) {
		// TODO Auto-generated method stub
		return allowDeduBulkEmpRepo.updateAllowDeductBulkEmplComp(deptEligibilityForAllowAndDeductModel,ddoCode);
	}


	@Override
	public void checkComponentAlreadyPresent(
			DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel,String ddoCode) {
		// TODO Auto-generated method stub
		allowDeduBulkEmpRepo.checkComponentAlreadyPresent(deptEligibilityForAllowAndDeductModel,ddoCode);
	}


	@Override
	public List<MstEmployeeModel> getListEmpBySchemBillGroup(String userName, Integer schemeBillGrpId,
			Integer departmentAllowdeducCode) {
		// TODO Auto-generated method stub
		List<Object[]> lstprop = allowDeduBulkEmpRepo.getListEmpBySchemBillGroup(userName,schemeBillGrpId,departmentAllowdeducCode);
		List<MstEmployeeModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstEmployeeModel obj = new MstEmployeeModel();
				obj.setSevaarthId(StringHelperUtils.isNullString(objLst[0]));
				obj.setEmployeeFullName(StringHelperUtils.isNullString(objLst[1]));
				obj.setBillDesc(StringHelperUtils.isNullString(objLst[9]));
				obj.setEmployeeId(StringHelperUtils.isNullLong(objLst[4]));
				obj.setEmpMapped(StringHelperUtils.isNullLong(objLst[10]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}


	@Override
	public List<Object[]> findpaybill(int billNumber, String userName) {
		return allowDeduBulkEmpRepo.findpaybill(billNumber,userName);
	}

}
