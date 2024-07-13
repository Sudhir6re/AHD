package com.mahait.gov.in.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.RltDCPSDdoSchemeEntity;
import com.mahait.gov.in.entity.RltDcpsDdoScheme;
import com.mahait.gov.in.model.MstSchemeModel;
import com.mahait.gov.in.repository.MstSchemeRepo;
@Service
@Transactional
public class MstSchemeServiceImpl implements MstSchemeService{

	@Autowired
	private MstSchemeRepo mstSchemeRepo;

	
	public List<MstSchemeModel> findAllScheme() {
		
		
		return mstSchemeRepo.findAllSchemename();
	}


	@Override
	public List<MstDcpsBillGroup> findAllMpgSchemeBillGroupByDDOCode(String DDOCode, int roleid) {
		// TODO Auto-generated method stub
		return mstSchemeRepo.findAllMpgSchemeBillGroupByDDOCode(DDOCode,roleid);
	}


	@Override
	public BigInteger findNumberOfEmployeeInBillGroup(String logUser, BigInteger schemeBillGroupId, int monthName,
			int yearName, int paybillType) {
		// TODO Auto-generated method stub
		return mstSchemeRepo.findNumberOfEmployeeInBillGroup(logUser,schemeBillGroupId,monthName,yearName,paybillType);
	}


	@Override
	public List<MstScheme> findAllSchemeDetails(String data) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<MstSchemeModel> findAllScheme(String username) {
		// TODO Auto-generated method stub
		return mstSchemeRepo.findAllSchemename(username);
		//Map<Integer, String> mpFinancialYearList=mstSchemeRepo.getFinYear("");
//		List<MstSchemeModel> lstSchemeModel=new ArrayList<MstSchemeModel>();
//		List<MstScheme> lstSchemeEntity=mstSchemeRepo.findAllScheme();
//		for (Iterator iterator = lstSchemeEntity.iterator(); iterator.hasNext();) {
//			MstScheme mstSchemeEntity = (MstScheme) iterator.next();
//			String strFinYear="";
//			MstSchemeModel mstSchemeModel=new MstSchemeModel();
//			mstSchemeModel.setSchemeId(mstSchemeEntity.getSchemeId());
//			mstSchemeModel.setDcpsDdoSchemeCode(mstSchemeEntity.getSchemeCode());
//			mstSchemeModel.setSchemeName(mstSchemeEntity.getSchemeName());
//			mstSchemeModel.setMajorHead(mstSchemeEntity.getMajorHead());
//			mstSchemeModel.setSubMajorHead(mstSchemeEntity.getSubMajorHead());
//			mstSchemeModel.setMinorHead(mstSchemeEntity.getMinorHead());
//			mstSchemeModel.setSubMinorHead(mstSchemeEntity.getSubMinorHead());
//			mstSchemeModel.setSubHead(mstSchemeEntity.getSubHead());
//			mstSchemeModel.setDemandCode(mstSchemeEntity.getDemandCode());
//			mstSchemeModel.setSchemeType(mstSchemeEntity.getSchemeType());
//			
//			lstSchemeModel.add(mstSchemeModel);
//		}
//		return lstSchemeModel;
	}


}
