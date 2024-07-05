package com.mahait.gov.in.model;

import java.math.BigInteger;

public class BillgroupMaintainanceModel

{
	
private BigInteger schemeId;
private String dcpsDdoSchemeCode;
private String description;
private String typeOfPost;
private String group;
private String dcpsDdoBillTypeOfPost;


public String getDcpsDdoBillTypeOfPost() {
	return dcpsDdoBillTypeOfPost;
}

public void setDcpsDdoBillTypeOfPost(String dcpsDdoBillTypeOfPost) {
	this.dcpsDdoBillTypeOfPost = dcpsDdoBillTypeOfPost;
}

public BigInteger getSchemeId() {
	return schemeId;
}

public void setSchemeId(BigInteger schemeId) {
	this.schemeId = schemeId;
}



public String getDcpsDdoSchemeCode() {
	return dcpsDdoSchemeCode;
}

public void setDcpsDdoSchemeCode(String dcpsDdoSchemeCode) {
	this.dcpsDdoSchemeCode = dcpsDdoSchemeCode;
}



public String getDescription() {
	return description;
}

public void setDescription(String description) {
	this.description = description;
}

public String getTypeOfPost() {
	return typeOfPost;
}

public void setTypeOfPost(String typeOfPost) {
	this.typeOfPost = typeOfPost;
}

public String getGroup() {
	return group;
}

public void setGroup(String group) {
	this.group = group;
}


}
