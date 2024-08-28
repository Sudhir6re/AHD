package com.mahait.gov.in.repository;

import java.math.BigInteger;
import java.util.List;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.EmpLoanModel;

public interface EmployeeLoanDetailsRepo {

	Long saveEmployeeLoanDtls(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity);

	List<Object[]> getEmpInfoBySevaarthId(String sevaarthId, String ddoCode);

	List<Object[]> findAllEmpLoanDtls(String ddoCode);

	Integer checkloanAlreadyTaken(BigInteger empId, BigInteger advId);

	List<LoanEmployeeDtlsEntity> getGpfAdvAppNo(String sevaarthId, OrgUserMst messages);

	List<LoanEmployeeDtlsEntity> getGpfAppTrnDtlsByAppId(String sevaarthId, Integer appId, OrgUserMst messages);

	LoanEmployeeDtlsEntity findLoanDetailsById(Integer loanEmpAdvId);

	void updateLoanDetail(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity);

	List<LoanEmployeeDtlsEntity> mergeMultipleGpfApp(String sevaarthId, OrgUserMst messages);

	List<DeptEligibilityForAllowAndDeductEntity> findLoanNames();

	EmpLoanModel findSavedEmpLoanDtls(String sevaarthId);

}
