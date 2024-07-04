package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;

@Repository
public class DdoBillGroupRepoImpl implements DdoBillGroupRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	public List<MstDcpsBillGroup> lstBillName() {
		// TODO Auto-generated method stub
		String HQL = "FROM MstDcpsBillGroup as t  ORDER BY t.dcpsDdoBillGroupId DESC";
		return (List<MstDcpsBillGroup>) entityManager.createQuery(HQL).getResultList();
	}
	@Override
	public int saveBillGroupMaintainance(MstDcpsBillGroup mstDcpsBillGroup) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = currentSession.save(mstDcpsBillGroup);
		return (Integer) saveId;
	}
	@Override
	public List<MstScheme> getSchemeCodeAgainstName(String schemeId) {
		// TODO Auto-generated method stub
		String HQL = "FROM MstSchemeEntity as t where t.schemeCode = '"+schemeId+"'  ORDER BY t.schemeId DESC";
		return (List<MstScheme>) entityManager.createQuery(HQL).getResultList();
	}

}
