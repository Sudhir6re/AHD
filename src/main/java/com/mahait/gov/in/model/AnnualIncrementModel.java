package com.mahait.gov.in.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author parvez
 *
 */
public class AnnualIncrementModel {

	String ddoCode;
	String sevaarthId;
	String empname;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date incrementOrderDate;
	private boolean checkboxid;
	String ddoName;
	String officeName;
	char isActive;
	Integer monthId;
	Integer yearId;
	String orderNo;
	String postName;
	Double basicPay;
	Integer currbasic;
	Integer incrBasic;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date incrDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date incrToDate; 
	Integer basicPayIncrementId;
	String empCount;
	public List<AnnualIncrementModel> getAnnualIncrementModelList() {
		return annualIncrementModelList;
	}
	public void setAnnualIncrementModelList(List<AnnualIncrementModel> annualIncrementModelList) {
		this.annualIncrementModelList = annualIncrementModelList;
	}
	public List<AnnualIncrementModel> annualIncrementModelList = new ArrayList<>();
	
	public String getDdoCode() {
		return ddoCode;
	}
	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}
	public String getSevaarthId() {
		return sevaarthId;
	}
	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}
	public String getEmpname() {
		return empname;
	}
	public void setEmpname(String empname) {
		this.empname = empname;
	}
	public Date getIncrementOrderDate() {
		return incrementOrderDate;
	}
	public void setIncrementOrderDate(Date incrementOrderDate) {
		this.incrementOrderDate = incrementOrderDate;
	}
	public boolean isCheckboxid() {
		return checkboxid;
	}
	public void setCheckboxid(boolean checkboxid) {
		this.checkboxid = checkboxid;
	}
	public String getDdoName() {
		return ddoName;
	}
	public void setDdoName(String ddoName) {
		this.ddoName = ddoName;
	}
	public String getOfficeName() {
		return officeName;
	}
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public Integer getMonthId() {
		return monthId;
	}
	public void setMonthId(Integer monthId) {
		this.monthId = monthId;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	public Double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}
	public Integer getBasicPayIncrementId() {
		return basicPayIncrementId;
	}
	public void setBasicPayIncrementId(Integer basicPayIncrementId) {
		this.basicPayIncrementId = basicPayIncrementId;
	}
	public Integer getCurrbasic() {
		return currbasic;
	}
	public void setCurrbasic(Integer currbasic) {
		this.currbasic = currbasic;
	}
	public Date getIncrDate() {
		return incrDate;
	}
	public void setIncrDate(Date incrDate) {
		this.incrDate = incrDate;
	}
	public String getEmpCount() {
		return empCount;
	}
	public void setEmpCount(String empCount) {
		this.empCount = empCount;
	}
	public Date getIncrToDate() {
		return incrToDate;
	}
	public void setIncrToDate(Date incrToDate) {
		this.incrToDate = incrToDate;
	}
	public Integer getIncrBasic() {
		return incrBasic;
	}
	public void setIncrBasic(Integer incrBasic) {
		this.incrBasic = incrBasic;
	}
	

}
