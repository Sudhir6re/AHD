package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "PAYBILL_GENERATION_TRN_DETAILS", schema = "public")
public class PaybillGenerationTrnDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYBILL_GNT_TRN_DETAIL_ID")
	private Long paybillGenerationTrnDetailId;

	@Column(name = "PAYBILL_GENERATION_TRN_ID")
	private Long paybillGenerationTrnId;

	@Column(name = "SEVAARTH_ID")
	private String sevaarthId;

	@Column(name = "BASIC_PAY")
	private Double basicPay;

	@Column(name = "DED_ADJUST")
	private Double dedAdjust;

	@Column(name = "GROSS_ADJUST")
	private Double grossAdjust;

	@Column(name = "GROSS_TOTAL_AMT")
	private Double grossTotalAmt;

	@Column(name = "TOTAL_NET_AMT")
	private Double totalNetAmt;

	@Column(name = "GROSS_AMT")
	private Double grossAmt;

	@Column(name = "created_user_id")
	private Integer createdUserId;

	@Column(name = "CREATED_DATE")
	private Date createdDate;

	@Column(name = "IS_ACTIVE")
	private Double isActive;

	@Column(name = "DA")
	private Double da;

	@Column(name = "HRA")
	private Double hra;
	
	@Column(name = "HRA5th")
	private Double hra5th;
	
	@Column(name = "HRA6th")
	private Double hra6th;

	@Column(name = "PT")
	private Double pt;

	@Column(name = "DCPS_EMPLOYER")
	private Double dcpsEmployer;

	@Column(name = "NPS_EMPR_ALLOW")
	private Double npsEmployerAllow;

	@Column(name = "NPS_EMPR_DEDUCT")
	private Double npsEmployerDeduct;

	@Column(name = "NPS_EMP_CONTRI")
	private Double npsEmployeeContri;

	@Column(name = "ADJUST_DCPS_EMPLOYER")
	private Double adjustDcpsEmployer;

	@Column(name = "TOTAL_DEDUCTION")
	private Double totalDeduction;



	@Column(name = "Emp_DCPS_DELAY")
	private Double EmpDcpsDelay;

	@Column(name = "Emp_DCPS_DA_ARR")
	private Double empDcpsDaArr;

	@Column(name = "Emp_DCPS_PAY_ARR")
	private Double EmpDcpsPayArr;

	@Column(name = "Emp_DCPS_REGULAR_RECOVERY")
	private Double EmpDcpsRegularRecovery;
	
	@Column(name = "Employer_DCPS_Delayed_Rec")
	private Double emprDcpsDelayedRec;
	
	@Column(name = "Employer_DCPS_DA_Arrears")
	private Double EmprDcpsDaArr;
	
	@Column(name = "Employer_DCPS_Pay_Arrears")
	private Double EmprDcpsPayArr;
	
	@Column(name = "Employer_DCPS_Regular_Rec")
	private Double EmprDcpsRegRec;
	
	

	@Column(name = "GIS")
	private Double gis;

	@Column(name = "GPF_GRP_ABC")
	private Double gpfGrpABC;

	@Column(name = "GPF_GRP_D")
	private Double gpfGrpD;

	@Column(name = "TA")
	private Double ta;
	
	@Column(name = "TA5th")
	private Double ta5th;
	
	@Column(name = "TA6th")
	private Double ta6th;

	@Column(name = "ADD_PAY")
	private Double addPay;

	@Column(name = "SPECIAL_PAY")
	private Double specialPay;

	@Column(name = "PERSONAL_PAY")
	private Double personalPay;

	@Column(name = "CONTRI_PROV_FUND")
	private Double contriProvFund;

	@Column(name = "OTHER_DEDUCT_BY_TREASURY")
	private Double otherDeductByTreasury;

	@Column(name = "HBA_HOUSE_INT_AMT")
	private Double hbaHouseIntAmt;

	@Column(name = "HBA_HOUSE")
	private Double hbaHouse;

	@Column(name = "COMP_ADV")
	private Double computerAdv;

	@Column(name = "SERV_CHARG")
	private Double servCharge;

	@Column(name = "PERMANENT_TRAVEL_ALLOW")
	private Double permanentTravelAllow;

	@Column(name = "Hill_Station_Allow")
	private Double hillStatAllow;

	@Column(name = "FAMILY_PLAN_ALLOW")
	private Double familyPlanAllow;

	@Column(name = "NON_COMP_HRA")
	private Double nonCompHRA;

	@Column(name = "NON_PRACT_ALLOW")
	private Double nonPractAllow;

	@Column(name = "OTA")
	private Double OTA;

	@Column(name = "TRANS_ALLOW_ARR")
	private Double transAllowArr;

	@Column(name = "TRIBAL_ALLOW")
	private Double tribalAllow;

	@Column(name = "UNIFORM_ALLOW")
	private Double uniformAllow;

	@Column(name = "WA")
	private Double wa;

	@Column(name = "GIS_ZP")
	private Double gisZp;
	
	@Column(name = "Bhagshree_Bank")
	private Double Bhagshree_Bank;

	@Column(name = "GPF_ABC_ARR")
	private Double gpfAbcArr;

	@Column(name = "GPF_D_ARR")
	private Double gpfDArr;

	@Column(name = "CONVEY_ALLOW")
	private Double conveyAllow;

	@Column(name = "GROUP_ACC_POLICY")
	private Double groupAccPolicy;

	@Column(name = "RECURRING_DEP")
	private Double recurringDep;

	@Column(name = "COP_Bank")
	private Double copBank;

	@Column(name = "CREDIT_SOC")
	private Double creditSoc;

	@Column(name = "CO_HSG_SOC")
	private Double coHsgSoc;

	@Column(name = "BASIC_ARR")
	private Double basicArr;

	@Column(name = "DCPS_ARR")
	private Double dcpsArr;

	@Column(name = "PAYBILL_MONTH")
	private Integer paybillMonth;

	@Column(name = "PAYBILL_YEAR")
	private Integer paybillYear;

	@Column(name = "PAY_COMMISSION_CODE")
	private Integer payCommissionCode;
	
	@Column(name = "seven_pc_level")
	private Integer sevenPcLevel;
	
	@Column(name = "PAY_BAND" )
	private BigInteger pay_band;

	@Column(name = "DA_ARR")
	private Double daArr;

	@Column(name = "ADD_HRA")
	private Double addHRA;

	@Column(name = "NAXAL_AREA_ALLOW")
	private Double naxalAreaAllow;

	@Column(name = "LIC")
	private Double LIC;
	
	@Column(name = "License_Fee")
	private Double License_Fee;

	@Column(name = "OTHER_REC")
	private Double otherRec;

	@Column(name = "PT_ARR")
	private Double ptArr;

	@Column(name = "OTHER_DEDUCT")
	private Double otherDeduct;

	@Column(name = "OTHER_ALLOW")
	private Double otherAllow;

	@Column(name = "SVN_PC_DA")
	private Double svnDA;

	@Column(name = "HRR")
	private Double hrr;

	@Column(name = "SPCL_DUTY_ALLOW")
	private Double spclDutyAllow;

	@Column(name = "CLA")
	private Double cla;

	@Column(name = "DEARNESS_PAY")
	private Double dearnessPay;

	@Column(name = "DA_ON_TA")
	private Double daOnTA;

	@Column(name = "INCOME_TAX")
	private Double it;

	@Column(name = "DEDUCT_ADJ_TRY")
	private Double dedAdjTry;

	@Column(name = "DEDUCT_ADJ_AG")
	private Double dedAdjAg;

	@Column(name = "DEDUCT_ADJ_OTR")
	private Double dedAdjOtr;

	@Column(name = "OTHER_DED")
	private Double otherDed;

	@Column(name = "MISC")
	private Double misc;

	@Column(name = "GPF_ADV_GRP_ABC")
	private Double gpfAdvGrpAbc;

	@Column(name = "GPF_ADV_GRP_D")
	private Double gpfAdvGrpD;
	
	@Column(name = "GPF_ADVANCE")
	private Double gpfAdvance;

	@Column(name = "EXC_PAYRC")
	private Double excPayRc;

	@Column(name = "revenue_stamp")
	private Double revenueStamp;

	@Column(name = "Excess_payment")
	private Double excessPayment;

	@Column(name = "CM_Fund_AC_INS")
	private Double cmFund;
	
	@Column(name = "OTHER_VEH_ADV")
	private Double otherVehAdv;
	
	@Column(name = "FA")
	private Double festivalAdv;
	
	@Column(name = "Arrears")
	private Double arrears;
	
	@Column(name = "Deputation_Allow")
	private Double deputationAllow;
	
	@Column(name = "Tracer_Allow")
	private Double tracerAllow;
	
	@Column(name = "Naksalied_Allow")
	private Double naksaliedAllow;
	
	@Column(name = "GPF_Subscription")
	private Double gpfSubscription;
	
	@Column(name = "HBA")
	private Double hba;
	
	@Column(name = "Society_Or_Bank_Loan")
	private Double socOrBankLoan;
	
	@Column(name = "BLWF")
	private Double BLWF;
	
	@Column(name = "GPF_Arrears")
	private Double gpfArrears;
	
	@Column(name = "GPF_Special_Arr")
	private Double gpfSpecialArr;
	
	@Column(name = "NDCPS_Subscription")
	private Double NDCPSSubscription;
	
	@Column(name = "BEGIS")
	private Double begis;
	
	@Column(name = "Leave_Pay")
	private Double leavePay;
	
	@Column(name = "Allied_Soc")
	private Double alliedSoc;
	
	@Column(name = "Mantralaya_Soci")
	private Double mantralayaSoci;
	
	@Column(name = "Chiplun_Soc")
	private Double chiplunSoc;
	
	@Column(name = "Ulhasnagar_Soc")
	private Double ulhasnagarSoc;
	
	@Column(name = "Engr_Soc")
	private Double engrSoc;
	
	@Column(name = "GPF_DA_Sub")
	private Double gpfDaSoc;
	
	@Column(name = "ROP")
	private Double rop;
	
	@Column(name = "Pay_Fix_Diff")
	private Double payFixDiff;
	
	@Column(name = "NPS")
	private Double nps;
	
	@Column(name = "Public_Health_Works")
	private Double pubHealWrks;
	
	@Column(name = "Sindhudurg_Oras")
	private Double sindhuOras;
	
	@Column(name = "Jalgaon_Society")
	private Double jalgaonSoc;
	
	@Column(name = "Manahar_bhai_Meh_Jal")
	private Double manaBhaiMehJal;
	
	@Column(name = "Akola_Pari_Abhiyani")
	private Double akolaPAriAbhi;
	
	@Column(name = "ZP_Karmchari_Pat")
	private Double zpKarmPat;
	
	@Column(name = "Vidharbha_Gramin_Kokan_Bn")
	private Double vidharbhaGramkokBn;
	
	@Column(name = "Chanda_Soc")
	private Double chandaSoc;
	
	@Column(name = "Jalseva_Soc_Nag")
	private Double jalsevaSocNag;
	
	@Column(name = "Bhandara_Soc")
	private Double bhandaraSoc;
	
	@Column(name = "GDCC_BANK")
	private Double gdccBank;
	
	@Column(name = "Gondia_Soc")
	private Double gondiaSoc;
	
	@Column(name = "Nagpur_Soc")
	private Double nagpurSoc;
	
	@Column(name = "Allahabad_Soc")
	private Double allahabadSoc;
	
	@Column(name = "Bhan_Dist_Cent_Cop_bnk")
	private Double bhanDistCenCopBnk;
	
	@Column(name = "Bank_of_Barora")
	private Double bankOfBarora;
	
	@Column(name = "Court_Computation")
	private Double courtComput;
	
	@Column(name = "Jalgaon_GS_Soc")
	private Double jalgaonGSSoc;
	
	@Column(name = "Jalgaon_Handicap_Soci")
	private Double jalgaonHandiSoc;
	
	@Column(name = "Dhule_Nandurbar_Bank")
	private Double dhulenandurbarBnk;
	
	@Column(name = "Parisar_Abhi_Soc_Nashik")
	private Double parisarAbhiSocNash;
	
	@Column(name = "Sarw_Aroy_Ban_Soci_Dhule")
	private Double sarwAroBanSoc;
	
	@Column(name = "Jaldhara_Soc_CL3")
	private Double jalSocCL3;
	
	@Column(name = "Panipurvtha_Soc_Cl3Or4")
	private Double panipurvtaSocCL3or4;
	
	@Column(name = "Govt_Bank")
	private Double govBank;
	
	@Column(name = "Sangli_Sal_Soc")
	private Double sangliSalSoc;
	
	@Column(name = "MJP_Soc")
	private Double mjpSoc;
	
	@Column(name = "Nashik_Road_Soc_CL3Or4")
	private Double nashikRoadSocCL3or4;
	
	@Column(name = "Jalseva_Malegaon_Soc_CL3")
	private Double jalsevaMalSocCL3;
	
	@Column(name = "Nashik_Bank_Soc")
	private Double nashikBankSoc;
	
	@Column(name = "Manda_Nashik_Soc")
	private Double mandaNashikSoc;
	
	@Column(name = "Ujwala_Mahila_Pat_Bhand")
	private Double ujwalaMahilaPatBhan;
	
	@Column(name = "BC_Quarter")
	private Double bcQuar;
	
	@Column(name = "Excess_Pay_Rec")
	private Double excessPayrec;
	
	@Column(name = "excess_pay_rec_int")
	private String excessPayrecint;
	
	@Column(name = "Flag_Day")
	private Double flagDay;
	
	@Column(name = "Bhand_Jil_Abhi_Karm_Pat")
	private Double bhandJilAbhiKarPat;
	
	@Column(name = "Jalseva_karm_saha_Path")
	private Double jalsevaKarmSahaPath;
	
	@Column(name = "Society_Nanded")
	private Double socNanded;
	
	@Column(name = "Society_Aurangabad")
	private Double socAurang;
	
	@Column(name = "Society_Latur")
	private Double socLatur;
	
	@Column(name = "MLWF_OnlyMJP")
	private Double mlwfonlyMJP;
	
	@Column(name = "Maha_Lab_Welfare_Fund")
	private Double mahaLabWelfareFund;
	
	@Column(name = "MJP_Soc_Latur")
	private Double mjpSocLatur;
	
	@Column(name = "Jal_Bhavan_Society")
	private Double jalbhavanSoc;
	
	@Column(name = "MJP_Soc_Solapur")
	private Double mjpSocSolapur;
	
	@Column(name = "Satara_Society")
	private Double sataraSoci;
	
	@Column(name = "Rajashri_Shahu")
	private Double rajashriShahu;
	
	@Column(name = "Parsik_Janata_Sh_Vasi")
	private Double Parsik_Janata_Sh_Vasi;
	
	@Column(name = "GPF_INST")
	private Double gpfInst;
	
	@Column(name = "FA_INST")
	private String faInst;
	
	@Column(name = "OTHER_VEH_ADV_INST")
	private String othrVehAdvInst;
	
	@Column(name = "HBA_HOUSE_INST")
	private String hbaHouseInst;
	
	@Column(name = "COMP_ADV_INST")
	private String compAdvInst;
	
	@Column(name = "GPF_ADVANCE_INST")
	private String gpfAdvInst;
	
	@Column(name = "PAY_FIXADV_Diff_INST")
	private String payFixAdvDiffInst;
	
	@Column(name = "GPF_Loan_REC")
	private Double gpfLoanRec;
	
	@Column(name = "Vangaon_Society")
	private Double vangaonSoc;
	
	@Column(name = "Accidential_Policy")
	private Double accidentPolicy;
	
	@Column(name = "panipuravtha_kolhapur")
	private Double panipuravthaKolhapur;
	
	@Column(name = "rajashrishahu_govbank_kolhapur")
	private Double rajashreishahuGovKolahpur;
	
	@Column(name = "Ahmednagar_pari_Abhiseva_Marya")
	private Double ahdPariAbhiMarya;
	
	@Column(name = "MJP_Soc_Beed")
	private Double mjpSocBeed;
	
	@Column(name = "Sal_owner_soc_Sangli")
	private Double salOwnSocSangli;
	
	@Column(name = "jalbhavan_soc_sangli")
	private Double jalbhavanSocSangli;
	
	@Column(name = "Hastantrit_pune_Mahan_soc")
	private Double hastantritPunemahanSoc;
	
	@Column(name = "Shaskiy_panipuravtha_soc_satara")
	private Double shaskiyPanipurvSocSatara;
	
	@Column(name = "Recovery")
	private Double recovery;
	
	@Column(name = "akola_society")
	private Double akolaSoc;
	
	@Column(name = "yavatmal_society")
	private Double yavatmalSoc;
	
	@Column(name = "nagari_sahakar_path_sansta")
	private Double nagariSahaPantSansta;
	
	@Column(name = "engineering_society")
	private Double enggSoc;
	
	@Column(name = "daryapur_society")
	private Double daryapurSoc;
	
	@Column(name = "public_health_society")
	private Double pubHealthSoc;
	
	@Column(name = "jalpradaya_society")
	private Double jalpradayaSoc;
	
	@Column(name = "zilha_pari_karmachari_pantsanstha_buldhana")
	private Double zilhaPariKarmPantBul;
	
	@Column(name = "sub_department_id")
	private Integer subDeptId;
	
	@Column(name = "desg_code")
	private BigInteger desgCode;
	
	@Column(name = "Jalna_soc")
	private Double jalnaSoc;
	
	@Column(name = "amrawati_dist_engg_credit_soc")
	private Double amrawatiEnggCredSoc;
	
	@Column(name = "GPF_Advance_II")
	private Double gpfAdvII;
	
	@Column(name = "GPF_Advance_II_Inst")
	private Double gpfAdvIIInst;
	
	@Column(name = "PUNE_DIST_CENTRAL_COP_BANK")
	private Double puneDistCentCopBnk;
	
	@Column(name = "NDCPS_REC")
	private Double ndcpsRec;
	
	@Column(name = "PAY_AND_ALLOWANCES_ARREARS")
	private Double payAndAllowancesArrears;
	
	@Column(name = "BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA")
	private Double bharatratnaVishweshAbhiSahPatMarya;
	
	@Column(name = "Motor_vehicle_advance")
	private Double motorvehicleAdvance;
	
	@Column(name = "Motor_Veh_Adv_Inst")
	private String MotorVehAdvInst ;
	
	@Column(name = "Motor_Veh_Adv_Inst_Amt")
	private Double MotorVehAdvInstAmt ;
	
	@Column(name = "bhandara_zilla_parishadwpanchayat_samiti_karamachari_sahakari")
	private Double BhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha;
	
	
	@Column(name = "HRA_Percentage")
	private Integer hraPercent;
	
	
	@Column(name = "DA_Percentage")
	private Integer daPercent;
	
}
