package com.mahait.gov.in.model;

import java.util.Date;

public class MstNomineeDetailsModel {


	private Integer nomineeid;
	
	private Integer employeeId;

	private String relation;
	
	private String nomineename;
	
	private String nomineeaddress;
	
	private String dob;
	
	private Integer percent_share;
		private String isactive;
	
	private Date createddate;
	
	private Integer createdid;
	
	private Date updatedate;
	
	private String age;

	private Integer updateid;
	public Integer getNomineeid() {
		return nomineeid;
	}
	public void setNomineeid(Integer nomineeid) {
		this.nomineeid = nomineeid;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public String getNomineename() {
		return nomineename;
	}
	public void setNomineename(String nomineename) {
		this.nomineename = nomineename;
	}
	public String getNomineeaddress() {
		return nomineeaddress;
	}
	public void setNomineeaddress(String nomineeaddress) {
		this.nomineeaddress = nomineeaddress;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public Integer getPercent_share() {
		return percent_share;
	}
	public void setPercent_share(Integer percent_share) {
		this.percent_share = percent_share;
	}
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	public Date getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}
	public Integer getCreatedid() {
		return createdid;
	}
	public void setCreatedid(Integer createdid) {
		this.createdid = createdid;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public Integer getUpdateid() {
		return updateid;
	}
	public void setUpdateid(Integer updateid) {
		this.updateid = updateid;
	}
	
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	
	
}
