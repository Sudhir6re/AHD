package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="rlt_dcps_billgroup_classgroup",schema="public")
public class RltBillgroupClassgroup  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DCPS_BILLGROUP_CLASSGROUP_ID")
	private Integer dcpsBillClassGroupId;
	
	@Column(name="DCPS_BILLGROUP_ID")
	private BigInteger dcpsBillGroupId;
	
	@Column(name="DCPS_CLASS_GROUP")
	private String dcpsClassGroup;
	
	
	@Column(name="LANG_ID")
	private BigInteger LangId;
	
	@Column(name="LOC_ID")
	private BigInteger LocId;
	
	@Column(name="DB_ID")
	private BigInteger DbId;
	
	@Column(name = "CREATED_POST_ID")
	private BigInteger PostId;
	

	@Column(name = "CREATED_USER_ID")
	private BigInteger UserId;
	
	@Column(name = "CREATED_DATE")
	private Date CreatedDate;
	
	@Column(name = "UPDATED_POST_ID")
	private BigInteger UpdatedPostId;

	public Integer getDcpsBillClassGroupId() {
		return dcpsBillClassGroupId;
	}

	public void setDcpsBillClassGroupId(Integer dcpsBillClassGroupId) {
		this.dcpsBillClassGroupId = dcpsBillClassGroupId;
	}

	public BigInteger getDcpsBillGroupId() {
		return dcpsBillGroupId;
	}

	public void setDcpsBillGroupId(BigInteger dcpsBillGroupId) {
		this.dcpsBillGroupId = dcpsBillGroupId;
	}

	public String getDcpsClassGroup() {
		return dcpsClassGroup;
	}

	public void setDcpsClassGroup(String dcpsClassGroup) {
		this.dcpsClassGroup = dcpsClassGroup;
	}

	public BigInteger getLangId() {
		return LangId;
	}

	public void setLangId(BigInteger langId) {
		LangId = langId;
	}

	public BigInteger getLocId() {
		return LocId;
	}

	public void setLocId(BigInteger locId) {
		LocId = locId;
	}

	public BigInteger getDbId() {
		return DbId;
	}

	public void setDbId(BigInteger dbId) {
		DbId = dbId;
	}

	public BigInteger getPostId() {
		return PostId;
	}

	public void setPostId(BigInteger postId) {
		PostId = postId;
	}

	public BigInteger getUserId() {
		return UserId;
	}

	public void setUserId(BigInteger userId) {
		UserId = userId;
	}

	public Date getCreatedDate() {
		return CreatedDate;
	}

	public void setCreatedDate(Date createdDate) {
		CreatedDate = createdDate;
	}

	public BigInteger getUpdatedPostId() {
		return UpdatedPostId;
	}

	public void setUpdatedPostId(BigInteger updatedPostId) {
		UpdatedPostId = updatedPostId;
	}

	public BigInteger getUpdatedUserId() {
		return UpdatedUserId;
	}

	public void setUpdatedUserId(BigInteger updatedUserId) {
		UpdatedUserId = updatedUserId;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Column(name = "UPDATED_USER_ID")
	private BigInteger UpdatedUserId;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
}