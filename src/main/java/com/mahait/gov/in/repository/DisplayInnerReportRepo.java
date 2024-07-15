package com.mahait.gov.in.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mahait.gov.in.model.DisplayInnerReportModel;

public  interface DisplayInnerReportRepo {
	public List<DisplayInnerReportModel> getAllDataForinnernew(String strddo,int billNumber);
	public List<Map<String,Object>> getempDetails(String bill_no);
	public Date findbillCreateDate(int billNumber);
	public String getLoanDtls(String empId,Long mon,int year);
	public String getDcpsGpfNoDtls(String sevaarthid);
	public String getGpfNoDtls(String sevaarthid);
	public String getfaLoanDtls(String sevaarthid, int billNumber);
	public String getcaLoanDtls(String sevaarthid, int billNumber);
	public String getvaLoanDtls(String sevaarthid, int billNumber);
	public String gethbaLoanDtls(String sevaarthid, int billNumber);
	public String getgpfLoanDtls(String sevaarthid, int billNumber);
	public String getgpfIILoanDtls(String sevaarthid, int billNumber);
	public String getbillDetails(int billNumber);
	public String getPayFixDiffLoanDtls(String string, int billNumber);
	public String gethbaLoanIntsDtls(String string, int billNumber);
	public String getexPayRecDtls(String string, int billNumber);
}
