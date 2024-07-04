package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.MstScheme;
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
