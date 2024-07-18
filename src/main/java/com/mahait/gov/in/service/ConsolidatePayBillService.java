package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;

public interface ConsolidatePayBillService {
	
	public int saveConsolidatePayBill(ConsolidatePayBillTrnEntity consolidatePayBillTrnEntity);
	
	public int saveConsolidatePaybillMpgDetails(Long paybillGenerationTransactionId, String ddoCode,int Id);
	
	public  List<Object[]> addConsComponents(String ddoCode,List<Integer> payBillGenerationTransId);

	int saveConsolidatePaybillMpgDetails(Long paybillGenerationTrnId, String ddoCode, Long Id);

}
