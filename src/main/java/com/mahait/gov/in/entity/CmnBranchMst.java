package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmn_branch_mst")
public class CmnBranchMst {

    @Id
    @Column(name = "BRANCH_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long branchId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LANG_ID", nullable = false)
    private CmnLanguageMst cmnLanguageMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DB_ID", nullable = false)
    private CmnDatabaseMst cmnDatabaseMst;
    
    
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "loc_id", nullable = false)
    private CmnLocationMst cmnLocationMst;

    @Column(name = "BRANCH_CODE", length = 20, nullable = false)
    private String branchCode;

    @Column(name = "BRANCH_NAME", length = 20, nullable = false)
    private String branchName;

    @Column(name = "BRANCH_DESC", length = 65535, nullable = false)
    private String branchDesc;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "ACTIVATE_FLAG")
    private Long activateFlag;

    @Column(name = "CREATED_BY_POST", nullable = false)
    private Long createdByPost;

    @Column(name = "UPDATED_BY_POST")
    private Long updatedByPost;

    @Column(name = "DISPLAY_NAME", nullable = false)
    private String displayName;
    
    @Transient
	String bankName;
}
