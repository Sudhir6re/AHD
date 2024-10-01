package com.mahait.gov.in.nsdl.repository;

import java.util.List;

import com.mahait.gov.in.entity.EmployeeDetailEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstNomineeDetailsEntity;
import com.mahait.gov.in.nsdl.entity.FormS1DetailsEntity;

public interface CSRFFormRepository {

	List<Object[]> findAllEmployees(String ddoCode);

	FormS1DetailsEntity findEmployeeBySevaarthId(int empId);

	MstEmployeeEntity findEmployeeDtlsBySevaarthId(int empId);


}
