package com.mahait.gov.in.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



	@Data
	@Entity
	@Table(name = "Qualification")
	public class QualificationEntity {
		
		    @Id
		    @Column(name = "Q_ID")
		    private Long qId;

		  
		    @Column(name = "QUALIFICATION", nullable = false, length = 100)
		    private String qualification;
	  
		}
