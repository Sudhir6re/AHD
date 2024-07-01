package com.mahait.gov.in.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "departmentId")
@Entity
@Table(name = "org_department_mst")
public class OrgDepartmentMst implements Serializable {

	@Id
	@Column(name = "department_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long departmentId;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "lang_id", nullable = false) private CmnLanguageMst
	 * cmnLanguageMst;
	 */

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "updated_by_post")
	private OrgPostMst orgPostMstByUpdatedByPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by_post", nullable = false)
	private OrgPostMst orgPostMstByCreatedByPost;
	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "updated_by") private OrgUserMst orgEmpMstByUpdatedBy;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "created_by", nullable = false) private OrgUserMst
	 * orgEmpMstByCreatedBy;
	 */

	/*
	 * 
	 * @OneToMany(mappedBy = "orgEmpMst") private Set<OrgDepartmentMst>
	 * orgDepartmentMstsForCreatedBy;
	 * 
	 * 
	 * 
	 * 
	 * @ManyToOne
	 * 
	 * @JoinColumn(name="orgDepartmentMstsForCreatedBy",nullable=false) private
	 * OrgEmpMst orgEmpMst;
	 * 
	 * 
	 * @OneToMany(mappedBy = "orgEmpMst") private Set<OrgEmpMst> orgEmpMsts;
	 */
	@Column(name = "dep_name", length = 30, nullable = false)
	private String depName;

	@Column(name = "dep_short_name", length = 15, nullable = false)
	private String depShortName;

	@Column(name = "parent_dep_id", nullable = false)
	private Long parentDepId;

	@Column(name = "start_date", length = 19, nullable = false)
	private Timestamp startDate;

	@Column(name = "end_date", length = 19)
	private Timestamp endDate;

	@Column(name = "activate_flag", nullable = false)
	private Long activateFlag;

	@Column(name = "created_date", length = 19, nullable = false)
	private Timestamp createdDate;

	@Column(name = "updated_date", length = 19)
	private Timestamp updatedDate;

	@Column(name = "DEPARTMENT_CODE", length = 20, nullable = false)
	private String depCode;

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepName() {
		return depName;
	}

	public void setDepName(String depName) {
		this.depName = depName;
	}

	public String getDepShortName() {
		return depShortName;
	}

	public void setDepShortName(String depShortName) {
		this.depShortName = depShortName;
	}

	public Long getParentDepId() {
		return parentDepId;
	}

	public void setParentDepId(Long parentDepId) {
		this.parentDepId = parentDepId;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}

	public Long getActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(Long activateFlag) {
		this.activateFlag = activateFlag;
	}

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

}
