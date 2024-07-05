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
@EqualsAndHashCode(of = "empPostId")
@Entity
@Table(name = "org_userpost_rlt")
public class OrgUserpostRlt implements Serializable {

	@Id
	@Column(name = "emp_post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long empPostId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private OrgUserMst orgUserMst;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "status_lookup_id", nullable = false)
	@Fetch(FetchMode.SELECT)
	private CmnLookupMst cmnLookupMst;

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by", referencedColumnName = "USER_ID", nullable = false )
	private OrgUserMst orgUserMstByUpdatedBy;
*/
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

    @ManyToOne
    @JoinColumn(name = "post_id")
    private OrgPostMst orgPostMstByPostId;
    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_id")
    private CmnLookupMst cmnLookupUserPostType;
    
    
    
    

}
