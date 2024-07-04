package com.mahait.gov.in.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ORG_DDO_MST")
@Cacheable
public class OrgDdoMst {

    @Id
    @Column(name = "DDO_ID", precision = 20, scale = 0)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ddoId;

    @Column(name = "DDO_CODE", length = 15)
    private String ddoCode;

    @Column(name = "DDO_NAME")
    private String ddoName;

    @Column(name = "POST_ID", precision = 20, scale = 0)
    private Long postId;

    @Column(name = "ATTACHMENT_ID", precision = 20, scale = 0)
    private Long attachmentId;

    @Column(name = "LANG_ID", precision = 20, scale = 0)
    private Long langId;

    @Column(name = "START_DATE")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Column(name = "END_DATE")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Column(name = "ACTIVATE_FLAG", precision = 20, scale = 0)
    private Long activateFlag;

    @Column(name = "CREATED_BY", precision = 20, scale = 0)
    private Long createdBy;

    @Column(name = "CREATED_BY_POST", precision = 20, scale = 0)
    private Long createdByPost;

    @Column(name = "CREATED_DATE")
    @Temporal(TemporalType.DATE)
    private Date createdDate;

    @Column(name = "UPDATED_BY", precision = 20, scale = 0)
    private Long updatedBy;

    @Column(name = "UPDATED_BY_POST", precision = 20, scale = 0)
    private Long updatedByPost;

    @Column(name = "UPDATED_DATE")
    @Temporal(TemporalType.DATE)
    private Date updatedDate;

    @Column(name = "DB_ID", precision = 20, scale = 0)
    private Long dbId;

    @Column(name = "SHORT_NAME")
    private String shortName;

    @Column(name = "MAJOR_HEAD", length = 10)
    private String majorHead;

    @Column(name = "DEMAND", length = 10)
    private String demand;

    @Column(name = "DDO_NO", precision = 5, scale = 0)
    private Integer ddoNo;

    @Column(name = "CARDEX_NO", precision = 5, scale = 0)
    private Integer cardexNo;

    @Column(name = "TRN_COUNTER", precision = 11, scale = 0)
    private Integer trnCounter;

    @Column(name = "ADMIN_FLAG", precision = 1, scale = 0)
    private Boolean adminFlag;
}
