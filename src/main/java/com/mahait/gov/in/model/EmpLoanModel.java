package com.mahait.gov.in.model;


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
	private Long loanprinamt ;
	private Long loaninterestamt ;
	private Long loanprininstno ;
	private Long loanintinstno;
	private Long  loanemiamt ;
	private String loanaccountno ;
	///private Date  loandate ;
	private Long loanintemiamt ;
	private Long loanprinemiamt;
	private String loansancorderno ;
	private Long oddinstno;
	private String voucherno;
	private String voucherdate;
	private String loansancorderdate;
	private String startdate ;
	private Date enddate ;
	private String loanAdvName;
	private Long oddinstamt;
	private String officeName;
	private String loanDate;
	private Long loanStatus;
	private Long loanEmpAdvId;
	private Long RecoveredAmount;
	private Long totalRecoveredAmount;
	private Long totalRecoveredInstMent;
	private Long totalRecoveredInstAmt;
	private Long totalRecoveredInstallment;
	
	private String dcpsNo;
	private Date doj;
	private Date serviceEndDate;
	private Long dcpsId;
	private String orgInstName;

	
	private String appNo;
	private String pfDesc;
	private Long appId;
	

}
