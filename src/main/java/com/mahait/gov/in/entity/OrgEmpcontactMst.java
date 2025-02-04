package com.mahait.gov.in.entity;


import java.io.Serializable;
import java.sql.Timestamp;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "empContactId")
@Entity
@Table(name = "org_empcontact_mst")
public class OrgEmpcontactMst implements Serializable {

    @Id
    @Column(name = "emp_contact_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empContactId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private OrgEmpMst orgEmpMst;
    
    
    
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
    
    

  /*  @ManyToOne(fetch = FetchType.LAZY)
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
    @JoinColumn(name = "type_lookup_id", nullable = false)
    private CmnLookupMst cmnLookupMst;
    
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private OrgUserMst orgUserMst;
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private OrgEmpMst orgEmpMst;
    */
    

    @Column(name = "contact_number", length = 50, nullable = false)
    private String contactNumber;

    @Column(name = "created_date", length = 19, nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", length = 19)
    private Timestamp updatedDate;

    @Column(name = "trn_counter")
    private Integer trnCounter;

    @Column(name = "activate_flag")
    private Short activateFlag;
    
}
