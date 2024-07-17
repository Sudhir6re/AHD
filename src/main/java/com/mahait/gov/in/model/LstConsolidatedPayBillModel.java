package com.mahait.gov.in.model;

public class LstConsolidatedPayBillModel {
	
	private int consolidatePayBillTrnId;
	private String schemeCode;
	private String schemeName;
	private double billGrossAmt;
	private double billNetAmount;
	private char isActive;
	public int getConsolidatePayBillTrnId() {
		return consolidatePayBillTrnId;
	}
	public void setConsolidatePayBillTrnId(int consolidatePayBillTrnId) {
		this.consolidatePayBillTrnId = consolidatePayBillTrnId;
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
	public double getBillGrossAmt() {
		return billGrossAmt;
	}
	public void setBillGrossAmt(double billGrossAmt) {
		this.billGrossAmt = billGrossAmt;
	}
	public double getBillNetAmount() {
		return billNetAmount;
	}
	public void setBillNetAmount(double billNetAmount) {
		this.billNetAmount = billNetAmount;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	
	
	
	
	

}
