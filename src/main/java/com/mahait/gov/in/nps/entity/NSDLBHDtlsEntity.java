package com.mahait.gov.in.nps.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name="NSDL_BH",schema="public")
public class NSDLBHDtlsEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="NSDL_BH_id")
    private int nsdlBhId;  
	
	@Column(name="SR_NO")
    private int srno;
	
	@Column(name = "HEADER_NAME")
	private String headerName;
	
	@Column(name="BH_NO")
	private Character bhNo;
	
	@Column(name = "BH_COL2")
	private Character bhCol2;
	
	@Column(name = "BH_FIX_NO")
	private String bhFixNo;
	
	@Column(name = "BH_DATE")
	private String bhDate;
	
	@Column(name = "BH_BATCH_FIX_ID")
	private String bhBatchFixId;
	
	@Column(name = "BH_DDO_COUNT")
	private Integer bhddoCount;
	
	@Column(name = "BH_PRAN_COUNT")
	private String bhPRANCount;
	
	@Column(name = "BH_EMP_AMOUNT",length=11,precision=2)
	private Double bhEmpAmt;
	
	@Column(name = "BH_EMPLR_AMOUNT",length=11,precision=2)
	private Double bhEmplrAmt;
	
	@Column(name = "BH_TOTAL_AMT",length=11,precision=2)
	private Double bhTotalAmt;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "YEAR")
	private Integer year;
	
	
	@Column(name = "YEAR_NAME")
	private Integer yearName;
	
	@Column(name = "MONTH")
	private Integer month;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "NSDL_FILE_HASH")
	private String nsdlFileHash;
	
	@Column(name = "FILE_STATUS")
	private String fileStatus;
	
	@Column(name = "TRANSACTION_ID")
	private String transactionId;
	
	@Column(name = "ERROR_DATA")
	private String errorData;
	
	@Column(name = "OLD_TRANSACTION_ID")
	private String oldTransactionId;
	
	@Column(name = "REASON_FOR_TRAN_ID_UPDATE")
	private String reasonFortranIdUpdt;
	
	@Column(name = "REMARK_FOR_TRAN_ID_UPDATE")
	private String remarkForTranIdUpdt;
	
	@Column(name = "FRN_NO")
	private String frnNo;
	
	@Column(name = "IS_LEGACY_DATA")
	private String isLegacyData;
	
	
	@Column(name = "NSDL_STATUS_CODE")
	private String nsdlStatusCode;
	
	@Column(name = "IS_ERROR")
	private Integer isError;
	

	@Column(name = "CREATED_USER_ID")
	private Integer createdUserId;
	
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	
	@Column(name = "UPDATED_USER_ID")
	private Integer updatedUserId;

	@Column(name = "UPDATED_DATE")
	private Date updatedDate;


	
}
