package com.mahait.gov.in.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.ZpAdminNameMst;

@Repository
public interface ZpAdminNameMstRepository extends JpaRepository<ZpAdminNameMst, Long> {

	 List<ZpAdminNameMst> findByAdminCodeInOrderByAdminCode(List<String> adminCodes);

}