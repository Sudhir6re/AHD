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
import com.mahait.gov.in.entity.MstEmployeeEntity;

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

}
