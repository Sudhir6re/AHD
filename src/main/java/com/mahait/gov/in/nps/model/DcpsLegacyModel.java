package com.mahait.gov.in.nps.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class DcpsLegacyModel {
	
	String dcpsNo;
	String employeeFullNameEn;
	String sevaarthId;
	
	Date dob;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date doj;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date superAnnDate;
	
	Long mulTwoYearBasic;
	Long mulOneYearBasic;
	String pranNo;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date empServiceEndDate;
	
	String pranStatus;
	String reptDdoCode;
	
	String action;
	
	String txtSevaarthId;
	String txtDcpsNo;
	String employeeFullName;
	
	String remark;
	
	Long total;
	Long employerInterest;
	Long empInterest;
	Long employerContri;
	Long empContri;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date contriEndDt;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date contriStartDt;
	
	String period;
	
	String ddoCode;
	
	List<DcpsLegacyModel> lstDcpsLegacyModel;
	
	
	

}
