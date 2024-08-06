
package com.mahait.gov.in.entity;

import java.math.BigInteger;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
@Entity
@Table(name="nominee_details_hst",schema="public")
public class MstNomineeDetailsHistEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="nominee_id")
	private Long nomineeid;
	
	@Column(name="employee_id")
	private Long employeeId;
	@Column(name="relation")
	private String relation;
	@Column(name="nominee_name")
	private String nomineename;
	@Column(name="nominee_address")
	private String nomineeaddress;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="dob")
	private Date dob;
	
	
	@Column(name="percent_share")
	private Long percent_share;
	
	@Column(name="guardian_name")
	private String guardianName;

	@Column(name="major_minor")
	private String majorMinor;
	
	
	@Column(name="is_active")
	private String isactive;
	@Column(name="created_date")
	private Date createddate;
	@Column(name="created_id")
	private Long createdid;
	@Column(name="update_date")
	private Date updatedate;
	@Column(name="update_id")
	private Long updateid;
	@Column(name="sevaarth_id")
	private String sevaarthId;
	
	
	@ManyToOne(fetch = FetchType.LAZY) 
	  @JoinColumn(name = "EMPLOYEE_ID",insertable = false, updatable = false) 
	  private MstEmployeeEntity mstEmployeeEntity; 

}
