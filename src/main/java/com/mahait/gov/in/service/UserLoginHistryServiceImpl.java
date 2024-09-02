package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.repository.UserLoginHistryRepo;

@Service
@Transactional
public class UserLoginHistryServiceImpl implements UserLoginHistryService {
	
	@Autowired
	UserLoginHistryRepo userLoginHistryRepo;

	@Override
	public List<UserLoginHistryEntity> findlogindata() {
		// TODO Auto-generated method stub
		return userLoginHistryRepo.findlogindata();
	}

	@Override
	public void saveLoginDtls(UserLoginHistryEntity userLoginHistryEntity) {

		 userLoginHistryRepo.saveLoginDtls(userLoginHistryEntity);
	}

}
