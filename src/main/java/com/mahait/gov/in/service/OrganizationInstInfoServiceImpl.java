package com.mahait.gov.in.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.repository.OrganizationInstInfoRepo;

@Service
@Transactional
public class OrganizationInstInfoServiceImpl implements OrganizationInstInfoService {
	
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private OrganizationInstInfoRepo organizationInstInfoRepo;

	@Override
	public List<OrgDdoMst> findDDOInfo(String userName) {
		// TODO Auto-generated method stub
		return organizationInstInfoRepo.findDDOInfo(userName);
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
		objForSave.setDeptLocCode(orgDdoMstModel.getParentAdminDepartmentId());
		objForSave.setHodLocCode(orgDdoMstModel.getParentFieldDepartmentId());
		objForSave.setInstituteTypeId(orgDdoMstModel.getInstituteType());
		objForSave.setDdoPersonalName(orgDdoMstModel.getDdoName());
		objForSave.setDsgnName(orgDdoMstModel.getDesignName());
		objForSave.setStartDate(orgDdoMstModel.getStartDate());
		
		objForSave.setTanNo(orgDdoMstModel.getTanNo());
		objForSave.setItawardcircle(orgDdoMstModel.getItoWardCircle());
		objForSave.setBankName(orgDdoMstModel.getBankId());
		objForSave.setBranchName(orgDdoMstModel.getBranchName());
		
		objForSave.setIfsCode(orgDdoMstModel.getIfsCode());
		objForSave.setAccountNo(orgDdoMstModel.getAccountNo());
		objForSave.setRemarks(orgDdoMstModel.getRemarks());
		objForSave.setDdoCode(orgDdoMstModel.getDdoCode());
		objForSave.setCreatedDate(new Date());
		//objForSave.setIsActive('1');
		
		int saveId=0;
		if(orgDdoMstModel.getDdoId()==null || orgDdoMstModel.getDdoId()<=0) {
			 saveId = organizationInstInfoRepo.saveorgInstInfo(objForSave);
		}else {
			saveId=5;
			organizationInstInfoRepo.updateDDOInfo(objForSave);
		}
		
		return saveId;
	}

	@Override
	public int saveEditOrgInstInfo(OrgDdoMstModel orgDdoMstModel) {
		OrgDdoMst objForSave = new OrgDdoMst();
		objForSave.setDeptLocCode(orgDdoMstModel.getParentAdminDepartmentId());
		objForSave.setHodLocCode(orgDdoMstModel.getParentFieldDepartmentId());
		objForSave.setInstituteTypeId(orgDdoMstModel.getInstituteType());
		objForSave.setDdoPersonalName(orgDdoMstModel.getDdoName());
		objForSave.setDsgnName(orgDdoMstModel.getDesignName());
		objForSave.setStartDate(orgDdoMstModel.getStartDate());
		
		objForSave.setTanNo(orgDdoMstModel.getTanNo());
		objForSave.setItawardcircle(orgDdoMstModel.getItoWardCircle());
		objForSave.setBankName(orgDdoMstModel.getBankId());
		objForSave.setBranchName(orgDdoMstModel.getBranchName());
		
		objForSave.setIfsCode(orgDdoMstModel.getIfsCode());
		objForSave.setAccountNo(orgDdoMstModel.getAccountNo());
		objForSave.setRemarks(orgDdoMstModel.getRemarks());
		objForSave.setDdoCode(orgDdoMstModel.getDdoCode());
		objForSave.setCreatedDate(new Date());
		//objForSave.setIsActive('1');
		
		int saveId=0;
		if(orgDdoMstModel.getDdoId()==null || orgDdoMstModel.getDdoId()<=0) {
			 saveId = organizationInstInfoRepo.saveorgInstInfo(objForSave);
		}else {
			saveId=5;
			organizationInstInfoRepo.updateDDOInfo(objForSave);
		}
		
		return saveId;
	}
	

	

	
}
