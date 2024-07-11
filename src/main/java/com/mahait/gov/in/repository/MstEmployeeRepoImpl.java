package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.type.LongType;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
import com.mahait.gov.in.model.MstEmployeeModel;

@Repository
public class MstEmployeeRepoImpl implements MstEmployeeRepo {
	@PersistenceContext
	EntityManager entityManager;

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findDDOScreenDataTable(String ddoCode) {

		Session currentSession = entityManager.unwrap(Session.class);
		
//		String hql = "select  a.department_code,  b.sub_department_id,   a.department_name_en,   a.department_name_mr, \r\n"
//				+ "b.sub_department_name_en,   b.sub_department_name_mr,b.sub_department_short_name  From org_ddo_mst a inner join sub_department_mst b \r\n"
//				+ "on a.department_code = b.department_code inner join ddo_reg_mst c on \r\n" + 
//				"c.sub_department_code = b.sub_department_code where b.is_active ='1' and c.ddo_code = '"+ddoCode+"'";
		
		
		String hql = "SELECT cast(CM.loc_id as int),CM.loc_name FROM org_ddo_mst DM,cmn_location_mst CM \r\n"
				+ "WHERE DM.ddo_Code = '06710100040' AND cast(CM.loc_id as varchar) = DM.hod_loc_code";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getInstitueDtls(String ddocode) {
		Session currentSession = entityManager.unwrap(Session.class);
//		String hql = "select org_inst_name,tel_no_1,tel_no_2,email_id from org_inst_mst where ddo_reg_id in (select ddo_reg_id from ddo_reg_mst where ddo_code='"
//				+ ddocode + "')";
		String hql = "select org_inst_name,tel_no_1,tel_no_2,email_id from org_inst_mst where cast(ddo_reg_id as bigint) ='06710100040'";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
		/*
		 * String HQL = "FROM MstCadreEntity as t ORDER BY t.cadreId DESC"; return
		 * (List<MstCadreEntity>) entityManager.createQuery(HQL).getResultList();
		 */
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCadreMstData() {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "SELECT a.cadre_id, " +
		/*
		 * "b.sub_department_name_en, "+ "b.sub_department_name_mr, "+
		 */
				"c.group_name_en, " + "c.group_name_mh, " + "a.cadre_code, " + "a.cadre_name, " + "a.ministerial_flag, "
				+ "a.superannuation_age, " + "a.is_active " + "FROM   cadre_mst a, " +
				/* "sub_department_mst b, "+ */
				"cadre_group_mst c " +
				/* "WHERE  a.org_category_id = b.sub_department_id "+ */
				"Where a.group_id = c.id and a.is_active='1'  order by c.id";
		Query query = currentSession.createSQLQuery(hql);
		return (List<Object[]>) query.list();
		/*
		 * String HQL = "FROM MstCadreEntity as t ORDER BY t.cadreId DESC"; return
		 * (List<MstCadreEntity>) entityManager.createQuery(HQL).getResultList();
		 */
	}
	@Override
	public List<MstCadreGroupEntity> getGISGroup() {
		Session hibSession = entityManager.unwrap(Session.class);
		List<MstCadreGroupEntity> result = null;
		result = entityManager.createQuery("from MstCadreGroupEntity", MstCadreGroupEntity.class).getResultList();
		return result;
	}
	@Override
	public List<MstRoleEntity> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
}