package com.mahait.gov.in.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="rlt_dcps_billgroup_classgroup",schema="public")
public class RltBillgroupClassgroup  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="DCPS_BILLGROUP_CLASSGROUP_ID")
	private Long dcpsBillClassGroupId;
	
	@Column(name="DCPS_BILLGROUP_ID")
	private Long dcpsBillGroupId;
	
	@Column(name="DCPS_CLASS_GROUP")
	private String dcpsClassGroup;
	
	
	@Column(name="LANG_ID")
	private Long LangId;
	
	@Column(name="LOC_ID")
	private Long LocId;
	
	@Column(name="DB_ID")
	private Long DbId;
	
	@Column(name = "CREATED_POST_ID")
	private Long PostId;
	

	@Column(name = "CREATED_USER_ID")
	private Long UserId;
	
	@Column(name = "CREATED_DATE")
	private Date CreatedDate;
	
	@Column(name = "UPDATED_POST_ID")
	private Long UpdatedPostId;

	@Column(name = "UPDATED_USER_ID")
	private Long UpdatedUserId;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
}