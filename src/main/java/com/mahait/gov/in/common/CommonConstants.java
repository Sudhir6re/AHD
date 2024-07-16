package com.mahait.gov.in.common;

public class CommonConstants {

	public interface Message{
		String ADDED_ENGLSH = "Record added successfully !!! ";
		String ADDED_MARATHI = "रेकॉर्ड यशस्वीरित्या जोडले";
		String UPDATED_ENGLSH = "Record updated successfully !!! ";
		String UPDATED_MARATHI = "रेकॉर्ड यशस्वीरित्या अद्यतनित केले";
		String DELETE = "Record delete successfully";
		String INACTIVATED = "Record inactivated successfully !!! ";
		String REJECTED_ENGLSH = "Record rejected successfully !!! ";
		String REJECTED_MARATHI = "रेकॉर्ड यशस्वीरित्या नाकारला";
		String SWR = "Some Thing Went Wrong !!! ";
		String DD_CADRE_GRP ="DD_CADRE_GRP";
		String ALLREADYEXISTS_ENGLISH = "Same record is already present in database !!!";
		String ALLREADYEXISTS_MARATHI = "समान रेकॉर्ड आधीच डेटाबेसमध्ये सादर आहे";
		String SAVEDRAFT="All the details saved successfully";
		String FRWDDDO="Registration form is forwarded successfully";
		
		
		String FRWDCLK="Opening Balance Saved and Forwarded To Next Authority";
		
		String SAVEUPDATEATTACHDETTACH="Bill Group Modified Successfully";
		
		
	}
	
	public interface STATUS{
		String SUCCESS = "SUCCESS";
		String WARNING = "WARNING";
		String ERROR = "ERROR";
		String APPROVED = "APPROVED";
		String REJECTED = "REJECTED";
	}
	
	public interface DEFAULTPASSWORD{
		String DEFAULT_PASSWORD = "ifms123";
	}
	
	public interface LEVELS {
		String LEVEL1 = "DDO_USER_LEVEL1";
		String LEVEL2 = "DDO_USER_LEVEL2";
		String LEVEL3 = "DDO_USER_LEVEL3";
		String LEVEL4 = "DDO_USER_LEVEL4";
		String LEVEL8 = "Inward Level";
		String LEVEL9 = "Clerk Level";
		String LEVEL10 = "Ass Accountant Level";
		String LEVEL11 = "Superintendent";
		String LEVEL12 = "Pension_AST";
		String LEVEL13 = "AG";
		String LEVEL14 = "Inward Clerk";
		String LEVEL15 = "Pension Clerk";
		String LEVEL16 = "AUDITOR";
		String LEVEL17 = "ATO";
		String LEVEL18 = "Sr Clerk";
		String LEVEL19 = "Final Clerk";
		String LEVEL20 = "ROLE OS";
		String LEVEL21 = "ROLE SE";
		String LEVEL22 = "ROLE MS";
		String LEVEL23 = "ROLE JR";
		String LEVEL24 = "ROLE DO";
		String LEVEL25 = "ROLE AEO";
		String LEVEL26 = "CAO";
		String LEVEL29 = "Pen_FC";
		String LEVEL30 = "Pen_AAO";
		String LEVEL31 = "Pen_AO";
		String LEVEL32 = "Pen_SAO";
		String LEVEL33 = "Pen_DYCAO1";
		String LEVEL34 = "Pen_CASHIER";
	}
	public interface DDOUSERPWD {
		String PASSWORD = "Password@123";
		String PASSWORD1 = "ifms123";
	}
	public interface NUMBERS {
		String ZERO = "0";
		String ONE = "1";
		String TWO = "2";
		String THREE = "3";
		String FOUR = "4";
		String FIVE = "5";
	}
	public interface COMMONMSTTABLE{
		String COMMONCODE_STATUS = "STATUS";
		String COMMONCODE_SALUTATION = "SALUTATION";
		String COMMONCODE_GENDER = "GENDER";
		String LOAN_ADVANCE = "LNA";
		String GPFLOAN_ADVANCE = "GPFLOAN";
		String COMPUTER_ADVANCE = "CA";
		String FESTIVAL_ADVANCE = "FA";
		String PAYCOMMISION = "PAYCOMM";
		String VEHICLE_ADVANCE = "VA";
		String MOTOR_VEHICLE_ADVANCE = "MOTORVEHADV";
		String HOUSE_ADVANCE = "HBA";
		String EMPLOYEE_TYPE = "EMPLOYEE_TYPE";
		String BILL_TYPE = "PAYBILL_TYPE";
		String CADRE_GROUP = "CADRE_GROUP";
		String ADVANCE_PURPOSE = "PURPOSE_OF_WITHDRAWL";
		String DCPS_OFFICE_CLASS = "DCPS_OFFICE_CLASS";
		String COMMONCODE_GIS = "GIS";
		String PENSION_BILL = "PENSION_BILL";
		String EMPSERVICEENDDTREASONS = "EMP_SERVICE_END_DT_REASONS";
		String HEADOFOFFICECODE = "10001198220";
		String COMMONCODE_SALUTATIONS = "Salutation";
	}
	
	public interface PAYBILLDETAILS{
		
