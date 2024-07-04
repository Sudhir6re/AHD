package com.mahait.gov.in.service;

import java.util.List;

import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.model.ApproveDDOHstModel;

public interface DDOInfoService {

	String getDdoCodeForDDO(Long postId);

	 List <ApproveDDOHstModel> getLevel1DDOList(String lStrDdoCode);

	 List <Object[]> getDDoHistoryDetailsForApprove(String ddo);
	

}
