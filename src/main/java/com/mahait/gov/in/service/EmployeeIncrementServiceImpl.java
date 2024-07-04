package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.AnnualIncrementModel;
import com.mahait.gov.in.repository.EmployeeIncrementRepo;

@Service
@Transactional
public class EmployeeIncrementServiceImpl implements EmployeeIncrementService{

	
	@Autowired
	EmployeeIncrementRepo employeeIncrementRepo;
	

	@Override
	public List<AnnualIncrementModel> getEmpDataForIncrementApproval(String userName) {
		List<Object[]> lstprop = employeeIncrementRepo.getEmpDataForIncrementApproval(userName);
		List<AnnualIncrementModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				AnnualIncrementModel obj = new AnnualIncrementModel();

				obj.setIncrementOrderDate(StringHelperUtils.isNullDate(objLst[0]));
				obj.setOrderNo(StringHelperUtils.isNullString(objLst[1].toString()));
				obj.setOfficeName(StringHelperUtils.isNullString(objLst[2]));
				obj.setDdoCode(StringHelperUtils.isNullString(objLst[3]));
				// obj.setBasicPayIncrementId(StringHelperUtils.isNullInt(objLst[4]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}



	@Override
	public String officeName(String userName) {
		return employeeIncrementRepo.officeName(userName);
	}

	@Override
	public List<AnnualIncrementModel> lstEmpforMTR21(String orderNo, String orderDate, int levelRoleVal,
			String ddoCode) {
		List<Object[]> lstprop = employeeIncrementRepo.lstEmpforMTR21(orderNo,orderDate,levelRoleVal,ddoCode);
		List<AnnualIncrementModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				AnnualIncrementModel obj = new AnnualIncrementModel();
				//b.employee_full_name_en,c.post_short_name,a.current_basic_pay,b.sevaarth_id,a.effective_from_date,\r\n" + 
				//"a.to_increment_date

				obj.setEmpname(StringHelperUtils.isNullString(objLst[0]));
				obj.setPostName(StringHelperUtils.isNullString(objLst[1]));
				obj.setCurrbasic(StringHelperUtils.isNullInt(objLst[2]));
				obj.setIncrDate(StringHelperUtils.isNullDate(objLst[4]));
				obj.setIncrToDate(StringHelperUtils.isNullDate(objLst[5]));
				obj.setIncrBasic(StringHelperUtils.isNullInt(objLst[6]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}

}
