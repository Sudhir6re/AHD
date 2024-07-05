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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
    
	@JsonIgnore // Ignore this property during serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY_POST", referencedColumnName = "POST_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgPostMst createdByPost;

    
    @JsonIgnore // Ignore this property during serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY_POST", referencedColumnName = "POST_ID")
    @Fetch(FetchMode.SELECT)
    private OrgPostMst updatedByPost;

    
    @JsonIgnore // Ignore this property during serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATED_BY", referencedColumnName = "USER_ID", nullable = false)
    @Fetch(FetchMode.SELECT)
    private OrgUserMst createdBy;

    @JsonIgnore // Ignore this property during serialization
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPDATED_BY", referencedColumnName = "USER_ID")
    @Fetch(FetchMode.SELECT)
    private OrgUserMst updatedBy;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private OrgEmpMst orgEmpMst;
    
	
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


}
