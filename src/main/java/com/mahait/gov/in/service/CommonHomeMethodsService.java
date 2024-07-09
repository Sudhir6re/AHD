package com.mahait.gov.in.service;

import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstBankPay;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.model.MstDesnModel;
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

	public int saveMstRole(@Valid MstRoleEntity mstRoleEntity);

	public String editRoleSave(@Valid MstRoleEntity mstRoleEntity);

	public  List<CmnLookupMst> findCommonMstByCommonCode(String commoncodeStatus);

	public List<MstBankPay> findBankName();

	public List<MstDesnModel> findDesignation(String userName);
	
	

}