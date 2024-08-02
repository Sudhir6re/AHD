package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.MstEmployeeDetailEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstGisdetailsEntity;
import com.mahait.gov.in.entity.MstGpfDetailsEntity;
import com.mahait.gov.in.entity.MstNomineeDetailsEntity;
import com.mahait.gov.in.model.EmpChangeDetailsModel;
import com.mahait.gov.in.model.MstEmployeeModel;

public interface EmpChangeDetailsRepo {

	List<Object[]> findEmpforChangeDtls(String userName);

	MstEmployeeModel getEmpData(int empId);

	List<Object[]> getEmpSignPhoto(Long employeeId);

	/*List<MstSevenMatrixEntity> getsevenPCBasic(int payscaleId);

	List<MstEmployeeEntity> findEmpLst(String ddocode);

	MstEmployeeEntity getEmployeeData(int empId);

	List<Object[]> GetCurrentPost(int designationId, String ddocode);

	void updateChangeEmpDtls(@Valid MstEmployeeEntity mstEmployeeEntity);

	List<MstEmployeeEntity> findEmpLstforApprovChngDtls();

	MstEmployeeEntity findempid(Integer employeeId);
*/
	List<Object[]> GetCurrentPostDesigation(Integer postdetailid);

	List<MstEmployeeDetailEntity> getEmployeeDetails(String ddoCode);

	public String getDesignationName(String strDesgId);

	EmpChangeDetailsModel getEmployeeinfo(Long employeeId);

	MstEmployeeDetailEntity findbyemplidForChangeDetails(Long employeeId);

	public long updateChangeEmployeeDetails(MstEmployeeDetailEntity objEntity,
			@Valid EmpChangeDetailsModel empChangeDetailsModel, MstNomineeDetailsEntity[] lArrNomineeDtls);

	MstGpfDetailsEntity findbyGPFid(Long gpf_id);

	MstGisdetailsEntity findbyGisid(Long gisid);
	
	/*void updateChangeEmpHstDtls(ChangeDtlsHst changeDtlsHst);
*/
}
