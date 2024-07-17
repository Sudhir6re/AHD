/**
 * 
 */
package com.mahait.gov.in.model;

import java.io.Serializable;

/**
 * @author Parvez
 *
 */
public class ConsolidatePayBillModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer monthName;
	private Integer yearName;
	private String schemeCode;
	private String subSchemeName;
	private int billStatus;
	private double  billGrossAmt;
	

	public double getBillGrossAmt() {
		return billGrossAmt;
	}
	public void setBillGrossAmt(double billGrossAmt) {
		this.billGrossAmt = billGrossAmt;
	}
	private Double netAmt;

	public Double getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(Double netAmt) {
		this.netAmt = netAmt;
	}
	public Integer getMonthName() {
		return monthName;
	}
	public void setMonthName(Integer monthName) {
		this.monthName = monthName;
	}
	public Integer getYearName() {
		return yearName;
	}
	public void setYearName(Integer yearName) {
		this.yearName = yearName;
	}
	public int getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(int billStatus) {
		this.billStatus = billStatus;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSubSchemeName() {
		return subSchemeName;
	}
	public void setSubSchemeName(String subSchemeName) {
		this.subSchemeName = subSchemeName;
	}

}
