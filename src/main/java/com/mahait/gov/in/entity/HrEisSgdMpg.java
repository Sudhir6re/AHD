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

@Entity
@Table(name = "HR_EIS_SGD_MPG")
public class HrEisSgdMpg {

    @Id
    @Column(name = "SGD_MAP_ID", precision = 22, scale = 0)
    private Long sgdMapId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGD_SCALE_ID")
    private HrEisScaleMst hrEisScaleMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SGD_GD_ID")
    private HrEisGdMpg hrEisGdMpg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "db_id")
    private CmnDatabaseMst cmnDatabaseMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post")
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_id")
    private CmnLocationMst cmnLocationMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "ELEMENT_CODE", precision = 22, scale = 0)
    private Long elementCode;

}
