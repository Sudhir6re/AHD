package com.mahait.gov.in.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgDesignationMst;


@Repository
public interface OrgDesignationMstRepository  extends JpaRepository<OrgDesignationMst, Long>{
	  List<OrgDesignationMst> findByDsgnNameIgnoreCaseContaining(String name);
}
