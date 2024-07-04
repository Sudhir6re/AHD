package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.BillgroupMaintainanceModel;

public interface DdoBillGroupService {

	public List<MstDcpsBillGroup> lstBillName();



	public int saveBillGroupMaintainance(BillgroupMaintainanceModel billgroupMaintainanceModel, OrgUserMst messages);



	public List<MstScheme> getSchemeCodeAgainstName(String schemeId);
}
