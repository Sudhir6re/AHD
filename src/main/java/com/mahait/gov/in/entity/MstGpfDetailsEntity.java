package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="gpf_mst",schema="public")
public class MstGpfDetailsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gpf_id")
	private Integer gpf_id;
	@Column(name="employee_id")
	private Integer employeeId;
	@Column(name="account_maintain_by")
	private String accountmaintainby;
	@Column(name="pf_account_no")
	private String pfacno;
	@Column(name="pf_description")
	private String pfdescription;
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
	
	
/*	 @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstGpfDetailsEntity", orphanRemoval = true) 
	  private List<MstEmployeeEntity> mstEmployeeEntity;*/
	
	 
/*	  @ManyToOne(fetch = FetchType.LAZY) 
	  @JoinColumn(name = "EMPLOYEE_ID",insertable = false, updatable = false) 
	  private MstEmployeeEntity mstEmployeeEntity; 
	  */
	  @OneToOne(fetch = FetchType.LAZY) 
	  @JoinColumn(name = "EMPLOYEE_ID",insertable = false, updatable = false) 
	  private MstEmployeeEntity mstEmployeeEntity; 
	
	  
	  

	public MstEmployeeEntity getMstEmployeeEntity() {
		return mstEmployeeEntity;
	}
	public void setMstEmployeeEntity(MstEmployeeEntity mstEmployeeEntity) {
		this.mstEmployeeEntity = mstEmployeeEntity;
	}
	public String getSevaarthId() {
		return sevaarthId;
	}
	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}
	public Integer getGpf_id() {
		return gpf_id;
	}
	public void setGpf_id(Integer gpf_id) {
		this.gpf_id = gpf_id;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getAccountmaintainby() {
		return accountmaintainby;
	}
	public void setAccountmaintainby(String accountmaintainby) {
		this.accountmaintainby = accountmaintainby;
	}
	public String getPfacno() {
		return pfacno;
	}
	public void setPfacno(String pfacno) {
		this.pfacno = pfacno;
	}
	public String getPfdescription() {
		return pfdescription;
	}
	public void setPfdescription(String pfdescription) {
		this.pfdescription = pfdescription;
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
	
	
	
}
