package com.mahait.gov.in.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnBranchMst;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.HrPayOfficepostMpg;
import com.mahait.gov.in.entity.HrPayOrderHeadMpg;
import com.mahait.gov.in.entity.HrPayOrderHeadPostMpg;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.HrPayPsrPostMpg;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.SubjectPostMpg;

@Repository
public class EntryOfPostsRepoImpl implements EntryOfPostsRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<MstDesignationEntity> getActiveDesig(Long lLngFieldDept) {
		Session session = getSession();
		String HQL_QUERY = "select mst from MstDcpsDesignation dcpsDesig, MstDesignationEntity mst  where mst.desginationId=dcpsDesig.orgDesignation and mst.isActive='1' and  dcpsDesig.fieldDeptId =  "
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
		String HQL_QUERY = " from MstDcpsBillGroup where Loc_Id= " + locId
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
		String districtId = "";
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT distinct DIstrict FROM MST_DCPS_DDO_OFFICE where ddo_code='" + ddoCode
				+ "' and lower(ddo_office)='yes'");
		Query query = session.createSQLQuery(sb.toString());
		districtId = (String) query.uniqueResult();
		return districtId;
	}

	@Override
	public List allTaluka(String districtID) {
		List talukaList = null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT TALUKA_ID,TALUKA_NAME FROM CMN_TALUKA_MST where DISTRICT_ID=" + districtID);
		Query query = session.createSQLQuery(sb.toString());
		talukaList = query.list();
		return talukaList;
	}

	@Override
	public List getSubDDOsOffc(long postId, String talukaId, String ddoSelected) {
		List ddoDtls = null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();

		// Added by roshan
		sb.append(
				"SELECT ddo.DDO_CODE ,ddo.DDO_office FROM RLT_ZP_DDO_MAP rep, ORG_DDO_MST ddo, mst_dcps_ddo_office office where lower(office.ddo_office)= 'yes' and ddo.DDO_OFFICE !='null' and office.status_flag=1 and ddo.DDO_CODE = rep.ZP_DDO_CODE and office.ddo_code=ddo.ddo_code and rep.status =1 and ((rep.REPT_DDO_POST_ID='"
						+ postId + "') or (rep.FINAL_DDO_POST_ID='" + postId + "'))");
		if ((talukaId != null) && (talukaId != "") && (Long.parseLong(talukaId) != -1)) {
			sb.append(" and office.taluka=" + talukaId);
		}
		if ((ddoSelected != null) && (ddoSelected != "")) {
			sb.append(" and (office.ddo_code like '%" + ddoSelected + "%' or ddo.ddo_office like '%" + ddoSelected
					+ "%')");
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

	@Override
	public List getSubLocationDDOs(BigInteger loggedInPostId) {
		List ddoDtls = null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT ddo.LOCATION_CODE FROM RLT_ZP_DDO_MAP rep, ORG_DDO_MST ddo where ddo.DDO_CODE = rep.ZP_DDO_CODE and ((rep.REPT_DDO_POST_ID='"
						+ loggedInPostId + "') or (rep.FINAL_DDO_POST_ID='" + loggedInPostId + "'))");
		Query query = session.createSQLQuery(sb.toString());
		ddoDtls = query.list();
		return ddoDtls;
	}

	@Override
	public List getFilterDdoCode(String locationcodeArray) {
		List orderMstList = null;
		Session hibSession = getSession();
		String strQuery = " SELECT DDO_CODE,DDO_OFFICE FROM org_ddo_mst where LOCATION_CODE in (" + locationcodeArray
				+ ") order by DDO_CODE asc";
		Query query = hibSession.createSQLQuery(strQuery);
		orderMstList = query.list();
		return orderMstList;
	}

	@Override
	public List getSubDDOsOffc(BigInteger loggedInPostId, String talukaId, String ddoSelected) {
		List ddoDtls = null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT ddo.DDO_CODE ,ddo.DDO_office FROM RLT_ZP_DDO_MAP rep, ORG_DDO_MST ddo, mst_dcps_ddo_office office where lower(office.ddo_office)= 'yes' and ddo.DDO_OFFICE !='null' and office.status_flag=1 and ddo.DDO_CODE = rep.ZP_DDO_CODE and office.ddo_code=ddo.ddo_code and rep.status =1 and ((rep.REPT_DDO_POST_ID='"
						+ loggedInPostId + "') or (rep.FINAL_DDO_POST_ID='" + loggedInPostId + "'))");
		if ((talukaId != null) && (talukaId != "") && (Long.parseLong(talukaId) != -1)) {
			sb.append(" and office.taluka=" + talukaId);
		}
		if ((ddoSelected != null) && (ddoSelected != "")) {
			sb.append(" and (office.ddo_code like '%" + ddoSelected + "%' or ddo.ddo_office like '%" + ddoSelected
					+ "%')");
		}
		Query query = session.createSQLQuery(sb.toString());
		ddoDtls = query.list();
		return ddoDtls;

	}

	@Override
	public List getDDODtls(String ddoCode) {
		List ddoDtls = null;
		Session session = getSession();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT LOCATION_CODE,HOD_LOC_CODE,post_ID FROM ORG_DDO_MST where DDO_CODE='" + ddoCode + "'");
		Query query = session.createSQLQuery(sb.toString());
		ddoDtls = query.list();
		return ddoDtls;

	}

	@Override
	public List findHrPayOrderHeadMpg(long orderId) {
		Session session = getSession();
		String HQL_QUERY = " from HrPayOrderHeadMpg where orderId='" + orderId + "'";
		Query query = session.createQuery(HQL_QUERY);
		List resultList = query.list();
		return resultList;

	}

	@Override
	public void create(HrPayOrderHeadMpg orderHeadMpg) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		session.save(orderHeadMpg);
	}

	@Override
	public OrgPostMst findPost(long longValue) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		return session.find(OrgPostMst.class, longValue);
	}

	@Override
	public Long submitSubject(SubjectPostMpg subjectPostMpg) {
		// TODO Auto-generated method stub
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(subjectPostMpg);

	}

	@Override
	public Long save(HrPayPsrPostMpg postPsrMpg) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(postPsrMpg);
	}

	@Override
	public Long savePostDtls(OrgPostDetailsRlt orgPostDtlRlt) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(orgPostDtlRlt);

	}

	@Override
	public Long save(HrPayOrderHeadPostMpg orderHeadPostmpg) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(orderHeadPostmpg);
	}

	@Override
	public Long save(HrPayOfficepostMpg hrOfficePostMpg) {
		Session session = entityManager.unwrap(Session.class);
		return (Long) session.save(hrOfficePostMpg);

	}

	@Override
	public MstDesignationEntity finddesignationByCode(Long valueOf) {
		Session session = entityManager.unwrap(Session.class);
		return session.find(MstDesignationEntity.class, valueOf);
	}

	@Override
	public long getNextPsrNo() {
		long nextPsr = 0;
		Session hibSession = getSession();
		String nextPsrlstr = "";
		StringBuffer strQuery = new StringBuffer();
		strQuery.append("Select max(psrId) from HrPayPsrPostMpg psr ");
		List psrList = hibSession.createQuery(strQuery.toString()).list();
		if (psrList.get(0) == null) {
			nextPsr = 1l;
		} else {
			nextPsr = Long.parseLong(psrList.get(0).toString()) + 1;
		}
		return nextPsr;
	}

	@Override
	public DdoOffice findOfficeByfficeId(Long valueOf) {
		Session session = entityManager.unwrap(Session.class);
		return session.find(DdoOffice.class, valueOf);
	}

	@Override
	public DdoOffice findOfficeByOfficeId(Long valueOf) {
		Session session = entityManager.unwrap(Session.class);
		return session.find(DdoOffice.class, valueOf);
	}

	@Override
	public List<HrPayOrderMst> getAllOrderData(long locId, String ddoCode) {
		List<HrPayOrderMst> orderMstList = null;
		Session hibSession = getSession();
		String strQuery = "from HrPayOrderMst orderMst where orderMst.locationCode = :locId or ddoCode=:ddoCode order by orderMst.orderName";
		Query query = hibSession.createQuery(strQuery);
		query.setParameter("locId", Long.toString(locId));
		query.setParameter("ddoCode", ddoCode);
		orderMstList = query.list();
		return orderMstList;
	}

	@Override
	public List<HrPayOrderMst> findGrOrderDetails(Long orderId) {
		List<HrPayOrderMst> orderMstList = null;
		Session hibSession = getSession();
		String strQuery = "from HrPayOrderMst orderMst where orderMst.orderId = :orderId";
		Query query = hibSession.createQuery(strQuery);
		query.setParameter("orderId", orderId);
		orderMstList = query.list();
		return orderMstList;
	}

	@Override
	public List getPostNameForDisplay(String locId, String lPostName, String PsrNo, String BillNo, String Dsgn,
			String ddoSelected) {
		List postNameList = new ArrayList();
		Session hibSession = getSession();
		StringBuffer sb = new StringBuffer();
		
	//	Long locId1=Long.parseLong(locId);
		//Long ddoSelected1=Long.parseLong(ddoSelected);
		
		sb.append(
				"select pd.post_name, pd.post_id, (select o.employee_full_name_en from employee_mst o, org_userpost_rlt up ");
		sb.append(
				" where o.user_id = up.user_id and up.post_id = pd.post_id and up.end_date is null and up.activate_flag = 1), ds.designation_name , P.PSR_NO    ");

		sb.append(
				"  ,(select mp.ddo_code from org_ddo_mst mp where p.loc_id = cast(mp.location_code as bigint)) BillNo, org.post_type_lookup_id, cmn.lookup_name from org_post_details_rlt pd, designation_mst ds, org_post_mst org, cmn_lookup_mst cmn");
		sb.append("  , HR_PAY_POST_PSR_MPG p ");
		sb.append("  where pd.loc_id in (" + locId
				+ ") and P.POST_ID = PD.POST_ID and  pd.dsgn_id = ds.designation_id  ");
		if (PsrNo != null && !(PsrNo.trim()).equals(""))
			sb.append("  and p.psr_no = " + PsrNo);
		else if ((ddoSelected != null) && (ddoSelected != "")) {
			sb.append(" and pd.loc_id =(select cast(loc.location_code as bigint) from org_ddo_mst loc where loc.ddo_code='" + ddoSelected
					+ "')");
		} else if (BillNo != null && !(BillNo.trim()).equals(""))
			sb.append("  and mp.bill_no  = " + BillNo);
		else if (Dsgn != null && !(Dsgn.trim()).equals(""))
			sb.append("  and  upper(ds.designation_id) like  upper('%" + Dsgn + "%')  ");
		else
			sb.append("  and  upper(pd.post_name) like  upper('%" + lPostName + "%') ");
		sb.append(" and pd.post_id=org.post_id ");
		sb.append(" and cmn.lookup_id=org.post_type_lookup_id and org.activate_flag=1 ");
		sb.append("   order by pd.CREATED_DATE desc  ");

		
		System.out.println(sb.toString());
		
		Query query = hibSession.createSQLQuery(sb.toString());
		postNameList = query.list();
		return postNameList;
	}

	@Override
	public OrgPostMst findPostObj(Long postId) {
		Session hibSession = getSession();
		OrgPostMst save = hibSession.get(OrgPostMst.class, postId);
		return save;
	}

}
