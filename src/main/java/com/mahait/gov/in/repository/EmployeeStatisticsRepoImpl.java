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
	public List<Object[]> findEmployeeStatistics(String DDOCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select employee_full_name_en,a.sevaarth_id,to_char(dob, 'DD/MM/YYYY')as dob,pran_no,pran_status,pran_remarks, emp_type,group_name_en,  case when post_name is null or trim(post_name)='' then '-'  else e.post_name end as post_name,"
				+ "  employee_type as Post_Type,case when a.post_detail_id is null or e.start_date is null then '-'  else  to_char(e.start_date, 'DD/MM/YYYY') end as start_date,case when post_detail_id is null or e.end_date is null then '-'  else   to_char(e.end_date, 'DD/MM/YYYY') end as end_date, "
				+ " to_char(doj, 'DD/MM/YYYY')as doj,to_char(emp_service_end_date, 'DD/MM/YYYY')as emp_service_end_date,   case when a.pay_scale_code is null or pay_scale is null then ''  else f.pay_scale end as pay_scale,  basic_pay,  case when pay_commission_code=8 then a.seven_pc_basic end as sevenpc, "
				+ " case when dcps_gpf_flag='Y'  then 'DCPS' when dcps_gpf_flag='N' then g.account_maintain_by else '' end as DCPS,case when dcps_gpf_flag='Y'   then h.dcps_no  when dcps_gpf_flag='N' then g.pf_account_no  else '' end,group_name_en as gis_group, k.bill_description,  pyhical_handicapped,bank_name,"
				+ "bank_branch_name,j.ifsc_code,bank_acnt_no,uid_no,pan_no  from employee_mst a inner join cadre_mst b ON a.cadre_code=b.cadre_code   inner join cadre_group_mst c ON b.group_id=c.id inner join bank_mst i ON a.bank_code=i.bank_code inner join bank_branch_mst j ON a.bank_branch_code=j.bank_branch_id "
				+ " left join bill_group_mst k ON k.bill_group_id=a.billgroup_id left join  post_details_rlt as e on e.post_details_id = a.post_detail_id left join pay_scale_mst as f on f.pay_scale_id=a.pay_scale_code left join gpf_mst as g on g.employee_id=a.employee_id "
				+ " left join dcps_details_mst as h  on h.employee_id= a.employee_id where a.ddo_code  = '"+ DDOCode + "' and a.is_active='1'";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}
}
