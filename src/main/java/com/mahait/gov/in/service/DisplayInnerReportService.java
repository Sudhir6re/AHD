package com.mahait.gov.in.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.mahait.gov.in.model.DisplayInnerReportModel;

public interface DisplayInnerReportService {
	public List<DisplayInnerReportModel> getAllDataForinnernew(int billNumber,String strddo,int currentPage);
	public List<Map<String,Object>> getempDetails(String bill_no);
	public Page<DisplayInnerReportModel> findPaginated(Pageable pageable,int billNumber,String strddo);
	public Date findbillCreateDate(int billNumber);
	public String getbillDetails(int billNumber);
}
