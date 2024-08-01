package com.mahait.gov.in.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnMpgEntity;
import com.mahait.gov.in.model.ConsolidatePayBillModel;
import com.mahait.gov.in.repository.ConsolidatePayBillRepo;
import com.mahait.gov.in.common.StringHelperUtils;

@Service
@Transactional
public class ConsolidatePayBillServiceImpl implements ConsolidatePayBillService {

	@Autowired
	ConsolidatePayBillRepo consolidatePayBillRepo;

	@Override
	public int saveConsolidatePayBill(ConsolidatePayBillTrnEntity consolidatePayBillTrnEntity) {

		ConsolidatePayBillTrnEntity objEntity = new ConsolidatePayBillTrnEntity();
		objEntity.setSchemeCode(consolidatePayBillTrnEntity.getSchemeCode());
		objEntity.setPaybillMonth(consolidatePayBillTrnEntity.getPaybillMonth());
		objEntity.setPaybillYear(consolidatePayBillTrnEntity.getPaybillYear());

		objEntity.setGrossAmt(consolidatePayBillTrnEntity.getGrossAmt());
		objEntity.setCreatedUserId(consolidatePayBillTrnEntity.getCreatedUserId());
		objEntity.setDdoCode(consolidatePayBillTrnEntity.getDdoCode());
		objEntity.setNetAmt(consolidatePayBillTrnEntity.getNetAmt());
		/*
		 * consolidatePayBillTrnEntity.setCreatedDate(new Date());
		 * consolidatePayBillTrnEntity.setCreatedDate(new Date());
		 * consolidatePayBillTrnEntity.setCreatedDate(new Date());
		 * 
		 * consolidatePayBillTrnEntity.setStatus(9);
		 */

		Serializable id = consolidatePayBillRepo.saveConsolidatePayBill(consolidatePayBillTrnEntity);

		return (int) id;
	}

	@Override
	public int saveConsolidatePaybillMpgDetails(Long paybillGenerationTrnId, String ddoCode, Long Id) {
		ConsolidatePayBillTrnMpgEntity objEntity = new ConsolidatePayBillTrnMpgEntity();
		objEntity.setDdoCode(ddoCode);
		objEntity.setPaybillGenerationTrnId(paybillGenerationTrnId);
		objEntity.setConsolidatePaybillTrnId(Id);

		Serializable id = consolidatePayBillRepo.saveConsolidatePayBillTrnMpg(objEntity);

		return (int) id;
	}

	public List<Object[]> addConsComponents(String ddoCode, List<Integer> payBillGenerationTransId) {

		return consolidatePayBillRepo.addConsComponents(ddoCode, payBillGenerationTransId);
	}

	@Override
	public int saveConsolidatePaybillMpgDetails(Long paybillGenerationTransactionId, String ddoCode, int Id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ConsolidatePayBillModel> fetchDDOLst(String ddoCode) {

		List<Object[]> lstprop = consolidatePayBillRepo.fetchDDOLst(ddoCode);
		List<ConsolidatePayBillModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				ConsolidatePayBillModel obj = new ConsolidatePayBillModel();
				// c.ddo_code,a.off_name
				obj.setDdoCode(StringHelperUtils.isNullString(objLst[0]));
				obj.setOfficeName(StringHelperUtils.isNullString(objLst[1]));

				lstObj.add(obj);
			}
		}
		return lstObj;

	}

}