		// Group A B BnGz Values
		Double COMMONCODE_GROUP_A = (double) 960;
		Double COMMONCODE_GROUP_B = (double) 480;
		Double COMMONCODE_GROUP_BNGZ = (double) 480;
		Double COMMONCODE_GROUP_C = (double) 360;
		Double COMMONCODE_GROUP_D = (double) 240;
		
		// All PT Values
				Double COMMONCODE_PT_AMOUNT_LESS_THAN_4999 = (double) 4999;
				Double COMMONCODE_PT_AMOUNT_LESS_THAN_9999 = (double) 9999;
				Double COMMONCODE_PT_AMOUNT_GREATER_THAN_9999 = (double) 9999;
				
		
		// Group A B BnGz C D
		String COMMONCODE_GROUP_GROUP_A = "A";
		String COMMONCODE_GROUP_GROUP_B = "B";
		String COMMONCODE_GROUP_GROUP_BNGZ = "BnGz";
		String COMMONCODE_GROUP_GROUP_C = "C";
		String COMMONCODE_GROUP_GROUP_D = "D";
		
		// DA Rate Six And Seven 
		Double COMMONCODE_SEVENTH_PAY_DA = (double) 10;
		Double COMMONCODE_SIXTH_PAY_DA = (double) 9;
		
		//Added by Brijoy 13012021 PayCommission
		int COMMONCODE_PAYCOMMISSION_6PC = 2;
		int COMMONCODE_PAYCOMMISSION_5PC = 1;
		int COMMONCODE_PAYCOMMISSION_7PC = 8;
		
		// City Wise X Y Z 
		String COMMONCODE_CITY_CLASS_X = "X";
		String COMMONCODE_CITY_CLASS_Y = "Y";
		String COMMONCODE_CITY_CLASS_Z = "Z";
		
		// All Component 
		String COMMONCODE_COMPONENT_DA = "Dearness Allowance (D.A)";
		String COMMONCODE_COMPONENT_SVN_PC_DA = "SVN_PC_DA";
		String COMMONCODE_COMPONENT_GIS = "GIS";
		String COMMONCODE_COMPONENT_GIS_ZP = "GIS_ZP";
		String COMMONCODE_COMPONENT_HRA = "HRA"; 
		String COMMONCODE_COMPONENT_HRA5th = "HRA5th"; 
		String COMMONCODE_COMPONENT_HRA6th = "HRA6th"; 
		String COMMONCODE_COMPONENT_DCPS_EMPR = "DCPS_EMPR"; 
		String COMMONCODE_COMPONENT_PT = "PT"; 
		String COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR = "ADJUST_DCPS_EMPLOYER";  //Adjust_DCPS_EMPR M Compile
		String COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR = "Emp_DCPS_DA_ARR"; 
		String COMMONCODE_COMPONENT_Emp_DCPS_DELAY = "Emp_DCPS_DELAY"; 
		String COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR = "Emp_DCPS_PAY_ARR"; 
		String COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY = "Emp_DCPS_REGULAR_RECOVERY"; 
		String COMMONCODE_COMPONENT_DCPS_ARR = "DCPS_ARR"; 
		String COMMONCODE_COMPONENT_GPF_GRP_ABC = "GPF_GRP_ABC"; 
		String COMMONCODE_COMPONENT_GPF_GRP_D = "GPF_GRP_D"; 
		String COMMONCODE_COMPONENT_SVN_DA ="SVN_PC_DA";
		String COMMONCODE_COMPONENT_DA_on_TA="DA_on_TA";
		String COMMONCODE_COMPONENT_DCPS_EMPLOYER = "DCPS_EMPLOYER"; 
		String COMMONCODE_COMPONENT_DCPS_REGULAR_RECOVERY ="DCPS_REGULAR_RECOVERY";
		String COMMONCODE_COMPONENT_GPF_ABC_ARR = "GPF_ABC_ARR";
		String COMMONCODE_COMPONENT_TRIBAL_ALLOW = "TRIBAL_ALLOW"; //Tribal_Allowance
		String COMMONCODE_COMPONENT_TRANS_ALLOW_ARR = "TRANS_ALLOW_ARR";
		String COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE = "TA";
		String COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th = "TA5th";
		String COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE6th = "TA6th";
		String COMMONCODE_COMPONENT_GRP_ACC_POLICY = "GROUP_ACC_POLICY";
		String COMMONCODE_COMPONENT_GPF_D_ARR = "GPF_D_ARR";
		String COMMONCODE_COMPONENT_CONTRI_PROV_FUND = "CONTRI_PROV_FUND";
		String COMMONCODE_COMPONENT_DA_ARR = "DA_ARR";
		String COMMONCODE_COMPONENT_INCOME_TAX = "INCOME_TAX";
		String COMMONCODE_COMPONENT_ADD_HRA = "ADD_HRA";
		String COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW = "NAXAL_AREA_ALLOW";
		String COMMONCODE_COMPONENT_CO_HSG_SOC = "CO_HSG_SOC";
		String COMMONCODE_COMPONENT_CREDIT_SOC = "CREDIT_SOC";
		String COMMONCODE_COMPONENT_LIC = "LIC";
		String COMMONCODE_COMPONENT_OTHER_REC = "OTHER_REC";
		String COMMONCODE_COMPONENT_PT_ARR = "PT_ARR";
		String COMMONCODE_COMPONENT_SPECIAL_PAY = "SPECIAL_PAY";
		String COMMONCODE_COMPONENT_PERSONAL_PAY = "PERSONAL_PAY";
		String COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW = "SPCL_DUTY_ALLOW";
		String COMMONCODE_COMPONENT_RECURRING_DEP = "RECURRING_DEP";
		String COMMONCODE_COMPONENT_COP_Bank = "COP_Bank";
		String COMMONCODE_COMPONENT_OTHER_DEDUCT = "OTHER_DEDUCT";
		String COMMONCODE_COMPONENT_OTHER_ALLOW = "OTHER_ALLOW";
		String COMMONCODE_COMPONENT_HRR = "HRR";
		String COMMONCODE_COMPONENT_BASIC_ARR = "BASIC_ARR";
		String COMMONCODE_COMPONENT_CLA = "CLA";
		String COMMONCODE_COMPONENT_DEARNESS_PAY = "DEARNESS_PAY";
		String COMMONCODE_COMPONENT_CONVEY_ALLOW = "CONVEY_ALLOW";
		String COMMONCODE_COMPONENT_SERVICE_CHARGE = "SERV_CHARG";
		String COMMONCODE_COMPONENT_OTHER_DEDUCTION = "OTHER_DEDUCTION";
		String COMMONCODE_COMPONENT_MISC = "MISC";
		String COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC = "GPF_ADV_GRP_ABC";
		String COMMONCODE_COMPONENT_GPF_ADV_GRP_D = "GPF_ADV_GRP_D";
		String COMMONCODE_COMPONENT_EXC_PAYRC= "EXC_PAYRC";
		String COMMONCODE_COMPONENT_Revenue_Stamp= "Revenue_Stamp";
		String COMMONCODE_COMPONENT_Excess_payment= "Excess_payment";
		String COMMONCODE_COMPONENT_CM_Fund_AC_INS= "CM_Fund_AC_INS";
		String COMMONCODE_COMPONENT_NPS_EMP_CONTRI= "NPS_EMP_CONTRI";
		String COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT= "NPS_EMPR_DEDUCT";
		String COMMONCODE_COMPONENT_NPS_EMPR_ALLOW= "NPS_EMPR_ALLOW";
		String COMMONCODE_PAY_AND_ALLOWANCES_ARREARS= "PAY_AND_ALLOWANCES_ARREARS";
		String COMMONCODE_COMPONENT_COMP_ADV= "COMP_ADV";
		String COMMONCODE_COMPONENT_HBA_HOUSE= "HBA_HOUSE";
		String COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE= "Motor_vehicle_advance";
		
		
		
