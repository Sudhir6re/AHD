package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name="cadre_group_mst",schema="public")
public class MstCadreGroupEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private long id;
	
	@Column(name="GROUP_NAME_EN")
	private String group_name_en;
	
	@Column(name="GROUP_NAME_MH")
	private String group_name_mh;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "IS_ACTIVE")
	private Character isActive;
	
	@Column(name = "GIS_AMOUNT")
	private Double gisAmount;
	
	@Column(name = "PREMIUM_GIS_AMOUNT")
	private Double preGisAmount;
	
	
	
	

}
