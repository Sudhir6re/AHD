package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.model.MstMenuModel;
import com.mahait.gov.in.model.MstMenuRoleMappingModel;
import com.mahait.gov.in.model.MstRoleModel;
import com.mahait.gov.in.model.MstSubMenuModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.repository.CommonHomeMethodsRepo;
import com.mahait.gov.in.repository.MstRoleRepo;
import com.mahait.gov.in.repository.UserInfoRepo;

public class CommonHomeMethodsServiceImpl implements CommonHomeMethodsService  {
	// protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	CommonHomeMethodsRepo commonHomeMethodsRepo;

	@Autowired
	MstRoleRepo mstRoleRepo;

	@Autowired
	private UserInfoRepo userInfoDAO;

	
	@Override
	public List<TopicModel> findMenuNameByRoleID(int levelRoleVal, String lang) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findMenuNameByRoleID(levelRoleVal);
		List<TopicModel> lstMenuObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				TopicModel obj = new TopicModel();
				obj.setKey(StringHelperUtils.isNullInt(objLst[0]));
				if (lang.equals("en")) {
					obj.setMenuName(StringHelperUtils.isNullString(objLst[1]));
				} else {
					obj.setMenuName(StringHelperUtils.isNullString(objLst[2]));
				}
				lstMenuObj.add(obj);
			}
		}
		return lstMenuObj;
	}

	@Override
	public List<TopicModel> findSubMenuByRoleID(Integr levelRoleVal, String lang) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findSubMenuByRoleID(levelRoleVal);
		List<TopicModel> lstSubMenuObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				TopicModel obj = new TopicModel();
				obj.setKey(StringHelperUtils.isNullInt(objLst[0]));
				obj.setMenuKey(StringHelperUtils.isNullInt(objLst[1]));
				obj.setRoleKey(StringHelperUtils.isNullInt(objLst[2]));
				if (lang.equals("en")) {
					obj.setSubMenuName(StringHelperUtils.isNullString(objLst[3]));
				} else {
					obj.setSubMenuName(StringHelperUtils.isNullString(objLst[4]));
				}

				obj.setControllerName(StringHelperUtils.isNullString(objLst[5]));
				obj.setLinkName(StringHelperUtils.isNullString(objLst[6]));

				lstSubMenuObj.add(obj);
			}
		}
		return lstSubMenuObj;

	}

	@Override
	public List<MstMenuModel> findAllMenu(String language) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findAllMenu();
		List<MstMenuModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstMenuModel obj = new MstMenuModel();
				obj.setMenuId(StringHelperUtils.isNullInt(objLst[0]));
				obj.setMenuCode(StringHelperUtils.isNullInt(objLst[0]));
				obj.setMenuNameEnglish(StringHelperUtils.isNullString(objLst[2]));
				obj.setMenuNameMarathi(StringHelperUtils.isNullString(objLst[3]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[4]))));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstRoleModel> findAllRole(String language) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findAllRole();
		List<MstRoleModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstRoleModel obj = new MstRoleModel();
				obj.setKey(StringHelperUtils.isNullInt(objLst[0]));
				obj.setRoleId(StringHelperUtils.isNullInt(objLst[1]));
				obj.setRoleName(StringHelperUtils.isNullString(objLst[2]));
				obj.setRoleDesc(StringHelperUtils.isNullString(objLst[3]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[4]))));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstSubMenuModel> findAllSubMenu(String language) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findAllSubMenu();
		List<MstSubMenuModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstSubMenuModel obj = new MstSubMenuModel();
				obj.setSubMenuCode(StringHelperUtils.isNullInt(objLst[0]));
				obj.setSubMenuId(StringHelperUtils.isNullInt(objLst[0])); // key for edit sub menu
				obj.setRoleName(StringHelperUtils.isNullString(objLst[1]));
				obj.setMenuName(StringHelperUtils.isNullString(objLst[2]));
				obj.setSubMenuEnglish(StringHelperUtils.isNullString(objLst[3]));
				obj.setSubMenuMarathi(StringHelperUtils.isNullString(objLst[4]));
				obj.setControllerName(StringHelperUtils.isNullString(objLst[5]));
				obj.setLinkName(StringHelperUtils.isNullString(objLst[6]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[7]))));
				obj.setMenuCode(StringHelperUtils.isNullInt(objLst[8]));
				obj.setRoleId(StringHelperUtils.isNullInt(objLst[9]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}

	@Override
	public List<MstMenuRoleMappingModel> findAllMenuRoleMapping(String language) {

		List<Object[]> lstprop = commonHomeMethodsRepo.findAllMenuRoleMapping();
		List<MstMenuRoleMappingModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				MstMenuRoleMappingModel obj = new MstMenuRoleMappingModel();
				obj.setMenuMapId(StringHelperUtils.isNullInt(objLst[0]));
				if (language.equals("en")) {
					obj.setMenuName(StringHelperUtils.isNullString(objLst[1]));
				} else {
					obj.setMenuName(StringHelperUtils.isNullString(objLst[2]));
				}
				obj.setRoleName(StringHelperUtils.isNullString(objLst[3]));
				obj.setIsActive(StringHelperUtils.isNullInt(Integer.parseInt(String.valueOf(objLst[4]))));
				obj.setMenuCode(StringHelperUtils.isNullInt(objLst[5]));
				obj.setRoleId(StringHelperUtils.isNullInt(objLst[6]));
				lstObj.add(obj);
			}
		}
		return lstObj;
	}


	@Override
	public List<MstRoleEntity> findAllRole() {
		// TODO Auto-generated method stub
		return mstRoleRepo.findAll();
	}

	@Override
	public MstRoleEntity findRole(int roleId) {
		// TODO Auto-generated method stub
		return userInfoDAO.getRoleByRoleId(roleId);
	}


	public int saveMstRole(MstRoleEntity mstRoleEntity) {
		MstRoleEntity mstRole = new MstRoleEntity();
		int saveId = 0;
		if (mstRoleEntity.getRoleName() != null) {
			mstRole.setRoleName(mstRoleEntity.getRoleName().toUpperCase());
			mstRole.setRoleDescription(mstRoleEntity.getRoleDescription().toUpperCase());
			List<MstRoleEntity> mstroleobj = mstRoleRepo.findAll();
			int role = 0;
			for (MstRoleEntity mstRoleEntity1 : mstroleobj) {
				role = mstRoleEntity1.getRoleId();
			}
			mstRole.setRoleId(role + 1);
			mstRole.setIsActive('1');
			mstRole.setCreatedUserId(1);
			mstRole.setCreatedDate(new Date());
			saveId = commonHomeMethodsRepo.saveMstRole(mstRole);
		}

		return saveId;
	}
	

	@Override
	public MstRoleEntity deleteRoleById(int roleId) {
		// TODO Auto-generated method stub
		MstRoleEntity objDeptForReject = commonHomeMethodsRepo.findMstRoleId(roleId);
		if (objDeptForReject != null) {
			objDeptForReject.setIsActive('0'); // REJECTED
			objDeptForReject.setUpdatedDate(new Date());
			commonHomeMethodsRepo.updateMstRoleStatus(objDeptForReject);
		}
		return objDeptForReject;
	}



}
