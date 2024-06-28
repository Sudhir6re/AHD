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
@EqualsAndHashCode(of = "empAddressId")
@Entity
@Table(name = "org_empaddress_mst")
public class OrgEmpaddressMst implements Serializable {

    @Id
    @Column(name = "emp_address_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long empAddressId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private OrgEmpMst orgEmpMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by_post")
    private OrgPostMst orgPostMstByUpdateByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_post", nullable = false)
    private OrgPostMst orgPostMstByCreatedByPost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private OrgUserMst orgUserMstByCreatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_lookup_id", nullable = false)
    private CmnLookupMst cmnLookupMst;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updated_by")
    private OrgUserMst orgUserMstByUpdateBy;

    @Column(name = "EMP_ADDR_1", length = 50)
    private String empAddr1;

    @Column(name = "EMP_ADDR_2", length = 50)
    private String empAddr2;

    @Column(name = "EMP_CITY_ID")
    private Long empCityId;

    @Column(name = "EMP_DISTRICT_ID")
    private Long empDistrictId;

    @Column(name = "EMP_STATE_ID")
    private Long empStateId;

    @Column(name = "emp_pin", length = 10, nullable = false)
    private String empPin;

    @Column(name = "created_date", length = 19, nullable = false)
    private Timestamp createdDate;

    @Column(name = "updated_date", length = 19)
    private Timestamp updateDate;
}
