package com.mahait.gov.in.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name = "PAYBILL_GENERATION_TRN_DETAILS1", schema = "public")
public class PaybillGenerationTrnDetails1 implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYBILL_GNT_TRN_DETAIL_ID")
	private Long paybillGenerationTrnDetailId;

	@Column(name = "PAYBILL_GENERATION_TRN_ID")
	private Long paybillGenerationTrnId;

	@Id
	@Column(name = "ID", nullable = false)
	private Long id;

	@Column(name = "EMP_ID")
	private Long empId;

	@Column(name = "SPL_PAY")
	private Long splPay;

	@Column(name = "PO")
	private Long po;

	@Column(name = "D_PAY")
	private Long dPay;

	@Column(name = "DA")
	private Long da;

	@Column(name = "HRA")
	private Long hra;

	@Column(name = "CLA")
	private Long cla;

	@Column(name = "MA")
	private Long ma;

	@Column(name = "WA")
	private Long wa;

	@Column(name = "TRANS_ALL")
	private Long transAll;

	@Column(name = "PAY_RECOVER")
	private Long payRecover;

	@Column(name = "GROSS_AMT")
	private Long grossAmt;

	@Column(name = "IT")
	private Long it;

	@Column(name = "HRR")
	private Long hrr;

	@Column(name = "PLI")
	private Long pli;

	@Column(name = "PT")
	private Long pt;

	@Column(name = "HBA")
	private Long hba;

	@Column(name = "FAN_ADV")
	private Long fanAdv;

	@Column(name = "JEEP_R")
	private Long jeepR;

	@Column(name = "GPF_IV")
	private Long gpfIv;

	@Column(name = "TOTAL_DED")
	private Long totalDed;

	@Column(name = "NET_TOTAL")
	private Long netTotal;

	@Column(name = "CREATED_BY")
	private Long createdBy;

	@Column(name = "CREATED_BY_POST")
	private Long createdByPost;

	@Column(name = "CREATED_DATE")
	private Timestamp createdDate;

	@Column(name = "DB_ID")
	private Short dbId;

	@Column(name = "LOC_ID")
	private Long locId;

	@Column(name = "PER_PAY")
	private Long perPay;

	@Column(name = "PE")
	private Long pe;

	@Column(name = "OTHER_ALLOW")
	private Long otherAllow;

	@Column(name = "BONUS")
	private Long bonus;

	@Column(name = "SURCHARGE")
	private Long surcharge;

	@Column(name = "RENT_B")
	private Long rentB;

	@Column(name = "GPF_ADV")
	private Long gpfAdv;

	@Column(name = "MISC_RECOV")
	private Long miscRecov;

	@Column(name = "TRN_COUNTER")
	private Long trnCounter;

	@Column(name = "DP_GAZZETED")
	private Long dpGazzeted;

	@Column(name = "PAYBILL_GRP_ID", nullable = false)
	private Long paybillGrpId;

	@Column(name = "GPF_IV_ADV")
	private Long gpfIvAdv;

	@Column(name = "POST_ID")
	private Long postId;

	@Column(name = "APPROVE_REJECT_DATE")
	private Timestamp approveRejectDate;

	@Column(name = "DCPS")
	private Integer dcps;

	@Column(name = "PSR_NO")
	private Long psrNo;

	@Column(name = "DA_GPF")
	private Long daGpf;

	@Column(name = "DA_GPFIV")
	private Long daGpfiv;

	@Column(name = "PAYBILL_MONTH")
	private Long paybillMonth;

	@Column(name = "PAYBILL_YEAR")
	private Long paybillYear;

	@Column(name = "OTHER_ID")
	private Long otherId;

	@Column(name = "OTHER_TRN_CNTR")
	private Long otherTrnCntr;

	@Column(name = "GPAY", columnDefinition = "bigint default 0")
	private Long gpay;

	@Column(name = "TECH_ALLOW")
	private Long techAllow;

	@Column(name = "HILLY_ALLOWANCE")
	private Long hillyAllowance;

	@Column(name = "ATS_INCENTIVE_30")
	private Long atsIncentive30;

	@Column(name = "ATS_INCENTIVE_50")
	private Long atsIncentive50;

	@Column(name = "PG_ALLOWANCE")
	private Long pgAllowance;

	@Column(name = "TAA")
	private Long taa;

	@Column(name = "FORCE_1_100")
	private Long force1100;

	@Column(name = "FORCE_1_25")
	private Long force125;

	@Column(name = "ARM_ALLOWANCE")
	private Long armAllowance;

	@Column(name = "ARMOURER")
	private Long armourer;

	@Column(name = "BMI")
	private Long bmi;

	@Column(name = "CASH_ALLOWANCE")
	private Long cashAllowance;

	@Column(name = "CID")
	private Long cid;

	@Column(name = "CONVEYANCE")
	private Long conveyance;

	@Column(name = "EMERGENCY_ALLOW")
	private Long emergencyAllow;

	@Column(name = "ESIS")
	private Long esis;

	@Column(name = "ELA")
	private Long ela;

	@Column(name = "FITNESS_ALLOW")
	private Long fitnessAllow;

	@Column(name = "GALLANTRY_AWARDS")
	private Long gallantryAwards;

	@Column(name = "KIT_MAINTENANCE")
	private Long kitMaintenance;

	@Column(name = "LISENCE_FEE")
	private Long lisenceFee;

	@Column(name = "MECHANICAL_ALLOW")
	private Long mechanicalAllow;

	@Column(name = "MEDICAL_EDUCATION_ALLOW")
	private Long medicalEducationAllow;

	@Column(name = "MESS_ALLOW")
	private Long messAllow;

	@Column(name = "NAXEL_AREA_ALLOW")
	private Long naxelAreaAllow;

	@Column(name = "NON_PRAC_ALLOW")
	private Long nonPracAllow;

	@Column(name = "SUMPTUARY")
	private Long sumptuary;

	@Column(name = "PROJECT_ALLOW")
	private Long projectAllow;

	@Column(name = "SDA")
	private Long sda;

	@Column(name = "ADD_PAY")
	private Long addPay;

	@Column(name = "UNIFORM_ALLOW")
	private Long uniformAllow;

	@Column(name = "FAMILY_PALNNING")
	private Long familyPalnning;

	@Column(name = "GIS")
	private Long gis;

	@Column(name = "CENTRAL_GIS")
	private Long centralGis;

	@Column(name = "GIS_IFS")
	private Long gisIfs;

	@Column(name = "GIS_IAS")
	private Long gisIas;

	@Column(name = "GIS_IPS")
	private Long gisIps;

	@Column(name = "GPF_IAS_OTHER", columnDefinition = "int default 0")
	private Integer gpfIasOther;

	@Column(name = "GPF_IAS", columnDefinition = "int default 0")
	private Integer gpfIas;

	@Column(name = "GPF_IPS", columnDefinition = "int default 0")
	private Integer gpfIps;

	@Column(name = "GPF_IFS", columnDefinition = "int default 0")
	private Integer gpfIfs;

	@Column(name = "GPF_GRP_ABC", columnDefinition = "int default 0")
	private Integer gpfGrpAbc;

	@Column(name = "GPF_GRP_D", columnDefinition = "int default 0")
	private Integer gpfGrpD;

	@Column(name = "SERVICE_CHARGE", columnDefinition = "int default 0")
	private Integer serviceCharge;

	@Column(name = "OTHER_DEDUCTION", columnDefinition = "int default 0")
	private Integer otherDeduction;

	@Column(name = "MAHA_STATE_LIFE_INSURANCE", columnDefinition = "int default 0")
	private Integer mahaStateLifeInsurance;

	@Column(name = "LTC")
	private Long ltc;

	@Column(name = "HBA_CONSTRUCTION", columnDefinition = "int default 0")
	private Integer hbaConstruction;

	@Column(name = "HBA_LAND", columnDefinition = "int default 0")
	private Integer hbaLand;

	@Column(name = "PAY_ADVANCE", columnDefinition = "int default 0")
	private Integer payAdvance;

	@Column(name = "FESTIVAL_ADVANCE", columnDefinition = "int default 0")
	private Integer festivalAdvance;

	@Column(name = "TRAVEL_ADVANCE", columnDefinition = "int default 0")
	private Integer travelAdvance;

	@Column(name = "GPF_ADV_GRP_ABC", columnDefinition = "int default 0")
	private Integer gpfAdvGrpAbc;

	@Column(name = "GPF_ADV_GRP_D", columnDefinition = "int default 0")
	private Integer gpfAdvGrpD;

	@Column(name = "MOTOR_CYCLE_ADV", columnDefinition = "int default 0")
	private Integer motorCycleAdv;

	@Column(name = "OTHER_VEH_ADV", columnDefinition = "int default 0")
	private Integer otherVehAdv;

	@Column(name = "COMPUTER_ADV", columnDefinition = "int default 0")
	private Integer computerAdv;

	@Column(name = "HBA_CONSTRUCTION_INT", columnDefinition = "int default 0")
	private Integer hbaConstructionInt;

	@Column(name = "HBA_LAND_INT", columnDefinition = "int default 0")
	private Integer hbaLandInt;

	@Column(name = "PAY_ADVANCE_INT", columnDefinition = "int default 0")
	private Integer payAdvanceInt;

	@Column(name = "TRAVEL_ADVANCE_INT", columnDefinition = "int default 0")
	private Integer travelAdvanceInt;

	@Column(name = "GPF_ADV_GRP_ABC_INT", columnDefinition = "int default 0")
	private Integer gpfAdvGrpAbcInt;

	@Column(name = "GPF_ADV_GRP_D_INT", columnDefinition = "int default 0")
	private Integer gpfAdvGrpDInt;

	@Column(name = "MOTOR_CYCLE_ADV_INT", columnDefinition = "int default 0")
	private Integer motorCycleAdvInt;

	@Column(name = "OTHER_VEH_ADV_INT", columnDefinition = "int default 0")
	private Integer otherVehAdvInt;

	@Column(name = "COMPUTER_ADV_INT", columnDefinition = "int default 0")
	private Integer computerAdvInt;

	@Column(name = "GIS_ZP")
	private Integer gisZp;

	@Column(name = "GPF_ABC_ARR_MR")
	private Integer gpfAbcArrMr;

	@Column(name = "GPF_D_ARR_MR")
	private Integer gpfDArrMr;

	@Column(name = "GPF_IAS_ARR_MR")
	private Integer gpfIasArrMr;

	@Column(name = "GPF_IFS_ARR_MR")
	private Integer gpfIfsArrMr;

	@Column(name = "GPF_IPS_ARR_MR")
	private Integer gpfIpsArrMr;

	@Column(name = "HRR_ARR")
	private Integer hrrArr;

	@Column(name = "JANJULGISARR")
	private Integer janjulgisarr;

	@Column(name = "OTHER_REC")
	private Integer otherRec;

	@Column(name = "PT_ARR")
	private Integer ptArr;

	@Column(name = "OTHER_DED_TRY")
	private Integer otherDedTry;

	@Column(name = "OTHER_ADV_INT")
	private Long otherAdvInt;

	@Column(name = "MCA_LAND_INT")
	private Long mcaLandInt;

	@Column(name = "MCA_LAND")
	private Long mcaLand;

	@Column(name = "ADD_DA")
	private Integer addDa;

	@Column(name = "ADD_HRA")
	private Integer addHra;

	@Column(name = "DA_ARR")
	private Integer daArr;

	@Column(name = "TEMP_CLA_5THPAY")
	private Integer tempCla5thpay;

	@Column(name = "FRANKING_ALLOW")
	private Integer frankingAllow;

	@Column(name = "TEMP_HRA_5THPAY")
	private Integer tempHra5thpay;

	@Column(name = "LEAVE_TRAVEL_ASSISTANCE")
	private Integer leaveTravelAssistance;

	@Column(name = "MEDICAL_STUDY_ALLOW")
	private Integer medicalStudyAllow;

	@Column(name = "OTHER_ALLOWANCES")
	private Integer otherAllowances;

	@Column(name = "PERMANENT_TRAVELLING")
	private Integer permanentTravelling;

	@Column(name = "TEMP_TA_5THPAY")
	private Integer tempTa5thpay;

	@Column(name = "WASH_ALLOW")
	private Integer washAllow;

	@Column(name = "WRITER_PAY_ALLOW")
	private Long writerPayAllow;

	@Column(name = "TRIBAL_ALLOW")
	private Integer tribalAllow;

	@Column(name = "CO_HSG_SOC", columnDefinition = "int default 0")
	private Integer coHsgSoc;

	@Column(name = "CO_HSG_SOC_INT", columnDefinition = "int default 0")
	private Integer coHsgSocInt;

	@Column(name = "COMP_AIS", columnDefinition = "int default 0")
	private Integer compAis;

	@Column(name = "COMP_AIS_INT", columnDefinition = "int default 0")
	private Integer compAisInt;

	@Column(name = "EXC_PAYRC", columnDefinition = "int default 0")
	private Integer excPayrc;

	@Column(name = "GPF_OTHER_STATE", columnDefinition = "int default 0")
	private Integer gpfOtherState;

	@Column(name = "HBA_AIS", columnDefinition = "int default 0")
	private Integer hbaAis;

	@Column(name = "HBA_AIS_INT", columnDefinition = "int default 0")
	private Integer hbaAisInt;

	@Column(name = "HBA_HOUSE", columnDefinition = "int default 0")
	private Integer hbaHouse;

	@Column(name = "HBA_HOUSE_INT", columnDefinition = "int default 0")
	private Integer hbaHouseInt;

	@Column(name = "MCA_AIS", columnDefinition = "int default 0")
	private Integer mcaAis;

	@Column(name = "MCA_AIS_INT", columnDefinition = "int default 0")
	private Integer mcaAisInt;

	@Column(name = "OTHER_ADV", columnDefinition = "int default 0")
	private Integer otherAdv;

	@Column(name = "GPF_IAS_LOAN")
	private Integer gpfIasLoan;

	@Column(name = "DCPS_DELAY")
	private Long dcpsDelay;

	@Column(name = "DCPS_PAY")
	private Long dcpsPay;

	@Column(name = "DCPS_DA")
	private Long dcpsDa;

	@Column(name = "REFRESHMENT_ALLOW")
	private Long refreshmentAllow;

	@Column(name = "JANJULGIS")
	private Long janjulgis;

	@Column(name = "CDA")
	private Long cda;

	@Column(name = "CTA")
	private Long cta;

	@Column(name = "PEON_ALLOWANCE")
	private Long peonAllowance;

	@Column(name = "INCENTIVE_BDDS")
	private Long incentiveBdds;

	@Column(name = "RT_PILOT")
	private Long rtPilot;

	@Column(name = "CHPL_PILOT")
	private Long chplPilot;

	@Column(name = "KIT_PILOT")
	private Long kitPilot;

	@Column(name = "FLYING_PAY_PILOT")
	private Long flyingPayPilot;

	@Column(name = "INSTRUCTIONAL_PILOT")
	private Long instructionalPilot;

	@Column(name = "QUALIFICATION_PILOT")
	private Long qualificationPilot;

	@Column(name = "INSPECTION_PILOT")
	private Long inspectionPilot;

	@Column(name = "FLYING_ALLOW_PILOT")
	private Long flyingAllowPilot;

	@Column(name = "OUTFIT_PILOT")
	private Long outfitPilot;

	@Column(name = "MILITERY_PILOT")
	private Long militeryPilot;

	@Column(name = "SPECIAL_PAY_PILOT")
	private Long specialPayPilot;

	@Column(name = "CPF")
	private Long cpf;

	@Column(name = "EMP_LNAME", length = 25)
	private String empLname;

	@Column(name = "BASIC_ARR")
	private Long basicArr;

	@Column(name = "DA_ON_TA", columnDefinition = "bigint default 0")
	private Long daOnTa;

	@Column(name = "SCALE_ID", columnDefinition = "bigint default 0")
	private Long scaleId;

	@Column(name = "TRANS_ARREAR", columnDefinition = "bigint default 0")
	private Long transArrear;

	@Column(name = "OVERTIME_ALLOW", columnDefinition = "bigint default 0")
	private Long overtimeAllow;

	@Column(name = "CPF_CONTRIBUTION", columnDefinition = "bigint default 0")
	private Long cpfContribution;

	@Column(name = "CPF_EMPLOYEE", columnDefinition = "bigint default 0")
	private Long cpfEmployee;

	@Column(name = "CPF_EMPLOYER", columnDefinition = "bigint default 0")
	private Long cpfEmployer;

	@Column(name = "ACC_POLICY", columnDefinition = "bigint default 0")
	private Long accPolicy;

	@Column(name = "SVNPC_DA", columnDefinition = "bigint default 0")
	private Long svnpcDa;

	@Column(name = "GROSS_NEW", columnDefinition = "bigint default 0")
	private Long grossNew;

	@Column(name = "TOTAL_DED_NEW", columnDefinition = "bigint default 0")
	private Long totalDedNew;

	@Column(name = "GROSS_SAL", columnDefinition = "bigint default 0")
	private Long grossSal;

	@Column(name = "SVNPC_TA", columnDefinition = "bigint default 0")
	private Long svnpcTa;

	@Column(name = "SVNPC_GPF_ARR", columnDefinition = "bigint default 0")
	private Long svnpcGpfArr;

	@Column(name = "SVNPC_DCPS_ARR", columnDefinition = "bigint default 0")
	private Long svnpcDcpsArr;

	@Column(name = "SVNPC_TA_ARR", columnDefinition = "bigint default 0")
	private Long svnpcTaArr;

	@Column(name = "SVNPC_GPF_ARR_DEDU", columnDefinition = "bigint default 0")
	private Long svnpcGpfArrDedu;

	@Column(name = "SVNPC_GPF_RECO", columnDefinition = "bigint default 0")
	private Long svnpcGpfReco;

	@Column(name = "SVNPC_DCPS_RECO", columnDefinition = "bigint default 0")
	private Long svnpcDcpsReco;

	@Column(name = "NPS_EMPLR", columnDefinition = "bigint default 0")
	private Long npsEmplr;

	@Column(name = "NPS_EMPLR_CONTRI_DED", columnDefinition = "bigint default 0")
	private Long npsEmplrContriDed;

	@Column(name = "REVENUE_STAMP", columnDefinition = "bigint default 0")
	private Long revenueStamp;

}
