package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;

@Repository
public class DeptEligibilityForAllowAndDeductRepoImpl implements DeptEligibilityForAllowAndDeductRepo{
//	protected final Log logger = LogFactory.getLog(getClass());
	
	@PersistenceContext
	EntityManager entityManager;
	
		
//	@Autowired
//	SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptEligibilityForAllowAndDeductList() {
		String HQL = "FROM DeptEligibilityForAllowAndDeductEntity as t ORDER BY t.departmentAllowdeducName ASC";// WHERE t.isActive='1'
		return (List<DeptEligibilityForAllowAndDeductEntity>) entityManager.createQuery(HQL).getResultList();
	}
	

	@Override
	public int saveAllowDeductionMst(DeptEligibilityForAllowAndDeductEntity mstDeptEligibilityForAllowAndDeductEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(mstDeptEligibilityForAllowAndDeductEntity);
		return (Integer) saveId;
		
	}
	
	

	@SuppressWarnings("unchecked")
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptAllowAndDeductList() {
		String HQL = "FROM DeptEligibilityForAllowAndDeductEntity as t where t.departmentAllowdeducCode in (4,7,13,15,18,24,37,38,41,42,47,50,66,76,78,89,90,100,102,105,108,110,111,112,113,114,115,116,118,119,120,121,122,123,124,125,126,127,128,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146,147,148,149,150,\r\n" + 
				"151,152,153,154,155,156,157,158,159,160,162,163,"
				+ "164,165,166,167,168,169,170,171,172,173,174,175,176,177,178,179,180,181,182,183,184,185,186,187,188,189,190,191,192,193,194,195,196,198,199,200,201,202)   ORDER BY t.departmentAllowdeducName ASC"; //changed from departmentAllowdeducId to departmentAllowdeducCode 
		return (List<DeptEligibilityForAllowAndDeductEntity>) entityManager.createQuery(HQL).getResultList();
		
	}

	@Override
	public int deleteMpgDdoAllowDeduc(int action, Object object) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1 = "delete from department_allowdeduc_mpg where ddo_code = '"+object.toString()+"' ";
		
     	Query query = currentSession.createSQLQuery(hql1);

		return query.executeUpdate();
     	
	}

	@Override
	public int saveEmpMpgDdoAllowDeduc(Object object, int action, Object[] serialid, String effectiveDate,
			Object object2) {
		// TODO Auto-generated method stub
		String hql="";
				Session currentSession = entityManager.unwrap(Session.class);
				try {
					 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
					 Date date = dt.parse(effectiveDate); // *** same for the format String below 
					 SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
				if(action==1)
				{
					 hql = " insert into department_allowdeduc_mpg (department_allowdeduc_code,is_active,created_date,with_effective_date,ddo_code) values ("+object+","+"1"+",current_timestamp,'"+dt1.format(date)+"','"+object2.toString()+"')";
					Query query = currentSession.createSQLQuery(hql);
					query.executeUpdate();
				//effective_date
					
					hql = " insert into department_allowdeduc_mpg_hst (department_allowdeduc_code,is_active,created_date,with_effective_date,ddo_code) values ("+object+","+"1"+",current_timestamp,'"+dt1.format(date)+"','"+object2.toString()+"')";
					query = currentSession.createSQLQuery(hql);
					query.executeUpdate();
				}
				}catch(Exception e) {
					e.printStackTrace();
				}
				return  1;
	}

	
	@Override
	public List<Object[]> findallowDeductLevel2(String ddoCode2) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select * from department_allowdeduc_mpg where ddo_code ='"+ddoCode2+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<Object[]> findlevel1DDOAgaintlevel2(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = " select t.ddo_reg_id,t.ddo_code,t.office_name as office_name,t.sub_department_code,t.ddo_code_level_2,t.parent_district_code,"
				+ "t.ddo_name,t.level_hierarchy from ddo_reg_mst t  where t.ddo_code_level_2= '"+userName+"' and t.is_active = '1' order by ddo_code";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	
}

	@Override
	public List<Object[]> getAllowDeductComponentByDDO(String ddoCode) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select * from department_allowdeduc_mpg where ddo_code = '"+ddoCode+"' and is_active = '1'";
	//	String hql = "select department_allowdeduc_code,department_allowdeduc_mpg_id from department_allowdeduc_mpg where ddo_code='"+ddoCode+"' and is_active = '1'";
		System.out.println(hql);
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}

}
