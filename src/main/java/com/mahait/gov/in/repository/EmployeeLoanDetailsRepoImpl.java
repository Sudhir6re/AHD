package com.mahait.gov.in.repository;


import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;


@Repository
public class EmployeeLoanDetailsRepoImpl implements EmployeeLoanDetailsRepo {
	
	@PersistenceContext
	EntityManager entityManager;
//
//	@Override
//	public int saveEmployeeLoanDtls(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity) {
//		// TODO Auto-generated method stub
//	    Session session=entityManager.unwrap(Session.class);
//	    return  (int) session.save(loanEmployeeDtlsEntity);
//	}

//	@Override
//	public List<Object[]> getEmpInfoBySevaarthId(String sevaarthId) {
//		// TODO Auto-generated method stub
//	    Session session=entityManager.unwrap(Session.class);
//	    String queryString="select a.sevaarth_id ,a.designation_code,a.employee_id,b.designation_name,a.employee_full_name_en,g.pf_account_no,d.name,f.org_inst_name from employee_mst a inner join designation_mst b on b.designation_code=a.designation_code "
//	    		+ "left outer join gpf_advance c on   c.employee_id=a.employee_id left join mst_org_inst_info d on d.ddo_code=a.ddo_code inner join ddo_reg_mst e on e.ddo_code=a.ddo_code left outer join org_inst_mst f on f.ddo_reg_id=e.ddo_reg_id   left outer join gpf_mst g on g.employee_id=a.employee_id where a.sevaarth_id || a.employee_full_name_en ilike '%"+sevaarthId+"%' and dcps_gpf_flag = 'N' ";
//	    Query query=session.createSQLQuery(queryString);
//	    System.out.println("-------"+queryString);
//		return query.list();
//	}

	@Override
	public List<Object[]> findAllEmpLoanDtls(String ddoCode) {
		// TODO Auto-generated method stub
		    Session session=entityManager.unwrap(Session.class);
		    String queryString="select b.employee_full_name_en,b.pfdescription,a.* from loan_employee_dtls a inner join employee_mst b on a.sevaarth_id=b.sevaarth_id and b.ddo_code='"+ddoCode+"'";
		    Query query=session.createSQLQuery(queryString);
			return query.list();
	}

//	@Override
//	public Integer checkloanAlreadyTaken(BigInteger empId, BigInteger advId) {
//		// TODO Auto-generated method stub
//		    Session session=entityManager.unwrap(Session.class);
//		    String queryString="select * from loan_employee_dtls where loan_type_id="+empId+" and employee_id="+advId;
//		    Query query=session.createSQLQuery(queryString);
//			return query.list().size();
//	}

//	@Override
//	public List<LoanEmployeeDtlsEntity> getGpfAdvAppNo(String sevaarthId, UserInfo messages) {
//		// TODO Auto-generated method stub
//		 Session session=entityManager.unwrap(Session.class);
//		String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"'"; // and t.loan_activate_flag=1";
//		System.out.println(hqlQuery);
//		return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
//	}

//	@Override
//	public List<LoanEmployeeDtlsEntity> getGpfAppTrnDtlsByAppId(String sevaarthId, Integer appId, UserInfo messages) {
//		// TODO Auto-generated method stub
//		  Session session=entityManager.unwrap(Session.class);
//			String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"'  and t.id="+appId;  //and t.loanactivateflag=1
//			return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
//	}

//	@Override
//	public LoanEmployeeDtlsEntity findLoanDetailsById(Integer loanEmpAdvId) {
//		// TODO Auto-generated method stub
//		return entityManager.find(LoanEmployeeDtlsEntity.class, loanEmpAdvId);
//	}
//

//
//	@Override
//	public void updateLoanDetail(LoanEmployeeDtlsEntity loanEmployeeDtlsEntity) {
//		// TODO Auto-generated method stub
//		 Session session=entityManager.unwrap(Session.class);
//		  session.update(loanEmployeeDtlsEntity);
//	}
//
//	@Override
//	public List<LoanEmployeeDtlsEntity> mergeMultipleGpfApp(String sevaarthId, UserInfo messages) {
//		    Session session=entityManager.unwrap(Session.class);
//			String hqlQuery="From LoanEmployeeDtlsEntity as t where t.sevaarthid='"+sevaarthId+"' and t.loanactivateflag=1";  //and loanprininstno
//			return (List<LoanEmployeeDtlsEntity>) entityManager.createQuery(hqlQuery).getResultList();
//	}
//	
	
	

}
