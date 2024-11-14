package com.mahait.gov.in.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgUserMst;

@Repository
public class LimitLoginRepoImpl implements LimitLoginRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public void updateFailedAttempts(OrgUserMst user) {
		Session session = entityManager.unwrap(Session.class);
		session.update(user);
	}

	@Override
	public OrgUserMst findUserbyUsername(String userName) {
	//	String sql = "FROM  OrgUserMst e Where e.userName = :userName ";
		String sql = "FROM  OrgUserMst e Where e.userName ='"+userName+"' ";
		Query query = entityManager.createQuery(sql, OrgUserMst.class);
//		query.setParameter("userName", userName);
		
		try {
			return (OrgUserMst) query.getSingleResult();
		}catch(Exception e) {
			return new OrgUserMst();
		}
		
	}

}
