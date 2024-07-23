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
	private String recordcount;
	private Double basicpay;
	private String name;
	private Double svnpcda;
	private String pfacc;
	private String dcpsNo;
	private Double totalContri;
	private Double npsEmpContri;
	private Double npsEmployerDedu;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fromDate;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date toDate;
	private Double totalNpsEmpContri;
	private Double totalNpsEmprContri;

}
