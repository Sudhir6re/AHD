package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeIncrementRepoImpl implements EmployeeIncrementRepo {
	
	@PersistenceContext
	EntityManager entityManager;


	/*@Override
	public List<PayCommssionEntity> findAppPayCommssion() {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "FROM PayCommssionEntity";
		return (List<PayCommssionEntity>) currentSession.createQuery(HQL, PayCommssionEntity.class).getResultList();
	}*/


	@Override
	public List<Object[]> getEmpDataForIncrementApproval(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select distinct increment_order_date,increment_order_no,d.office_name,c.ddo_code_level1 from employee_increment a \r\n"
				+ "inner join employee_mst b on b.sevaarth_id = a.sevaarth_id\r\n"
				+ "inner join ddo_map_rlt c on c.ddo_code_level1 = b.ddo_code\r\n"
				+ "inner join ddo_reg_mst d on d.ddo_code = c.ddo_code_level2 where c.ddo_code_level2='" + userName
				+ "' and a.is_Active = '1' and increment_order_no <>'0' ";
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


	/*@Override
	public List<MpgSchemeBillGroupEntity> findMpgSchemeBillGroupByDDOCode(String dDOCode, int roleid) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = null;
		if (roleid == 1) {
			HQL = "select ddo_map_id from ddo_reg_mst a inner join ddo_map_rlt b on a.ddo_reg_id = b.ddo_code_user_id1 and a.ddo_code = b.ddo_code_level1 where a.ddo_code = '"
					+ dDOCode + "'";
		} else {
			HQL = "select ddo_map_id from ddo_reg_mst a inner join ddo_map_rlt b on a.ddo_reg_id= b.ddo_code_user_id2 and a.ddo_code = b.ddo_code_level2 where a.ddo_code = '"
					+ dDOCode + "'";
		}
		
		Query query = currentSession.createSQLQuery(HQL);
		List<Long> lstIds = query.getResultList();
		if (query.getResultList().size() > 0) {
			String HQL11 = "FROM MpgSchemeBillGroupEntity as t where t.isActive = '1' and t.ddoMapId IN (:ids) ORDER BY t.billDescription ";
			
			Query q1 = (Query) entityManager.createQuery(HQL11);
			q1.setParameterList("ids", lstIds);
			return (List<MpgSchemeBillGroupEntity>) q1.getResultList();
		}

		return null;
	}*/


	@Override
	public String officeName(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select ddo_name from ddo_reg_mst  where ddo_code  = '" + userName + "'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list().get(0).toString();
	}


	@Override
	public List<Object[]> lstEmpforMTR21(String orderNo, String orderDate, int levelRoleVal, String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		
		String hql=null;
		if(levelRoleVal==1)
		{
		hql = "select distinct b.employee_full_name_en,c.post_short_name,a.current_basic_pay,b.sevaarth_id,a.effective_from_date,\r\n" + 
				"a.to_increment_date,a.increment_basic_pay_sal from employee_increment a \r\n"
				+ "inner join employee_mst b on a.sevaarth_id = b.sevaarth_id \r\n"
				+ "inner join post_details_rlt c on b.post_detail_id = c.post_details_id  where increment_order_no = '" + orderNo + "' "
						+ " and increment_order_date = '"+orderDate+"' and b.ddo_code='"+ddoCode+"'";
		}else {
			hql ="select distinct b.employee_full_name_en,c.post_short_name,a.current_basic_pay,b.sevaarth_id,a.effective_from_date,\r\n" + 
					"a.to_increment_date,a.increment_basic_pay_sal from employee_increment a inner join employee_mst b on a.sevaarth_id = b.sevaarth_id   \r\n" + 
					"    inner join  post_details_rlt c  on b.post_detail_id = c.post_details_id   inner join ddo_map_rlt e on b.ddo_code = e.ddo_code_level1\r\n" + 
					"    where  increment_order_no = '"+orderNo+"' and increment_order_date = '"+orderDate+"' and e.ddo_code_level2='"+ddoCode+"'";
		}
		
		System.out.println("lstEmpforMTR21------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}



	

}
