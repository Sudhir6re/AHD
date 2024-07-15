package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

//import com.mahait.gov.in.entity.OrgDepartmentMst;
@Repository
public class MstDepartmentRepoImpl  implements MstDepartmentRepo {

	@PersistenceContext
	EntityManager manager;

//	@Override
//	public List<OrgDepartmentMst> findAllDepartment() {
//		// TODO Auto-generated method stub
//		String HQL = "FROM OrgDepartmentMst as t ORDER BY t.departmentId DESC";
//		return (List<OrgDepartmentMst>) manager.createQuery(HQL).getResultList();
//	}
}