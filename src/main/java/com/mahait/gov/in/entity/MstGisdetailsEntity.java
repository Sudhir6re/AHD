package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="gis_details_mst",schema="public")
public class MstGisdetailsEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gis_id")
	private Integer gisid;
	@Column(name="employee_id")
	private Integer employeeId;
	@Column(name="gis_applicable")
	private String gisapplicable;
	@Column(name="gis_group")
	private String gisgroup;
	@Column(name="membership_date")
	private Date membership_date;
	@Column(name="is_active")
	private String isactive;
	@Column(name="created_date")
	private Date createddate;
	@Column(name="created_id")
	private BigInteger createdid;
	@Column(name="update_date")
	private Date updatedate;
	@Column(name="update_id")
	private BigInteger updateid;
	@Column(name="sevaarth_id")
	private String sevaarthId;
	
	@Column(name="remark")
	private String remark;
	
	
	@Column(name="gisApplicableOther")
	private String gisApplicableOther;
	
	
	
	
	
	public String getSevaarthId() {
		return sevaarthId;
	}
	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}
	public Integer getGisid() {
		return gisid;
	}
	public void setGisid(Integer gisid) {
		this.gisid = gisid;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getGisapplicable() {
		return gisapplicable;
	}
	public void setGisapplicable(String gisapplicable) {
		this.gisapplicable = gisapplicable;
	}
	public String getGisgroup() {
		return gisgroup;
	}
	public void setGisgroup(String gisgroup) {
		this.gisgroup = gisgroup;
	}
	public Date getMembership_date() {
		return membership_date;
	}
	public void setMembership_date(Date membership_date) {
		this.membership_date = membership_date;
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
	public BigInteger getCreatedid() {
		return createdid;
	}
	public void setCreatedid(BigInteger createdid) {
		this.createdid = createdid;
	}
	public Date getUpdatedate() {
		return updatedate;
	}
	public void setUpdatedate(Date updatedate) {
		this.updatedate = updatedate;
	}
	public BigInteger getUpdateid() {
		return updateid;
	}
	public void setUpdateid(BigInteger updateid) {
		this.updateid = updateid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGisApplicableOther() {
		return gisApplicableOther;
	}
	public void setGisApplicableOther(String gisApplicableOther) {
		this.gisApplicableOther = gisApplicableOther;
	}
	
	  
	
	
}
