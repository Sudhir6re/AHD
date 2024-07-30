package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstEmployeeEntity;

@Repository
@Transactional
public class UpdateUIDRepoImpl implements UpdateUIDRepo{
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findAllEmployee(String userName) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select employee_id,sevaarth_id, employee_full_name_en,uid_no from employee_mst where ddo_code ='" +userName+ "'";
		System.out.println("HQL:"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public MstEmployeeEntity findEmpData(Long employeeId) {
			System.out.println("----------Inside findEMpdata------------");
		
			MstEmployeeEntity objDept = null;
			Session currentSession = entityManager.unwrap(Session.class);
			objDept = currentSession.get(MstEmployeeEntity.class, employeeId);
			return objDept;
	}

	@Override
	public Serializable saveupdateMobNo(MstEmployeeEntity mstEmployeeEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId=1;
		currentSession.update(mstEmployeeEntity);
		return (Integer) saveId;
	}


}
