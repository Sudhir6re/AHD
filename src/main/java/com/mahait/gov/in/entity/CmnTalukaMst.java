package com.mahait.gov.in.entity;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmn_taluka_mst")
public class CmnTalukaMst {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taluka_id")
    private Long talukaId;

    @Column(name = "lang_id", nullable = false)
    private Integer langId;

    @Column(name = "district_id", nullable = false)
    private Long districtId;

    @Column(name = "taluka_name", nullable = false, length = 50)
    private String talukaName;


    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

  

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_POST", referencedColumnName = "POST_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgPostMst createdByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY_POST", referencedColumnName = "POST_ID")
    @Fetch(FetchMode.SELECT)
    private OrgPostMst updatedByPost;

    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgUserMst createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", referencedColumnName = "USER_ID")
    @Fetch(FetchMode.SELECT)
    private OrgUserMst updatedBy;

    @Column(name = "taluka_code", nullable = false, length = 15)
    private String talukaCode;

    @Column(name = "vidhansabha_id")
    private Long vidhansabhaId;

    @Column(name = "activate_flag", nullable = false)
    private Integer activateFlag;

   
    
}

   
