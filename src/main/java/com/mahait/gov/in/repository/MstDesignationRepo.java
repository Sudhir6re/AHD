package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstPayCommissionEntity;

public interface MstDesignationRepo {

//	public int saveDesignationMst(MstDesignationEntity mstDesignationEntity);
//	public List<Object[]> getCadreDesc(Integer fieldDepartmrntID);
	public List<MstPayCommissionEntity> findAllPayCommission();
	/*public List<Object[]> getDesignationMstData();
	public MstDesignationEntity findMstDesgByDesgId(int designationId);
	public List<MstCadreEntity> findCadreDescByFldDeptId(int fldDeptId);
	public void updateDesginationStatus(MstDesignationEntity objDesg);
	public List<Long> validateDesignationName(String desgname);
	public List<MstCadreEntity> getCadre();*/
}
