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
@EqualsAndHashCode(of = "dsgnId")
@Entity
@Table(name = "org_designation_mst")
public class OrgDesignationMst implements Serializable {

    @Id
    @Column(name = "dsgn_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dsgnId;

    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;*/

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsgn_type")
    private CmnLookupMst cmnLookupMstDsgnType;

    @Column(name = "dsgn_name", length = 30, nullable = false)
    private String dsgnName;

    @Column(name = "dsgn_shrt_name", length = 15, nullable = false)
    private String dsgnShrtName;

    @Column(name = "start_date", length = 19)
    private Timestamp startDate;

    @Column(name = "end_date", length = 19)
    private Timestamp endDate;

    @Column(name = "activate_flag")
    private Long activateFlag;

    @Column(name = "created_date", length = 19, nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", length = 19)
    private Timestamp updatedDate;

    @Column(name = "DSGN_CODE", length = 40, nullable = false)
    private String dsgnCode;

    @Column(name = "dsgn_level", nullable = false, columnDefinition = "long default 0")
    private Long dsgnLevel;

    @OneToMany(mappedBy = "orgDesignationMst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgPostMst> orgPostMsts;
}
