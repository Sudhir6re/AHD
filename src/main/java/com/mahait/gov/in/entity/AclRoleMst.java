package com.mahait.gov.in.entity;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;





@Data
@Entity
@Table(name = "acl_role_mst")
public class AclRoleMst {

    @Id
    @Column(name = "role_id")
    private Long roleId;

    @ManyToOne(fetch = FetchType.LAZY) // Adjust FetchType as needed
    @JoinColumn(name = "role_status")
    private CmnLookupMst cmnLookupMstByStatus;

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

    @OneToMany(mappedBy = "aclRoleMst", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AclPostroleRlt> aclPostroleRlts;
/*
    @OneToMany(mappedBy = "aclRoleMst", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AclRoleElementRlt> aclRoleElementRlts;

    @OneToMany(mappedBy = "aclRoleMst", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AclUserRoleRlt> aclUserRoleRlts;

    @OneToMany(mappedBy = "aclRoleMst", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AclRoleDetailsRlt> aclRoleDetailsRlts;*/

}
