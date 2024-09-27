package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.repository.GPFAbstractReportRepo;

@Transactional
@Service
public class GPFAbstractReportServiceImpl implements GPFAbstractReportService{

	@Autowired
	GPFAbstractReportRepo gpfAbstractReportRepo;
	
	@Override
	public List<RegularReportModel> findgpfRptDtls(Integer monthId, Integer yearId, Long billGroup) {
		
		List<Object[]> lst = gpfAbstractReportRepo.findgpfRptDtls(monthId,yearId,billGroup);
		List<RegularReportModel> lstObj= new ArrayList<>();
		for (Object[] objects : lst) {
			RegularReportModel obj = new RegularReportModel();
			obj.setPfSeries(StringHelperUtils.isNullString(objects[0]));
			obj.setCountGPFEmp(StringHelperUtils.isNullBigInteger(objects[1]));
			obj.setDp(StringHelperUtils.isNullDouble(objects[2]));
			obj.setSubsAmt(StringHelperUtils.isNullDouble(objects[3]));
			obj.setPayDaArrMrg(StringHelperUtils.isNullDouble(objects[4]));
			Integer refund = (Integer) objects[5];
			obj.setRefundAmt(refund.doubleValue());
			
			lstObj.add(obj);
			
		}
		return lstObj;
	}

}