		String COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT= "HBA_HOUSE_INT_AMT";
		String COMMONCODE_COMPONENT_Motor_Veh_Adv_Inst= "Motor_Veh_Adv_Inst_Amt";
		String COMMONCODE_COMPONENT_OTHER_VEH_ADV= "OTHER_REC";
		String COMMONCODE_COMPONENT_FA= "FA";
		String COMMONCODE_COMPONENT_GPFAdvance= "GPF_Advance";
		String COMMONCODE_COMPONENT_GPFAdvanceII= "GPF_Advance_II";
		
		String COMMONCODE_COMPONENT_Employer_DCPS_DA_Arrears = "Employer_DCPS_DA_Arrears"; 
		String COMMONCODE_COMPONENT_Employer_DCPS_Delayed_Rec = "Employer_DCPS_Delayed_Rec"; 
		String COMMONCODE_COMPONENT_Employer_DCPS_Pay_Arrears = "Employer_DCPS_Pay_Arrears"; 
		String COMMONCODE_COMPONENT_Employer_DCPS_Regular_Rec = "Employer_DCPS_Regular_Rec"; 
		String COMMONCODE_COMPONENT_Arrears = "Arrears"; 
		String COMMONCODE_COMPONENT_PENS_Other_Arrears = "Other_Arrears"; 
		String COMMONCODE_COMPONENT_Deputation_Allow = "Deputation_Allow"; 
		String COMMONCODE_COMPONENT_Overtime_Allowance = "OTA"; 
		String COMMONCODE_COMPONENT_Hill_Station_Allowances = "Hill_Station_Allow"; 
		String COMMONCODE_COMPONENT_Tracer_Allowances = "Tracer_Allow"; 
		String COMMONCODE_COMPONENT_Naksalied_Allowances = "Naksalied_Allow"; 
		String COMMONCODE_COMPONENT_Washing_Allowance = "WA"; 
		String COMMONCODE_COMPONENT_GPF_Subscription = "GPF_Subscription"; 
		String COMMONCODE_COMPONENT_HBA = "HBA"; 
		String COMMONCODE_COMPONENT_Society_Or_Bank_Loan = "Society_Or_Bank_Loan"; 
		String COMMONCODE_COMPONENT_BLWF = "BLWF"; 
		String COMMONCODE_COMPONENT_NDCPS_Subscription = "NDCPS_Subscription"; 
		String COMMONCODE_COMPONENT_GPF_Arrears = "GPF_Arrears"; 
		String COMMONCODE_COMPONENT_GPF_Special_Arrears = "GPF_Special_Arr"; 
		String COMMONCODE_COMPONENT_BEGIS = "BEGIS"; 
		String COMMONCODE_COMPONENT_Allied_Soc = "Allied_Soc"; 
		String COMMONCODE_COMPONENT_Mantralaya_Soci = "Mantralaya_Soci"; 
		String COMMONCODE_COMPONENT_Chiplun_Soc = "Chiplun_Soc";
		String COMMONCODE_COMPONENT_Ulhasnagar_Soc = "Ulhasnagar_Soc";
		String COMMONCODE_COMPONENT_Engr_Soc = "Engr_Soc";
		String COMMONCODE_COMPONENT_GPF_DA_Sub = "GPF_DA_Sub";
		String COMMONCODE_COMPONENT_ROP = "ROP";
		String COMMONCODE_COMPONENT_Pay_Fix_Diff = "Pay_Fix_Diff";
		String COMMONCODE_COMPONENT_NPS = "NPS";
		String COMMONCODE_COMPONENT_Public_Health_Works = "Public_Health_Works";
		String COMMONCODE_COMPONENT_Sindhudurg_Oras = "Sindhudurg_Oras";
		String COMMONCODE_COMPONENT_Jalgaon_Society = "Jalgaon_Society";
		String COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal = "Manahar_bhai_Meh_Jal";
		String COMMONCODE_COMPONENT_Akola_Pari_Abhiyani = "Akola_Pari_Abhiyani";
		String COMMONCODE_COMPONENT_ZP_Karmchari_Pat = "ZP_Karmchari_Pat";
		String COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn = "Vidharbha_Gramin_Kokan_Bn";
		String COMMONCODE_COMPONENT_Chanda_Soc = "Chanda_Soc";
		String COMMONCODE_COMPONENT_Jalseva_Soc_Nag = "Jalseva_Soc_Nag";
		String COMMONCODE_COMPONENT_Bhandara_Soc = "Bhandara_Soc";
		String COMMONCODE_COMPONENT_GDCC_BANK = "GDCC_BANK";
		String COMMONCODE_COMPONENT_Gondia_Soc = "Gondia_Soc";
		String COMMONCODE_COMPONENT_Nagpur_Soc = "Nagpur_Soc";
		String COMMONCODE_COMPONENT_Allahabad_Soc = "Allahabad_Soc";
		String COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk = "Bhan_Dist_Cent_Cop_bnk";
		String COMMONCODE_COMPONENT_Bank_of_Barora = "Bank_of_Barora";
		String COMMONCODE_COMPONENT_Court_Computation = "Court_Computation";
		String COMMONCODE_COMPONENT_Jalgaon_GS_Soc = "Jalgaon_GS_Soc";
		String COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci = "Jalgaon_Handicap_Soci";
		String COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank = "Dhule_Nandurbar_Bank";
		String COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik = "Parisar_Abhi_Soc_Nashik";
		String COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule = "Sarw_Aroy_Ban_Soci_Dhule";
		String COMMONCODE_COMPONENT_Jaldhara_Soc_CL3 = "Jaldhara_Soc_CL3";
		String COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4 = "Panipurvtha_Soc_Cl3Or4";
		String COMMONCODE_COMPONENT_Govt_Bank = "Govt_Bank";
		String COMMONCODE_COMPONENT_Sangli_Sal_Soc = "Sangli_Sal_Soc";
		String COMMONCODE_COMPONENT_MJP_Soc = "MJP_Soc";
		String COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4 = "Nashik_Road_Soc_CL3Or4";
		String COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3 = "Jalseva_MAlegaon_Soc_CL3";
		String COMMONCODE_COMPONENT_Nashik_Bank_Soc = "Nashik_Bank_Soc";
		String COMMONCODE_COMPONENT_Manda_Nashik_Soc = "Manda_Nashik_Soc";
		String COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand = "Ujwala_Mahila_Pat_Bhand";
		String COMMONCODE_COMPONENT_BC_Quarter = "BC_Quarter";
		String COMMONCODE_COMPONENT_Excess_Pay_Rec = "Excess_Pay_Rec";
		String COMMONCODE_COMPONENT_Flag_Day = "Flag_Day";
		String COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat = "Bhand_Jil_Abhi_Karm_Pat";
		String COMMONCODE_COMPONENT_Jalseva_karm_saha_Path = "Jalseva_karm_saha_Path";
		String COMMONCODE_COMPONENT_Society_Nanded = "Society_Nanded";
		String COMMONCODE_COMPONENT_Society_Aurangabad = "Society_Aurangabad";
		String COMMONCODE_COMPONENT_Society_Latur = "Society_Latur";
		String COMMONCODE_COMPONENT_MLWF_OnlyMJP = "MLWF_OnlyMJP";
		String COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund = "Maha_Lab_Welfare_Fund";
		String COMMONCODE_COMPONENT_MJP_Soc_Latur = "MJP_Soc_Latur";
		String COMMONCODE_COMPONENT_JalBhavan_Society = "Jal_Bhavan_Society";
		String COMMONCODE_COMPONENT_MJP_Soc_Solapur = "MJP_Soc_Solapur";
		String COMMONCODE_COMPONENT_GPF_INST = "GPF_INST";
		String COMMONCODE_COMPONENT_Commutations = "Commutation";
		String COMMONCODE_COMPONENT_Gratuity = "Gratuity";
		String COMMONCODE_COMPONENT_Gratuity_Recovery = "Gratuity_Recovery";
		String COMMONCODE_COMPONENT_Recovery = "Recovery";
		String COMMONCODE_COMPONENT_Pension_Recovery = "Pension_Recovery";
		String COMMONCODE_COMPONENT_Pension_Comm = "Comm";
		String COMMONCODE_COMPONENT_Satara_Society = "Satara_Society";
		String COMMONCODE_COMPONENT_Bhagshree_Bank = "Bhagshree_Bank";
		String COMMONCODE_COMPONENT_Prasik_janata = "Parsik_Janata_Sh_Vasi";
		String COMMONCODE_COMPONENT_Rajashri_Shahu = "Rajashri_Shahu";
		String COMMONCODE_COMPONENT_license_fee = "License_Fee";
		String COMMONCODE_COMPONENT_PensDA =  "DA";
		String COMMONCODE_COMPONENT_PensDAArrears =  "DA_Arrears";
		String COMMONCODE_COMPONENT_PensBasicArrears =  "Basic_Arrears";
		String COMMONCODE_COMPONENT_GPFLoanREC =  "GPF_Loan_REC";
		String COMMONCODE_COMPONENT_LeavePay =  "Leave_Pay";
		String COMMONCODE_COMPONENT_VangaonSociety =  "Vangaon_Society";
		String COMMONCODE_COMPONENT_AccidentialPolicy =  "Accidential_Policy";
		String COMMONCODE_COMPONENT_panipuravtha_kolhapur =  "panipuravtha_kolhapur";
		String COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur =  "rajashrishahu_govbank_kolhapur";
		String COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya =  "Ahmednagar_pari_Abhiseva_Marya";
		String COMMONCODE_COMPONENT_MJP_Soc_Beed =  "MJP_Soc_Beed";
		String COMMONCODE_COMPONENT_jalbhavan_soc_sangli =  "jalbhavan_soc_sangli";
		String COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc =  "Hastantrit_pune_Mahan_soc";
		String COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara =  "Shaskiy_panipuravtha_soc_satara";
		String COMMONCODE_COMPONENT_Sal_owner_soc_Sangli =  "Sal_owner_soc_Sangli";
		String COMMONCODE_COMPONENT_yavatmal_society =  "yavatmal_society";
		String COMMONCODE_COMPONENT_nagari_sahakar_path_sansta =  "nagari_sahakar_path_sansta";
		String COMMONCODE_COMPONENT_akola_society =  "akola_society";
		String COMMONCODE_COMPONENT_engineering_society =  "engineering_society";
		String COMMONCODE_COMPONENT_daryapur_society =  "daryapur_society";
		String COMMONCODE_COMPONENT_public_health_society =  "public_health_society";
		String COMMONCODE_COMPONENT_jalpradaya_society =  "jalpradaya_society";
		String COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana =  "zilha_pari_karmachari_pantsanstha_buldhana";
		String COMMONCODE_COMPONENT_jalna_Soc = "Jalna_soc";
		String COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc = "amrawati_dist_engg_credit_soc";
		String COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK = "PUNE_DIST_CENTRAL_COP_BANK";
		String COMMONCODE_COMPONENT_NDCPS_REC = "NDCPS_REC";
		String COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA = "BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA";
		String COMMONCODE_COMPONENT_Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara = "bhandara_zilla_parishadwpanchayat_samiti_karamachari_sahakari";
		
