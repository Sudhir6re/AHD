package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.OrgDdoMst;

@Repository
public class OrganisationDtlsRepoImpl implements OrganisationDtlsRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public DdoOffice lstGetOfficeDtls(String username) {
		
		String HQL = "From DdoOffice as t where t.dcpsDdoCode = '"+username+"'";
		List<DdoOffice> lst = entityManager.createQuery(HQL).getResultList();
		if (lst.size() > 0) {
			return lst.get(0);
		} else {
			return null;
		}

	}

	@Override
	public Long saveorgInstInfo(DdoOffice objForSave) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objForSave);
		return (Long) saveId;
	}

	@Override
	public void updateorgInstituteInfo(DdoOffice objForSave) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objForSave);
	}

	@Override
	public List findtalukalist(String districtId) {
		Session hibSession = entityManager.unwrap(Session.class);
		List temp = null;
		try {
			String talukaQuery = "SELECT taluka_id,taluka_name FROM cmn_taluka_mst where district_id =  '"
					+ String.valueOf(districtId)+"'";
			Query sqlQuery = hibSession.createSQLQuery(talukaQuery);
			temp = sqlQuery.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public List findcitylist(String districtId) {
		Session hibSession = entityManager.unwrap(Session.class);
		List temp = null;
		try {
			String cityQuery = "SELECT city_id, city_name, city_class  FROM cmn_city_mst where district_id = '"
					+ String.valueOf(districtId)+"'";
			Query sqlQuery = hibSession.createSQLQuery(cityQuery);
			temp = sqlQuery.list();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return temp;
	}

	@Override
	public void updateddoOfficeDetails(DdoOffice lstprop) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(lstprop);
	}


}
