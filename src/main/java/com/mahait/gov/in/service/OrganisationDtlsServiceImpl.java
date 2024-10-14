package com.mahait.gov.in.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.model.OrganisationDtlsModel;
import com.mahait.gov.in.repository.OrganisationDtlsRepo;
import com.mahait.gov.in.repository.OrganizationInstInfoRepo;

@Service
@Transactional
public class OrganisationDtlsServiceImpl implements OrganisationDtlsService {
	
	@Autowired
	OrganisationDtlsRepo organisationDtlsRepo;
	
	@Autowired
	OrganizationInstInfoRepo organizationInstInfoRepo;
	
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
       // return obj;
        
        
        OrgDdoMst OrgInfo = organizationInstInfoRepo.findDDOInfo(ddoCode);
		//OrgDdoMstModel orgDdoMstModel = new OrgDdoMstModel();
       if(OrgInfo!=null) {
    	   obj.setDdoOffice(OrgInfo.getDdoOffice());
    	   obj.setDesignationId(OrgInfo.getDsgnCode());
    	   obj.setStartDate(OrgInfo.getStartDate());
    	   obj.setTanNo(OrgInfo.getTanNo());
    	   obj.setItaWardNo(OrgInfo.getItawardcircle());
    	   obj.setBankName(OrgInfo.getBankName());
    	   obj.setBranchName(OrgInfo.getBranchName());
    	   obj.setIfscCode(OrgInfo.getIfsCode());
    	   obj.setAccountNo(OrgInfo.getAccountNo());
    	   obj.setRemarks(OrgInfo.getRemarks());
    	   obj.setInstituteType(OrgInfo.getInstituteTypeId());
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
		
		
        OrgDdoMst OrgInfo = new OrgDdoMst();
		//OrgDdoMst OrgInfo = organizationInstInfoRepo.findDDOInfo(organisationDtlsModel.getDdoCode());
		OrgInfo.setDdoOffice(organisationDtlsModel.getDdoOffice());
		OrgInfo.setDsgnCode(organisationDtlsModel.getDesignationId());
		OrgInfo.setStartDate(organisationDtlsModel.getStartDate());
		OrgInfo.setTanNo(organisationDtlsModel.getTanNo());
		OrgInfo.setItawardcircle(organisationDtlsModel.getItaWardNo());
		OrgInfo.setBankName(organisationDtlsModel.getBankName());
		OrgInfo.setBranchName(organisationDtlsModel.getBranchName());
		OrgInfo.setIfsCode(organisationDtlsModel.getIfscCode());
		OrgInfo.setAccountNo(organisationDtlsModel.getAccountNo());
		OrgInfo.setRemarks(organisationDtlsModel.getRemarks());
		OrgInfo.setInstituteTypeId(organisationDtlsModel.getInstituteType());
		OrgInfo.setDdoCode(organisationDtlsModel.getDdoCode());
		
		//Long saveId=null;
		 organizationInstInfoRepo.updateorgInstituteInfo(OrgInfo);
		 
			// saveId = objForSave.getDdoId();
		

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
		
		DdoOffice lstprop = organisationDtlsRepo.lstGetOfficeDtls(messages.getDdoCode());

		
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
        lstprop.setDcpsDdoOfficePin(organisationDtlsModel.getPin());
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
        
        
        OrgDdoMst findDDOInfo = organizationInstInfoRepo.findDDOInfo(messages.getDdoCode());
		findDDOInfo.setDdoOffice(organisationDtlsModel.getDdoOffice());
		findDDOInfo.setDsgnCode(organisationDtlsModel.getDesignationId());
		findDDOInfo.setStartDate(organisationDtlsModel.getStartDate());
		findDDOInfo.setTanNo(organisationDtlsModel.getTanNo());
		findDDOInfo.setItawardcircle(organisationDtlsModel.getItaWardNo());
		findDDOInfo.setBankName(organisationDtlsModel.getBankName());
		findDDOInfo.setBranchName(organisationDtlsModel.getBranchName());
		findDDOInfo.setIfsCode(organisationDtlsModel.getIfscCode());
		findDDOInfo.setAccountNo(organisationDtlsModel.getAccountNo());
	  	 findDDOInfo.setRemarks(organisationDtlsModel.getRemarks());
	  	 findDDOInfo.setInstituteTypeId(organisationDtlsModel.getInstituteType());
		organizationInstInfoRepo.updateorgInstituteInfo(findDDOInfo);
	
		
		return 10;
	}

	


}
