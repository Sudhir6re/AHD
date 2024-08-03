/**
 * 
 */
package com.mahait.gov.in.model;

import java.io.Serializable;

import lombok.Data;


@Data	
public class ConsolidatePayBillModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer monthName;
	private Integer yearName;
	private String schemeCode;
	private String subSchemeName;
	private String ddoCode;
	private String officeName;
	private int billStatus;
	private double  billGrossAmt;
	
}
