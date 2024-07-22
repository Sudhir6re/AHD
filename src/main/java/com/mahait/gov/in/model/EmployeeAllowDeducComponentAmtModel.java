package com.mahait.gov.in.model;

import java.util.Arrays;
import java.util.Date;

public class EmployeeAllowDeducComponentAmtModel {

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private int empAwnDedCompoAmtId;  
	private int employeeId;  
	private double[] existingAmt;  
	private double[] netAmt;  
	private int departmentAllowDeducId;  
	private int departmentAllowdeducCode;
	private String[] sevaarthId;  
	private int isType;  
	private int ddoCode;  
	private char isActive;  
	private int createdUserId;  
	private Date createdDate;
	private int updatedUserId;
	private Date updatedDate;
	private String[] empName;
	private boolean srno;
	private int deptallowcode;
	private int deptcode;
	
	
	private String componentType;
	
	
	/*public getEmployeeAllowDeducComponentAmtModel()
	{
		this.EmployeeAllowDeducComponentAmtModel=EmployeeAllowDeducComponentAmtModel;
	}*/
	
	public String getComponentType() {
		return componentType;
	}
	public void setComponentType(String componentType) {
		this.componentType = componentType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getDeptallowcode() {
		return deptallowcode;
	}
	public void setDeptallowcode(int deptallowcode) {
		this.deptallowcode = deptallowcode;
	}
	public int getDeptcode() {
		return deptcode;
	}
	public void setDeptcode(int deptcode) {
		this.deptcode = deptcode;
	}
	public boolean isSrno() {
		return srno;
	}
	public void setSrno(boolean srno) {
		this.srno = srno;
	}

	public int getEmpAwnDedCompoAmtId() {
		return empAwnDedCompoAmtId;
	}
	public void setEmpAwnDedCompoAmtId(int empAwnDedCompoAmtId) {
		this.empAwnDedCompoAmtId = empAwnDedCompoAmtId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	public double[] getExistingAmt() {
		return existingAmt;
	}
	public void setExistingAmt(double[] existingAmt) {
		this.existingAmt = existingAmt;
	}
	public double[] getNetAmt() {
		return netAmt;
	}
	public void setNetAmt(double[] netAmt) {
		this.netAmt = netAmt;
	}
	public int getDepartmentAllowDeducId() {
		return departmentAllowDeducId;
	}
	public void setDepartmentAllowDeducId(int departmentAllowDeducId) {
		this.departmentAllowDeducId = departmentAllowDeducId;
	}
	public int getIsType() {
		return isType;
	}
	public void setIsType(int isType) {
		this.isType = isType;
	}
	public int getDdoCode() {
		return ddoCode;
	}
	public void setDdoCode(int ddoCode) {
		this.ddoCode = ddoCode;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	public String[] getSevaarthId() {
		return sevaarthId;
	}
	public void setSevaarthId(String[] sevaarthId) {
		this.sevaarthId = sevaarthId;
	}
	public String[] getEmpName() {
		return empName;
	}
	public void setEmpName(String[] empName) {
		this.empName = empName;
	}
	public int getCreatedUserId() {
		return createdUserId;
	}
	public void setCreatedUserId(int createdUserId) {
		this.createdUserId = createdUserId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getUpdatedUserId() {
		return updatedUserId;
	}
	public void setUpdatedUserId(int updatedUserId) {
		this.updatedUserId = updatedUserId;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}
	public int getDepartmentAllowdeducCode() {
		return departmentAllowdeducCode;
	}
	public void setDepartmentAllowdeducCode(int departmentAllowdeducCode) {
		this.departmentAllowdeducCode = departmentAllowdeducCode;
	}
	@Override
	public String toString() {
		return "EmployeeAllowDeducComponentAmtModel [empAwnDedCompoAmtId=" + empAwnDedCompoAmtId + ", employeeId="
				+ employeeId + ", existingAmt=" + Arrays.toString(existingAmt) + ", netAmt=" + Arrays.toString(netAmt)
				+ ", departmentAllowDeducId=" + departmentAllowDeducId + ", sevaarthId=" + Arrays.toString(sevaarthId)
				+ ", isType=" + isType + ", ddoCode=" + ddoCode + ", isActive=" + isActive + ", createdUserId="
				+ createdUserId + ", createdDate=" + createdDate + ", updatedUserId=" + updatedUserId + ", updatedDate="
				+ updatedDate + ", empName=" + Arrays.toString(empName) + ", srno=" + srno + ", deptallowcode="
				+ deptallowcode + ", deptcode=" + deptcode + ", componentType=" + componentType + "]";
	}
	
	
}
