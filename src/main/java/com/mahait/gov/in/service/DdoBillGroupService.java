package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.BillgroupMaintainanceModel;
import com.mahait.gov.in.model.MpgSchemeBillGroupModel;
import com.mahait.gov.in.model.MstEmployeeModel;

public interface DdoBillGroupService {

	public List<MstDcpsBillGroup> lstBillName(String string);



	public long saveBillGroupMaintainance(BillgroupMaintainanceModel billgroupMaintainanceModel, OrgUserMst messages);



	public List<MstScheme> getSchemeCodeAgainstName(String schemeId);



	public List<MstEmployeeModel> findAllEmployeesByDDOName(String ddoCode);


	public List<MstEmployeeEntity> firstgetfindAllEmployeeByddoCode(String ddoCode);



	public MstDcpsBillGroup findAllMpgSchemeBillGroupbyParameter(int parseInt);



	public List<Object[]> findAttachedEmployee(String userName, String string);



	public Object findDettachEmployee(String userName, String string);



	public List<Object[]> getBillDtlsForAlreadySaved(String billGrpId);



	public String saveAttachDettachEmployee(MpgSchemeBillGroupModel mpgSchemeBillGroupModel);



	public MstDcpsBillGroup findAllMpgSchemeBillGroupbyParameter(Long valueOf);
}
