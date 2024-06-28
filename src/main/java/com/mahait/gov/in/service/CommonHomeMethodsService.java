package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.model.MstMenuModel;
import com.mahait.gov.in.model.MstMenuRoleMappingModel;
import com.mahait.gov.in.model.MstRoleModel;
import com.mahait.gov.in.model.MstSubMenuModel;
import com.mahait.gov.in.model.TopicModel;

public interface CommonHomeMethodsService {
	public List<TopicModel> findMenuNameByRoleID(int levelRoleVal, String lang);

	public List<TopicModel> findSubMenuByRoleID(int levelRoleVal, String lang);

	public List<MstMenuModel> findAllMenu(String language);

	public List<MstRoleModel> findAllRole(String language);

	public List<MstSubMenuModel> findAllSubMenu(String language);

	public List<MstMenuRoleMappingModel> findAllMenuRoleMapping(String language);

	public MstRoleEntity findRole(int roleId);


	public MstRoleEntity deleteRoleById(int roleId);

	List<MstRoleEntity> findAllRole();
}