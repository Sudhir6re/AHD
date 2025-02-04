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
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;


    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;


    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_POST", referencedColumnName = "POST_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgPostMst createdByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY_POST", referencedColumnName = "POST_ID")
    @Fetch(FetchMode.SELECT)
    private OrgPostMst updatedByPost;

    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgUserMst createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", referencedColumnName = "USER_ID")
    @Fetch(FetchMode.SELECT)
    private OrgUserMst updatedBy;
    


    
    
}

