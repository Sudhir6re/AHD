package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.mahait.gov.in.entity.OrgDepartmentMst;
import com.mahait.gov.in.repository.MstDepartmentRepo;

@Service
@Transactional
public class MstDepartmentServiceImpl implements MstDepartmentService {
	@Autowired
	private MstDepartmentRepo mstDepartmentRepo;
	
	
//	@Override
//	public List<OrgDepartmentMst> findAllDepartment() {
//		// TODO Auto-generated method stub
//		return mstDepartmentRepo.findAllDepartment();
//	}

	
	
}
