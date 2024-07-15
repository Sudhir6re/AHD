package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.mahait.gov.in.entity.MstEmployeeEntity;
/*import com.mahait.gov.in.entity.MstEmployeeEntity;*/
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.model.PaybillHeadMpgModel;

public interface PaybillGenerationTrnService {
	
	public Long savePaybillHeadMpg(PaybillHeadMpgModel paybillHeadMpgModel);
	
	public Long saveSupPaybillHeadMpg(PaybillHeadMpgModel paybillHeadMpgModel);
	
	public PaybillGenerationTrnEntity findForwardChangeStatementById(Long paybillGenerationTrnId, Long userId, String namePIp);
	
	public PaybillGenerationTrnEntity findApproveChangeStatementById(Long paybillGenerationTrnId, Long userId, String namePIp);
	
	public PaybillGenerationTrnEntity findRejectChangeStatementById(Long paybillGenerationTrnId, Long userId, String namePIp);
	
	public PaybillGenerationTrnEntity findDeleteBillById(Long paybillGenerationTrnId, Long userId, String namePIp);	
	
	public PaybillGenerationTrnEntity findForwardBillById(Long paybillGenerationTrnId, Long userId, String namePIp);

	public PaybillGenerationTrnEntity forwardPayBillToLevel2(Long paybillGenerationTrnId, Long userId, String namePIp);

	public PaybillGenerationTrnEntity generatePaybill(Long paybillGenerationTrnId,Long userId, String namePIp);

	public  int  getpaybilldata(BigInteger billGroup, int paybillMonth, int paybillYear);
	
	public List<Object[]> findpaybill(BigInteger billGroup, int paybillMonth, int paybillYear,String ddo);


	public List<Object[]> getChangeStatementReport(String paybillGenerationTrnId);
	
	public List<Object[]> getChangeStatementReportFromPreviousMonth(String paybillGenerationTrnId);
	
	public PaybillGenerationTrnEntity consolidatedPaybill(Long paybillGenerationTrnId, Long userId, String namePIp);

	public List<Object[]>  findDDOinfo(String userName);

	public List<Object[]> findregIdinfo(Long regid);

	public List<Object[]> findmonthinfo(BigInteger currmonth);

	public List<Object[]> findofcIdinfo(Long ofcid);

	public List<Object[]> getAbstractReport(String paybillGenerationTrnId);

	public List<Object[]> findyearinfo(BigInteger yearcurr);

	public List<Object[]> getViewDetialsReport(Integer monthName);
	public Object[] getSevaarthIdMappedWithBill(String ddocode,int noofemp,BigInteger schemeBillGroupId,int month,int year ) ;

	public Integer isPaybillExists(BigInteger schemeBillgroupId, int paybillMonth, int paybillYear);

	public int getCheckIsBillInProcess(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType);

	public String isEmpRetired(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType, String userName);

	public PaybillGenerationTrnEntity findPaybillById(Long paybillGenerationTrnId, String voucherNo, Date voucherDate,String chqNo,Date chequeDate,String accNo,String ifscCode,Long userId);

	public List<MstEmployeeEntity> checkedBgisAndGisCatNull(int schemeBillGroupId, String userName);

	
}
