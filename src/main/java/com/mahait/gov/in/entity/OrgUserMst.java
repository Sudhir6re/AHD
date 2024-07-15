package com.mahait.gov.in.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "org_user_mst")
public class OrgUserMst implements Serializable {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
  
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
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_lookup_id", nullable = false)
    @Fetch(FetchMode.SELECT)
    private CmnLookupMst cmnLookupMst;
    


    @Column(name = "user_name", length = 20, nullable = false)
    private String userName;

    @Column(name = "secret_que_other", length = 255, nullable = false)
    private String secretQueOther;

    @Column(name = "secret_answer", length = 255, nullable = false)
    private String secretAnswer;

    @Column(name = "secret_que_code", length = 30, nullable = false)
    private String secretQueCode;

    @Column(name = "password", length = 128, nullable = false)
    private String password;

    @Column(name = "password_sha", length = 128)
    private String passwordSha;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "activate_flag", nullable = false)
    private Long activateFlag;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "pwdchanged_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pwdchangedDate;

    @Column(name = "unlock_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date unlockTime;

    @Column(name = "invalid_login_cnt")
    private Integer invalidLoginCnt;

    @Column(name = "ip_login")
    private Integer ipLogin;

    @Column(name = "always_login")
    private Integer alwaysLogin;
    
    
    @Column(name = "app_code")
    private Integer appCode;
    
    
    
    

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "firstlogin", length = 1)
    private String firstlogin;
    
    
    
    @Column(name = "ddo_code", length = 1)
    private String ddoCode;
    
    
    

    @OneToMany(mappedBy = "orgUserMst",  fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRlts;

    

   /* @OneToMany(mappedBy = "orgUserMst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRlts;
    
   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private OrgUserMst orgUserMst;
*/

    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private MstRoleEntity mstRoleEntity;
    
    
    


    /*@OneToMany(mappedBy = "orgUserMstByCreatedBy")
    private Set<OrgEmpMst> orgEmpMstsForCreatedBy;

    @OneToMany(mappedBy = "orgUserMstByUpdatedBy")
    private Set<OrgEmpMst> orgEmpMstsForUpdatedBy;
*/
	

    
}

