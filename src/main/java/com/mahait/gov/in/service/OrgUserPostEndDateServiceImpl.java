package com.mahait.gov.in.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.repository.OrgUserPostEndDateRepo;


@Service
@Transactional
public class OrgUserPostEndDateServiceImpl implements OrgUserPostEndDateService {
	
	@Autowired
	OrgUserPostEndDateRepo orgUserPostEndDateRepo;

}
