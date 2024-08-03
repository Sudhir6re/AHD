package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.AllowanceDeductionRuleMstEntity;
import com.mahait.gov.in.repository.AllowDeducRuleMasterRepo;


@Transactional
@Service
public class AllowDeducRuleMasterServiceImpl implements AllowDeducRuleMasterService{
	
	
	@Autowired 
	AllowDeducRuleMasterRepo allowDeducRuleMasterRepo;

	@Override
	public List<AllowanceDeductionRuleMstEntity> findAllRules() {
		List<AllowanceDeductionRuleMstEntity> lstAllowanceDeductionMstEntity=new ArrayList<>();
		List<Object[]> lstObj=allowDeducRuleMasterRepo.findAllRules();
		
 		for(Object object[]:lstObj) {
 			AllowanceDeductionRuleMstEntity allowanceDeductionMstEntity=new AllowanceDeductionRuleMstEntity(); 
 			allowanceDeductionMstEntity.setAllowanceDeductionWiseRuleId(StringHelperUtils.isNullInt(object[0]));
 			allowanceDeductionMstEntity.setAmount(StringHelperUtils.isNullDouble(object[1]));  //10 amt
 			//allowanceDeductionMstEntity.setCreatedUserId(StringHelperUtils.isNullLong(object[3]));
 			
 			allowanceDeductionMstEntity.setDepartmentAllowdeducCode(StringHelperUtils.isNullInt(object[4]));
 			if(object[5]!=null) {
 	 			allowanceDeductionMstEntity.setEndDate(StringHelperUtils.isNullDate(object[5]));
 	 			}
 			allowanceDeductionMstEntity.setIsActive(StringHelperUtils.isNullChar(object[6]));
 			allowanceDeductionMstEntity.setIsType(StringHelperUtils.isNullInt(object[7]));
 			allowanceDeductionMstEntity.setPayCommissionCode(StringHelperUtils.isNullInt(object[8]));
 			allowanceDeductionMstEntity.setPercentage(StringHelperUtils.isNullInt(object[9]));  //11 perc  
 			allowanceDeductionMstEntity.setStartDate(StringHelperUtils.isNullDate(object[10]));
 			allowanceDeductionMstEntity.setBasic(StringHelperUtils.isNullDouble(object[13]));
 			allowanceDeductionMstEntity.setCityClass(StringHelperUtils.isNullCharacter(object[14]));
 			allowanceDeductionMstEntity.setMaxBasic(StringHelperUtils.isNullDouble(object[15]));
 			allowanceDeductionMstEntity.setMinBasic(StringHelperUtils.isNullDouble(object[16]));
 			allowanceDeductionMstEntity.setCityClass(StringHelperUtils.isNullCharacter(object[17]));
 			allowanceDeductionMstEntity.setGradePayHigher(StringHelperUtils.isNullInt(object[18]));
 			allowanceDeductionMstEntity.setGradePayLower(StringHelperUtils.isNullInt(object[19]));
 		
 			allowanceDeductionMstEntity.setDepartmentAllowdeducName(StringHelperUtils.isNullString(object[20]));     //12
 			allowanceDeductionMstEntity.setPayCommisionName(StringHelperUtils.isNullString(object[21]));
 			lstAllowanceDeductionMstEntity.add(allowanceDeductionMstEntity);
		}
		return lstAllowanceDeductionMstEntity;
	}

