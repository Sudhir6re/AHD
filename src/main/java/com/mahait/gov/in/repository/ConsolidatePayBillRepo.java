package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnMpgEntity;

public interface ConsolidatePayBillRepo {
	public int saveConsolidatePayBill(ConsolidatePayBillTrnEntity objEntity);
	/*public int getConsolidatePayBillTrnId();*/
	public int saveConsolidatePayBillTrnMpg(ConsolidatePayBillTrnMpgEntity objEntity);
	//List<Object[]> addConsComponents(String ddoCode,int paybillGenerationTransId);
	List<Object[]> addConsComponents(String ddoCode, List<Integer> payBillGenerationTransId);
	public List<Object[]> fetchDDOLst(String ddoCode);

}
