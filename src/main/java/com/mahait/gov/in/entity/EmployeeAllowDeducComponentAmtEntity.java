package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EMPLOYEE_ALLOWDEDUC_COMPONENT_AMT",schema="public")
public class EmployeeAllowDeducComponentAmtEntity {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="EMP_AWNDED_COMPO_AMT_ID")
	private int empAwnDedCompoAmtId;  
	
	@Column(name="employee_id")
	private int employeeId;  
	   
	@Column(name="EXISTING_AMT")
	private double existingAmt;  
	
	@Column(name="NET_AMT")
	private double netAmt;  
	
	@Column(name="DEPARTMENT_ALLOWDEDUC_ID")
	private int departmentAllowDeducId;  
	
	@Column(name="SEVAARTH_ID")
	private String sevaarthId;  
	
	@Column(name="IS_TYPE")
	private int isType;  
	
	@Column(name="DDO_CODE")
	private int ddoCode;  
	
	@Column(name="IS_ACTIVE")
	private char isActive;  
	
	@Column(name="CREATED_USER_ID")
	private int createdUserId;  
	
	@Column(name="CREATED_DATE")
	private Date createdDate;
	
	@Column(name="UPDATED_USER_ID")
	private int updatedUserId;
	
	@Column(name="UPDATED_DATE")
	private Date updatedDate;

	@Column(name ="EMPLOYEE_NAME")
	private String empName;
	
	@Column(name ="department_allowdeduc_code")
	private int deptallowcode;
	
	
	/*private transient String component;
	
	
	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}
*/
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

	@Column(name ="department_code")
	private int deptcode;
	
	

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
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

	public double getExistingAmt() {
		return existingAmt;
	}

	public void setExistingAmt(double existingAmt) {
		this.existingAmt = existingAmt;
	}

	public double getNetAmt() {
		return netAmt;
	}

	public void setNetAmt(double netAmt) {
		this.netAmt = netAmt;
	}

	public int getDepartmentAllowDeducId() {
		return departmentAllowDeducId;
	}

	public void setDepartmentAllowDeducId(int departmentAllowDeducId) {
		this.departmentAllowDeducId = departmentAllowDeducId;
	}

	public String getSevaarthId() {
		return sevaarthId;
	}

	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
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
	  
	
	
	  

}
