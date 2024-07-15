package com.mahait.gov.in.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
@Data
@Entity
@Table(name = "Appointment")
public class AppoinmentEntity {
	
	    @Id
	    @Column(name = "APPOINTMENT_ID")
	    private Long appointmentId;

	  
	    @Column(name = "APPOINTMENT_NAME", nullable = false, length = 100)
	    private String appointmentName;
  
	}



