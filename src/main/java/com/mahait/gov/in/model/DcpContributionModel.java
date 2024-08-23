package com.mahait.gov.in.model;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lombok.Data;


@Data
public class DcpContributionModel {
	
    private Long dcpContributionId;

    private Long dcpEmpId;

    private Integer treasuryCode;

    private String ddoCode;

    private Long billGroupId;

    private String schemeCode;

    private String payCommission;

    private String typeOfPayment;

    private Long finYearId;

    private Long monthId;

    private Float basicPay;

    private Float da;

    private Float contribution;

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

    private Date startDate;

    private Date endDate;

    private Long rltContriVoucherId;

    private Long delayedFinYearId;

    private Long delayedMonthId;

    private String employerContriFlag;

    private String status;

    private Date voucherDate;

    private String voucherNo;
    
    List<DcpContributionModel> lstDcpContributionModel;
    
    


}
