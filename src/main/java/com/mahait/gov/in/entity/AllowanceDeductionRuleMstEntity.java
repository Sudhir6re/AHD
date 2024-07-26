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
@Table(name="ALLOWANCE_DEDUCDUCTION_WISE_RULE_MST",schema="public")
public class AllowanceDeductionRuleMstEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ALLOWANCE_DEDUCDUCTION_WISE_RULE_ID")
	private Integer allowanceDeductionWiseRuleId;
	
	@Column(name="department_allowdeduc_code")
	private Integer departmentAllowdeducCode;

	@Column(name="IS_TYPE")
	private Integer isType;
	
	@Column(name="AMOUNT",length =13,precision=2)
	private Double amount;
	
	@Column(name="PERCENTAGE")
	private Integer percentage;
    
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="START_DATE")
	private Date startDate;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="END_DATE")
	private Date endDate;
	
	@Column(name="pay_commission_code")
	private Integer payCommissionCode;
	
	@Column(name = "created_user_id")
	private int createdUserId;
	
	@Column(name = "updated_user_id")
	private int updatedUserId;
	
	@Column(name = "created_date")
	private Date createdDate;
	
	@Column(name = "updated_date")
	private Date updatedDate;
	
	
	@Column(name = "is_active")
	private char isActive;
	
	
	private transient String commisionName;
	private transient String departmentAlloDeducName;



	
}