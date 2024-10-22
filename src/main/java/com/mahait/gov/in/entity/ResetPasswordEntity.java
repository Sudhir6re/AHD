package com.mahait.gov.in.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;


@Table(name="reset_password_mst")
@Data
@Entity
public class ResetPasswordEntity {
  
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;

    @OneToOne
    private OrgUserMst user;

}