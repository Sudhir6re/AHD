package com.mahait.gov.in.model;

import java.util.ArrayList;
import java.util.List;

public class EmpChangeDetailsModel {
	
	private String SevaarthId;
	private String EmpName;
	private String DOB;
    private char gender;
    private String remark;
    public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	private Integer empId;
    public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<EmpChangeDetailsModel> EmpChangeDetailsModelList=new ArrayList<>();
    
    
    
    
	public List<EmpChangeDetailsModel> getEmpChangeDetailsModelList() {
		return EmpChangeDetailsModelList;
	}
	public void setEmpChangeDetailsModelList(List<EmpChangeDetailsModel> empChangeDetailsModelList) {
		EmpChangeDetailsModelList = empChangeDetailsModelList;
	}
	public String getSevaarthId() {
		return SevaarthId;
	}
	public void setSevaarthId(String sevaarthId) {
		SevaarthId = sevaarthId;
	}
	public String getEmpName() {
		return EmpName;
	}
	public void setEmpName(String empName) {
		EmpName = empName;
	}
	public String getDOB() {
		return DOB;
	}
	public void setDOB(String dOB) {
		DOB = dOB;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	
}
