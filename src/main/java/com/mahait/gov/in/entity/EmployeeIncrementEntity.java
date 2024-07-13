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
@Table(name = "employee_increment", schema = "public")
public class EmployeeIncrementEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "basic_pay_increment_id")
	private Long basicPayIncrementId;
	@Column(name = "sevaarth_id")
	private String sevaarthId;

	@Column(name = "employee_id")
	private int employeeId;

	@Column(name = "current_basic_pay")
	private Integer currentBasicPay;

	@Column(name = "current_basic_level_id")
	private Integer currentBasicLevelId;

	@Column(name = "pre_basic_pay")
	private Integer preBasicPay;

	@Column(name = "increment_order_no")
	private String incrementOrderNo;

	@Column(name = "increment_order_date")
	private Date incrementOrderDate;

	@Column(name = "increment_basic_pay_sal")
	private Integer incrementBasicPaySal;

	@Column(name = "increment_basic_level_id")
	private Integer incrementBasicLevelId;

	@Column(name = "effective_from_date")
	private Date effective_from_date;

	@Column(name = "to_increment_date")
	private Date to_increment_date;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Column(name = "remark")
	private String remark;

	@Column(name = "is_active")
	private Character isActive;

	@Column(name = "month")
	private Integer month;
	
	@Column(name = "year")
	private Integer year;

	@Column(name = "CREATED_USER_ID")
	private Integer createdUserId;

	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "DDO_CODE")
	private String ddoCode;

}
