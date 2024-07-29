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
	public List<Object[]> findentry(Integer yearId, Integer monthId, Long billGroup,String ddoCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select distinct a.employee_full_name_en as name,c.basic_pay,c.svn_pc_da,b.pf_ac_no,d.paybill_generation_trn_id,c.nps_emp_contri,\r\n" + 
				"c.nps_empr_deduct,c.nps_empr_allow,a.dcps_no from  employee_mst a left join dcps_details_mst b on b.employee_id=a.employee_id \r\n" + 
				"inner join paybill_generation_trn_details c on c.sevaarth_id=a.sevaarth_id inner join paybill_generation_trn d \r\n" + 
				"on d.paybill_generation_trn_id=c.paybill_generation_trn_id where d.scheme_billgroup_id = '"+billGroup+"'\r\n" + 
				"and  d.paybill_month= "+monthId+" and  d.paybill_year= "+yearId+"  and d.ddo_code='"+ddoCode+"'\r\n" + 
				"and a.dcps_gpf_flag = 'Y'";
		Query query = currentSession.createSQLQuery(HQL);
		System.out.println("HQL:"+HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findbillgrp(String billno) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select * from bill_group_mst   where bill_group_id   ="+billno+" ";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findpaybill(int billNumber, int monthName, int yearName, String ddo) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.paybill_generation_trn_id,a.scheme_billgroup_id,b.bill_group_id from paybill_generation_trn  a\r\n" + 
		"inner join bill_group_mst bb on a.scheme_billgroup_id = bb.bill_group_id\r\n" + 
		"inner join  ddo_reg_mst dd on dd.ddo_code = a.ddo_code  and a.ddo_code='"+ddo+"'\r\n" + 
		"inner join ddo_map_rlt ddd on dd.ddo_reg_id = ddd.ddo_code_user_id1\r\n" + 
		"inner join scheme_billgroup_mpg b on a.scheme_billgroup_id=b.bill_group_id and b.ddo_map_id = ddd.ddo_map_id where "
		+ "a.paybill_month='"+monthName+"'   and a.paybill_year='"+yearName+"'  and a.is_Active in ('1','2','5','6','9','10','11','14') "
				+ "and   b.bill_group_id='"+billNumber+"'";
		
		/*String HQL = "select a.paybill_generation_trn_id,a.scheme_billgroup_id,b.bill_group_id from paybill_generation_trn  a\r\n" + 
				"inner join bill_group_mst bb on a.scheme_billgroup_id = bb.bill_group_id\r\n" + 
				"inner join  ddo_reg_mst dd on dd.ddo_code = a.ddo_code  and a.ddo_code='"+ddo+"'\r\n" + 
				"inner join ddo_map_rlt ddd on dd.ddo_reg_id = ddd.ddo_code_user_id1\r\n" + 
				"inner join scheme_billgroup_mpg b on a.scheme_billgroup_id=b.bill_group_id and b.ddo_map_id = ddd.ddo_map_id where a.is_Active not in (14,8) and b.scheme_billgroup_id='"+billNumber+"'";*/
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
		query.append("select bill_description from paybill_generation_trn a inner join scheme_billgroup_mpg b on a.scheme_billgroup_id=b.bill_group_id\r\n" + 
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
		String HQL = "select a.sevaarth_id||' '||a.employee_full_name_en as name,c.basic_pay,c.svn_pc_da,b.pf_ac_no,d.paybill_generation_trn_id from  employee_mst a \r\n" + 
				"left join dcps_details_mst b on b.employee_id=a.employee_id \r\n" + 
				"inner join paybill_generation_trn_details c on c.sevaarth_id=a.sevaarth_id \r\n" + 
				"inner join paybill_generation_trn d on d.paybill_generation_trn_id=c.paybill_generation_trn_id \r\n" + 
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
