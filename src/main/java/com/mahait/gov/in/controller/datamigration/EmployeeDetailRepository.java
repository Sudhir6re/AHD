package com.mahait.gov.in.controller.datamigration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.EmployeeDetailEntity;

@Repository
public interface EmployeeDetailRepository extends JpaRepository<EmployeeDetailEntity, Long> {
}
