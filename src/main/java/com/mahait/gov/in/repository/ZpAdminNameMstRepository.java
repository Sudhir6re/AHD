package com.mahait.gov.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.ZpAdminNameMst;

@Repository
public interface ZpAdminNameMstRepository extends JpaRepository<ZpAdminNameMst, Long> {

    @Query("SELECT MAX(adminCode) FROM ZpAdminNameMst")
    Long findMaxAdminCode();
}