package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.UserLoginHistryEntity;


@Repository
public class UserLoginHistryRepoImpl implements UserLoginHistryRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<UserLoginHistryEntity> findlogindata() {
		// TODO Auto-generated method stub
		String HQL = "FROM UserLoginHistryEntity as t order by id DESC";
		return (List<UserLoginHistryEntity>) entityManager.createQuery(HQL).getResultList();
	}

	@Override
	public void saveLoginDtls(UserLoginHistryEntity userLoginHistryEntity) {
		Session session= entityManager.unwrap(Session.class);
		session.save(userLoginHistryEntity);
	}

}
