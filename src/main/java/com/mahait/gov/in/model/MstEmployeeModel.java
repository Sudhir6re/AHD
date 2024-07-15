package com.mahait.gov.in.model;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class MstEmployeeModel {
	//Fields
	private Integer employeeId;
	private String sevaarthId;
	private Character gender;
	private Integer salutation;
	private String employeeFullNameEn;
	private String employeeFNameEn;
	private String employeeMNameEn;
	private String employeeLNameEn;
	private String employeeFullNameMr;
	private String employeeFNameMr;
	private String employeeMNameMr;
	private String employeeLNameMr;
	private String locality;
	
	private String employeeMotherName;
	private Character maritalStatus;
	private Double sevenPcBasic;
	private Integer yearId;
	
	
	private Integer sevenPCLevel;
	
	

	private BigInteger empMapped;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dob;
	private Date nomineeDOB;

	private Character bloodGroup;
	private Long mobileNo1;
	
	private Long mobileNo2;
	private String landlineNo;
	private String emailId;
	private Integer religionCode;
	private String eidNo;
	private String uidNo;				
	private String uidNo1;
	private String uidNo2;
	private String uidNo3;
	private String panNo;
	private String address1;
	private String address2;
	private String address3;
	private Integer pinCode;
	private Integer villageCode;
	private String villageName;
	private Integer talukaCode;
	private Integer districtCode;
	private Integer stateCode;
	private Integer countryCode;
	private Date appointmentDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date doj;
	private BigInteger userId;
	private BigInteger cadreId;
	private Integer empClass;
	private BigInteger firstDesignationId;
	private BigInteger designationId;
	private BigInteger parentAdminDepartmentId;
	private BigInteger parentFieldDepartmentId;
	private BigInteger adminDepartmentId;
	private BigInteger subCorporationId;
	
	private BigInteger fieldDepartmentId;
	private BigInteger currentOfficeId;
	private Integer payCommissionCode;
	private BigInteger PayScaleCode;
	private String payscaleDesc;
	private String bankBranchName;
	private String postName;
	private String cadreName;
	private Integer orderNo;
	private Integer gisApplId;
	private String billDesc;


	private BigInteger payInPayBand;
	private String payscalelevelId;
//	private BigInteger svnthpaybasic;
	private Integer gradePay;
	private Integer bankId;
	private String ifscCode;
	private BigInteger bankAcntNo;
	private BigInteger bankBranchId;
	private String ddoCode;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date approvalByDdoDate;
	private BigInteger billgroupId;
	private Double basicPay;
	private Integer percentageOfBasic;
	private String headActCode;
	private Character employeeType;
	private Integer isActive;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date empServiceEndDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date superAnnDate;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dtJoinCurrentPost;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date withEffectFromDate;
	private Date gradeId;
	private String photoAttachmentId;
	private String signatureAttachmentId;
	private BigInteger createdUserId;
	private Date createdDate;
	private Date updatedDate;
	private BigInteger updatedUserId;
	private String payCommissionName;
	private Integer giscatagory;
	
	//Extra
	private String employeeFullName;
	private String designationName;
	private String departmentNameEn;
	//End
	
    private String action;
    private String deptNm;
    private String subdeptNm;

	private String remark;
    private Integer postdetailid;
    private String dcpsgpfflag;
    private String dcpsaccountmaintainby;
    private String pfacno;
    private String accountmaintainby;
    private String pfdescription;
    private String gisapplicable;
    private String gisgroup;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date membership_date;
    private String nomineename;
    private String nomineeaddress;
    private Integer percent_share;
    private String relation;
    private String indiApproveOrderNo;
   
	private String strArrNomineeName;
    private String strArrAddress;
    private String strArrDob;
    private String strArrPercentShare;
    private String strArrRelationship;
    
	private Integer dcpsid;
	private String dcpsno;
    private Integer gisid;
    private Integer gpf_id;
    private Integer nomineeid;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date rdob;
    private String pfseries;
    private String imagePath;
    private String imagePathSign;
   // private String deptNm;
	private Integer svnthpaybasic;
	private String physicallyHandicapped;
	private String superannuationage;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dtInitialAppointmentParentInst;
	private String instName;
	private String insttelnoone;
	private String insttelnotwo;
	private String instemail;
	private BigInteger payScaleId;
	private Character cityClass;
	private Integer acDcpsMaintainedBy;
	private String instituteAdd;
	
	private Integer buckleNo;
	private Boolean isChangeParentDepartment;
	private String reasonForChngParentFieldDept;
	private String pranNo;
	private String empType;
	private String gisRemark;
	private String rltnOther;
	private String accMaintainedByOther;
	private Double hraBasic;
	private String begisCatg;
	private String isAllowOrDeduct;
	private Double totalGrossAmt;
	private Double totalNetAmt;
	private Double totalDedAmt;
	private String label;
	
	
	private List<MstNomineeDetailsModel> lstMstNomineeDetails;
	
	public List<MstEmployeeModel> mstEmployeeModelList=new ArrayList<>();
	
	
	
}