		String COMMONCODE_COMPONENT_CVP1 =  "CVP1";
		String COMMONCODE_COMPONENT_CVP2 =  "CVP2";
		String COMMONCODE_COMPONENT_CVP3 =  "CVP3";
		
		
		
		// All PT Values
		Double COMMONCODE_PT_AMOUNT_LESS_THAN_4500 = (double) 4500;
		Double COMMONCODE_PT_AMOUNT_LESS_THAN_5500 = (double) 5500;
		Double COMMONCODE_PT_AMOUNT_GREATER_THAN_5500 = (double) 5500;
		Double COMMONCODE_PT_AMOUNT_200 = (double) 200;
		Double COMMONCODE_PT_AMOUNT_175 = (double) 175;
		Double COMMONCODE_PT_AMOUNT_275 = (double) 275;
		Double COMMONCODE_PT_AMOUNT_0 = (double) 0;
		Double COMMONCODE_PT_AMOUNT_300 = (double) 300;
		
		// Set Null Vaule
		String COMMONCODE_VALUE_NULL = "Null";
		int COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE = 200;
		
		// Percentage 100
		Double COMMONCODE_PERCENTAGE_100 = (double) 100;
		Double COMMONCODE_PERCENTAGE_50 = (double) 50;
		
		// GradePay Againt Travels Allowance
		
