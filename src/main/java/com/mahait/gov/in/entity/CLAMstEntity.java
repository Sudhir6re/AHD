package com.mahait.gov.in.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name="cla_mst",schema="public")
public class CLAMstEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cla_mst_id")
	private Long claMstId;
	
	@Column(name="city_group")
	private String cityGrp;

	@Column(name="IS_TYPE")
	private Integer isType;
	
	@Column(name="AMOUNT",length =13,precision=2)
	private Double amount;
	
	@Column(name="PERCENTAGE")
	private Integer percentage;
	
	@Column(name="pay_commission_code")
	private Long payCommissionCode;
	
	@Column(name = "created_user_id")
	private Long createdUserId;
	
	@Column(name = "updated_user_id")
	private Long updatedUserId;
	
	@Column(name = "svnPcLvl")
	private Long svnPcLvl;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	
	@Column(name = "is_active")
	private char isActive;

	
}