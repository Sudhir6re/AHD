package com.mahait.gov.in.model;

import java.io.Serializable;

public class BrokenPeriodModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private int paybillgenerationtrnid;
	private String billdescription;
	//start
	private String deptalldetNm;
	private String deptalldetValue;// edp desc
	private int type; 
	private int deptallowdeducid;
	private String tempvalue; // insted of subdetail head and headofaccountcode
	private String tempempty;
	//end
	private String sevaarthid;
	private String ppoNo;
	private String empName;
	private String empId;
	private String month;
	private String year;
	private String noOfDays;
	private String paybillMonth;
	private String paybillYear;
	private String action;
	private String groupNm;
	private double gisAmount;
	private String ddocode;
	private Double arrears;
	private String remarks;
	private String desgName;
	public int getPaybillgenerationtrnid() {
		return paybillgenerationtrnid;
	}
	public void setPaybillgenerationtrnid(int paybillgenerationtrnid) {
		this.paybillgenerationtrnid = paybillgenerationtrnid;
	}
	public String getBilldescription() {
		return billdescription;
	}
	public void setBilldescription(String billdescription) {
		this.billdescription = billdescription;
	}
	public String getDeptalldetNm() {
		return deptalldetNm;
	}
	public void setDeptalldetNm(String deptalldetNm) {
		this.deptalldetNm = deptalldetNm;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDeptallowdeducid() {
		return deptallowdeducid;
	}
	public void setDeptallowdeducid(int deptallowdeducid) {
		this.deptallowdeducid = deptallowdeducid;
	}
	public String getTempvalue() {
		return tempvalue;
	}
	public void setTempvalue(String tempvalue) {
		this.tempvalue = tempvalue;
	}
	public String getTempempty() {
		return tempempty;
	}
	public void setTempempty(String tempempty) {
		this.tempempty = tempempty;
	}
	
	public String getSevaarthid() {
		return sevaarthid;
	}
	public void setSevaarthid(String sevaarthid) {
		this.sevaarthid = sevaarthid;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getNoOfDays() {
		return noOfDays;
	}
	public void setNoOfDays(String noOfDays) {
		this.noOfDays = noOfDays;
	}
	public String getPaybillMonth() {
		return paybillMonth;
	}
	public void setPaybillMonth(String paybillMonth) {
		this.paybillMonth = paybillMonth;
	}
	public String getPaybillYear() {
		return paybillYear;
	}
	public void setPaybillYear(String paybillYear) {
		this.paybillYear = paybillYear;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getDeptalldetValue() {
		return deptalldetValue;
	}
	public void setDeptalldetValue(String deptalldetValue) {
		this.deptalldetValue = deptalldetValue;
	}
	public String getGroupNm() {
		return groupNm;
	}
	public void setGroupNm(String groupNm) {
		this.groupNm = groupNm;
	}
	public double getGisAmount() {
		return gisAmount;
	}
	public void setGisAmount(double gisAmount) {
		this.gisAmount = gisAmount;
	}
	public String getDdocode() {
		return ddocode;
	}
	public void setDdocode(String ddocode) {
		this.ddocode = ddocode;
	}
	public Double getArrears() {
		return arrears;
	}
	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getPpoNo() {
		return ppoNo;
	}
	public void setPpoNo(String ppoNo) {
		this.ppoNo = ppoNo;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getDesgName() {
		return desgName;
	}
	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}
	
	
	

}
