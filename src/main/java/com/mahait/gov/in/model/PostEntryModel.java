package com.mahait.gov.in.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class PostEntryModel {
	
	    private String cmbTaluka;
	    private String ddoCodeforFilter;
	    private String ddoCode;
	    private Long cmbSubFieldDept;
	    private Long postTypeCmbBox;
	    private String postSubTypeCmbBoxTemp;
	    private String postSubTypeCmbBoxPerm;
	    private String purposeCmbBox;
	    private String tempPostTypeCmbBox;
	    private String designationCmb;
	    private String subjectCmb;
	    private long orderCmb;
	    
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date orderDate;
	    
	    private String originalOrderCmb;
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private String originalOrderDate;
	    private String renewalOrderCmb;
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date renewalOrderDate;
	    
	    private Long officeCmb;
	    
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date startDate;
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date endDate;
	    
	    
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date tempEndDate;
	    
	    
	    
	    private int postNumber;
	    
	    private String remarks;
	    
	    private String flag;
	    private Long postId;
	    private String givenurl;

}
