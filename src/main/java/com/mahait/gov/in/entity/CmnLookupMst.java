package com.mahait.gov.in.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmn_lookup_mst")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY, region = "ecache_lookup")
public class CmnLookupMst implements Serializable {

    @Id
    @Column(name = "lookup_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lookupId;

    @Column(name = "parent_lookup_id")
    private Long parentLookupId;

    @Column(name = "lookup_name", length = 30, nullable = false)
    private String lookupName;

    @Column(name = "lookup_short_name", length = 15, nullable = false)
    private String lookupShortName;

    @Column(name = "lookup_desc", length = 100, nullable = false)
    private String lookupDesc;

    @Column(name = "order_no", nullable = false)
    private Long orderNo;

  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;*/

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

	public Long getLookupId() {
		return lookupId;
	}

	public void setLookupId(Long lookupId) {
		this.lookupId = lookupId;
	}

	public Long getParentLookupId() {
		return parentLookupId;
	}

	public void setParentLookupId(Long parentLookupId) {
		this.parentLookupId = parentLookupId;
	}

	public String getLookupName() {
		return lookupName;
	}

	public void setLookupName(String lookupName) {
		this.lookupName = lookupName;
	}

	public String getLookupShortName() {
		return lookupShortName;
	}

	public void setLookupShortName(String lookupShortName) {
		this.lookupShortName = lookupShortName;
	}

	public String getLookupDesc() {
		return lookupDesc;
	}

	public void setLookupDesc(String lookupDesc) {
		this.lookupDesc = lookupDesc;
	}

	public Long getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Long orderNo) {
		this.orderNo = orderNo;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}
    
    
    
    

 
}