	@Override
	public List<AllowanceDeductionRuleMstEntity> findAllRules(int departmentAllowdeducCode) {
		List<AllowanceDeductionRuleMstEntity> lstAllowanceDeductionMstEntity=new ArrayList<>();
		List<Object[]> lstObj=allowDeducRuleMasterRepo.findAllRules(departmentAllowdeducCode);
 		for(Object object[]:lstObj) {
 			AllowanceDeductionRuleMstEntity allowanceDeductionMstEntity=new AllowanceDeductionRuleMstEntity(); 
 			allowanceDeductionMstEntity.setAllowanceDeductionWiseRuleId(StringHelperUtils.isNullInt(object[0]));
 			allowanceDeductionMstEntity.setAmount(StringHelperUtils.isNullDouble(object[1]));  //10 amt
 			//allowanceDeductionMstEntity.setCreatedUserId(StringHelperUtils.isNullLong(object[3]));
 			
 			allowanceDeductionMstEntity.setDepartmentAllowdeducCode(StringHelperUtils.isNullInt(object[4]));
 			if(object[5]!=null) {
 	 			allowanceDeductionMstEntity.setEndDate(StringHelperUtils.isNullDate(object[5]));
 	 			}
 			allowanceDeductionMstEntity.setIsActive(StringHelperUtils.isNullChar(object[6]));
 			allowanceDeductionMstEntity.setIsType(StringHelperUtils.isNullInt(object[7]));
 			allowanceDeductionMstEntity.setPayCommissionCode(StringHelperUtils.isNullInt(object[8]));
 			allowanceDeductionMstEntity.setPercentage(StringHelperUtils.isNullInt(object[9]));  //11 perc  
 			allowanceDeductionMstEntity.setStartDate(StringHelperUtils.isNullDate(object[10]));
 			allowanceDeductionMstEntity.setBasic(StringHelperUtils.isNullDouble(object[13]));
 			allowanceDeductionMstEntity.setCityClass(StringHelperUtils.isNullCharacter(object[14]));
 			allowanceDeductionMstEntity.setMaxBasic(StringHelperUtils.isNullDouble(object[15]));
 			allowanceDeductionMstEntity.setMinBasic(StringHelperUtils.isNullDouble(object[16]));
 			allowanceDeductionMstEntity.setCityClass(StringHelperUtils.isNullCharacter(object[17]));
 			allowanceDeductionMstEntity.setGradePayHigher(StringHelperUtils.isNullInt(object[18]));
 			allowanceDeductionMstEntity.setGradePayLower(StringHelperUtils.isNullInt(object[19]));
 		
 			allowanceDeductionMstEntity.setDepartmentAllowdeducName(StringHelperUtils.isNullString(object[20]));     //12
 			allowanceDeductionMstEntity.setPayCommisionName(StringHelperUtils.isNullString(object[21]));
 			lstAllowanceDeductionMstEntity.add(allowanceDeductionMstEntity);
		}
		return lstAllowanceDeductionMstEntity;
	}


	@Override
	public int saveAllowanceDeductionRulesMaster(AllowanceDeductionRuleMstEntity allowanceDeductionRuleMstEntity) {
		return allowDeducRuleMasterRepo.saveAllowanceDeductionRulesMaster(allowanceDeductionRuleMstEntity);
		}

	@Override
	public int updateAllowanceDeductionRulesMaster(AllowanceDeductionRuleMstEntity allowanceDeductionRuleMstEntity) {
		AllowanceDeductionRuleMstEntity 	allowanceDeductionRuleMstEntity1=allowDeducRuleMasterRepo.findRuleByComponentCode(allowanceDeductionRuleMstEntity.getAllowanceDeductionWiseRuleId());
		if(allowanceDeductionRuleMstEntity1!=null) {
			allowanceDeductionRuleMstEntity1.setStartDate(allowanceDeductionRuleMstEntity.getStartDate());
			allowanceDeductionRuleMstEntity1.setEndDate(allowanceDeductionRuleMstEntity.getEndDate());
			allowanceDeductionRuleMstEntity1.setMinBasic(allowanceDeductionRuleMstEntity.getMinBasic());
			allowanceDeductionRuleMstEntity1.setMaxBasic(allowanceDeductionRuleMstEntity.getMaxBasic());
			allowanceDeductionRuleMstEntity1.setCityClass(allowanceDeductionRuleMstEntity.getCityClass());
			allowanceDeductionRuleMstEntity1.setGradePayHigher(allowanceDeductionRuleMstEntity.getGradePayHigher());
			allowanceDeductionRuleMstEntity1.setGradePayLower(allowanceDeductionRuleMstEntity.getGradePayLower());
			allowanceDeductionRuleMstEntity1.setAmount(allowanceDeductionRuleMstEntity.getAmount());
			allowanceDeductionRuleMstEntity1.setPercentage(allowanceDeductionRuleMstEntity.getPercentage());
			allowanceDeductionRuleMstEntity1.setPayCommissionCode(allowanceDeductionRuleMstEntity.getPayCommissionCode());
			allowDeducRuleMasterRepo.updateAllowanceDeductionRulesMaster(allowanceDeductionRuleMstEntity1);
		}
		return allowanceDeductionRuleMstEntity1.getAllowanceDeductionWiseRuleId();
		
	}

	@Override
	public AllowanceDeductionRuleMstEntity findRuleByRuleId(int allowanceDeductionWiseRuleId) {
		return allowDeducRuleMasterRepo.findRuleByRuleId(allowanceDeductionWiseRuleId);
	}

}
