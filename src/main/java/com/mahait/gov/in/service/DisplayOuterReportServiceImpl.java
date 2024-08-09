package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.DisplayOuterReportModel;
import com.mahait.gov.in.repository.DisplayOuterReportRepo;

@Service
@Transactional
public class DisplayOuterReportServiceImpl implements DisplayOuterReportService {
	@Autowired
	DisplayOuterReportRepo 	displayOuterReportRepo;
	public String getOffice(String locId) {
		return displayOuterReportRepo.getOffice(locId);
	}
	
	public List getReportHeaderDetails(String bill_no) {
		return displayOuterReportRepo.getReportHeaderDetails(bill_no);
	}
	public List<DisplayOuterReportModel> findBillDescription(String ddoCode,int month,int year){
		return displayOuterReportRepo.findBillDescription(ddoCode,month,year);
	}
	public int getTotalDeduction(double billno) {
		return displayOuterReportRepo.getTotalDeduction(billno);
	}
	public List<DisplayOuterReportModel> getAllDataForOuternew(String ddocode,Long billNumber){
		
		List<Object[]> lstprop = displayOuterReportRepo.getAllDataForOuternew(ddocode,billNumber);
		
		List<DisplayOuterReportModel> lstObj = new ArrayList<>();
        if (!lstprop.isEmpty()) {
        	int i=1;
            for (Object[] objLst : lstprop) {
            	DisplayOuterReportModel obj = new DisplayOuterReportModel();
            	obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
                obj.setType(StringHelperUtils.isNullInt(objLst[1]));
                obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
                obj.setHeadAccountCode(StringHelperUtils.isNullString(objLst[3]));
                lstObj.add(obj);
                i++;
            }
        }
		
		return lstObj;
	}
	public List<Map<String,Object>> getempDetails(String bill_no){
		return displayOuterReportRepo.getempDetails(bill_no);
	}

	@Override
	public List<Object[]> findbillcrateornot(int monthName, int yearName, String ddocode, String billnumber) {
		return displayOuterReportRepo.getempDetails(monthName,yearName,ddocode,billnumber);
	}

	@Override
	public List<DisplayOuterReportModel> findAllSchemeBillGroupByDDOCode(String DDOCode, int roleid) {
		return displayOuterReportRepo.findAllSchemeBillGroupByDDOCode(DDOCode,roleid);
	}

	@Override
	public List<Object[]> getcardecode(String strddo) {
		List<Object[]> obj = displayOuterReportRepo.getcardecode(strddo);
		return obj;
	}

	@Override
	public String getbillDetails(Long billDetails) {
		return displayOuterReportRepo.getbillDetails(billDetails);
	}
}

