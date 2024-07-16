package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.CmnStateMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;

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

	@Override
	public List<CmnStateMst> getStateLst(long countryId) {
		String HQL = "FROM CmnStateMst as  t  where t.countryId = 1 order by t.stateId";
		return (List<CmnStateMst>) entityManager.createQuery(HQL).getResultList();

	}
	@Override
	public List<CmnDistrictMst> getDistrictlst(long stateId) {	
		String HQL = "FROM CmnDistrictMst as  t  where t.stateId = 15 and t.activateFlag=1";
	return (List<CmnDistrictMst>) entityManager.createQuery(HQL).getResultList();}

	@Override
	public List<DdoOffice> getAllOffices(String ddoCode) {	
		String HQL = "FROM DdoOffice as  t  where t.dcpsDdoCode = '"+ddoCode+"'";
	return (List<DdoOffice>) entityManager.createQuery(HQL).getResultList();}

	@Override
	public String getDistrictId(String ddoCode) {

		Session currentSession = entityManager.unwrap(Session.class);
		List list = new ArrayList();
		String rtnStr = null;
		StringBuffer query = new StringBuffer();
		query.append("SELECT DIstrict FROM MST_DCPS_DDO_OFFICE where ddo_code='"+ddoCode+"' and DDO_OFFICE='Yes'");
		Query hsqlQuery = currentSession.createSQLQuery(query.toString());
		list = hsqlQuery.list();
		if (list != null && list.size() > 0)
			rtnStr = list.get(0).toString();

		return rtnStr;

	}

	@Override
	public List<CmnTalukaMst> getTalukalst() {	
		String HQL = "FROM CmnTalukaMst as  t ";
		System.out.println("getTalukalst"+HQL);
	return (List<CmnTalukaMst>) entityManager.createQuery(HQL).getResultList();}

	@Override
	public DdoOffice getDdoOfficeDtls(Long dcpsDdoOfficeMstId) {try {
		String HQL = "FROM DdoOffice as  t  where t.dcpsDdoOfficeMstId = '" + dcpsDdoOfficeMstId+ "' ";
		
		return (DdoOffice) entityManager.createQuery(HQL).getSingleResult();
	} catch (NoResultException e) {
		return null;
	}}

	@Override
	public Serializable SaveApproveDdoOffice(DdoOffice ddoOffice) {
		Session currentSession = entityManager.unwrap(Session.class);
		Serializable saveId = 1;
		currentSession.update(ddoOffice);
		return (Integer) saveId;
	}

	@Override
	public List<CmnLookupMst> findDDOOffClass(Long lookupId) {	
		String HQL = "FROM CmnLookupMst as  t  where t.parentLookupId = '"+lookupId+"'";
	return (List<CmnLookupMst>) entityManager.createQuery(HQL).getResultList();}

	@Override
	public List<Object[]> getDDOOffForApproval(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DCPS_DDO_OFFICE_MST_ID,OFF_NAME,ADDRESS1,ADDRESS2,STATUS_FLAG,ddo_code FROM MST_DCPS_DDO_OFFICE where STATUS_FLAG=0 \r\n" + 
				"and ddo_code in (SELECT zp_ddo_code FROM rlt_zp_ddo_map where rept_ddo_code='"+ddoCode+"')";
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public List<Object[]> getAlreadySavedDataforDDO(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select off_name,state,district,taluka,town,village,address1,office_pin,office_city_class,dice_code,grant_applicable,tel_no1,fax,tribal_area_flag,\r\n" + 
				" email,hilly_area_flag,naxalite_area_flag from MST_DCPS_DDO_OFFICE where ddo_code='"+ddoCode+"'";
		System.out.println("getAlreadySavedDataforDDO------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}

	@Override
	public DdoOffice findDdoData(String ddoCode) {
		try {
			String HQL = "FROM DdoOffice as  t  where t.dcpsDdoCode = '"+ddoCode+"'";
			return (DdoOffice) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void updateApproveRejectStatus(DdoOffice ddoOffice) {
		// TODO Auto-generated method stub
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.update(ddoOffice);
	}

	@Override
	public List<Object[]> getLstTown() {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select city_id,city_name from cmn_city_mst";
		System.out.println("getLstTown------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


}
