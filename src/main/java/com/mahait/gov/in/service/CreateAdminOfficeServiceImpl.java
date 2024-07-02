package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.model.ZpRltDdoMapModel;

@Transactional
@Service
public class CreateAdminOfficeServiceImpl implements CreateAdminOfficeService {
	
	
	

	@Override
	public List<ZpRltDdoMapModel> findAllDdoMappedlist(OrgUserMst messages) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ZpAdminNameMst> fetchAllOfficeList(OrgUserMst messages) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
