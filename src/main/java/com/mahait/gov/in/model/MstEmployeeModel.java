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
	private Long appointmentId;
	private String employeeMotherName;
	private Character maritalStatus;
	private Double sevenPcBasic;
	private Integer yearId;
	
	
	
	

	private BigInteger empMapped;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date dob;
	private Date nomineeDOB;

	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
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
	private Integer billgroupId;
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
	
	
	
	
    public List<MstNomineeDetailsModel> getLstMstNomineeDetails() {
		return lstMstNomineeDetails;
	}
	public void setLstMstNomineeDetails(List<MstNomineeDetailsModel> lstMstNomineeDetails) {
		this.lstMstNomineeDetails = lstMstNomineeDetails;
	}
	public Date getDtJoinCurrentPost() {
		return dtJoinCurrentPost;
	}
	public void setDtJoinCurrentPost(Date dtJoinCurrentPost) {
		this.dtJoinCurrentPost = dtJoinCurrentPost;
	}
	public String getInstituteAdd() {
		return instituteAdd;
	}
	public void setInstituteAdd(String instituteAdd) {
		this.instituteAdd = instituteAdd;
	}
	public String getSubdeptNm() {
		return subdeptNm;
	}
	public void setSubdeptNm(String subdeptNm) {
		this.subdeptNm = subdeptNm;
	}
	private String otherRlstnName;
		
		public String getGisRemark() {
		return gisRemark;
	}
	public void setGisRemark(String gisRemark) {
		this.gisRemark = gisRemark;
	}
		public String getBillDesc() {
		return billDesc;
	}
	public void setBillDesc(String billDesc) {
		this.billDesc = billDesc;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public String getCadreName() {
		return cadreName;
	}
	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}
	public String getPostName() {
		return postName;
	}
	public void setPostName(String postName) {
		this.postName = postName;
	}
	private Integer sevenPCLevel;
	
	public Integer getSevenPCLevel() {
		return sevenPCLevel;
	}
	public void setSevenPCLevel(Integer sevenPCLevel) {
		this.sevenPCLevel = sevenPCLevel;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getPayscaleDesc() {
		return payscaleDesc;
	}
	public void setPayscaleDesc(String payscaleDesc) {
		this.payscaleDesc = payscaleDesc;
	}
	public BigInteger getPayScaleCode() {
		return PayScaleCode;
	}
	public void setPayScaleCode(BigInteger payScaleCode) {
		PayScaleCode = payScaleCode;
	}
	public Integer getGisApplId() {
		return gisApplId;
	}
	public void setGisApplId(Integer gisApplId) {
		this.gisApplId = gisApplId;
	}
	public Date getNomineeDOB() {
		return nomineeDOB;
	}
	public void setNomineeDOB(Date nomineeDOB) {
		this.nomineeDOB = nomineeDOB;
	}
	public Integer getAcDcpsMaintainedBy() {
		return acDcpsMaintainedBy;
	}
	public void setAcDcpsMaintainedBy(Integer acDcpsMaintainedBy) {
		this.acDcpsMaintainedBy = acDcpsMaintainedBy;
	}
	public Character getCityClass() {
		return cityClass;
	}
	public void setCityClass(Character cityClass) {
		this.cityClass = cityClass;
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
	public String getDeptNm() {
		return deptNm;
	}
	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEmployeeFullName() {
		return employeeFullName;
	}
	public void setEmployeeFullName(String employeeFullName) {
		this.employeeFullName = employeeFullName;
	}
	public String getDesignationName() {
		return designationName;
	}
	public void setDesignationName(String designationName) {
		this.designationName = designationName;
	}
	public String getDepartmentNameEn() {
		return departmentNameEn;
	}
	public void setDepartmentNameEn(String departmentNameEn) {
		this.departmentNameEn = departmentNameEn;
	}
	//Setter And Getter
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
	public Date getAppointmentDate() {
		return appointmentDate;
	}
	public void setAppointmentDate(Date date) {
		this.appointmentDate = date;
	}
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
	public BigInteger getCadreId() {
		return cadreId;
	}
	public void setCadreId(BigInteger cadreId) {
		this.cadreId = cadreId;
	}
	
	
	public Integer getEmpClass() {
		return empClass;
	}
	public void setEmpClass(Integer empClass) {
		this.empClass = empClass;
	}
	public BigInteger getFirstDesignationId() {
		return firstDesignationId;
	}
	public void setFirstDesignationId(BigInteger firstDesignationId) {
		this.firstDesignationId = firstDesignationId;
	}
	public BigInteger getDesignationId() {
		return designationId;
	}
	public void setDesignationId(BigInteger designationId) {
		this.designationId = designationId;
	}
	public BigInteger getParentAdminDepartmentId() {
		return parentAdminDepartmentId;
	}
	public void setParentAdminDepartmentId(BigInteger parentAdminDepartmentId) {
		this.parentAdminDepartmentId = parentAdminDepartmentId;
	}
	public BigInteger getParentFieldDepartmentId() {
		return parentFieldDepartmentId;
	}
	public void setParentFieldDepartmentId(BigInteger parentFieldDepartmentId) {
		this.parentFieldDepartmentId = parentFieldDepartmentId;
	}
	public BigInteger getAdminDepartmentId() {
		return adminDepartmentId;
	}
	public void setAdminDepartmentId(BigInteger adminDepartmentId) {
		this.adminDepartmentId = adminDepartmentId;
	}
	public BigInteger getFieldDepartmentId() {
		return fieldDepartmentId;
	}
	public void setFieldDepartmentId(BigInteger fieldDepartmentId) {
		this.fieldDepartmentId = fieldDepartmentId;
	}
	public BigInteger getCurrentOfficeId() {
		return currentOfficeId;
	}
	public void setCurrentOfficeId(BigInteger currentOfficeId) {
		this.currentOfficeId = currentOfficeId;
	}
	public Integer getPayCommissionCode() {
		return payCommissionCode;
	}
	public void setPayCommissionCode(Integer payCommissionCode) {
		this.payCommissionCode = payCommissionCode;
	}
	public BigInteger getPayScaleId() {
		return payScaleId;
	}
	public void setPayScaleId(BigInteger payScaleId) {
		this.payScaleId = payScaleId;
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
	public Integer getBankId() {
		return bankId;
	}
	public void setBankId(Integer bankId) {
		this.bankId = bankId;
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
	public BigInteger getBankBranchId() {
		return bankBranchId;
	}
	public void setBankBranchId(BigInteger bankBranchId) {
		this.bankBranchId = bankBranchId;
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
	
	public Integer getBillgroupId() {
		return billgroupId;
	}
	public void setBillgroupId(Integer billgroupId) {
		this.billgroupId = billgroupId;
	}
	
	public Double getBasicPay() {
		return basicPay;
	}
	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
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
	public String getDcpsaccountmaintainby() {
		return dcpsaccountmaintainby;
	}
	public void setDcpsaccountmaintainby(String dcpsaccountmaintainby) {
		this.dcpsaccountmaintainby = dcpsaccountmaintainby;
	}
	public String getPfacno() {
		return pfacno;
	}
	public void setPfacno(String pfacno) {
		this.pfacno = pfacno;
	}
	public String getAccountmaintainby() {
		return accountmaintainby;
	}
	public void setAccountmaintainby(String accountmaintainby) {
		this.accountmaintainby = accountmaintainby;
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
	public String getGisgroup() {
		return gisgroup;
	}
	public void setGisgroup(String gisgroup) {
		this.gisgroup = gisgroup;
	}
	public Date getMembership_date() {
		return membership_date;
	}
	public void setMembership_date(Date membership_date) {
		this.membership_date = membership_date;
	}
	public String getNomineename() {
		return nomineename;
	}
	public void setNomineename(String nomineename) {
		this.nomineename = nomineename;
	}
	public String getNomineeaddress() {
		return nomineeaddress;
	}
	public void setNomineeaddress(String nomineeaddress) {
		this.nomineeaddress = nomineeaddress;
	}
	public Integer getPercent_share() {
		return percent_share;
	}
	public void setPercent_share(Integer percent_share) {
		this.percent_share = percent_share;
	}
	public String getRelation() {
		return relation;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public Date getRdob() {
		return rdob;
	}
	public void setRdob(Date rdob) {
		this.rdob = rdob;
	}
	public String getPfseries() {
		return pfseries;
	}
	public void setPfseries(String pfseries) {
		this.pfseries = pfseries;
	}
	public Integer getDcpsid() {
		return dcpsid;
	}
	public void setDcpsid(Integer dcpsid) {
		this.dcpsid = dcpsid;
	}
	public Integer getGisid() {
		return gisid;
	}
	public void setGisid(Integer gisid) {
		this.gisid = gisid;
	}
	public Integer getGpf_id() {
		return gpf_id;
	}
	public void setGpf_id(Integer gpf_id) {
		this.gpf_id = gpf_id;
	}
	public Integer getNomineeid() {
		return nomineeid;
	}
	public void setNomineeid(Integer nomineeid) {
		this.nomineeid = nomineeid;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImagePathSign() {
		return imagePathSign;
	}
	public void setImagePathSign(String imagePathSign) {
		this.imagePathSign = imagePathSign;
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
	
	public String getSuperannuationage() {
		return superannuationage;
	}
	public void setSuperannuationage(String superannuationage) {
		this.superannuationage = superannuationage;
	}
	public String getInstName() {
		return instName;
	}
	public void setInstName(String instName) {
		this.instName = instName;
	}
	public String getInsttelnoone() {
		return insttelnoone;
	}
	public void setInsttelnoone(String insttelnoone) {
		this.insttelnoone = insttelnoone;
	}
	public String getInsttelnotwo() {
		return insttelnotwo;
	}
	public void setInsttelnotwo(String insttelnotwo) {
		this.insttelnotwo = insttelnotwo;
	}
	public String getInstemail() {
		return instemail;
	}
	public void setInstemail(String instemail) {
		this.instemail = instemail;
	}
	
	public String getStrArrNomineeName() {
		return strArrNomineeName;
	}
	public void setStrArrNomineeName(String strArrNomineeName) {
		this.strArrNomineeName = strArrNomineeName;
	}
	public String getStrArrAddress() {
		return strArrAddress;
	}
	public void setStrArrAddress(String strArrAddress) {
		this.strArrAddress = strArrAddress;
	}
	public String getStrArrDob() {
		return strArrDob;
	}
	public void setStrArrDob(String strArrDob) {
		this.strArrDob = strArrDob;
	}
	public String getStrArrPercentShare() {
		return strArrPercentShare;
	}
	public void setStrArrPercentShare(String strArrPercentShare) {
		this.strArrPercentShare = strArrPercentShare;
	}
	public String getStrArrRelationship() {
		return strArrRelationship;
	}
	public void setStrArrRelationship(String strArrRelationship) {
		this.strArrRelationship = strArrRelationship;
	}
	
	public String getDcpsno() {
		return dcpsno;
	}
	public void setDcpsno(String dcpsno) {
		this.dcpsno = dcpsno;
	}
	public String getPayCommissionName() {
		return payCommissionName;
	}
	public void setPayCommissionName(String payCommissionName) {
		this.payCommissionName = payCommissionName;
	}
	
	public Double getSevenPcBasic() {
		return sevenPcBasic;
	}
	public void setSevenPcBasic(Double sevenPcBasic) {
		this.sevenPcBasic = sevenPcBasic;
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
	public String getRltnOther() {
		return rltnOther;
	}
	public void setRltnOther(String rltnOther) {
		this.rltnOther = rltnOther;
	}
	public String getAccMaintainedByOther() {
		return accMaintainedByOther;
	}
	public void setAccMaintainedByOther(String accMaintainedByOther) {
		this.accMaintainedByOther = accMaintainedByOther;
	}
	public String getIndiApproveOrderNo() {
		return indiApproveOrderNo;
	}
	public void setIndiApproveOrderNo(String indiApproveOrderNo) {
		this.indiApproveOrderNo = indiApproveOrderNo;
	}
	public String getOtherRlstnName() {
		return otherRlstnName;
	}
	public void setOtherRlstnName(String otherRlstnName) {
		this.otherRlstnName = otherRlstnName;
	}
	public Integer getGiscatagory() {
		return giscatagory;
	}
	public void setGiscatagory(Integer giscatagory) {
		this.giscatagory = giscatagory;
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
	public BigInteger getEmpMapped() {
		return empMapped;
	}
	public void setEmpMapped(BigInteger empMapped) {
		this.empMapped = empMapped;
	}
	public Integer getYearId() {
		return yearId;
	}
	public void setYearId(Integer yearId) {
		this.yearId = yearId;
	}   
	public String getIsAllowOrDeduct() {
		return isAllowOrDeduct;
	}
	public void setIsAllowOrDeduct(String isAllowOrDeduct) {
		this.isAllowOrDeduct = isAllowOrDeduct;
	}
	public Double getTotalGrossAmt() {
		return totalGrossAmt;
	}
	public void setTotalGrossAmt(Double totalGrossAmt) {
		this.totalGrossAmt = totalGrossAmt;
	}
	public Double getTotalNetAmt() {
		return totalNetAmt;
	}
	public void setTotalNetAmt(Double totalNetAmt) {
		this.totalNetAmt = totalNetAmt;
	}
	public Double getTotalDedAmt() {
		return totalDedAmt;
	}
	public void setTotalDedAmt(Double totalDedAmt) {
		this.totalDedAmt = totalDedAmt;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public List<MstEmployeeModel> getMstEmployeeModelList() {
		return mstEmployeeModelList;
	}
	public void setMstEmployeeModelList(List<MstEmployeeModel> mstEmployeeModelList) {
		this.mstEmployeeModelList = mstEmployeeModelList;
	}
	
	
}
