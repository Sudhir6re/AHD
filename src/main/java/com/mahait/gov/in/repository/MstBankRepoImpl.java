package com.mahait.gov.in.repository;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.type.LongType;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.MstBankEntity;
import com.mahait.gov.in.entity.MstBankPay;



@Repository
public class MstBankRepoImpl implements MstBankRepo{
	
//	protected final Log logger = LogFactory.getLog(getClass());

	@PersistenceContext
	EntityManager entityManager;

@Override
public List<MstBankEntity> lstAllBank() {
	// TODO Auto-generated method stub
	String HQL = "FROM MstBankEntity as t ORDER BY t.bankName ASC";
	return (List<MstBankEntity>) entityManager.createQuery(HQL).getResultList();
}
		
@SuppressWarnings("unchecked")
@Override
public List<Object[]> findAllBankBranchList(int bankCode) {
	Session currentSession = entityManager.unwrap(Session.class);
	String hql =  "select * from bank_branch_mst where bank_code = "+bankCode+" order by bank_branch_name asc";
	Query query = currentSession.createSQLQuery(hql);
	return query.list();
}

@Override
public int saveBank(MstBankEntity mstBankEntity) {
	Session currentSession = entityManager.unwrap(Session.class);
	Serializable saveId = currentSession.save(mstBankEntity);
	return (Integer) saveId;
}


@Override
public MstBankEntity findBankById(int bankId) {
	MstBankEntity objCad = null;
	Session currentSession = entityManager.unwrap(Session.class);
	objCad = currentSession.get(MstBankEntity.class, bankId);
	return objCad;
}


@Override
public void deleteBankById(MstBankEntity mstBankEntity) {
	Session currentSession = entityManager.unwrap(Session.class);
	currentSession.delete(mstBankEntity);
}
@Override
public void updatebankStatus(MstBankEntity objbank) {
	Session currentSession = entityManager.unwrap(Session.class);
	currentSession.update(objbank);
}


@Override
public List<Long> validateBankName(String bankname) {
	Session currentSession =  entityManager.unwrap(Session.class);

	String hql = "select count(*) as count from bank_mst where bank_name ='"+bankname+"'";
	
	Query query = currentSession.createSQLQuery(hql).addScalar("count", LongType.INSTANCE);
	
	List<Long> lstresult = query.list();
	return lstresult;
}
	
}