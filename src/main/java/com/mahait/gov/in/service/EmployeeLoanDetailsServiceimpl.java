package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.annotations.common.util.StringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.model.EmpLoanModel;
import com.mahait.gov.in.repository.EmployeeLoanDetailsRepo;
import com.mahait.gov.in.common.StringHelperUtils;

@Service
@Transactional
public class EmployeeLoanDetailsServiceimpl implements EmployeeLoanDetailsService {

	@Autowired
	EmployeeLoanDetailsRepo employeeLoanDetailsRepo;

//	@Override
//	public int saveEmployeeLoanDtls(EmpLoanModel empLoanModel) {
//		// TODO Auto-generated method stub
//		LoanEmployeeDtlsEntity loanEmployeeDtlsEntity = null;
//		if (empLoanModel.getLoanEmpAdvId() != null) {
//			loanEmployeeDtlsEntity = employeeLoanDetailsRepo.findLoanDetailsById(empLoanModel.getLoanEmpAdvId());
//		} else {
//			loanEmployeeDtlsEntity = new LoanEmployeeDtlsEntity();
//		}
//
//		String arrLoanDtl[] = empLoanModel.getLoanAdvName().split("-");
//		String str = arrLoanDtl[0];
//		BigInteger loantypeid = new BigInteger(str);
//
//		loanEmployeeDtlsEntity.setLoantypeid(loantypeid);
//		loanEmployeeDtlsEntity.setDepartmentallowdeduccode(Integer.parseInt(arrLoanDtl[1]));
//		loanEmployeeDtlsEntity.setCreateddate(new Date());
//		loanEmployeeDtlsEntity.setLoanactivateflag(empLoanModel.getLoanStatus());
//		loanEmployeeDtlsEntity.setEmployeeid(BigInteger.valueOf(empLoanModel.getEmployeeid().longValue()));
//		loanEmployeeDtlsEntity.setLoanprinamt(empLoanModel.getLoanprinamt());
//		loanEmployeeDtlsEntity.setLoantypeid(empLoanModel.getLoantypeid());
//		loanEmployeeDtlsEntity.setLoaninterestamt(empLoanModel.getLoaninterestamt());
//		loanEmployeeDtlsEntity.setLoanaccountno(empLoanModel.getLoanaccountno());
//		loanEmployeeDtlsEntity.setLoanprininstno(empLoanModel.getLoanprininstno());
//		loanEmployeeDtlsEntity.setLoanintinstno(empLoanModel.getLoanintinstno());
//		loanEmployeeDtlsEntity.setLoanemiamt(empLoanModel.getLoanemiamt());
//		loanEmployeeDtlsEntity.setLoansancorderno(empLoanModel.getLoansancorderno());
//		loanEmployeeDtlsEntity.setVoucherno(empLoanModel.getVoucherno());
//		loanEmployeeDtlsEntity.setOddinstamt(empLoanModel.getOddinstamt());
//		loanEmployeeDtlsEntity.setLoanintemiamt(empLoanModel.getLoanintemiamt());
//		loanEmployeeDtlsEntity.setLoanprinemiamt(empLoanModel.getLoanprinemiamt());
//		loanEmployeeDtlsEntity.setCreatedby(BigInteger.valueOf(1l));
//		loanEmployeeDtlsEntity.setCreatedbypost(BigInteger.valueOf(1l));
//		loanEmployeeDtlsEntity.setSevaarthid(empLoanModel.getSevaarthId());
//		loanEmployeeDtlsEntity.setLoanaccountno(empLoanModel.getAppNo());
//		// loanEmployeeDtlsEntity.setLoanactivateflag(loanactivateflag);
//
//		String sDate1 = empLoanModel.getLoanDate();
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");///yyyy-MM-dd
//		Date loanDate = null;
//
//		int saveId = 0;
//
//		if (empLoanModel.getLoanEmpAdvId() != null) {
//			employeeLoanDetailsRepo.updateLoanDetail(loanEmployeeDtlsEntity);
//			saveId = 1;
//		} else {
//			saveId = employeeLoanDetailsRepo.saveEmployeeLoanDtls(loanEmployeeDtlsEntity);
//		}
//
//		return saveId;
//	}
//
//	@Override
//	public List<EmpLoanModel> getEmpInfoBySevaarthId(String sevaarthId) {
//		// TODO Auto-generated method stub
//		List<EmpLoanModel> listEmpLoanModel = new ArrayList<>();
//		List<Object[]> lst = employeeLoanDetailsRepo.getEmpInfoBySevaarthId(sevaarthId);
//		for (Object[] objects : lst) {
//			EmpLoanModel empLoanModel = new EmpLoanModel();
//			empLoanModel.setSevaarthId(StringHelperUtils.isNullString(objects[0]));
//			empLoanModel.setEmployeeid(StringHelperUtils.isNullInt(objects[2]));
//			empLoanModel.setDesignName(StringHelperUtils.isNullString(objects[3]));
//			empLoanModel.setEmployeeName(StringHelperUtils.isNullString(objects[4]));
//			empLoanModel.setGpfNo(StringHelperUtils.isNullString(objects[5]));
//			empLoanModel.setOrgInstName(StringHelperUtils.isNullString(objects[7]));
//			listEmpLoanModel.add(empLoanModel);
//		}
//		return listEmpLoanModel;
//	}

