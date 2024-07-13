package com.mahait.gov.in.repository;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.RltDCPSDdoSchemeEntity;
import com.mahait.gov.in.model.MstSchemeModel;

@Repository
public class MstSchemeRepoImpl implements MstSchemeRepo {
	@PersistenceContext
	EntityManager manager;
		
	@Override
	public List<MstScheme> findAllScheme() {
		// TODO Auto-generated method stub
//		String HQL = "FROM MstScheme as t,RltDcpsDdoScheme as r where r.dcpsSchemeCode=t.schemeCode ORDER BY t.schemeId DESC";
		String HQL = "FROM MstScheme as t ORDER BY t.schemeId DESC";
		return (List<MstScheme>) manager.createQuery(HQL).getResultList();
		
	}

	@Override
	public List<MstSchemeModel> findAllSchemename() {
		// TODO Auto-generated method stub
		Session currentSession = manager.unwrap(Session.class);
		String HQL = "SELECT mst.SCHEME_CODE,mst.SCHEME_NAME FROM MST_SCHEME mst  "
				+ " inner join  RLT_DCPS_DDO_SCHEMES rlt on rlt.SCHEME_CODE = mst.SCHEME_CODE  ";

Query query = currentSession.createSQLQuery(HQL);

List<Object[]> lstprop = query.list();
List<MstSchemeModel> lstObj = new ArrayList<>();
if (!lstprop.isEmpty()) {
    for (Object[] objLst : lstprop) {
    	MstSchemeModel obj = new MstSchemeModel();
    	obj.setDcpsDdoSchemeCode(StringHelperUtils.isNullString(objLst[0]));
        obj.setSchemeName(StringHelperUtils.isNullString(objLst[1]));
        lstObj.add(obj);
    }
}
return lstObj;
}

	@Override
	public List<MstDcpsBillGroup> findAllMpgSchemeBillGroupByDDOCode(String dDOCode, int roleid) {
		String HQL = "FROM MstDcpsBillGroup as t where dcpsDdoCode ='"+dDOCode+"'ORDER BY t.dcpsDdoBillGroupId DESC";
		return (List<MstDcpsBillGroup>) manager.createQuery(HQL).getResultList();
		
	}

	@Override
	public BigInteger findNumberOfEmployeeInBillGroup(String ddoCode, BigInteger billGroupId, int monthName, int yearName, int paybillType) {
		Session currentSession = manager.unwrap(Session.class);
		yearName = yearName -1;
		String HQL;

		if(monthName >= 1 && monthName < 10) {
		HQL = "select count(emst.employee_id) from employee_mst emst where emst.is_active = 1 and  emst.ddo_code = '"+ddoCode.trim()+"' and emst.billgroup_id = '"+billGroupId+"' and to_char(emst.doj,'YYYY-MM')<='20"+yearName+"-0"+monthName+"' "
				+ " and  (to_char(emst.super_ann_date,'YYYY-MM')>='20"+yearName+"-0"+monthName+"' and   to_char(emst.emp_service_end_date,'YYYY-MM')>='20"+yearName+"-0"+monthName+"') ";
		}else {
		HQL = "select count(emst.employee_id) from employee_mst emst where emst.is_active = 1 and  emst.ddo_code = '"+ddoCode.trim()+"' and emst.billgroup_id = '"+billGroupId+"' and to_char(emst.doj,'YYYY-MM')<='20"+yearName+"-"+monthName+"' "
				+ " and  (to_char(emst.super_ann_date,'YYYY-MM')>='20"+yearName+"-"+monthName+"' and   to_char(emst.emp_service_end_date,'YYYY-MM')>='20"+yearName+"-"+monthName+"') ";
		}
		
		System.out.println(HQL);
		Query query = currentSession.createSQLQuery(HQL);
		return   (BigInteger) query.list().get(0);
	}


	
}
