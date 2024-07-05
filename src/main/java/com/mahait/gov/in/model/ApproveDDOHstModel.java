package com.mahait.gov.in.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


public class ApproveDDOHstModel {

	String ddoCode;
	String ddoName;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date toDate;
	String status;


	public List<ApproveDDOHstModel> approveDDOHstModel = new ArrayList<>();


	public String getDdoCode() {
		return ddoCode;
	}


	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}


	public String getDdoName() {
		return ddoName;
	}


	public void setDdoName(String ddoName) {
		this.ddoName = ddoName;
	}


	public Date getFromDate() {
		return fromDate;
	}


	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}


	public Date getToDate() {
		return toDate;
	}


	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public List<ApproveDDOHstModel> getApproveDDOHstModel() {
		return approveDDOHstModel;
	}


	public void setApproveDDOHstModel(List<ApproveDDOHstModel> approveDDOHstModel) {
		this.approveDDOHstModel = approveDDOHstModel;
	}


	
	
	
}