	@Override
	public List<EmpLoanModel> findAllEmpLoanDtls(String ddoCode) {
		// TODO Auto-generated method stub
		List<Object[]> lst = employeeLoanDetailsRepo.findAllEmpLoanDtls(ddoCode);
		List<EmpLoanModel> listEmpLoanModel = new ArrayList<>();
		for (Object[] object : lst) {
			EmpLoanModel empLoanModel = new EmpLoanModel();
			
			empLoanModel.setEmployeeName(StringHelperUtils.isNullString(object[0]));
			empLoanModel.setPfDesc(StringHelperUtils.isNullString(object[1]));
			empLoanModel.setLoanEmpAdvId(StringHelperUtils.isNullLong(object[2]));
			empLoanModel.setEmployeeid(StringHelperUtils.isNullLong(object[3]));
			empLoanModel.setSevaarthId(StringHelperUtils.isNullString(object[4]));
			empLoanModel.setLoantypeid(StringHelperUtils.isNullLong(object[6]));
			empLoanModel.setLoanprinamt(StringHelperUtils.isNullLong(object[7]));
			empLoanModel.setLoaninterestamt(StringHelperUtils.isNullLong(object[8]));
			empLoanModel.setLoanprininstno(StringHelperUtils.isNullLong(object[9]));
			empLoanModel.setLoanintinstno(StringHelperUtils.isNullLong(object[10]));
			empLoanModel.setLoanemiamt(StringHelperUtils.isNullLong(object[11]));
			empLoanModel.setLoanaccountno(StringHelperUtils.isNullString(object[12]));
			
			Date date = (Date) object[13];
			Date date1 = (Date) object[23];
			Date date2 = (Date) object[24];
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");///yyyy-MM-dd
			String loanDate = sdf.format(date);  
			String voucherDate =null;
			if(date1!=null)
			 voucherDate = sdf.format(date1);  
			String sancOrderDate = sdf.format(date2);  
			
			empLoanModel.setLoanDate(loanDate);
			empLoanModel.setLoanintemiamt(StringHelperUtils.isNullLong(object[15]));
			empLoanModel.setLoanprinemiamt(StringHelperUtils.isNullLong(object[16]));
			empLoanModel.setLoansancorderno(StringHelperUtils.isNullString(object[17]));
			empLoanModel.setOddinstno(StringHelperUtils.isNullLong(object[19]));
			empLoanModel.setOddinstamt(StringHelperUtils.isNullLong(object[20]));
			empLoanModel.setVoucherno(StringHelperUtils.isNullString(object[22]));
			empLoanModel.setVoucherdate(voucherDate);
			empLoanModel.setLoansancorderdate(sancOrderDate);

			empLoanModel.setLoanAdvName("GPF Advance");
			
			///empLoanModel.setEmployeeName(StringHelperUtils.isNullString(object[0]));
			empLoanModel.setTotalRecoveredAmount(StringHelperUtils.isNullLong(object[41]));
			
			
			
			listEmpLoanModel.add(empLoanModel);
		}
		return listEmpLoanModel;
	}

//	@Override
//	public Integer checkloanAlreadyTaken(BigInteger empId, BigInteger advId) {
//		// TODO Auto-generated method stub
//		return employeeLoanDetailsRepo.checkloanAlreadyTaken(empId, advId);
//	}
//
//	@Override
//	public List<LoanEmployeeDtlsEntity> getGpfAdvAppNo(String sevaarthId, UserInfo messages) {
//		// TODO Auto-generated method stub
//		return employeeLoanDetailsRepo.getGpfAdvAppNo(sevaarthId, messages);
//	}
//
//	@Override
//	public List<LoanEmployeeDtlsEntity> getGpfAppTrnDtlsByAppId(String sevaarthId, Integer appId, UserInfo messages) {
//		// TODO Auto-generated method stub
//		return employeeLoanDetailsRepo.getGpfAppTrnDtlsByAppId(sevaarthId, appId, messages);
//	}
//
//	@Override
//	public List<LoanEmployeeDtlsEntity> mergeMultipleGpfApp(String sevaarthId, UserInfo messages) {
//		// TODO Auto-generated method stub
//		return employeeLoanDetailsRepo.mergeMultipleGpfApp(sevaarthId, messages);
//	}

}
