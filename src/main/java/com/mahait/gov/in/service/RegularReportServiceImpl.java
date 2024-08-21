package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.repository.CommonHomeMethodsRepo;
import com.mahait.gov.in.repository.RegularReportRepo;

@Service
@Transactional
public class RegularReportServiceImpl implements RegularReportService{
	
	@Autowired
	RegularReportRepo regularReportRepo;
	
	@Autowired
	CommonHomeMethodsRepo commonHomeMethodsRepo;

	@Override
	public List<OrgDdoMst> getDDOName(String userName) {
		// TODO Auto-generated method stub
		return regularReportRepo.getDDOName(userName);
	}

	@Override
	public List<RegularReportModel> findDCPSRegularEmpLst(Integer yearId, Integer monthId, Long billGroup,String ddoCode) {

		

		List<Object[]> lstprop = regularReportRepo.findDCPSRegularEmpLst(yearId,monthId,billGroup,ddoCode);
		List<RegularReportModel> lstObj = new ArrayList<>();
		
		Double sum=0d;
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				RegularReportModel obj = new RegularReportModel();
				/*a.employee_full_name_en,a.pran_no,b.basic_pay,b.dearness_pay,case when a.pay_commission_code =700005 then " + 
						"b.svn_pc_da else da end as DA, b.dcps_regular,nps_empr_deduct,c.created_date
*/				
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

	@Override
	public List<Object[]> findbillgrp(Long billno) {
		// TODO Auto-generated method stub
		return regularReportRepo.findbillgrp(billno);
	}

	@Override
	public List<Object[]> findpaybill(Long billNumber, int monthName, int yearName, String ddo) {
		// TODO Auto-generated method stub
		return regularReportRepo.findpaybill(billNumber,monthName,yearName,ddo);
	}

	@Override
	public String getbillGroup(int billnumber) {
		// TODO Auto-generated method stub
		return regularReportRepo.getbillGroup(billnumber);
	}

	@Override
	public List<Object[]> checktheEntryForForm2Regular(int billNumber, int monthName, int yearName, String userName) {
		// TODO Auto-generated method stub
		return regularReportRepo.checktheEntryForForm2Regular(billNumber,monthName,yearName,userName);
	}

	@Override
	public List<MstDcpsBillGroup> lstBillDesc(String ddoCode) {
		// TODO Auto-generated method stub
		return regularReportRepo.lstBillDesc(ddoCode);
	}

	@Override
	public List<Object[]> findTrsyDtls(String ddoCode) {
		// TODO Auto-generated method stub
		return regularReportRepo.findTrsyDtls(ddoCode);
	}

}
