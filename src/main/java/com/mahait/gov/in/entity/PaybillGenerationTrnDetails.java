package com.mahait.gov.in.entity;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PAYBILL_GENERATION_TRN_DETAILS", schema = "public")
public class PaybillGenerationTrnDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PAYBILL_GNT_TRN_DETAIL_ID")
	private int paybillGenerationTrnDetailId;

	@Column(name = "PAYBILL_GENERATION_TRN_ID")
	private int paybillGenerationTrnId;

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
	
	
	public Double getNpsEmployerAllow() {
		return npsEmployerAllow;
	}

	public void setNpsEmployerAllow(Double npsEmployerAllow) {
		this.npsEmployerAllow = npsEmployerAllow;
	}

	public Double getNpsEmployerDeduct() {
		return npsEmployerDeduct;
	}

	public void setNpsEmployerDeduct(Double npsEmployerDeduct) {
		this.npsEmployerDeduct = npsEmployerDeduct;
	}

	public Double getNpsEmployeeContri() {
		return npsEmployeeContri;
	}

	public void setNpsEmployeeContri(Double npsEmployeeContri) {
		this.npsEmployeeContri = npsEmployeeContri;
	}

	public Double getEmprDcpsDelayedRec() {
		return emprDcpsDelayedRec;
	}

	public void setEmprDcpsDelayedRec(Double emprDcpsDelayedRec) {
		this.emprDcpsDelayedRec = emprDcpsDelayedRec;
	}

	public Double getEmprDcpsDaArr() {
		return EmprDcpsDaArr;
	}

	public void setEmprDcpsDaArr(Double emprDcpsDaArr) {
		EmprDcpsDaArr = emprDcpsDaArr;
	}

	public Double getEmprDcpsPayArr() {
		return EmprDcpsPayArr;
	}

	public void setEmprDcpsPayArr(Double emprDcpsPayArr) {
		EmprDcpsPayArr = emprDcpsPayArr;
	}

	public Double getEmprDcpsRegRec() {
		return EmprDcpsRegRec;
	}

	public void setEmprDcpsRegRec(Double emprDcpsRegRec) {
		EmprDcpsRegRec = emprDcpsRegRec;
	}

	
	
	public Double getExcessPayment() {
		return excessPayment;
	}

	public void setExcessPayment(Double excessPayment) {
		this.excessPayment = excessPayment;
	}

	public Double getCmFund() {
		return cmFund;
	}

	public void setCmFund(Double cmFund) {
		this.cmFund = cmFund;
	}

	public Double getRevenueStamp() {
		return revenueStamp;
	}

	public void setRevenueStamp(Double revenueStamp) {
		this.revenueStamp = revenueStamp;
	}

	public Double getExcPayRc() {
		return excPayRc;
	}

	public void setExcPayRc(Double excPayRc) {
		this.excPayRc = excPayRc;
	}

	public Double getGpfAdvGrpD() {
		return gpfAdvGrpD;
	}

	public void setGpfAdvGrpD(Double gpfAdvGrpD) {
		this.gpfAdvGrpD = gpfAdvGrpD;
	}

	public Double getGpfAdvGrpAbc() {
		return gpfAdvGrpAbc;
	}

	public void setGpfAdvGrpAbc(Double gpfAdvGrpAbc) {
		this.gpfAdvGrpAbc = gpfAdvGrpAbc;
	}

	public Double getMisc() {
		return misc;
	}

	public void setMisc(Double misc) {
		this.misc = misc;
	}

	public Double getOtherDed() {
		return otherDed;
	}

	public void setOtherDed(Double otherDed) {
		this.otherDed = otherDed;
	}

	public int getPaybillGenerationTrnDetailId() {
		return paybillGenerationTrnDetailId;
	}

	public void setPaybillGenerationTrnDetailId(int paybillGenerationTrnDetailId) {
		this.paybillGenerationTrnDetailId = paybillGenerationTrnDetailId;
	}

	public int getPaybillGenerationTrnId() {
		return paybillGenerationTrnId;
	}

	public void setPaybillGenerationTrnId(int paybillGenerationTrnId) {
		this.paybillGenerationTrnId = paybillGenerationTrnId;
	}

	public String getSevaarthId() {
		return sevaarthId;
	}

	public void setSevaarthId(String sevaarthId) {
		this.sevaarthId = sevaarthId;
	}

	public Double getBasicPay() {
		return basicPay;
	}

	public void setBasicPay(Double basicPay) {
		this.basicPay = basicPay;
	}

	public Double getDedAdjust() {
		return dedAdjust;
	}

	public void setDedAdjust(Double dedAdjust) {
		this.dedAdjust = dedAdjust;
	}

	public Double getGrossAdjust() {
		return grossAdjust;
	}

	public void setGrossAdjust(Double grossAdjust) {
		this.grossAdjust = grossAdjust;
	}

	public Double getGrossTotalAmt() {
		return grossTotalAmt;
	}

	public void setGrossTotalAmt(Double grossTotalAmt) {
		this.grossTotalAmt = grossTotalAmt;
	}

	public Double getTotalNetAmt() {
		return totalNetAmt;
	}

	public void setTotalNetAmt(Double totalNetAmt) {
		this.totalNetAmt = totalNetAmt;
	}

	public Double getGrossAmt() {
		return grossAmt;
	}

	public void setGrossAmt(Double grossAmt) {
		this.grossAmt = grossAmt;
	}

	public Integer getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Integer createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Double getIsActive() {
		return isActive;
	}

	public void setIsActive(Double isActive) {
		this.isActive = isActive;
	}

	public Double getDa() {
		return da;
	}

	public void setDa(Double da) {
		this.da = da;
	}

	public Double getHra() {
		return hra;
	}

	public void setHra(Double hra) {
		this.hra = hra;
	}

	public Double getPt() {
		return pt;
	}

	public void setPt(Double pt) {
		this.pt = pt;
	}

	public Double getDcpsEmployer() {
		return dcpsEmployer;
	}

	public void setDcpsEmployer(Double dcpsEmployer) {
		this.dcpsEmployer = dcpsEmployer;
	}

	public Double getAdjustDcpsEmployer() {
		return adjustDcpsEmployer;
	}

	public void setAdjustDcpsEmployer(Double adjustDcpsEmployer) {
		this.adjustDcpsEmployer = adjustDcpsEmployer;
	}

	public Double getTotalDeduction() {
		return totalDeduction;
	}

	public void setTotalDeduction(Double totalDeduction) {
		this.totalDeduction = totalDeduction;
	}

	public Double getEmpDcpsDelay() {
		return EmpDcpsDelay;
	}

	public void setEmpDcpsDelay(Double empDcpsDelay) {
		EmpDcpsDelay = empDcpsDelay;
	}

	public Double getEmpDcpsDaArr() {
		return empDcpsDaArr;
	}

	public void setEmpDcpsDaArr(Double empDcpsDaArr) {
		this.empDcpsDaArr = empDcpsDaArr;
	}

	public Double getEmpDcpsPayArr() {
		return EmpDcpsPayArr;
	}

	public void setEmpDcpsPayArr(Double empDcpsPayArr) {
		EmpDcpsPayArr = empDcpsPayArr;
	}

	public Double getEmpDcpsRegularRecovery() {
		return EmpDcpsRegularRecovery;
	}

	public void setEmpDcpsRegularRecovery(Double empDcpsRegularRecovery) {
		EmpDcpsRegularRecovery = empDcpsRegularRecovery;
	}

	public Double getGis() {
		return gis;
	}

	public void setGis(Double gis) {
		this.gis = gis;
	}

	public Double getGpfGrpABC() {
		return gpfGrpABC;
	}

	public void setGpfGrpABC(Double gpfGrpABC) {
		this.gpfGrpABC = gpfGrpABC;
	}

	public Double getGpfGrpD() {
		return gpfGrpD;
	}

	public void setGpfGrpD(Double gpfGrpD) {
		this.gpfGrpD = gpfGrpD;
	}

	public Double getTa() {
		return ta;
	}

	public void setTa(Double ta) {
		this.ta = ta;
	}

	public Double getAddPay() {
		return addPay;
	}

	public void setAddPay(Double addPay) {
		this.addPay = addPay;
	}

	public Double getSpecialPay() {
		return specialPay;
	}

	public void setSpecialPay(Double specialPay) {
		this.specialPay = specialPay;
	}

	public Double getPersonalPay() {
		return personalPay;
	}

	public void setPersonalPay(Double personalPay) {
		this.personalPay = personalPay;
	}

	public Double getContriProvFund() {
		return contriProvFund;
	}

	public void setContriProvFund(Double contriProvFund) {
		this.contriProvFund = contriProvFund;
	}

	public Double getOtherDeductByTreasury() {
		return otherDeductByTreasury;
	}

	public void setOtherDeductByTreasury(Double otherDeductByTreasury) {
		this.otherDeductByTreasury = otherDeductByTreasury;
	}


	public Double getHbaHouseIntAmt() {
		return hbaHouseIntAmt;
	}

	public void setHbaHouseIntAmt(Double hbaHouseIntAmt) {
		this.hbaHouseIntAmt = hbaHouseIntAmt;
	}

	public Double getHbaHouse() {
		return hbaHouse;
	}

	public void setHbaHouse(Double hbaHouse) {
		this.hbaHouse = hbaHouse;
	}

	public Double getServCharge() {
		return servCharge;
	}

	public void setServCharge(Double servCharge) {
		this.servCharge = servCharge;
	}

	public Double getPermanentTravelAllow() {
		return permanentTravelAllow;
	}

	public void setPermanentTravelAllow(Double permanentTravelAllow) {
		this.permanentTravelAllow = permanentTravelAllow;
	}

	public Double getHillStatAllow() {
		return hillStatAllow;
	}

	public void setHillStatAllow(Double hillStatAllow) {
		this.hillStatAllow = hillStatAllow;
	}

	public Double getFamilyPlanAllow() {
		return familyPlanAllow;
	}

	public void setFamilyPlanAllow(Double familyPlanAllow) {
		this.familyPlanAllow = familyPlanAllow;
	}

	public Double getNonCompHRA() {
		return nonCompHRA;
	}

	public void setNonCompHRA(Double nonCompHRA) {
		this.nonCompHRA = nonCompHRA;
	}

	public Double getNonPractAllow() {
		return nonPractAllow;
	}

	public void setNonPractAllow(Double nonPractAllow) {
		this.nonPractAllow = nonPractAllow;
	}

	public Double getOTA() {
		return OTA;
	}

	public void setOTA(Double oTA) {
		OTA = oTA;
	}

	public Double getTransAllowArr() {
		return transAllowArr;
	}

	public void setTransAllowArr(Double transAllowArr) {
		this.transAllowArr = transAllowArr;
	}

	public Double getTribalAllow() {
		return tribalAllow;
	}

	public void setTribalAllow(Double tribalAllow) {
		this.tribalAllow = tribalAllow;
	}

	public Double getUniformAllow() {
		return uniformAllow;
	}

	public void setUniformAllow(Double uniformAllow) {
		this.uniformAllow = uniformAllow;
	}

	public Double getWa() {
		return wa;
	}

	public void setWa(Double wa) {
		this.wa = wa;
	}

	public Double getGisZp() {
		return gisZp;
	}

	public void setGisZp(Double gisZp) {
		this.gisZp = gisZp;
	}

	public Double getGpfAbcArr() {
		return gpfAbcArr;
	}

	public void setGpfAbcArr(Double gpfAbcArr) {
		this.gpfAbcArr = gpfAbcArr;
	}

	public Double getGpfDArr() {
		return gpfDArr;
	}

	public void setGpfDArr(Double gpfDArr) {
		this.gpfDArr = gpfDArr;
	}

	public Double getConveyAllow() {
		return conveyAllow;
	}

	public void setConveyAllow(Double conveyAllow) {
		this.conveyAllow = conveyAllow;
	}

	public Double getGroupAccPolicy() {
		return groupAccPolicy;
	}

	public void setGroupAccPolicy(Double groupAccPolicy) {
		this.groupAccPolicy = groupAccPolicy;
	}

	public Double getRecurringDep() {
		return recurringDep;
	}

	public void setRecurringDep(Double recurringDep) {
		this.recurringDep = recurringDep;
	}

	public Double getCopBank() {
		return copBank;
	}

	public void setCopBank(Double copBank) {
		this.copBank = copBank;
	}

	public Double getCreditSoc() {
		return creditSoc;
	}

	public void setCreditSoc(Double creditSoc) {
		this.creditSoc = creditSoc;
	}

	public Double getCoHsgSoc() {
		return coHsgSoc;
	}

	public void setCoHsgSoc(Double coHsgSoc) {
		this.coHsgSoc = coHsgSoc;
	}

	public Double getBasicArr() {
		return basicArr;
	}

	public void setBasicArr(Double basicArr) {
		this.basicArr = basicArr;
	}

	public Double getDcpsArr() {
		return dcpsArr;
	}

	public void setDcpsArr(Double dcpsArr) {
		this.dcpsArr = dcpsArr;
	}

	public Integer getPaybillMonth() {
		return paybillMonth;
	}

	public void setPaybillMonth(Integer paybillMonth) {
		this.paybillMonth = paybillMonth;
	}

	public Integer getPaybillYear() {
		return paybillYear;
	}

	public void setPaybillYear(Integer paybillYear) {
		this.paybillYear = paybillYear;
	}

	

	public Integer getPayCommissionCode() {
		return payCommissionCode;
	}

	public void setPayCommissionCode(Integer payCommissionCode) {
		this.payCommissionCode = payCommissionCode;
	}

	

	public BigInteger getPay_band() {
		return pay_band;
	}

	public void setPay_band(BigInteger pay_band) {
		this.pay_band = pay_band;
	}

	public Double getDaArr() {
		return daArr;
	}

	public void setDaArr(Double daArr) {
		this.daArr = daArr;
	}

	public Double getAddHRA() {
		return addHRA;
	}

	public void setAddHRA(Double addHRA) {
		this.addHRA = addHRA;
	}

	public Double getNaxalAreaAllow() {
		return naxalAreaAllow;
	}

	public void setNaxalAreaAllow(Double naxalAreaAllow) {
		this.naxalAreaAllow = naxalAreaAllow;
	}

	public Double getLIC() {
		return LIC;
	}

	public void setLIC(Double lIC) {
		LIC = lIC;
	}

	public Double getOtherRec() {
		return otherRec;
	}

	public void setOtherRec(Double otherRec) {
		this.otherRec = otherRec;
	}

	public Double getPtArr() {
		return ptArr;
	}

	public void setPtArr(Double ptArr) {
		this.ptArr = ptArr;
	}

	public Double getOtherDeduct() {
		return otherDeduct;
	}

	public void setOtherDeduct(Double otherDeduct) {
		this.otherDeduct = otherDeduct;
	}

	public Double getOtherAllow() {
		return otherAllow;
	}

	public void setOtherAllow(Double otherAllow) {
		this.otherAllow = otherAllow;
	}

	public Double getSvnDA() {
		return svnDA;
	}

	public void setSvnDA(Double svnDA) {
		this.svnDA = svnDA;
	}

	public Double getHrr() {
		return hrr;
	}

	public void setHrr(Double hrr) {
		this.hrr = hrr;
	}

	public Double getSpclDutyAllow() {
		return spclDutyAllow;
	}

	public void setSpclDutyAllow(Double spclDutyAllow) {
		this.spclDutyAllow = spclDutyAllow;
	}

	public Double getCla() {
		return cla;
	}

	public void setCla(Double cla) {
		this.cla = cla;
	}

	public Double getDearnessPay() {
		return dearnessPay;
	}

	public void setDearnessPay(Double dearnessPay) {
		this.dearnessPay = dearnessPay;
	}

	public Double getDaOnTA() {
		return daOnTA;
	}

	public void setDaOnTA(Double daOnTA) {
		this.daOnTA = daOnTA;
	}

	public Double getIt() {
		return it;
	}

	public void setIt(Double it) {
		this.it = it;
	}

	public Double getDedAdjTry() {
		return dedAdjTry;
	}

	public void setDedAdjTry(Double dedAdjTry) {
		this.dedAdjTry = dedAdjTry;
	}

	public Double getDedAdjAg() {
		return dedAdjAg;
	}

	public void setDedAdjAg(Double dedAdjAg) {
		this.dedAdjAg = dedAdjAg;
	}

	public Double getDedAdjOtr() {
		return dedAdjOtr;
	}

	public void setDedAdjOtr(Double dedAdjOtr) {
		this.dedAdjOtr = dedAdjOtr;
	}

	public Double getComputerAdv() {
		return computerAdv;
	}

	public void setComputerAdv(Double computerAdv) {
		this.computerAdv = computerAdv;
	}public Double getOtherVehAdv() {
		return otherVehAdv;
	}

	public void setOtherVehAdv(Double otherVehAdv) {
		this.otherVehAdv = otherVehAdv;
	}

	public Double getFestivalAdv() {
		return festivalAdv;
	}

	public void setFestivalAdv(Double festivalAdv) {
		this.festivalAdv = festivalAdv;
	}

	public Double getGpfAdvance() {
		return gpfAdvance;
	}

	public void setGpfAdvance(Double gpfAdvance) {
		this.gpfAdvance = gpfAdvance;
	}

	public Double getHra5th() {
		return hra5th;
	}

	public void setHra5th(Double hra5th) {
		this.hra5th = hra5th;
	}

	public Double getHra6th() {
		return hra6th;
	}

	public void setHra6th(Double hra6th) {
		this.hra6th = hra6th;
	}

	public Double getTa5th() {
		return ta5th;
	}

	public void setTa5th(Double ta5th) {
		this.ta5th = ta5th;
	}

	public Double getTa6th() {
		return ta6th;
	}

	public void setTa6th(Double ta6th) {
		this.ta6th = ta6th;
	}

	public Double getArrears() {
		return arrears;
	}

	public void setArrears(Double arrears) {
		this.arrears = arrears;
	}

	public Double getDeputationAllow() {
		return deputationAllow;
	}

	public void setDeputationAllow(Double deputationAllow) {
		this.deputationAllow = deputationAllow;
	}

	public Double getTracerAllow() {
		return tracerAllow;
	}

	public void setTracerAllow(Double tracerAllow) {
		this.tracerAllow = tracerAllow;
	}

	public Double getNaksaliedAllow() {
		return naksaliedAllow;
	}

	public void setNaksaliedAllow(Double naksaliedAllow) {
		this.naksaliedAllow = naksaliedAllow;
	}

	public Double getGpfSubscription() {
		return gpfSubscription;
	}

	public void setGpfSubscription(Double gpfSubscription) {
		this.gpfSubscription = gpfSubscription;
	}

	public Double getHba() {
		return hba;
	}

	public void setHba(Double hba) {
		this.hba = hba;
	}

	public Double getSocOrBankLoan() {
		return socOrBankLoan;
	}

	public void setSocOrBankLoan(Double socOrBankLoan) {
		this.socOrBankLoan = socOrBankLoan;
	}

	public Double getBLWF() {
		return BLWF;
	}

	public void setBLWF(Double bLWF) {
		BLWF = bLWF;
	}

	public Double getGpfArrears() {
		return gpfArrears;
	}

	public void setGpfArrears(Double gpfArrears) {
		this.gpfArrears = gpfArrears;
	}

	public Double getGpfSpecialArr() {
		return gpfSpecialArr;
	}

	public void setGpfSpecialArr(Double gpfSpecialArr) {
		this.gpfSpecialArr = gpfSpecialArr;
	}

	public Double getNDCPSSubscription() {
		return NDCPSSubscription;
	}

	public void setNDCPSSubscription(Double nDCPSSubscription) {
		NDCPSSubscription = nDCPSSubscription;
	}

	public Double getBegis() {
		return begis;
	}

	public void setBegis(Double begis) {
		this.begis = begis;
	}

	public Double getLeavePay() {
		return leavePay;
	}

	public void setLeavePay(Double leavePay) {
		this.leavePay = leavePay;
	}

	public Double getAlliedSoc() {
		return alliedSoc;
	}

	public void setAlliedSoc(Double alliedSoc) {
		this.alliedSoc = alliedSoc;
	}

	public Double getMantralayaSoci() {
		return mantralayaSoci;
	}

	public void setMantralayaSoci(Double mantralayaSoci) {
		this.mantralayaSoci = mantralayaSoci;
	}

	public Double getChiplunSoc() {
		return chiplunSoc;
	}

	public void setChiplunSoc(Double chiplunSoc) {
		this.chiplunSoc = chiplunSoc;
	}

	public Double getUlhasnagarSoc() {
		return ulhasnagarSoc;
	}

	public void setUlhasnagarSoc(Double ulhasnagarSoc) {
		this.ulhasnagarSoc = ulhasnagarSoc;
	}

	public Double getEngrSoc() {
		return engrSoc;
	}

	public void setEngrSoc(Double engrSoc) {
		this.engrSoc = engrSoc;
	}

	public Double getGpfDaSoc() {
		return gpfDaSoc;
	}

	public void setGpfDaSoc(Double gpfDaSoc) {
		this.gpfDaSoc = gpfDaSoc;
	}

	public Double getRop() {
		return rop;
	}

	public void setRop(Double rop) {
		this.rop = rop;
	}

	public Double getPayFixDiff() {
		return payFixDiff;
	}

	public void setPayFixDiff(Double payFixDiff) {
		this.payFixDiff = payFixDiff;
	}

	public Double getNps() {
		return nps;
	}

	public void setNps(Double nps) {
		this.nps = nps;
	}

	public Double getPubHealWrks() {
		return pubHealWrks;
	}

	public void setPubHealWrks(Double pubHealWrks) {
		this.pubHealWrks = pubHealWrks;
	}

	public Double getSindhuOras() {
		return sindhuOras;
	}

	public void setSindhuOras(Double sindhuOras) {
		this.sindhuOras = sindhuOras;
	}

	public Double getJalgaonSoc() {
		return jalgaonSoc;
	}

	public void setJalgaonSoc(Double jalgaonSoc) {
		this.jalgaonSoc = jalgaonSoc;
	}

	public Double getManaBhaiMehJal() {
		return manaBhaiMehJal;
	}

	public void setManaBhaiMehJal(Double manaBhaiMehJal) {
		this.manaBhaiMehJal = manaBhaiMehJal;
	}

	public Double getAkolaPAriAbhi() {
		return akolaPAriAbhi;
	}

	public void setAkolaPAriAbhi(Double akolaPAriAbhi) {
		this.akolaPAriAbhi = akolaPAriAbhi;
	}

	public Double getZpKarmPat() {
		return zpKarmPat;
	}

	public void setZpKarmPat(Double zpKarmPat) {
		this.zpKarmPat = zpKarmPat;
	}

	public Double getVidharbhaGramkokBn() {
		return vidharbhaGramkokBn;
	}

	public void setVidharbhaGramkokBn(Double vidharbhaGramkokBn) {
		this.vidharbhaGramkokBn = vidharbhaGramkokBn;
	}

	public Double getChandaSoc() {
		return chandaSoc;
	}

	public void setChandaSoc(Double chandaSoc) {
		this.chandaSoc = chandaSoc;
	}

	public Double getJalsevaSocNag() {
		return jalsevaSocNag;
	}

	public void setJalsevaSocNag(Double jalsevaSocNag) {
		this.jalsevaSocNag = jalsevaSocNag;
	}

	public Double getBhandaraSoc() {
		return bhandaraSoc;
	}

	public void setBhandaraSoc(Double bhandaraSoc) {
		this.bhandaraSoc = bhandaraSoc;
	}

	public Double getGdccBank() {
		return gdccBank;
	}

	public void setGdccBank(Double gdccBank) {
		this.gdccBank = gdccBank;
	}

	public Double getGondiaSoc() {
		return gondiaSoc;
	}

	public void setGondiaSoc(Double gondiaSoc) {
		this.gondiaSoc = gondiaSoc;
	}

	public Double getNagpurSoc() {
		return nagpurSoc;
	}

	public void setNagpurSoc(Double nagpurSoc) {
		this.nagpurSoc = nagpurSoc;
	}

	public Double getAllahabadSoc() {
		return allahabadSoc;
	}

	public void setAllahabadSoc(Double allahabadSoc) {
		this.allahabadSoc = allahabadSoc;
	}

	public Double getBhanDistCenCopBnk() {
		return bhanDistCenCopBnk;
	}

	public void setBhanDistCenCopBnk(Double bhanDistCenCopBnk) {
		this.bhanDistCenCopBnk = bhanDistCenCopBnk;
	}

	public Double getBankOfBarora() {
		return bankOfBarora;
	}

	public void setBankOfBarora(Double bankOfBarora) {
		this.bankOfBarora = bankOfBarora;
	}

	public Double getCourtComput() {
		return courtComput;
	}

	public void setCourtComput(Double courtComput) {
		this.courtComput = courtComput;
	}

	public Double getJalgaonGSSoc() {
		return jalgaonGSSoc;
	}

	public void setJalgaonGSSoc(Double jalgaonGSSoc) {
		this.jalgaonGSSoc = jalgaonGSSoc;
	}

	public Double getJalgaonHandiSoc() {
		return jalgaonHandiSoc;
	}

	public void setJalgaonHandiSoc(Double jalgaonHandiSoc) {
		this.jalgaonHandiSoc = jalgaonHandiSoc;
	}

	public Double getDhulenandurbarBnk() {
		return dhulenandurbarBnk;
	}

	public void setDhulenandurbarBnk(Double dhulenandurbarBnk) {
		this.dhulenandurbarBnk = dhulenandurbarBnk;
	}

	public Double getParisarAbhiSocNash() {
		return parisarAbhiSocNash;
	}

	public void setParisarAbhiSocNash(Double parisarAbhiSocNash) {
		this.parisarAbhiSocNash = parisarAbhiSocNash;
	}

	public Double getSarwAroBanSoc() {
		return sarwAroBanSoc;
	}

	public void setSarwAroBanSoc(Double sarwAroBanSoc) {
		this.sarwAroBanSoc = sarwAroBanSoc;
	}

	public Double getJalSocCL3() {
		return jalSocCL3;
	}

	public void setJalSocCL3(Double jalSocCL3) {
		this.jalSocCL3 = jalSocCL3;
	}

	public Double getPanipurvtaSocCL3or4() {
		return panipurvtaSocCL3or4;
	}

	public void setPanipurvtaSocCL3or4(Double panipurvtaSocCL3or4) {
		this.panipurvtaSocCL3or4 = panipurvtaSocCL3or4;
	}

	public Double getGovBank() {
		return govBank;
	}

	public void setGovBank(Double govBank) {
		this.govBank = govBank;
	}

	public Double getSangliSalSoc() {
		return sangliSalSoc;
	}

	public void setSangliSalSoc(Double sangliSalSoc) {
		this.sangliSalSoc = sangliSalSoc;
	}

	public Double getMjpSoc() {
		return mjpSoc;
	}

	public void setMjpSoc(Double mjpSoc) {
		this.mjpSoc = mjpSoc;
	}

	public Double getNashikRoadSocCL3or4() {
		return nashikRoadSocCL3or4;
	}

	public void setNashikRoadSocCL3or4(Double nashikRoadSocCL3or4) {
		this.nashikRoadSocCL3or4 = nashikRoadSocCL3or4;
	}

	public Double getJalsevaMalSocCL3() {
		return jalsevaMalSocCL3;
	}

	public void setJalsevaMalSocCL3(Double jalsevaMalSocCL3) {
		this.jalsevaMalSocCL3 = jalsevaMalSocCL3;
	}

	public Double getNashikBankSoc() {
		return nashikBankSoc;
	}

	public void setNashikBankSoc(Double nashikBankSoc) {
		this.nashikBankSoc = nashikBankSoc;
	}

	public Double getMandaNashikSoc() {
		return mandaNashikSoc;
	}

	public void setMandaNashikSoc(Double mandaNashikSoc) {
		this.mandaNashikSoc = mandaNashikSoc;
	}

	public Double getUjwalaMahilaPatBhan() {
		return ujwalaMahilaPatBhan;
	}

	public void setUjwalaMahilaPatBhan(Double ujwalaMahilaPatBhan) {
		this.ujwalaMahilaPatBhan = ujwalaMahilaPatBhan;
	}

	public Double getBcQuar() {
		return bcQuar;
	}

	public void setBcQuar(Double bcQuar) {
		this.bcQuar = bcQuar;
	}

	public Double getExcessPayrec() {
		return excessPayrec;
	}

	public void setExcessPayrec(Double excessPayrec) {
		this.excessPayrec = excessPayrec;
	}

	public Double getFlagDay() {
		return flagDay;
	}

	public void setFlagDay(Double flagDay) {
		this.flagDay = flagDay;
	}

	public Double getBhandJilAbhiKarPat() {
		return bhandJilAbhiKarPat;
	}

	public void setBhandJilAbhiKarPat(Double bhandJilAbhiKarPat) {
		this.bhandJilAbhiKarPat = bhandJilAbhiKarPat;
	}

	public Double getJalsevaKarmSahaPath() {
		return jalsevaKarmSahaPath;
	}

	public void setJalsevaKarmSahaPath(Double jalsevaKarmSahaPath) {
		this.jalsevaKarmSahaPath = jalsevaKarmSahaPath;
	}

	public Double getSocNanded() {
		return socNanded;
	}

	public void setSocNanded(Double socNanded) {
		this.socNanded = socNanded;
	}

	public Double getSocAurang() {
		return socAurang;
	}

	public void setSocAurang(Double socAurang) {
		this.socAurang = socAurang;
	}

	public Double getSocLatur() {
		return socLatur;
	}

	public void setSocLatur(Double socLatur) {
		this.socLatur = socLatur;
	}

	public Double getMlwfonlyMJP() {
		return mlwfonlyMJP;
	}

	public void setMlwfonlyMJP(Double mlwfonlyMJP) {
		this.mlwfonlyMJP = mlwfonlyMJP;
	}

	public Double getMahaLabWelfareFund() {
		return mahaLabWelfareFund;
	}

	public void setMahaLabWelfareFund(Double mahaLabWelfareFund) {
		this.mahaLabWelfareFund = mahaLabWelfareFund;
	}

	public Double getMjpSocLatur() {
		return mjpSocLatur;
	}

	public void setMjpSocLatur(Double mjpSocLatur) {
		this.mjpSocLatur = mjpSocLatur;
	}

	public Double getJalbhavanSoc() {
		return jalbhavanSoc;
	}

	public void setJalbhavanSoc(Double jalbhavanSoc) {
		this.jalbhavanSoc = jalbhavanSoc;
	}

	public Double getMjpSocSolapur() {
		return mjpSocSolapur;
	}

	public void setMjpSocSolapur(Double mjpSocSolapur) {
		this.mjpSocSolapur = mjpSocSolapur;
	}

	public Double getGpfInst() {
		return gpfInst;
	}

	public void setGpfInst(Double gpfInst) {
		this.gpfInst = gpfInst;
	}

	public String getFaInst() {
		return faInst;
	}

	public void setFaInst(String faInst) {
		this.faInst = faInst;
	}

	public String getOthrVehAdvInst() {
		return othrVehAdvInst;
	}

	public void setOthrVehAdvInst(String othrVehAdvInst) {
		this.othrVehAdvInst = othrVehAdvInst;
	}

	public String getHbaHouseInst() {
		return hbaHouseInst;
	}

	public void setHbaHouseInst(String hbaHouseInst) {
		this.hbaHouseInst = hbaHouseInst;
	}

	public String getCompAdvInst() {
		return compAdvInst;
	}

	public void setCompAdvInst(String compAdvInst) {
		this.compAdvInst = compAdvInst;
	}

	public String getGpfAdvInst() {
		return gpfAdvInst;
	}

	public void setGpfAdvInst(String gpfAdvInst) {
		this.gpfAdvInst = gpfAdvInst;
	}

	public Double getSataraSoci() {
		return sataraSoci;
	}

	public void setSataraSoci(Double sataraSoci) {
		this.sataraSoci = sataraSoci;
	}

	public Double getRajashriShahu() {
		return rajashriShahu;
	}

	public void setRajashriShahu(Double rajashriShahu) {
		this.rajashriShahu = rajashriShahu;
	}

	public Double getLicense_Fee() {
		return License_Fee;
	}

	public void setLicense_Fee(Double license_Fee) {
		License_Fee = license_Fee;
	}

	public Double getParsik_Janata_Sh_Vasi() {
		return Parsik_Janata_Sh_Vasi;
	}

	public void setParsik_Janata_Sh_Vasi(Double parsik_Janata_Sh_Vasi) {
		Parsik_Janata_Sh_Vasi = parsik_Janata_Sh_Vasi;
	}

	public String getPayFixAdvDiffInst() {
		return payFixAdvDiffInst;
	}

	public void setPayFixAdvDiffInst(String payFixAdvDiffInst) {
		this.payFixAdvDiffInst = payFixAdvDiffInst;
	}

	public Double getBhagshree_Bank() {
		return Bhagshree_Bank;
	}

	public void setBhagshree_Bank(Double bhagshree_Bank) {
		Bhagshree_Bank = bhagshree_Bank;
	}

	public Double getGpfLoanRec() {
		return gpfLoanRec;
	}

	public void setGpfLoanRec(Double gpfLoanRec) {
		this.gpfLoanRec = gpfLoanRec;
	}

	public Double getVangaonSoc() {
		return vangaonSoc;
	}

	public void setVangaonSoc(Double vangaonSoc) {
		this.vangaonSoc = vangaonSoc;
	}

	public Double getAccidentPolicy() {
		return accidentPolicy;
	}

	public void setAccidentPolicy(Double accidentPolicy) {
		this.accidentPolicy = accidentPolicy;
	}

	public Double getPanipuravthaKolhapur() {
		return panipuravthaKolhapur;
	}

	public void setPanipuravthaKolhapur(Double panipuravthaKolhapur) {
		this.panipuravthaKolhapur = panipuravthaKolhapur;
	}

	public Double getRajashreishahuGovKolahpur() {
		return rajashreishahuGovKolahpur;
	}

	public void setRajashreishahuGovKolahpur(Double rajashreishahuGovKolahpur) {
		this.rajashreishahuGovKolahpur = rajashreishahuGovKolahpur;
	}

	public Double getAhdPariAbhiMarya() {
		return ahdPariAbhiMarya;
	}

	public void setAhdPariAbhiMarya(Double ahdPariAbhiMarya) {
		this.ahdPariAbhiMarya = ahdPariAbhiMarya;
	}

	public String getExcessPayrecint() {
		return excessPayrecint;
	}

	public void setExcessPayrecint(String excessPayrecint) {
		this.excessPayrecint = excessPayrecint;
	}

	public Double getMjpSocBeed() {
		return mjpSocBeed;
	}

	public void setMjpSocBeed(Double mjpSocBeed) {
		this.mjpSocBeed = mjpSocBeed;
	}

	public Double getSalOwnSocSangli() {
		return salOwnSocSangli;
	}

	public void setSalOwnSocSangli(Double salOwnSocSangli) {
		this.salOwnSocSangli = salOwnSocSangli;
	}

	public Double getJalbhavanSocSangli() {
		return jalbhavanSocSangli;
	}

	public void setJalbhavanSocSangli(Double jalbhavanSocSangli) {
		this.jalbhavanSocSangli = jalbhavanSocSangli;
	}

	public Double getHastantritPunemahanSoc() {
		return hastantritPunemahanSoc;
	}

	public void setHastantritPunemahanSoc(Double hastantritPunemahanSoc) {
		this.hastantritPunemahanSoc = hastantritPunemahanSoc;
	}

	public Double getShaskiyPanipurvSocSatara() {
		return shaskiyPanipurvSocSatara;
	}

	public void setShaskiyPanipurvSocSatara(Double shaskiyPanipurvSocSatara) {
		this.shaskiyPanipurvSocSatara = shaskiyPanipurvSocSatara;
	}

	public Double getRecovery() {
		return recovery;
	}

	public void setRecovery(Double recovery) {
		this.recovery = recovery;
	}

	public Double getAkolaSoc() {
		return akolaSoc;
	}

	public void setAkolaSoc(Double akolaSoc) {
		this.akolaSoc = akolaSoc;
	}

	public Double getYavatmalSoc() {
		return yavatmalSoc;
	}

	public void setYavatmalSoc(Double yavatmalSoc) {
		this.yavatmalSoc = yavatmalSoc;
	}

	public Double getNagariSahaPantSansta() {
		return nagariSahaPantSansta;
	}

	public void setNagariSahaPantSansta(Double nagariSahaPantSansta) {
		this.nagariSahaPantSansta = nagariSahaPantSansta;
	}

	public Double getEnggSoc() {
		return enggSoc;
	}

	public void setEnggSoc(Double enggSoc) {
		this.enggSoc = enggSoc;
	}

	public Double getDaryapurSoc() {
		return daryapurSoc;
	}

	public void setDaryapurSoc(Double daryapurSoc) {
		this.daryapurSoc = daryapurSoc;
	}

	public Double getPubHealthSoc() {
		return pubHealthSoc;
	}

	public void setPubHealthSoc(Double pubHealthSoc) {
		this.pubHealthSoc = pubHealthSoc;
	}

	public Double getJalpradayaSoc() {
		return jalpradayaSoc;
	}

	public void setJalpradayaSoc(Double jalpradayaSoc) {
		this.jalpradayaSoc = jalpradayaSoc;
	}

	public Double getZilhaPariKarmPantBul() {
		return zilhaPariKarmPantBul;
	}

	public void setZilhaPariKarmPantBul(Double zilhaPariKarmPantBul) {
		this.zilhaPariKarmPantBul = zilhaPariKarmPantBul;
	}

	public Integer getSubDeptId() {
		return subDeptId;
	}

	public void setSubDeptId(Integer subDeptId) {
		this.subDeptId = subDeptId;
	}

	public BigInteger getDesgCode() {
		return desgCode;
	}

	public void setDesgCode(BigInteger desgCode) {
		this.desgCode = desgCode;
	}

	public Double getJalnaSoc() {
		return jalnaSoc;
	}

	public void setJalnaSoc(Double jalnaSoc) {
		this.jalnaSoc = jalnaSoc;
	}

	public Double getAmrawatiEnggCredSoc() {
		return amrawatiEnggCredSoc;
	}

	public void setAmrawatiEnggCredSoc(Double amrawatiEnggCredSoc) {
		this.amrawatiEnggCredSoc = amrawatiEnggCredSoc;
	}

	public Double getGpfAdvII() {
		return gpfAdvII;
	}

	public void setGpfAdvII(Double gpfAdvII) {
		this.gpfAdvII = gpfAdvII;
	}

	public Double getGpfAdvIIInst() {
		return gpfAdvIIInst;
	}

	public void setGpfAdvIIInst(Double gpfAdvIIInst) {
		this.gpfAdvIIInst = gpfAdvIIInst;
	}

	public Double getPuneDistCentCopBnk() {
		return puneDistCentCopBnk;
	}

	public void setPuneDistCentCopBnk(Double puneDistCentCopBnk) {
		this.puneDistCentCopBnk = puneDistCentCopBnk;
	}

	public Double getNdcpsRec() {
		return ndcpsRec;
	}

	public void setNdcpsRec(Double ndcpsRec) {
		this.ndcpsRec = ndcpsRec;
	}

	public Double getPayAndAllowancesArrears() {
		return payAndAllowancesArrears;
	}

	public void setPayAndAllowancesArrears(Double payAndAllowancesArrears) {
		this.payAndAllowancesArrears = payAndAllowancesArrears;
	}

	public Double getBharatratnaVishweshAbhiSahPatMarya() {
		return bharatratnaVishweshAbhiSahPatMarya;
	}

	public void setBharatratnaVishweshAbhiSahPatMarya(Double bharatratnaVishweshAbhiSahPatMarya) {
		this.bharatratnaVishweshAbhiSahPatMarya = bharatratnaVishweshAbhiSahPatMarya;
	}

	public Integer getSevenPcLevel() {
		return sevenPcLevel;
	}

	public void setSevenPcLevel(Integer sevenPcLevel) {
		this.sevenPcLevel = sevenPcLevel;
	}

	public Double getMotorvehicleAdvance() {
		return motorvehicleAdvance;
	}

	public void setMotorvehicleAdvance(Double motorvehicleAdvance) {
		this.motorvehicleAdvance = motorvehicleAdvance;
	}

	public String getMotorVehAdvInst() {
		return MotorVehAdvInst;
	}

	public void setMotorVehAdvInst(String motorVehAdvInst) {
		MotorVehAdvInst = motorVehAdvInst;
	}

	public Double getMotorVehAdvInstAmt() {
		return MotorVehAdvInstAmt;
	}

	public void setMotorVehAdvInstAmt(Double motorVehAdvInstAmt) {
		MotorVehAdvInstAmt = motorVehAdvInstAmt;
	}

	public Double getBhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha() {
		return BhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha;
	}

	public void setBhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha(
			Double bhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha) {
		BhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha = bhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha;
	}

	public Integer getHraPercent() {
		return hraPercent;
	}

	public void setHraPercent(Integer hraPercent) {
		this.hraPercent = hraPercent;
	}

	public Integer getDaPercent() {
		return daPercent;
	}

	public void setDaPercent(Integer daPercent) {
		this.daPercent = daPercent;
	}

	

	


	
}
