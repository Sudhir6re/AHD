package com.mahait.gov.in.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name = "acl_postrole_rlt")
public class AclPostroleRlt {

    @Id
    @Column(name = "post_role_id")
    private Long postRoleId;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "role_id", nullable = false)
    private AclRoleMst aclRoleMst;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "post_id", nullable = false)
    private OrgPostMst orgPostMst;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "activate_flag", nullable = false)
    private CmnLookupMst cmnLookupMstByActivate;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

  
}
