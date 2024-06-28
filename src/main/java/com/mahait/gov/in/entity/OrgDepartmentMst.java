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
@EqualsAndHashCode(of = "departmentId")
@Entity
@Table(name = "org_department_mst")
public class OrgDepartmentMst implements Serializable {

    @Id
    @Column(name = "department_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long departmentId;

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
