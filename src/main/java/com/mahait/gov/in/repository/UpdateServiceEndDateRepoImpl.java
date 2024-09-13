package com.mahait.gov.in.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository
public class UpdateServiceEndDateRepoImpl implements UpdateServiceEndDateRepo {
	
	@PersistenceContext
	EntityManager entityManager;

}
