package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.QualificationEntity;

@Repository
public class QualificationRepositoryImpl implements QualificationRepository {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public QualificationEntity findQualificationByidForDelete(long id) {
		QualificationEntity objMenu = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objMenu = currentSession.get(QualificationEntity.class, id);
		return objMenu;
	}

	@Override
	public void deleteQualification( QualificationEntity objMenu) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.delete(objMenu);
		
	}

	@Override
	public QualificationEntity findQualificationByIdForEdit(long id) {
		QualificationEntity objMenu = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objMenu = currentSession.get(QualificationEntity.class, id);
		return objMenu;
	}

	@Override
	public void updateQualification(QualificationEntity objMenu) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objMenu);
	}

	@Override
	public List<QualificationEntity> lstAllQualification() {
		Session currentSession = entityManager.unwrap(Session.class);

	    // SQL query to fetch all columns of the ComponentMstEntity
	    String hql = "SELECT * FROM Qualification";  // Fetches all fields

	    // Create SQL query and map it to the ComponentMstEntity class
	    Query query = currentSession.createSQLQuery(hql)
	                                .addEntity(QualificationEntity.class);

	    // Return the result list as ComponentMstEntity
	    return query.list();
	}

	@Override
	public QualificationEntity save(QualificationEntity qualificationEntity) {
	    // Unwrapping the session from the EntityManager
	    Session currentSession = entityManager.unwrap(Session.class);
	    
	    // Save or update the entity
	    currentSession.saveOrUpdate(qualificationEntity);
	    
	    // Flush and clear the session to ensure the data is committed and session is clean
	    currentSession.flush();
	    currentSession.clear();
	    
	    return qualificationEntity;
	}



}
