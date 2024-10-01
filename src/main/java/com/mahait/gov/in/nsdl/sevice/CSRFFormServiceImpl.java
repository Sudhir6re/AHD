package com.mahait.gov.in.nsdl.sevice;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.EmployeeDetailEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.nsdl.entity.FormS1DetailsEntity;
import com.mahait.gov.in.nsdl.model.CSRFFormModel;
import com.mahait.gov.in.nsdl.repository.CSRFFormRepository;

@Service
@Transactional
public class CSRFFormServiceImpl implements CSRFFormService {

	@Autowired
	private CSRFFormRepository csrfFormRepository;

	@Override
	public List<CSRFFormModel> findAllEmployees(String ddoCode) {
		List<Object[]> lstprop = csrfFormRepository.findAllEmployees(ddoCode);
		List<CSRFFormModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				CSRFFormModel obj = new CSRFFormModel();

				obj.setSevaarthId(StringHelperUtils.isNullString(objLst[0]));
				obj.setEmpName(StringHelperUtils.isNullString(objLst[1]));
				obj.setDOJ(StringHelperUtils.isNullString(objLst[2]));
				obj.setDesignationName(StringHelperUtils.isNullString(objLst[3]));
				obj.setDDOCode(StringHelperUtils.isNullString(objLst[4]));
				obj.setOfficeName(StringHelperUtils.isNullString(objLst[5]));
				obj.setDcpsId(StringHelperUtils.isNullString(objLst[6]));
				obj.setEmpId(StringHelperUtils.isNullBigInteger(objLst[7]).longValue());

				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public FormS1DetailsEntity findEmployeeBySevaarthId(int empId) {
		return csrfFormRepository.findEmployeeBySevaarthId(empId);

	}
	

	@Override
	public MstEmployeeEntity findEmployeeDtlsBySevaarthId(int empId) {
		return csrfFormRepository.findEmployeeDtlsBySevaarthId(empId);
				
			}
	}