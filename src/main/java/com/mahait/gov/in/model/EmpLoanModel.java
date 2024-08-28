package com.mahait.gov.in.model;

import java.math.BigInteger;
import java.util.Date;

import lombok.Data;

@Data
public class EmpLoanModel {
	String sevaarthId;
	String employeeName;
	String designName;
	String gpfNo;
	String orgnisationName;
	private Long employeeid;
	private Long loantypeid ;
	private Double loanprinamt ;
	private Double loaninterestamt ;
	private Integer loanprininstno ;
	private Integer loanintinstno;
	private Double  loanemiamt ;
	private String loanaccountno ;
	private Double loanintemiamt ;
	private Double loanprinemiamt;
	private String loansancorderno ;
	private Double oddinstno;
	private String voucherno;
	private String voucherdate;
	private String loansancorderdate;
	private String startdate ;
	private Date enddate ;
	private String loanAdvName;
	private Double oddinstamt;
	private String officeName;
	private String loanDate;
	private Integer loanStatus;
	private Integer loanEmpAdvId;
	private Double RecoveredAmount;
	private Double totalRecoveredAmount;
	private Double totalRecoveredInstMent;
	private Double totalRecoveredInstAmt;
	private Integer totalRecoveredInstallment;
	
	private String dcpsNo;
	private Date doj;
	private Date serviceEndDate;
	private Integer dcpsId;
	private String orgInstName;

	
	private String appNo;
	private String pfDesc;
	private Integer appId;
	private Long deptAllowdeducCode;

}
