package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Data
@Entity
@Table(name="MST_DCPS_BILL_GROUP",schema="public")
public class MstDcpsBillGroup  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="bill_group_id")
	private BigInteger dcpsDdoBillGroupId;
	
	@Column(name="ddo_code")
	private String dcpsDdoCode;
	
	@Column(name="description")
	private String description;
	
	@Column(name="scheme_name")
	private String dcpsDdoBillSchemeName;
	
	@Column(name="scheme_code")
	private String dcpsDdoSchemeCode;
	
	@Column(name="TYPE_OF_POST")
	private String dcpsDdoBillTypeOfPost;
	

	@Column(name="SUB_BG_OR_NOT")
	private Integer subBGOrNot;
	
	
	@Column(name="LANG_ID")
	private BigInteger LangId;
	
	@Column(name="LOC_ID")
	private BigInteger LocId;
	
	@Column(name="DB_ID")
	private BigInteger DbId;
	
	@Column(name = "CREATED_POST_ID")
	private BigInteger PostId;
	

	@Column(name = "CREATED_USER_ID")
	private BigInteger UserId;
	
	@Column(name = "CREATED_DATE")
	private Date CreatedDate;
	
	@Column(name = "UPDATED_POST_ID")
	private BigInteger UpdatedPostId;

	@Column(name = "UPDATED_USER_ID")
	private BigInteger UpdatedUserId;
	
	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "bill_no")
	private BigInteger billNo;
	

	@Column(name="BILL_DELETED")
	private Character billDeleted;
	
	@Column(name="BILL_DCPS")
	private Character billDcps;
	
	@Column(name="SUB_SCHEME_CODE")
	private String dcpsDdoSubSchemeCode;

	
	

}

