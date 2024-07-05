package com.mahait.gov.in.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;




public class MstGrOrderModel implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long grOrderId;
	private Integer subDepartmentId;
	private Integer ddoId;
	private String sanctionOrderNo;
	private String ddoCode;
	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderDate;
	
	
	private String subDepartmentNameEn;
	private Integer districtCode;
	private String districtConcatCode;
	private Integer talukaCode;
	private String talukaConcatCode;
	private Integer departmentId;
	
	
	private String departmentNameEn;
	private char isActive;
	private Integer orderType;
	private Integer createdUserId;
	private Date createdDate;
	private Integer updatedUserId;
	private Date updatedDate;
	
	
	
	private String isApplicableLocation;
	private String isApplicableForEndDt;
	
	private MultipartFile[] documentPath;
	
	
	private String docPath;
	
	

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endDate;
	
	
	
	private String grDescription;
	
	public Integer getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}
	public Integer getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(Integer districtCode) {
		this.districtCode = districtCode;
	}
	
	
	public String getDistrictConcatCode() {
		return districtConcatCode;
	}
	public void setDistrictConcatCode(String districtConcatCode) {
		this.districtConcatCode = districtConcatCode;
	}
	public Integer getTalukaCode() {
		return talukaCode;
	}
	public void setTalukaCode(Integer talukaCode) {
		this.talukaCode = talukaCode;
	}
	public String getTalukaConcatCode() {
		return talukaConcatCode;
	}
	public void setTalukaConcatCode(String talukaConcatCode) {
		this.talukaConcatCode = talukaConcatCode;
	}
	public String getSubDepartmentNameEn() {
		return subDepartmentNameEn;
	}
	public void setSubDepartmentNameEn(String subDepartmentNameEn) {
		this.subDepartmentNameEn = subDepartmentNameEn;
	}
	public String getDepartmentNameEn() {
		return departmentNameEn;
	}
	public void setDepartmentNameEn(String departmentNameEn) {
		this.departmentNameEn = departmentNameEn;
	}

	
	
	public Long getGrOrderId() {
		return grOrderId;
	}
	public void setGrOrderId(Long grOrderId) {
		this.grOrderId = grOrderId;
	}
	public Integer getSubDepartmentId() {
		return subDepartmentId;
	}
	public void setSubDepartmentId(Integer subDepartmentId) {
		this.subDepartmentId = subDepartmentId;
	}
	public Integer getDdoId() {
		return ddoId;
	}
	public void setDdoId(Integer ddoId) {
		this.ddoId = ddoId;
	}
	public String getSanctionOrderNo() {
		return sanctionOrderNo;
	}
	public void setSanctionOrderNo(String sanctionOrderNo) {
		this.sanctionOrderNo = sanctionOrderNo;
	}
	public String getDdoCode() {
		return ddoCode;
	}
	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public Integer getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(Integer updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public MultipartFile[] getDocumentPath() {
		return documentPath;
	}
	public void setDocumentPath(MultipartFile[] documentPath) {
		this.documentPath = documentPath;
	}
	
	public String getIsApplicableLocation() {
		return isApplicableLocation;
	}
	public void setIsApplicableLocation(String isApplicableLocation) {
		this.isApplicableLocation = isApplicableLocation;
	}
	public String getIsApplicableForEndDt() {
		return isApplicableForEndDt;
	}
	public void setIsApplicableForEndDt(String isApplicableForEndDt) {
		this.isApplicableForEndDt = isApplicableForEndDt;
	}
	public String getGrDescription() {
		return grDescription;
	}
	public void setGrDescription(String grDescription) {
		this.grDescription = grDescription;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDocPath() {
		return docPath;
	}
	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
	
	
	
	
	
	
	

}
