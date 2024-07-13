package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DdoOffice;

@Repository
public class OrganisationDtlsRepoImpl implements OrganisationDtlsRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> lstGetOfficeDtls(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DDO_CODE,OFF_NAME,STATE,DISTRICT,TALUKA,TOWN,VILLAGE,ADDRESS1,OFFICE_PIN,OFFICE_CITY_CLASS,OFFICE_CD,"
				+ "GRANT_APPLICABLE,TEL_NO1,TEL_NO2,EMAIL,FAX FROM MST_DCPS_DDO_OFFICE where DDO_CODE = '"+username+"'";
				   
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();

	}

	@Override
	public int saveorgInstInfo(DdoOffice objForSave) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objForSave);
		return (Integer) saveId;
	}

	@Override
	public void updateorgInstituteInfo(DdoOffice objForSave) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objForSave);
	}


}
