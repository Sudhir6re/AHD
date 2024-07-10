package com.mahait.gov.in.entity;

import javax.persistence.*;

import lombok.Data;


@Data
@Entity
@Table(name = "hr_pay_post_psr_mpg") 
public class HrPayPsrPostMpg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    @Column(name = "ID") 
    private Long psrPostId;

    @Column(name = "PSR_NO") 
    private Long psrId;

    @Column(name = "POST_ID") 
    private Long postId;

    @Column(name = "LOC_ID") 
    private Long locId;

    @Column(name = "BILL_NO")
    private Long billNo;
    
}