package com.mahait.gov.in.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "gradeId")
@Entity
@Table(name = "org_grade_mst")
public class OrgGradeMst implements Serializable {

	@Id
	@Column(name = "Grade_Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long gradeId;

	@Column(name = "Grade_Name", length = 160, nullable = false)
	private String gradeName;

	@Column(name = "Grade_Desc", length = 400)
	private String gradeDesc;

	@Column(name = "Grade_Code", length = 15, nullable = false)
	private String gradeCode;

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	public String getGradeDesc() {
		return gradeDesc;
	}

	public void setGradeDesc(String gradeDesc) {
		this.gradeDesc = gradeDesc;
	}

	public String getGradeCode() {
		return gradeCode;
	}

	public void setGradeCode(String gradeCode) {
		this.gradeCode = gradeCode;
	}

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Lang_Id", nullable = false) private CmnLanguageMst
	 * cmnLanguageMst;
	 */

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Created_By", nullable = false) private OrgUserMst
	 * orgUserMstByCreatedBy;
	 * 
	 * @Column(name = "Created_Date", nullable = false) private Timestamp
	 * createdDate;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Created_By_Post", nullable = false) private OrgPostMst
	 * orgPostMstByCreatedByPost;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Updated_By") private OrgUserMst orgUserMstByUpdatedBy;
	 * 
	 * 
	 * @Column(name = "Activate_flag", nullable = false) private Long activateFlag;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Updated_By_Post") private OrgPostMst
	 * orgPostMstByUpdatedByPost;
	 * 
	 * @OneToMany(mappedBy = "orgGradeMst") private Set<OrgEmpMst> orgEmpMsts;
	 */

	@Column(name = "Updated_Date")
	private Timestamp updatedDate;

	@Column(name = "Activate_flag", nullable = false)
	private Long activateFlag;

	public Timestamp getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Timestamp updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Long getActivateFlag() {
		return activateFlag;
	}

	public void setActivateFlag(Long activateFlag) {
		this.activateFlag = activateFlag;
	}

	
	
}
