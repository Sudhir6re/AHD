/**
 * 
 */
package com.mahait.gov.in.model;

import java.io.Serializable;

/**
 * @author Parvez
 *
 */
public class PayBillViewApprDelBillModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String monthName;
	private String yearName;
	private String billType;
	private String billStatus;
	private String billNumber;
	
	private String empName;
	private String componentName;
	private Double previousMonthAmount;
	private Double currentMonthAmount;
	private Double difference;
	private String dateAndTime;
	private String color; 
	private String voucherNo; 
	private String voucherDate; 
	private Integer bankId; 
	

	public String getColor() {
		return color;
	}
	public Double getPreviousMonthAmount() {
		return previousMonthAmount;
	}
	public void setPreviousMonthAmount(Double previousMonthAmount) {
		this.previousMonthAmount = previousMonthAmount;
	}
	public Double getCurrentMonthAmount() {
		return currentMonthAmount;
	}
	public void setCurrentMonthAmount(Double currentMonthAmount) {
		this.currentMonthAmount = currentMonthAmount;
	}
	public Double getDifference() {
		return difference;
	}
	public void setDifference(Double difference) {
		this.difference = difference;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public String getDateAndTime() {
		return dateAndTime;
	}
	public void setDateAndTime(String dateAndTime) {
		this.dateAndTime = dateAndTime;
	}
	public String getMonthName() {
		return monthName;
	}
	public void setMonthName(String monthName) {
		this.monthName = monthName;
	}
	public String getYearName() {
		return yearName;
	}
	public void setYearName(String yearName) {
		this.yearName = yearName;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public String getBillStatus() {
		return billStatus;
	}
	public void setBillStatus(String billStatus) {
		this.billStatus = billStatus;
	}
	public String getBillNumber() {
		return billNumber;
	}
	public void setBillNumber(String billNumber) {
		this.billNumber = billNumber;
	}
	public String getVoucherNo() {
		return voucherNo;
	}
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}
	public String getVoucherDate() {
		return voucherDate;
	}
	public void setVoucherDate(String voucherDate) {
		this.voucherDate = voucherDate;
	}
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}
	
	
}
