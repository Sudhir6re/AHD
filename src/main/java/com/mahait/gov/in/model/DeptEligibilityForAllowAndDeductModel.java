package com.mahait.gov.in.model;

import java.io.Serializable;
import java.util.List;

public class DeptEligibilityForAllowAndDeductModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer departmentAllowdeducId;
	private Integer departmentAllowdeducCode;
	private String departmentAllowdeducName;
	private Integer schemeBillGrpId;
	private Integer isType;
	private boolean isChecked;	
	private boolean checkBox;	
	
	private Integer employeeId;
	private String sevaarthId;
	
	private List<DeptEligibilityForAllowAndDeductModel> lstDeptEligibilityForAllowAndDeductModel;
	
	
	public Integer getDepartmentAllowdeducId() {
		return departmentAllowdeducId;
	}
	public void setDepartmentAllowdeducId(Integer departmentAllowdeducId) {
		this.departmentAllowdeducId = departmentAllowdeducId;
	}
	
	public Integer getDepartmentAllowdeducCode() {
		return departmentAllowdeducCode;
	}
	public void setDepartmentAllowdeducCode(Integer departmentAllowdeducCode) {
		this.departmentAllowdeducCode = departmentAllowdeducCode;
	}
	public String getDepartmentAllowdeducName() {
		return departmentAllowdeducName;
	}
	public void setDepartmentAllowdeducName(String departmentAllowdeducName) {
		this.departmentAllowdeducName = departmentAllowdeducName;
	}
	public Integer getIsType() {
		return isType;
	}
	public void setIsType(Integer isType) {
		this.isType = isType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public List<DeptEligibilityForAllowAndDeductModel> getLstDeptEligibilityForAllowAndDeductModel() {
		return lstDeptEligibilityForAllowAndDeductModel;
	}
	public void setLstDeptEligibilityForAllowAndDeductModel(
			List<DeptEligibilityForAllowAndDeductModel> lstDeptEligibilityForAllowAndDeductModel) {
		this.lstDeptEligibilityForAllowAndDeductModel = lstDeptEligibilityForAllowAndDeductModel;
	}
	public boolean isChecked() {
		return isChecked;
	}
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}
	public boolean isCheckBox() {
		return checkBox;
	}
	public void setCheckBox(boolean checkBox) {
		this.checkBox = checkBox;
	}
	public Integer getSchemeBillGrpId() {
		return schemeBillGrpId;
	}
	public void setSchemeBillGrpId(Integer schemeBillGrpId) {
		this.schemeBillGrpId = schemeBillGrpId;
	}
	
	
}
