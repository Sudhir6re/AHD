package com.mahait.gov.in.repository;

import java.util.List;

public interface EmployeeLoanDetailsRepo {
	List<Object[]> findAllEmpLoanDtls(String ddoCode);
}
