package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(value = {"mstNomineeDetailsEntity","mstSubDepartmentEntity","mstGpfDetailsEntity"})
@Entity
@Table(name = "employee_mst", schema = "public")
public class MstEmployeeEntity {

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployeeEntity", orphanRemoval = true)
	private List<MstNomineeDetailsEntity> mstNomineeDetailsEntity;

//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name = "SUB_DEPARTMENT_ID", nullable = true, insertable = false, updatable = false)
//	private MstSubDepartmentEntity mstSubDepartmentEntity;

	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "mstEmployeeEntity",
	 * orphanRemoval = true) private List<MstGpfDetailsEntity> mstGpfDetailsEntity;
	 */

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "mstEmployeeEntity", orphanRemoval = true)
	private MstGpfDetailsEntity mstGpfDetailsEntity;

	/*
	 * @OneToOne(cascade = CascadeType.ALL, mappedBy = "mstEmployeeEntity",
	 * orphanRemoval = true) private List<MstEmployeeNPSEntity> mstEmployeeNPSEntity
	 * = new ArrayList<>();
	 */

	// private List<MstNomineeDetailsEntity> mstNomineeDetailsEntity;
	/*
	 * @JoinTable( name = "sub_department_mst", joinColumns = @JoinColumn(name =
	 * "parent_field_department_code"), inverseJoinColumns = @JoinColumn(name =
	 * "SUB_DEPARTMENT_ID"))
	 */

	/*
	 * public List<MstGpfDetailsEntity> getMstGpfDetailsEntity() { return
	 * mstGpfDetailsEntity; }
	 * 
	 * public void setMstGpfDetailsEntity(List<MstGpfDetailsEntity>
	 * mstGpfDetailsEntity) { this.mstGpfDetailsEntity = mstGpfDetailsEntity; }
	 */

	/*
	 * public void setMstSubDepartmentEntity(MstSubDepartmentEntity
	 * mstSubDepartmentEntity) { this.mstSubDepartmentEntity =
	 * mstSubDepartmentEntity; }
	 */
	public MstGpfDetailsEntity getMstGpfDetailsEntity() {
		return mstGpfDetailsEntity;
	}

	/*
	 * public void setMstGpfDetailsEntity(MstGpfDetailsEntity mstGpfDetailsEntity) {
	 * this.mstGpfDetailsEntity = mstGpfDetailsEntity; }
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMPLOYEE_ID")
	private Integer employeeId;

	@Column(name = "SEVAARTH_ID")
	private String sevaarthId;

//	public MstSubDepartmentEntity getMstSubDepartmentEntity() {
//		return mstSubDepartmentEntity;
//	}

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

	// @Column(name="APPOINTMENT_DATE")
	// private Date appointmentDate;

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

	public String getReasonForChangedtls() {
		return reasonForChangedtls;
	}

	public void setReasonForChangedtls(String reasonForChangedtls) {
		this.reasonForChangedtls = reasonForChangedtls;
	}

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

	public Integer getSuperAnnAge() {
		return superAnnAge;
	}

	public void setSuperAnnAge(Integer superAnnAge) {
		this.superAnnAge = superAnnAge;
	}

	

	public String getUidNo1() {
		return uidNo1;
	}

	public void setUidNo1(String uidNo1) {
		this.uidNo1 = uidNo1;
	}

	public String getUidNo2() {
		return uidNo2;
	}

	public void setUidNo2(String uidNo2) {
		this.uidNo2 = uidNo2;
	}

	public String getUidNo3() {
		return uidNo3;
	}

	public void setUidNo3(String uidNo3) {
		this.uidNo3 = uidNo3;
	}

	@Transient
	private String uidNo3;

	public Integer getDisplayNameonPranCard() {
		return displayNameonPranCard;
	}

	public void setDisplayNameonPranCard(Integer displayNameonPranCard) {
		this.displayNameonPranCard = displayNameonPranCard;
	}

	public Integer getDobProof() {
		return dobProof;
	}

	public void setDobProof(Integer dobProof) {
		this.dobProof = dobProof;
	}

	public Integer getEduQual() {
		return eduQual;
	}

	public void setEduQual(Integer eduQual) {
		this.eduQual = eduQual;
	}

	public Integer getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(Integer incomeRange) {
		this.incomeRange = incomeRange;
	}

	public String getEmployeeBankBankAddress() {
		return employeeBankBankAddress;
	}

	public void setEmployeeBankBankAddress(String employeeBankBankAddress) {
		this.employeeBankBankAddress = employeeBankBankAddress;
	}

	public String getEmployeeBankName() {
		return employeeBankName;
	}

	public void setEmployeeBankName(String employeeBankName) {
		this.employeeBankName = employeeBankName;
	}

	public String getEmployeeBankBranchName() {
		return employeeBankBranchName;
	}

	public void setEmployeeBankBranchName(String employeeBankBranchName) {
		this.employeeBankBranchName = employeeBankBranchName;
	}

	public String getEmpPhoneNo() {
		return empPhoneNo;
	}

	public void setEmpPhoneNo(String empPhoneNo) {
		this.empPhoneNo = empPhoneNo;
	}

	public String getEmpSTDCode() {
		return empSTDCode;
	}

	public void setEmpSTDCode(String empSTDCode) {
		this.empSTDCode = empSTDCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public BigInteger getPayScaleCode() {
		return payScaleCode;
	}

	public void setPayScaleCode(BigInteger payScaleCode) {
		this.payScaleCode = payScaleCode;
	}

	public void setParentAdminDepartmentCode(BigInteger parentAdminDepartmentCode) {
		this.parentAdminDepartmentCode = parentAdminDepartmentCode;
	}

	public void setParentFieldDepartmentCode(BigInteger parentFieldDepartmentCode) {
		this.parentFieldDepartmentCode = parentFieldDepartmentCode;
	}

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

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

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

	public String getInstemail() {
		return instemail;
	}

	public void setInstemail(String instemail) {
		this.instemail = instemail;
	}

	public Character getIsMappedWithNps() {
		return isMappedWithNps;
	}

	public void setIsMappedWithNps(Character isMappedWithNps) {
		this.isMappedWithNps = isMappedWithNps;
	}

	public String getReasonForRejOrApprv() {
		return reasonForRejOrApprv;
	}

	public void setReasonForRejOrApprv(String reasonForRejOrApprv) {
		this.reasonForRejOrApprv = reasonForRejOrApprv;
	}

	public String getFlatUnitNo() {
		return flatUnitNo;
	}

	public void setFlatUnitNo(String flatUnitNo) {
		this.flatUnitNo = flatUnitNo;
	}

	public Integer getAcDcpsMaintedBy() {
		return acDcpsMaintedBy;
	}

	public void setAcDcpsMaintedBy(Integer acDcpsMaintedBy) {
		this.acDcpsMaintedBy = acDcpsMaintedBy;
	}

	public Date getNewJoiningDate() {
		return newJoiningDate;
	}

	public void setNewJoiningDate(Date newJoiningDate) {
		this.newJoiningDate = newJoiningDate;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	@Column(name = "order_no")
	private Integer orderNo;

	public Date getRelievingDate() {
		return relievingDate;
	}

	public void setRelievingDate(Date relievingDate) {
		this.relievingDate = relievingDate;
	}

	public String getJoiningRelievingReason() {
		return joiningRelievingReason;
	}

	public void setJoiningRelievingReason(String joiningRelievingReason) {
		this.joiningRelievingReason = joiningRelievingReason;
	}

	@Column(name = "joining_relieving_reason")
	private String joiningRelievingReason;

	@Column(name = "city_class")
	private Character cityClass;

	@Column(name = "GID_ID")
	private BigInteger gisId;

	@Column(name = "pay_scal_desc")
	private String payScaleDesc;

	public String getPayScaleDesc() {
		return payScaleDesc;
	}

	public void setPayScaleDesc(String payScaleDesc) {
		this.payScaleDesc = payScaleDesc;
	}

	public BigInteger getGisId() {
		return gisId;
	}

	public void setGisId(BigInteger gisId) {
		this.gisId = gisId;
	}

	public Character getCityClass() {
		return cityClass;
	}

	public void setCityClass(Character cityClass) {
		this.cityClass = cityClass;
	}

	public Date getUpdatedBasicDate() {
		return updatedBasicDate;
	}

	public void setUpdatedBasicDate(Date updatedBasicDate) {
		this.updatedBasicDate = updatedBasicDate;
	}

	public BigInteger getUpdatedBasicUserId() {
		return updatedBasicUserId;
	}

	public void setUpdatedBasicUserId(BigInteger updatedBasicUserId) {
		this.updatedBasicUserId = updatedBasicUserId;
	}

	public Integer getUpdatedBasicPercent() {
		return updatedBasicPercent;
	}

	public void setUpdatedBasicPercent(Integer updatedBasicPercent) {
		this.updatedBasicPercent = updatedBasicPercent;
	}

	public Date getUpdatedBasicwitheffDate() {
		return updatedBasicwitheffDate;
	}

	public void setUpdatedBasicwitheffDate(Date updatedBasicwitheffDate) {
		this.updatedBasicwitheffDate = updatedBasicwitheffDate;
	}

	public Double getRevisedBasic() {
		return revisedBasic;
	}

	public void setRevisedBasic(Double revisedBasic) {
		this.revisedBasic = revisedBasic;
	}

	public Integer getSevenPcLevel() {
		return sevenPcLevel;
	}

	public void setSevenPcLevel(Integer sevenPcLevel) {
		this.sevenPcLevel = sevenPcLevel;
	}

	public String getDcpsgpfflag() {
		return dcpsgpfflag;
	}

	public void setDcpsgpfflag(String dcpsgpfflag) {
		this.dcpsgpfflag = dcpsgpfflag;
	}

	public Integer getPostdetailid() {
		return postdetailid;
	}

	public void setPostdetailid(Integer postdetailid) {
		this.postdetailid = postdetailid;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getSevaarthId() {
		return sevaarthId;
	}

	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Integer getSalutation() {
		return salutation;
	}

	public void setSalutation(Integer salutation) {
		this.salutation = salutation;
	}

	public String getEmployeeFullNameEn() {
		return employeeFullNameEn;
	}

	public void setEmployeeFullNameEn(String employeeFullNameEn) {
		this.employeeFullNameEn = employeeFullNameEn;
	}

	public String getEmployeeFNameEn() {
		return employeeFNameEn;
	}

	public void setEmployeeFNameEn(String employeeFNameEn) {
		this.employeeFNameEn = employeeFNameEn;
	}

	public String getEmployeeMNameEn() {
		return employeeMNameEn;
	}

	public void setEmployeeMNameEn(String employeeMNameEn) {
		this.employeeMNameEn = employeeMNameEn;
	}

	public String getEmployeeLNameEn() {
		return employeeLNameEn;
	}

	public void setEmployeeLNameEn(String employeeLNameEn) {
		this.employeeLNameEn = employeeLNameEn;
	}

	public String getEmployeeFullNameMr() {
		return employeeFullNameMr;
	}

	public void setEmployeeFullNameMr(String employeeFullNameMr) {
		this.employeeFullNameMr = employeeFullNameMr;
	}

	public String getEmployeeFNameMr() {
		return employeeFNameMr;
	}

	public void setEmployeeFNameMr(String employeeFNameMr) {
		this.employeeFNameMr = employeeFNameMr;
	}

	public String getEmployeeMNameMr() {
		return employeeMNameMr;
	}

	public void setEmployeeMNameMr(String employeeMNameMr) {
		this.employeeMNameMr = employeeMNameMr;
	}

	public String getEmployeeLNameMr() {
		return employeeLNameMr;
	}

	public void setEmployeeLNameMr(String employeeLNameMr) {
		this.employeeLNameMr = employeeLNameMr;
	}

	public String getEmployeeMotherName() {
		return employeeMotherName;
	}

	public void setEmployeeMotherName(String employeeMotherName) {
		this.employeeMotherName = employeeMotherName;
	}

	public Character getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(Character maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Character getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(Character bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Long getMobileNo1() {
		return mobileNo1;
	}

	public void setMobileNo1(Long mobileNo1) {
		this.mobileNo1 = mobileNo1;
	}

	public Long getMobileNo2() {
		return mobileNo2;
	}

	public void setMobileNo2(Long mobileNo2) {
		this.mobileNo2 = mobileNo2;
	}

	public String getLandlineNo() {
		return landlineNo;
	}

	public void setLandlineNo(String landlineNo) {
		this.landlineNo = landlineNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Integer getReligionCode() {
		return religionCode;
	}

	public void setReligionCode(Integer religionCode) {
		this.religionCode = religionCode;
	}

	public String getEidNo() {
		return eidNo;
	}

	public void setEidNo(String eidNo) {
		this.eidNo = eidNo;
	}

	public String getUidNo() {
		return uidNo;
	}

	public void setUidNo(String uidNo) {
		this.uidNo = uidNo;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public Integer getPinCode() {
		return pinCode;
	}

	public void setPinCode(Integer pinCode) {
		this.pinCode = pinCode;
	}

	public Integer getVillageCode() {
		return villageCode;
	}

	public void setVillageCode(Integer villageCode) {
		this.villageCode = villageCode;
	}

	public String getVillageName() {
		return villageName;
	}

	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}

	public Integer getTalukaCode() {
		return talukaCode;
	}

	public void setTalukaCode(Integer talukaCode) {
		this.talukaCode = talukaCode;
	}

	public Integer getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Integer countryCode) {
		this.countryCode = countryCode;
	}

	// public Date getAppointmentDate() {
	// return appointmentDate;
	// }
	//
	// public void setAppointmentDate(Date appointmentDate) {
	// this.appointmentDate = appointmentDate;
	// }

	public Date getDoj() {
		return doj;
	}

	public void setDoj(Date doj) {
		this.doj = doj;
	}

	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public BigInteger getCadreCode() {
		return cadreCode;
	}

	public void setCadreCode(BigInteger cadreCode) {
		this.cadreCode = cadreCode;
	}

	public Integer getEmpClass() {
		return empClass;
	}

	public void setEmpClass(Integer empClass) {
		this.empClass = empClass;
	}

//	public void setMstSubDepartmentEntity(MstSubDepartmentEntity mstSubDepartmentEntity) {
//		this.mstSubDepartmentEntity = mstSubDepartmentEntity;
//	}

	public void setMstGpfDetailsEntity(MstGpfDetailsEntity mstGpfDetailsEntity) {
		this.mstGpfDetailsEntity = mstGpfDetailsEntity;
	}

	public BigInteger getFirstDesignationCode() {
		return firstDesignationCode;
	}

	public void setFirstDesignationCode(BigInteger firstDesignationCode) {
		this.firstDesignationCode = firstDesignationCode;
	}

	public BigInteger getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(BigInteger designationCode) {
		this.designationCode = designationCode;
	}

	public BigInteger getParentAdminDepartmentCode() {
		return parentAdminDepartmentCode;
	}

	public void setParentAdminDepartmentId(BigInteger parentAdminDepartmentCode) {
		this.parentAdminDepartmentCode = parentAdminDepartmentCode;
	}

	public BigInteger getParentFieldDepartmentCode() {
		return parentFieldDepartmentCode;
	}

	public void setParentFieldDepartmentId(BigInteger parentFieldDepartmentCode) {
		this.parentFieldDepartmentCode = parentFieldDepartmentCode;
	}

	public BigInteger getAdminDepartmentCode() {
		return adminDepartmentCode;
	}

	public void setAdminDepartmentCode(BigInteger adminDepartmentCode) {
		this.adminDepartmentCode = adminDepartmentCode;
	}

	public BigInteger getFieldDepartmentCode() {
		return fieldDepartmentCode;
	}

	public void setFieldDepartmentCode(BigInteger fieldDepartmentCode) {
		this.fieldDepartmentCode = fieldDepartmentCode;
	}

	public BigInteger getCurrentOfficeCode() {
		return currentOfficeCode;
	}

	public void setCurrentOfficeCode(BigInteger currentOfficeCode) {
		this.currentOfficeCode = currentOfficeCode;
	}

	public Integer getPayCommissionCode() {
		return payCommissionCode;
	}

	public void setPayCommissionCode(Integer payCommissionCode) {
		this.payCommissionCode = payCommissionCode;
	}

	public BigInteger getPayInPayBand() {
		return payInPayBand;
	}

	public void setPayInPayBand(BigInteger payInPayBand) {
		this.payInPayBand = payInPayBand;
	}

	public Integer getGradePay() {
		return gradePay;
	}

	public void setGradePay(Integer gradePay) {
		this.gradePay = gradePay;
	}

	public Integer getBankCode() {
		return bankCode;
	}

	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

	public BigInteger getBankAcntNo() {
		return bankAcntNo;
	}

	public void setBankAcntNo(BigInteger bankAcntNo) {
		this.bankAcntNo = bankAcntNo;
	}

	public BigInteger getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(BigInteger bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getDdoCode() {
		return ddoCode;
	}

	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}

	public Date getApprovalByDdoDate() {
		return approvalByDdoDate;
	}

	public void setApprovalByDdoDate(Date approvalByDdoDate) {
		this.approvalByDdoDate = approvalByDdoDate;
	}

	public Integer getBillGroupId() {
		return billGroupId;
	}

	public void setBillGroupId(Integer billGroupId) {
		this.billGroupId = billGroupId;
	}

	public Double getBasicPay() {
		return basicPay;
	}

	public Integer getPercentageOfBasic() {
		return percentageOfBasic;
	}

	public void setPercentageOfBasic(Integer percentageOfBasic) {
		this.percentageOfBasic = percentageOfBasic;
	}

	public String getHeadActCode() {
		return headActCode;
	}

	public void setHeadActCode(String headActCode) {
		this.headActCode = headActCode;
	}

	public Character getEmployeeType() {
		return employeeType;
	}

	public void setEmployeeType(Character employeeType) {
		this.employeeType = employeeType;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public Date getEmpServiceEndDate() {
		return empServiceEndDate;
	}

	public void setEmpServiceEndDate(Date empServiceEndDate) {
		this.empServiceEndDate = empServiceEndDate;
	}

	public Date getSuperAnnDate() {
		return superAnnDate;
	}

	public void setSuperAnnDate(Date superAnnDate) {
		this.superAnnDate = superAnnDate;
	}

	public Date getWithEffectFromDate() {
		return withEffectFromDate;
	}

	public void setWithEffectFromDate(Date withEffectFromDate) {
		this.withEffectFromDate = withEffectFromDate;
	}

	public Date getGradeId() {
		return gradeId;
	}

	public void setGradeId(Date gradeId) {
		this.gradeId = gradeId;
	}

	public String getPhotoAttachmentId() {
		return photoAttachmentId;
	}

	public void setPhotoAttachmentId(String photoAttachmentId) {
		this.photoAttachmentId = photoAttachmentId;
	}

	public String getSignatureAttachmentId() {
		return signatureAttachmentId;
	}

	public void setSignatureAttachmentId(String signatureAttachmentId) {
		this.signatureAttachmentId = signatureAttachmentId;
	}

	public BigInteger getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(BigInteger createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public BigInteger getUpdatedUserId() {
		return updatedUserId;
	}

	public void setUpdatedUserId(BigInteger updatedUserId) {
		this.updatedUserId = updatedUserId;
	}

	public String getPayscalelevelId() {
		return payscalelevelId;
	}

	public void setPayscalelevelId(String payscalelevelId) {
		this.payscalelevelId = payscalelevelId;
	}

	// public BigInteger getSvnthpaybasic() {
	// return svnthpaybasic;
	// }
	//
	// public void setSvnthpaybasic(BigInteger svnthpaybasic) {
	// this.svnthpaybasic = svnthpaybasic;
	// }

	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}

	public Integer getSvnthpaybasic() {
		return svnthpaybasic;
	}

	public void setSvnthpaybasic(Integer svnthpaybasic) {
		this.svnthpaybasic = svnthpaybasic;
	}

	public String getPhysicallyHandicapped() {
		return physicallyHandicapped;
	}

	public void setPhysicallyHandicapped(String physicallyHandicapped) {
		this.physicallyHandicapped = physicallyHandicapped;
	}

	public Date getDtInitialAppointmentParentInst() {
		return dtInitialAppointmentParentInst;
	}

	public void setDtInitialAppointmentParentInst(Date dtInitialAppointmentParentInst) {
		this.dtInitialAppointmentParentInst = dtInitialAppointmentParentInst;
	}

	public Double getSevenPcBasic() {
		return sevenPcBasic;
	}

	public void setSevenPcBasic(Double sevenPcBasic) {
		this.sevenPcBasic = sevenPcBasic;
	}

	public List<MstNomineeDetailsEntity> getMstNomineeDetailsEntity() {
		return mstNomineeDetailsEntity;
	}

	public void setMstNomineeDetailsEntity(List<MstNomineeDetailsEntity> mstNomineeDetailsEntity) {
		this.mstNomineeDetailsEntity = mstNomineeDetailsEntity;
	}

	public String getEmployeeBirthPlace() {
		return employeeBirthPlace;
	}

	public void setEmployeeBirthPlace(String employeeBirthPlace) {
		this.employeeBirthPlace = employeeBirthPlace;
	}

	public String getPpanNo() {
		return ppanNo;
	}

	public void setPpanNo(String ppanNo) {
		this.ppanNo = ppanNo;
	}

	public String getEmployeeFatherHubandName() {
		return employeeFatherHubandName;
	}

	public void setEmployeeFatherHubandName(String employeeFatherHubandName) {
		this.employeeFatherHubandName = employeeFatherHubandName;
	}

	public String getEmployeeSpouseName() {
		return employeeSpouseName;
	}

	public void setEmployeeSpouseName(String employeeSpouseName) {
		this.employeeSpouseName = employeeSpouseName;
	}

	public String getEmployeeBankPinCode() {
		return employeeBankPinCode;
	}

	public void setEmployeeBankPinCode(String employeeBankPinCode) {
		this.employeeBankPinCode = employeeBankPinCode;
	}

	public String getBuildingName() {
		return buildingName;
	}

	public void setBuildingName(String buildingName) {
		this.buildingName = buildingName;
	}

	public String getEmpPermanentFlatUnitNo() {
		return empPermanentFlatUnitNo;
	}

	public void setEmpPermanentFlatUnitNo(String empPermanentFlatUnitNo) {
		this.empPermanentFlatUnitNo = empPermanentFlatUnitNo;
	}

	public String getEmpPermanentBuildingName() {
		return empPermanentBuildingName;
	}

	public void setEmpPermanentBuildingName(String empPermanentBuildingName) {
		this.empPermanentBuildingName = empPermanentBuildingName;
	}

	public String getEmpPermanentLocality() {
		return empPermanentLocality;
	}

	public void setEmpPermanentLocality(String empPermanentLocality) {
		this.empPermanentLocality = empPermanentLocality;
	}

	public String getEmpPermanentDistrict() {
		return empPermanentDistrict;
	}

	public void setEmpPermanentDistrict(String empPermanentDistrict) {
		this.empPermanentDistrict = empPermanentDistrict;
	}

	public String getEmpPermanentState() {
		return empPermanentState;
	}

	public void setEmpPermanentState(String empPermanentState) {
		this.empPermanentState = empPermanentState;
	}

	public String getEmpPermanentCountry() {
		return empPermanentCountry;
	}

	public void setEmpPermanentCountry(String empPermanentCountry) {
		this.empPermanentCountry = empPermanentCountry;
	}

	public String getEmpPermanentPinCode() {
		return empPermanentPinCode;
	}

	public void setEmpPermanentPinCode(String empPermanentPinCode) {
		this.empPermanentPinCode = empPermanentPinCode;
	}

	public String getEmpNominee1GuardName() {
		return empNominee1GuardName;
	}

	public void setEmpNominee1GuardName(String empNominee1GuardName) {
		this.empNominee1GuardName = empNominee1GuardName;
	}

	public String getEmpNominee1InvalidCondn() {
		return empNominee1InvalidCondn;
	}

	public void setEmpNominee1InvalidCondn(String empNominee1InvalidCondn) {
		this.empNominee1InvalidCondn = empNominee1InvalidCondn;
	}

	public String getEmpNominee2GuardName() {
		return empNominee2GuardName;
	}

	public void setEmpNominee2GuardName(String empNominee2GuardName) {
		this.empNominee2GuardName = empNominee2GuardName;
	}

	public String getEmpNominee2InvalidCondn() {
		return empNominee2InvalidCondn;
	}

	public void setEmpNominee2InvalidCondn(String empNominee2InvalidCondn) {
		this.empNominee2InvalidCondn = empNominee2InvalidCondn;
	}

	public String getEmpNominee3GuardName() {
		return empNominee3GuardName;
	}

	public void setEmpNominee3GuardName(String empNominee3GuardName) {
		this.empNominee3GuardName = empNominee3GuardName;
	}

	public String getEmpNominee3InvalidCondn() {
		return empNominee3InvalidCondn;
	}

	public void setEmpNominee3InvalidCondn(String empNominee3InvalidCondn) {
		this.empNominee3InvalidCondn = empNominee3InvalidCondn;
	}

	public String getNSDLStatus() {
		return NSDLStatus;
	}

	public void setNSDLStatus(String nSDLStatus) {
		NSDLStatus = nSDLStatus;
	}

	public String getUSPerson() {
		return USPerson;
	}

	public void setUSPerson(String uSPerson) {
		USPerson = uSPerson;
	}

	public String getCountryofTax() {
		return countryofTax;
	}

	public void setCountryofTax(String countryofTax) {
		this.countryofTax = countryofTax;
	}

	public String getAddressOfTax() {
		return addressOfTax;
	}

	public void setAddressOfTax(String addressOfTax) {
		this.addressOfTax = addressOfTax;
	}

	public String getCityOfTax() {
		return cityOfTax;
	}

	public void setCityOfTax(String cityOfTax) {
		this.cityOfTax = cityOfTax;
	}

	public String getStateofTax() {
		return stateofTax;
	}

	public void setStateofTax(String stateofTax) {
		this.stateofTax = stateofTax;
	}

	public String getPostCodeofTax() {
		return postCodeofTax;
	}

	public void setPostCodeofTax(String postCodeofTax) {
		this.postCodeofTax = postCodeofTax;
	}

	public String getTinOrPan() {
		return tinOrPan;
	}

	public void setTinOrPan(String tinOrPan) {
		this.tinOrPan = tinOrPan;
	}

	public MultipartFile getSignatureAttachmentIdnew() {
		return signatureAttachmentIdnew;
	}

	public void setSignatureAttachmentIdnew(MultipartFile signatureAttachmentIdnew) {
		this.signatureAttachmentIdnew = signatureAttachmentIdnew;
	}

	public MultipartFile getPhotoAttachmentIdnew() {
		return photoAttachmentIdnew;
	}

	public void setPhotoAttachmentIdnew(MultipartFile photoAttachmentIdnew) {
		this.photoAttachmentIdnew = photoAttachmentIdnew;
	}

	public BigInteger getSubCorporationId() {
		return subCorporationId;
	}

	public void setSubCorporationId(BigInteger subCorporationId) {
		this.subCorporationId = subCorporationId;
	}

	public Integer getBuckleNo() {
		return buckleNo;
	}

	public void setBuckleNo(Integer buckleNo) {
		this.buckleNo = buckleNo;
	}

	public Boolean getIsChangeParentDepartment() {
		return isChangeParentDepartment;
	}

	public void setIsChangeParentDepartment(Boolean isChangeParentDepartment) {
		this.isChangeParentDepartment = isChangeParentDepartment;
	}

	public String getReasonForChngParentFieldDept() {
		return reasonForChngParentFieldDept;
	}

	public void setReasonForChngParentFieldDept(String reasonForChngParentFieldDept) {
		this.reasonForChngParentFieldDept = reasonForChngParentFieldDept;
	}

	public String getPranNo() {
		return pranNo;
	}

	public void setPranNo(String pranNo) {
		this.pranNo = pranNo;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}

	public Integer getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(Integer subDeptId) {
		this.subDeptId = subDeptId;
	}

	public Character getIsApplicableforBeams() {
		return IsApplicableforBeams;
	}

	public void setIsApplicableforBeams(Character isApplicableforBeams) {
		IsApplicableforBeams = isApplicableforBeams;
	}

	public String getGisRemark() {
		return GisRemark;
	}

	public void setGisRemark(String gisRemark) {
		GisRemark = gisRemark;
	}

	public String getAccMaintainedByOther() {
		return accMaintainedByOther;
	}

	public void setAccMaintainedByOther(String accMaintainedByOther) {
		this.accMaintainedByOther = accMaintainedByOther;
	}

	public String getInstituteAdd() {
		return instituteAdd;
	}

	public void setInstituteAdd(String instituteAdd) {
		this.instituteAdd = instituteAdd;
	}

	public Date getDtJoinCurrentPost() {
		return dtJoinCurrentPost;
	}

	public void setDtJoinCurrentPost(Date dtJoinCurrentPost) {
		this.dtJoinCurrentPost = dtJoinCurrentPost;
	}

	public String getDepartmentNameEn() {
		return departmentNameEn;
	}

	public void setDepartmentNameEn(String departmentNameEn) {
		this.departmentNameEn = departmentNameEn;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getIndiApproveOrderNo() {
		return indiApproveOrderNo;
	}

	public void setIndiApproveOrderNo(String indiApproveOrderNo) {
		this.indiApproveOrderNo = indiApproveOrderNo;
	}

	public String getDcpsaccountmaintainby() {
		return dcpsaccountmaintainby;
	}

	public void setDcpsaccountmaintainby(String dcpsaccountmaintainby) {
		this.dcpsaccountmaintainby = dcpsaccountmaintainby;
	}

	public String getAccountmaintainby() {
		return accountmaintainby;
	}

	public void setAccountmaintainby(String accountmaintainby) {
		this.accountmaintainby = accountmaintainby;
	}

	public String getGisgroup() {
		return gisgroup;
	}

	public void setGisgroup(String gisgroup) {
		this.gisgroup = gisgroup;
	}

	public String getPfseries() {
		return pfseries;
	}

	public void setPfseries(String pfseries) {
		this.pfseries = pfseries;
	}

	public String getPfacno() {
		return pfacno;
	}

	public void setPfacno(String pfacno) {
		this.pfacno = pfacno;
	}

	public String getPfdescription() {
		return pfdescription;
	}

	public void setPfdescription(String pfdescription) {
		this.pfdescription = pfdescription;
	}

	public String getGisapplicable() {
		return gisapplicable;
	}

	public void setGisapplicable(String gisapplicable) {
		this.gisapplicable = gisapplicable;
	}

	public Date getMembership_date() {
		return membership_date;
	}

	public void setMembership_date(Date membership_date) {
		this.membership_date = membership_date;
	}

	public Integer getGiscatagory() {
		return giscatagory;
	}

	public void setGiscatagory(Integer giscatagory) {
		this.giscatagory = giscatagory;
	}

	public String getNameOfPostDesg() {
		return nameOfPostDesg;
	}

	public void setNameOfPostDesg(String nameOfPostDesg) {
		this.nameOfPostDesg = nameOfPostDesg;
	}

	public Double getHraBasic() {
		return hraBasic;
	}

	public void setHraBasic(Double hraBasic) {
		this.hraBasic = hraBasic;
	}

	public String getBegisCatg() {
		return begisCatg;
	}

	public void setBegisCatg(String begisCatg) {
		this.begisCatg = begisCatg;
	}
	
	
	

	public String getDcpsNo() {
		return dcpsNo;
	}

	public void setDcpsNo(String dcpsNo) {
		this.dcpsNo = dcpsNo;
	}
	
	public Integer getCrtId() {
		return crtId;
	}

	public void setCrtId(Integer crtId) {
		this.crtId = crtId;
	}
	
	

	public Integer getIsgpfflag() {
		return isgpfflag;
	}

	public void setIsgpfflag(Integer isgpfflag) {
		this.isgpfflag = isgpfflag;
	}
	
	

	public String getHeadOfAccCode() {
		return headOfAccCode;
	}

	public void setHeadOfAccCode(String headOfAccCode) {
		this.headOfAccCode = headOfAccCode;
	}



}
