package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.model.MstDesignationModel;


public interface MstDesignationService {
	List<MstPayCommissionEntity> findAllPayCommission();
	public List<MstDesignationModel> getDesignationMstData(String locale);
	/*
	
	public int saveDesignationMst(MstDesignationModel mstDesignationModel,int userId);
	public String getCadreDesc(Integer fieldDepartmrntID, String language);
	public List<MstPayCommissionEntity> findAllPayCommission();
	public List<MstDesignationModel> getDesignationMstData(String locale);
=======
import com.mahait.gov.in.entity.MstPayCommissionEntity;

public interface MstDesignationService {
	
//	public int saveDesignationMst(MstDesignationModel mstDesignationModel,int userId);
//	public String getCadreDesc(Integer fieldDepartmrntID, String language);
	public List<MstPayCommissionEntity> findAllPayCommission();
	/*public List<MstDesignationModel> getDesignationMstData(String locale);
>>>>>>> d62234043a6ef75399080141dbb407484af22d68
	public MstDesignationEntity findMstDesgByDesgId(int designationId);
	public List<MstCadreEntity> findCadreDescByFldDeptId(Integer fldDeptId);
	public String editDesgSave(MstDesignationEntity mstDesgEntity, int userId);
	public MstDesignationEntity findMstDesgByIdForReject(int designationId,int userId);
	public List<Long> validateDesignationName(String desgname);
	public List<MstCadreEntity> getCadre();
<<<<<<< HEAD

*/
	}


