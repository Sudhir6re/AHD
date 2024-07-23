package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
		
	/*@Autowired
	SessionFactory sessionFactory;*/
	
}