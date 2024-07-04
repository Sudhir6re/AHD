package com.mahait.gov.in.repository;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.model.MstEmployeeModel;

public interface EmpChangeDetailsRepo {

	List<Object[]> findEmpforChangeDtls(String userName);

	MstEmployeeModel getEmpData(int empId);

	List<Object[]> getEmpSignPhoto(Integer employeeId);

	/*List<MstSevenMatrixEntity> getsevenPCBasic(int payscaleId);

	List<MstEmployeeEntity> findEmpLst(String ddocode);

	MstEmployeeEntity getEmployeeData(int empId);

	List<Object[]> GetCurrentPost(int designationId, String ddocode);

	void updateChangeEmpDtls(@Valid MstEmployeeEntity mstEmployeeEntity);

	List<MstEmployeeEntity> findEmpLstforApprovChngDtls();

	MstEmployeeEntity findempid(Integer employeeId);
*/
	List<Object[]> GetCurrentPostDesigation(Integer postdetailid);

	/*void updateChangeEmpHstDtls(ChangeDtlsHst changeDtlsHst);
*/
}
