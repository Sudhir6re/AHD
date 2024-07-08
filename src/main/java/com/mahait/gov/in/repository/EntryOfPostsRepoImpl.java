package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgDesignationMst;

@Repository
public class EntryOfPostsRepoImpl implements EntryOfPostsRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<OrgDesignationMst> getActiveDesig(Long lLngFieldDept) {
		Session session = getSession();
		String HQL_QUERY = "select mst from MstDcpsDesignation dcpsDesig, OrgDesignationMst mst  where mst.dsgnId=dcpsDesig.orgDesignationId and mst.activateFlag=1 and  dcpsDesig.fieldDeptId =  "
				+ lLngFieldDept;
		Query query = session.createQuery(HQL_QUERY);
		List resultList = query.list();
		return resultList;
	}

	public Session getSession() {
		Session session = entityManager.unwrap(Session.class);
		return session;
	}

	@Override
	public List getAllBillsFromLocation(Long locId) {
		Session session = getSession();
		String HQL_QUERY = " from MstDcpsBillGroup where LocId= " + locId
				+ " and (billDeleted is null or billDeleted <> 'Y') and (billDcps is null or billDcps <> 'Y')";
		Query query = session.createQuery(HQL_QUERY);
		List resultList = query.list();
		return resultList;
	}

}
