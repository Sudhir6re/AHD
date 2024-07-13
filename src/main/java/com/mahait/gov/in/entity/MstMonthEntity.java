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
@Table(name="month_mst",schema="public")
public class MstMonthEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="MONTH_ID")
	private Integer monthId;
	
	@Column(name="MONTH_ENGLISH")
	private String monthEnglish;
	
	@Column(name="MONTH_MARATHI")
	private String monthMarathi;
	
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

}
