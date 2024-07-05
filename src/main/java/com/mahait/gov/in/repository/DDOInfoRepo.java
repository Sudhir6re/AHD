package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.OrgPostMst;

public interface DDOInfoRepo {

	String getDdoCodeForDDO(Long postId);

	List<Object[]> getLevel1DDOList(String lStrDdoCode);

	List<Object[]> getDDoHistoryDetailsForApprove(String ddo);


}
