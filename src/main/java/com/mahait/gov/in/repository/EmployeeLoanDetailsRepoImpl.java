package com.mahait.gov.in.repository;


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.OrgUserMst;

@Repository
public class EmployeeLoanDetailsRepoImpl implements EmployeeLoanDetailsRepo {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public Long saveEmployeeLoanDtls(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity) {
		// TODO Auto-generated method stub
	    Session session=entityManager.unwrap(Session.class);
	    return  (Long) session.save(loanEmployeeDtlsEntity);
	}

	@Override
	public List<Object[]> getEmpInfoBySevaarthId(String sevaarthId,String ddoCode) {
		// TODO Auto-generated method stub
	    Session session=entityManager.unwrap(Session.class);
	    String queryString=" select a.sevaarth_id,b.designation_name,c.ddo_office,a.pfacno,a.employee_full_name_en from employee_mst a inner join designation_mst b on a.designation_code=b.designation_code " + 
	    		" inner join org_ddo_mst c on c.ddo_code=a.ddo_code where a.sevaarth_id || a.employee_full_name_en " + 
	    		" ilike '%"+sevaarthId+"%' and dcps_gpf_flag = 'N' and a.ddo_code = '"+ddoCode+"'";
	    Query query=session.createSQLQuery(queryString);
	    System.out.println("-------"+queryString);
		return query.list();
	}

	@Override
	public List<Object[]> findAllEmpLoanDtls(String ddoCode) {
		// TODO Auto-generated method stub
		    Session session=entityManager.unwrap(Session.class);
		    String queryString=" select a.employee_full_name_en,c.department_allowdeduc_col_nm,b.loan_prin_amt,b.loan_interest_amt,b.loan_prin_emi_amt from employee_mst a inner join loan_employee_dtls b on a.sevaarth_id = b.sevaarth_id " + 
		    		" inner join department_allowdeduc_mst c on c.department_allowdeduc_code=b.department_allowdeduc_code where a.ddo_code ='"+ddoCode+"'";
		    Query query=session.createSQLQuery(queryString);
			return query.list();
	}

	@Override
	public Integer checkloanAlreadyTaken(BigInteger empId, BigInteger advId) {
		// TODO Auto-generated method stub
		    Session session=entityManager.unwrap(Session.class);
		    String queryString="select * from loan_employee_dtls where loan_type_id="+empId+" and employee_id="+advId;
		    Query query=session.createSQLQuery(queryString);
			return query.list().size();
	}

	@Override
	public List<LoanEmployeeDtlsEntity> getGpfAdvAppNo(String sevaarthId, OrgUserMst messages) {
		// TODO Auto-generated method stub
		 Session session=entityManager.unwrap(Session.class);
		String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"'"; // and t.loan_activate_flag=1";
		System.out.println(hqlQuery);
		return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
	}

	@Override
	public List<LoanEmployeeDtlsEntity> getGpfAppTrnDtlsByAppId(String sevaarthId, Integer appId, OrgUserMst messages) {
		// TODO Auto-generated method stub
		  Session session=entityManager.unwrap(Session.class);
			String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"'  and t.id="+appId;  //and t.loanactivateflag=1
			return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
	}

	@Override
	public LoanEmployeeDtlsEntity findLoanDetailsById(Integer loanEmpAdvId) {
		// TODO Auto-generated method stub
		return entityManager.find(LoanEmployeeDtlsEntity.class, loanEmpAdvId);
	}



	@Override
	public void updateLoanDetail(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity) {
		// TODO Auto-generated method stub
		 Session session=entityManager.unwrap(Session.class);
		  session.update(loanEmployeeDtlsEntity);
	}

	@Override
	public List<LoanEmployeeDtlsEntity> mergeMultipleGpfApp(String sevaarthId, OrgUserMst messages) {
		    Session session=entityManager.unwrap(Session.class);
			String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"' and t.loanactivateflag=1";  //and loanprininstno
			return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
	}

	@Override
	public List<DeptEligibilityForAllowAndDeductEntity> findLoanNames() {
		// TODO Auto-generated method stub
		 Session session=entityManager.unwrap(Session.class);
		String hqlQuery="From DeptEligibilityForAllowAndDeductEntity as t where t.departmentAllowdeducCode in (154,155,159,166,167)"; // and t.loan_activate_flag=1";
		System.out.println(hqlQuery);
		return (List<DeptEligibilityForAllowAndDeductEntity>) entityManager.createQuery(hqlQuery).getResultList();
	}
	
	
	

}
