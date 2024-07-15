package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.model.OrganisationDtlsModel;
import com.mahait.gov.in.repository.OrganisationDtlsRepo;

@Service
@Transactional
public class OrganisationDtlsServiceImpl implements OrganisationDtlsService {
	
	@Autowired
	OrganisationDtlsRepo organisationDtlsRepo;
	
	
	@Override
	public @Valid OrganisationDtlsModel lstOfficeDetails(String ddoCode) {
		DdoOffice lstprop = organisationDtlsRepo.lstGetOfficeDtls(ddoCode);
		OrganisationDtlsModel obj = new OrganisationDtlsModel();
        if (lstprop!=null) {
                obj.setDdoCode(lstprop.getDcpsDdoCode());
                obj.setOfficeName(lstprop.getDcpsDdoOfficeName());
                obj.setStateId(lstprop.getDcpsDdoOfficeState());
                obj.setDistrictId(lstprop.getDcpsDdoOfficeDistrict());
                obj.setTalukaId(lstprop.getDcpsDdoOfficeTaluka());
                obj.setCity(lstprop.getDcpsDdoOfficeTown());
                obj.setVillage(lstprop.getDcpsDdoOfficeVillage());
                obj.setAddress(lstprop.getDcpsDdoOfficeAddress1());
                obj.setPin(lstprop.getDcpsDdoOfficePin());
                obj.setCityClass(lstprop.getDcpsDdoOfficeCityClass());
                obj.setInstiNo(lstprop.getDiceCode());
                obj.setPercGrant(lstprop.getDcpsDdoOfficeGrant());
                obj.setTel1(lstprop.getDcpsDdoOfficeTelNo1());
                obj.setTel2(lstprop.getDcpsDdoOfficeTelNo2());
                obj.setFax(lstprop.getDcpsDdoOfficeFax());
                obj.setEmail(lstprop.getDcpsDdoOfficeEmail());
                
	}
        return obj;

	}

	@Override
	public Long SaveorgInstituteInfo(@Valid OrganisationDtlsModel organisationDtlsModel,OrgUserMst messages) {
		

		DdoOffice objForSave = new DdoOffice();
		
		objForSave.setDcpsDdoOfficeName(organisationDtlsModel.getOfficeName());
		objForSave.setDcpsDdoOfficeState(organisationDtlsModel.getStateId());
        objForSave.setDcpsDdoOfficeDistrict(organisationDtlsModel.getDistrictId());
        objForSave.setDcpsDdoOfficeTaluka(organisationDtlsModel.getTalukaId());
        objForSave.setDcpsDdoOfficeTown(organisationDtlsModel.getCity());
        objForSave.setDcpsDdoOfficeVillage(organisationDtlsModel.getVillage());
        objForSave.setDcpsDdoOfficeAddress1(organisationDtlsModel.getAddress());
        objForSave.setDcpsDdoOfficePin(organisationDtlsModel.getPin());
        objForSave.setDcpsDdoOfficeCityClass(organisationDtlsModel.getCityClass());
        objForSave.setDiceCode(organisationDtlsModel.getInstiNo());
        objForSave.setDcpsDdoOfficeGrant(organisationDtlsModel.getPercGrant());
        objForSave.setDcpsDdoOfficeTelNo1(organisationDtlsModel.getTel1());
        objForSave.setDcpsDdoOfficeTelNo2(organisationDtlsModel.getTel2());
        objForSave.setDcpsDdoOfficeFax(organisationDtlsModel.getFax());
        objForSave.setDcpsDdoOfficeEmail(organisationDtlsModel.getEmail());
        objForSave.setStatusFlag(0l);
        objForSave.setDcpsDdoOfficeDdoFlag("YES");
        objForSave.setLangId(1l);
        objForSave.setLocId(1l);
        objForSave.setCreatedDate(new Date());
        objForSave.setPostId(messages.getCreatedByPost().getPostId());
        objForSave.setUpdatedPostId(messages.getCreatedByPost().getPostId());
        objForSave.setUserId(messages.getUserId());
        objForSave.setDbId(99l);
		
		Long saveId=0l;
		if(organisationDtlsModel.getDdoCode()==null) {
			 saveId = organisationDtlsRepo.saveorgInstInfo(objForSave);
		}else {
			saveId=5l;
			organisationDtlsRepo.updateorgInstituteInfo(objForSave);
		}
		
		return saveId;
		
		
	}

	@Override
	public Map<String, Object> findDataByDistrict(String districtId) {
		List talukalist = organisationDtlsRepo.findtalukalist(districtId);
		
		
		if(talukalist.size()>0 && talukalist!=null) {

			List citylist = organisationDtlsRepo.findcitylist(districtId);
			
			Map<String, Object> response = new HashMap<>();
			
			response.put("talukaList", talukalist);
			response.put("cityList", citylist);
			return response;
		}else {
			Map<String, Object> response = new HashMap<>();
			return response;
		}

	}

	@Override
	public int updateddoOfficeDetails(OrganisationDtlsModel organisationDtlsModel,OrgUserMst messages) {
	String[] ddo = organisationDtlsModel.getDdoCode().split("_");
		
		String ddoCode = ddo[0]; 
		
		DdoOffice lstprop = organisationDtlsRepo.lstGetOfficeDtls(ddoCode);

		
		lstprop.setDcpsDdoOfficeName(organisationDtlsModel.getOfficeName());
		lstprop.setDcpsDdoOfficeState(organisationDtlsModel.getStateId());
        lstprop.setDcpsDdoOfficeDistrict(organisationDtlsModel.getDistrictId());
        lstprop.setDcpsDdoOfficeTaluka(organisationDtlsModel.getTalukaId());
        lstprop.setDcpsDdoOfficeTown(organisationDtlsModel.getCity());
        lstprop.setDcpsDdoOfficeVillage(organisationDtlsModel.getVillage());
        lstprop.setDcpsDdoOfficeAddress1(organisationDtlsModel.getAddress());
        lstprop.setDcpsDdoOfficeCityClass(organisationDtlsModel.getCityClass());
        lstprop.setDiceCode(organisationDtlsModel.getInstiNo());
        lstprop.setDcpsDdoOfficeGrant(organisationDtlsModel.getPercGrant());
        lstprop.setDcpsDdoOfficeTelNo1(organisationDtlsModel.getTel1());
        lstprop.setDcpsDdoOfficeTelNo2(organisationDtlsModel.getTel2());
        lstprop.setDcpsDdoOfficeFax(organisationDtlsModel.getFax());
        lstprop.setDcpsDdoOfficeEmail(organisationDtlsModel.getEmail());
        lstprop.setStatusFlag(0l);
        lstprop.setDcpsDdoOfficeDdoFlag("YES");
        lstprop.setLangId(1l);
        lstprop.setLocId(1l);
        lstprop.setCreatedDate(new Date());
        lstprop.setPostId(messages.getCreatedByPost().getPostId());
        lstprop.setUpdatedPostId(messages.getCreatedByPost().getPostId());
        lstprop.setUserId(messages.getUserId());
        lstprop.setDbId(99l);
		
        organisationDtlsRepo.updateddoOfficeDetails(lstprop);
	
		
		return 10;
	}

	


}
