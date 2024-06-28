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
    @JoinColumn(name = "status_lookup_id", nullable = false)
    @Fetch(FetchMode.SELECT)
    private CmnLookupMst cmnLookupMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    @Fetch(FetchMode.SELECT)
    private OrgPostMst orgPostMstByUpdatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    @Fetch(FetchMode.SELECT)
    private OrgUserMst orgUserMstByUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgUserMst orgUserMstByCreatedBy;

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

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "firstlogin", length = 1)
    private String firstlogin;

    @OneToMany(mappedBy = "orgUserMst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgUserpostRlt> orgUserpostRlts;

    @OneToMany(mappedBy = "orgUserMst", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrgEmpMst> orgEmpMsts;
    
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")
    private MstRoleEntity mstRoleEntity;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public CmnLookupMst getCmnLookupMst() {
		return cmnLookupMst;
	}

	public void setCmnLookupMst(CmnLookupMst cmnLookupMst) {
		this.cmnLookupMst = cmnLookupMst;
	}

	public OrgPostMst getOrgPostMstByUpdatedByPost() {
		return orgPostMstByUpdatedByPost;
	}

	public void setOrgPostMstByUpdatedByPost(OrgPostMst orgPostMstByUpdatedByPost) {
		this.orgPostMstByUpdatedByPost = orgPostMstByUpdatedByPost;
	}

	public OrgPostMst getOrgPostMstByCreatedByPost() {
		return orgPostMstByCreatedByPost;
	}

	public void setOrgPostMstByCreatedByPost(OrgPostMst orgPostMstByCreatedByPost) {
		this.orgPostMstByCreatedByPost = orgPostMstByCreatedByPost;
	}

	public OrgUserMst getOrgUserMstByUpdatedBy() {
		return orgUserMstByUpdatedBy;
	}

	public void setOrgUserMstByUpdatedBy(OrgUserMst orgUserMstByUpdatedBy) {
		this.orgUserMstByUpdatedBy = orgUserMstByUpdatedBy;
	}

	public OrgUserMst getOrgUserMstByCreatedBy() {
		return orgUserMstByCreatedBy;
	}

	public void setOrgUserMstByCreatedBy(OrgUserMst orgUserMstByCreatedBy) {
		this.orgUserMstByCreatedBy = orgUserMstByCreatedBy;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSecretQueOther() {
		return secretQueOther;
	}

	public void setSecretQueOther(String secretQueOther) {
		this.secretQueOther = secretQueOther;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public String getSecretQueCode() {
		return secretQueCode;
	}

	public void setSecretQueCode(String secretQueCode) {
		this.secretQueCode = secretQueCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordSha() {
		return passwordSha;
	}

	public void setPasswordSha(String passwordSha) {
		this.passwordSha = passwordSha;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(Long activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Date getPwdchangedDate() {
		return pwdchangedDate;
	}

	public void setPwdchangedDate(Date pwdchangedDate) {
		this.pwdchangedDate = pwdchangedDate;
	}

	public Date getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(Date unlockTime) {
		this.unlockTime = unlockTime;
	}

	public Integer getInvalidLoginCnt() {
		return invalidLoginCnt;
	}

	public void setInvalidLoginCnt(Integer invalidLoginCnt) {
		this.invalidLoginCnt = invalidLoginCnt;
	}

	public Integer getIpLogin() {
		return ipLogin;
	}

	public void setIpLogin(Integer ipLogin) {
		this.ipLogin = ipLogin;
	}

	public Integer getAlwaysLogin() {
		return alwaysLogin;
	}

	public void setAlwaysLogin(Integer alwaysLogin) {
		this.alwaysLogin = alwaysLogin;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFirstlogin() {
		return firstlogin;
	}

	public void setFirstlogin(String firstlogin) {
		this.firstlogin = firstlogin;
	}

	public Set<OrgUserpostRlt> getOrgUserpostRlts() {
		return orgUserpostRlts;
	}

	public void setOrgUserpostRlts(Set<OrgUserpostRlt> orgUserpostRlts) {
		this.orgUserpostRlts = orgUserpostRlts;
	}

	public Set<OrgEmpMst> getOrgEmpMsts() {
		return orgEmpMsts;
	}

	public void setOrgEmpMsts(Set<OrgEmpMst> orgEmpMsts) {
		this.orgEmpMsts = orgEmpMsts;
	}

	public MstRoleEntity getMstRoleEntity() {
		return mstRoleEntity;
	}

	public void setMstRoleEntity(MstRoleEntity mstRoleEntity) {
		this.mstRoleEntity = mstRoleEntity;
	}
    
    
    
    
    
    
}
