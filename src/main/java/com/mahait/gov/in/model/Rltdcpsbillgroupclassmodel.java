package com.mahait.gov.in.model;

import java.math.BigInteger;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Data
public class Rltdcpsbillgroupclassmodel {
	
	
	private Long dcpsBillClassGroupId;
	private Long dcpsBillGroupId;
	private String billgroupclass;
	private Long LangId;
	private Long LocId;
	private Long DbId;
	private Long PostId;
	private Long UserId;
	private Date CreatedDate;
	private Long UpdatedPostId;
}
