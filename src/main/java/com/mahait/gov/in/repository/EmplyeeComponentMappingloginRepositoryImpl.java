package com.mahait.gov.in.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;


@Repository
public class EmplyeeComponentMappingloginRepositoryImpl implements EmplyeeComponentMappingloginRepository{

	
	@PersistenceContext
	EntityManager entityManager;
	
	
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findAllComponentDetails() {
		Session session=entityManager.unwrap(Session.class);
		Query query=session.createQuery("From DeptEligibilityForAllowAndDeductEntity");
		return query.getResultList();
	}


	@Override
	public List<Map<String, Object>> getEmployeeList() {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "WITH LatestEntries AS (\r\n" + 
				"    SELECT\r\n" + 
				"        c.sevaarth_id AS sevaarthid,\r\n" + 
				"        c.employee_full_name_en,\r\n" + 
				"        c.employee_l_name_en,\r\n" + 
				"        d.designation_name,\r\n" + 
				"        c.emp_class,\r\n" + 
				"        CASE \r\n" + 
				"            WHEN c.pay_commission_code = 8 THEN CAST(c.pay_commission_code AS varchar) \r\n" + 
				"            ELSE e.scale_start_amt || '-' || e.scale_end_amt \r\n" + 
				"        END AS payComm,\r\n" + 
				"        CASE \r\n" + 
				"            WHEN c.seven_pc_level IS NOT NULL THEN 'Level ' || f.levels \r\n" + 
				"            ELSE c.basic_pay - c.grade_pay || '+' || c.grade_pay \r\n" + 
				"        END AS payband,\r\n" + 
				"        b.basic_pay AS basic,\r\n" + 
				"        d.designation_code,\r\n" + 
				"        b.*,\r\n" + 
				"        ROW_NUMBER() OVER (PARTITION BY c.sevaarth_id ORDER BY a.created_date DESC) AS rn\r\n" + 
				"    FROM\r\n" + 
				"        paybill_generation_trn a\r\n" + 
				"        INNER JOIN paybill_generation_trn_details b ON a.paybill_generation_trn_id = b.paybill_generation_trn_id\r\n" + 
				"        INNER JOIN employee_mst c ON b.sevaarth_id = c.sevaarth_id\r\n" + 
				"        INNER JOIN designation_mst d ON d.designation_code = c.designation_code\r\n" + 
				"        LEFT JOIN pay_scale_sixpc_mst e ON e.pay_scale_code = c.pay_scale_code\r\n" + 
				"        LEFT JOIN payband_gp_state_7pc f ON f.level_id = c.seven_pc_level where  c.sevaarth_id NOT IN (SELECT sevaarth_id FROM employee_allowdeduc_mpg1) \r\n" + 
				")\r\n" + 
				"SELECT *\r\n" + 
				"FROM LatestEntries\r\n" + 
				"WHERE rn = 1;\r\n" ;
		 org.hibernate.Query<Map<String, Object>> hibernateQuery =  currentSession.createNativeQuery(HQL);
	      hibernateQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
	        List<Map<String,Object>> resvalue = hibernateQuery.list();
		return resvalue;
	}


	
	@Override
	public void mapComonent(Long empId, String sevaarthId, Integer deptAllowDeducCode) {
			
			Session currentSession = entityManager.unwrap(Session.class);
			 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			 Date date1 =new Date();// dt.parse(effectiveDate); // *** same for the format String below 
			 SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
				String hql = "insert into employee_allowdeduc_mpg1 (sevaarth_id,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) values ('"+sevaarthId+"',"+deptAllowDeducCode+","+"1"+",current_timestamp,'"+empId+"','"+dt1.format(date1)+"')";
				Query query = currentSession.createSQLQuery(hql);
				query.executeUpdate();
				
				/*hql = "insert into employee_allowdeduc_mpg_hst (sevaarth_id,department_allowdeduc_code,is_active,created_date,employee_id,with_effective_date) values ('"+sevaarthId+"',"+deptAllowDeducCode+","+"1"+",current_timestamp,'"+empId+"','"+dt1.format(date1)+"')";
				query = currentSession.createSQLQuery(hql);
				query.executeUpdate();*/

	}
	
	
	

}
