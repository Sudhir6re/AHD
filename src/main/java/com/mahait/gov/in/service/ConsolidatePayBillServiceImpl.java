package com.mahait.gov.in.service;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnMpgEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.PaybillGenerationTrnDetails;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.model.ConsolidatePayBillModel;
import com.mahait.gov.in.repository.ConsolidatePayBillRepo;

@Service
@Transactional
public class ConsolidatePayBillServiceImpl implements ConsolidatePayBillService {

	@Autowired
	ConsolidatePayBillRepo consolidatePayBillRepo;

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

	@Override
	public List<ConsolidatePayBillModel> searchConsolidatedPaybill(ConsolidatePayBillModel consolidatePayBillModel,
			OrgUserMst messages) {
		
		List<ConsolidatePayBillModel> lstObj = new ArrayList<>();
		
		for(ConsolidatePayBillModel model : consolidatePayBillModel.getLstCons()) {
			if(model.getCheckboxid()!=null) {
			if(model.getCheckboxid()==true) {
				
				List<Object[]> lstprop = consolidatePayBillRepo.fetchConsolidateDetails(model.getDdoCode(),consolidatePayBillModel.getMonthName(),consolidatePayBillModel.getYearName(),consolidatePayBillModel.getSchemeCode());
					if (!lstprop.isEmpty()) {
						for (Object[] objLst : lstprop) {
							ConsolidatePayBillModel obj = new ConsolidatePayBillModel();
							obj.setDdoCode(StringHelperUtils.isNullString(objLst[0]));
							BigInteger b=(BigInteger) objLst[1];
							Integer billNo= b.intValue();
							obj.setBillNo(billNo);
							obj.setBillDesc(StringHelperUtils.isNullString(objLst[2]));
							obj.setBillGrossAmt(StringHelperUtils.isNullDouble(objLst[3]));
							obj.setBillNetAmt(StringHelperUtils.isNullDouble(objLst[4]));
							lstObj.add(obj);
						}
					}
				}
			}
			}
		return lstObj;
		
		


	}

	@Override
	public int saveConsolidatePayBill(ConsolidatePayBillModel consolidatePayBillModel, OrgUserMst messages) {
		
		Serializable id =0;
		Double sumGrossAmount=0d;
		Double sumNetAmount=0d;
		Double sumItAmount=0d;
		Double sumptAmount=0d;
		Double sumgisAmount=0d;
		Double sumhrrAmount=0d;
		Double sumtotalDeducAmount=0d;
		Double sumProvFundAmount=0d;
		Double sumAccPolicyAmount=0d;
		Double sumdcpsArrAmount=0d;
		
		ConsolidatePayBillTrnMpgEntity consolidateTrnMpg = new ConsolidatePayBillTrnMpgEntity();
		for (ConsolidatePayBillModel model : consolidatePayBillModel.getLstCons()) {
			
			sumGrossAmount +=model.getBillGrossAmt();
			sumNetAmount +=model.getBillNetAmt();
			
			List<PaybillGenerationTrnEntity> lst =consolidatePayBillRepo.getPaybillDtls(consolidatePayBillModel.getMonthName(),consolidatePayBillModel.getYearName(),model.getDdoCode());
			
			for (PaybillGenerationTrnEntity paybillGenerationTrnEntity : lst) {
				
				List<Object[]> lstpaybilldtls = consolidatePayBillRepo.fetchbilldts(lst.get(0).getPaybillGenerationTrnId()); 
				
				for (Object[] obj : lstpaybilldtls) {
					
					ConsolidatePayBillTrnEntity objEntity = new ConsolidatePayBillTrnEntity();
///Sum(total_deduction)as TotalDeduct, SUM(COALESCE(gpf_grp_abc,0) + COALESCE(GPF_ADV_GRP_ABC,0)+COALESCE(GPF_ABC_ARR,0)+COALESCE(gpf_grp_d,0)+COALESCE(GPF_ADV_GRP_D,0)
//+COALESCE(GPF_D_ARR,0))as prov_fund
					consolidateTrnMpg.setConsolidatePaybillTrnId(consolidatePayBillRepo.getConsolidateTrnId());
					consolidateTrnMpg.setDdoCode(model.getDdoCode());
					consolidateTrnMpg.setPaybillGenerationTrnId(paybillGenerationTrnEntity.getPaybillGenerationTrnId());
					if(obj[0]!=null)
					objEntity.setIt(StringHelperUtils.isNullDouble(obj[0]));
					if(obj[1]!=null)
					objEntity.setDcpsArr(StringHelperUtils.isNullDouble(obj[1]));
					if(obj[2]!=null)
					objEntity.setGis(StringHelperUtils.isNullDouble(obj[2]));
					if(obj[3]!=null)
					objEntity.setPt(StringHelperUtils.isNullDouble(obj[3]));
					if(obj[4]!=null)
					objEntity.setAccPolicy(StringHelperUtils.isNullDouble(obj[4]));
					if(obj[5]!=null)
					objEntity.setHrr(StringHelperUtils.isNullDouble(obj[5]));
					if(obj[6]!=null)
					objEntity.setTotalDeduct(StringHelperUtils.isNullDouble(obj[6]));
					if(obj[7]!=null)
					objEntity.setPf(StringHelperUtils.isNullDouble(obj[7]));
					objEntity.setCreatedDate(new Date());
					objEntity.setCreatedUserId(messages.getUserId());
					objEntity.setIsActive(9);
					
					paybillGenerationTrnEntity.setIsActive(9);
					id = consolidatePayBillRepo.saveConsolidatePayBill(objEntity);
					id = consolidatePayBillRepo.saveConsolidatePayBillTrnMpg(consolidateTrnMpg);
					id = consolidatePayBillRepo.saveDtlsPaybillTrn(paybillGenerationTrnEntity);
				}
			}
			
		}
		
		
		// TODO Auto-generated method stub
		return 0;
	}

}
