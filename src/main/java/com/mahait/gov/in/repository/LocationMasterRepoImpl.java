package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;



@Repository
public class LocationMasterRepoImpl implements LocationMasterRepo{

	@PersistenceContext
	EntityManager entityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findAllStates(int countryId) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		//String hql =  "select a.country_name,b.state_id,b.state_name from country_mst  a inner join state_mst b on a.country_id = b.country_id  where a.is_active = '1' and a.country_id = '"+countryId+"' order by b.state_name asc";
		String hql =  "select a.country_name_en,b.state_code,b.state_name_en,b.nps_state_code  from country_mst  a inner join state_mst b on a.country_code = b.country_code  where a.is_active = '1' and a.country_code = '"+countryId+"' order by b.state_name_en asc";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
}