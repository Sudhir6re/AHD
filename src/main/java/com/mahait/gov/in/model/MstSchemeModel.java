package com.mahait.gov.in.model;

import java.io.Serializable;
import java.math.BigInteger;

public class MstSchemeModel implements Serializable{
	
	
private static final long serialVersionUID = 1L;
	
    private BigInteger schemeId;	
    private String majorHead;
	private String subMajorHead;
	private String minorHead;
	private String subMinorHead;
	private String subHead;
	private String demandCode;
	private String schemeCode;
	private String schemeName;
	public String getDcpsDdoSchemeCode() {
		return dcpsDdoSchemeCode;
	}
	public void setDcpsDdoSchemeCode(String dcpsDdoSchemeCode) {
		this.dcpsDdoSchemeCode = dcpsDdoSchemeCode;
	}
	private String schemeType;
	private char plan;
	private char isactive;
	private BigInteger finYear;
	private String financialYear;
	private String dcpsDdoCode;
	private BigInteger dcpsDdoSchemesId;
	private String dcpsDdoSchemeCode;
	
	

	public String getDcpsDdoCode() {
		return dcpsDdoCode;
	}
	public void setDcpsDdoCode(String dcpsDdoCode) {
		this.dcpsDdoCode = dcpsDdoCode;
	}
	
	public BigInteger getFinYear() {
		return finYear;
	}
	public void setFinYear(BigInteger finYear) {
		this.finYear = finYear;
	}
	public BigInteger getDcpsDdoSchemesId() {
		return dcpsDdoSchemesId;
	}
	public void setDcpsDdoSchemesId(BigInteger dcpsDdoSchemesId) {
		this.dcpsDdoSchemesId = dcpsDdoSchemesId;
	}
	public char getIsactive() {
		return isactive;
	}
	public void setIsactive(char isactive) {
		this.isactive = isactive;
	}
	public String getSchemeType() {
		return schemeType;
	}
	public void setSchemeType(String schemeType) {
		this.schemeType = schemeType;
	}
	public char getPlan() {
		return plan;
	}
	public void setPlan(char plan) {
		this.plan = plan;
	}
	public String getCharged() {
		return charged;
	}
	public void setCharged(String charged) {
		this.charged = charged;
	}
	private String charged;
	
	
	public String getMajorHead() {
		return majorHead;
	}
	public void setMajorHead(String majorHead) {
		this.majorHead = majorHead;
	}
	public String getSubMajorHead() {
		return subMajorHead;
	}
	public void setSubMajorHead(String subMajorHead) {
		this.subMajorHead = subMajorHead;
	}
	public String getMinorHead() {
		return minorHead;
	}
	public void setMinorHead(String minorHead) {
		this.minorHead = minorHead;
	}
	public String getSubMinorHead() {
		return subMinorHead;
	}
	public void setSubMinorHead(String subMinorHead) {
		this.subMinorHead = subMinorHead;
	}
	public String getSubHead() {
		return subHead;
	}
	public void setSubHead(String subHead) {
		this.subHead = subHead;
	}
	public String getDemandCode() {
		return demandCode;
	}
	public void setDemandCode(String demandCode) {
		this.demandCode = demandCode;
	}
	public String getSchemeCode() {
		return schemeCode;
	}
	public void setSchemeCode(String schemeCode) {
		this.schemeCode = schemeCode;
	}
	public String getSchemeName() {
		return schemeName;
	}
	public void setSchemeName(String schemeName) {
		this.schemeName = schemeName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public BigInteger getSchemeId() {
		return schemeId;
	}
	public void setSchemeId(BigInteger schemeId) {
		this.schemeId = schemeId;
	}
	public String getFinancialYear() {
		return financialYear;
	}
	public void setFinancialYear(String financialYear) {
		this.financialYear = financialYear;
	}
	
	
}
