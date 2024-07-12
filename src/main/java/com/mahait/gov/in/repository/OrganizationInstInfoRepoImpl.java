package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.OrgDdoMst;


@Repository
public class OrganizationInstInfoRepoImpl implements OrganizationInstInfoRepo {

	@PersistenceContext
	EntityManager manager;
		
/*	@Autowired
	SessionFactory sessionFactory;
*/
	

	@SuppressWarnings("unchecked")
	@Override
	public List<OrgDdoMst> findDDOInfo(String userName) {
		String HQL = "FROM OrgDdoMst as  t  where ddoCode = '" + userName+ "'"; 
		return (List<OrgDdoMst>) manager.createQuery(HQL).getResultList();
	}

	@Override
	public List<InstituteType> lstInstType() {
		Session currentSession = manager.unwrap(Session.class);
		String hql = "select * from INSTITUTE_TYPE";
		Query query = currentSession.createSQLQuery(hql);
		return (List<InstituteType>) query.list();
	}

	@Override
	public List<Object[]> getBankBranch(String valueOf) {
		Session hibSession = manager.unwrap(Session.class);
		Query query = hibSession.createSQLQuery(
				"select bank_branch_id,bank_branch_name,bank_branch_code from bank_branch_mst where bank_code="
						+ valueOf);
		List<Object[]> lstbankbranchdata = query.list();
		return lstbankbranchdata;
	}

	@Override
	public int saveorgInstInfo(OrgDdoMst objForSave) {
		Session currentSession = manager.unwrap(Session.class);
		Serializable saveId = currentSession.save(objForSave);
		return (Integer) saveId;
	}

	@Override
	public void updateDDOInfo(OrgDdoMst objForSave) {
		// TODO Auto-generated method stub
		Session currentSession = manager.unwrap(Session.class);
		currentSession.update(objForSave);
	}
}
