package com.mahait.gov.in.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.CmnLookupMst;

@Repository
public class OnlineContributionRepoImpl implements OnlineContributionRepo {
	
	@PersistenceContext
	EntityManager entityManager;
	
	
	
	@Override
	public List<CmnLookupMst> getPaymentTypeLst() {
		String HQL = "FROM CmnLookupMst as t  where lookupId in ('700047','700048','700049')  ORDER BY t.lookupId";
		return (List<CmnLookupMst>) entityManager.createQuery(HQL).getResultList();
	}


}
