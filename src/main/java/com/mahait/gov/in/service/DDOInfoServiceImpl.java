package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.model.ApproveDDOHstModel;
import com.mahait.gov.in.repository.DDOInfoRepo;

@Service
@Transactional
public class DDOInfoServiceImpl implements DDOInfoService{

	
	@Autowired
	DDOInfoRepo ddoInfoRepo;

	@Override
	public String getDdoCodeForDDO(Long postId) {
		// TODO Auto-generated method stub
		return ddoInfoRepo.getDdoCodeForDDO(postId);
	}

	@Override
	public  List <ApproveDDOHstModel> getLevel1DDOList(String lStrDdoCode) {
		

		List<Object[]> lstprop = ddoInfoRepo.getLevel1DDOList(lStrDdoCode);
		List<ApproveDDOHstModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				ApproveDDOHstModel obj = new ApproveDDOHstModel();
			/*	obj.setDdoregid(StringHelperUtils.isNullInt(objLst[0]));
				obj.setOrginstname(StringHelperUtils.isNullString(objLst[1]));
				obj.setAddress(StringHelperUtils.isNullString(objLst[2]));
				obj.setDdoName(StringHelperUtils.isNullString(objLst[3]));*/

				lstObj.add(obj);
			}
		}
		return lstObj;
	
		// TODO Auto-generated method stub
		///return ddoInfoRepo.getLevel1DDOList(lStrDdoCode);
	}

	@Override
	public  List <Object[]> getDDoHistoryDetailsForApprove(String ddo) {
		// TODO Auto-generated method stub
		return ddoInfoRepo.getDDoHistoryDetailsForApprove(ddo);
	}
	
	

}
