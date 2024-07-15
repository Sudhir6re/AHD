package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstBankEntity;
import com.mahait.gov.in.entity.MstMonthEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.entity.MstYearEntity;
import com.mahait.gov.in.entity.ReligionMstEntity;
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

	public List<MstBankEntity> findBankName();

	/*public List<MstDesnModel> findDesignation(String userName);

	public List<MstDistrictModel> lstGetAllDistrict(String userName);*/
/*
	public List<MstTalukaModel> lstGetAllTaluka();

	public List<MstvillageModel> lstGetAllVillage();

	public List<MstcityModel> lstGetAllCity();
*/
	public List<Object[]> findAllBankBranchList(int bankCode);

	public Object getIfscCodeByBranchId(int branchId);

	public Object getBankBranch(String bankId);

	public List<Object[]> retriveUserdetails(Long userId);

	public List<ReligionMstEntity> fetchAllReligions();


	public List<CmnLookupMst> findCommonMstByLookupname(String commoncodeSalutations);


	public List<MstDesnModel> findDesignation(String userName);
	
	public List<MstMonthEntity> lstGetAllMonths();

	public List<MstYearEntity> lstGetAllYears();
	
	public Date findbillCreateDate(int billNumber);
	
	public List<Object[]> findDetailsBillNumber(int billNumber);
	
	public List<Object[]> findyearinfo(BigInteger yearcurr);
	
	public List<Object[]> findmonthinfo(BigInteger bigInteger);

	public String getOffice(String ddoCode);
	
	

}