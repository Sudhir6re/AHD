package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@JsonIgnoreProperties(value = {"mstNomineeDetailsEntity","mstSubDepartmentEntity","mstGpfDetailsEntity"})
@Entity
@Data
@Table(name = "employee_mst", schema = "public")
public class MstEmployeeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private Integer employeeId;

	@Column(name = "SEVAARTH_ID")
	private String sevaarthId;

	@Column(name = "GENDER")
	private Character gender;

	@Column(name = "SALUTATION")
	private Integer salutation;

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
	private Integer religionCode;

	@Column(name = "GISCATAHORY")
	private Integer giscatagory;

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
	private Integer pinCode;

	@Column(name = "VILLAGE_CODE")
	private Integer villageCode;

	@Column(name = "VILLAGE_NAME")
	private String villageName;

	@Column(name = "TALUKA_CODE")
	private Integer talukaCode;

	@Column(name = "DISTRICT_CODE")
	private Integer districtCode;

	@Column(name = "sub_department_id")
	private Integer subDeptId;

	@Column(name = "STATE_CODE")
	private Integer stateCode;

	@Column(name = "COUNTRY_CODE")
	private Integer countryCode;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "DOJ")
	private Date doj;

	@Column(name = "USER_ID")
	private BigInteger userId;

	@Column(name = "CADRE_CODE")
	private BigInteger cadreCode;

	@Column(name = "EMP_CLASS")
	private Integer empClass;

	@Column(name = "FIRST_DESIGNATION_CODE")
	private BigInteger firstDesignationCode;

	@Column(name = "DESIGNATION_CODE")
	private BigInteger designationCode;

	@Column(name = "PARENT_ADMIN_DEPARTMENT_CODE")
	private BigInteger parentAdminDepartmentCode;

	@Column(name = "PARENT_FIELD_DEPARTMENT_CODE")
	private BigInteger parentFieldDepartmentCode;

	@Column(name = "ADMIN_DEPARTMENT_CODE")
	private BigInteger adminDepartmentCode;

	@Column(name = "SUB_CORPORATION_CODE")
	private BigInteger subCorporationId;

	@Column(name = "FIELD_DEPARTMENT_CODE")
	private BigInteger fieldDepartmentCode;

	@Column(name = "CURRENT_OFFICE_CODE")
	private BigInteger currentOfficeCode;

	@Column(name = "PAY_COMMISSION_CODE")
	private Integer payCommissionCode;

	@Column(name = "PAY_SCALE_CODE")
	private BigInteger payScaleCode;

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
	private Integer isgpfflag;

	@Column(name = "crtId")
	private Integer crtId;
	
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
	private Integer displayNameonPranCard;
	@Transient
	private Integer dobProof;
	@Transient
	private Integer eduQual;
	@Transient
	private Integer incomeRange;
	@Transient
	private String uidNo1;
	@Transient
	private String uidNo2;
	@Transient
	private Integer superAnnAge;

	@Column(name = "accMaintainedByOther")
	private String accMaintainedByOther;

	@Transient
	private String uidNo3;

	@Column(name = "PAY_IN_PAY_BAND")
	private BigInteger payInPayBand;

	@Column(name = "GRADE_PAY")
	private Integer gradePay;

	@Column(name = "BANK_CODE")
	private Integer bankCode;

	@Column(name = "IFSC_CODE")
	private String ifscCode;

	@Column(name = "BANK_ACNT_NO")
	private BigInteger bankAcntNo;

	@Column(name = "BANK_BRANCH_CODE")
	private BigInteger bankBranchCode;

	@Column(name = "DDO_CODE")
	private String ddoCode;

	@Column(name = "APPROVAL_BY_DDO_DATE")
	private Date approvalByDdoDate;

	@Column(name = "BILLGROUP_ID")
	private Integer billGroupId;

	@Column(name = "BASIC_PAY")
	private Double basicPay;

	@Column(name = "PERCENTAGE_OF_BASIC")
	private Integer percentageOfBasic;

	@Column(name = "HEAD_ACT_CODE")
	private String headActCode;

	@Column(name = "EMPLOYEE_TYPE")
	private Character employeeType;

	@Column(name = "IS_ACTIVE")
	private Integer isActive;

	@Column(name = "EMP_SERVICE_END_DATE")
	private Date empServiceEndDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "SUPER_ANN_DATE")
	private Date superAnnDate;

	@Column(name = "WITH_EFFECT_FROM_DATE")
	private Date withEffectFromDate;

	@Column(name = "GRADE_ID")
	private Date gradeId;

	@Column(name = "PHOTO_ATTACHMENT_ID")
	private String photoAttachmentId;

	@Column(name = "SIGNATURE_ATTACHMENT_ID")
	private String signatureAttachmentId;

	@Column(name = "CREATED_USER_ID")
	private BigInteger createdUserId;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;

	@Column(name = "UPDATED_USER_ID")
	private BigInteger updatedUserId;
	@Column(name = "PAY_SCALE_LEVEL_ID")
	private String payscalelevelId;

	@Column(name = "locality")
	private String locality;

	@Column(name = "seven_pc_basic")
	private Double sevenPcBasic;

	@Column(name = "remark")
	private String remark;

	@Column(name = "post_detail_id")
	private Integer postdetailid;

	@Column(name = "dcps_gpf_flag")
	private String dcpsgpfflag;

	@Column(name = "state_matrix_7pc_id")
	private Integer svnthpaybasic;

	@Column(name = "pyhical_handicapped")
	private String physicallyHandicapped;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name = "appointment_date")
	private Date dtInitialAppointmentParentInst;

	@Column(name = "seven_pc_level")
	private Integer sevenPcLevel;

	@Column(name = "updated_basic_date")
	private Date updatedBasicDate;

	@Column(name = "updated_basic_user_id")
	private BigInteger updatedBasicUserId;

	@Column(name = "updated_basic_percentage")
	private Integer updatedBasicPercent;

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
	private Integer acDcpsMaintedBy;

	@Column(name = "buckle_no")
	private Integer buckleNo;

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
	private Character cityClass;

	@Column(name = "GID_ID")
	private BigInteger gisId;

	@Column(name = "pay_scal_desc")
	private String payScaleDesc;

	@Override
	public String toString() {
		return "MstEmployeeEntity [employeeId=" + employeeId
				+ ", sevaarthId=" + sevaarthId + ", gender=" + gender + ", salutation=" + salutation
				+ ", employeeFullNameEn=" + employeeFullNameEn + ", employeeFNameEn=" + employeeFNameEn
				+ ", employeeMNameEn=" + employeeMNameEn + ", employeeLNameEn=" + employeeLNameEn
				+ ", employeeFullNameMr=" + employeeFullNameMr + ", employeeFNameMr=" + employeeFNameMr
				+ ", employeeMNameMr=" + employeeMNameMr + ", employeeLNameMr=" + employeeLNameMr
				+ ", employeeMotherName=" + employeeMotherName + ", maritalStatus=" + maritalStatus + ", dob=" + dob
				+ ", bloodGroup=" + bloodGroup + ", mobileNo1=" + mobileNo1 + ", mobileNo2=" + mobileNo2
				+ ", landlineNo=" + landlineNo + ", emailId=" + emailId + ", religionCode=" + religionCode + ", eidNo="
				+ eidNo + ", uidNo=" + uidNo + ", panNo=" + panNo + ", address1=" + address1 + ", address2=" + address2
				+ ", address3=" + address3 + ", pinCode=" + pinCode + ", villageCode=" + villageCode + ", villageName="
				+ villageName + ", talukaCode=" + talukaCode + ", districtCode=" + districtCode + ", subDeptId="
				+ subDeptId + ", stateCode=" + stateCode + ", countryCode=" + countryCode + ", doj=" + doj + ", userId="
				+ userId + ", cadreCode=" + cadreCode + ", empClass=" + empClass + ", firstDesignationCode="
				+ firstDesignationCode + ", designationCode=" + designationCode + ", parentAdminDepartmentCode="
				+ parentAdminDepartmentCode + ", parentFieldDepartmentCode=" + parentFieldDepartmentCode
				+ ", adminDepartmentCode=" + adminDepartmentCode + ", subCorporationId=" + subCorporationId
				+ ", fieldDepartmentCode=" + fieldDepartmentCode + ", currentOfficeCode=" + currentOfficeCode
				+ ", payCommissionCode=" + payCommissionCode + ", payScaleCode=" + payScaleCode
				+ ", reasonForChangedtls=" + reasonForChangedtls + ", employeeBirthPlace=" + employeeBirthPlace
				+ ", ppanNo=" + ppanNo + ", flatUnitNo=" + flatUnitNo + ", employeeFatherHubandName="
				+ employeeFatherHubandName + ", employeeSpouseName=" + employeeSpouseName + ", employeeBankPinCode="
				+ employeeBankPinCode + ", buildingName=" + buildingName + ", locality=" + locality
				+ ", empPermanentFlatUnitNo=" + empPermanentFlatUnitNo + ", empPermanentBuildingName="
				+ empPermanentBuildingName + ", empPermanentLocality=" + empPermanentLocality
				+ ", empPermanentDistrict=" + empPermanentDistrict + ", empPermanentState=" + empPermanentState
				+ ", empPermanentCountry=" + empPermanentCountry + ", empPermanentPinCode=" + empPermanentPinCode
				+ ", empNominee1GuardName=" + empNominee1GuardName + ", empNominee1InvalidCondn="
				+ empNominee1InvalidCondn + ", empNominee2GuardName=" + empNominee2GuardName
				+ ", empNominee2InvalidCondn=" + empNominee2InvalidCondn + ", empNominee3GuardName="
				+ empNominee3GuardName + ", empNominee3InvalidCondn=" + empNominee3InvalidCondn + ", NSDLStatus="
				+ NSDLStatus + ", USPerson=" + USPerson + ", countryofTax=" + countryofTax + ", addressOfTax="
				+ addressOfTax + ", cityOfTax=" + cityOfTax + ", stateofTax=" + stateofTax + ", postCodeofTax="
				+ postCodeofTax + ", tinOrPan=" + tinOrPan + ", state=" + state + ", country=" + country + ", district="
				+ district + ", empSTDCode=" + empSTDCode + ", empPhoneNo=" + empPhoneNo + ", employeeBankName="
				+ employeeBankName + ", employeeBankBranchName=" + employeeBankBranchName + ", employeeBankBankAddress="
				+ employeeBankBankAddress + ", signatureAttachmentIdnew=" + signatureAttachmentIdnew
				+ ", photoAttachmentIdnew=" + photoAttachmentIdnew + ", displayNameonPranCard=" + displayNameonPranCard
				+ ", dobProof=" + dobProof + ", eduQual=" + eduQual + ", incomeRange=" + incomeRange + ", uidNo1="
				+ uidNo1 + ", uidNo2=" + uidNo2 + ", superAnnAge=" + superAnnAge + ", uidNo3=" + uidNo3
				+ ", payInPayBand=" + payInPayBand + ", gradePay=" + gradePay + ", bankCode=" + bankCode + ", ifscCode="
				+ ifscCode + ", bankAcntNo=" + bankAcntNo + ", bankBranchCode=" + bankBranchCode + ", ddoCode="
				+ ddoCode + ", approvalByDdoDate=" + approvalByDdoDate + ", billGroupId=" + billGroupId + ", basicPay="
				+ basicPay + ", percentageOfBasic=" + percentageOfBasic + ", headActCode=" + headActCode
				+ ", employeeType=" + employeeType + ", isActive=" + isActive + ", empServiceEndDate="
				+ empServiceEndDate + ", superAnnDate=" + superAnnDate + ", withEffectFromDate=" + withEffectFromDate
				+ ", gradeId=" + gradeId + ", photoAttachmentId=" + photoAttachmentId + ", signatureAttachmentId="
				+ signatureAttachmentId + ", createdUserId=" + createdUserId + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", updatedUserId=" + updatedUserId + ", payscalelevelId="
				+ payscalelevelId + ", sevenPcBasic=" + sevenPcBasic + ", remark=" + remark + ", postdetailid="
				+ postdetailid + ", dcpsgpfflag=" + dcpsgpfflag + ", svnthpaybasic=" + svnthpaybasic
				+ ", physicallyHandicapped=" + physicallyHandicapped + ", dtInitialAppointmentParentInst="
				+ dtInitialAppointmentParentInst + ", sevenPcLevel=" + sevenPcLevel + ", updatedBasicDate="
				+ updatedBasicDate + ", updatedBasicUserId=" + updatedBasicUserId + ", updatedBasicPercent="
				+ updatedBasicPercent + ", updatedBasicwitheffDate=" + updatedBasicwitheffDate + ", revisedBasic="
				+ revisedBasic + ", relievingDate=" + relievingDate + ", orderDate=" + orderDate + ", newJoiningDate="
				+ newJoiningDate + ", acDcpsMaintedBy=" + acDcpsMaintedBy + ", buckleNo=" + buckleNo
				+ ", isChangeParentDepartment=" + isChangeParentDepartment + ", reasonForChngParentFieldDept="
				+ reasonForChngParentFieldDept + ", pranNo=" + pranNo + ", empType=" + empType
				+ ", reasonForRejOrApprv=" + reasonForRejOrApprv + ", joiningRelievingReason="
				+ joiningRelievingReason + ", cityClass=" + cityClass + ", gisId=" + gisId + "]";
	}

}
