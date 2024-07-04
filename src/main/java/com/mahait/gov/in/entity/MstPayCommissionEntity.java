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
@Table(name="pay_commission_mst",schema="public")
public class MstPayCommissionEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="COMMISSION_NAME_EN")
	private String commission_name_en;
	
	@Column(name="COMMISSION_NAME_MH")
	private String commission_name_mh;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "IS_ACTIVE")
	private Character isActive;
	
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Column(name = "END_DATE")
	private Date endDate;
	

	
}
