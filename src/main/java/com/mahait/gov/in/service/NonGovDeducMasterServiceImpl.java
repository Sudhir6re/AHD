package com.mahait.gov.in.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.repository.NonGovDeducMasterRepo;

@Service
@Transactional
public class NonGovDeducMasterServiceImpl implements NonGovDeducMasterService {
	
	@Autowired
	NonGovDeducMasterRepo nonGovDeducMasterRepo;

}
