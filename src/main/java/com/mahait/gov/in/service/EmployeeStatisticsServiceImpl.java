package com.mahait.gov.in.service;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.repository.EmployeeStatisticsRepo;

@Service
@Transactional
public class EmployeeStatisticsServiceImpl implements EmployeeStatisticsService{

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private EmployeeStatisticsRepo employeeStatisticsRepo;
	
	

	@Override
	public List<Object[]> findEmployeeStatistics(String DDOCode) {
		
		return employeeStatisticsRepo.findEmployeeStatistics(DDOCode);
		/*return null;*/
	}
}