		Double COMMONCODE_GRADE_PAY_AMOUNT_GREATER_THAN_5400_FOR_CLASS_A1 = (double) 2400;
		Double COMMONCODE_GRADE_PAY_AMOUNT_LESS_THAN_5400_FOR_CLASS_A1 = (double) 1200;
		Double COMMONCODE_GRADE_PAY_AMOUNT_LESS_THAN_4400_FOR_CLASS_A1 = (double) 400;
		
		Double COMMONCODE_GRADE_PAY_AMOUNT_GREATER_THAN_5400_CLASS_A1_OTHER = (double) 1200;
		Double COMMONCODE_GRADE_PAY_AMOUNT_LESS_THAN_5400_CLASS_A1_OTHER = (double) 600;
		Double COMMONCODE_GRADE_PAY_AMOUNT_LESS_THAN_4400_CLASS_A1_OTHER = (double) 400;
		
		
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1300 = 1300;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1400 = 1400;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1600 = 1600;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1700= 1700;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1800 = 1800;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_1900 = 1900;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_2000 = 2000;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_2400 = 2400;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_2500 = 2500;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_2800 = 2800;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_3000 = 3000;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_3500 = 3500;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_4200 = 4200;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_4300 = 4300;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_4400 = 4400;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_4600 = 4600;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_4800 = 4800;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_5000 = 5000;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_5000_s19 = 5000;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_5400 = 5400;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_5500 = 5500;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_5800 = 5800;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_6600 = 6600;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_6900 = 6900;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_7600 = 7600;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_7900 = 7900;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_8700 = 8700;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_8800 = 8800;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_8900 = 8900;
		Integer COMMONCODE_GRADE_PAY_AMOUNT_10000 = 10000;

		
		///Added for Non-Computation and Non-government deduction components
		int COMMONCODE_COMPONENT_SVN_DA_CODE =1;
		int COMMONCODE_COMPONENT_ADD_PAY_CODE=2;
		int COMMONCODE_COMPONENT_BASIC_ARR_CODE=3;
		int COMMONCODE_COMPONENT_CA_CODE=5;
		int COMMONCODE_COMPONENT_CLA_CODE=4;
		int COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE=6;
		int COMMONCODE_COMPONENT_DA_ARR_CODE = 7;
		int COMMONCODE_DA_on_TA_CODE=8;
		int COMPONENT_DCPS_EMPLOYER_CODE=9;
		int COMMONCODE_COMPONENT_DA_CODE=10;
		int COMMONCODE_COMPONENT_DP_CODE=11;
		int COMMONCODE_COMPONENT_FPA_CODE=12;
		int COMMONCODE_COMPONENT_HA_CODE=13;
		int COMPONENT_COMPONENT_HRA_CODE=14;
		int COMMONCODE_COMPONENT_LF_CODE=15;
		int COMMONCODE_COMPONENT_NCA_CODE=16;
		int COMMONCODE_COMPONENT_NPA_CODE=17;
		int COMMONCODE_COMPONENT_OTA_CODE=18;
		int COMMONCODE_COMPONENT_PTA_CODE=19;
		int COMMONCODE_COMPONENT_PERSONAL_PAY_CODE = 20;
		int COMMONCODE_COMPONENT_SPECIAL_PAY_CODE = 76;
		int COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE_CODE = 22;
		int COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE = 23;
		int COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE = 24;
		int COMMONCODE_COMPONENT_UNIFORM_ALLOWANCE_CODE = 25;
		int COMMONCODE_COMPONENT_WASHING_ALLOWANCE_CODE = 26;
		int COMMONCODE_COMPONENT_DCPS_ARR_CODE = 27;
		int COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE = 28;
		int COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE = 29;
		int COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE = 30;
		int COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE = 31;
		int COMPONENT_ADJUST_DCPS_EMPR_CODE=32;
		int COMMONCODE_COMPONENT_GIS_CODE = 33;
		int COMMONCODE_COMPONENT_GIS_ZP_CODE = 34;
		int COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE = 35; 
		int COMMONCODE_COMPONENT_GPF_D_ARR_CODE = 36;
		int COMMONCODE_GPF_GRP_ABC_CODE = 37;
		int COMMONCODE_GPF_GRP_D_CODE = 38;
		int COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE = 39;
		int COMMONCODE_COMPONENT_HRR_ARREAR_CODE = 40;
		int COMMONCODE_COMPONENT_HRR_CODE = 41;
		int COMMONCODE_COMPONENT_INCOME_TAX_CODE = 42;
		int COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE = 43;
		int COMMONCODE_COMPONENT_OTHER_REC_CODE = 44;
		int COMMONCODE_COMPONENT_PT_ARR_CODE = 45;
		int COMPONENT_COMPONENT_PT_CODE=46;
		int COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE = 47;
		int COMMONCODE_COMPONENT_COP_Bank_CODE = 48;
		int COMMONCODE_COMPONENT_CREDIT_SOC_CODE = 49;
		int COMMONCODE_COMPONENT_LIC_CODE = 50;
		int COMMONCODE_COMPONENT_RECURRING_DEP_CODE = 52;
		int COMMONCODE_COMPONENT_CO_HSG_SOC_CODE = 53;
		int COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE = 65;
		int COMMONCODE_COMPONENT_OTHER_ALLOW_CODE = 66;
		int COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE = 67;
		int COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE = 68;
		int COMMONCODE_COMPONENT_ADD_HRA_CODE = 69;
		int COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE = 74;
		int COMMONCODE_COMPONENT_MISC_CODE = 71;
		int COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE = 58;
		int COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE = 59;
		int COMMONCODE_COMPONENT_EXC_PAYRC_CODE = 75;
		int COMMONCODE_COMPONENT_Revenue_Stamp_Code = 85;
		int COMMONCODE_COMPONENT_Excess_payment_Code = 86;
		int COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code = 87;
		int COMMONCODE_COMPONENT_NPS_EMP_CONTRI_Code = 83;
		int COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT_Code = 82;
		int COMMONCODE_COMPONENT_NPS_EMPR_ALLOW_Code = 95;
		int COMMONCODE_COMPONENT_HBA_HOUSE_Code = 60;
		int COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code = 202;
		int COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT_Code = 61;
		int COMMONCODE_COMPONENT_COMP_ADV_Code = 54;
		int COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code = 62;
		int COMMONCODE_COMPONENT_FA_Code = 57;
		int COMMONCODE_COMPONENT_GPFA_Code = 103;
		int COMMONCODE_COMPONENT_GPFAII_Code = 197;
		
