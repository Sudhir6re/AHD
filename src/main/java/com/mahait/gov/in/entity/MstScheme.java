package com.mahait.gov.in.entity;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;

@Data
@Entity
@Table(name="mst_scheme",schema="public")
public class MstScheme {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="scheme_Id")
	private BigInteger schemeId;

	@Column(name="scheme_Code")
	private String schemeCode;
	
	@Column(name="scheme_Name")
	private String schemeName;

	@Column(name="demand_Code")
	private String demandCode;
	
	@Column(name="major_Head")
	private String majorHead;
	
	@Column(name="sub_Major_Head")
	private String subMajorHead;
	
	@Column(name="minor_Head")
	private String minorHead;
	
	@Column(name="sub_Minor_Head")
	private String subMinorHead;

	
	@Column(name="sub_Head")
	private String subHead;
	

	@Column(name="status")
	private String status;
	
	
	@Column(name="lang_id")
	private String lang_id;
	
	@Column(name = "fin_Yr_Id")
	private BigInteger finYrId;
	

	@Column(name="charged")
	private String charged;

	
	@Column(name="plan")
	private char plan;
	
	@Column(name="scheme_Type")
	private String schemeType;
	
	
	@Column(name="dept_Id")
	private String deptId;
	

    
}

	