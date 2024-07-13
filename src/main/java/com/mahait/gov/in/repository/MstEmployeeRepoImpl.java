package com.mahait.gov.in.repository;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.MstCadreGroupEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstRoleEntity;

@Repository
public class MstEmployeeRepoImpl implements MstEmployeeRepo {
	@PersistenceContext
	EntityManager entityManager;

	
	
	@Override
	public DdoOffice findAllGroup(String ddocode) {
		String HQL = "FROM DdoOffice as  t  where  t.dcpsDdoCode = '" + ddocode + "'";
		return (DdoOffice) entityManager.createQuery(HQL).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MstEmployeeEntity> findAllWorkingEmployeeByDDOCodeAndBillGroup(String ddoCode, BigInteger billGroupId,
			int month, int year) {
		String HQL = null;
		year = year - 1;
		try {
			if (month >= 1 && month < 10) {

				HQL = "FROM MstEmployeeEntity as  t  where t.ddoCode = '" + ddoCode.trim() + "' and t.billGroupId = '"+ billGroupId + "' and to_char(t.doj,'YYYY-MM')<='20" + year + "-0" + month
						+ "' and  (to_char(t.superAnnDate,'YYYY-MM')>='20" + year + "-0" + month
						+ "' and   to_char(t.empServiceEndDate,'YYYY-MM')>='20" + year + "-0" + month
						+ "') AND t.isActive='1' ORDER BY t.employeeFullNameEn";
			} else
				HQL = "FROM MstEmployeeEntity as  t  where t.ddoCode = '" + ddoCode.trim() + "' and t.billGroupId = '"+ billGroupId + "' and to_char(t.doj,'YYYY-MM')<='20" + year + "-" + month
						+ "' and  (to_char(t.superAnnDate,'YYYY-MM')>='20" + year + "-" + month
						+ "' and   to_char(t.empServiceEndDate,'YYYY-MM')>='20" + year + "-" + month
						+ "') AND t.isActive='1' ORDER BY t.employeeFullNameEn";
			return (List<MstEmployeeEntity>) entityManager.createQuery(HQL).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public int getpayCommissionAgainstEmployee(String sevaarthId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = "select b.pay_commission_code from employee_mst a inner join pay_commission_mst b on a.pay_commission_code = b.pay_commission_code where a.sevaarth_id ='"
				+ sevaarthId + "'";
		Query query = currentSession.createSQLQuery(hql);
		return (int) query.list().get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findEmployeeAllowanceDeduction(String sevaarthId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String hql = " Select a.sevaarth_id,a.department_code,a.department_allowdeduc_code,b.employee_full_name_en,c.department_allowdeduc_name,c.is_type,c.department_allowdeduc_col_nm,"
				+ "cc.group_name_en,cc.gis_amount,b.grade_pay,b.pyhical_handicapped,ccc.group_name_en as gisgroup from employee_allowdeduc_mpg a inner join  employee_mst b on b.employee_id = a.employee_id "
				+ " inner join department_allowdeduc_mst c on a.department_allowdeduc_code = c.department_allowdeduc_code  inner join cadre_group_mst  cc    on b.emp_class = cc.id left outer join cadre_group_mst  ccc    on CAST(b.gisgroup AS integer)  = ccc.id  "
				+ " where a.sevaarth_id= '" + sevaarthId
				+ "'  and c.is_active='1' ORDER BY c.is_type,a.department_allowdeduc_code ASC  ";
		Query query = currentSession.createSQLQuery(hql);
		return query.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public EmployeeAllowDeducComponentAmtEntity findGRPComponentsData(String sevaarthId, int allowDedCode) {
		try {
			String HQL = "FROM EmployeeAllowDeducComponentAmtEntity as  t  where t.sevaarthId = '" + sevaarthId
					+ "' and t.deptallowcode = " + allowDedCode + "";
			return (EmployeeAllowDeducComponentAmtEntity) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public LoanEmployeeDtlsEntity findGPFADetails(String sevaarthid, int commoncodeComponentGpfaCode) {
		try {
			String HQL = "FROM LoanEmployeeDtlsEntity as  t  where t.totalRecoveredInst<=t.loanprininstno and t.sevaarthid ='"
					+ sevaarthid + "' and t.loanactivateflag = 1";
			return (LoanEmployeeDtlsEntity) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	@Override
	public LoanEmployeeDtlsEntity findGPFAdvDetails(String sevaarthid, int commoncodeComponentGpfaCode) {
		try {
			String HQL = "FROM LoanEmployeeDtlsEntity as  t  where t.totalRecoveredInstGpfII<=t.sancInstGpfII and t.sevaarthid ='"
					+ sevaarthid + "' and t.loanactivateflag = 1";
			System.out.println("-----------"+HQL);
			return (LoanEmployeeDtlsEntity) entityManager.createQuery(HQL).getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


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
