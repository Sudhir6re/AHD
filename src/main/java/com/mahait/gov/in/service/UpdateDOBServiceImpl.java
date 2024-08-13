package com.mahait.gov.in.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdateDOBModel;
import com.mahait.gov.in.model.UpdatePanNoModel;
import com.mahait.gov.in.repository.UpdateDOBRepo;

@Service
@Transactional
public class UpdateDOBServiceImpl implements UpdateDOBService {
	
	@Autowired
	UpdateDOBRepo updateDOBRepo;

	@Override
	public List<UpdateDOBModel> findAllEmployee(String userName) {
		List<Object[]> list = updateDOBRepo.findAllEmployee(userName);
		
		List<UpdateDOBModel> listobj = new ArrayList<>();
		if(!list.isEmpty())
		{
			for(Object[] obj:list ) //for (Object[] objLst : lstprop) {
			{
				UpdateDOBModel obj1 = new UpdateDOBModel();
				obj1.setEmployeeId(StringHelperUtils.isNullBigInteger(obj[0]).longValue());
				obj1.setSevaarthId(StringHelperUtils.isNullString(obj[1]));
				obj1.setEmployeeFullNameEn(StringHelperUtils.isNullString(obj[2]));
                obj1.setDob(StringHelperUtils.isNullDate(obj[3]));
                obj1.setDesignation(StringHelperUtils.isNullString(obj[4]));
				
				listobj.add(obj1);
			}
		}
		return listobj;
	}

	@Override
	public int saveupdatedob(@Valid UpdateDOBModel updateDOBModel, OrgUserMst messages) {
		int id = 0;
		int i= 0;
			// TODO Auto-generated method stub
			for(UpdateDOBModel model : updateDOBModel.getEmplist()) {
				if(model.isCheckboxid()==true) {
					MstEmployeeEntity mstEmployeeEntity = updateDOBRepo.findEmpData(model.getEmployeeId());
					

					mstEmployeeEntity.setDob(model.getDob());
					mstEmployeeEntity.setUpdatedDate(new Date());
					
					
					Serializable  saveChangeBasicdtls= updateDOBRepo.saveupdatedob(mstEmployeeEntity);	
					id = (int) saveChangeBasicdtls;
					i++;
					
				}
			
			}
				return  id;
				
		     
	}

}
