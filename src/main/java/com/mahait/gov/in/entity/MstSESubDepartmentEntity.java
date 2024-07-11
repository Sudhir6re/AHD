package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name="sub_se_department_mst",schema="public")
public class MstSESubDepartmentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUB_SE_DEPARTMENT_ID")
	private int subseDepartmentId;
	
	@ManyToOne
	@JoinColumn(name = "SUB_CE_DEPARTMENT_ID",  nullable=true,insertable = false, updatable = false)
    private MstCESubDepartmentEntity mstCESubDepartmentEntity;

	
	
	@Column(name="SUB_SE_DEPARTMENT_NAME_EN")
	private String subseDepartmentNameEn;
	

	@Column(name="SUB_SE_DEPARTMENT_NAME_Mr")
	private String subseDepartmentNameMr;
	
	
	
	
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

	public int getSubseDepartmentId() {
		return subseDepartmentId;
	}

	public void setSubseDepartmentId(int subseDepartmentId) {
		this.subseDepartmentId = subseDepartmentId;
	}

	public MstCESubDepartmentEntity getMstCESubDepartmentEntity() {
		return mstCESubDepartmentEntity;
	}

	public void setMstCESubDepartmentEntity(MstCESubDepartmentEntity mstCESubDepartmentEntity) {
		this.mstCESubDepartmentEntity = mstCESubDepartmentEntity;
	}

	public String getSubseDepartmentNameEn() {
		return subseDepartmentNameEn;
	}

	public void setSubseDepartmentNameEn(String subseDepartmentNameEn) {
		this.subseDepartmentNameEn = subseDepartmentNameEn;
	}

	public String getSubseDepartmentNameMr() {
		return subseDepartmentNameMr;
	}

	public void setSubseDepartmentNameMr(String subseDepartmentNameMr) {
		this.subseDepartmentNameMr = subseDepartmentNameMr;
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
