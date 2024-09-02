package com.mahait.gov.in.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgUserMst;

@Repository
public class ChangePasswordNewRepoImpl implements changePasswordNewRepo  {
	
	
	
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void updatePassword(OrgUserMst orgUserMst) {
		// TODO Auto-generated method stub
		Session session=entityManager.unwrap(Session.class);
		session.update(orgUserMst);
	}

}
