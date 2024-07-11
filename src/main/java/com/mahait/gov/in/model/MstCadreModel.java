package com.mahait.gov.in.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class MstCadreModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/*private String fieldDepartmrnt;*/
	private String cadreGroup;
	private Integer cadreCode;
	private String cadreDescription;
	private String whetherMinisterial;
	private BigDecimal superAnnuationAge;
	private Integer cadreId;
	private int isActive;
	
	/*private String cadreControlledByOwnDepartment;*/
	
	public String getCadreGroup() {
		return cadreGroup;
	}
	/*public String getFieldDepartmrnt() {
		return fieldDepartmrnt;
	}
	public void setFieldDepartmrnt(String fieldDepartmrnt) {
		this.fieldDepartmrnt = fieldDepartmrnt;
	}*/
	public void setCadreGroup(String cadreGroup) {
		this.cadreGroup = cadreGroup;
	}
	public Integer getCadreCode() {
		return cadreCode;
	}
	public void setCadreCode(Integer cadreCode) {
		this.cadreCode = cadreCode;
	}
	public String getCadreDescription() {
		return cadreDescription;
	}
	public void setCadreDescription(String cadreDescription) {
		this.cadreDescription = cadreDescription;
	}
	public String getWhetherMinisterial() {
		return whetherMinisterial;
	}
	public void setWhetherMinisterial(String whetherMinisterial) {
		this.whetherMinisterial = whetherMinisterial;
	}
	
	public BigDecimal getSuperAnnuationAge() {
		return superAnnuationAge;
	}
	public void setSuperAnnuationAge(BigDecimal superAnnuationAge) {
		this.superAnnuationAge = superAnnuationAge;
	}
	public Integer getCadreId() {
		return cadreId;
	}
	public void setCadreId(Integer cadreId) {
		this.cadreId = cadreId;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	
}