		int COMMONCODE_COMPONENT_Employer_DCPS_DA_Arrears_Code = 91;
		int COMMONCODE_COMPONENT_Employer_DCPS_Delayed_Rec_Code = 92;
		int COMMONCODE_COMPONENT_Employer_DCPS_Pay_Arrears_Code = 93;
		int COMMONCODE_COMPONENT_Employer_DCPS_Regular_Rec_Code = 94;
		int COMMONCODE_COMPONENT_Arrears_Code = 113;
		int COMMONCODE_COMPONENT_Deputation_Allow_Code = 114;
		int COMMONCODE_COMPONENT_Overtime_Allowance_Code = 18;
		int COMMONCODE_COMPONENT_Hill_Station_Allowances_Code = 13;
		int COMMONCODE_COMPONENT_Tracer_Allowances_Code = 115;
		int COMMONCODE_COMPONENT_Naksalied_Allowances_Code = 116;
		int COMMONCODE_COMPONENT_Washing_Allowance_Code = 26;
		int COMMONCODE_COMPONENT_GPF_Subscription_Code = 102;
		int COMMONCODE_COMPONENT_HBA_Code = 117;
		int COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code = 108;
		int COMMONCODE_COMPONENT_BLWF_Code = 109;
		int COMMONCODE_COMPONENT_NDCPS_Subscription_Code = 110;
		int COMMONCODE_COMPONENT_GPF_Arrears_Code = 111;
		int COMMONCODE_COMPONENT_GPF_Special_Arrears_Code = 112;
		int COMMONCODE_COMPONENT_BEGIS_Code = 78;
		int COMMONCODE_COMPONENT_HRA6th_Code = 105;
		int COMMONCODE_COMPONENT_HRA5th_Code = 104;
		int COMMONCODE_COMPONENT_Allied_Soc_Code = 118;
		int COMMONCODE_COMPONENT_Mantralaya_Soci_Code = 119;
		int COMMONCODE_COMPONENT_Chiplun_Soc_Code = 120;
		int COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code = 121;
		int COMMONCODE_COMPONENT_Engr_Soc_Code = 122;
		int COMMONCODE_COMPONENT_GPF_DA_Sub_Code = 123;
		int COMMONCODE_COMPONENT_ROP_Code = 124;
		int COMMONCODE_COMPONENT_Pay_Fix_Diff_Code = 125;
		int COMMONCODE_COMPONENT_NPS_Code = 126;
		
