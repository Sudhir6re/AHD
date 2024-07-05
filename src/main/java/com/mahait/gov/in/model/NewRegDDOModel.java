package com.mahait.gov.in.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Tejashree.Thombre
 *
 */
public class NewRegDDOModel {
	
	
	public String sevaarthId;
	private Integer employeeId;
	private String empName;
	private boolean checkboxid;
	private String ddoCode;
	private String lvl1ddoCode;
	private String lvl2ddoCode;
	private String lvl3ddoCode;
	private String lvl1ddoName;
	private String lvl2ddoName;
	private String officeName;
	private Date dob;
	private char gender;
	private String desgName;
	
	public List<NewRegDDOModel> emplist=new ArrayList<>();

	public String getSevaarthId() {
		return sevaarthId;
	}

	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public boolean isCheckboxid() {
		return checkboxid;
	}

	public void setCheckboxid(boolean checkboxid) {
		this.checkboxid = checkboxid;
	}

	public String getDdoCode() {
		return ddoCode;
	}

	public void setDdoCode(String ddoCode) {
		this.ddoCode = ddoCode;
	}

	public List<NewRegDDOModel> getEmplist() {
		return emplist;
	}

	public void setEmplist(List<NewRegDDOModel> emplist) {
		this.emplist = emplist;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public String getDesgName() {
		return desgName;
	}

	public void setDesgName(String desgName) {
		this.desgName = desgName;
	}

	public String getLvl1ddoCode() {
		return lvl1ddoCode;
	}

	public void setLvl1ddoCode(String lvl1ddoCode) {
		this.lvl1ddoCode = lvl1ddoCode;
	}

	public String getLvl2ddoCode() {
		return lvl2ddoCode;
	}

	public void setLvl2ddoCode(String lvl2ddoCode) {
		this.lvl2ddoCode = lvl2ddoCode;
	}

	public String getLvl1ddoName() {
		return lvl1ddoName;
	}

	public void setLvl1ddoName(String lvl1ddoName) {
		this.lvl1ddoName = lvl1ddoName;
	}

	public String getLvl2ddoName() {
		return lvl2ddoName;
	}

	public void setLvl2ddoName(String lvl2ddoName) {
		this.lvl2ddoName = lvl2ddoName;
	}

	public String getLvl3ddoCode() {
		return lvl3ddoCode;
	}

	public void setLvl3ddoCode(String lvl3ddoCode) {
		this.lvl3ddoCode = lvl3ddoCode;
	}
	
	
	
	
	

}
