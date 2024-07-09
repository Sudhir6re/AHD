package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class EmployeeIncrementRepoImpl implements EmployeeIncrementRepo {
	
	@PersistenceContext
	EntityManager entityManager;


	@Override
	public String officeName(String userName) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select ddo_name from ddo_reg_mst  where ddo_code  = '" + userName + "'";
		Query query = currentSession.createSQLQuery(hql);
		return query.list().get(0).toString();
	}


	@Override
	public List<Object[]> lstEmpforMTR21(String orderNo, int levelRoleVal, String ddoCode) {//,String locId
		Session currentSession = entityManager.unwrap(Session.class);
		
		String hql=null;
		if(levelRoleVal==1)
		{
			hql ="select o.EMP_FNAME,o.EMP_MNAME,o.EMP_LNAME,d.DSGN_NAME,fix.PAY_FIX_DATE,fix.OLD_BASIC,fix.NEW_BASIC,fix.NXT_INCR_DATE,fix.INCR_ORDER_DATE,fix.PAY_COMMISSION_ID\r\n" + 
					"FROM ORG_EMP_MST o,HR_EIS_EMP_MST e,ORG_USER_MST u,ORG_USERPOST_RLT uu,ORG_POST_DETAILS_RLT post,ORG_DESIGNATION_MST d,HR_EIS_OTHER_DTLS ooo,HR_PAYFIX_MST fix\r\n" + 
					"where o.EMP_ID=e.EMP_MPG_ID and o.USER_ID=u.USER_ID and u.USER_ID=uu.USER_ID and uu.ACTIVATE_FLAG = 1 and post.POST_ID = uu.POST_ID and fix.USER_ID=u.USER_ID and \r\n" + 
					"post.DSGN_ID=d.DSGN_ID and fix.INCR_CERTI_ORDER_NO= '"+orderNo+"'  ";//and post.LOC_ID ='"+locId+"'
		}else {
			hql ="select o.EMP_FNAME,o.EMP_MNAME,o.EMP_LNAME,d.DSGN_NAME,fix.PAY_FIX_DATE,fix.OLD_BASIC,fix.NEW_BASIC,fix.NXT_INCR_DATE,fix.INCR_ORDER_DATE,fix.PAY_COMMISSION_ID\r\n" + 
					"FROM ORG_EMP_MST o,HR_EIS_EMP_MST e,ORG_USER_MST u,ORG_USERPOST_RLT uu,ORG_POST_DETAILS_RLT post,ORG_DESIGNATION_MST d,HR_EIS_OTHER_DTLS ooo,HR_PAYFIX_MST fix\r\n" + 
					"where o.EMP_ID=e.EMP_MPG_ID and o.USER_ID=u.USER_ID and u.USER_ID=uu.USER_ID and uu.ACTIVATE_FLAG = 1 and post.POST_ID = uu.POST_ID and fix.USER_ID=u.USER_ID and \r\n" + 
					"post.DSGN_ID=d.DSGN_ID and fix.INCR_CERTI_ORDER_NO= '"+orderNo+"' ";//and post.LOC_ID = '"+locId+"'
		}
		
		System.out.println("lstEmpforMTR21------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


	@Override
	public List<Object[]> getIncrementDataForReptDDO(String userName, String currYear) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT  distinct p.INCR_CERTI_ORDER_NO,p.loc_id,p.INCR_ORDER_DATE,p.status,zp.zp_ddo_code,ddo.ddo_office \r\n" + 
				"FROM RLT_ZP_DDO_MAP zp, ORG_DDO_MST ddo, HR_PAYFIX_MST p  where p.LOC_ID = cast(ddo.LOCATION_CODE as BigInt) and zp.ZP_DDO_CODE = ddo.DDO_CODE and p.activate_Flag in (0,1,-1) \r\n" + 
				"and zp.ZP_DDO_CODE = ddo.DDO_CODE and zp.REPT_DDO_CODE ='"+userName+"'  order by zp.zp_ddo_code";//and year(p.CREATED_DATE)="+currYear+"
		System.out.println("getEmpDataForIncrementApproval------" + hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}



	

}
