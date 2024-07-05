package com.mahait.gov.in.model;

public class LstGenerateBillDetailsModel {
	
	private Integer paybillGenerationTrnId;
	private String  billDescription;
	private String schemeCode;
	private String schemeName;
	private Double billGrossAmt;
	private Double billNetAmt;
	private int isActive;
	private String ddoCode;
	private Integer noOfEmployee;
	private String authno;
	
	public String getAuthno() {
		return authno;
	}
	public void setAuthno(String authno) {
		this.authno = authno;
	}
	public Integer getPaybillGenerationTrnId() {
		return paybillGenerationTrnId;
	}
	public void setPaybillGenerationTrnId(Integer paybillGenerationTrnId) {
		this.paybillGenerationTrnId = paybillGenerationTrnId;
	}
	public String getBillDescription() {
		return billDescription;
	}
	public void setBillDescription(String billDescription) {
		this.billDescription = billDescription;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public Double getBillGrossAmt() {
		return billGrossAmt;
	}
	public void setBillGrossAmt(Double billGrossAmt) {
		this.billGrossAmt = billGrossAmt;
	}
	public Double getBillNetAmt() {
		return billNetAmt;
	}
	public void setBillNetAmt(Double billNetAmt) {
		this.billNetAmt = billNetAmt;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getDdoCode() {
		return ddoCode;
	}
	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}
	public Integer getNoOfEmployee() {
		return noOfEmployee;
	}
	public void setNoOfEmployee(Integer noOfEmployee) {
		this.noOfEmployee = noOfEmployee;
	}
	
	
	
	
	
	

}
