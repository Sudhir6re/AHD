package com.mahait.gov.in.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name="ALLOWANCE_HRA_MST",schema="public")
public class HRAAllowanceMstEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ALLOWANCE_HRA_ID")
	private Integer allowanceHRAId;
	
	@Column(name="department_allowdeduc_code")
	private Integer departmentAllowdeducCode;
	

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="START_DATE")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="pay_commission_code")
	private Integer payCommissionCode;
	
	@Column(name="city_class_X")
	private String cityClassX;
	
	@Column(name="city_class_Y")
	private String cityClassY;
	
	@Column(name="city_class_Z")
	private String cityClassZ;
	
	@Column(name = "created_user_id")
	private Integer createdUserId;
	
	@Column(name = "updated_user_id")
	private Integer updatedUserId;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "is_active")
	private char isActive;
	
	
	@Transient
	private String payCommissionName;
	

	
}