package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ORG_DDO_MST", schema = "IFMS")
public class OrgDdoMst {

    @Id
    @Column(name = "DDO_ID")
    private Long ddoId;

    @Column(name = "DDO_CODE", nullable = false, length = 15)
    private String ddoCode;

    @Column(name = "DDO_NAME", length = 500)
    private String ddoName;

    @Column(name = "DDO_PERSONAL_NAME", length = 500)
    private String ddoPersonalName;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private OrgPostMst post;

    /*@ManyToOne
    @JoinColumn(name = "ATTACHMENT_ID")
    private CmnAttachmentMst attachment;*/

    @Column(name = "LANG_ID", nullable = false)
    private Short langId;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "ACTIVATE_FLAG")
    private Short activateFlag;

    @Column(name = "CREATED_BY", nullable = false)
    private Long createdBy;

    @Column(name = "CREATED_BY_POST", nullable = false)
    private Long createdByPost;

    @Column(name = "CREATED_DATE", nullable = false)
    private Date createdDate;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;

    @Column(name = "UPDATED_BY_POST")
    private Long updatedByPost;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

    @Column(name = "DB_ID", nullable = false)
    private Short dbId;

    @Column(name = "SHORT_NAME", length = 150)
    private String shortName;

    @Column(name = "MAJOR_HEAD", length = 4)
    private String majorHead;

    @Column(name = "DEMAND", length = 3)
    private String demand;

    @Column(name = "DDO_NO")
    private Integer ddoNo;

    @Column(name = "CARDEX_NO")
    private Short cardexNo;

    @Column(name = "TRN_COUNTER")
    private Short trnCounter;

    @Column(name = "ADMIN_FLAG")
    private Short adminFlag;

    @Column(name = "OFFICE_CODE", length = 20)
    private String officeCode;

    @Column(name = "LOCATION_CODE", nullable = false, length = 20)
    private String locationCode;

    @Column(name = "DEPT_LOC_CODE", length = 20)
    private String deptLocCode;

    @Column(name = "HOD_LOC_CODE", length = 20)
    private String hodLocCode;

    @Column(name = "IS_CO")
    private Short isCo;

    @Column(name = "IS_CS")
    private Short isCs;

    @Column(name = "TYPE")
    private Short type;

    @Column(name = "VERIFIED")
    private Integer verified;

    @Column(name = "DSGN_CODE", length = 45)
    private String dsgnCode;

    @Column(name = "DSGN_NAME", length = 400)
    private String dsgnName;

    @Column(name = "DDO_OFFICE", length = 300)
    private String ddoOffice;

    @Column(name = "SUB_OFFICE_CODE", length = 20)
    private String subOfficeCode;

    @Column(name = "ATTACHED_PARENT_LOC_CODE", length = 20)
    private String attachedParentLocCode;

    @Column(name = "OFFICE_UNIQUE_CODE", length = 50)
    private String officeUniqueCode;

    @Column(name = "REMARKS", length = 200)
    private String remarks;

    @Column(name = "VERIFIED_DATE")
    private Date verifiedDate;

    @Column(name = "FIXEDID")
    private Long fixedId;

    @Column(name = "TAN_NO", length = 20)
    private String tanNo;

    @Column(name = "ITAWARDCIRCLE", length = 20)
    private String itawardCircle;

    @Column(name = "BANK_NAME", length = 20)
    private String bankName;

    @Column(name = "BRANCH_NAME", length = 20)
    private String branchName;

    @Column(name = "ACCOUNT_NO", length = 20)
    private String accountNo;

    @Column(name = "IFS_CODE", length = 20)
    private String ifsCode;

    @Column(name = "DDO_TYPE")
    private Long ddoType;

    @Column(name = "ADMIN_DEPT_TYPE")
    private Long adminDeptType;

    @Column(name = "INSTITUTE_TYPE_ID")
    private Long instituteTypeId;

    @Column(name = "STATUS_FLAG", length = 20)
    private String statusFlag;

    // Constructors, Getters, and Setters omitted for brevity
    // Implement as per your requirements
}
