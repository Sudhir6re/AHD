package com.mahait.gov.in.entity;

<<<<<<< HEAD

import javax.persistence.*;

import lombok.Data;

import java.io.Serializable;
=======
import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
>>>>>>> 670bac58cd5d146238c19729e8d81e21c0baf175

@Data
@Entity
@Table(name = "employee_details_no_approval")
public class EmployeeDetailEntity implements Serializable {

<<<<<<< HEAD
    @Id
    @Column(name = "employee_id")
    private String employeeId;

    @Column(name = "sevaarth_id")
    private String sevaarthId;

    @Column(name = "gender")
    private String gender;

    @Column(name = "salutation")
    private String salutation;

    @Column(name = "employee_full_name_en")
    private String employeeFullNameEn;

    @Column(name = "employee_f_name_en")
    private String employeeFNameEn;

    @Column(name = "employee_m_name_en")
    private String employeeMNameEn;

    @Column(name = "employee_l_name_en")
    private String employeeLNameEn;

    @Column(name = "dob")
    private String dob; 

    @Column(name = "mobile_no1")
    private String mobileNo1;

    @Column(name = "mobile_no2")
    private String mobileNo2;

    @Column(name = "email_id")
    private String emailId;

    @Column(name = "eid_no")
    private String eidNo;

    @Column(name = "uid_no")
    private String uidNo;

    @Column(name = "pan_no")
    private String panNo;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "address3")
    private String address3;

    @Column(name = "pincode")
    private String pincode;

    @Column(name = "district_code")
    private String districtCode;

    @Column(name = "state_code")
    private String stateCode;

    @Column(name = "appointment_date")
    private String appointmentDate;

    @Column(name = "doj")
    private String doj; 

    @Column(name = "user_id")
    private String userId;

    @Column(name = "cadre_code")
    private String cadreCode;

    @Column(name = "first_designation_code")
    private String firstDesignationCode;

    @Column(name = "designation_code")
    private String designationCode;

    @Column(name = "field_department_code")
    private String fieldDepartmentCode;

    @Column(name = "pay_scale_code")
    private String payScaleCode;

    @Column(name = "pay_in_pay_band")
    private String payInPayBand;

    @Column(name = "grade_pay")
    private String gradePay;

    @Column(name = "bank_code")
    private String bankCode;

    @Column(name = "ifsc_code")
    private String ifscCode;

    @Column(name = "bank_acnt_no")
    private String bankAcntNo;

    @Column(name = "bank_branch_code")
    private String bankBranchCode;

    @Column(name = "ddo_code")
    private String ddoCode;

    @Column(name = "approval_by_ddo_date")
    private String approvalByDdoDate; 

    @Column(name = "billgroup_id")
    private String billgroupId;

    @Column(name = "basic_pay")
    private String basicPay;

    @Column(name = "percentage_of_basic")
    private String percentageOfBasic;

    @Column(name = "head_act_code")
    private String headActCode;

    @Column(name = "employee_type")
    private String employeeType;

    @Column(name = "is_active")
    private String isActive;

    @Column(name = "emp_service_end_date")
    private String empServiceEndDate; 

    @Column(name = "super_ann_date")
    private String superAnnDate;

    @Column(name = "with_effect_from_date")
    private String withEffectFromDate;

    @Column(name = "grade_id")
    private String gradeId;

    @Column(name = "photo_attachment_id")
    private String photoAttachmentId;

    @Column(name = "signature_attachment_id")
    private String signatureAttachmentId;

    @Column(name = "created_user_id")
    private String createdUserId;

    @Column(name = "created_date")
    private String createdDate; 
    @Column(name = "updated_date")
    private String updatedDate; 

    @Column(name = "updated_user_id")
    private String updatedUserId;

    @Column(name = "pay_scale_level_id")
    private String payScaleLevelId;

    @Column(name = "post_detail_id")
    private String postDetailId;

    @Column(name = "remark")
    private String remark;

    @Column(name = "state_matrix_7pc_id")
    private String stateMatrix7pcId;

    @Column(name = "pyhical_handicapped")
    private String pyhicalHandicapped;

    @Column(name = "dcps_gpf_flag")
    private String dcpsGpfFlag;

    @Column(name = "pran_no")
    private String pranNo;

    @Column(name = "pran_status")
    private String pranStatus;

    @Column(name = "pran_remarks")
    private String pranRemarks;

    @Column(name = "pay_commission_code")
    private String payCommissionCode;

    @Column(name = "current_office_code")
    private String currentOfficeCode;

    @Column(name = "pay_scale_id_old")
    private String payScaleIdOld;

    @Column(name = "billgroup_id_old")
    private String billgroupIdOld;

    @Column(name = "seven_pc_basic")
    private String sevenPcBasic;

    @Column(name = "seven_pc_level")
    private String sevenPcLevel;

    @Column(name = "pay_scale_id")
    private String payScaleId;

    @Column(name = "ac_dcps_maintained_by")
    private String acDcpsMaintainedBy;

    @Column(name = "buckle_no")
    private String buckleNo;

    @Column(name = "emp_type")
    private String empType;

    @Column(name = "reason_for_change_parent_field_dept")
    private String reasonForChangeParentFieldDept;

    @Column(name = "city_class")
    private String cityClass;

    @Column(name = "reason_for_changedtls")
    private String reasonForChangedtls;

    @Column(name = "emp_class")
    private String empClass;

    @Column(name = "acc_maintained_by_other")
    private String accMaintainedByOther;

    @Column(name = "locality")
    private String locality;

    @Column(name = "inst_name")
    private String instName;

    @Column(name = "inst_email")
    private String instEmail;

    @Column(name = "curr_post_joining_date")
    private String currPostJoiningDate;

    @Column(name = "dt_join_current_post")
    private String dtJoinCurrentPost; 

    @Column(name = "accountmaintainby")
    private String accountmaintainby;

    @Column(name = "dcpsaccountmaintainby")
    private String dcpsaccountmaintainby;

    @Column(name = "gisapplicable")
    private String gisapplicable;

    @Column(name = "gisgroup")
    private String gisgroup;

    @Column(name = "indi_approve_order_no")
    private String indiApproveOrderNo;

    @Column(name = "membership_date")
    private String membershipDate; 

    @Column(name = "pfacno")
    private String pfacno;

    @Column(name = "pfdescription")
    private String pfdescription;

    @Column(name = "pfseries")
    private String pfseries;

    @Column(name = "dcps_no")
    private String dcpsNo;

    @Column(name = "head_of_acc_code")
    private String headOfAccCode;

    @Column(name = "appointment")
    private String appointment;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "more_qualification")
    private String moreQualification;

    @Column(name = "emp_types")
    private String empTypes;

