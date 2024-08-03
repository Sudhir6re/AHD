package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnMpgEntity;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;

public interface ConsolidatePayBillRepo {
	public int saveConsolidatePayBill(ConsolidatePayBillTrnEntity objEntity);
	public int saveConsolidatePayBillTrnMpg(ConsolidatePayBillTrnMpgEntity objEntity);
	List<Object[]> addConsComponents(String ddoCode, List<Integer> payBillGenerationTransId);
	public List<Object[]> fetchDDOLst(String ddoCode);
	public List<Object[]> fetchConsolidateDetails(String ddoCode, Integer monthId, Integer yearId, String schemeCode);
	public List<PaybillGenerationTrnEntity> getPaybillDtls(Integer monthName, Integer yearName, String ddoCode);
	public List<Object[]> fetchbilldts(Long paybillGenerationTrnId);
	Long getConsolidateTrnId();
	public Serializable saveDtlsPaybillTrn(PaybillGenerationTrnEntity paybillGenerationTrnEntity);

}
