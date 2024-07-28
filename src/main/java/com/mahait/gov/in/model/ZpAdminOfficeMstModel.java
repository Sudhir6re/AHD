package com.mahait.gov.in.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
public class ZpAdminOfficeMstModel {
	
	private Long ofcId;

	private String officeName;

	private String officeCode;

	private Long langId;

	private Long createdBy;

	private Timestamp createdDate;

	private Long updatedBy;

	private Timestamp updatedDate;

	private Long createdByPost;

	private String schemeCode;

	private String dcpsOffName;
	
    private Integer isActive;

}
