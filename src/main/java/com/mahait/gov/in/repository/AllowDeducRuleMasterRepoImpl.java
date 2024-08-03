package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.AllowanceDeductionRuleMstEntity;

@Repository
public class AllowDeducRuleMasterRepoImpl implements AllowDeducRuleMasterRepo {

	@PersistenceContext
	EntityManager entityManager;

	@Override
	public List<Object[]> findAllRules() {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.*,c.DEPARTMENT_ALLOWDEDUC_NAME,b.commission_name_en,b.commission_name_mh "
				+ " from ALLOWANCE_DEDUCTION_WISE_RULE_MST a inner join pay_commission_mst b "
				+ " on a.pay_commission_code=b.pay_commission_code"
				+ "  inner join department_allowdeduc_mst c on  c.DEPARTMENT_ALLOWDEDUC_CODE=a.DEPARTMENT_ALLOWDEDUC_CODE";
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public List<Object[]> findAllRules(int departmentAllowdeducCode) {
		Session currentSession = entityManager.unwrap(Session.class);
		String HQL = "select a.*,c.DEPARTMENT_ALLOWDEDUC_NAME,b.commission_name_en,b.commission_name_mh "
				+ " from ALLOWANCE_DEDUCTION_WISE_RULE_MST a inner join pay_commission_mst b "
				+ " on a.pay_commission_code=b.pay_commission_code"
				+ "  inner join department_allowdeduc_mst c on  c.DEPARTMENT_ALLOWDEDUC_CODE=a.DEPARTMENT_ALLOWDEDUC_CODE where  a.DEPARTMENT_ALLOWDEDUC_CODE="
				+ departmentAllowdeducCode;
		Query query = currentSession.createSQLQuery(HQL);
		return query.list();
	}

	@Override
	public int saveAllowanceDeductionRulesMaster(AllowanceDeductionRuleMstEntity allowanceDeductionRuleMstEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		return (int) currentSession.save(allowanceDeductionRuleMstEntity);
	}

	@Override
	public void updateAllowanceDeductionRulesMaster(AllowanceDeductionRuleMstEntity allowanceDeductionRuleMstEntity) {
		Session currentSession = entityManager.unwrap(Session.class);
		 currentSession.update(allowanceDeductionRuleMstEntity);
	}

	@Override
	public AllowanceDeductionRuleMstEntity findRuleByComponentCode(Integer allowanceDeductionWiseRuleId) {
		Session currentSession = entityManager.unwrap(Session.class);
		String  HQL = "FROM AllowanceDeductionRuleMstEntity as t where t.departmentAllowdeducCode="+allowanceDeductionWiseRuleId;
		List<AllowanceDeductionRuleMstEntity> lstAllowanceDeductionMstEntity= (List<AllowanceDeductionRuleMstEntity>) entityManager.createQuery(HQL).getResultList();
		if(lstAllowanceDeductionMstEntity.size()>0) {
			return lstAllowanceDeductionMstEntity.get(0);
		}else {
			return null;
		}
	}

	@Override
	public AllowanceDeductionRuleMstEntity findRuleByRuleId(int allowanceDeductionWiseRuleId) {
		Session currentSession = entityManager.unwrap(Session.class);
		return currentSession.find(AllowanceDeductionRuleMstEntity.class, allowanceDeductionWiseRuleId);
	}

}
