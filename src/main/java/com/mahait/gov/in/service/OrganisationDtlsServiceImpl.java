package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.model.OrganisationDtlsModel;
import com.mahait.gov.in.repository.OrganisationDtlsRepo;

@Service
@Transactional
public class OrganisationDtlsServiceImpl implements OrganisationDtlsService {
	
	@Autowired
	OrganisationDtlsRepo organisationDtlsRepo;

	@Override
	public List<OrganisationDtlsModel> lstGetOfficeDtls(String userName) {
		List<Object[]> lstprop = organisationDtlsRepo.lstGetOfficeDtls(userName);
		List<OrganisationDtlsModel> lstObj = new ArrayList<>();
        if (!lstprop.isEmpty()) {
            for (Object[] objLst : lstprop) {
            	OrganisationDtlsModel obj = new OrganisationDtlsModel();
                obj.setDdoCode(StringHelperUtils.isNullString(objLst[0]));
               
                obj.setOfficeName(StringHelperUtils.isNullString(objLst[1]));
                obj.setStateId(StringHelperUtils.isNullString(objLst[2]));
                obj.setDistrictId(StringHelperUtils.isNullString(objLst[3]));
                obj.setTalukaId(StringHelperUtils.isNullString(objLst[4]));
                obj.setCity(StringHelperUtils.isNullString(objLst[5]));
                obj.setVillage(StringHelperUtils.isNullString(objLst[6]));
                obj.setAddress(StringHelperUtils.isNullString(objLst[7]));
                obj.setCityClass(StringHelperUtils.isNullString(objLst[8]));
                obj.setPercGrant(StringHelperUtils.isNullString(objLst[9]));
                obj.setTel1(StringHelperUtils.isNullLong(objLst[10]));
                obj.setTel2(StringHelperUtils.isNullLong(objLst[11]));
                obj.setFax(StringHelperUtils.isNullLong(objLst[13]));
                obj.setEmail(StringHelperUtils.isNullString(objLst[12]));
                
                lstObj.add(obj);
            }
           
	}
        return lstObj;
	}

	@Override
	public int SaveorgInstituteInfo(@Valid OrganisationDtlsModel organisationDtlsModel) {
		

		DdoOffice objForSave = new DdoOffice();
		
		objForSave.setDcpsDdoOfficeName(organisationDtlsModel.getOfficeName());
		objForSave.setDcpsDdoOfficeState(organisationDtlsModel.getStateId());
        objForSave.setDcpsDdoOfficeDistrict(organisationDtlsModel.getDistrictId());
        objForSave.setDcpsDdoOfficeTaluka(organisationDtlsModel.getTalukaId());
        objForSave.setDcpsDdoOfficeTown(organisationDtlsModel.getCity());
        objForSave.setDcpsDdoOfficeVillage(organisationDtlsModel.getVillage());
        objForSave.setDcpsDdoOfficeAddress1(organisationDtlsModel.getAddress());
        objForSave.setDcpsDdoOfficeCityClass(organisationDtlsModel.getCityClass());
        objForSave.setDcpsDdoOfficeGrant(organisationDtlsModel.getPercGrant());
        objForSave.setDcpsDdoOfficeTelNo1(organisationDtlsModel.getTel1());
        objForSave.setDcpsDdoOfficeTelNo2(organisationDtlsModel.getTel2());
        objForSave.setDcpsDdoOfficeFax(organisationDtlsModel.getFax());
        objForSave.setDcpsDdoOfficeEmail(organisationDtlsModel.getEmail());
        objForSave.setCreatedDate(new Date());
		
		int saveId=0;
		if(organisationDtlsModel.getDdoCode()==null) {
			 saveId = organisationDtlsRepo.saveorgInstInfo(objForSave);
		}else {
			saveId=5;
			organisationDtlsRepo.updateorgInstituteInfo(objForSave);
		}
		
		return saveId;
		
		
	}


}
