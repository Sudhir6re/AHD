package com.mahait.gov.in.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class RegularReportModel {
	
	private Integer yearId;
	private Integer monthId;
	private String treasuryName;
	private String treasuryCode;
	private String DeptName;
	private Long billGroup;
	private Long paybillId;
	private String recordcount;
	private Double basicpay;
	private String name;
	private String pran;
	private Double svnpcda;
	private String pfacc;
	private String dcpsNo;
	private Double totalContri;
	private Double npsEmpContri;
	private Double npsEmployerDedu;
	private Double npsEmprAllow;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	private Double totalNpsEmpContri;
	private Double totalNpsEmprContri;
	private Double dp;
	private Double dcpsReg;
	private String amtInWords;
	private String ddoCode;
	private String schemeName;
	private String schemeCode;
	private Double grossAmount;
	private Double netAmount;
	private  String demandCode;
	private  String majorHead;
	private  String subMajorHead;
	private  String minorHead;
	private  String subMinorHead;
	private  String subHead;
	private  String desgName;
	private  String panNo;
	private  Date billCreatedDate;
	private  String bankAccNo;
	private  String branchName;
	private  String sevaarthId;
}
