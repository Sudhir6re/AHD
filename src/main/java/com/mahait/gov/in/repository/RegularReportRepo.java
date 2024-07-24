package com.mahait.gov.in.repository;

import java.util.List;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.OrgDdoMst;

public interface RegularReportRepo {

	List<OrgDdoMst> getDDOName(String userName);

	List<Object[]> findentry(Integer yearId, Integer monthId, Long billGroup, String ddoCode);

	List<Object[]> findbillgrp(String billno);

	List<Object[]> findpaybill(int billNumber, int monthName, int yearName, String ddo);

	String getbillGroup(int billnumber);

	List<Object[]> checktheEntryForForm2Regular(int billNumber, int monthName, int yearName, String userName);

	List<MstDcpsBillGroup> lstBillDesc(String ddoCode);

}
