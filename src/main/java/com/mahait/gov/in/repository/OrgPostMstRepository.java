package com.mahait.gov.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.OrgPostMst;


@Repository
public interface OrgPostMstRepository  extends JpaRepository<OrgPostMst, Long>{

}