		int COMMONCODE_COMPONENT_Public_Health_Works_Code = 127;
		int COMMONCODE_COMPONENT_Sindhudurg_Oras_Code = 128;
		int COMMONCODE_COMPONENT_Jalgaon_Society_Code = 129;
		int COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code = 130;
		int COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code = 131;
		int COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code = 132;
		int COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code = 133;
		int COMMONCODE_COMPONENT_Chanda_Soc_Code = 134;
		int COMMONCODE_COMPONENT_Bhagshree_Bank_Code = 135;
		int COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code = 136;
		int COMMONCODE_COMPONENT_Bhandara_Soc_Code = 137;
		int COMMONCODE_COMPONENT_GDCC_BANK_Code = 138;
		int COMMONCODE_COMPONENT_Gondia_Soc_Code = 139;
		int COMMONCODE_COMPONENT_Nagpur_Soc_Code = 140;
		int COMMONCODE_COMPONENT_Allahabad_Soc_Code = 141;
		int COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code = 142;
		int COMMONCODE_COMPONENT_Bank_of_Barora_Code = 143;
		int COMMONCODE_COMPONENT_Court_Computation_Code = 144;
		int COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code = 145;
		int COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code = 146;
		int COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code = 147;
		int COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code = 148;
		int COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code = 149;
		int COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code = 150;
		int COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code = 151;
		int COMMONCODE_COMPONENT_Govt_Bank_Code = 152;
		int COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code = 153;
		int COMMONCODE_COMPONENT_MJP_Soc_Code = 154;
		int COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code = 155;
		int COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code = 156;
		int COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code = 157;
		int COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code = 158;
		int COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code = 159;
		int COMMONCODE_COMPONENT_BC_Quarter_Code = 160;
		int COMMONCODE_COMPONENT_Excess_Pay_Rec_Code = 161;
		int COMMONCODE_COMPONENT_Flag_Day_Code = 162;
		int COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code = 163;
		int COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code = 164;
		int COMMONCODE_COMPONENT_Society_Nanded_Code = 165;
		int COMMONCODE_COMPONENT_Society_Aurangabad_Code = 166;
		int COMMONCODE_COMPONENT_Society_Latur_Code = 167;
		int COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code = 168;
		int COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code = 169;
		int COMMONCODE_COMPONENT_MJP_Soc_Latur_Code = 170;
		int COMMONCODE_COMPONENT_JalBhavan_Society_Code = 171;
		int COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code = 172;
		int COMMONCODE_COMPONENT_Satara_Society_Code = 173;
		int COMMONCODE_COMPONENT_Rajashri_Shahu_Code = 174;
		int COMMONCODE_COMPONENT_Prasik_janata_Code = 175;
		int COMMONCODE_COMPONENT_GPF_INST_Code = 90;
		int COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code = 106;
		int COMMONCODE_COMPONENT_PensCommutations_Code = 4;
		int COMMONCODE_COMPONENT_PensGratuity_Code = 12;
		int COMMONCODE_COMPONENT_PensGratuityRecovery_Code = 14;
		int COMMONCODE_COMPONENT_PensDA_Code = 1;
		int COMMONCODE_COMPONENT_PensPT_Code = 5;
		int COMMONCODE_COMPONENT_PensArrears_Code = 3;
//		int COMMONCODE_COMPONENT_PensDA_Arrears_Code = 6;
		int COMMONCODE_COMPONENT_Pens_OTHER_REC_CODE = 2;
		int COMMONCODE_COMPONENT_Pens_REC_CODE = 15;
		int COMMONCODE_COMPONENT_Pens_Other_Arrears_CODE = 16;
		int COMMONCODE_COMPONENT_Pens_Comm_Code = 17;
		int COMMONCODE_COMPONENT_Pens_INCOME_TAX_CODE = 6;
		int COMMONCODE_COMPONENT_Pens_Basic_Arrears = 7;
		int COMMONCODE_COMPONENT_Pens_DA_Arrears = 8;
		int COMMONCODE_COMPONENT_GPFLoanREC_Code = 89;
		int COMMONCODE_COMPONENT_LeavePay_Code = 100;
		int COMMONCODE_COMPONENT_VangaonSociety_Code = 176;
		int COMMONCODE_COMPONENT_AccidentialPolicy_Code = 177;
		int COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code = 178;
		int COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur_Code = 179;
		int COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya_Code = 180;
		int COMMONCODE_COMPONENT_MJP_Soc_Beed_Code = 181;
		int COMMONCODE_COMPONENT_jalbhavan_soc_sangli_Code =  183;
		int COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc_Code = 184 ;
		int COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara_Code =  185;
		int COMMONCODE_COMPONENT_Sal_owner_soc_Sangli_Code = 182;
		int COMMONCODE_COMPONENT_Recovery_Code = 193;
		int COMMONCODE_COMPONENT_akola_society_Code = 190;
		int COMMONCODE_COMPONENT_yavatmal_society_Code = 192;
		int COMMONCODE_COMPONENT_nagari_sahakar_path_sansta_Code = 191;
		int COMMONCODE_COMPONENT_engineering_society_Code = 189;
		int COMMONCODE_COMPONENT_daryapur_society_Code  = 188;
		int COMMONCODE_COMPONENT_public_health_society_Code  = 187;
		int COMMONCODE_COMPONENT_jalpradaya_society_Code  = 186;
		int COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana_Code  = 194;
		int COMMONCODE_COMPONENT_jalna_Soc_Code = 195;
		int COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc_Code = 196;
		int COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK_Code = 198;
		int COMMONCODE_COMPONENT_NDCPS_REC_Code = 199;
		int COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code = 201;
		///int COMMONCODE_COMPONENT_Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara_Code = 163;

		
		
		//pension allowdeduct com ponent code
		
		int COMPONENT_PENSION_DA=1;
		int COMPONENT_PENSION_RECOVERY=2;
		int COMPONENT_PENSION_ARREARS=3;
		int COMPONENT_PENSION_COMMUTATION=4;
		int COMPONENT_PENSION_PT=5;
		int COMPONENT_PENSION_INCOME_TAX=6;
		int COMPONENT_PENSION_BASIC_ARREARS=7;
		int COMPONENT_PENSION_DA_ARREARS=8;
		int COMPONENT_PENSION_CVP1=9;
		int COMPONENT_PENSION_CVP2=10;
		int COMPONENT_PENSION_CVP3=11;
		
		
		
	}
	
	public interface PostType {
		int Permanent = 1;
		int Temporary = 2;
	}
	
}
