package com.mahait.gov.in.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.GROrderDocumentEntity;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.ZpRltDdoMap;

@Repository
public class OrderMasterRepoImpl implements OrderMasterRepo {
	
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
	public List<Object[]> getSubGRType(long parentGrpId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT * FROM cmn_lookup_mst where PARENT_LOOKUP_ID="+parentGrpId+" order by ORDER_NO ASC";
		System.out.println("HQL:"+hql);
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}


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
	public List<CmnTalukaMst> gettalukalst(String districtId) {
		String HQL = "FROM CmnTalukaMst as  t  where districtId='"+districtId+"'";
		return (List<CmnTalukaMst>) entityManager.createQuery(HQL).getResultList();

	}


	@Override
	public List<Long> findUsertype(String ddoCode) {
		Session currentSession = entityManager.unwrap(Session.class);

		String hql = "select count(1) from rlt_Zp_ddo_map where rept_ddo_code='"+ddoCode+"'";
		Query query = currentSession.createSQLQuery(hql).addScalar("count", LongType.INSTANCE);
		List<Long> lstresult = query.list();
		return lstresult;

	}


	@Override
	public Integer saveGrOrder(HrPayOrderMst payOrderMst, MultipartFile[] files) {
		// TODO Auto-generated method stub
		Session session=entityManager.unwrap(Session.class);
		Integer saveId=(Integer) session.save(payOrderMst);
		return saveId;
	}


	@Override
	public Integer saveAdvanceDocuments(GROrderDocumentEntity grOrderDocumentEntity) {
		// TODO Auto-generated method stub
		Session session=entityManager.unwrap(Session.class);
		Integer saveId=(Integer) session.save(grOrderDocumentEntity);
		return saveId;
	}


	

}
