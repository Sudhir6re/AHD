package com.mahait.gov.in.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.model.DeptEligibilityForAllowAndDeductModel;

@Repository
public class AllowDeduBulkEmpRepoImpl implements AllowDeduBulkEmpRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public int updateAllowDeductBulkEmplComp(
			DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel,String ddoCode) {
		

		try {
				
			Session currentSession = entityManager.unwrap(Session.class);
			 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			 Date date1 = new Date();
			 SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
				String hql = "insert into employee_allowdeduc_mpg (sevaarth_id,department_code,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) "
						+ "values ('"+deptEligibilityForAllowAndDeductModel.getSevaarthId()+"',51,"+deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducCode()+",'1',current_timestamp,"+deptEligibilityForAllowAndDeductModel.getEmployeeId()+",'"+dt1.format(date1)+"')";
				Query query = currentSession.createSQLQuery(hql);
				query.executeUpdate();
				
				hql = "insert into employee_allowdeduc_mpg_hst (sevaarth_id,department_code,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) "
						+ "values ('"+deptEligibilityForAllowAndDeductModel.getSevaarthId()+"',51,"+deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducCode()+",'1',current_timestamp,"+deptEligibilityForAllowAndDeductModel.getEmployeeId()+",'"+dt1.format(date1)+"');";
				query = currentSession.createSQLQuery(hql);
				query.executeUpdate();

			return  1;
			}catch(Exception e) {
				e.printStackTrace();
			}
		
		return  1;
		   
			
		
	}

	@Override
	public void checkComponentAlreadyPresent(
			DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel,String ddoCode) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		
		
		
		String hql1 = "delete from employee_allowdeduc_mpg where  department_allowdeduc_code="+deptEligibilityForAllowAndDeductModel.getDepartmentAllowdeducCode()+" and employee_id="+deptEligibilityForAllowAndDeductModel.getEmployeeId()+"  and  sevaarth_id = '"+deptEligibilityForAllowAndDeductModel.getSevaarthId()+"' ";
		
     	Query query = currentSession.createSQLQuery(hql1);

		 query.executeUpdate();
     	
	}

	@Override
	public List<Object[]> getListEmpBySchemBillGroup(String userName, Integer schemeBillGrpId,
			Integer departmentAllowdeducCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "Select a.sevaarth_id,a.employee_full_name_en,b.designation_name,c.department_name_en,a.employee_id,a.pay_commission_code,d.commission_name_en,a.dcps_gpf_flag,a.emp_service_end_date,e.bill_description,(select count(*) from employee_allowdeduc_mpg where sevaarth_id=a.sevaarth_id and department_allowdeduc_code="+departmentAllowdeducCode+")  FROM employee_mst a,designation_mst b,department_mst c,\r\n"
				+ " pay_commission_mst d,bill_group_mst e where a.designation_code = b.designation_code and a.admin_department_code = c.department_code and a.pay_commission_code=d.pay_commission_code and e.bill_group_id = a.billgroup_id and a.billgroup_id is not null  and a.is_active='1' and  a.ddo_code = '"
				+ userName + "' and a.billgroup_id="+schemeBillGrpId+" order by a.employee_full_name_en"; //and emp_service_end_date > now()
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<Object[]> findpaybill(int billNumber, String userName) {

		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select paybill_generation_trn_id,scheme_billgroup_id from paybill_generation_trn where scheme_billgroup_id ="+billNumber+" and is_Active not in (14,8) \r\n" + 
				"		and ddo_code='"+userName+"' ";
		System.out.println("Findpaybill Quer +++++" + HQL);

		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

}
