package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.repository.EmpBasicDtlsReportRepo;

@Transactional
@Service

public class EmpBasicDtlsReportServiceImpl implements EmpBasicDtlsReportService{

	@Autowired
	EmpBasicDtlsReportRepo empBasicDtlsReportRepo;
	
	@Override
	public List<MstEmployeeEntity> lstDDOWiseEmp(String ddoCode) {
		// TODO Auto-generated method stub
		return empBasicDtlsReportRepo.lstDDOWiseEmp(ddoCode);
	}

	@Override
	public List<RegularReportModel> findEmpBasicDtls(Integer yearId, Integer monthId, Long billGroup, String ddoCode) {
		
		List<Object[]> lstprop = empBasicDtlsReportRepo.findEmpBasicDtls(yearId,monthId,billGroup,ddoCode);
		List<RegularReportModel> lstObj = new ArrayList<>();
		
		Double sum=0d;
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				RegularReportModel obj = new RegularReportModel();
				obj.setName(StringHelperUtils.isNullString(objLst[0]));
				obj.setPran(StringHelperUtils.isNullString(objLst[1]));
				obj.setBasicpay(StringHelperUtils.isNullDouble(objLst[2]));
				obj.setDp(StringHelperUtils.isNullDouble(objLst[3]));
				obj.setSvnpcda(StringHelperUtils.isNullDouble(objLst[4]));
				obj.setDcpsReg(StringHelperUtils.isNullDouble(objLst[5]));
				obj.setNpsEmployerDedu(StringHelperUtils.isNullDouble(objLst[6]));
				
				lstObj.add(obj);
			}
		}
		return lstObj;
	
	}

}
