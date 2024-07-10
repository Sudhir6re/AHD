package com.mahait.gov.in.entity;


import javax.persistence.*;

@Entity
@Table(name = "INSTITUTE_TYPE")
public class InstituteType {

    @Id
    @Column(name = "INSTITUTE_TYPE_ID")
    private Long instituteTypeId;

    @Column(name = "INSTITUTE_TYPE_NAME", length = 50)
    private String instituteTypeName;

    @Column(name = "PARENT_INSTITUTE_ID")
    private Long parentInstituteId;

    
}

