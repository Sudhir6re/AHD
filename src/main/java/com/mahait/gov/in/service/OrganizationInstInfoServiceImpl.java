package com.mahait.gov.in.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.model.MstDesnModel;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.repository.OrganizationInstInfoRepo;

@Service
@Transactional
public class OrganizationInstInfoServiceImpl implements OrganizationInstInfoService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private OrganizationInstInfoRepo organizationInstInfoRepo;

	@Override
	public OrgDdoMstModel findDDOInfo(String userName) {

		OrgDdoMst lstprop = organizationInstInfoRepo.findDDOInfo(userName);
		OrgDdoMstModel orgDdoMstModel = new OrgDdoMstModel();
       if(lstprop!=null) {
    	   orgDdoMstModel.setDdoOffice(lstprop.getDdoOffice());
    	   orgDdoMstModel.setDeptLocCode(lstprop.getDsgnCode());
    	   orgDdoMstModel.setStartDate(lstprop.getStartDate());
    	   orgDdoMstModel.setTanNo(lstprop.getTanNo());
    	   orgDdoMstModel.setItaWardNo(lstprop.getItawardcircle());
    	   orgDdoMstModel.setBankName(lstprop.getBankName());
    	   orgDdoMstModel.setBranchName(lstprop.getBranchName());
    	   orgDdoMstModel.setIfsCode(lstprop.getIfsCode());
    	   orgDdoMstModel.setAccountNo(lstprop.getAccountNo());
    	   orgDdoMstModel.setRemarks(lstprop.getRemarks());
    	   orgDdoMstModel.setInstituteType(lstprop.getInstituteTypeId());
       }   
            
        
        return orgDdoMstModel;
	}

	@Override
	public List<InstituteType> lstInstType() {
		// TODO Auto-generated method stub
		return organizationInstInfoRepo.lstInstType();
	}

	@Override
	public List<Object[]> getBankBranch(String valueOf) {
		// TODO Auto-generated method stub
		List<Object[]> lstbranchname = organizationInstInfoRepo.getBankBranch(valueOf);
		return lstbranchname;
	}

	@Override
	public int SaveorgInstituteInfo(@Valid OrgDdoMstModel orgDdoMstModel) {
		OrgDdoMst objForSave = new OrgDdoMst();
		
		
		objForSave.setDdoOffice(orgDdoMstModel.getDdoOffice());
		objForSave.setDdoId(orgDdoMstModel.getDdoId());
		objForSave.setStartDate(orgDdoMstModel.getStartDate());
		objForSave.setTanNo(orgDdoMstModel.getTanNo());
		objForSave.setItawardcircle(orgDdoMstModel.getItaWardNo());
		objForSave.setBankName(orgDdoMstModel.getBankName());
		objForSave.setBranchName(orgDdoMstModel.getBranchName());
		objForSave.setIfsCode(orgDdoMstModel.getIfsCode());
		objForSave.setAccountNo(orgDdoMstModel.getAccountNo());
		objForSave.setRemarks(orgDdoMstModel.getRemarks());
		objForSave.setInstituteTypeId(orgDdoMstModel.getInstituteType());
		
		
		/*objForSave.setDdoOffice(orgDdoMstModel.getDdoOffice());
       // objForSave.setState(StringHelperUtils.isNullInt(ďdoOfficeModel.getState()));
        objForSave.setDistrict(orgDdoMstModel.getDistrict());
        objForSave.setTaluka(orgDdoMstModel.getTaluka());
        objForSave.setTown(orgDdoMstModel.getCity());
        objForSave.setVillage(orgDdoMstModel.getVillage());
        objForSave.setAddress1(orgDdoMstModel.getAddress());
        objForSave.setOfficeCityClass(orgDdoMstModel.getCityClass());
        objForSave.setGrantApplicable(orgDdoMstModel.getPercGrant());
        objForSave.setTelNo1(orgDdoMstModel.getTel1());
        objForSave.setTelNo2(orgDdoMstModel.getTel2());
        objForSave.setFax(orgDdoMstModel.getFax());
        objForSave.setEmail(orgDdoMstModel.getEmail());
        objForSave.setCreatedDate(new Date());*/
		
		int saveId=0;
		 
			 saveId = organizationInstInfoRepo.saveorgInstituteInfo(objForSave);
		
		
		return saveId;
	}

	@Override
	public int updateorgInstituteInfo(OrgDdoMstModel orgDdoMstModel) {
		
		String[] ddo = orgDdoMstModel.getDdoCode().split("_");
		
		String ddoCode = ddo[0]; 
		
		OrgDdoMst findDDOInfo = organizationInstInfoRepo.findDDOInfo(ddoCode);
		findDDOInfo.setDdoOffice(orgDdoMstModel.getDdoOffice());
		//findDDOInfo.setDdoId(orgDdoMstModel.getDdoId());
		findDDOInfo.setStartDate(orgDdoMstModel.getStartDate());
		findDDOInfo.setTanNo(orgDdoMstModel.getTanNo());
		findDDOInfo.setItawardcircle(orgDdoMstModel.getItaWardNo());
		findDDOInfo.setBankName(orgDdoMstModel.getBankName());
		findDDOInfo.setBranchName(orgDdoMstModel.getBranchName());
		findDDOInfo.setIfsCode(orgDdoMstModel.getIfsCode());
		findDDOInfo.setAccountNo(orgDdoMstModel.getAccountNo());
	  	 findDDOInfo.setRemarks(orgDdoMstModel.getRemarks());
	  	 findDDOInfo.setInstituteTypeId(orgDdoMstModel.getInstituteType());
		
		/*objForSave.setDdoOffice(orgDdoMstModel.getOfficeName());
       // objForSave.setState(StringHelperUtils.isNullInt(ďdoOfficeModel.getState()));
        objForSave.setDistrict(orgDdoMstModel.getDistrict());
        objForSave.setTaluka(orgDdoMstModel.getTaluka());
        objForSave.setTown(orgDdoMstModel.getCity());
        objForSave.setVillage(orgDdoMstModel.getVillage());
        objForSave.setAddress1(orgDdoMstModel.getAddress());
        objForSave.setOfficeCityClass(orgDdoMstModel.getCityClass());
        objForSave.setGrantApplicable(orgDdoMstModel.getPercGrant());
        objForSave.setTelNo1(orgDdoMstModel.getTel1());
        objForSave.setTelNo2(orgDdoMstModel.getTel2());
        objForSave.setFax(orgDdoMstModel.getFax());
        objForSave.setEmail(orgDdoMstModel.getEmail());
        objForSave.setCreatedDate(new Date());*/
		organizationInstInfoRepo.updateorgInstituteInfo(findDDOInfo);
	
		
		return 10;
	}
	

	

	
}
