package com.mahait.gov.in.entity;
import javax.persistence.*;

import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "HR_PAY_ORDER_HEAD_MPG")
public class HrPayOrderHeadMpg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_HEAD_ID", precision = 20, scale = 0)
    private Long orderHeadId;

    @Column(name = "ORDER_ID", precision = 20, scale = 0)
    private Long orderId;

    @Column(name = "SUBHEAD_ID")
    private String subheadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    @Column(name = "TRN_COUNTER")
    private Integer trnCounter;

    
}
