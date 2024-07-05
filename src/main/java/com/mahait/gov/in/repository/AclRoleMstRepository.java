package com.mahait.gov.in.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mahait.gov.in.entity.AclRoleMst;

@Repository
public interface AclRoleMstRepository extends JpaRepository<AclRoleMst,Long> {

	AclRoleMst findByRoleId(long l);

}
