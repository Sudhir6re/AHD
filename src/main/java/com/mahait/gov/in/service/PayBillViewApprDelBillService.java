package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.model.LstGenerateBillDetailsModel;
import com.mahait.gov.in.model.PaybillViewApproveDeleteModel;

public interface PayBillViewApprDelBillService {

	public List<LstGenerateBillDetailsModel> findAlllstGenerateBillDetailsAgainstDDO(String ddoCode,int roleId,int month);

	public List<LstGenerateBillDetailsModel> findAllPayBillsForConsolidatedAgainstDDO(String userName,int roleId);
	
	public List<PaybillViewApproveDeleteModel> findPayBillByBillNumber(String billGroup, int paybillMonth, int paybillYear, int ddoCode);
	
	public  List<PaybillViewApproveDeleteModel> findPayBillByMonthYear(int paybillMonth, int paybillYear, String ddoCode,int roleId);

	
}
