package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.model.EmpLoanModel;

public interface EmployeeLoanDetailsService {

	List<EmpLoanModel> findAllEmpLoanDtls(String ddoCode);

}
