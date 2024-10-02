package com.mahait.gov.in.nps.sevice;

import java.util.List;

import com.mahait.gov.in.entity.EmployeeDetailEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.nps.entity.FormS1DetailsEntity;
import com.mahait.gov.in.nps.model.CSRFFormModel;

public interface CSRFFormService {

	

	public List<CSRFFormModel> findAllEmployees(String ddoCode);
	
	public FormS1DetailsEntity findEmployeeBySevaarthId(int empId);
//
//	public MstEmployeeEntity findEmployeeBySevaarthId(int empId);
//	
//	public MstEmployeeNPSModel findCSRFEmployeeBySevaarthId(int empId);
//
//	public List<MstNomineeDetailsEntity> findNomineeBySevaarthId(int sevaarthId);
//
//	public int saveCSRF(MstEmployeeEntity mstEmployeeEntity,MultipartFile[] files,String ddoLevel2);
//
//	public MstEmployeeNPSEntity findEmployeeDtlsBySevaarthId(int empId);
//
//	public List<CSRFFormModel> findAllCSRFApprovedEmployees();
//
//	public List<Object[]> getEmpData(String sevaarthId, String ddoAsst);
//
//	public List<MstEmployeeNPSEntity> getEmpDataForTextFile(String userName,int countEmp);
//
//	public String getDDOLevel2FromDDO1(String userName);
//
//	public List<Object[]> getDeptNameFromDDO2(String ddoLevel2);
//
//	public List<Object[]> getempData(String sevaarthId);
//
//	public Integer getSeqUpdate();
//
//	public Long getSequenceForTextFile(String string);
//
//	public int saveTrnNpsRegFile(TrnNpsRegFileEntity trnNpsRegFile);
//
//	public String getPayScale(BigInteger payScaleCode);
//
//	public TrnNpsRegFileEntity findTrnNpsFileEntityById(Integer id);
//
//	public void updateTrnNpsFileEntity(TrnNpsRegFileEntity trnNpsRegFileEntity);
//
//	public List<Object[]> findNpsIdByAckNo(String string);
//
//	public MstEmployeeNPSEntity findEmployeeByNpsid(Integer npsId);
//
//	public void updatePranNumberByNpsId(MstEmployeeNPSEntity mstEmployeeNPSEntity);
//
//	public void updateEmployeeByEmpId(MstEmployeeEntity mstEmployeeEntity);
//
//	public String getStateName(String empState);
//
//	public List<Object[]> viewCSRFPhotoSign(int empId);
//
//	

	public MstEmployeeEntity findEmployeeDtlsBySevaarthId(int empId);


}

