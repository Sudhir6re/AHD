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

    @Column(name = "OFFICE_CODE", length = 20)
    private String officeCode;

    @Column(name = "LOCATION_CODE", length = 20)
    private String locationCode;

    @Column(name = "DEPT_LOC_CODE", length = 20)
    private String deptLocCode;

    @Column(name = "HOD_LOC_CODE", length = 20)
    private String hodLocCode;

    @Column(name = "IS_CO", precision = 1, scale = 0)
    private Boolean isCo;

    @Column(name = "IS_CS", precision = 1, scale = 0)
    private Boolean isCs;

    @Column(name = "TYPE", precision = 2, scale = 0)
    private Short type;

    @Column(name = "DDO_PERSONAL_NAME", length = 20)
    private String ddoPersonalName;

    @Column(name = "DSGN_CODE", length = 20)
    private String designCode;

    @Column(name = "DSGN_NAME", length = 20)
    private String designName;

    @Column(name = "DDO_OFFICE", length = 20)
    private String ddoOffice;

    @Column(name = "REMARKS", length = 20)
    private String remarks;

    @Column(name = "TAN_NO", length = 20)
    private String tanNo;

    @Column(name = "ITAWARDCIRCLE", length = 20)
    private String itaWardNo;

    @Column(name = "BANK_NAME", length = 20)
    private String bankName;

    @Column(name = "BRANCH_NAME", length = 20)
    private String branchName;

    @Column(name = "ACCOUNT_NO", length = 20)
    private String accountNo;

    @Column(name = "IFS_CODE", length = 20)
    private String ifsCode;

    @Column(name = "DDO_TYPE", precision = 19, scale = 0)
    private Long ddoType;

    @Column(name = "ADMIN_DEPT_TYPE", precision = 20, scale = 0)
    private Long adminDeptType;

    @Column(name = "INSTITUTE_TYPE_ID", precision = 19, scale = 0)
    private Long instituteType;

    @Column(name = "STATUS_FLAG")
    private Long statusFlag;
}
