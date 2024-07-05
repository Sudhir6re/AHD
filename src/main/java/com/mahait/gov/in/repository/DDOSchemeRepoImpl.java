package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgPostMst;

@Repository
public class DDOSchemeRepoImpl implements DDOSchemeRepo {
	
	@PersistenceContext
	EntityManager entityManager;


	@Override
	public List<Object[]> findAllEmployee(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select employee_id,sevaarth_id,employee_full_name_en,district_code,taluka_code,city_class from employee_mst where ddo_code = '"+userName+"' and is_active = '1'";
		System.out.println("HQL:"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


	@Override
	public String getDdoCodeForDDO(OrgPostMst createdByPost) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DDO_CODE FROM ORG_DDO_MST where POST_ID = '"+createdByPost+"'";

		Query query = currentSession.createSQLQuery(hql);
		return query.list().get(0).toString();
		// return null;
	}


	@Override
	public List<Object[]> getDDOCodeByLoggedInlocId(int i) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "from OrgDdoMst as ddo where locationCode="+i;
		System.out.println("HQL:"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


	@Override
	public List getAllSchemesForDDO(String lStrDDOCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		List lDcpsDdoSchemList = null;
		try {
			StringBuilder SBQuery = new StringBuilder();
			SBQuery.append("select rlt.Scheme_Code,mst.scheme_Name,rlt.sub_scheme_code,rlt.Ddo_Code FROM RLT_DCPS_DDO_SCHEMES rlt,Mst_Scheme mst where mst.scheme_Code=rlt.Scheme_Code and rlt.Ddo_Code in (select zp_ddo_code from rlt_zp_ddo_map where REPT_DDO_CODE="+lStrDDOCode+")");
			Query lQuery = currentSession.createQuery(SBQuery.toString());
			//lQuery.setParameter("ddoCode", lStrDDOCode);
			lDcpsDdoSchemList = lQuery.list();

		} catch (Exception e) {

		}
		return lDcpsDdoSchemList;
	}


	@Override
	public String districtName(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT DIstrict FROM MST_DCPS_DDO_OFFICE where lower(ddo_office)='yes' and ddo_code='"+ddoCode+"'";

		Query query = currentSession.createSQLQuery(hql);
		return query.list().get(0).toString();
		// return null;
	}


	@Override
	public List allTaluka(String districtID) {
		Session currentSession = entityManager.unwrap(Session.class);
		List lDcpsDdoSchemList = null;
		try {
			StringBuilder SBQuery = new StringBuilder();
			SBQuery.append("SELECT TALUKA_ID,TALUKA_NAME FROM CMN_TALUKA_MST where DISTRICT_ID="+districtID);
			Query lQuery = currentSession.createQuery(SBQuery.toString());
			//lQuery.setParameter("ddoCode", lStrDDOCode);
			lDcpsDdoSchemList = lQuery.list();

		} catch (Exception e) {

		}
		return lDcpsDdoSchemList;
	}

	  public List getSubDDOs (Long locId, String talukaId, String ddoSelected)
	  { List ddoDtls = null;
		Session currentSession = entityManager.unwrap(Session.class);
	    StringBuffer sb = new StringBuffer();
	    sb.append("SELECT ddo.DDO_CODE ,ddo.LOCATION_CODE,ddo.POST_ID,ddo.DDO_office FROM RLT_ZP_DDO_MAP rep, ORG_DDO_MST ddo, MST_DCPS_DDO_OFFICE office where lower(office.ddo_office)= 'yes' and ddo.DDO_OFFICE !='null' and office.status_flag=1 and ddo.DDO_CODE = rep.ZP_DDO_CODE and office.DDO_CODE=ddo.DDO_CODE and rep.status =1 and ((rep.REPT_DDO_POST_ID='" + locId + "') or (rep.FINAL_DDO_POST_ID='" + locId + "')) order by ddo.DDO_CODE asc");
	    
	    if ((talukaId != null) && (talukaId != "") && (Long.parseLong(talukaId) != -1L)) {
	      sb.append(" and office.taluka=" + talukaId );
	    }
	    if ((ddoSelected != null) && (ddoSelected != "")) {
	      sb.append(" and (office.ddo_code like '" + ddoSelected + "%' or ddo.ddo_office like '%" + ddoSelected + "%') ");
	    }
	    Query lQuery = currentSession.createQuery(sb.toString());
	    ddoDtls = lQuery.list();
	    return ddoDtls;
		}


	@Override
	public List getpostRole(OrgPostMst createdByPost) {
		Session currentSession = entityManager.unwrap(Session.class);
		List lDcpsDdoSchemList = null;
		try {
			StringBuilder SBQuery = new StringBuilder();
			SBQuery.append("SELECT ROLE_ID FROM ACL_POSTROLE_RLT where post_id="+createdByPost);
			Query lQuery = currentSession.createQuery(SBQuery.toString());
			//lQuery.setParameter("ddoCode", lStrDDOCode);
			lDcpsDdoSchemList = lQuery.list();

		} catch (Exception e) {

		}
		return lDcpsDdoSchemList;
	}

	

}
