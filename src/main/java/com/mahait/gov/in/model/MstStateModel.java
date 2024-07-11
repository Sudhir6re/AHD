package com.mahait.gov.in.model;

import java.io.Serializable;

public class MstStateModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private int id;
	private String stateNameEn;
	private String stateNameMr;
	private int countryCode;
	private String countryNameEn;
	private String countryNameMr;
	private int stateCode;
	private char isActive;
	private int districtCode;
	private String districtNameEn;
	
	
	//int, java.lang.String, java.lang.String, int, java.lang.String, java.lang.String, int, char
	
	public MstStateModel(int id, String stateNameEn, String stateNameMr, int countryCode, String countryNameEn,
			String countryNameMr, int stateCode, char isActive) {//,int districtCode,String districtNameEn
		super();
		this.id = id;
		this.stateNameEn = stateNameEn;
		this.stateNameMr = stateNameMr;
		this.countryCode = countryCode;
		this.countryNameEn = countryNameEn;
		this.countryNameMr = countryNameMr;
		this.stateCode = stateCode;
		this.isActive = isActive;
		/*this.districtCode = districtCode;
		this.districtNameEn = districtNameEn;*/
	}
	public int getDistrictCode() {
		return districtCode;
	}
	public void setDistrictCode(int districtCode) {
		this.districtCode = districtCode;
	}
	public String getDistrictNameEn() {
		return districtNameEn;
	}
	public void setDistrictNameEn(String districtNameEn) {
		this.districtNameEn = districtNameEn;
	}
	public MstStateModel() {
		//just create Default Parameter for Employee configuration : Manikandan Jayaraman
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStateNameEn() {
		return stateNameEn;
	}
	public void setStateNameEn(String stateNameEn) {
		this.stateNameEn = stateNameEn;
	}
	public String getStateNameMr() {
		return stateNameMr;
	}
	public void setStateNameMr(String stateNameMr) {
		this.stateNameMr = stateNameMr;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}
	public String getCountryNameEn() {
		return countryNameEn;
	}
	public void setCountryNameEn(String countryNameEn) {
		this.countryNameEn = countryNameEn;
	}
	public String getCountryNameMr() {
		return countryNameMr;
	}
	public void setCountryNameMr(String countryNameMr) {
		this.countryNameMr = countryNameMr;
	}
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public char getIsActive() {
		return isActive;
	}
	public void setIsActive(char isActive) {
		this.isActive = isActive;
	}
	@Override
	public String toString() {
		return "MstStateModel [id=" + id + ", stateNameEn=" + stateNameEn + ", stateNameMr=" + stateNameMr
				+ ", countryCode=" + countryCode + ", countryNameEn=" + countryNameEn + ", countryNameMr="
				+ countryNameMr + ", stateCode=" + stateCode + ", isActive=" + isActive + "]";
	}
	
	
	
	
	
	
}
