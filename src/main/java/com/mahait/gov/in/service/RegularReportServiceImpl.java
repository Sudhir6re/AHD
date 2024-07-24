package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.repository.RegularReportRepo;

@Service
@Transactional
public class RegularReportServiceImpl implements RegularReportService{
	
	@Autowired
	RegularReportRepo regularReportRepo;

	@Override
	public List<OrgDdoMst> getDDOName(String userName) {
		// TODO Auto-generated method stub
		return regularReportRepo.getDDOName(userName);
	}

	@Override
	public List<RegularReportModel> findentry(Integer yearId, Integer monthId, Long billGroup,String ddoCode) {

		

		List<Object[]> lstprop = regularReportRepo.findentry(yearId,monthId,billGroup,ddoCode);
		List<RegularReportModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				RegularReportModel obj = new RegularReportModel();
				/*obj.setOfficeName(StringHelperUtils.isNullString(objLst[1]));
				String address=objLst[2] + " " + objLst[3];
				obj.setAddress(StringHelperUtils.isNullString(objLst[2]));
				obj.setDdoCode(StringHelperUtils.isNullString(objLst[5]));
				if(objLst[4].equals('0')) {
					obj.setStatus("Pending");
				}*/

				lstObj.add(obj);
			}
		}
		return lstObj;
	
	}

	@Override
	public List<Object[]> findbillgrp(String billno) {
		// TODO Auto-generated method stub
		return regularReportRepo.findbillgrp(billno);
	}

	@Override
	public List<Object[]> findpaybill(int billNumber, int monthName, int yearName, String ddo) {
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

}
