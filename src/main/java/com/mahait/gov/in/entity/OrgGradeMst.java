package com.mahait.gov.in.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "Lang_Id", nullable = false) private CmnLanguageMst
	 * cmnLanguageMst;
	 */

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Created_By", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @Column(name = "Created_Date", nullable = false)
    private Timestamp createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Created_By_Post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Updated_By")
    private OrgUserMst orgUserMstByUpdatedBy;

    @Column(name = "Updated_Date")
    private Timestamp updatedDate;

    @Column(name = "Activate_flag", nullable = false)
    private Long activateFlag;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Updated_By_Post")
    private OrgPostMst orgPostMstByUpdatedByPost;
}
