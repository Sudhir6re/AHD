package com.mahait.gov.in.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="designation_mst",schema="public")
public class MstDesignationEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DESIGNATION_ID")
	private Long desgination_id;
	
	
	@Column(name="DESIGNATION_CODE")
	private Integer desgination_code;
	
	@Column(name="DESIGNATION_NAME")
	private String desgination;
	
	@Column(name="DESIGNATION_SHORT_NAME")
	private String designationShortName;
	
	@Column(name = "CREATED_USER_ID")
	private Integer CreatedUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "IS_ACTIVE")
	private char isActive;

	@Column(name = "CADRE_CODE") 
	private Integer cadreCode;
	
	
	@Column(name = "CADRE_GROUP") 
	private Integer cadreGroup;
}
