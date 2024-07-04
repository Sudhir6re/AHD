package com.mahait.gov.in.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class DDOScreenModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer departmentId;
	private Integer subDepartmentId;
	private String ddoName;
	private Character optradio;
	private String ddoCode;
	private Integer level;
	
	private Integer ddoRegID;
	private String deptName;
	private String subDeptName;
	private String subDeptShortName;
	private Integer levelHier;
	private int isActive;
	private String officeName;
	private int rId;
	
	private int treasuryCode;
	
	private String ddoCityCategory;
	private String cityGroup;
	
	
	private int roleId;
	private String roleName;
	private String roleDesc;
	
	private String roleLevel;
	private int districtCode;
	private int countryCode;
	private int stateCode;
	private int talukaId;
	public Integer talukaCode;
	private String talukaConcatId;
	private char is_ddo_exist;
	private String approval_pending_at_level;
	private int parentDistrictCode;
	private int parentTalukaCode;
	private String villageName;
	private String office_name;
	
	
}
