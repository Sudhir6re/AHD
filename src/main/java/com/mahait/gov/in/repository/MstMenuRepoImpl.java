package com.mahait.gov.in.repository;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstMenuEntity;

@Repository
public class MstMenuRepoImpl implements MstMenuRepo {

	@PersistenceContext	
	private EntityManager entityManager;
	
	@Override
	public int saveMenu(MstMenuEntity objMenuEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objMenuEntity);
		return (Integer) saveId;
	}

	@Override
	public MstMenuEntity findMenuByKeyForDelete(int key) {
		MstMenuEntity objMenu = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objMenu = currentSession.get(MstMenuEntity.class, key);
		return objMenu;
	}

	@Override
	public void updateMenuStatus(MstMenuEntity objMenu) {
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objMenu);
	}

	@Override
	public MstMenuEntity findMenuByKeyForEdit(int key) {
		MstMenuEntity objMenu = null;
		Session currentSession = entityManager.unwrap(Session.class);
		objMenu = currentSession.get(MstMenuEntity.class, key);
		return objMenu;
	}

}
