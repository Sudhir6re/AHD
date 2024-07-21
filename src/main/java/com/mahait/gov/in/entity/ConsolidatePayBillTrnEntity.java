package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CONSOLIDATE_PAYBILL_TRN",schema="public")
public class ConsolidatePayBillTrnEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CONSOLIDATE_PAYBILL_TRN_ID")
    private Integer consolidatePaybillTrnId;
	
	
	@Column(name ="IS_ACTIVE")
	private Integer isActive;
	

	@Column(name ="status")
	private Integer status;
	
	
	@Column(name = "PAYBILL_MONTH")
	private Integer paybillMonth;
	
	@Column(name = "PAYBILL_YEAR")
	private Integer paybillYear;
	
	@Column(name="SCHEME_CODE")
	private String schemeCode;
	
	@Column(name = "DDO_Code")
	private String ddoCode;
	
	@Column(name = "GROSS_AMT")
	private Double GrossAmt;
	
	@Column(name = "NET_AMT")
	private Double netAmt;
	
	@Column(name = "AUTH_NO")
	private Integer authNo; 
	
	@Column(name = "CMP_FILE_STATUS")
	private Integer CMPFileStatus;
	
	@Column(name = "CMP_DATE")
	private Date CMPDate;
	
	@Column(name = "BILL_TYPE")
	private Integer billType;
	
	@Column(name = "CMP_DOWNLOAD_STATUS")
	private Integer cmpdownloadStatus;
	
	@Column(name = "CREATED_USER_ID")
	private Integer createdUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;
	
	@Column(name = "PF")
	private Double pf;
	
	@Column(name = "PT")
	private Double pt;
	
	@Column(name = "INCOME_TAX")
	private Double it;
	
	@Column(name = "HRR")
	private Double hrr;
	
	@Column(name = "DCPS_ARR")
	private Double dcpsArr;
	
	@Column(name = "GIS")
	private Double gis;

	@Column(name = "GROUP_ACC_POLICY")
	private Double accPolicy;
	
	@Column(name = "REVENUE_STAMP")
	private Double revenueStamp;
	
	@Column(name = "TOTAL_DEDUCTION")  
	private Double totalDeduct;

	public Double getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(Double totalDeduct) {
		this.totalDeduct = totalDeduct;
	}
	@Column(name = "DCPS")
	private Double dcps;

	public Double getDcps() {
		return dcps;
	}

	public void setDcps(Double dcps) {
		this.dcps = dcps;
	}

	public Integer getConsolidatePaybillTrnId() {
		return consolidatePaybillTrnId;
	}

	public void setConsolidatePaybillTrnId(Integer consolidatePaybillTrnId) {
		this.consolidatePaybillTrnId = consolidatePaybillTrnId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPaybillMonth() {
		return paybillMonth;
	}

	public void setPaybillMonth(Integer paybillMonth) {
		this.paybillMonth = paybillMonth;
	}

	public Integer getPaybillYear() {
		return paybillYear;
	}

	public void setPaybillYear(Integer paybillYear) {
		this.paybillYear = paybillYear;
	}

	public String getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}

	public String getDdoCode() {
		return ddoCode;
	}

	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}

	public Double getGrossAmt() {
		return GrossAmt;
	}

	public void setGrossAmt(Double grossAmt) {
		GrossAmt = grossAmt;
	}

	public Double getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(Double netAmt) {
		this.netAmt = netAmt;
	}

	public Integer getAuthNo() {
		return authNo;
	}

	public void setAuthNo(Integer authNo) {
		this.authNo = authNo;
	}

	public Integer getCMPFileStatus() {
		return CMPFileStatus;
	}

	public void setCMPFileStatus(Integer cMPFileStatus) {
		CMPFileStatus = cMPFileStatus;
	}

	public Date getCMPDate() {
		return CMPDate;
	}

	public void setCMPDate(Date cMPDate) {
		CMPDate = cMPDate;
	}

	public Integer getBillType() {
		return billType;
	}

	public void setBillType(Integer billType) {
		this.billType = billType;
	}

	public Integer getCmpdownloadStatus() {
		return cmpdownloadStatus;
	}

	public void setCmpdownloadStatus(Integer cmpdownloadStatus) {
		this.cmpdownloadStatus = cmpdownloadStatus;
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

	public Double getPf() {
		return pf;
	}

	public void setPf(Double pf) {
		this.pf = pf;
	}

	public Double getPt() {
		return pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Double getIt() {
		return it;
	}

	public void setIt(Double it) {
		this.it = it;
	}

	public Double getHrr() {
		return hrr;
	}

	public void setHrr(Double hrr) {
		this.hrr = hrr;
	}

	public Double getDcpsArr() {
		return dcpsArr;
	}

	public void setDcpsArr(Double dcpsArr) {
		this.dcpsArr = dcpsArr;
	}

	public Double getGis() {
		return gis;
	}

	public void setGis(Double gis) {
		this.gis = gis;
	}

	public Double getAccPolicy() {
		return accPolicy;
	}

	public void setAccPolicy(Double accPolicy) {
		this.accPolicy = accPolicy;
	}

	public Double getRevenueStamp() {
		return revenueStamp;
	}

	public void setRevenueStamp(Double revenueStamp) {
		this.revenueStamp = revenueStamp;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	
	
	
	
	

}
