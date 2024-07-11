package com.mahait.gov.in.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="sub_ce_department_mst",schema="public")
public class MstCESubDepartmentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SUB_CE_DEPARTMENT_ID")
	private int subceDepartmentId;
	

	
	
	@Column(name="SUB_CE_DEPARTMENT_NAME_EN")
	private String subceDepartmentNameEn;
	

	@Column(name="SUB_CE_DEPARTMENT_NAME_Mr")
	private String subceDepartmentNameMr;
	
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

	public int getSubceDepartmentId() {
		return subceDepartmentId;
	}

	public void setSubceDepartmentId(int subceDepartmentId) {
		this.subceDepartmentId = subceDepartmentId;
	}

	public String getSubceDepartmentNameEn() {
		return subceDepartmentNameEn;
	}

	public void setSubceDepartmentNameEn(String subceDepartmentNameEn) {
		this.subceDepartmentNameEn = subceDepartmentNameEn;
	}

	public String getSubceDepartmentNameMr() {
		return subceDepartmentNameMr;
	}

	public void setSubceDepartmentNameMr(String subceDepartmentNameMr) {
		this.subceDepartmentNameMr = subceDepartmentNameMr;
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
