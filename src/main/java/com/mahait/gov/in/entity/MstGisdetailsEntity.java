package com.mahait.gov.in.entity;

import java.math.BigInteger;
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
@Table(name="gis_details_mst",schema="public")
public class MstGisdetailsEntity {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gis_id")
	private Long gisid;
	@Column(name="employee_id")
	private Integer employeeId;
	@Column(name="gis_applicable")
	private String gisapplicable;
	@Column(name="gis_group")
	private String gisgroup;
	@Column(name="membership_date")
	private Date membership_date;
	@Column(name="is_active")
	private String isactive;
	@Column(name="created_date")
	private Date createddate;
	@Column(name="created_id")
	private BigInteger createdid;
	@Column(name="update_date")
	private Date updatedate;
	@Column(name="update_id")
	private BigInteger updateid;
	@Column(name="sevaarth_id")
	private String sevaarthId;
	
	@Column(name="remark")
	private String remark;
	
	@Column(name="gisApplicableOther")
	private String gisApplicableOther;
	
}
