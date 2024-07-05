package com.mahait.gov.in.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postDetailId")
@Entity
@Table(name = "org_post_details_rlt")
public class OrgPostDetailsRlt implements Serializable {

    @Id
    @Column(name = "post_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postDetailId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private OrgPostMst orgPostMst;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_lookup_id", nullable = false)
    private CmnLookupMst cmnLookupMst;

    
    
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
    
    
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsgn_id", nullable = false)
    private OrgDesignationMst orgDesignationMst;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private CmnBranchMst cmnBranchMst;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category")
    private CmnLookupMst postCategory;

    @Column(name = "post_name", length = 100, nullable = false)
    private String postName;

    @Column(name = "post_short_name", length = 60, nullable = false)
    private String postShortName;

    @Column(name = "CREATED_DATE", length = 19, nullable = false)
    private Timestamp createdDate;


  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

   

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY")
    private OrgUserMst orgUserMstByUpdatedBy;

    @Column(name = "UPDATED_DATE", length = 19)
    private Timestamp updatedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_POST", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY_POST")
    private OrgPostMst orgPostMstByUpdatedByPost;*/
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_id", nullable = false)
    private CmnLocationMst cmnLocationMst;
    
    
    
    
}
