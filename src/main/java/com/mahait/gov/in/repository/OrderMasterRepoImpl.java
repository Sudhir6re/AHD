package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class OrderMasterRepoImpl implements OrderMasterRepo {
	
	@PersistenceContext
	EntityManager entityManager;


	@Override
	public List<Object[]> findAllEmployee(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select employee_id,sevaarth_id,employee_full_name_en,district_code,taluka_code,city_class from employee_mst where ddo_code = '"+userName+"' and is_active = '1'";
		System.out.println("HQL:"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


	

}
