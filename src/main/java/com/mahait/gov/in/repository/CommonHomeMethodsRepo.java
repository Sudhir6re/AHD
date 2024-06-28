package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstRoleEntity;

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

	
}
