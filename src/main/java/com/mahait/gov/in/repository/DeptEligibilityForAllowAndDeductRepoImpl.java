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
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId(int id,String ddoCode) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select * from department_allowdeduc_mpg where department_code ='"+id+"' and ddo_code = '"+ddoCode+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> empEligibilityForAllowAndDeductCheckBoxId(String id) {// after clicking on checkbox of employee for allowances and deductions
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select e.department_allowdeduc_code,c.department_allowdeduc_name,e.sevaarth_id,e.with_effective_date from employee_mst d inner join employee_allowdeduc_mpg e on d.sevaarth_id = e.sevaarth_id  and e.is_active = '1' inner join department_allowdeduc_mst c on e.department_allowdeduc_code = c.department_allowdeduc_code and c.is_active = '1' where  e.sevaarth_id =  '"+id+"'"; ///changed from c.department_allowdeduc_id to c.department_allowdeduc_code
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId1(int id) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select b.sevaarth_id,b.employee_full_name_en,c.designation_name, a.sub_department_name_en ,d.department_name_en from sub_department_mst a inner join   employee_mst b on a.sub_department_id = b.field_department_id inner join  department_mst d on d.department_id = b.admin_department_id inner join designation_mst c on c.designation_id = b.designation_id  where b.is_active = '1' and d.is_active = '1' and a.is_active = '1' and d.department_id = "+id+"order by b.employee_full_name_en asc";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMpgSchemeBillGroupBySchemeBillGroupId2(int emp_id, int ddo_code) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select * from department_allowdeduc_mpg where department_code = '"+ddo_code+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteMappedComponent(int ddo_code) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "delete from department_allowdeduc_mpg where department_code = "+ddo_code;
        Query	query1=	currentSession.createSQLQuery(hql);
		///logger.info("Query is >>> "+hql);
		
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

	@SuppressWarnings("unchecked")
	public List<Object[]> getEmployeeAgainstId(int allowDeducComponentId, String ddoCode,String sevaarthId){
		Session currentSession = entityManager.unwrap(Session.class);
		String hql=null;
		if(sevaarthId.equals("1"))
		{
			hql = "select  a.employee_full_name_en,a.sevaarth_id,c.is_type,case when a.pay_commission_code = 8  then a.seven_pc_basic else a.basic_pay end as basic_pay,a.employee_id,b.department_allowdeduc_code,a.field_department_code," + 
					"case when d.net_amt <> 0  then d.net_amt else 0 end as net_amt, case when d.existing_amt <> 0  then d.existing_amt else 0 end as existing_amt ,case when d.net_amt <> 0  then d.net_amt else 0 end\r\n" + 
					"from employee_mst a\r\n" + 
					"inner join employee_allowdeduc_mpg b on a.sevaarth_id = b.sevaarth_id  \r\n" + 
					"left join department_allowdeduc_mst c on b.department_allowdeduc_code = c.department_allowdeduc_code\r\n" + 
					"left join employee_allowdeduc_component_amt d on a.sevaarth_id = d.sevaarth_id  and  d.department_allowdeduc_code = "+allowDeducComponentId+" \r\n" + 
					"where a.ddo_code = '"+ddoCode.trim()+"' and  b.department_allowdeduc_code = '"+allowDeducComponentId+"' and a.is_active='1'";
		}
		else {
			
	
		 //hql =  "select DISTINCT c.employee_full_name_en,c.sevaarth_id,a.is_type,c.basic_pay,c.employee_id,b.department_allowdeduc_code,c.field_department_code from department_allowdeduc_mst a inner join employee_allowdeduc_mpg b on a.department_allowdeduc_code = b.department_allowdeduc_code inner join employee_mst c on b.sevaarth_id = c.sevaarth_id where c.ddo_code = '"+ddoCode+"' and a.department_allowdeduc_code = "+allowDeducComponentId+" and c.sevaarth_id = '"+sevaarthId+"'"; //changed from department_allowdeduc_Id to department_allowdeduc_code 
			hql = "select  a.employee_full_name_en,a.sevaarth_id,c.is_type,case when a.pay_commission_code = 8  then a.seven_pc_basic else a.basic_pay end as basic_pay,a.employee_id,b.department_allowdeduc_code,a.field_department_code," + 
					"case when d.net_amt <> 0  then d.net_amt else 0 end as net_amt, case when d.existing_amt <> 0  then d.existing_amt else 0 end as existing_amt ,case when d.net_amt <> 0  then d.net_amt else 0 end\r\n" + 
					"from employee_mst a\r\n" + 
					"inner join employee_allowdeduc_mpg b on a.sevaarth_id = b.sevaarth_id  \r\n" + 
					"left join department_allowdeduc_mst c on b.department_allowdeduc_code = c.department_allowdeduc_code\r\n" + 
					"left join employee_allowdeduc_component_amt d on a.sevaarth_id = d.sevaarth_id  and  d.department_allowdeduc_code = "+allowDeducComponentId+" \r\n" + 
					"where a.ddo_code = '"+ddoCode.trim()+"' and  b.department_allowdeduc_code = "+allowDeducComponentId+" and a.sevaarth_id = '"+sevaarthId+"' and a.is_active='1'"; 
		}
		System.out.println("query >>>>"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> isPaybillIsInProcess(String sevaarthId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1 = " select pd.sevaarth_id, pg.is_active,pd.paybill_month,pd.paybill_year,pg.scheme_billgroup_id  from paybill_generation_trn pg inner join paybill_generation_trn_details pd  on pg.paybill_generation_trn_id =  pd.paybill_generation_trn_id where   pd.sevaarth_id='"+sevaarthId+"' and pg.is_active not in(14,8)";;
		Query query12 = currentSession.createSQLQuery(hql1);
		return (List<Object[]>) query12.list();
	}
	
	//Added For Non Gov Deduction
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findDeptNonGovDeductList() {
		String HQL = "FROM DeptEligibilityForAllowAndDeductEntity as t where t.departmentAllowdeducCode in (48,49,50,52,71,74,75)   ORDER BY t.departmentAllowdeducName ASC"; //changed from departmentAllowdeducId to departmentAllowdeducCode
		return (List<DeptEligibilityForAllowAndDeductEntity>) entityManager.createQuery(HQL).getResultList();
		
	}
	
	//for saving Non Gov Deduction
	
	/*@Override
	public int saveEmployeeNonGovDuesDeduct(
			EmployeeAllowDeducComponentAmtEntity lstArrEmployeeAllowDeducComponentAmtEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId=0;
		
		saveId =currentSession.save(lstArrEmployeeAllowDeducComponentAmtEntity);
		return (Integer) saveId;
		
	}
	
	@Override
	public EmployeeAllowDeducComponentAmtEntity findMstDeptByDeptId(String sevaarthId,int deptCode) { // for checking sevaarth id for grp components
	
		try {
			
		
		String hql1 = " select e from "+EmployeeAllowDeducComponentAmtEntity.class.getName()+" e where SEVAARTH_ID='"+sevaarthId+"' and department_allowdeduc_code= "+deptCode;
		Query query = (Query) entityManager.createQuery(hql1,EmployeeAllowDeducComponentAmtEntity.class);
		return (EmployeeAllowDeducComponentAmtEntity) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public void updateComponent(EmployeeAllowDeducComponentAmtEntity empdata) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(empdata);
	}*/

/*	@Override
	public int deleteMpgDdoAllowDeduc(int input, int action, String ddo_code) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		String hql1 = "delete from department_allowdeduc_mpg where department_code='"+input+"' and ddo_code = '"+ddo_code+"'";
		Query query1 = currentSession.createSQLQuery(hql1);
		logger.info( ">>> query11 <<<< " + hql1);		
     	return  1;
	}

	@Override
	public int saveEmpMpgDdoAllowDeduc(Object object, int input, int action, Object[] serialid, String effectiveDate,
			String ddo_code) {
		// TODO Auto-generated method stub
		
		Session currentSession = entityManager.unwrap(Session.class);
		try {
			 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
			 Date date = dt.parse(effectiveDate); // *** same for the format String below 
			 SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd"); 
			
			 logger.info( ">>> query <<<< " + dt1.format(date));
			
		if(action==1)
		{
			String hql = " insert into department_allowdeduc_mpg (department_code,department_allowdeduc_code,is_active,created_date,with_effective_date,ddo_code) values ("+input+","+object+","+"1"+",current_timestamp,'"+dt1.format(date)+"',"+ddo_code+")";
			System.out.println( hql);
			Query query = currentSession.createSQLQuery(hql);
			logger.info( ">>> query <<<< " + query.executeUpdate());
			
		//effective_date
			
			hql = " insert into department_allowdeduc_mpg_hst (department_code,department_allowdeduc_code,is_active,created_date,with_effective_date,ddo_code) values ("+input+","+object+","+"1"+",current_timestamp,'"+dt1.format(date)+"',"+ddo_code+")";
			logger.info( hql);
			query = currentSession.createSQLQuery(hql);
			logger.info( ">>> query <<<< " + query.executeUpdate());
		}
		}catch(Exception e) {
			logger.info( ">>> query <<<< " + e.getMessage());
		}
		return  1;
		
	}*/

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
	public List<Object[]> findAllMpgSchemeBillGroupbyParameterDDOWise(String input) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select * from department_allowdeduc_mpg where ddo_code ='"+input+"'";
		//String hql =  "select * from department_allowdeduc_mpg where ddo_code in('"+input+"')";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	
	@Override
	public List<Object[]> findallowDeductLevel2(String ddoCode2) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select * from department_allowdeduc_mpg where ddo_code ='"+ddoCode2+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	
	@Override
	public List<Object[]> findallDDOLevel2AgaintDept(int deptCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql =  "select distinct a.ddo_code_level2,b.ddo_name from ddo_map_rlt a inner join ddo_reg_mst b on a.ddo_code_level2 = b.ddo_code where a.department_code ='"+deptCode+"'";
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
