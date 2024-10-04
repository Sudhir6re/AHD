package com.mahait.gov.in.nps.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="NSDL_SD",schema="public")
public class NSDLSDDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NSDL_SD_id")
    private int nsdlSdId;  
	
	@Column(name="SR_NO")
    private int srno;
	
	@Column(name = "HEADER_NAME",length=2)
	private String headerName;
	
	@Column(name="SD_NO")
	private Long sdNo;
	
	@Column(name = "SD_NO_2")
	private Long sdNo2;
	
	@Column(name = "SD_NO_3")
	private Long sdNo3;
	
	@Column(name = "SD_PRAN_NO")
	private String sdPranNo;
	
	@Column(name = "SD_EMP_AMOUNT",length=11,precision=2)
	private Double sdEmpAmt;
	
	@Column(name = "SD_EMPLR_AMOUNT",length=11,precision=2)
	private Double sdEmplrAmt;
	
	@Column(name = "SD_TOTAL_AMT",length=11,precision=2)
	private Double sdTotalAmt;
	
	@Column(name = "SD_STATUS")
	private String sdStatus;
	
	@Column(name = "SD_REMARK")
	private String sdRemark;
	
	@Column(name = "FILE_NAME")
	private String fileRemark;
	
	@Column(name = "DDO_REG_NO")
	private String ddoRegNo;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "IS_LEGACY_DATA")
	private Character isLegacyData;

	
		
}
