package com.mahait.gov.in.repository;

import java.util.Date;
import java.util.List;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstBankEntity;
import com.mahait.gov.in.entity.MstMonthEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.entity.MstYearEntity;
import com.mahait.gov.in.entity.ReligionMstEntity;
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

	
	public List<CmnLookupMst> findCommonMstByCommonCode(String commoncodeStatus);

	public List<MstBankEntity> findBankName();

	public List<Object[]> findDesignation();



	public List<Object[]> lstGetAllTaluka();

	public List<Object[]> lstGetAllVillage();

	public List<Object[]> lstGetAllCity();

	public List<Object[]> lstGetAllDistrict();

	public List<Object[]> findAllBankBranchList(int bankCode);

	public Object getIfscCodeByBranchId(int branchId);

	public List<Object[]> getBankBranch(String bankId);

	public List<Object[]> retriveUserdetails(Long userId);

	public List<ReligionMstEntity> fetchAllReligions();

	public List<MstDesnModel> findDesignation(String userName);
	
	public List<MstMonthEntity> lstGetAllMonths();

	public List<MstYearEntity> lstGetAllYears();

	public Date findbillCreateDate(int billNumber);
	
}
