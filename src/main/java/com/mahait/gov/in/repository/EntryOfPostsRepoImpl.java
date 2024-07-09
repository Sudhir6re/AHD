package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnBranchMst;
import com.mahait.gov.in.entity.HrPayOfficepostMpg;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;

@Repository
public class EntryOfPostsRepoImpl implements EntryOfPostsRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<MstDesignationEntity> getActiveDesig(Long lLngFieldDept) {
		Session session = getSession();
		String HQL_QUERY = "select mst from MstDcpsDesignation dcpsDesig, MstDesignationEntity mst  where mst.desginationId=dcpsDesig.orgDesignationId and mst.isActive=1 and  dcpsDesig.fieldDeptId =  "
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

	@Override
	public List<OrgDdoMst> getDDOCodeByLoggedInlocId(long locId) {
		List<OrgDdoMst> ddoList = null;
		Session hibSession = getSession();
		StringBuffer query = new StringBuffer();
		query.append(" from OrgDdoMst as ddo where locationCode='" + locId + "' ");
		Query sqlQuery = hibSession.createQuery(query.toString());
		ddoList = sqlQuery.list();
		return ddoList;
	}

	@Override
	public List getAllBranchList(long langId) {
		Session session = getSession();
		Criteria crit = session.createCriteria(CmnBranchMst.class);
		crit.add(Restrictions.eq("cmnLanguageMst.langId", Long.valueOf(langId)));
		return crit.list();
	}

	@Override
	public List<HrPayOrderMst> getAllOrderData(long locId) {
		List orderMstList = null;
		Session hibSession = getSession();
		String strQuery = "from HrPayOrderMst orderMst where orderMst.locationCode in (" + locId
				+ ") order by orderMst.orderName";
		Query query = hibSession.createQuery(strQuery);
		orderMstList = query.list();
		return orderMstList;
	}

	@Override
	public List getAllOfficesFromDDO(String ddoCode) {
		Session session = getSession();
		String HQL_QUERY = " from DdoOffice where dcpsDdoCode='" + ddoCode + "'";
		Query query = session.createQuery(HQL_QUERY);
		List resultList = query.list();
		return resultList;
	}

	@Override
	public List<Object[]> getSubjectList() {
    	List subList = null;
    	Session session = getSession();
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT * FROM FRM_SUBJECT_MST");
    	Query query = session.createSQLQuery(sb.toString());
    	subList = query.list();
    	return subList;
    }

	@Override
	public List getSubOfficesFromDDONew(Long postId) {
		List SubOffices = null;
    	Session session = getSession();
    	String strQuery = "SELECT FIELD_ID,SUB_FIELD_NAME FROM SUB_FIELD_DEPT";
    	Query query = session.createSQLQuery(strQuery);
    	SubOffices = query.list();
    	return SubOffices;
	}

	@Override
	public String districtName(String ddoCode) {
		String districtId="";
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT distinct DIstrict FROM MST_DCPS_DDO_OFFICE where ddo_code='"+ddoCode+"' and lower(ddo_office)='yes'");
		Query query = session.createSQLQuery(sb.toString());
		districtId = (String) query.uniqueResult();
		return districtId;
	}

	@Override
	public List allTaluka(String districtID) {
		List talukaList=null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TALUKA_ID,TALUKA_NAME FROM CMN_TALUKA_MST where DISTRICT_ID="+districtID);
		Query query = session.createSQLQuery(sb.toString());
		talukaList = query.list();
		return talukaList;
	}

	@Override
	public List getSubDDOsOffc(long postId, String talukaId, String ddoSelected) {
    	List ddoDtls = null;
    	Session session = getSession();
    	StringBuffer sb = new StringBuffer();
    	
    	//Added by roshan 
    	sb.append("SELECT ddo.DDO_CODE ,ddo.DDO_office FROM RLT_ZP_DDO_MAP rep, ORG_DDO_MST ddo, mst_dcps_ddo_office office where lower(office.ddo_office)= 'yes' and ddo.DDO_OFFICE !='null' and office.status_flag=1 and ddo.DDO_CODE = rep.ZP_DDO_CODE and office.ddo_code=ddo.ddo_code and rep.status =1 and ((rep.REPT_DDO_POST_ID='" + postId + "') or (rep.FINAL_DDO_POST_ID='" + postId + "'))");
    	if((talukaId!=null)&&(talukaId!="")&&(Long.parseLong(talukaId)!=-1)){
    		sb.append(" and office.taluka="+talukaId);
    	}
    	if((ddoSelected!=null)&&(ddoSelected!="")){
    		sb.append(" and (office.ddo_code like '%"+ddoSelected+"%' or ddo.ddo_office like '%"+ddoSelected+"%')");
    	}
    	Query query = session.createSQLQuery(sb.toString());
    	ddoDtls = query.list();
    	return ddoDtls;
    	
    }

	@Override
	public Long savePostDetails(OrgPostDetailsRlt orgPostDetailsRlt) {
		Session session = getSession();
		return (Long) session.save(orgPostDetailsRlt);
	}

	@Override
	public Long savePost(OrgPostMst orgPostMst) {
		Session session = getSession();
		return (Long) session.save(orgPostMst);
	}

	@Override
	public Long savePostDetails(HrPayOfficepostMpg hrPayOfficepostMpg) {
		Session session = getSession();
		return (Long) session.save(hrPayOfficepostMpg);
	}

}
