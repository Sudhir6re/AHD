/**
 * 
 */
package com.mahait.gov.in.model;

import java.io.Serializable;
import java.util.List;

import lombok.Data;


@Data	
public class ConsolidatePayBillModel implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer monthName;
	private Integer billNo;
	private Integer yearName;
	private String schemeCode;
	private String subSchemeName;
	private String ddoCode;
	private String officeName;
	private String billDesc;
	private int billStatus;
	private double  billGrossAmt;
	private double  billNetAmt;
	private Boolean istrue;
	private Boolean checkboxid;
	
	List<ConsolidatePayBillModel> lstCons ;
	
}
