package com.mahait.gov.in.entity;


import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "empId")
@Entity
@Table(name = "org_emp_mst")
public class OrgEmpMst implements Serializable {

    @Id
    @Column(name = "emp_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empId;

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lang_id", nullable = false)
    private CmnLanguageMst cmnLanguageMst;*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private OrgUserMst orgUserMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_id", nullable = false)
    private OrgGradeMst orgGradeMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "emp_fname", length = 30, nullable = false)
    private String empFname;

    @Column(name = "emp_mname", length = 30)
    private String empMname;

    @Column(name = "emp_lname", length = 30, nullable = false)
    private String empLname;

    @Column(name = "emp_dob", nullable = false)
    private Timestamp empDob;

    @Column(name = "emp_doj", nullable = false)
    private Timestamp empDoj;

    @Column(name = "emp_gpf_num", length = 30)
    private String empGPFnumber;

    @Column(name = "emp_srvc_exp")
    private Timestamp empSrvcExp;

    @Column(name = "emp_srvc_flag", nullable = false)
    private Long empSrvcFlag;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "activate_flag", nullable = false)
    private Long activateFlag;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    @Column(name = "emp_prefix", length = 10, nullable = false)
    private String empPrefix;

    @Column(name = "trn_counter")
    private Integer trnCounter;

    @Column(name = "cadre", length = 10)
    private String cadre;

    @Column(name = "buckle_no")
    private Long buckleNo;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpcontactMst> orgEmpcontactMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgUserMst> orgUserMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpMst> orgEmpMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgPostMst> orgPostMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgDepartmentMst> orgDepartmentMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgDesignationMst> orgDesignationMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpaddressMst> orgEmpaddressMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpcontactMst> orgEmpcontactMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgDepartmentMst> orgDepartmentMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpaddressMst> orgEmpaddressMstsForUpdateBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpaddressMst> orgEmpaddressMstsForEmpId;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgUserpostRlt> orgUserpostRltsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgUserpostRlt> orgUserpostRltsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgUserMst> orgUserMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgPostMst> orgPostMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgDesignationMst> orgDesignationMstsForCreatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpMst> orgEmpMstsForUpdatedBy;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpcontactMst> orgEmpcontactMstsForEmpId;

    @OneToMany(mappedBy = "orgEmpMst")
    private Set<OrgEmpDsgnMpg> orgEmpDsgnMpg;
}
