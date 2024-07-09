package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstBankPay;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.model.MstDesnModel;

public interface CommonHomeMethodsRepo {


	public List<Object[]> findRoleLevelMstList();

	public List<Object[]> findMenuNameByRoleID(int levelRoleVal);

	public List<Object[]> findSubMenuByRoleID(int levelRoleVal);

	public List<Object[]> findAllMenu();

	public List<Object[]> findAllRole();

	public List<Object[]> findAllSubMenu();

	public List<Object[]> findAllMenuRoleMapping();
																								// Date,int
						
	public int saveMstRole(MstRoleEntity mstRoleEntity);


	public MstRoleEntity findMstRoleId(int roleId);

	public void updateMstRoleStatus(MstRoleEntity objDeptForReject);


	public MstRoleEntity findroleById(Integer roleId);

	public void updaterole(MstRoleEntity objrole);

	
	public List<MstCommonEntity> findCommonMstByCommonCode(String commoncodeStatus);

	public List<MstBankPay> findBankName();

	public List<MstDesnModel> findDesignation(String userName);

	public List<Object[]> retriveUserdetails(Long userId);
	
}
