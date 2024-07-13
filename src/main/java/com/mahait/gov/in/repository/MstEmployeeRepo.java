package com.mahait.gov.in.repository;

import java.math.BigInteger;
import java.util.List;

import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstRoleEntity;

public interface MstEmployeeRepo {
	
	public List<MstEmployeeEntity> findAllWorkingEmployeeByDDOCodeAndBillGroup(String ddoCode, BigInteger billGroupId,
			int month, int year);

	public DdoOffice findAllGroup(String ddoCode);

	public int getpayCommissionAgainstEmployee(String sevaarthId);

	public List<Object[]> findEmployeeAllowanceDeduction(String sevaarthId);

	public EmployeeAllowDeducComponentAmtEntity findGRPComponentsData(String sevaarthId, int allowDedCode);

	public LoanEmployeeDtlsEntity findGPFADetails(String sevaarthid, int commoncodeComponentGpfaCode);

	public LoanEmployeeDtlsEntity findGPFAdvDetails(String sevaarthid, int commoncodeComponentGpfaCode);



	public List<Object[]> findDDOScreenDataTable(String ddoCode);

	public List<Object[]> getInstitueDtls(String ddocode);

	public List<Object[]> getCadreMstData();

	public List<MstCadreGroupEntity> getGISGroup();

	public List<MstRoleEntity> findAll();
	
	

}
