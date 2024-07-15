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
@Table(name="dcps_details_mst",schema="public")
public class MstDcpsDetailsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="dcps_id")
	private Integer dcpsid;
	@Column(name="employee_id")
	private Integer employeeId;
	@Column(name="dcps_no")
	private String dcpsno;
	@Column(name="pf_ac_no")
	private String pfacno;
	@Column(name="account_maintain_by")
	private String accountmaintainby;
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
	public Integer getDcpsid() {
		return dcpsid;
	}
	public void setDcpsid(Integer dcpsid) {
		this.dcpsid = dcpsid;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getDcpsno() {
		return dcpsno;
	}
	public void setDcpsno(String dcpsno) {
		this.dcpsno = dcpsno;
	}
	public String getPfacno() {
		return pfacno;
	}
	public void setPfacno(String pfacno) {
		this.pfacno = pfacno;
	}
	public String getAccountmaintainby() {
		return accountmaintainby;
	}
	public void setAccountmaintainby(String accountmaintainby) {
		this.accountmaintainby = accountmaintainby;
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
	public String getSevaarthId() {
		return sevaarthId;
	}
	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}
}
