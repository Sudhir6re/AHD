package com.mahait.gov.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "bill_flag_status", schema = "public")
public class FlagStatusEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	@Column(name="bill_status_id")
	private int billStatusId;
	
	@Column(name="bill_status_code")
	private int billStatusCode;
	
	@Column(name="is_active")
	private int isActive;
	
	@Column(name="status_description")
	private String billDescription;
	
	@Column(name="status_name")
	private String billName;
	
	
}
