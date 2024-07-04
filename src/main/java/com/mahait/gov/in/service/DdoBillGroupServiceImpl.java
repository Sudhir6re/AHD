package com.mahait.gov.in.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.BillgroupMaintainanceModel;
import com.mahait.gov.in.repository.DdoBillGroupRepo;
import com.mahait.gov.in.repository.DdoBillGroupRepoImpl;

@Service
@Transactional
public class DdoBillGroupServiceImpl implements DdoBillGroupService{
	@Autowired
	private DdoBillGroupRepo ddoBillGroupRepo;
	
 @Override

	public List<MstDcpsBillGroup> lstBillName() {
		// TODO Auto-generated method stub
		return ddoBillGroupRepo.lstBillName();
	}


@Override
public int saveBillGroupMaintainance(BillgroupMaintainanceModel billgroupMaintainanceModel, OrgUserMst messages) {
	// TODO Auto-generated method stub
	MstDcpsBillGroup mstDcpsBillGroup = new MstDcpsBillGroup();
	int savedId =0;
	mstDcpsBillGroup.setDcpsDdoSchemeCode(billgroupMaintainanceModel.getDcpsDdoSchemeCode());
	mstDcpsBillGroup.setDescription(billgroupMaintainanceModel.getDescription());
	
		savedId	= ddoBillGroupRepo.saveBillGroupMaintainance(mstDcpsBillGroup);
	
		
		return  savedId;
}


@Override
public List<MstScheme> getSchemeCodeAgainstName(String schemeId) {
	// TODO Auto-generated method stub
	return ddoBillGroupRepo.getSchemeCodeAgainstName(schemeId);
}

}
