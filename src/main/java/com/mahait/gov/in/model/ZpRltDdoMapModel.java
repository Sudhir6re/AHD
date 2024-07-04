package com.mahait.gov.in.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Data;


@Data
public class ZpRltDdoMapModel implements Serializable {
    
    private Long zpMapId;

    private Long zpDdoPostId;

    private Long reptDdoPostId;

    private Long finalDdoPostId;

    private Long specialDdoPostId;

    private String zpDdoCode;

    private String finalDdoCode;

    private String reptDdoCode;

    private String specialDdoCode;

    private Long zplevel;

    private Long langId;

    private Long createdUserId;

    private Timestamp createdDate;

    private Long updatedUserId;

    private Timestamp updatedDate;

    private Long createdPostId;

    private Long updatedPostId;
 
    
    private String cmbAdminOffice;
    private String cmbDistOffice;
    private String radioFinalLevel;
    private String txtRepDDOCode;
    private String txtFinalDDOCode;
    private String txtSpecialDDOCode;
    private String radioSalutation;
    private String txtDDOName;
    private String radioGender;
    private String txtTreasuryName;
    private String txtTreasuryCode;
    private String cmbSubTreasury;
    private String txtDDODsgn;
    private String txtOfficeName;
    private String txtDDOCode;
    private String txtMobileNo;
    private String txtEmailId;
}