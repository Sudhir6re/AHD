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
@Table(name="gr_order_documents",schema="public")
public class GROrderDocumentEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="gr_document_id")
    private Long grDocId;  
	
	
	@Column(name="grOrder_id")
	private Long grOrderId;  
	
	@Column(name="file_path")
    private String filePath;

	@Column(name = "CREATED_USER_ID")
	private Long createdUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Long updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "IS_ACTIVE")
	private char isActive;

	

	
	

	
}
