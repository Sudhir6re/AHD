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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
@Entity
@Table(name="nominee_details_mst",schema="public")
public class MstNomineeDetailsEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="nominee_id")
	private Integer nomineeid;
	
	@Column(name="employee_id")
	private Integer employeeId;
	@Column(name="relation")
	private String relation;
	@Column(name="nominee_name")
	private String nomineename;
	@Column(name="nominee_address")
	private String nomineeaddress;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="dob")
	private Date dob;
	
	
	@Column(name="percent_share")
	private Integer percent_share;
	
	@Column(name="guardian_name")
	private String guardianName;

	@Column(name="major_minor")
	private String majorMinor;
	
	
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
	
	
	@ManyToOne(fetch = FetchType.LAZY) 
	  @JoinColumn(name = "EMPLOYEE_ID",insertable = false, updatable = false) 
	  private MstEmployeeEntity mstEmployeeEntity; 
	
	
	
	
	public MstEmployeeEntity getMstEmployeeEntity() {
		return mstEmployeeEntity;
	}
	public void setMstEmployeeEntity(MstEmployeeEntity mstEmployeeEntity) {
		this.mstEmployeeEntity = mstEmployeeEntity;
	}
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
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
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
	
	
	
	
	public String getGuardianName() {
		return guardianName;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public String getMajorMinor() {
		return majorMinor;
	}
	public void setMajorMinor(String majorMinor) {
		this.majorMinor = majorMinor;
	}
	@Override
	public String toString() {
		return "MstNomineeDetailsEntity [nomineeid=" + nomineeid + ", employeeId=" + employeeId + ", relation="
				+ relation + ", nomineename=" + nomineename + ", nomineeaddress=" + nomineeaddress + ", dob=" + dob
				+ ", percent_share=" + percent_share + ", isactive=" + isactive + ", createddate=" + createddate
				+ ", createdid=" + createdid + ", updatedate=" + updatedate + ", updateid=" + updateid + ", sevaarthId="
				+ sevaarthId + "]";
	}
}
