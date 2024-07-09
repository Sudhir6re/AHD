package com.mahait.gov.in.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpRltDdoMap;

@Repository
public class ZpDDOOfficeRepoImpl implements ZpDDOOfficeRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<ZpRltDdoMap> getAllDDOOfficeDtlsDataByPostID(String username) { 
		String HQL = "FROM ZpRltDdoMap as  t  where langId = 1 and reptDdoCode='"+username+"' and status=0  ORDER BY t.zpMapId desc";
		return (List<ZpRltDdoMap>) entityManager.createQuery(HQL).getResultList();

	}

	@Override
	public List<Object[]> getDDOinfo(String zpDdoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select zp.ZP_DDO_CODE,zp.REPT_DDO_CODE,zp.FINAL_DDO_CODE,zp.SPECIAL_DDO_CODE,org1.DDO_PERSONAL_NAME as ddo1, \r\n" + 
				"org2.DDO_PERSONAL_NAME as ddo2,org3.DDO_PERSONAL_NAME as ddo3,org4.DDO_PERSONAL_NAME as ddo4\r\n" + 
				"from RLT_ZP_DDO_MAP zp left OUTER join ORG_DDO_MST org1 on org1.DDO_CODE=zp.ZP_DDO_CODE \r\n" + 
				" left OUTER join ORG_DDO_MST org2 on org2.DDO_CODE=zp.REPT_DDO_CODE \r\n" + 
				" left OUTER join ORG_DDO_MST org3 on org3.DDO_CODE=zp.FINAL_DDO_CODE \r\n" + 
				" left OUTER join ORG_DDO_MST org4 on org4.DDO_CODE=zp.SPECIAL_DDO_CODE where zp.ZP_DDO_CODE='"+zpDdoCode+"'";
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public String getOfficeName(String zpDdoCode) {

		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT OFF_NAME FROM mst_dcps_ddo_office  where DDO_CODE='"+zpDdoCode+"'");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			rtnStr = list.get(0).toString();

		return rtnStr;

	}

	@Override
	public OrgUserMst findddo(String zpDdoCode) {
		try {
			String HQL = "FROM OrgUserMst as  t  where t.userName = '0"+zpDdoCode+"_AST'";
			return (OrgUserMst) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public int approveChangeStatement(String zpDdoCode, int flag) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void updateApproveStatus(OrgUserMst objOrgUserMst) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(objOrgUserMst);
	}

	@Override
	public ZpRltDdoMap findddoinZPRlt(String zpDdoCode) {
		try {
			String HQL = "FROM ZpRltDdoMap as  t  where t.zpDdoCode = '"+zpDdoCode+"'";
			return (ZpRltDdoMap) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void updateApproveStatusinZpRlt(ZpRltDdoMap zpRltDdoMap) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(zpRltDdoMap);
	}
}	
