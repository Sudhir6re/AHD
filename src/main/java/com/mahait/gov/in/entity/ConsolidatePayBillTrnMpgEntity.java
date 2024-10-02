package com.mahait.gov.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="CONSOLIDATE_PAYBILL_TRN_MPG",schema="public")
public class ConsolidatePayBillTrnMpgEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="CONSOLIDATE_PAYBILL_TRN_MPG_ID")
    private Long ConsolidatePaybillTrnMpgId;
	
	
	@Column(name = "PAYBILL_GENERATION_TRN_ID")
	private Long PaybillGenerationTrnId;
	
	@Column(name = "DDO_CODE")
	private String ddoCode;
	
	@Column(name="CONSOLIDATE_PAYBILL_TRN_ID")
    private Long ConsolidatePaybillTrnId;

	

}
