package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="bank_branch_mst",schema="public")
public class MstBankBranchEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	/*@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER,mappedBy = 'SUB_DEPARTMENT_ID')*/
	@Column(name="BANK_BRANCH_ID")
	private int bankBranchId;
	
	@Column(name="BANK_CODE")
	private Integer bankCode;
	
	@Column(name="BANK_ID")
	private Integer bankid;
	
	public Integer getBankid() {
		return bankid;
	}

	public void setBankid(Integer bankid) {
		this.bankid = bankid;
	}


	@Column(name="BANK_BRANCH_CODE")
	private BigInteger bankBranchCode;
	
	
	@Column(name="BANK_BRANCH_SHORT_NAME")
	private String bankBranchShortName;
	
	@Column(name="BANK_BRANCH_NAME")
	private String bankBranchName;
	
	@Column(name="BANK_BRANCH_ADDRESS")
	private String bankBranchAddress;
	
	@Column(name="IFSC_CODE")
	private String ifscCode;
	
	@Column(name="MICR_CODE")
	private String micrCode;	
	
	@Column(name="CONTACT_NAME")
	private String contactName;
	
	@Column(name="MOBILE_NO")
	private Integer mobileNo;
	
	@Column(name="LANDLINE_NO")
	private Integer landlineNo;

	@Column(name="EMAIL_ID")
	private String emailId; 
	
	@Column(name = "IS_ACTIVE")
	private Character isActive;
	
	@Column(name = "CREATED_USER_ID")
	private Integer createdUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Transient
	String bankName;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public int getBankBranchId() {
		return bankBranchId;
	}

	public void setBankBranchId(int bankBranchId) {
		this.bankBranchId = bankBranchId;
	}

	public Integer getBankCode() {
		return bankCode;
	}
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public void setBankId(Integer bankId) {
		this.bankCode = bankId;
	}

	public BigInteger getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(BigInteger bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getBankBranchShortName() {
		return bankBranchShortName;
	}

	public void setBankBranchShortName(String bankBranchShortName) {
		this.bankBranchShortName = bankBranchShortName;
	}

	public String getBankBranchName() {
		return bankBranchName;
	}

	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}

	public String getBankBranchAddress() {
		return bankBranchAddress;
	}

	public void setBankBranchAddress(String bankBranchAddress) {
		this.bankBranchAddress = bankBranchAddress;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public String getMicrCode() {
		return micrCode;
	}

	public void setMicrCode(String micrCode) {
		this.micrCode = micrCode;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Integer getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Integer mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Integer getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(Integer landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Character getIsActive() {
		return isActive;
	}

	public void setIsActive(Character isActive) {
		this.isActive = isActive;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}


	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	
	


}
