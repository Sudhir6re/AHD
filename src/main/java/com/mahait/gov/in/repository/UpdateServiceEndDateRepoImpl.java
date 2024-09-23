package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstEmployeeEntity;

@Repository
public class UpdateServiceEndDateRepoImpl implements UpdateServiceEndDateRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findAllEmployee(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select a.employee_id,a.sevaarth_id, a.employee_full_name_en,a.super_ann_date,b.designation_name,a.dob,a.cadre_code from employee_mst\r\n" + 
				     "a inner join designation_mst b on b.designation_code=a.designation_code where ddo_code ='" +userName+ "'";
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
	public Serializable saveupdateSED(MstEmployeeEntity mstEmployeeEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId=1;
		currentSession.update(mstEmployeeEntity);
		return (Integer) saveId;
	}

}
