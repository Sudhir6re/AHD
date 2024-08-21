package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.repository.OnlineContributionRepo;

@Transactional
@Service
public class OnlineContributionServiceImpl implements OnlineContributionService{

	
	@Autowired
	OnlineContributionRepo onlineContributionRepo;
	
	
	@Override
	public List<CmnLookupMst> getPaymentTypeLst() {
		// TODO Auto-generated method stub
		return onlineContributionRepo.getPaymentTypeLst();
	}

}