=======
	@Id
	@Column(name = "EMPLOYEE_ID")
	private Long employeeId;

	@Column(name = "SEVAARTH_ID")
	private String sevaarthId;

	@Column(name = "GENDER")
	private Character gender;

	@Column(name = "SALUTATION")
	private Long salutation;

	@Column(name = "EMPLOYEE_FULL_NAME_EN")
	private String employeeFullNameEn;

	@Column(name = "EMPLOYEE_F_NAME_EN")
	private String employeeFNameEn;

	@Column(name = "gis_remark")
	private String GisRemark;

	@Column(name = "EMPLOYEE_M_NAME_EN")
	private String employeeMNameEn;

	@Column(name = "EMPLOYEE_L_NAME_EN")
	private String employeeLNameEn;

	@Column(name = "EMPLOYEE_FULL_NAME_MR")
	private String employeeFullNameMr;

	@Column(name = "EMPLOYEE_F_NAME_MR")
	private String employeeFNameMr;

	@Column(name = "EMPLOYEE_M_NAME_MR")
	private String employeeMNameMr;

	@Column(name = "EMPLOYEE_L_NAME_MR")
	private String employeeLNameMr;

	@Column(name = "EMPLOYEE_MOTHER_NAME")
	private String employeeMotherName;

	@Column(name = "MARITAL_STATUS")
	private Character maritalStatus;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DOB")
	private Date dob;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "membership_date")
	private Date membership_date;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DtJoinCurrentPost")
	private Date dtJoinCurrentPost;

	@Column(name = "BLOOD_GROUP")
	private Character bloodGroup;

	@Column(name = "MOBILE_NO1")
	private Long mobileNo1;

	@Column(name = "MOBILE_NO2")
	private Long mobileNo2;

	@Column(name = "LANDLINE_NO")
	private String landlineNo;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "Inst_email")
	private String instemail;

	@Column(name = "RELIGION_CODE")
	private Long religionCode;

	@Column(name = "GISCATAHORY")
	private Long giscatagory;

	@Column(name = "EID_NO")
	private String eidNo;

	@Column(name = "UID_NO")
	private String uidNo;

	@Column(name = "PAN_NO")
	private String panNo;

	@Column(name = "ADDRESS1")
	private String address1;

	@Column(name = "ADDRESS2")
	private String address2;

	@Column(name = "ADDRESS3")
	private String address3;

	@Column(name = "PINCODE")
	private Long pinCode;

	@Column(name = "VILLAGE_CODE")
	private Long villageCode;

	@Column(name = "VILLAGE_NAME")
	private String villageName;

	@Column(name = "TALUKA_CODE")
	private Long talukaCode;

	@Column(name = "DISTRICT_CODE")
	private Long districtCode;

	@Column(name = "sub_department_id")
	private Long subDeptId;

	@Column(name = "STATE_CODE")
	private Long stateCode;

	@Column(name = "COUNTRY_CODE")
	private Long countryCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DOJ")
	private Date doj;

	@Column(name = "USER_ID")
	private Long userId;

	@Column(name = "CADRE_CODE")
	private Long cadreCode;

	@Column(name = "EMP_CLASS")
	private Long empClass;

	@Column(name = "FIRST_DESIGNATION_CODE")
	private Long firstDesignationCode;

	@Column(name = "DESIGNATION_CODE")
	private Long designationCode;

	@Column(name = "PARENT_ADMIN_DEPARTMENT_CODE")
	private Long parentAdminDepartmentCode;

	@Column(name = "PARENT_FIELD_DEPARTMENT_CODE")
	private Long parentFieldDepartmentCode;

	@Column(name = "ADMIN_DEPARTMENT_CODE")
	private Long adminDepartmentCode;

	@Column(name = "SUB_CORPORATION_CODE")
	private Long subCorporationId;

	@Column(name = "FIELD_DEPARTMENT_CODE")
	private Long fieldDepartmentCode;

	@Column(name = "CURRENT_OFFICE_CODE")
	private Long currentOfficeCode;

	@Column(name = "PAY_COMMISSION_CODE")
	private Long payCommissionCode;

	@Column(name = "PAY_SCALE_CODE")
	private Long payScaleCode;

	@Column(name = "IsApplicableforBeams", length = 1)
	private Character IsApplicableforBeams;

	@Column(name = "REASON_FOR_CHANGEDTLS")
	private String reasonForChangedtls;

	@Column(name = "instituteAdd")
	private String instituteAdd;

	@Column(name = "departmentNameEn")
	private String departmentNameEn;

	@Column(name = "instName")
	private String instName;

	@Column(name = "pfacno")
	private String pfacno;

	@Column(name = "pfdescription")
	private String pfdescription;

	@Column(name = "gisapplicable")
	private String gisapplicable;

	@Column(name = "gisgroup")
	private String gisgroup;

	@Column(name = "dcpsaccountmaintainby")
	private String dcpsaccountmaintainby;

	@Column(name = "accountmaintainby")
	private String accountmaintainby;

	@Column(name = "indiApproveOrderNo")
	private String indiApproveOrderNo;

	@Column(name = "pfseries")
	private String pfseries;

	@Column(name = "dcps_no")
	private String dcpsNo;

	@Column(name = "isgpfflag")
	private Long isgpfflag;

	@Column(name = "crtId")
	private Long crtId;

	@Column(name = "headOfAccCode")
	private String headOfAccCode;

	@Transient
	private String employeeBirthPlace;

	@Transient
	private String nameOfPostDesg;

	@Transient
	private String ppanNo;
	@Transient
	private String flatUnitNo;
	@Transient
	private String employeeFatherHubandName;
	@Transient
	private String employeeSpouseName;
	@Transient
	private String employeeBankPinCode;
	@Transient
	private String buildingName;

	@Transient
	private String empPermanentFlatUnitNo;
	@Transient
	private String empPermanentBuildingName;
	@Transient
	private String empPermanentLocality;
	@Transient
	private String empPermanentDistrict;
	@Transient
	private String empPermanentState;
	@Transient
	private String empPermanentCountry;
	@Transient
	private String empPermanentPinCode;
	@Transient
	private String empNominee1GuardName;
	@Transient
	private String empNominee1InvalidCondn;
	@Transient
	private String empNominee2GuardName;
	@Transient
	private String empNominee2InvalidCondn;
	@Transient
	private String empNominee3GuardName;
	@Transient
	private String empNominee3InvalidCondn;
	@Transient
	private String NSDLStatus;
	@Transient
	private String USPerson;
	@Transient
	private String countryofTax;
	@Transient
	private String addressOfTax;
	@Transient
	private String cityOfTax;
	@Transient
	private String stateofTax;
	@Transient
	private String postCodeofTax;
	@Transient
	private String tinOrPan;
	@Transient
	private String state;;
	@Transient
	private String country;
	@Transient
	private String district;
	@Transient
	private String empSTDCode;
	@Transient
	private String empPhoneNo;
	@Transient
	private String employeeBankName;
	@Transient
	private String employeeBankBranchName;
	@Transient
	private String employeeBankBankAddress;
	@Transient
	private MultipartFile signatureAttachmentIdnew;
	@Transient
	private MultipartFile photoAttachmentIdnew;
	@Transient
	private Long displayNameonPranCard;
	@Transient
	private Long dobProof;
	@Transient
	private Long eduQual;
	@Transient
	private Long incomeRange;
	@Transient
	private String uidNo1;
	@Transient
	private String uidNo2;
	@Transient
	private Long superAnnAge;

	@Column(name = "accMaintainedByOther")
	private String accMaintainedByOther;

	@Transient
	private String uidNo3;

	@Column(name = "PAY_IN_PAY_BAND")
	private Long payInPayBand;

	@Column(name = "GRADE_PAY")
	private Long gradePay;

	@Column(name = "BANK_CODE")
	private Long bankCode;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "BANK_ACNT_NO")
	private Long bankAcntNo;

	@Column(name = "BANK_BRANCH_CODE")
	private Long bankBranchCode;

	@Column(name = "DDO_CODE")
	private String ddoCode;

	@Column(name = "APPROVAL_BY_DDO_DATE")
	private Date approvalByDdoDate;

	@Column(name = "BILLGROUP_ID")
	private Long billGroupId;

	@Column(name = "BASIC_PAY")
	private Double basicPay;

	@Column(name = "PERCENTAGE_OF_BASIC")
	private Long percentageOfBasic;

	@Column(name = "HEAD_ACT_CODE")
	private String headActCode;

	@Column(name = "EMPLOYEE_TYPE")
	private Character employeeType;

	@Column(name = "IS_ACTIVE")
	private Long isActive;

	@Column(name = "EMP_SERVICE_END_DATE")
	private Date empServiceEndDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "SUPER_ANN_DATE")
	private Date superAnnDate;

	@Column(name = "WITH_EFFECT_FROM_DATE")
	private Date withEffectFromDate;

	@Column(name = "GRADE_ID")
	private String gradeId;

	@Column(name = "PHOTO_ATTACHMENT_ID")
	private String photoAttachmentId;

	@Column(name = "SIGNATURE_ATTACHMENT_ID")
	private String signatureAttachmentId;

	@Column(name = "CREATED_USER_ID")
	private Long createdUserId;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_USER_ID")
	private Long updatedUserId;

	@Column(name = "PAY_SCALE_LEVEL_ID")
	private String payscalelevelId;

	@Column(name = "locality")
	private String locality;

	@Column(name = "seven_pc_basic")
	private Double sevenPcBasic;

	@Column(name = "remark")
	private String remark;

	@Column(name = "post_detail_id")
	private Long postdetailid;

	@Column(name = "dcps_gpf_flag")
	private String dcpsgpfflag;

	@Column(name = "state_matrix_7pc_id")
	private Long svnthpaybasic;

	@Column(name = "pyhical_handicapped")
	private String physicallyHandicapped;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "appointment_date")
	private Date dtInitialAppointmentParentInst;

	@Column(name = "seven_pc_level")
	private Long sevenPcLevel;

	@Column(name = "updated_basic_date")
	private Date updatedBasicDate;

	@Column(name = "updated_basic_user_id")
	private Long updatedBasicUserId;

	@Column(name = "updated_basic_percentage")
	private Long updatedBasicPercent;

	@Column(name = "updated_basic_witheff_date")
	private Date updatedBasicwitheffDate;

	@Column(name = "revised_basic")
	private Double revisedBasic;

	@Column(name = "relieving_date")
	private Date relievingDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "order_date")
	private Date orderDate;

	@Column(name = "new_joining_date")
	private Date newJoiningDate;

	@Column(name = "ac_dcps_maintained_by ")
	private Long acDcpsMaintedBy;

	@Column(name = "buckle_no")
	private Long buckleNo;

	@Column(name = "is_changeParent_department")
	private Boolean isChangeParentDepartment;

	@Column(name = "reason_for_change_parent_field_dept ")
	private String reasonForChngParentFieldDept;

	@Column(name = "pran_no ")
	private String pranNo;

	@Column(name = "emp_type")
	private String empType;

	@Column(name = "reason_for_rejectorapproved")
	private String reasonForRejOrApprv;

	@Column(name = "is_mapped_with_nps")
	private Character isMappedWithNps;

	@Column(name = "hra_basic")
	private Double hraBasic;

	@Column(name = "BEGIS_CATG")
	private String begisCatg;

	@Column(name = "joining_relieving_reason")
	private String joiningRelievingReason;

	@Column(name = "city_class")
	private String cityClass;

	@Column(name = "GID_ID")
	private Long gisId;

	@Column(name = "pay_scal_desc")
	private String payScaleDesc;

	@Column(name = "HOD_lOC_CODE")
	private Long parentFieldDepartmentId;

	@Column(name = "QUALIFICATION")
	private String qualification;

	@Column(name = "APPOINTMENT")
	private String appointment;

	@Column(name = "EMP_TYPES")
	private String teaching;

	@Column(name = "SEC_QUALIFICATION")
	private String secqualification;

	@Column(name = "MORE_QUALIFICATION")
	private String morequalification;
>>>>>>> 670bac58cd5d146238c19729e8d81e21c0baf175
}
