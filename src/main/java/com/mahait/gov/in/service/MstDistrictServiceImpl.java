package com.mahait.gov.in.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Transactional
public class MstDistrictServiceImpl implements MstDistrictService{/*
	
SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Autowired
	private MstDistrictRepo mstDistrictRepo;
	@Autowired
	private MstSaveDistrictRepo mstSaveDistrictRepo;
	
	@Override
	public List<MstDistrictEntity> findAllDistrict() {
		return mstDistrictRepo.findAllDistrict();
	}
	@Override
	public List<MstStateModel> fetchStateByCountry(int countryId) {
		return mstDistrictRepo.fetchStateByCountry(countryId);
	}
	 
	@Override
	public MstDistrictEntity saveDistrict(MstDistrictEntity mstDepartmentEntity) {
		mstDepartmentEntity.setIsActive('1');
		mstDepartmentEntity.setCreatedUserId(1); 
		mstDepartmentEntity.setCreatedDate(now());
		mstDepartmentEntity.setDistrictNameEn(mstDepartmentEntity.getDistrictNameEn().toUpperCase());
		mstDepartmentEntity.setStateCode(mstDepartmentEntity.getStateCode());
		mstDepartmentEntity.setCountryCode(mstDepartmentEntity.getCountryCode());
		return mstSaveDistrictRepo.save(mstDepartmentEntity);
}
	private Date now() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Long> validateDistrictname(String districtname) {
		// TODO Auto-generated method stub
		return mstDistrictRepo.validateDistrictName(districtname);
	}
*/}
