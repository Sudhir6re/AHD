package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.model.EmpWiseCityClassModel;
import com.mahait.gov.in.model.OrgDdoMst;
import com.mahait.gov.in.repository.DDOSchemeRepo;

@Service
@Transactional
public class DDOSchemeServiceImpl implements DDOSchemeService {
	
	
	@Autowired
	DDOSchemeRepo ddoSchemeRepo;

	@Override
	public List<EmpWiseCityClassModel> findAllEmployee(String userName) {
		 List<Object[]> list = ddoSchemeRepo.findAllEmployee(userName);
			
			List<EmpWiseCityClassModel> listobj = new ArrayList<>();
			if(!list.isEmpty())
			{
				
				//employee_id,sevaarth_id,employee_full_name_en,district_code,taluka_code,city_class
				for(Object[] obj:list ) //for (Object[] objLst : lstprop) {
				{
					EmpWiseCityClassModel obj1 = new EmpWiseCityClassModel();
					obj1.setEmployeeId(StringHelperUtils.isNullInt(obj[0]));
					obj1.setSevaarthId(StringHelperUtils.isNullString(obj[1]));
					obj1.setEmpName(StringHelperUtils.isNullString(obj[2]));
					obj1.setDistrictId(StringHelperUtils.isNullString(obj[3]));
					obj1.setTalukaId(StringHelperUtils.isNullString(obj[4]));
					if(obj[5]!=null)
					obj1.setCityClass(StringHelperUtils.isNullString(obj[5].toString()));
					
					listobj.add(obj1);
				}
			}
			return listobj;
	}

	@Override
	public String getDdoCodeForDDO(OrgPostMst createdByPost) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.getDdoCodeForDDO(createdByPost);
	}

	@Override
	public List<OrgDdoMst> getDDOCodeByLoggedInlocId(long l) {
		 List<Object[]> list = ddoSchemeRepo.getDDOCodeByLoggedInlocId(1);
			
			List<OrgDdoMst> listobj = new ArrayList<>();
			if(!list.isEmpty())
			{
				
				//employee_id,sevaarth_id,employee_full_name_en,district_code,taluka_code,city_class
				for(Object[] obj:list ) //for (Object[] objLst : lstprop) {
				{/*
					EmpWiseCityClassModel obj1 = new EmpWiseCityClassModel();
					obj1.setEmployeeId(StringHelperUtils.isNullInt(obj[0]));
					obj1.setSevaarthId(StringHelperUtils.isNullString(obj[1]));
					obj1.setEmpName(StringHelperUtils.isNullString(obj[2]));
					obj1.setDistrictId(StringHelperUtils.isNullString(obj[3]));
					obj1.setTalukaId(StringHelperUtils.isNullString(obj[4]));
					if(obj[5]!=null)
					obj1.setCityClass(StringHelperUtils.isNullString(obj[5].toString()));
					
					listobj.add(obj1);
				*/}
			}
			return listobj;
	}

	@Override
	public List getAllSchemesForDDO(String lStrDDOCode) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.getAllSchemesForDDO(lStrDDOCode);
	}

	@Override
	public String districtName(String ddoCode) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.districtName(ddoCode);
	}

	@Override
	public List allTaluka(String districtID) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.allTaluka(districtID);
	}

	@Override
	public List getSubDDOs(Long locId, String talukaId, String ddoSelected) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.getSubDDOs(locId,talukaId,ddoSelected);
	}

	@Override
	public List getpostRole(OrgPostMst createdByPost) {
		// TODO Auto-generated method stub
		return ddoSchemeRepo.getpostRole(createdByPost);
	}

}
