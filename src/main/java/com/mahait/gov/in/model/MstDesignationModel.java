package com.mahait.gov.in.model;

import java.io.Serializable;

public class MstDesignationModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*private Integer fieldDepartment;*/
	private Integer designationCode;
	private String designation;
	private String designationShortName;
	private Integer designationId;
	private Integer cadreCode;
	private String cadreName;
	private int cadreGroup;
	
	public String getCadreName() {
		return cadreName;
	}
	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}
	public Integer getCadreCode() {
		return cadreCode;
	}
	public void setCadreCode(Integer cadreCode) {
		this.cadreCode = cadreCode;
	}
	private int isActive;
	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public Integer getDesignationId() {
		return designationId;
	}
	public void setDesignationId(Integer designationId) {
		this.designationId = designationId;
	}
	
	/*public Integer getFieldDepartment() {
		return fieldDepartment;
	}
	public void setFieldDepartment(Integer fieldDepartment) {
		this.fieldDepartment = fieldDepartment;
	}*/
	public Integer getDesignationCode() {
		return designationCode;
	}
	public void setDesignationCode(Integer designationCode) {
		this.designationCode = designationCode;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDesignationShortName() {
		return designationShortName;
	}
	public void setDesignationShortName(String designationShortName) {
		this.designationShortName = designationShortName;
	}
	public int getCadreGroup() {
		return cadreGroup;
	}
	public void setCadreGroup(int cadreGroup) {
		this.cadreGroup = cadreGroup;
	}
	
	
	
	/*public Integer getCadre() {
		return cadre;
	}
	public void setCadre(Integer cadre) {
		this.cadre = cadre;
	}*/
	/*public Integer getPayCommission() {
		return payCommission;
	}
	public void setPayCommission(Integer payCommission) {
		this.payCommission = payCommission;
	}*/
	
}
