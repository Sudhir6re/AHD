package com.mahait.gov.in.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.repository.changePasswordNewRepo;

@Transactional
@Service
public class ChangePasswordNewServiceImpl implements ChangePasswordNewService{

	
	@Autowired
	private changePasswordNewRepo cchangePasswordNewRepo;
	
	@Override
	public void updatePassword(OrgUserMst orgUserMst) {
		// TODO Auto-generated method stub
		cchangePasswordNewRepo.updatePassword(orgUserMst);
	}
	
	
	

}
