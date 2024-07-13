package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmn_database_mst")
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_ONLY, region = "ecache_lookup")
public class CmnDatabaseMst {

    @Id
    @Column(name = "db_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dbId;

    @Column(name = "db_description", length = 100, nullable = false)
    private String dbDescription;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @Column(name = "activate_flag", nullable = false)
    private Long activateFlag;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "created_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @Column(name = "created_by_post", nullable = false)
    private Long createdByPost;

    @Column(name = "updated_by", nullable = false)
    private Long updatedBy;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @Column(name = "updated_by_post")
    private Long updatedByPost;
}
