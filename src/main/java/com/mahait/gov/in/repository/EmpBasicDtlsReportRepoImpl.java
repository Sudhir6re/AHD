package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstEmployeeEntity;

@Repository
public class EmpBasicDtlsReportRepoImpl implements EmpBasicDtlsReportRepo{

	@PersistenceContext
	EntityManager manager;
	
	@Override
	public List<MstEmployeeEntity> lstDDOWiseEmp(String ddoCode) {
		String HQL = "FROM MstEmployeeEntity as t  WHERE t.ddoCode='"+ ddoCode+"' ORDER BY t.employeeId ";
		return (List<MstEmployeeEntity>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<Object[]> findEmpBasicDtls(Integer yearId, Integer monthId, Long billGroup, String ddoCode) {
		// TODO Auto-generated method stub
		Session currentSession = manager.unwrap(Session.class);
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

}
