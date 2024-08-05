package com.mahait.gov.in.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.model.LstConsolidatedPayBillModel;
import com.mahait.gov.in.repository.ViewConsolidatePayBillRepo;

@Service
@Transactional
public class ViewDelConsolidatePayBillServiceImpl implements ViewDelConsolidatePayBillService{
//	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	ViewConsolidatePayBillRepo viewConsolidatePayBillRepo;
	
		@Override
	public List<LstConsolidatedPayBillModel> viewDelconsolidatePayBill(int monthName,int yearName,String ddoCode) {
		List<LstConsolidatedPayBillModel> lstObj = new ArrayList<>();
			List<Object[]> lstConsolidatedBillList = viewConsolidatePayBillRepo.findAllConsolidatedPaybillList(monthName,yearName,ddoCode);
			lstObj = new ArrayList<>();
			if (!lstConsolidatedBillList.isEmpty()) {
				
				for (Object[] objLst : lstConsolidatedBillList) {
					LstConsolidatedPayBillModel obj = new LstConsolidatedPayBillModel();
					obj.setConsolidatePayBillTrnId(StringHelperUtils.isNullInt(objLst[0]));
					obj.setSchemeCode(StringHelperUtils.isNullString(objLst[1]));
					obj.setSchemeName(StringHelperUtils.isNullString(objLst[2]));
					obj.setBillGrossAmt(StringHelperUtils.isNullDouble(objLst[3]));
					obj.setBillNetAmount(StringHelperUtils.isNullDouble(objLst[4]));
					obj.setIsActive(StringHelperUtils.isNullChar(objLst[5]));
					lstObj.add(obj);
				}
			}		
		return lstObj;
	}
		//Searching Consolidate Against schemeName//
		@Override
		public List<Object[]> findAllConsolidatedPaybillListUsingFilter(int monthName,int yearName,String schemeCodeArr,int afterSaveId,String ddoName) {
			List<LstConsolidatedPayBillModel> lstObj = new ArrayList<>();
			List<Object[]> lstConsolidatedBillList = viewConsolidatePayBillRepo.findAllConsolidatedPaybillListUsingFilter(monthName,yearName,schemeCodeArr,afterSaveId,ddoName);
			return lstConsolidatedBillList;
		}
		//Searching Consolidate paybill without filter//
		@Override
		public List<Object[]> findAllConsolidatedPaybillListWithoutFilter(int monthName,int yearName,int afterSaveId,String ddoName) {
			List<LstConsolidatedPayBillModel> lstObj = new ArrayList<>();
			List<Object[]> lstConsolidatedBillList = viewConsolidatePayBillRepo.findAllConsolidatedPaybillListWithoutFilter(monthName,yearName,afterSaveId,ddoName);
			return lstConsolidatedBillList;
		}

		//for delete the consolidate paybill
		
		@Override
		public ConsolidatePayBillTrnEntity findDeleteBillById(int consPaybillGenerationTrnId) {
			ConsolidatePayBillTrnEntity objConsolidatePayBillTrnEntity = viewConsolidatePayBillRepo
					.updateConsolidateStatusById(consPaybillGenerationTrnId);
			/*PaybillGenerationTrnEntity paybillGenerationTrnEntity = new PaybillGenerationTrnEntity();*/
			/*PaybillGenerationTrnEntity lstConsolidatedBillList = viewConsolidatePayBillRepo.findAllConsolidatedPaybill(consPaybillGenerationTrnId);*/
			Integer lstConsolidatedBillList =viewConsolidatePayBillRepo.findConsolidatedPaybillNumber(consPaybillGenerationTrnId);
			
		/*	logger.info("findDeleteBillById"+lstConsolidatedBillList);
			if(lstConsolidatedBillList !=null){
				lstConsolidatedBillList.setIsActive('6');*/
				
			/*}*/
			
			if (objConsolidatePayBillTrnEntity != null) {
				objConsolidatePayBillTrnEntity.setStatus(13); // Deleted
				objConsolidatePayBillTrnEntity.setIsActive(13); // Deleted
				objConsolidatePayBillTrnEntity.setUpdatedDate(new Date());
				viewConsolidatePayBillRepo.updateConsolidateStatus(objConsolidatePayBillTrnEntity);
				int consolatedNo=viewConsolidatePayBillRepo.updatePaybillStatus(consPaybillGenerationTrnId);
			}
			
			
			return objConsolidatePayBillTrnEntity;
		}
		
}
