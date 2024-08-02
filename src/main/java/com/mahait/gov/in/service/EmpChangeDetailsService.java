package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.entity.MstNomineeDetailsEntity;
import com.mahait.gov.in.model.EmpChangeDetailsModel;
import com.mahait.gov.in.model.MstEmployeeModel;


public interface EmpChangeDetailsService {

	List<EmpChangeDetailsModel> findEmpforChangeDtls(String userName);


	MstEmployeeModel getEmpData(int empId);


	List<Object[]> getEmpSignPhoto(Long employeeId);


	/*List<MstSevenMatrixEntity> getsevenPCBasic(int payscaleId);


	List<MstEmployeeEntity> findEmpLst(String ddocode);


	MstEmployeeEntity getEmployeeData(int empId);

	public List<Object[]> GetCurrentPost(int designationId, String ddocode);


	int updateChangeEmpDtls(@Valid MstEmployeeEntity mstEmployeeEntity, MultipartFile[] files,int roleid, UserInfo messages);


	List<MstEmployeeEntity> findEmpLstforApprovChngDtls();*/
	int updateChangeEmpDtls();

	public List<Object[]> GetCurrentPostDesigation(Integer postdetailid);


	List<EmpChangeDetailsModel> getEmployeeDetails(String ddoCode, String language);


	EmpChangeDetailsModel getEmployeeinfo(Long employeeId);


	long updateEmployeeChangeDetails(@Valid EmpChangeDetailsModel empChangeDetailsModel, MultipartFile[] files);


	String[] savePhotoSignature(MultipartFile[] files, String DeptNm, Long long1, String existphotpath,
			String existsignpath);


}
