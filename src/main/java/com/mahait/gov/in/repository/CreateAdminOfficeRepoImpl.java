package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class CreateAdminOfficeRepoImpl implements CreateAdminOfficeRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> getAllDDOOfficeDtlsData(String districtName, String talukaNametName, String adminType) {
		List list = null;

		Session hibSession = entityManager.unwrap(Session.class);

		StringBuffer strQuery = new StringBuffer();
		strQuery.append(
				"SELECT zp.ZP_DDO_CODE,zp.REPT_DDO_CODE,zp.FINAL_DDO_CODE,zp.SPECIAL_DDO_CODE,zp.ZPLEVEL,zp.STATUS FROM  RLT_ZP_DDO_MAP zp ");
		strQuery.append(" inner join mst_dcps_ddo_office office on zp.zp_ddo_code=office.ddo_code ");
		strQuery.append(" where zp.LANG_ID =1 and zp.status is not null ");
		if ((districtName != null) && (districtName != "") && (Long.parseLong(districtName) != -1)) {
			strQuery.append(" and office.district='" + districtName + "'");
		}
		if ((talukaNametName != null) && (talukaNametName != "") && (Long.parseLong(talukaNametName) != -1)) {
			strQuery.append(" and office.taluka='" + talukaNametName + "'");
		}

		if ((adminType != null) && (adminType != "") && (Long.parseLong(adminType) != -1)) {
			strQuery.append(" and zp.ZP_DDO_CODE like '" + adminType + "%'");
		}
		strQuery.append(" order by ZP_MAP_ID desc");

		// logger.info("Details Query :"+strQuery.toString());
		Query query = hibSession.createSQLQuery(strQuery.toString());
		list = query.list();
		return list;
	}

}
