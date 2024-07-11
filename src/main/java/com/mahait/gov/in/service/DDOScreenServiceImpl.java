package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.repository.DDOScreenRepo;

@Service
@Transactional
public class DDOScreenServiceImpl implements DDOScreenService{

	@Autowired
	DDOScreenRepo ddoScreenRepo;

	@Override
	public List<OrgDdoMst> findDDOByUsername(String ddoCode) {
		// TODO Auto-generated method stub
		return ddoScreenRepo.findDDOByUsername(ddoCode);
	}}
	
	
	
