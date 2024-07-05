package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgPostMst;

@Repository
public class DDOInfoRepoImpl implements DDOInfoRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public String getDdoCodeForDDO(Long postId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DDO_CODE FROM ORG_DDO_MST where POST_ID = '"+postId+"'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list().get(0).toString();
	}

	@Override
	public List<Object[]> getLevel1DDOList(String lStrDdoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DISTINCT ZP_DDO_CODE FROM  RLT_ZP_DDO_MAP where REPT_DDO_CODE =  '"+lStrDdoCode+"'";
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<Object[]> getDDoHistoryDetailsForApprove(String ddo) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select sr_no,ddo_code,ddo_name,from_date,to_date,status,user_id from HR_PAY_DDO_HISTORY where DDO_code in ("+ddo+") and status=0 ";
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}



}
