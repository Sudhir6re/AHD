package com.mahait.gov.in.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Component;


@Component
public class SequenceChecker {

    @PersistenceContext
    private EntityManager entityManager;

    public void createSequenceIfNotExists(String sequenceName) {
        String query = "CREATE SEQUENCE IF NOT EXISTS " + sequenceName + " START WITH 1 INCREMENT BY 1";
        Query createSequenceQuery = entityManager.createNativeQuery(query);
        createSequenceQuery.executeUpdate();
    }
}