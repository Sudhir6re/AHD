package com.mahait.gov.in.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "postId")
@Entity
@Table(name = "org_post_mst")
public class OrgPostMst implements Serializable {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_lookup_id", nullable = false)
    private CmnLookupMst cmnLookupMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;

    @Column(name = "parent_post_id", nullable = false)
    private Long parentPostId;

    @Column(name = "post_level_id", nullable = false)
    private Long postLevelId;

    @Column(name = "start_date", length = 19)
    private Timestamp startDate;

    @Column(name = "end_date", length = 19)
    private Timestamp endDate;

    @Column(name = "activate_flag", nullable = false)
    private Long activateFlag;

    @Column(name = "created_date", length = 19, nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", length = 19)
    private Timestamp updatedDate;

    @Column(name = "location_code", length = 20, nullable = false)
    private String locationCode;

    @Column(name = "branch_code", length = 20)
    private String branchCode;

    @Column(name = "dsgn_code", length = 40, nullable = false)
    private String dsgnCode;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgDepartmentMst> orgDepartmentMstsForUpdatedByPost;

  /*  @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRltsForCreatedByPost;*/

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserMst> orgUserMstsForUpdatedByPost;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgDesignationMst> orgDesignationMstsForUpdatedByPost;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpMst> orgEmpMstsForUpdatedByPost;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgPostMst> orgPostMstsForUpdatedByPost;
/*
    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpcontactMst> orgEmpcontactMstsForCreatedByPost;

    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpaddressMst> orgEmpaddressMstsForCreatedByPost;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRltsForUpdatedByPost;
*/
    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserMst> orgUserMstsForCreatedByPost;
/*
    @OneToMany(mappedBy = "orgPostMstByPostId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRltsForPostId;
*/
    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgDesignationMst> orgDesignationMstsForCreatedByPost;

    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgPostMst> orgPostMstsForCreatedByPost;

    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpMst> orgEmpMstsForCreatedByPost;

    @OneToMany(mappedBy = "orgPostMstByCreatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgDepartmentMst> orgDepartmentMstsForCreatedByPost;
/*
    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpcontactMst> orgEmpcontactMstsForUpdatedByPost;

    @OneToMany(mappedBy = "orgPostMstByUpdatedByPost", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpaddressMst> orgEmpaddressMstsForUpdateByPost;
*/
    @OneToMany(mappedBy = "orgPostMstByPostId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgPostDetailsRlt> orgPostDetailsRlt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_type_lookup_id")
    private CmnLookupMst postTypeLookupId;
}
