package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;

public interface DdoBillGroupRepo {
	
	public List<MstDcpsBillGroup> lstBillName();

	public int saveBillGroupMaintainance(MstDcpsBillGroup mstDcpsBillGroup);

	public List<MstScheme> getSchemeCodeAgainstName(String schemeId);

}
