package com.mahait.gov.in.entity;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "MST_PAYROLL_DESIGNATION")
public class MstPayrollDesignationMst {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DESIG_ID")
    private Long desigId;

    @Column(name = "ORG_DESIGNATION_ID")
    private Long orgDesignationId;

    @Column(name = "DB_ID")
    private Long dbId;

    @Column(name = "LOCATION_CODE")
    private Long locationCode;

    @Column(name = "LANG_ID")
    private Long langId;

    @Column(name = "FIELD_DEPT_ID")
    private Long fieldDeptId;

    @Column(name = "DESIG_CODE")
    private Long desigCode;

    @Column(name = "DSGN_CODE")
    private Long dsgnCode;

    @Column(name = "DESIG_DESC")
    private String desigDesc;

    @Column(name = "CADRE_TYPE_ID")
    private Long cadreTypeId;

    @Column(name = "PAY_COMSN_ID")
    private Long payComsnId;

    @Column(name = "CREATED_USER_ID")
    private Long createdUserId;

    @Column(name = "CREATED_POST_ID")
    private Long createdPostId;

    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @Column(name = "UPDATED_USER_ID")
    private Long updatedUserId;

    @Column(name = "UPDATED_POST_ID")
    private Long updatedPostId;

    @Column(name = "UPDATED_DATE")
    private Date updatedDate;

}
