package com.mahait.gov.in.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgDdoMst;

@Repository
public class RegularReportRepoImpl implements RegularReportRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<OrgDdoMst> getDDOName(String userName) {
		String HQL = "FROM OrgDdoMst as t  where ddoCode='"+userName+"' and t.activateFlag='1' ORDER BY t.ddoId";
		return (List<OrgDdoMst>) entityManager.createQuery(HQL).getResultList();
	}

	@Override
	public List<Object[]> findDCPSRegularEmpLst(Integer yearId, Integer monthId, Long billGroup,String ddoCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select  a.employee_full_name_en,a.pran_no,b.basic_pay,b.dearness_pay,case when a.pay_commission_code =700005 then " + 
				"b.svn_pc_da else da end as DA, b.dcps_regular,nps_empr_deduct,c.paybill_month,c.paybill_year from employee_mst a " + 
				" inner join paybill_generation_trn_details b on  a.sevaarth_id=b.sevaarth_id " + 
				" inner join paybill_generation_trn c on b.paybill_generation_trn_id=c.paybill_generation_trn_id " + 
				" where c.scheme_billgroup_id = '"+billGroup+"' " + 
				" and  c.paybill_month= "+monthId+" and  c.paybill_year= "+yearId+"  and c.ddo_code='"+ddoCode+"'" + 
				" and a.dcps_gpf_flag = 'Y' and c.is_active in (5,6,9,11,14) ";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println("HQL:"+HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findbillgrp(Long billno) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from mst_dcps_bill_group where bill_group_id='"+billno+"'";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findpaybill(Long billNumber, int monthName, int yearName, String ddo) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = " select a.paybill_generation_trn_id,a.scheme_billgroup_id,bb.bill_group_id from paybill_generation_trn  a" + 
				" inner join mst_dcps_bill_group bb on a.scheme_billgroup_id = bb.bill_group_id" + 
				" inner join  org_ddo_mst dd on dd.ddo_code = a.ddo_code  and a.ddo_code='"+ddo+"'" + 
				" inner join rlt_zp_ddo_map ddd on dd.ddo_code = ddd.zp_ddo_code" + 
				" where a.paybill_month="+monthName+"   and a.paybill_year="+yearName+"  and a.is_Active in ('1','2','5','6','9','10','11','14')" + 
				" and   bb.bill_group_id='"+billNumber+"'";
		System.out.println("Findpaybill Quer +++++"+HQL);
		
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public String getbillGroup(int billnumber) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("select bill_description from paybill_generation_trn a inner join scheme_billgroup_mpg b on a.scheme_billgroup_id=b.bill_group_id" + 
				"where paybill_generation_trn_id="+billnumber);
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();

		if (list != null && list.size() > 0)
			rtnStr = list.get(0).toString();

		return rtnStr;
	}

	@Override
	public List<Object[]> checktheEntryForForm2Regular(int billNumber, int monthName, int yearName, String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.sevaarth_id||' '||a.employee_full_name_en as name,c.basic_pay,c.svn_pc_da,b.pf_ac_no,d.paybill_generation_trn_id from  employee_mst a " + 
				"left join dcps_details_mst b on b.employee_id=a.employee_id " + 
				"inner join paybill_generation_trn_details c on c.sevaarth_id=a.sevaarth_id " + 
				"inner join paybill_generation_trn d on d.paybill_generation_trn_id=c.paybill_generation_trn_id " + 
				"where d.scheme_billgroup_id = " + billNumber + " and d.paybill_month= " + monthName + "and d.paybill_year= '" + yearName + "' and d.is_active=14" ;
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println("HQL-"+HQL);
		return query.list();
	}

	@Override
	public List<MstDcpsBillGroup> lstBillDesc(String ddoCode) {
		String HQL = "FROM MstDcpsBillGroup as t  where dcpsDdoCode='"+ddoCode+"' ORDER BY t.dcpsDdoBillGroupId";
		return (List<MstDcpsBillGroup>) entityManager.createQuery(HQL).getResultList();
	}
	
	

}
