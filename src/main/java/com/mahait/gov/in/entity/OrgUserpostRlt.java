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
	@JoinColumn(name = "updated_by_post")
	private OrgPostMst orgPostMstByUpdatedByPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	private OrgPostMst orgPostMstByPostId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_post", nullable = false)
	private OrgPostMst orgPostMstByCreatedByPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private OrgUserMst orgUserMstByCreatedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by")
	private OrgUserMst orgUserMstByUpdatedBy;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userpost_type_lookup_id", nullable = false)
	private CmnLookupMst cmnLookupUserPostType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reason_lookup_id")
	private CmnLookupMst cmnLookupReason;
}
