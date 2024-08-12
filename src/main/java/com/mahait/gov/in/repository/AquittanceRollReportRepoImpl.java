package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AquittanceRollReportRepoImpl implements AquittanceRollReportRepo{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findAquittanceReportDtls(int monthName, int yearName, String ddoCode, Long paybillId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select emp.employee_full_name_en||' ('||  emp.sevaarth_id ||' )'|| deg.designation_name as employee,billdtl.total_net_amt,billdtl.total_deduction from paybill_generation_trn  bill "
				+ " inner join paybill_generation_trn_details billdtl ON billdtl.paybill_generation_trn_id=bill.paybill_generation_trn_id  inner join employee_mst  emp  on billdtl.sevaarth_id = emp.sevaarth_id "
				+ " inner join designation_mst as deg on deg.designation_code=emp.designation_code where bill.paybill_month= "+monthName+" and bill.paybill_year="+yearName+" and bill.ddo_code='"+ddoCode+"'and bill.paybill_generation_trn_id ='"+paybillId+"' " ;
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

}
