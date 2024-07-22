package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeStatisticsRepoImpl implements EmployeeStatisticsRepo{

	@PersistenceContext
	EntityManager entityManager;
		
	

	@Override
	public List<Object[]> findEmployeeStatistics(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.employee_full_name_en,a.sevaarth_id,a.dob,b.group_name_en,f.post_name,l.lookup_desc,j.start_date,j.end_date,a.doj,\r\n" + 
				"a.emp_service_end_date,g.scale_desc,a.basic_pay,a.seven_pc_basic,m.levels,\r\n" + 
				"case when dcps_gpf_flag='Y'  then 'DCPS' when dcps_gpf_flag='N' then k.lookup_name else '' end as DCPS,\r\n" + 
				"case when dcps_gpf_flag='Y' then i.dcps_no  when dcps_gpf_flag='N' then h.pf_account_no  else '' end as gpfdcps,b.group_name_en as gisGroup,e.description,a.pyhical_handicapped,c.bank_name,\r\n" + 
				"d.bank_branch_name,a.bank_acnt_no,a.pran_no from employee_mst a \r\n" + 
				"inner join cadre_group_mst b on b.id=a.cadre_code \r\n" + 
				"inner join bank_mst c on c.bank_code=a.bank_code \r\n" + 
				"inner join bank_branch_mst d on d.bank_branch_code=a.bank_branch_code\r\n" + 
				"left join mst_dcps_Bill_group e on e.bill_group_id=a.billgroup_id\r\n" + 
				"left join org_post_details_rlt f on f.post_id=a.post_detail_id\r\n" + 
				"left join pay_scale_sixpc_mst g on g.pay_scale_code=a.pay_scale_code\r\n" + 
				"left join gpf_mst h on h.employee_id=a.employee_id\r\n" + 
				"left join dcps_details_mst i on i.employee_id=a.employee_id\r\n" + 
				"left join org_post_mst j on j.post_id=f.post_id \r\n" + 
				"left join cmn_lookup_mst k on k.lookup_id=cast (a.pfseries as BigInt) \r\n" + 
				"left join cmn_lookup_mst l on l.lookup_id=j.post_type_lookup_id \r\n" + 
				"inner join payband_gp_state_7pc m on a.seven_pc_level =m.level_id\r\n" + 
				"where a.ddo_code  = '"+ddoCode+"' and a.is_active='1'";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}
}
