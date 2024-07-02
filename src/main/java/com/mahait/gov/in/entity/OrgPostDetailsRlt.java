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

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;*/

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

	public Long getPostDetailId() {
		return postDetailId;
	}

	public void setPostDetailId(Long postDetailId) {
		this.postDetailId = postDetailId;
	}

	public OrgPostMst getOrgPostMst() {
		return orgPostMst;
	}

	public void setOrgPostMst(OrgPostMst orgPostMst) {
		this.orgPostMst = orgPostMst;
	}

	public OrgDesignationMst getOrgDesignationMst() {
		return orgDesignationMst;
	}

	public void setOrgDesignationMst(OrgDesignationMst orgDesignationMst) {
		this.orgDesignationMst = orgDesignationMst;
	}

	public CmnLookupMst getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(CmnLookupMst postCategory) {
		this.postCategory = postCategory;
	}

	public String getPostName() {
		return postName;
	}

	public void setPostName(String postName) {
		this.postName = postName;
	}

	public String getPostShortName() {
		return postShortName;
	}

	public void setPostShortName(String postShortName) {
		this.postShortName = postShortName;
	}

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_id", nullable = false)
    private CmnLocationMst cmnLocationMst;
*/
  /*  @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "CREATED_DATE", length = 19, nullable = false)
    private Timestamp createdDate;

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
    
    
    
}
