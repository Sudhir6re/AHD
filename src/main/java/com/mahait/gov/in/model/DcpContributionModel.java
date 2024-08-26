package com.mahait.gov.in.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;


@Data
public class DcpContributionModel {
	
    private Long dcpContributionId;

    private Long dcpEmpId;
    
    private String dcpsNO;
    
    private String employeeName;

    private Integer treasuryCode;

    private String ddoCode;

    private Long billGroupId;

    private String schemeCode;
    
    private String subSchemeCode;

    private String payCommission;

    private String typeOfPayment;

    private int finYearId;

    private int monthId;

    private Double basicPay;

    private Double da;

    private Double contribution;

    private Integer regStatus;

    private Long langId;

    private Long locId;

    private Long dbId;

    private Long createdPostId;

    private Long createdUserId;

    
    private Timestamp createdDate;

    private Long updatedPostId;

    private Long updatedUserId;

    private Timestamp updatedDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date doj;

    private Long rltContriVoucherId;

    private int delayedFinYearId;

    private int delayedMonthId;

    private String employerContriFlag;

    private String status;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date voucherDate;

    private String voucherNo;
    
    private String action;
    
    private String useType;
    
    private Double daRate;
    
	private Double dp;
    
	private Double emprContribution;
    
    
    
    List<DcpContributionModel> lstDcpContributionModel;
    
    


}
