package com.mahait.gov.in.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "empDsgnId")
@Entity
@Table(name = "org_emp_dsgn_mpg")
public class OrgEmpDsgnMpg implements Serializable {

    @Id
    @Column(name = "emp_dsgn_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empDsgnId;

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

   /* @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dsgn_id", nullable = false)
    private OrgDesignationMst orgDesignationMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdatedBy;
    */
    

    @Column(name = "start_date")
    private Timestamp startDate;

    @Column(name = "end_date")
    private Timestamp endDate;

    @Column(name = "activate_flag", nullable = false)
    private Long activateFlag;

    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

	
    
}

