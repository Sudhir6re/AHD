package com.mahait.gov.in.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.PaybillGenerationTrnDetails;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.entity.PaybillStatusEntity;
import com.mahait.gov.in.model.PaybillHeadMpgModel;
import com.mahait.gov.in.repository.PaybillGenerationTrnRepo;

@Service
@Transactional
public class PaybillGenerationTrnServiceImpl implements PaybillGenerationTrnService {

	@Autowired
	PaybillGenerationTrnRepo paybillHeadMpgRepo;

	@Autowired
	MstEmployeeService mstEmployeeService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@SuppressWarnings("unused")
	@Override
	public Long savePaybillHeadMpg(PaybillHeadMpgModel paybillHeadMpgModel) {
		PaybillGenerationTrnEntity objEntity = new PaybillGenerationTrnEntity();
		PaybillGenerationTrnDetails hr = new PaybillGenerationTrnDetails();
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		Double grossAmt = 0d;
		Double netAmt = 0d;
		Double da = 0d;
		Double hra = 0d;
		Double hra6th = 0d;
		Double hra5th = 0d;
		Double pt = 0d;
		Double dcps_empr = 0d;
		Double dcps_empr1 = 0d;// Added for testing nps
		Double grossAdjust = 0d;
		Double dedAdjust = 0d;
		Double adjust_dcps_empr = 0d;
		Double adjust_dcps_empr1 = 0d;// Added for testing nps
		Double dcpsDelay = 0d;
		Double dcpsDa = 0d;
		Double dcpsPayArr = 0d;
		Double dcpsArr = 0d;
		Double dcpsReg = 0d;
		Double gisAmount = 0d;
		/* Double gradePay = 0d; */
		Double gpfGrpD = 0d;
		Double GpfAbcArr = 0d;
		Double gpfGrpABC = 0d;
		Double ta = 0d;
		Double ta6th = 0d;
		Double ta5th = 0d;
		Double tribalAllow = 0d;
		Double transAllowArr = 0d;
		Double groupAccPolicy = 0d;
		Double gpfDArr = 0d;
		Double DaArr = 0d;
		Double addHRA = 0d;
		Double naxalAreaAllow = 0d;
		Double specialPay = 0d;
		Double otherAllow = 0d;
		Double spclDutyAllow = 0d;
		Double hrr = 0d;
		Double personalPay = 0d;
		Double otherDeduc = 0d;
		Double contriProvFund = 0d;
		Double basicArr = 0d;
		Double cla = 0d;
		Double dearnessPay = 0d;
		Double conveyAllow = 0d;
		Double DaOnTA = 0d;
		Double ptArr = 0d;
		Double GisZp = 0d;
		Double otherRec = 0d;
		Double it = 0d;
		Double ServCharge = 0d;
		Double CopBank = 0d;
		Double RecurringDep = 0d;
		Double lic = 0d;
		Double CreditSoc = 0d;
		Double svnDA = 0d;
		Double othrded = 0d;
		Double coHsg = 0d;
		Double GpfABC = 0d;
		Double GpfAdvDLoan = 0d;
		Double misc = 0d;
		int gradePay = 0;
		int day = 0;
		int monthday = 0;
		Double excPayRc = 0d;
		Double SevenPcBasic = 0d;
		Double SixPcBasic = 0d;
		Double revenueStamp = 0d;
		Double excessPayment = 0d;
		Double cmFund = 0d;
		Double npsEmprAllow = 0d;
		Double npsEmprContri = 0d;
		Double npsEmpContri = 0d;
		Double HouseAdv = 0d;
		Double HouseAdvInstAmt = 0d;
		Double CA = 0d;
		Double FA = 0d;
		Double vehAdv = 0d;
		Double emprDcpsDaArr = 0d;
		Double emprDcpsDelayedRec = 0d;
		Double emprdcpsDelay = 0d;
		Double emprDcpsPayArr = 0d;
		Double emprDcpsReg = 0d;
		Double emprDcpsRegRec = 0d;
		Double empDcpsDaArr = 0d;
		Double empDcpsDelay = 0d;
		Double empDcpsPayArr = 0d;
		Double empDcpsReg = 0d;
		Double gpfAdvance = 0d;
		Double gpfAdvanceII = 0d;
		String citygroup = null;
		String gpfAdvInstII = null;
		Double deputAllow = 0d;
		Double ota = 0d;
		Double hillStatAllow = 0d;
		Double tracerAllow = 0d;
		Double naksaliedAllow = 0d;
		Double wa = 0d;
		Double gpfSubscrb = 0d;
		Double hba = 0d;
		Double socOrBankLoan = 0d;
		Double BLWF = 0d;
		Double GpfArrears = 0d;
		Double GpfSpclArrears = 0d;
		Double NDCPSsubscrp = 0d;
		Double beigs = 0d;
		Double alliedSoc = 0d;
		Double mantralayaSoci = 0d;
		Double chiplunSoc = 0d;
		Double ulhasnagarSoc = 0d;
		Double engrSoc = 0d;
		Double gpfDaSub = 0d;
		Double rop = 0d;
		Double payFixDiff = 0d;
		Double nps = 0d;
		Double empContri = 0d;
		Double emprContri = 0d;
		Double gpfInst = 0d;
		Double arrears = 0d;
		String faInst = null;
		String otherVehAdvInst = null;
		String hbaHouseInst = null;
		String excessPayrecInst = null;
		String caInst = null;
		String gpfAdvInst = null;
		Double begis = 0d;
		Double sataraSoci = 0d;
		Double rajashriShahu = 0d;
		Double Parsik_Janata_Sh_Vasi = 0d;
		Double license = 0d;
		Double BhagshreeBank = 0d;
		String payfixAdvInst = null;
		Double payfixAdv = 0d;
		Double leavePay = 0d;
		Double gpfLoanRec = 0d;
		Double vangaonSoc = 0d;
		Double accidentPolicy = 0d;
		Double panipuravthaKolhapur = 0d;
		Double rajshriGovbankKolhapur = 0d;
		Double ahdPariAbhiMarya = 0d;
		Double mjpSocBeed = 0d;
		Double salOwnSocSangli = 0d;
		Double jalbhavanSocSangli = 0d;
		Double hastantritpune = 0d;
		Double shaskiyPaniPurvSocSatara = 0d;
		Double recovery = 0d;
		Double akolaSoc = 0d;
		Double yavatmalSoc = 0d;
		Double nagSahaPatSansta = 0d;
		Double enggSoc = 0d;
		Double daryapurSoc = 0d;
		Double pubhealSoc = 0d;
		Double jalpradayaSoc = 0d;
		Double zilhaPariKarmPant = 0d;
		Double jalnaSoc = 0d;
		Double amrawatidistEnggCredSoc = 0d;
		Double puneDistCentCoBnk = 0d;
		Double ndcpsRec = 0d;
		Double payandallarr = 0d;
		Double bharatratnavishsolapur = 0d;
		Double motorCycleAdv = 0d;
		Double motorCycleAdvInst = 0d;
		Double motorCycleAdvIntAmt = 0d;
		String motorCycleInst = null;
		Double bhanZillaParWaPanchayatSamitiSanstha = 0d;

		/*
		 * MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
		 * .findAllMpgSchemeBillGroupbyParameter(paybillHeadMpgModel.
		 * getSchemeBillgroupId());
		 */
		//String splitddo[] = paybillHeadMpgModel.getDdoCode().split("_");
		String ddoCode = null;
		ddoCode =  paybillHeadMpgModel.getDdoCode();

		DdoOffice ddoScreenEntity = mstEmployeeService.findAllGroup(ddoCode.trim());
		citygroup = ddoScreenEntity.getDcpsDdoOfficeCityClass();
		String spilt[] = ddoScreenEntity.getDcpsDdoOfficeCityClass().split("-");

		citygroup =spilt[1];
		
			System.out.println("CityGroup"+citygroup);
		objEntity.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
		objEntity.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
		objEntity.setBillTypeId(paybillHeadMpgModel.getBillTypeId());
		objEntity.setCreatedUserId(paybillHeadMpgModel.getCreatedUserId());
		/*
		 * objEntity.setSchemeBillgroupId(paybillHeadMpgModel.getSchemeBillgroupId());
		 */
		objEntity.setSchemeBillgroupId(paybillHeadMpgModel.getSchemeBillgroupId());
		objEntity.setCreatedDate(new Date());
		objEntity.setBillcreationDate(new Date());
		objEntity.setIsActive(5);
		objEntity.setDdoCode(ddoCode);
		objEntity.setNoOfEmployee(paybillHeadMpgModel.getNoOfEmployee());

		List<MstEmployeeEntity> mstEmployeeEntity = null;
		mstEmployeeEntity = mstEmployeeService.findAllWorkingEmployeeByDDOCodeAndBillGroup(
				paybillHeadMpgModel.getDdoCode(), paybillHeadMpgModel.getSchemeBillgroupId(),
				paybillHeadMpgModel.getPaybillMonth(), paybillHeadMpgModel.getPaybillYear());
		/// }

		Long val = paybillHeadMpgRepo.getPaybillGenerationTrnId() + 1;

		paybillStatusEntity.setBillNo(val);
		paybillStatusEntity.setCreatedDate(new Date());
		paybillStatusEntity.setIsActive(1);
		paybillStatusEntity.setUserId(paybillHeadMpgModel.getCreatedUserId());
		paybillStatusEntity.setMacId(paybillHeadMpgModel.getMacId());

		for (MstEmployeeEntity mstEmployeeEntity2 : mstEmployeeEntity) {

			int count = paybillHeadMpgRepo.isBrokenPeriodEmpty(mstEmployeeEntity2.getSevaarthId().trim(),
					String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
					String.valueOf(paybillHeadMpgModel.getPaybillYear()));

			if (mstEmployeeEntity2.getSevaarthId().equals("MJPVSBM6601")) {
				System.out.println("-------------" + mstEmployeeEntity2.getSevaarthId());

			}

			if (count > 0) {

				// To Check Broken Period Exist or not
				Long payCommission = mstEmployeeEntity2.getPayCommissionCode();
				int percentage = 0;
				String percentageHRA = null;
				String startDate = null;

				int month2 = paybillHeadMpgModel.getPaybillMonth();
				int year2 = paybillHeadMpgModel.getPaybillYear();
				/*
				 * int month2 = month + 1; int year2 = year;
				 */
				if (month2 < 10) {
					startDate = String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
				} else {
					startDate = String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
				}

				if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
							mstEmployeeEntity2.getCityClass());

				} else {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
							mstEmployeeEntity2.getCityClass());
				}

				if (count > 0) {

					// START:Fetch Broken Period Allowance and Deduction Data
					Map hmAllowDeducCodeAndValues = new HashMap();
					int basicpay = 0;
					int netpay = 0;
					int allowdedcode = 0;
					List<Object[]> lstBrokenPeriodData = paybillHeadMpgRepo.getBrokenPeriodData(
							mstEmployeeEntity2.getSevaarthId().toString().trim(),
							String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
							String.valueOf(paybillHeadMpgModel.getPaybillYear()),
							paybillHeadMpgModel.getDdoCode().trim());
					for (Object[] objects : lstBrokenPeriodData) {
						allowdedcode = (int) objects[4];
						hmAllowDeducCodeAndValues.put(objects[4], objects[5]); // 4 code,5 amount,6 Name
						basicpay = ((BigInteger) objects[2]).intValue();
						netpay = ((BigInteger) objects[3]).intValue();
					}

					// END:Fetch Broken Period Allowance and Deduction Data

					PaybillGenerationTrnDetails paybillGenerationTrnDetails = new PaybillGenerationTrnDetails();
					// Fetching Broken Period Data

					paybillGenerationTrnDetails.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
					paybillGenerationTrnDetails.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
					////paybillGenerationTrnDetails.setSubDeptId(mstEmployeeEntity2.getSubDeptId());
					paybillGenerationTrnDetails.setDesgCode(mstEmployeeEntity2.getDesignationCode());


					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE) != null) {
						paybillGenerationTrnDetails.setPt((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE)).intValue());
						// grossAmount+=paybillGenerationTrnDetails.getSvnDA();
						pt = paybillGenerationTrnDetails.getPt();
					}

					String cityClass = mstEmployeeEntity2.getCityClass().toString();

					if (payCommission == 700005) {

						if (hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
							paybillGenerationTrnDetails.setHra((double) ((BigDecimal) hmAllowDeducCodeAndValues
									.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE)).intValue());
							// grossAmount+=paybillGenerationTrnDetails.getSvnDA();
							hra = paybillGenerationTrnDetails.getHra();

						}

					}

					else if (payCommission == 700016) {
						if (hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
							paybillGenerationTrnDetails.setHra((double) ((BigDecimal) hmAllowDeducCodeAndValues
									.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE)).intValue());
							// grossAmount+=paybillGenerationTrnDetails.getSvnDA();
							hra = paybillGenerationTrnDetails.getHra();
						}
					}
					paybillGenerationTrnDetails.setSevaarthId(mstEmployeeEntity2.getSevaarthId());
					if (percentageHRA != null) {
						paybillGenerationTrnDetails.setHraPercent(Integer.parseInt(percentageHRA));
					}
					paybillGenerationTrnDetails.setDaPercent(percentage);
					paybillGenerationTrnDetails.setBasicPay((double) basicpay);

					// Start:Add Broken Period

					double grossAmount = 0;
					double totalDeductionAmount = 0;
					double netAmount = 0;


					// Allowances Component Start###########################

					// 7PC DA
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE) != null) {
						paybillGenerationTrnDetails.setSvnDA((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE)).intValue());
						svnDA = paybillGenerationTrnDetails.getSvnDA();
					}
					// Additional Pay
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_PAY_CODE) != null) {
						paybillGenerationTrnDetails.setAddPay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_PAY_CODE)).intValue());
					}
					// Basic Arrear
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setBasicArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE)).intValue());
						basicArr = paybillGenerationTrnDetails.getBasicArr();
					}

					// CLA-5thPay
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) != null) {
						paybillGenerationTrnDetails.setCla((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE)).intValue());
						cla = paybillGenerationTrnDetails.getCla();
					}
					// Conveyance Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE) != null) {
						paybillGenerationTrnDetails.setConveyAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE))
										.intValue());
						conveyAllow = paybillGenerationTrnDetails.getConveyAllow();
					}
					// DA Arrears
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setDaArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE)).intValue());
						DaArr = paybillGenerationTrnDetails.getDaArr();
					}
					// DA On TA
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE) != null) {
						paybillGenerationTrnDetails.setDaOnTA((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE)).intValue());
						DaOnTA = paybillGenerationTrnDetails.getDaOnTA();
					}

					// Dearness Allowance (D.A)
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code) != null) {
						paybillGenerationTrnDetails.setHra6th((double) ((BigInteger) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code)).intValue());
						hra6th = paybillGenerationTrnDetails.getHra6th();
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE) != null) {
						paybillGenerationTrnDetails
								.setPayAndAllowancesArrears((double) ((BigDecimal) hmAllowDeducCodeAndValues
										.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE))
												.intValue());
						payandallarr = paybillGenerationTrnDetails.getPayAndAllowancesArrears();
					}

					/*if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE) != null) {
						paybillGenerationTrnDetails.setFamilyPlanAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE))
								.intValue());;
						da = paybillGenerationTrnDetails.getDa();
					}*/

					// DCPS Employer Contribution
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_DCPS_EMPLOYER_CODE) != null) {
						paybillGenerationTrnDetails.setDcpsEmployer((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_DCPS_EMPLOYER_CODE)).intValue());
					} else {
						if (paybillHeadMpgModel.getPaybillYear() >= 20 && paybillHeadMpgModel.getPaybillMonth() >= 8) {
							paybillGenerationTrnDetails.setDcpsEmployer((double) (basicpay + da + svnDA * 14 / 100));
						} else {
							paybillGenerationTrnDetails.setDcpsEmployer((double) (basicpay + da + svnDA * 10 / 100));
						}
					}
					// Dearness Pay
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE) != null) {
						paybillGenerationTrnDetails.setDearnessPay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE)).intValue());
						dearnessPay = paybillGenerationTrnDetails.getDearnessPay();
					}

					// Family Planning Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE) != null) {
						paybillGenerationTrnDetails.setFamilyPlanAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE)).intValue());
					}
					// Hilly Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HA_CODE) != null) {
						paybillGenerationTrnDetails.setHillStatAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HA_CODE)).intValue());
						grossAmount += paybillGenerationTrnDetails.getHillStatAllow();
					}
					// House Rent Allowance (H.R.A)
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
						paybillGenerationTrnDetails.setHra((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE)).intValue());
						hra = paybillGenerationTrnDetails.getHra();
					}
					// Non-Computational HRA
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NCA_CODE) != null) {
						paybillGenerationTrnDetails.setNonCompHRA((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NCA_CODE)).intValue());
						grossAmount += paybillGenerationTrnDetails.getNonCompHRA();

					}
					// Overtime Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTA_CODE) != null) {
						paybillGenerationTrnDetails.setOTA((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTA_CODE)).intValue());
					}
					// Non-Practising Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPA_CODE) != null) {
						paybillGenerationTrnDetails.setNonPractAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPA_CODE)).intValue());
						grossAmount += paybillGenerationTrnDetails.getNonPractAllow();
					}
					// Transport Allowance (T.A)
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE_CODE) != null) {
						paybillGenerationTrnDetails.setTa((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE_CODE))
										.intValue());
						ta = paybillGenerationTrnDetails.getTa();
					}
					// Transport Allowance Arrears
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setTransAllowArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE))
										.intValue());
						transAllowArr = paybillGenerationTrnDetails.getTransAllowArr();
					}
					// Tribal Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) != null) {
						paybillGenerationTrnDetails.setTribalAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE))
										.intValue());
						tribalAllow = paybillGenerationTrnDetails.getTribalAllow();
					}
					// Uniform Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_UNIFORM_ALLOWANCE_CODE) != null) {
						paybillGenerationTrnDetails.setUniformAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_UNIFORM_ALLOWANCE_CODE))
										.intValue());
					}
					// Washing Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_WASHING_ALLOWANCE_CODE) != null) {
						paybillGenerationTrnDetails.setWa((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_WASHING_ALLOWANCE_CODE))
										.intValue());
						grossAmount += paybillGenerationTrnDetails.getWa();
					}
					// Permanent Travelling Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PTA_CODE) != null) {
						paybillGenerationTrnDetails
								.setPermanentTravelAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
										.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PTA_CODE)).intValue());

					}
					// Personal Pay
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE) != null) {
						paybillGenerationTrnDetails.setPersonalPay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE))
										.intValue());
						personalPay = paybillGenerationTrnDetails.getPersonalPay();
					}
					// Naxal Area Allowance
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE) != null) {
						paybillGenerationTrnDetails.setNaxalAreaAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE))
										.intValue());
						grossAmount += paybillGenerationTrnDetails.getNaxalAreaAllow();
						naxalAreaAllow = paybillGenerationTrnDetails.getNaxalAreaAllow();
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI_Code) != null) {
						paybillGenerationTrnDetails
								.setNpsEmployeeContri((double) ((BigDecimal) hmAllowDeducCodeAndValues
										.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI_Code))
												.intValue());
						npsEmpContri = paybillGenerationTrnDetails.getNpsEmployeeContri();
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT_Code) != null) {
						paybillGenerationTrnDetails
								.setNpsEmployerDeduct((double) ((BigDecimal) hmAllowDeducCodeAndValues
										.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT_Code))
												.intValue());
						npsEmprContri = paybillGenerationTrnDetails.getNpsEmployerDeduct();
					}

					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW_Code) != null) {
						paybillGenerationTrnDetails.setNpsEmployerAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW_Code))
										.intValue());
						npsEmprAllow = paybillGenerationTrnDetails.getNpsEmployerAllow();
					}

					// Employer DCPS DA Arrears Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsDaArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE))
										.intValue());
						emprDcpsDaArr = paybillGenerationTrnDetails.getEmpDcpsDaArr();
						System.out.println("---------------" + emprDcpsDaArr);
					}
					// Employer DCPS Delayed Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsDelay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE))
										.intValue());
						emprdcpsDelay = paybillGenerationTrnDetails.getEmpDcpsDelay();
					}
					// Employer DCPS Pay Arrears Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsPayArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE))
										.intValue());
						emprDcpsPayArr = paybillGenerationTrnDetails.getEmpDcpsPayArr();
					}
					// Employer DCPS Regular Recovery
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE) != null) {
						paybillGenerationTrnDetails
								.setEmpDcpsRegularRecovery((double) ((BigDecimal) hmAllowDeducCodeAndValues.get(
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE))
												.intValue());
						emprDcpsReg = paybillGenerationTrnDetails.getEmpDcpsRegularRecovery();
					}

					// Naxal Area Allowance

					// Allowances Component End#############################

					// Deductions Component Start#############################

					// Adjustable DCPS Employer Contribution
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_ADJUST_DCPS_EMPR_CODE) != null) {
						paybillGenerationTrnDetails
								.setAdjustDcpsEmployer((double) ((BigDecimal) hmAllowDeducCodeAndValues
										.get(CommonConstants.PAYBILLDETAILS.COMPONENT_ADJUST_DCPS_EMPR_CODE))
												.intValue());
						adjust_dcps_empr = paybillGenerationTrnDetails.getAdjustDcpsEmployer();
					}
					// DCPS Arr
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setDcpsArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE)).intValue());
						dcpsArr = paybillGenerationTrnDetails.getDcpsArr();
					}
					// DCPS DA Arrears Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsDaArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE))
										.intValue());
						dcpsDa = paybillGenerationTrnDetails.getEmpDcpsDaArr();
					}
					// DCPS Delayed Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsDelay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE))
										.intValue());
						dcpsDelay = paybillGenerationTrnDetails.getEmpDcpsDelay();
					}
					// DCPS Pay Arrears Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setEmpDcpsPayArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE))
										.intValue());
						dcpsPayArr = paybillGenerationTrnDetails.getEmpDcpsPayArr();
					}
					// DCPS Regular Recovery
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE) != null) {
						paybillGenerationTrnDetails
								.setEmpDcpsRegularRecovery((double) ((BigDecimal) hmAllowDeducCodeAndValues.get(
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE))
												.intValue());
						dcpsReg = paybillGenerationTrnDetails.getEmpDcpsRegularRecovery();
					}
					// GIS
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_CODE) != null) {
						paybillGenerationTrnDetails.setGis((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_CODE)).intValue());
						gisAmount = paybillGenerationTrnDetails.getGis();
					}
					// GIS(ZP)
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE) != null) {
						paybillGenerationTrnDetails.setGisZp((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE)).intValue());
						GisZp = paybillGenerationTrnDetails.getGisZp();
					}
					// GPF_ABC Arrears
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setGpfAbcArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE)).intValue());
						GpfAbcArr = paybillGenerationTrnDetails.getGpfAbcArr();
					}
					// GPF_D_Arrears
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setGpfDArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE)).intValue());
						gpfDArr = paybillGenerationTrnDetails.getGpfDArr();
					}
					// GPF_GRP_ABC
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) != null) {
						paybillGenerationTrnDetails.setGpfGrpABC((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE)).intValue());
						gpfGrpABC = paybillGenerationTrnDetails.getGpfGrpABC();
					}
					// GPF_GRP_D
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) != null) {
						paybillGenerationTrnDetails.setGpfGrpD((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE)).intValue());
						gpfGrpD = paybillGenerationTrnDetails.getGpfGrpD();
					}
					// Group Acc. Policy
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) != null) {
						paybillGenerationTrnDetails.setGroupAccPolicy((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE))
										.intValue());
						groupAccPolicy = paybillGenerationTrnDetails.getGroupAccPolicy();
					}
					// House Rent Recovery ( H R R )
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE) != null) {
						paybillGenerationTrnDetails.setHrr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE)).intValue());
						hrr = paybillGenerationTrnDetails.getHrr();
					}
					// HRR Arrear
					// Income Tax( I .T )
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE) != null) {
						paybillGenerationTrnDetails.setIt((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE)).intValue());
						it = paybillGenerationTrnDetails.getIt();
					}
					// LIC
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) != null) {
						paybillGenerationTrnDetails.setLIC((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE)).intValue());
						lic = paybillGenerationTrnDetails.getLIC();
					}
					// GPF_INST
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_INST_Code) != null) {
						paybillGenerationTrnDetails.setGpfInst((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_INST_Code)).intValue());
						gpfInst = paybillGenerationTrnDetails.getGpfInst();
					}

					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Recovery_Code) != null) {
						paybillGenerationTrnDetails.setRecovery((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Recovery_Code)).intValue());
						recovery = paybillGenerationTrnDetails.getRecovery();
					}

					// Other Deductions
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE) != null) {
						paybillGenerationTrnDetails.setOtherDeduct((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE))
										.intValue());
						otherDeduc = paybillGenerationTrnDetails.getOtherDeduct();
					}
					// Other Recovery
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) != null) {
						paybillGenerationTrnDetails.setOtherRec((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE)).intValue());
						otherRec = paybillGenerationTrnDetails.getOtherRec();
					}

					// Profession Tax
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE) != null) {
						paybillGenerationTrnDetails.setPt((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE)).intValue());
						pt = paybillGenerationTrnDetails.getPt();
					}

					// PT Arrears
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE) != null) {
						paybillGenerationTrnDetails.setPtArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE)).intValue());
						ptArr = paybillGenerationTrnDetails.getPtArr();
					}

					// Service Charge
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE) != null) {
						paybillGenerationTrnDetails.setServCharge((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE))
										.intValue());
						ServCharge = paybillGenerationTrnDetails.getServCharge();
					}
					Double gpfadvGrpABC = 0d;
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE) != null) {

						paybillGenerationTrnDetails.setGpfAdvGrpAbc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE))
										.intValue());
						gpfadvGrpABC = paybillGenerationTrnDetails.getGpfAdvGrpAbc();
					}
					Double gpfadvGrpD = 0d;
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) != null) {

						paybillGenerationTrnDetails.setGpfAdvGrpD((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE))
										.intValue());
						gpfadvGrpD = paybillGenerationTrnDetails.getGpfAdvGrpD();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) != null) {

						paybillGenerationTrnDetails.setGpfAdvGrpD((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE))
										.intValue());
						gpfadvGrpD = paybillGenerationTrnDetails.getGpfAdvGrpD();

					}
					/*if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code) != null) {

						paybillGenerationTrnDetails.setHbaHouse((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code)).intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getHbaHouse();
						//// HouseAdv = paybillGenerationTrnDetails.getHbaHouse();

						LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity = mstEmployeeService
								.findHBADetails(mstEmployeeEntity2.getEmployeeId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);

						System.out.println("mstEmployeeEntity2.getEmployeeId()" + mstEmployeeEntity2.getEmployeeId());

						if (lnaHBAEmployeeRequestMstEntity != null
								&& lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) {
							if (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) {
								HouseAdv = lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();

								Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
								Integer sancHbaInst = lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment();
								hbaHouseInst = hbai.toString() + "/" + sancHbaInst.toString();

								paybillGenerationTrnDetails.setHbaHouseInst(hbaHouseInst);
								// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
							}

						}
					}*/
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code) != null) {

						paybillGenerationTrnDetails.setGpfAdvance((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code)).intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getHbaHouse();
						//// HouseAdv = paybillGenerationTrnDetails.getHbaHouse();

						LoanEmployeeDtlsEntity loandetails = mstEmployeeService.findGPFADetails(
								mstEmployeeEntity2.getSevaarthId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code);

						if (loandetails != null && loandetails.getLoanactivateflag() == 1) {
							if (loandetails.getLoanprinemiamt() != null) {
								paybillGenerationTrnDetails
										.setGpfAdvance((double) ((BigDecimal) hmAllowDeducCodeAndValues
												.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code))
														.intValue());
								gpfAdvance = paybillGenerationTrnDetails.getGpfAdvance();
								Integer gpfAdvi = loandetails.getTotalRecoveredInst() + 1;
								Integer sanGPFAdvns = loandetails.getLoanprininstno();
								gpfAdvInst = gpfAdvi.toString() + "/" + sanGPFAdvns.toString();
							}
							// GPF_ADVANCE
							gpfAdvance = (double) (Math.round(gpfAdvance));
							paybillGenerationTrnDetails.setGpfAdvInst(gpfAdvInst);

						} else {
							paybillGenerationTrnDetails.setGpfAdvance(0d);
							gpfAdvance = (double) 0;

						}
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code) != null) {

						paybillGenerationTrnDetails.setComputerAdv((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code)).intValue());
						CA = paybillGenerationTrnDetails.getComputerAdv();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Revenue_Stamp_Code) != null) {

						paybillGenerationTrnDetails.setRevenueStamp((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Revenue_Stamp_Code))
										.intValue());
						revenueStamp = paybillGenerationTrnDetails.getRevenueStamp();

					}
				/*	if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code) != null) {

						paybillGenerationTrnDetails.setOtherRec((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code))
										.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getOtherVehAdv();
						vehAdv = paybillGenerationTrnDetails.getOtherRec();

						LNAVAEmployeeRequestMstEntity lnaVAEmployeeRequestMstEntity = mstEmployeeService.findVADetails(
								mstEmployeeEntity2.getEmployeeId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code);

						System.out.println("mstEmployeeEntity2.getEmployeeId()" + mstEmployeeEntity2.getEmployeeId());

						if (lnaVAEmployeeRequestMstEntity != null && lnaVAEmployeeRequestMstEntity.getIsActive() == 1) {
							if (lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt() != null) {
								vehAdv = lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt();

								Integer vi = lnaVAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
								Integer sancVehInst = lnaVAEmployeeRequestMstEntity.getSancPrincipalInstallMCA();
								otherVehAdvInst = vi.toString() + "/" + sancVehInst.toString();
								paybillGenerationTrnDetails.setOthrVehAdvInst(otherVehAdvInst);
							}

						}
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code) != null) {

						paybillGenerationTrnDetails.setExcessPayrec((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code))
										.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getOtherVehAdv();
						excessPayrec = paybillGenerationTrnDetails.getExcessPayrec();

						ExcessPayRecoveryEntity excessPayRecoveryEntity = mstEmployeeService
								.findExcPayRec(mstEmployeeEntity2.getSevaarthId());

						if (excessPayRecoveryEntity != null && excessPayRecoveryEntity.getIsActive() == '1') {
							if (excessPayRecoveryEntity.getPrinEmiAmt() != null) {
								excessPayrec = excessPayRecoveryEntity.getPrinEmiAmt();

								Integer excInst = excessPayRecoveryEntity.getLoanPrinInstNo() + 1;
								Integer sancExcInst = excessPayRecoveryEntity.getTotalInstNo();
								excessPayrecInst = excInst.toString() + "/" + sancExcInst.toString();
								paybillGenerationTrnDetails.setExcessPayrecint(excessPayrecInst);
							}

						} else {
							excessPayrec = 0d;

						}
					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code) != null) {

						paybillGenerationTrnDetails.setFestivalAdv((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code)).intValue());
						totalDeductionAmount += paybillGenerationTrnDetails.getFestivalAdv();
						FA = paybillGenerationTrnDetails.getFestivalAdv();

						LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity = mstEmployeeService.findFADetails(
								mstEmployeeEntity2.getEmployeeId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);

						if (lnaFAEmployeeRequestMstEntity != null && lnaFAEmployeeRequestMstEntity.getIsActive() == 1) {
							if (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) {
								// FA = lnaFAEmployeeRequestMstEntity.getInstallmentAmount();

								Integer fi = lnaFAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
								Integer sancInst = lnaFAEmployeeRequestMstEntity.getSanctionedNoOfInstallment();
								faInst = fi.toString() + "/" + sancInst.toString();
							}
							paybillGenerationTrnDetails.setFaInst(faInst);
							paybillGenerationTrnDetails.setFestivalAdv(FA);

						}

					}*/
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code) != null) {

						paybillGenerationTrnDetails.setCmFund((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code))
										.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
						cmFund = paybillGenerationTrnDetails.getCmFund();

					}
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code) != null) {

						paybillGenerationTrnDetails.setTa5th((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code))
										.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
						ta5th = paybillGenerationTrnDetails.getTa5th();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code) != null) {

						paybillGenerationTrnDetails.setDeputationAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code))
										.intValue());
						deputAllow = paybillGenerationTrnDetails.getDeputationAllow();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code) != null) {

						paybillGenerationTrnDetails.setOTA((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code))
										.intValue());
						ota = paybillGenerationTrnDetails.getOTA();

					}
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code) != null) {

						paybillGenerationTrnDetails.setHillStatAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code))
										.intValue());
						hillStatAllow = paybillGenerationTrnDetails.getHillStatAllow();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code) != null) {

						paybillGenerationTrnDetails.setLeavePay((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code)).intValue());
						if (paybillGenerationTrnDetails.getLeavePay() != null) {
							leavePay = paybillGenerationTrnDetails.getLeavePay();
						} else {
							leavePay = 0d;
						}

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code) != null) {

						paybillGenerationTrnDetails.setTracerAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code))
										.intValue());
						tracerAllow = paybillGenerationTrnDetails.getTracerAllow();

					}
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code) != null) {

						paybillGenerationTrnDetails.setNaksaliedAllow((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code))
										.intValue());
						naksaliedAllow = paybillGenerationTrnDetails.getNaksaliedAllow();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance_Code) != null) {

						paybillGenerationTrnDetails.setWa((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance_Code))
										.intValue());
						wa = paybillGenerationTrnDetails.getWa(); /* paybillGenerationTrnDetails.getWa(); */

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code) != null) {

						paybillGenerationTrnDetails.setGpfSubscription((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code))
										.intValue());
						gpfSubscrb = paybillGenerationTrnDetails.getGpfSubscription();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code) != null) {

						paybillGenerationTrnDetails.setHba((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code)).intValue());
						hba = paybillGenerationTrnDetails.getHba();

					}
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code) != null) {

						paybillGenerationTrnDetails.setSocOrBankLoan((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code))
										.intValue());
						socOrBankLoan = paybillGenerationTrnDetails.getSocOrBankLoan();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code) != null) {

						paybillGenerationTrnDetails.setBLWF((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code)).intValue());
						BLWF = paybillGenerationTrnDetails.getBLWF();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) != null) {

						paybillGenerationTrnDetails.setGpfArrears((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code)).intValue());
						GpfArrears = paybillGenerationTrnDetails.getGpfArrears();

					}
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code) != null) {

						paybillGenerationTrnDetails.setGpfSpecialArr((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code))
										.intValue());
						GpfSpclArrears = paybillGenerationTrnDetails.getGpfSpecialArr();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code) != null) {

						paybillGenerationTrnDetails
								.setNDCPSSubscription((double) ((BigDecimal) hmAllowDeducCodeAndValues.get(
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code))
												.intValue());
						NDCPSsubscrp = paybillGenerationTrnDetails.getNDCPSSubscription();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code) != null) {

						paybillGenerationTrnDetails.setAlliedSoc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code)).intValue());
						alliedSoc = paybillGenerationTrnDetails.getAlliedSoc();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code) != null) {

						paybillGenerationTrnDetails.setMantralayaSoci((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code))
										.intValue());
						mantralayaSoci = paybillGenerationTrnDetails.getMantralayaSoci();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code) != null) {

						paybillGenerationTrnDetails.setChiplunSoc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code)).intValue());
						chiplunSoc = paybillGenerationTrnDetails.getChiplunSoc();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code) != null) {

						paybillGenerationTrnDetails.setUlhasnagarSoc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code))
										.intValue());
						ulhasnagarSoc = paybillGenerationTrnDetails.getUlhasnagarSoc();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code) != null) {

						paybillGenerationTrnDetails.setEngrSoc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code)).intValue());
						engrSoc = paybillGenerationTrnDetails.getEngrSoc();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code) != null) {

						paybillGenerationTrnDetails.setGpfDaSoc((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code)).intValue());
						gpfDaSub = paybillGenerationTrnDetails.getGpfDaSoc();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) != null) {

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code);
						if (employeeAllowDeducComponentAmtEntity != null) {

							paybillGenerationTrnDetails
									.setRop((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							rop = paybillGenerationTrnDetails.getRop();
						} else {
							rop = 0d;
						}

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code) != null) {

						paybillGenerationTrnDetails.setPayFixDiff((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code))
										.intValue());
						payFixDiff = paybillGenerationTrnDetails.getPayFixDiff();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code) != null) {

						paybillGenerationTrnDetails.setNps((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code)).intValue());
						nps = paybillGenerationTrnDetails.getNps();

					}
					
					if (hmAllowDeducCodeAndValues.get(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code) != null) {

						paybillGenerationTrnDetails.setTa5th((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code))
										.intValue());
						ta5th = paybillGenerationTrnDetails.getTa5th();

					}
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code) != null) {

						paybillGenerationTrnDetails.setArrears((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code)).intValue());
						arrears = paybillGenerationTrnDetails.getArrears();

					}



					/*if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT_Code) != null) { // COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE

						paybillGenerationTrnDetails.setHbaHouseIntAmt((double) ((BigDecimal) hmAllowDeducCodeAndValues
								.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT_Code))
										.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getHbaHouse();
						//// HouseAdv = paybillGenerationTrnDetails.getHbaHouse();

						LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity = mstEmployeeService
								.findHBADetails(mstEmployeeEntity2.getEmployeeId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT_Code);

						if (lnaHBAEmployeeRequestMstEntity != null
								&& lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) {
							if (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) {
								// HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth();
								HouseAdvInstAmt = lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();

								Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
								Integer sancHbaInst = lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment();
								hbaHouseInst = hbai.toString() + "/" + sancHbaInst.toString();
								paybillGenerationTrnDetails.setHbaHouseInst(hbaHouseInst);
								// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
							}

						}
					}*/

					/*if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code) != null) { //

						paybillGenerationTrnDetails
								.setMotorvehicleAdvance((double) ((BigDecimal) hmAllowDeducCodeAndValues.get(
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code))
												.intValue());
						// totalDeductionAmount+=paybillGenerationTrnDetails.getHbaHouse();
						//// HouseAdv = paybillGenerationTrnDetails.getHbaHouse();

						LNAMotorVehicleAdvEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity = mstEmployeeService
								.findmotorCycleAdvDetails(mstEmployeeEntity2.getEmployeeId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code);

						if (lnaHBAEmployeeRequestMstEntity != null
								&& lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) {
							if (lnaHBAEmployeeRequestMstEntity.getPrinInstallmentAmountMCA() != null) {
								// HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth();
								motorCycleAdv = lnaHBAEmployeeRequestMstEntity.getPrinInstallmentAmountMCA();

								Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
								Integer sancHbaInst = lnaHBAEmployeeRequestMstEntity.getSancPrincipalInstallMCA();
								hbaHouseInst = hbai.toString() + "/" + sancHbaInst.toString();
								paybillGenerationTrnDetails.setMotorVehAdvInst(hbaHouseInst);
								// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
							}

						}
					}*/

					// Deductions Component End#############################

					// End:Add Broken Period

					System.out.println("-----------checking data--------------" + arrears + specialPay + leavePay
							+ deputAllow + ta5th + ndcpsRec);

					grossAmount = (basicpay + hra + da + svnDA + dcps_empr + ta + tribalAllow + transAllowArr + DaArr
							+ naxalAreaAllow + basicArr + personalPay + cla + dearnessPay + conveyAllow + DaOnTA
							+ npsEmprAllow + emprDcpsDaArr + emprDcpsPayArr + hra6th + hra5th + ta6th + wa + deputAllow
							+ ota + hillStatAllow + tracerAllow + naksaliedAllow + GpfArrears + arrears + specialPay
							+ leavePay + deputAllow + ta5th + payandallarr);

					paybillGenerationTrnDetails.setGrossTotalAmt((double) Math.round(grossAmount));

					double dedByAG = gpfGrpABC + gpfGrpD + GpfAbcArr + gpfDArr + gisAmount;
					paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));
					System.out.println(gpfDaSub + rop + payFixDiff + nps + hrr + otherRec + lic + gpfInst);

					double dedByTreasury = GisZp + it + groupAccPolicy + pt  + npsEmpContri + npsEmprContri
							+ cmFund + socOrBankLoan + BLWF + NDCPSsubscrp + GpfSpclArrears + hba + begis + gpfSubscrb
							+ gpfDaSub + rop + payFixDiff + nps + hrr + otherRec + lic + gpfInst + ndcpsRec + recovery
							+ accidentPolicy + revenueStamp;

					paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

					System.out.println(CA + FA + HouseAdv + vehAdv + gpfAdvance + alliedSoc + mantralayaSoci
							+ chiplunSoc + ulhasnagarSoc + engrSoc + sataraSoci + rajashriShahu
							+ license + Parsik_Janata_Sh_Vasi + BhagshreeBank + ServCharge);

					// double dedByOthr = dedByTreasury + dedByAG ;
					double dedByOthr = CA + FA + HouseAdv + vehAdv + gpfAdvance + alliedSoc + mantralayaSoci
							+ chiplunSoc + ulhasnagarSoc + engrSoc + sataraSoci + rajashriShahu
							+ license + Parsik_Janata_Sh_Vasi + BhagshreeBank + hastantritpune + HouseAdvInstAmt
							+ ServCharge + vangaonSoc + panipuravthaKolhapur + ahdPariAbhiMarya + mjpSocBeed
							+ salOwnSocSangli + shaskiyPaniPurvSocSatara + jalbhavanSocSangli + akolaSoc + yavatmalSoc
							+ nagSahaPatSansta + enggSoc + daryapurSoc + pubhealSoc + jalpradayaSoc + zilhaPariKarmPant
							+ jalnaSoc + amrawatidistEnggCredSoc + puneDistCentCoBnk + bharatratnavishsolapur
							+ bhanZillaParWaPanchayatSamitiSanstha + motorCycleAdv;

					paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));

					double Totaldeduc = dedByAG + dedByTreasury + dedByOthr;

					paybillGenerationTrnDetails.setTotalDeduction((double) Math.round(Totaldeduc));

					double Net = grossAmount - Totaldeduc;
					paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));

					paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

					paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));

					paybillGenerationTrnDetails.setTotalDeduction((double) Math.round(Totaldeduc));
					/// grossAmt = 0d;
					//// netAmt = 0d;
					da = 0d;
					hra = 0d;
					hra6th = 0d;
					hra5th = 0d;
					pt = 0d;
					dcps_empr = 0d;
					dcps_empr1 = 0d;// Added for testing nps
					grossAdjust = 0d;
					dedAdjust = 0d;
					adjust_dcps_empr = 0d;
					adjust_dcps_empr1 = 0d;// Added for testing nps
					dcpsDelay = 0d;
					dcpsDa = 0d;
					dcpsPayArr = 0d;
					dcpsArr = 0d;
					dcpsReg = 0d;
					gisAmount = 0d;
					/* Double gradePay = 0d; */
					gpfGrpD = 0d;
					GpfAbcArr = 0d;
					gpfGrpABC = 0d;
					ta = 0d;
					ta6th = 0d;
					ta5th = 0d;
					tribalAllow = 0d;
					transAllowArr = 0d;
					groupAccPolicy = 0d;
					gpfDArr = 0d;
					DaArr = 0d;
					addHRA = 0d;
					naxalAreaAllow = 0d;
					specialPay = 0d;
					otherAllow = 0d;
					spclDutyAllow = 0d;
					hrr = 0d;
					personalPay = 0d;
					otherDeduc = 0d;
					contriProvFund = 0d;
					basicArr = 0d;
					cla = 0d;
					dearnessPay = 0d;
					conveyAllow = 0d;
					DaOnTA = 0d;
					ptArr = 0d;
					GisZp = 0d;
					otherRec = 0d;
					it = 0d;
					ServCharge = 0d;
					CopBank = 0d;
					RecurringDep = 0d;
					lic = 0d;
					CreditSoc = 0d;
					svnDA = 0d;
					othrded = 0d;
					coHsg = 0d;
					GpfABC = 0d;
					GpfAdvDLoan = 0d;
					misc = 0d;
					gradePay = 0;
					day = 0;
					monthday = 0;
					excPayRc = 0d;
					SevenPcBasic = 0d;
					SixPcBasic = 0d;
					revenueStamp = 0d;
					excessPayment = 0d;
					cmFund = 0d;
					npsEmprAllow = 0d;
					npsEmprContri = 0d;
					npsEmpContri = 0d;
					HouseAdv = 0d;
					HouseAdvInstAmt = 0d;
					CA = 0d;
					FA = 0d;
					vehAdv = 0d;
					emprDcpsDaArr = 0d;
					emprDcpsDelayedRec = 0d;
					emprdcpsDelay = 0d;
					emprDcpsPayArr = 0d;
					emprDcpsReg = 0d;
					emprDcpsRegRec = 0d;
					empDcpsDaArr = 0d;
					empDcpsDelay = 0d;
					empDcpsPayArr = 0d;
					empDcpsReg = 0d;
					gpfAdvance = 0d;
					gpfAdvanceII = 0d;
					gpfAdvInstII = null;
					deputAllow = 0d;
					ota = 0d;
					hillStatAllow = 0d;
					tracerAllow = 0d;
					naksaliedAllow = 0d;
					wa = 0d;
					gpfSubscrb = 0d;
					hba = 0d;
					socOrBankLoan = 0d;
					BLWF = 0d;
					GpfArrears = 0d;
					GpfSpclArrears = 0d;
					NDCPSsubscrp = 0d;
					beigs = 0d;
					alliedSoc = 0d;
					mantralayaSoci = 0d;
					chiplunSoc = 0d;
					ulhasnagarSoc = 0d;
					engrSoc = 0d;
					gpfDaSub = 0d;
					rop = 0d;
					payFixDiff = 0d;
					nps = 0d;
					empContri = 0d;
					emprContri = 0d;
					gpfInst = 0d;
					arrears = 0d;
					faInst = null;
					otherVehAdvInst = null;
					hbaHouseInst = null;
					excessPayrecInst = null;
					caInst = null;
					gpfAdvInst = null;
					begis = 0d;
					sataraSoci = 0d;
					rajashriShahu = 0d;
					Parsik_Janata_Sh_Vasi = 0d;
					license = 0d;
					BhagshreeBank = 0d;
					payfixAdvInst = null;
					payfixAdv = 0d;
					leavePay = 0d;
					gpfLoanRec = 0d;
					vangaonSoc = 0d;
					accidentPolicy = 0d;
					panipuravthaKolhapur = 0d;
					rajshriGovbankKolhapur = 0d;
					ahdPariAbhiMarya = 0d;
					mjpSocBeed = 0d;
					salOwnSocSangli = 0d;
					jalbhavanSocSangli = 0d;
					hastantritpune = 0d;
					shaskiyPaniPurvSocSatara = 0d;
					recovery = 0d;
					akolaSoc = 0d;
					yavatmalSoc = 0d;
					nagSahaPatSansta = 0d;
					enggSoc = 0d;
					daryapurSoc = 0d;
					pubhealSoc = 0d;
					jalpradayaSoc = 0d;
					zilhaPariKarmPant = 0d;
					jalnaSoc = 0d;
					amrawatidistEnggCredSoc = 0d;
					puneDistCentCoBnk = 0d;
					ndcpsRec = 0d;
					payandallarr = 0d;
					bharatratnavishsolapur = 0d;
					motorCycleAdv = 0d;
					motorCycleAdvInst = 0d;
					motorCycleAdvIntAmt = 0d;
					motorCycleInst = null;
					bhanZillaParWaPanchayatSamitiSanstha = 0d;
					motorCycleAdv = 0d;

					paybillGenerationTrnDetails.setGrossTotalAmt(grossAmount);
					/*
					 * paybillGenerationTrnDetails .setTotalDeduction(totalDeductionAmount);
					 */
					paybillGenerationTrnDetails.setTotalDeduction(Totaldeduc);

					// paybillGenerationTrnDetails.setTotalNetAmt(grossAmount -
					// totalDeductionAmount);
					paybillGenerationTrnDetails.setTotalNetAmt(grossAmount - Totaldeduc);
					paybillGenerationTrnDetails.setPayCommissionCode(mstEmployeeEntity2.getPayCommissionCode());
					if (mstEmployeeEntity2.getPayCommissionCode() == 700005) {
							paybillGenerationTrnDetails.setSevenPcLevel(mstEmployeeEntity2.getSevenPcLevel());
					} else {
						if (mstEmployeeEntity2.getPayInPayBand() != null)
							paybillGenerationTrnDetails.setPay_band(mstEmployeeEntity2.getPayInPayBand());
					}

					paybillGenerationTrnDetails.setPaybillGenerationTrnId(val);
					Serializable id12 = paybillHeadMpgRepo.saveHrPayPaybill(paybillGenerationTrnDetails);
					grossAmt += grossAmount;

					netAmt += grossAmount - Totaldeduc;
				}

				else {
					// return 1;

					return 2L;
				}

			} else {

			PaybillGenerationTrnDetails paybillGenerationTrnDetails = new PaybillGenerationTrnDetails();
			// MstEmployeeEntity mstEmployeeEntity2 = mstEmployeeEntity.get(i);

			paybillGenerationTrnDetails.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
			paybillGenerationTrnDetails.setPaybillYear(paybillHeadMpgModel.getPaybillYear());

			// String cityClass =
			// mstEmployeeService.getCityClassAgainstDDO(paybillHeadMpgModel.getDdoCode());
			String cityClass = mstEmployeeEntity2.getCityClass().toString();

			int payCommission = mstEmployeeService.getpayCommissionAgainstEmployee(mstEmployeeEntity2.getSevaarthId());
			int gradePaynew = 0;
			List<Object[]> object = mstEmployeeService.employeeAllowDeduction(mstEmployeeEntity2.getSevaarthId());

			//// paybillGenerationTrnDetails.setSubDeptId(mstEmployeeEntity2.getSubDeptId());
			paybillGenerationTrnDetails.setDesgCode(mstEmployeeEntity2.getDesignationCode());

			int percentage = 0;
			String percentageHRA = null;
			Double gpfpercentage = 0d;

			for (Object[] object12 : object) {

				int a = (int) object12[5];
				String str = (String) object12[6];
				String gisgroup = (String) object12[11];
				String groupname = (String) object12[7];
				// gisAmount = (double) object12[8];
				if (object12[9] != null)
					gradePaynew = (int) object12[9];
				int allowDeducCode = (int) object12[2];

				String physicalhand = Character.toString((char) object12[10]);

				String startDate = null;
				int month2 = paybillHeadMpgModel.getPaybillMonth();
				int year2 = paybillHeadMpgModel.getPaybillYear();
				Double svnPcBasic = null;
				Double hraBasic = 0d;
				if (mstEmployeeEntity2.getSevenPcBasic() != null) {

					if (mstEmployeeEntity2.getRevisedBasic() != null) {

						svnPcBasic = mstEmployeeEntity2.getRevisedBasic();
					} else {
						svnPcBasic = mstEmployeeEntity2.getSevenPcBasic();
					}

					SevenPcBasic = svnPcBasic.doubleValue();
				} else {
					SevenPcBasic = 0d;
				}
				if (mstEmployeeEntity2.getBasicPay() != null && !mstEmployeeEntity2.getPayCommissionCode().equals(700005)) {
					SixPcBasic = mstEmployeeEntity2.getBasicPay();
				}

				if (month2 < 10) {
					startDate = String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
				} else {
					startDate = String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
				}

				Integer amount = 0;
				Integer amount1 = 0;

				/*
				 * amount =
				 * paybillHeadMpgRepo.getannualincment(mstEmployeeEntity2.getSevaarthId(),
				 * startDate);
				 * 
				 * if (amount != 0 && payCommission == 8) { SevenPcBasic = amount.doubleValue();
				 * } else { if (payCommission == 8) amount1 =
				 * paybillHeadMpgRepo.getamtbeforeannualincment(mstEmployeeEntity2.getSevaarthId
				 * (), startDate);
				 * 
				 * if (amount1 != 0) SevenPcBasic = amount1.doubleValue(); }
				 * 
				 * if (amount != 0 && payCommission == 2) { SixPcBasic = amount.doubleValue(); }
				 * else { if (payCommission == 2) amount1 =
				 * paybillHeadMpgRepo.getamtbeforeannualincment(mstEmployeeEntity2.getSevaarthId
				 * (), startDate);
				 * 
				 * if (amount1 != 0) SixPcBasic = amount1.doubleValue(); }
				 */

				if (mstEmployeeEntity2.getHraBasic() != null) {
					hraBasic = mstEmployeeEntity2.getHraBasic();
				}

				System.out.println(payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
				if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
					if (payCommission == 8 && !mstEmployeeEntity2.getGiscatagory().equals(3)) {
						percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					} else {
						percentage = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					}

					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA5th)) {
						percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC,
								mstEmployeeEntity2.getCityClass());
					}
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th)) {
						percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
								mstEmployeeEntity2.getCityClass());
					}
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)) {
						percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
								mstEmployeeEntity2.getCityClass());
					}

					System.out.println("------------percentageHRA----------" + percentageHRA);

				} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
							mstEmployeeEntity2.getCityClass());
				} else {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC,
							mstEmployeeEntity2.getCityClass());
				}

				// basic =sixpcbasic/monthday*presentday;

				//

				switch (a) {
				case 1:
					System.out.println(1);

					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_PC_DA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
							&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC)) {

						if (mstEmployeeEntity2.getRevisedBasic() != null) {

							svnDA = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic() * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setSvnDA(svnDA);
						} else {
							svnDA = (double) ((SevenPcBasic * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
							paybillGenerationTrnDetails.setSvnDA(svnDA);
						}
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
							&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {

							da = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic() * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDa(da);
						} else {

							da = (double) (Math.round((SixPcBasic * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDa((double) Math.round((da)));
						}
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
							&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC)) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {

							da = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic() * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDa(da);
						} else {

							da = (double) (Math.round((SixPcBasic * percentage)
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDa((double) Math.round((da)));
						}
					}

					// End for 6PC and 7PC DA

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code) {

							paybillGenerationTrnDetails
									.setArrears((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							arrears = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setArrears((double) 0);
							arrears += 0;
						}
					}

					/* HRA component */

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						/* HRA component for 7PC */
						if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									if (mstEmployeeEntity2.getRevisedBasic() >= 22500) {
										hra = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic()
												* Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (5400);
									}
								} else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y)) {
									if (mstEmployeeEntity2.getRevisedBasic() >= 22500) {
										hra = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic()
												* Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (3600);
									}
								} else {
									if (mstEmployeeEntity2.getRevisedBasic() >= 22500) {
										hra = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic()
												* Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (1800);
									}
								}

							} else {
								if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									if (SevenPcBasic >= 22500) {
										hra = (double) (Math.round((SevenPcBasic * Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (5400);
									}
								} else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y)) {
									if (SevenPcBasic >= 22500) {
										hra = (double) (Math.round((SevenPcBasic * Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (3600);
									}
								} else {
									if (SevenPcBasic >= 22500) {
										hra = (double) (Math.round((SevenPcBasic * Double.parseDouble(percentageHRA))
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										hra = (double) (1800);
									}
								}

							}

							paybillGenerationTrnDetails.setHra((double) Math.round(hra));
						}

						/* HRA component for 6PC */
						else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
								|| payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC) {

							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra = (double) (Math.round(
											((mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							} else {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra = (double) (Math.round((SixPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra = (double) (Math.round((SixPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra = (double) (Math.round(((SixPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							}
							paybillGenerationTrnDetails.setHra((double) Math.round(hra));
						}
					}
					/* End of HRA Component */

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA5th)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA5th_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA5th_Code) {

							paybillGenerationTrnDetails
									.setHra5th((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							hra5th = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {

							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra5th = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra5th = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra5th = (double) (Math.round(
											((mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							} else {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra5th = (double) (Math.round((SevenPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra5th = (double) (Math.round((SevenPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra5th = (double) (Math.round(((SevenPcBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							}

						}
						paybillGenerationTrnDetails.setHra5th((double) Math.round(hra5th));

					}

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code) {

							paybillGenerationTrnDetails
									.setHra6th((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							hra6th = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra6th = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra6th = (double) (Math.round(
											(mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra6th = (double) (Math.round(
											((mstEmployeeEntity2.getRevisedBasic() * Double.parseDouble(percentageHRA))
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							} else {
								if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
									hra6th = (double) (Math.round((hraBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
									hra6th = (double) (Math.round((hraBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								else if (cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
									hra6th = (double) (Math.round(((hraBasic * Double.parseDouble(percentageHRA))
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)));
							}
							paybillGenerationTrnDetails.setHra6th((double) Math.round(hra6th));
						}

					}

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_EMPR)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							dcps_empr = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic() + da) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));// employee
																									// contribution
						} else {
							dcps_empr = (double) (Math.round(
									(SixPcBasic + da) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));// employee
																														// contribution
						}
						paybillGenerationTrnDetails
								.setDcpsEmployer(dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr);
					}

					/*
					 * if(mstEmployeeEntity2.getSevaarthId().equals("MJPDMSM7301")) {
					 * System.out.println("--------------");
					 * 
					 * }
					 */

					// Start Travels Allowances for 5th and 7PC

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();

						if (gradelevel >= 20) {
							if (citygroup.equals("Class A") || citygroup.equals("Class A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 3200);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 800);
								}
							}

							if ((citygroup.equals("Class A") || citygroup.equals("Class A1")) && (cityClass
									.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y)
									|| cityClass.equalsIgnoreCase(
											CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 800);
								}
							}
							ta5th = paybillGenerationTrnDetails.getTa5th();
						} else if (gradelevel >= 15 && gradelevel <= 19) {
							if (citygroup.equals("Class A") || citygroup.equals("Class A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 800);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 400);
								}
							}
							if ((citygroup.equals("Class A") || citygroup.equals("Class A1")) && (cityClass
									.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y)
									|| cityClass.equalsIgnoreCase(
											CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 400);
								}
							}

							ta5th = paybillGenerationTrnDetails.getTa5th();
						} else if (gradelevel >= 1 && gradelevel <= 14) {
							if (citygroup.equals("Class A") || citygroup.equals("Class A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 200);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 150);
								}
							}

							if ((citygroup.equals("Class A") || citygroup.equals("Class A1")) && (cityClass
									.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y)
									|| cityClass.equalsIgnoreCase(
											CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 150);
								}
							}

							ta5th = paybillGenerationTrnDetails.getTa5th();
						}

						if (mstEmployeeEntity2.getSevaarthId().equals("MJPPBNM8901")) {
							paybillGenerationTrnDetails.setTa5th((double) 800);
						}

						/// MJPSCRM6601
						if (mstEmployeeEntity2.getSevaarthId().equals("MJPSCRM6601")) {
							paybillGenerationTrnDetails.setTa5th((double) 800);
						}

						if (mstEmployeeEntity2.getSevaarthId().equals("MJPMBCM7001")) {
							paybillGenerationTrnDetails.setTa5th((double) 1000);
						}
						ta5th = paybillGenerationTrnDetails.getTa5th();

					}

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						if (gradePaynew >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (citygroup.equals("A") && citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 3200);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 800);
								}
							}
							ta5th = paybillGenerationTrnDetails.getTa5th();
						} else if (gradePaynew >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
								&& gradePaynew < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (citygroup.equals("A") || citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1600);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 800);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 400);
								}
							}
							ta5th = paybillGenerationTrnDetails.getTa5th();
						} else if (gradePaynew <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
							if (citygroup.equals("A") || citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 200);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa5th((double) 1000);
								} else {
									paybillGenerationTrnDetails.setTa5th((double) 150);
								}
							}
							ta5th = paybillGenerationTrnDetails.getTa5th();
						}

					}
					// End Travels Allowances for 6PC

					// Start Travels Allowances for 6PC
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE6th)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();
						if (gradelevel >= 20) {
							if (citygroup.equals("A")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 4800);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 2400);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 2400);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 1200);
								}
							}
							ta6th = paybillGenerationTrnDetails.getTa6th();
						} else if (gradelevel >= 7 && gradelevel <= 19) {
							if (citygroup.equals("A")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 2400);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 1200);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 600);
								}
							}
							ta6th = paybillGenerationTrnDetails.getTa6th();
						} else if (gradelevel >= 1 && gradelevel <= 6) {
							if (citygroup.equals("A")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 400);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa6th((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa6th((double) 400);
								}
							}
							ta6th = paybillGenerationTrnDetails.getTa6th();
						}

					}
					// End Travels Allowances for 6PC

					// Start Travels Allowances for 6PC
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						if (gradePaynew >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (citygroup.equals("A") && citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 4800);
								} else {
									paybillGenerationTrnDetails.setTa((double) 2400);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 2400);
								} else {
									paybillGenerationTrnDetails.setTa((double) 1200);
								}
							}
							ta = paybillGenerationTrnDetails.getTa();
						} else if (gradePaynew >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
								&& gradePaynew < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (citygroup.equals("A") || citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 2400);
								} else {
									paybillGenerationTrnDetails.setTa((double) 1200);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa((double) 600);
								}
							}
							ta = paybillGenerationTrnDetails.getTa();
						} else if (gradePaynew <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
							if (citygroup.equals("A") || citygroup.equals("A1")) {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa((double) 400);
								}
							} else {
								if (physicalhand.equals("Y")) {
									paybillGenerationTrnDetails.setTa((double) 2000);
								} else {
									paybillGenerationTrnDetails.setTa((double) 400);
								}
							}
							ta = paybillGenerationTrnDetails.getTa();
						}
					}
					// End Travels Allowances for 6PC

					// Start Travels Allowances for 7PC

					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
							&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();
						String gradePay7PC = paybillHeadMpgRepo.getgradePay7PC(gradelevel);
						Integer grade7PC = Integer.parseInt(gradePay7PC);

						if ((paybillHeadMpgModel.getPaybillMonth() >= 4 && paybillHeadMpgModel.getPaybillYear() >= 23)
								|| paybillHeadMpgModel.getPaybillYear() >= 24) {

							if (gradelevel >= 20) {
								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 10800);
									} else {
										paybillGenerationTrnDetails.setTa((double) 5400);
									}
								} else {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 5400);
									} else {
										paybillGenerationTrnDetails.setTa((double) 2700);
									}
								}
								ta = paybillGenerationTrnDetails.getTa();
							} else if (gradelevel >= 7 && gradelevel <= 19) {
								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 5400);
									} else {
										paybillGenerationTrnDetails.setTa((double) 2700);
									}
								} else {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2700);
									} else {
										paybillGenerationTrnDetails.setTa((double) 1350);
									}
								}
								ta = paybillGenerationTrnDetails.getTa();
							} else if (gradelevel >= 1 && gradelevel <= 6) {
								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										if (SevenPcBasic >= 24200 && cityClass.equalsIgnoreCase(
												CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
											paybillGenerationTrnDetails.setTa((double) 5400);
										} else {

											paybillGenerationTrnDetails.setTa((double) 2700);
										}
									} else if (SevenPcBasic >= 24200 && cityClass
											.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
										paybillGenerationTrnDetails.setTa((double) 2700);
									} else {
										paybillGenerationTrnDetails.setTa((double) 1000);
									}

								} else {
									if (physicalhand.equals("Y")) {
										if (SevenPcBasic >= 24200 && cityClass.equalsIgnoreCase(
												CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
											paybillGenerationTrnDetails.setTa((double) 2700);
										} else {

											paybillGenerationTrnDetails.setTa((double) 2250);
										}
									} else {
										if (SevenPcBasic >= 24200 && !cityClass.equalsIgnoreCase(
												CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
											paybillGenerationTrnDetails.setTa((double) 1350);
										} else {
											paybillGenerationTrnDetails.setTa((double) 675);
										}
									}
								}
								ta = paybillGenerationTrnDetails.getTa();
							}
						} else {
							if (gradelevel >= 20) {

								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 4800);
									} else {
										paybillGenerationTrnDetails.setTa((double) 2400);
									}
								} else {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2400);
									} else {
										paybillGenerationTrnDetails.setTa((double) 1200);
									}
								}
								ta = paybillGenerationTrnDetails.getTa();
							} else if (gradelevel >= 7 && gradelevel <= 19) {
								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2000);
									} else {
										paybillGenerationTrnDetails.setTa((double) 1200);
									}
								} else {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2000);
									} else {
										paybillGenerationTrnDetails.setTa((double) 600);
									}
								}
								ta = paybillGenerationTrnDetails.getTa();
							} else if (gradelevel >= 1 && gradelevel <= 6) {
								if (citygroup.equals("A") || citygroup.equals("A1")) {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2000);
									} else {
										paybillGenerationTrnDetails.setTa((double) 400);
									}
								} else {
									if (physicalhand.equals("Y")) {
										paybillGenerationTrnDetails.setTa((double) 2000);
									} else {
										paybillGenerationTrnDetails.setTa((double) 400);
									}
								}

							}
						}
						ta = paybillGenerationTrnDetails.getTa();
					}

					// End Travels Allowances for 7PC

					// Start Tribal Allowances
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE);
						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) {
							paybillGenerationTrnDetails.setTribalAllow(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							tribalAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setTribalAllow((double) 0);
							tribalAllow += 0;

						}
					}

					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE);
						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE) {
							paybillGenerationTrnDetails.setPayAndAllowancesArrears(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							payandallarr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setPayAndAllowancesArrears((double) 0);
							payandallarr += 0;

						}
					}
					// transport Allowance Arr
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) {

							paybillGenerationTrnDetails.setTransAllowArr(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							transAllowArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {

							paybillGenerationTrnDetails.setTransAllowArr((double) 0);
							transAllowArr += 0;

						}
					}
					// DA Arr
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) {

							paybillGenerationTrnDetails.setDaArr(
									(double) (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt()))));
							DaArr = (double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						}

						else {
							paybillGenerationTrnDetails.setDaArr((double) 0);
							DaArr += 0;

						}
					}
					// Additional HRA
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE) {

							paybillGenerationTrnDetails.setAddHRA(
									(double) (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt()))));
							addHRA = (double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						} else {
							paybillGenerationTrnDetails.setAddHRA((double) 0);
							addHRA += 0;
						}
					}
					// Naxal Area Allowance
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE) {

							paybillGenerationTrnDetails.setNaxalAreaAllow(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							naxalAreaAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setNaxalAreaAllow((double) 0);
							naxalAreaAllow += 0;

						}
					}
					// Special Duty Allowance
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE) {

							paybillGenerationTrnDetails.setSpclDutyAllow(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							spclDutyAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setSpclDutyAllow((double) 0);
							spclDutyAllow += 0;
						}
					}
					// Other Allowance
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE) {
							paybillGenerationTrnDetails.setOtherAllow(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							otherAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setOtherAllow((double) 0);
							otherAllow += 0;
						}
					}

					// Basic Arr //
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE) {

							paybillGenerationTrnDetails
									.setBasicArr((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							basicArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setBasicArr((double) 0);
							basicArr += 0;
						}
					}
					// Special Pay

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE) {

							paybillGenerationTrnDetails.setSpecialPay(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							specialPay = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setSpecialPay((double) 0);
							specialPay += 0;

						}
					}
					// Personal Pay

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE) {

							paybillGenerationTrnDetails.setPersonalPay(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							personalPay = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setPersonalPay((double) 0);
							personalPay += 0;
						}
					}
					// CLA

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& employeeAllowDeducComponentAmtEntity.getNetAmt() > 0d
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) {

							paybillGenerationTrnDetails
									.setCla((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							cla = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {

							Long gradelevel = mstEmployeeEntity2.getSevenPcLevel();
							int claamt = 0;

							if (SevenPcBasic < 3000) {
								if (citygroup.equals("A1")) {
									claamt = 90;
								} else if (citygroup.equals("A")) {
									claamt = 65;
								} else if (citygroup.equals("B1")) {
									claamt = 45;
								} else if (citygroup.equals("B")) {
									claamt = 25;
								}
							} else if (SevenPcBasic >= 3000 && SevenPcBasic < 4499) {
								if (citygroup.equals("A1")) {
									claamt = 125;
								} else if (citygroup.equals("A")) {
									claamt = 95;
								} else if (citygroup.equals("B1")) {
									claamt = 65;
								} else if (citygroup.equals("B")) {
									claamt = 35;
								}
							} else if (SevenPcBasic >= 4500 && SevenPcBasic < 5999) {
								if (citygroup.equals("A1")) {
									claamt = 200;
								} else if (citygroup.equals("A")) {
									claamt = 150;
								} else if (citygroup.equals("B1")) {
									claamt = 100;
								} else if (citygroup.equals("B")) {
									claamt = 65;
								}
							} else {
								if (citygroup.equals("A1")) {
									claamt = 300;
								} else if (citygroup.equals("A")) {
									claamt = 240;
								} else if (citygroup.equals("B1")) {
									claamt = 180;
								} else if (citygroup.equals("B")) {
									claamt = 120;
								}
							}

							paybillGenerationTrnDetails.setCla((double) claamt);
							cla = (double) claamt;

						}
					}
					// Dearness Pay
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DEARNESS_PAY)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE) {

							paybillGenerationTrnDetails.setDearnessPay(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							dearnessPay = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setDearnessPay((double) 0);
							dearnessPay += 0;
						}
					}
					// component for DA on TA
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_on_TA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE);// SevenPcBasic
						if (SixPcBasic != 0) {
							DaOnTA += (Math.round(
									(SixPcBasic) * 17 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDaOnTA((double) (Math.round(DaOnTA)));
						} else {
							DaOnTA += (Math.round((SevenPcBasic.doubleValue()) * 17
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setDaOnTA((double) (Math.round(DaOnTA)));
						}
					}

					// Contributory Fund
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE) {

							paybillGenerationTrnDetails.setContriProvFund(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							contriProvFund = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setContriProvFund((double) 0);
							contriProvFund += 0;
						}
					}

					// Employer_DCPS_DA_Arrears

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Employer_DCPS_DA_Arrears)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {// employeer
																								// contribution
																								// allowance

						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsDaArr = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsDaArr = 0d;
							}
						}
						paybillGenerationTrnDetails.setEmprDcpsDaArr((double) emprDcpsDaArr);
					}
					// Employer_DCPS_Delayed_Rec

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Employer_DCPS_Delayed_Rec)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {// employeer
																								// contribution
																								// allowance
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDelayedRec = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDelayedRec = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDelayedRec = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDelayedRec = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsDelayedRec = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDelayedRec = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDelayedRec = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsDelayedRec = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsDelayedRec = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsDelayedRec = 0d;
							}
						}
						paybillGenerationTrnDetails.setEmprDcpsDelayedRec((double) emprDcpsDelayedRec);

					}
					// Employer_DCPS_Pay_Arrears

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Employer_DCPS_Pay_Arrears)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																							// contribution
																							// allowance
					{

						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsPayArr = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsPayArr = 0d;
							}
						}
						paybillGenerationTrnDetails.setEmprDcpsPayArr((double) emprDcpsPayArr);

					}
					// Employer_DCPS_Regular_Rec

					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Employer_DCPS_Regular_Rec)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																							// contribution
																							// allowance
					{
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsRegRec = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsRegRec = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsRegRec = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsRegRec = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsRegRec = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsRegRec = (double) (Math.round((SevenPcBasic + svnDA) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsRegRec = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									emprDcpsRegRec = (double) (Math.round((SixPcBasic + da) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									emprDcpsRegRec = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								emprDcpsRegRec = 0d;
							}
						}
						paybillGenerationTrnDetails.setEmprDcpsRegRec((double) emprDcpsRegRec);
					}
					// NPS_EMPR_ALLOW
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																							// contribution
																							// allowance
					{
						Double GpfArrears11 = 0d;

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(), 113);

						if (employeeAllowDeducComponentAmtEntity != null) {

							GpfArrears11 = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						}

						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (mstEmployeeEntity2.getGiscatagory().equals(1)) {
									if (paybillHeadMpgModel.getPaybillMonth() >= 8
											&& paybillHeadMpgModel.getPaybillYear() == 24
											|| paybillHeadMpgModel.getPaybillMonth() >= 1
													&& paybillHeadMpgModel.getPaybillYear() == 25) {
										Double DaArr1 = 0d;
										Double DaArr2 = 0d;
										Double DaArrtenpersent = 0d;
										Double DaArrforteenpersent = 0d;
										Double totalDaArr = 0d;
										if (paybillHeadMpgModel.getPaybillMonth() == 11
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 4;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
										} else if (paybillHeadMpgModel.getPaybillMonth() == 12
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 5;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
										} else {
											npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
										}

									} else {
										npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 10
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}

								} else {
									npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (mstEmployeeEntity2.getGiscatagory().equals(1)) {
									if (paybillHeadMpgModel.getPaybillMonth() >= 8
											&& paybillHeadMpgModel.getPaybillYear() == 24
											|| paybillHeadMpgModel.getPaybillMonth() >= 1
													&& paybillHeadMpgModel.getPaybillYear() == 25) {

										Double DaArr1 = 0d;
										Double DaArr2 = 0d;
										Double DaArrtenpersent = 0d;
										Double DaArrforteenpersent = 0d;
										Double totalDaArr = 0d;
										if (paybillHeadMpgModel.getPaybillMonth() == 11
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 4;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SixPcBasic + da) * 14) / 100) + totalDaArr));
										} else if (paybillHeadMpgModel.getPaybillMonth() == 12
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 5;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SixPcBasic + da) * 14) / 100) + totalDaArr));
										} else {
											npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
										}
									} else {
										npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 10
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}

								} else {
									npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								npsEmprAllow = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (mstEmployeeEntity2.getGiscatagory().equals(1)) {
									if (paybillHeadMpgModel.getPaybillMonth() >= 8
											&& paybillHeadMpgModel.getPaybillYear() == 24
											|| paybillHeadMpgModel.getPaybillMonth() >= 1
													&& paybillHeadMpgModel.getPaybillYear() == 25) {

										Double DaArr1 = 0d;
										Double DaArr2 = 0d;
										Double DaArrtenpersent = 0d;
										Double DaArrforteenpersent = 0d;
										Double totalDaArr = 0d;
										if (paybillHeadMpgModel.getPaybillMonth() == 11
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 4;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
										} else if (paybillHeadMpgModel.getPaybillMonth() == 12
												&& paybillHeadMpgModel.getPaybillYear() == 24) {
											DaArr1 = DaArr / 5;
											DaArr2 = DaArr - DaArr1;
											DaArrtenpersent = (DaArr1 * 10 / 100);
											DaArrforteenpersent = (DaArr2 * 14 / 100);
											totalDaArr = DaArrtenpersent + DaArrforteenpersent;
											npsEmprAllow = (double) (Math
													.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
										} else {
											npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
													/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
										}
									} else {
										npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 10
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}

								} else {
									npsEmprAllow = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (mstEmployeeEntity2.getGiscatagory().equals(1)) {
									if (paybillHeadMpgModel.getPaybillMonth() >= 8
											&& paybillHeadMpgModel.getPaybillYear() == 24
											|| paybillHeadMpgModel.getPaybillMonth() >= 1
													&& paybillHeadMpgModel.getPaybillYear() == 25) {
										npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									} else {
										npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 10
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}

								} else {
									npsEmprAllow = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								npsEmprAllow = 0d;
							}
						}

						paybillGenerationTrnDetails.setNpsEmployerAllow((double) Math.ceil(npsEmprAllow));
					} else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code) {

							paybillGenerationTrnDetails.setDeputationAllow(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							deputAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setDeputationAllow((double) 0);
							deputAllow += 0;
						}
					} else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code) {

							paybillGenerationTrnDetails
									.setOTA((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							ota = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setOTA((double) 0);
							ota += 0;
						}
					} else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code) {

							paybillGenerationTrnDetails.setHillStatAllow(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							hillStatAllow = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setHillStatAllow((double) 0);
							hillStatAllow += 0;
						}
					}else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance)) {
						wa = 50d;
						paybillGenerationTrnDetails.setWa(wa);
						// wa += 0d;
					}

					// GPF_Arrears
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) {

							paybillGenerationTrnDetails.setGpfArrears(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							GpfArrears = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setGpfArrears((double) 0);
							GpfArrears += 0;
						}
					}
					// LEAVE_PAY
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code) {

							paybillGenerationTrnDetails
									.setLeavePay((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							leavePay = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setLeavePay((double) 0);
							leavePay += 0;
						}
					}

					// End For Allowances//
					// Start for deduction component

					break;
				case 2:
					// Professional Tax//
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						// Double svnpcbasic=
						// Double.valueOf(SevenPcBasic.toString());
						if (paybillHeadMpgModel.getPaybillMonth() == 2) {
							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (mstEmployeeEntity2
										.getRevisedBasic() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
								else if (mstEmployeeEntity2
										.getRevisedBasic() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
										&& mstEmployeeEntity2
												.getRevisedBasic() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
									pt = 275d;
								else if (mstEmployeeEntity2
										.getRevisedBasic() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
								else
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
							} else {
								if (SixPcBasic > 0) {
									if (mstEmployeeEntity2
											.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
									else if (mstEmployeeEntity2
											.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
											&& mstEmployeeEntity2
													.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = 275d;
									else if (mstEmployeeEntity2
											.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
									else
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
								} else {
									if (SevenPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
									else if (SevenPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
											&& SevenPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = 275d;
									else if (SevenPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
									else
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
								}
							}
						} else {

							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								if (mstEmployeeEntity2
										.getRevisedBasic() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
								else if (mstEmployeeEntity2
										.getRevisedBasic() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
										&& mstEmployeeEntity2
												.getRevisedBasic() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
								else if (mstEmployeeEntity2
										.getRevisedBasic() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
								else
									pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
							} else {// SevenPcBasic
								if (SixPcBasic > 0) {
									if (SixPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
									else if (SixPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
											&& SixPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = 175d;
									else if (SixPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
									else
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;

								} else {
									if (SevenPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
									else if (SevenPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
											&& SevenPcBasic <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = 275d;
									else if (SevenPcBasic > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
									else
										pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
								}

							}
						}
						paybillGenerationTrnDetails.setPt(pt);

					}
					// Emp_DCPS_DA_ARR//

					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR)) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsDaArr = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDaArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDaArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsDaArr = 0d;
							}
						}

						paybillGenerationTrnDetails.setEmpDcpsDaArr((double) empDcpsDaArr);
					}

					// Emp_DCPS_DELAY

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY)) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDelay = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDelay = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDelay = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDelay = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsDelay = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDelay = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDelay = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsDelay = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsDelay = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsDelay = 0d;
							}
						}

						paybillGenerationTrnDetails.setEmpDcpsDelay((double) empDcpsDelay);
					}

					// Emp_DCPS_PAY_ARR
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR)) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsPayArr = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsPayArr = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsPayArr = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsPayArr = 0d;
							}
						}

						paybillGenerationTrnDetails.setEmpDcpsPayArr((double) empDcpsPayArr);
					}

					// Emp_DCPS_REGULAR_RECOVERY
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsReg = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsReg = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsReg = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsReg = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsReg = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsReg = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsReg = (double) (Math.round((SevenPcBasic + svnDA) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								if (paybillHeadMpgModel.getPaybillYear() >= 20
										&& paybillHeadMpgModel.getPaybillMonth() >= 8
										|| paybillHeadMpgModel.getPaybillYear() >= 21
												&& paybillHeadMpgModel.getPaybillMonth() >= 1) {
									empDcpsReg = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								} else {
									empDcpsReg = (double) (Math.round((SixPcBasic + da) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}
							} else {
								empDcpsReg = 0d;
							}
						}

						paybillGenerationTrnDetails.setEmpDcpsRegularRecovery((double) Math.round(empDcpsReg));
					}
					// DCPS Arr

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE) {

							paybillGenerationTrnDetails
									.setDcpsArr((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							dcpsArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setDcpsArr((double) 0);
							dcpsArr += 0;
						}
					}

					// Adjustable DCPS Employer Contribution//
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																							// contribution
					{
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							adjust_dcps_empr = (double) (Math.round((mstEmployeeEntity2.getRevisedBasic() + da) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							paybillGenerationTrnDetails.setAdjustDcpsEmployer(
									(double) Math.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr));
						} else {
							if (SixPcBasic > 0) {
								adjust_dcps_empr = (double) (Math.round((SixPcBasic + da) * 14
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								paybillGenerationTrnDetails.setAdjustDcpsEmployer((double) Math
										.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr));
							}

							else {
								adjust_dcps_empr = (double) (Math.round((SevenPcBasic + da) * 14
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								paybillGenerationTrnDetails.setAdjustDcpsEmployer((double) Math
										.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr));
							}
						}
					}

					// Start GIS Component
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
						gisAmount = (double) object12[8];
						if (gisgroup.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_A)
								&& (gisAmount.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A)
										|| gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A)) {
							paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						} else if (gisgroup.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_B)
								&& (gisAmount.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B)
										|| gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B)) {
							paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						} else if (gisgroup.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_BNGZ)
								&& (gisAmount.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ)
										|| gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ)) {
							paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						} else if (gisgroup.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_C)
								&& (gisAmount.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C)
										|| gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C)) {
							paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						} else if (gisgroup.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_D)
								&& (gisAmount.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D)
										|| gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D)) {
							paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						}
					}
					// End GIS Component

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_ABC)) {

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE);
						// TODO select * from loan_employee_dtls where employee_id =8532
						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) {

							paybillGenerationTrnDetails.setGpfGrpABC(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfGrpABC = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {

							if (mstEmployeeEntity2.getRevisedBasic() != null) {
								gpfGrpABC = (double) (Math.round(mstEmployeeEntity2.getRevisedBasic()) * gpfpercentage
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
							} else {//
								if (SixPcBasic > 0) {
									gpfGrpABC = (double) (Math.round(SixPcBasic) * gpfpercentage
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
								} else {
									gpfGrpABC = (double) (Math.round(SevenPcBasic) * gpfpercentage
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
								}
							}

							paybillGenerationTrnDetails.setGpfGrpABC((double) Math.round(gpfGrpABC));
						}
						// GPF_GRP_D
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_D)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) {

							paybillGenerationTrnDetails
									.setGpfGrpD((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfGrpD = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							if (mstEmployeeEntity2.getRevisedBasic() != null) {// SevenPcBasic
								gpfGrpD = (double) (Math.round(mstEmployeeEntity2.getRevisedBasic()) * gpfpercentage
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
							} else {
								if (SixPcBasic > 0) {
									gpfGrpD = (double) (Math.round(SixPcBasic) * gpfpercentage
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
								} else {
									gpfGrpD = (double) (Math.round(SevenPcBasic) * gpfpercentage
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
								}
							}
							paybillGenerationTrnDetails.setGpfGrpD((double) Math.round(gpfGrpD));
						}
					}
					// GPF_ABC_ARR
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) {

							paybillGenerationTrnDetails.setGpfAbcArr(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							GpfAbcArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setGpfAbcArr((double) 0);
							GpfAbcArr += 0;
						}

					}
					// Group_Acc_policy
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) {

							paybillGenerationTrnDetails.setGroupAccPolicy(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							groupAccPolicy += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {// SevenPcBasic
							if (SixPcBasic > 0) {
								groupAccPolicy = (double) (Math.round(SixPcBasic) * gpfpercentage
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

								paybillGenerationTrnDetails.setGroupAccPolicy((double) Math.round(groupAccPolicy));
							} else {
								groupAccPolicy = (double) (Math.round(SevenPcBasic) * gpfpercentage
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

								paybillGenerationTrnDetails.setGroupAccPolicy((double) Math.round(groupAccPolicy));
							}
						}
					}
					// GPF_D_ARR
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) {

							paybillGenerationTrnDetails
									.setGpfDArr((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfDArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setGpfDArr((double) 0);
							gpfDArr += 0;
						}
					}

					/// PT Arr

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE) {

							paybillGenerationTrnDetails
									.setPtArr((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							ptArr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setPtArr((double) 0);
							ptArr += 0;
						}
					}

					// Other Deduction
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE) {

							paybillGenerationTrnDetails.setOtherDeduct(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							otherDeduc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setOtherDeduct((double) 0);
							otherDeduc += 0;
						}
					}
					// GIS ZP
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE) {

							paybillGenerationTrnDetails
									.setGisZp((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							GisZp = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setGisZp((double) 0);
							GisZp += 0;
						}
					}
					// House Rent Recovery
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE) {

							paybillGenerationTrnDetails
									.setHrr((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							hrr = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setHrr((double) 0);
							hrr += 0;
						}
					}
					// Other Recovery
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

							paybillGenerationTrnDetails
									.setOtherRec((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							otherRec = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setOtherRec((double) 0);
							otherRec += 0;
						}
					}
					// Income Tax
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE) {

							paybillGenerationTrnDetails
									.setIt((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							it = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setIt((double) 0);
							it += 0;
						}
					}
					// Revenue Stamp
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Revenue_Stamp)
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

						revenueStamp = 1d;

						paybillGenerationTrnDetails.setRevenueStamp(revenueStamp);
					}

					// Excess Payment
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code) {

							paybillGenerationTrnDetails.setExcessPayment(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							excessPayment = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setExcessPayment((double) 0);
							excessPayment += 0;
						}
					}
					// CM Fund/Accidential Insurance
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code) {

							paybillGenerationTrnDetails
									.setCmFund((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							cmFund = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setCmFund((double) 0);
							cmFund += 0;
						}
					}

					// NPS_EMPR_DEDUCT
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI)// COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																							// contribution
					{
						Double GpfArrears2 = 0d;
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(), 113);

						if (employeeAllowDeducComponentAmtEntity != null) {

							GpfArrears2 = (double) (Math.ceil(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						}
						if (mstEmployeeEntity2.getRevisedBasic() != null) {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								empContri = (double) (Math.ceil((mstEmployeeEntity2.getRevisedBasic() + svnDA + DaArr)
										* 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								empContri = (double) (Math.ceil((mstEmployeeEntity2.getRevisedBasic() + da + DaArr) * 10
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							} else {
								empContri = 0d;
							}
						} else {
							if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

								empContri = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 10
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

							} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {

								empContri = (double) (Math.ceil((SixPcBasic + da + DaArr) * 10
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							} else {
								empContri = 0d;
							}
						}

						paybillGenerationTrnDetails.setNpsEmployeeContri((double) Math.ceil(empContri));// npsEmprContri
					}
					// NPS_EMP_CONTRI
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)// COMMONCODE_COMPONENT_NPS_EMP_CONTRI
							&& str != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employees
																							// contribution
					{
						Double GpfArrears1 = 0d;

						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(), 113);

						if (employeeAllowDeducComponentAmtEntity != null) {

							GpfArrears1 = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						}
						if ((payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {

							if (mstEmployeeEntity2.getGiscatagory().equals(1)) {
								if (paybillHeadMpgModel.getPaybillMonth() >= 8
										&& paybillHeadMpgModel.getPaybillYear() == 24
										|| paybillHeadMpgModel.getPaybillMonth() >= 1
												&& paybillHeadMpgModel.getPaybillYear() == 25) {

									Double DaArr1 = 0d;
									Double DaArr2 = 0d;
									Double DaArrtenpersent = 0d;
									Double DaArrforteenpersent = 0d;
									Double totalDaArr = 0d;
									if (paybillHeadMpgModel.getPaybillMonth() == 11
											&& paybillHeadMpgModel.getPaybillYear() == 24) {
										DaArr1 = DaArr / 4;
										DaArr2 = DaArr - DaArr1;
										DaArrtenpersent = (DaArr1 * 10 / 100);
										DaArrforteenpersent = (DaArr2 * 14 / 100);
										totalDaArr = DaArrtenpersent + DaArrforteenpersent;
										npsEmprContri = (double) (Math
												.ceil((((SixPcBasic + da) * 14) / 100) + totalDaArr));
									} else if (paybillHeadMpgModel.getPaybillMonth() == 12
											&& paybillHeadMpgModel.getPaybillYear() == 24) {
										DaArr1 = DaArr / 5;
										DaArr2 = DaArr - DaArr1;
										DaArrtenpersent = (DaArr1 * 10 / 100);
										DaArrforteenpersent = (DaArr2 * 14 / 100);
										totalDaArr = DaArrtenpersent + DaArrforteenpersent;
										npsEmprContri = (double) (Math
												.ceil((((SixPcBasic + da) * 14) / 100) + totalDaArr));
									} else {
										npsEmprContri = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}
								} else {
									npsEmprContri = (double) (Math.ceil((SixPcBasic + da + DaArr) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}

							} else {
								npsEmprContri = (double) (Math.ceil((SixPcBasic + da + DaArr) * 14
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

							}
						} else if ((payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC)) {

							if (mstEmployeeEntity2.getGiscatagory().equals(1)) {

								if (paybillHeadMpgModel.getPaybillMonth() >= 8
										&& paybillHeadMpgModel.getPaybillYear() == 24
										|| paybillHeadMpgModel.getPaybillMonth() >= 1
												&& paybillHeadMpgModel.getPaybillYear() == 25) {

									Double DaArr1 = 0d;
									Double DaArr2 = 0d;
									Double DaArrtenpersent = 0d;
									Double DaArrforteenpersent = 0d;
									Double totalDaArr = 0d;
									if (paybillHeadMpgModel.getPaybillMonth() == 11
											&& paybillHeadMpgModel.getPaybillYear() == 24) {
										DaArr1 = DaArr / 4;
										DaArr2 = DaArr - DaArr1;
										DaArrtenpersent = (DaArr1 * 10 / 100);
										DaArrforteenpersent = (DaArr2 * 14 / 100);
										totalDaArr = DaArrtenpersent + DaArrforteenpersent;
										npsEmprContri = (double) (Math
												.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
									} else if (paybillHeadMpgModel.getPaybillMonth() == 12
											&& paybillHeadMpgModel.getPaybillYear() == 24) {
										DaArr1 = DaArr / 5;
										DaArr2 = DaArr - DaArr1;
										DaArrtenpersent = (DaArr1 * 10 / 100);
										DaArrforteenpersent = (DaArr2 * 14 / 100);
										totalDaArr = DaArrtenpersent + DaArrforteenpersent;
										npsEmprContri = (double) (Math
												.ceil((((SevenPcBasic + svnDA) * 14) / 100) + totalDaArr));
									} else {
										npsEmprContri = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
												/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
									}
								} else {
									npsEmprContri = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 10
											/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
								}

							} else {
								npsEmprContri = (double) (Math.ceil((SevenPcBasic + svnDA + DaArr) * 14
										/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							}
						}
						paybillGenerationTrnDetails.setNpsEmployerDeduct(npsEmprContri);// setNpsEmployeeContri
					}

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code) {

							paybillGenerationTrnDetails
									.setHba((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							hba = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setHba((double) 0);
							hba += 0;
						}
					} /*
						 * else if
						 * (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)
						 * ) {
						 * 
						 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
						 * mstEmployeeService .findFADetails(mstEmployeeEntity2.getEmployeeId(),
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
						 * 
						 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
						 * mstEmployeeEntity2.getEmployeeId());
						 * 
						 * if (lnaFAEmployeeRequestMstEntity != null &&
						 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
						 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
						 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount();
						 * 
						 * Integer fi = lnaFAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
						 * Integer sancInst =
						 * lnaFAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); faInst =
						 * fi.toString() + "/" + sancInst.toString();
						 * 
						 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
						 * gpfAdvD); } paybillGenerationTrnDetails.setFaInst(faInst); FA = (double)
						 * (Math.round(FA)); paybillGenerationTrnDetails.setFestivalAdv(FA);
						 * 
						 * } else { paybillGenerationTrnDetails.setFestivalAdv(0d); FA = (double) 0;
						 * 
						 * } }
						 */ /*
							 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
							 * COMMONCODE_COMPONENT_COMP_ADV)) { EmployeeAllowDeducComponentAmtEntity
							 * employeeAllowDeducComponentAmtEntity = mstEmployeeService
							 * .findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
							 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
							 * 
							 * if (employeeAllowDeducComponentAmtEntity != null && allowDeducCode ==
							 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code) {
							 * 
							 * paybillGenerationTrnDetails.setComputerAdv( (double)
							 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())); CA = (double)
							 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							 * 
							 * } else { paybillGenerationTrnDetails.setComputerAdv((double) 0); CA += 0; } }
							 */ else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) {

							paybillGenerationTrnDetails
									.setLIC((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							lic = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setLIC((double) 0);
							lic += 0;

						}
					}
					// Other Recovery

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

							paybillGenerationTrnDetails.setOtherRec(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							otherRec = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setOtherRec((double) 0);
							otherRec += 0;
						}
					}

					// Society_Or_Bank_Loan_Code
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code) {

							paybillGenerationTrnDetails.setSocOrBankLoan(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							socOrBankLoan = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setSocOrBankLoan((double) 0);
							socOrBankLoan += 0;
						}
					}
					// BLWF
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF)) {

						paybillGenerationTrnDetails.setBLWF(12d);
						BLWF += 12d;

						/*
						 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
						 * mstEmployeeService .findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code);
						 * 
						 * if (employeeAllowDeducComponentAmtEntity != null && allowDeducCode ==
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code) {
						 * 
						 * paybillGenerationTrnDetails .setBLWF((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))); BLWF =
						 * (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())); }
						 * else { paybillGenerationTrnDetails.setBLWF((double) 0); BLWF += 0; }
						 */
					}
					// NDCPS_Subscription
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code) {

							paybillGenerationTrnDetails.setNDCPSSubscription(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							NDCPSsubscrp = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setNDCPSSubscription((double) 0);
							NDCPSsubscrp += 0;
						}
					}
					/*
					 * // GPF_Arrears else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_GPF_Arrears)) { EmployeeAllowDeducComponentAmtEntity
					 * employeeAllowDeducComponentAmtEntity = mstEmployeeService
					 * .findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code);
					 * 
					 * if (employeeAllowDeducComponentAmtEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) {
					 * 
					 * paybillGenerationTrnDetails.setGpfArrears( (double)
					 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))); GpfArrears =
					 * (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())); }
					 * else { paybillGenerationTrnDetails.setGpfArrears((double) 0); GpfArrears +=
					 * 0; } }
					 */
					// GPF_Special_Arrears
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code) {

							paybillGenerationTrnDetails.setGpfSpecialArr(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							GpfSpclArrears = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setGpfSpecialArr((double) 0);
							GpfSpclArrears += 0;
						}
					}
					// GPF_Special_Arrears

					/*
					 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_BEGIS)) { OtherAllowanceEntity otherAllowanceEntity =
					 * null; if (mstEmployeeEntity2.getGiscatagory() != null) { if
					 * (mstEmployeeEntity2.getGiscatagory().equals(1) ||
					 * mstEmployeeEntity2.getGiscatagory().equals(2)) { otherAllowanceEntity =
					 * mstEmployeeService.findBEGISAmt( mstEmployeeEntity2.getGiscatagory(),
					 * mstEmployeeEntity2.getBegisCatg()); } else { otherAllowanceEntity =
					 * mstEmployeeService .findBEGISAmtwith(mstEmployeeEntity2.getGiscatagory()); }
					 * }
					 * 
					 * if (otherAllowanceEntity != null) { paybillGenerationTrnDetails
					 * .setBegis((double) (Math.round(otherAllowanceEntity.getAmount()))); beigs =
					 * ((double) (Math.round(otherAllowanceEntity.getAmount()))); } else {
					 * paybillGenerationTrnDetails.setBegis(0d); beigs = (0d); }
					 * 
					 * }
					 */
					// GPF_Subscription
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code) {

							paybillGenerationTrnDetails.setGpfSubscription(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfSubscrb = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setGpfSubscription((double) 0);
							gpfSubscrb += 0;
						}
					}
					// GPF_DA_Sub
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code) {

							paybillGenerationTrnDetails
									.setGpfDaSoc((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfDaSub = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setGpfDaSoc((double) 0);
							gpfDaSub += 0;
						}
					}
					// ROP
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) {

							paybillGenerationTrnDetails
									.setRop((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							rop = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setRop((double) 0);
							rop += 0;
						}
					}
					// pay_Fix_Diff
					/*
					 * else if (str .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_Pay_Fix_Diff)) {
					 * 
					 * PayfixationAdvEntity payfixationAdvEntity =
					 * mstEmployeeService.findPayfixDetails( mstEmployeeEntity2.getSevaarthId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code);
					 * 
					 * if (payfixationAdvEntity != null &&
					 * payfixationAdvEntity.getLoanactivateflag() == 1) { if
					 * (payfixationAdvEntity.getLoanprinemiamt() != null) { payfixAdv =
					 * payfixationAdvEntity.getLoanprinemiamt().doubleValue(); Integer payfix =
					 * payfixationAdvEntity.getTotalRecoveredInst() + 1; Integer sanPayfixAdvns =
					 * payfixationAdvEntity.getLoanprininstno(); payfixAdvInst = payfix.toString() +
					 * "/" + sanPayfixAdvns.toString(); //
					 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } // Pay_Fix_Diff payfixAdv = (double) (Math.round(payfixAdv));
					 * paybillGenerationTrnDetails.setPayFixAdvDiffInst(payfixAdvInst);
					 * paybillGenerationTrnDetails.setPayFixDiff(payfixAdv);
					 * 
					 * } else { paybillGenerationTrnDetails.setPayFixDiff(0d); payfixAdv = (double)
					 * 0;
					 * 
					 * } }
					 */
					// NPS
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code) {

							paybillGenerationTrnDetails
									.setNps((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							nps = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setNps((double) 0);
							nps += 0;
						}
					}

					/// Computer Advance
					/*
					 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_COMP_ADV)) {
					 * 
					 * LNACAEmployeeRequestMstEntity lnaCAEmployeeRequestMstEntity =
					 * mstEmployeeService .findCADetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
					 * 
					 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * if (lnaCAEmployeeRequestMstEntity != null &&
					 * lnaCAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaCAEmployeeRequestMstEntity.getInstallmentAmount() != null) { CA =
					 * lnaCAEmployeeRequestMstEntity.getInstallmentAmount();
					 * 
					 * Integer cai = lnaCAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
					 * Integer sanCAIns =
					 * lnaCAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); caInst =
					 * cai.toString() + "/" + sanCAIns.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setCompAdvInst(caInst);
					 * paybillGenerationTrnDetails.setComputerAdv(CA); CA = (double)
					 * (Math.round(CA));
					 * 
					 * } else { paybillGenerationTrnDetails.setComputerAdv(0d); CA = (double) 0;
					 * 
					 * } }
					 */

					/// Festival Advance
					/*
					 * else if
					 * (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)
					 * ) {
					 * 
					 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
					 * mstEmployeeService.findFADetails( mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
					 * 
					 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * if (lnaFAEmployeeRequestMstEntity != null &&
					 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
					 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount(); Integer
					 * fai=lnaFAEmployeeRequestMstEntity.getNoOfInstallmentsPaid()+1; Integer
					 * sanFAIns=lnaFAEmployeeRequestMstEntity.getSanctionedNoOfInstallment();
					 * caInst=cai.toString()+"/"+sanFAIns.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } FA = (double) (Math.round(FA));
					 * paybillGenerationTrnDetails.setFestivalAdv(FA);
					 * 
					 * } else { paybillGenerationTrnDetails.setFestivalAdv(0d); FA = (double) 0;
					 * 
					 * } }
					 */
					/// GPF Advance
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFAdvance)) {

						LoanEmployeeDtlsEntity loandetails = mstEmployeeService.findGPFADetails(
								mstEmployeeEntity2.getSevaarthId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code);

						System.out.println("mstEmployeeEntity2.getEmployeeId()" + mstEmployeeEntity2.getEmployeeId());

						if (loandetails != null && loandetails.getLoanactivateflag() == 1) {
							if (loandetails.getLoanprinemiamt() != null) {
								gpfAdvance = loandetails.getLoanprinemiamt().doubleValue();
								Integer gpfAdvi = loandetails.getTotalRecoveredInst() + 1;
								Integer sanGPFAdvns = loandetails.getLoanprininstno();
								gpfAdvInst = gpfAdvi.toString() + "/" + sanGPFAdvns.toString();
								// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
							}
							// GPF_ADVANCE
							gpfAdvance = (double) (Math.round(gpfAdvance));
							paybillGenerationTrnDetails.setGpfAdvInst(gpfAdvInst);
							paybillGenerationTrnDetails.setGpfAdvance(gpfAdvance);

						} else {
							paybillGenerationTrnDetails.setGpfAdvance(0d);
							gpfAdvance = (double) 0;

						}
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFAdvanceII)) {

						LoanEmployeeDtlsEntity loandetails = mstEmployeeService.findGPFAdvDetails(
								mstEmployeeEntity2.getSevaarthId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code);

						System.out.println("mstEmployeeEntity2.getEmployeeId()" + mstEmployeeEntity2.getEmployeeId());

						if (loandetails != null && loandetails.getLoanactivateflag() == 1) {
							if (loandetails.getLoanprinemiamt() != null) {
								gpfAdvanceII = loandetails.getLoanprinemiamt().doubleValue();
								Integer gpfAdviII = loandetails.getTotalRecoveredInstGpfII() + 1;
								Integer sanGPFAdvnsII = loandetails.getSancInstGpfII();
								gpfAdvInstII = gpfAdviII.toString() + "/" + sanGPFAdvnsII.toString();
								// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
							}
							// GPF_ADVANCE
							gpfAdvanceII = (double) (Math.round(gpfAdvanceII));
							paybillGenerationTrnDetails.setGpfAdvII(gpfAdvanceII);
							paybillGenerationTrnDetails.setGpfAdvIIInst(gpfAdvanceII);

						} else {
							paybillGenerationTrnDetails.setGpfAdvII(0d);
							gpfAdvanceII = (double) 0;

						}
					}

					// GPF_INST
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_INST)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_INST_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_INST_Code) {

							paybillGenerationTrnDetails
									.setGpfInst((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfInst = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setGpfInst((double) 0);
							gpfInst += 0;
						}
					}
					// Recovery

					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Recovery)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Recovery_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Recovery_Code) {

							paybillGenerationTrnDetails
									.setRecovery((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							recovery = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setRecovery((double) 0);
							recovery += 0;
						}
					}

					// Accidential_Policy
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_AccidentialPolicy)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_AccidentialPolicy_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_AccidentialPolicy_Code) {

							paybillGenerationTrnDetails.setAccidentPolicy(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							accidentPolicy = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setAccidentPolicy((double) 0);
							accidentPolicy += 0;
						}
					}

					/// House Advance
					/*
					 * else if (str.equalsIgnoreCase(
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT)) {
					 * 
					 * LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity =
					 * mstEmployeeService .findHBADetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
					 * 
					 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * if (lnaHBAEmployeeRequestMstEntity != null &&
					 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { // HouseAdv
					 * = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth(); HouseAdvInstAmt =
					 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
					 * 
					 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
					 * Integer sancHbaInst =
					 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
					 * hbai.toString() + "/" + sancHbaInst.toString();
					 * 
					 * } paybillGenerationTrnDetails.setHbaHouseIntAmt(HouseAdvInstAmt);
					 * paybillGenerationTrnDetails.setHbaHouseInst(hbaHouseInst); HouseAdvInstAmt =
					 * (double) (Math.round(HouseAdvInstAmt));
					 * 
					 * } else { paybillGenerationTrnDetails.setHbaHouseIntAmt(0d); HouseAdvInstAmt =
					 * (double) 0;
					 * 
					 * } }
					 */
					// NDCPS_REC
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC_Code) {

							paybillGenerationTrnDetails
									.setNdcpsRec((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							ndcpsRec = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setNdcpsRec((double) 0);
							ndcpsRec += 0;
						}
					}

					/*
					 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_HBA_HOUSE)) {
					 * 
					 * LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity =
					 * mstEmployeeService .findHBADetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
					 * 
					 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * if (lnaHBAEmployeeRequestMstEntity != null &&
					 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { HouseAdv =
					 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setHbaHouse(HouseAdv); HouseAdv =
					 * (double) (Math.round(HouseAdv));
					 * 
					 * } else { paybillGenerationTrnDetails.setHbaHouse(0d); HouseAdv = (double) 0;
					 * 
					 * } }
					 */

					break;
				case 3:
					// Credit Soc
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) {

							paybillGenerationTrnDetails
									.setRop((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							rop = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setRop((double) 0);
							rop += 0;

						}
					}

					// Recurring Dep
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE) {

							paybillGenerationTrnDetails.setRecurringDep(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							RecurringDep = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setRecurringDep((double) 0);
							RecurringDep += 0;
						}
					}
					// Cop_Bank
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE) {

							paybillGenerationTrnDetails.setCopBank(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							CopBank = (double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						} else {
							paybillGenerationTrnDetails.setCopBank((double) 0);
							CopBank += 0;
						}
					}
					// Other Deduction
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE) {

							paybillGenerationTrnDetails.setOtherDed(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							othrded = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setOtherDed((double) 0);
							othrded += 0;
						}
					}
					// MISC
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MISC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MISC_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MISC_CODE) {

							paybillGenerationTrnDetails
									.setMisc((double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							misc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setMisc((double) 0);
							misc += 0;
						}
					}
					// Allied_Soc
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code) {

							paybillGenerationTrnDetails.setAlliedSoc(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							alliedSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setAlliedSoc((double) 0);
							alliedSoc += 0;
						}
					}
					// Mantralaya_Soci
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code) {

							paybillGenerationTrnDetails.setMantralayaSoci(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							mantralayaSoci = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setMantralayaSoci((double) 0);
							mantralayaSoci += 0;
						}
					}
					// Chiplun_Soc
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code) {

							paybillGenerationTrnDetails.setChiplunSoc(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							chiplunSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setChiplunSoc((double) 0);
							chiplunSoc += 0;
						}
					}
					// Ulhasnagar_Soc
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code) {

							paybillGenerationTrnDetails.setUlhasnagarSoc(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							ulhasnagarSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setUlhasnagarSoc((double) 0);
							ulhasnagarSoc += 0;
						}
					}
					// Engr_Soc
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code) {

							paybillGenerationTrnDetails.setEngrSoc(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							engrSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setEngrSoc((double) 0);
							engrSoc += 0;
						}
					}
					// Satara_Society
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society_Code) {

							paybillGenerationTrnDetails.setSataraSoci(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							sataraSoci = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setSataraSoci((double) 0);
							sataraSoci += 0;
						}
					}
					// Bhagshree_Bank
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhagshree_Bank)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhagshree_Bank_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhagshree_Bank_Code) {

							paybillGenerationTrnDetails.setBhagshree_Bank(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							BhagshreeBank = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setBhagshree_Bank((double) 0);
							BhagshreeBank += 0;
						}
					}
					// Rajashri_Shahu
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Rajashri_Shahu)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Rajashri_Shahu_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Rajashri_Shahu_Code) {

							paybillGenerationTrnDetails.setRajashriShahu(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							rajashriShahu = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setRajashriShahu((double) 0);
							rajashriShahu += 0;
						}
					}
					// Parsik_Janata_Sh_Vasi
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Prasik_janata)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Prasik_janata_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Prasik_janata_Code) {

							paybillGenerationTrnDetails.setParsik_Janata_Sh_Vasi(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							Parsik_Janata_Sh_Vasi = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setRajashriShahu((double) 0);
							Parsik_Janata_Sh_Vasi += 0;
						}
					}
					// license
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_license_fee)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LF_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LF_CODE) {

							paybillGenerationTrnDetails.setLicense_Fee(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							license = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setLicense_Fee((double) 0);
							license += 0;
						}
					}
					// Service Charge
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE) {

							paybillGenerationTrnDetails.setServCharge(
									(double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
							ServCharge = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setServCharge((double) 0);
							ServCharge += 0;
						}
					} else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFLoanREC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFLoanREC_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFLoanREC_Code) {

							paybillGenerationTrnDetails.setGpfLoanRec(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							gpfLoanRec = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setGpfLoanRec((double) 0);
							gpfLoanRec += 0;
						}
					} else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety_Code) {

							paybillGenerationTrnDetails.setVangaonSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							vangaonSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setVangaonSoc((double) 0);
							vangaonSoc += 0;
						}
					}
					// panipuravtha_kolhapur
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code) {

							paybillGenerationTrnDetails.setPanipuravthaKolhapur(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							panipuravthaKolhapur = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setPanipuravthaKolhapur((double) 0);
							panipuravthaKolhapur += 0;
						}
					}
					// rajashrishahu_govbank_kolhapur
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur_Code) {

							paybillGenerationTrnDetails.setRajashreishahuGovKolahpur(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							rajshriGovbankKolhapur = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setRajashreishahuGovKolahpur((double) 0);
							rajshriGovbankKolhapur += 0;
						}
					}
					// Ahmednagar_pari_Abhiseva_Marya
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya_Code) {

							paybillGenerationTrnDetails.setAhdPariAbhiMarya(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							ahdPariAbhiMarya = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setAhdPariAbhiMarya((double) 0);
							ahdPariAbhiMarya += 0;
						}
					}
					// MJP_Soc_Beed
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed_Code) {

							paybillGenerationTrnDetails.setMjpSocBeed(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							mjpSocBeed = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setMjpSocBeed((double) 0);
							mjpSocBeed += 0;
						}
					}

					// Sal_owner_soc_Sangli
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli_Code) {

							paybillGenerationTrnDetails.setSalOwnSocSangli(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							salOwnSocSangli = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setSalOwnSocSangli((double) 0);
							salOwnSocSangli += 0;
						}
					}
					// Shaskiy_panipuravtha_soc_satara
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara_Code) {

							paybillGenerationTrnDetails.setShaskiyPanipurvSocSatara(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							shaskiyPaniPurvSocSatara = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setShaskiyPanipurvSocSatara((double) 0);
							shaskiyPaniPurvSocSatara += 0;
						}
					}
					// Hastantrit_pune_Mahan_soc
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc_Code) {

							paybillGenerationTrnDetails.setHastantritPunemahanSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							hastantritpune = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setHastantritPunemahanSoc((double) 0);
							hastantritpune += 0;
						}
					}
					// jalbhavan_soc_sangli
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli_Code) {

							paybillGenerationTrnDetails.setJalbhavanSocSangli(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							jalbhavanSocSangli = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setJalbhavanSocSangli((double) 0);
							jalbhavanSocSangli += 0;
						}
					}

					// akola_society
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society_Code) {

							paybillGenerationTrnDetails
									.setAkolaSoc((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							akolaSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setAkolaSoc((double) 0);
							akolaSoc += 0;
						}
					}
					// yavatmal_society
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society_Code) {

							paybillGenerationTrnDetails.setYavatmalSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							yavatmalSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setYavatmalSoc((double) 0);
							yavatmalSoc += 0;
						}
					}
					// nagari_sahakar_path_sansta
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta_Code) {

							paybillGenerationTrnDetails.setNagariSahaPantSansta(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							nagSahaPatSansta = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setNagariSahaPantSansta((double) 0);
							nagSahaPatSansta += 0;
						}
					}
					// engineering_society
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society_Code) {

							paybillGenerationTrnDetails
									.setEnggSoc((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							enggSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setEnggSoc((double) 0);
							enggSoc += 0;
						}
					}
					// daryapur_society
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society_Code) {

							paybillGenerationTrnDetails.setDaryapurSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							daryapurSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setDaryapurSoc((double) 0);
							daryapurSoc += 0;
						}
					}
					// public_health_society
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society_Code) {

							paybillGenerationTrnDetails.setPubHealthSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							pubhealSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setPubHealthSoc((double) 0);
							pubhealSoc += 0;
						}
					}
					// jalpradaya_society
					else if (str
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society_Code) {

							paybillGenerationTrnDetails.setJalpradayaSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							jalpradayaSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setJalpradayaSoc((double) 0);
							jalpradayaSoc += 0;
						}
					}
					// zilha_pari_karmachari_pantsanstha_buldhana
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana_Code) {

							paybillGenerationTrnDetails.setZilhaPariKarmPantBul(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							zilhaPariKarmPant = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setZilhaPariKarmPantBul((double) 0);
							zilhaPariKarmPant += 0;
						}
					}
					// jalna_soc
					else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc_Code) {

							paybillGenerationTrnDetails
									.setJalnaSoc((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							jalnaSoc = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setJalnaSoc((double) 0);
							jalnaSoc += 0;
						}
					}
					// amrawati_dist_engg_credit_soc
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc_Code) {

							paybillGenerationTrnDetails.setAmrawatiEnggCredSoc(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							amrawatidistEnggCredSoc = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setAmrawatiEnggCredSoc((double) 0);
							amrawatidistEnggCredSoc += 0;
						}
					}
					// Added by Vaibhav
					// BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code) {

							paybillGenerationTrnDetails.setBharatratnaVishweshAbhiSahPatMarya(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							bharatratnavishsolapur = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setBharatratnaVishweshAbhiSahPatMarya((double) 0);
							bharatratnavishsolapur += 0;
						}
					}
					// Added by Vaibhav
					// Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) {

							paybillGenerationTrnDetails
									.setBhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha(
											(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							bhanZillaParWaPanchayatSamitiSanstha = (double) (Math
									.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails
									.setBhandaraZillaParishadWPanchayatSamitiKaramachariSahakariSanstha((double) 0);
							bhanZillaParWaPanchayatSamitiSanstha += 0;
						}
					}
					// PUNE_DIST_CENTRAL_COP_BANK
					else if (str.equalsIgnoreCase(
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK_Code);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK_Code) {

							paybillGenerationTrnDetails.setPuneDistCentCopBnk(
									(double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							puneDistCentCoBnk = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						} else {
							paybillGenerationTrnDetails.setPuneDistCentCopBnk((double) 0);
							puneDistCentCoBnk += 0;
						}
					}

					/*
					 * "jalpradaya_society"
					 */
					/*
					 * // Other Recovery
					 * 
					 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_OTHER_REC)) { EmployeeAllowDeducComponentAmtEntity
					 * employeeAllowDeducComponentAmtEntity = mstEmployeeService
					 * .findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);
					 * 
					 * if (employeeAllowDeducComponentAmtEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {
					 * 
					 * paybillGenerationTrnDetails.setOtherRec( (double)
					 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))); otherRec =
					 * (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())); }
					 * else { paybillGenerationTrnDetails.setOtherRec((double) 0); otherRec += 0; }
					 * }
					 */
					break;
				case 4:
					// CO_HSG_SOC
					if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC)) {
						EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
								.findGRPComponentsData(mstEmployeeEntity2.getSevaarthId(),
										CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE);

						if (employeeAllowDeducComponentAmtEntity != null
								&& allowDeducCode == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE) {

							paybillGenerationTrnDetails
									.setCoHsgSoc((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
							coHsg = (double) (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						} else {
							paybillGenerationTrnDetails.setCoHsgSoc((double) 0);
							coHsg = (double) 0;
						}
					}
					// for GPF_ADV_GRP_ABC
					/*
					 * else if (str.equalsIgnoreCase(
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC)) { int
					 * loanActive = 0; int mon = 0; int yer = 0; int curryear = 0; mon =
					 * paybillHeadMpgModel.getPaybillMonth(); yer =
					 * paybillHeadMpgModel.getPaybillYear();
					 * 
					 * BigDecimal monthcurr = BigDecimal.valueOf(mon); BigDecimal yearcurr =
					 * BigDecimal.valueOf(yer);
					 * 
					 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
					 * mstEmployeeService.findLoanDetails( mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
					 * List<Object[]> yearinfo = commonHomeMethodsService.findyearinfo(yearcurr);
					 * for (Object[] yearLst : yearinfo) { curryear = (Integer) yearLst[1]; }
					 * 
					 * PaybillLoanRecoverDtlsEntity paybillLoanRecoverDtlsEntity =
					 * mstEmployeeService
					 * .findInstallmentNoForCurrMont(mstEmployeeEntity2.getSevaarthId(), mon,
					 * curryear); if (paybillLoanRecoverDtlsEntity != null) { if
					 * (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigDecimal gpfabc =
					 * null; Double gpfd = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt() !=
					 * null) { gpfabc = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfd =
					 * gpfabc.doubleValue(); //
					 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setGpfAdvGrpAbc(gpfd); GpfABC =
					 * (double) (Math.round(gpfd)); } else {
					 * paybillGenerationTrnDetails.setGpfAdvGrpAbc(0d); GpfABC = (double) 0; }
					 * 
					 * } else {
					 * 
					 * if (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigDecimal gpfabc =
					 * null; Double gpfd = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt() !=
					 * null) { gpfabc = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfd =
					 * gpfabc.doubleValue();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD);
					 * mstEmployeeService.saveEmpLoanData(mstEmployeeEntity2.getSevaarthId(), gpfd,
					 * mon, curryear); } paybillGenerationTrnDetails.setGpfAdvGrpAbc(gpfd); GpfABC =
					 * (double) (Math.round(gpfd)); } else {
					 * paybillGenerationTrnDetails.setGpfAdvGrpAbc(0d); GpfABC = (double) 0; } } }
					 */
					/// GPF_ADV_GRP_D
					/*
					 * else if (str .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_GPF_ADV_GRP_D)) { int loanActive = 0; int mon = 0; int
					 * yer = 0; int curryear = 0; mon = paybillHeadMpgModel.getPaybillMonth(); yer =
					 * paybillHeadMpgModel.getPaybillYear();
					 * 
					 * BigDecimal monthcurr = BigDecimal.valueOf(mon); BigDecimal yearcurr =
					 * BigDecimal.valueOf(yer); List<Object[]> yearinfo =
					 * commonHomeMethodsService.findyearinfo(yearcurr); for (Object[] yearLst :
					 * yearinfo) { curryear = (Integer) yearLst[1]; }
					 * 
					 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
					 * mstEmployeeService.findLoanDetails( mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE);
					 * 
					 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * PaybillLoanRecoverDtlsEntity paybillLoanRecoverDtlsEntity =
					 * mstEmployeeService
					 * .findInstallmentNoForCurrMont(mstEmployeeEntity2.getSevaarthId(), mon,
					 * curryear); if (paybillLoanRecoverDtlsEntity != null) {
					 * 
					 * if (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigDecimal gpfAdvD =
					 * null; Double gpfGrpAdvD = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt()
					 * != null) { gpfAdvD = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfGrpAdvD =
					 * gpfAdvD.doubleValue();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setGpfAdvGrpD(gpfGrpAdvD);
					 * GpfAdvDLoan = (double) (Math.round(gpfGrpAdvD));
					 * 
					 * } else { paybillGenerationTrnDetails.setGpfAdvGrpD(0d); GpfAdvDLoan =
					 * (double) 0;
					 * 
					 * } } else { // TODO if (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigDecimal gpfAdvD =
					 * null; Double gpfGrpAdvD = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt()
					 * != null) { gpfAdvD = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfGrpAdvD =
					 * gpfAdvD.doubleValue();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD);
					 * mstEmployeeService.saveEmpLoanData(mstEmployeeEntity2.getSevaarthId(),
					 * gpfGrpAdvD, mon, curryear); }
					 * paybillGenerationTrnDetails.setGpfAdvGrpD(gpfGrpAdvD); GpfAdvDLoan = (double)
					 * (Math.round(gpfGrpAdvD));
					 * 
					 * } else { paybillGenerationTrnDetails.setGpfAdvGrpD(0d); GpfAdvDLoan =
					 * (double) 0;
					 * 
					 * } } }
					 */

					/*
					 * else if (str .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_EXC_PAYRC)) { int loanActive = 0; int mon = 0; int yer =
					 * 0; int curryear = 0; mon = paybillHeadMpgModel.getPaybillMonth(); yer =
					 * paybillHeadMpgModel.getPaybillYear(); BigDecimal monthcurr =
					 * BigDecimal.valueOf(mon); BigDecimal yearcurr = BigDecimal.valueOf(yer);
					 * 
					 * List<Object[]> yearinfo = commonHomeMethodsService.findyearinfo(yearcurr);
					 * for (Object[] yearLst : yearinfo) { curryear = (Integer) yearLst[1]; }
					 * 
					 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
					 * mstEmployeeService.findLoanDetails( mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_EXC_PAYRC_CODE);
					 * 
					 * PaybillLoanRecoverDtlsEntity paybillLoanRecoverDtlsEntity =
					 * mstEmployeeService
					 * .findInstallmentNoForCurrMont(mstEmployeeEntity2.getSevaarthId(), mon,
					 * curryear); if (paybillLoanRecoverDtlsEntity != null) {
					 * 
					 * if (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_EXC_PAYRC_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigDecimal exc = null;
					 * Double excPayLoan = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt() !=
					 * null) { exc = loanEmployeeDtlsEntity.getLoanprinemiamt(); excPayLoan =
					 * exc.doubleValue();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setExcPayRc(excPayLoan); excPayRc =
					 * (double) (Math.round(excPayLoan));
					 * 
					 * } else { paybillGenerationTrnDetails.setExcPayRc(0d); excPayRc = (double) 0;
					 * 
					 * } } else { // TODO if (loanEmployeeDtlsEntity != null && allowDeducCode ==
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_EXC_PAYRC_CODE &&
					 * loanEmployeeDtlsEntity.getLoanactivateflag() == 1) { BigInteger exc = null;
					 * Double excPayLoan = 0d; if (loanEmployeeDtlsEntity.getLoanprinemiamt() !=
					 * null) { exc = loanEmployeeDtlsEntity.getLoanprinemiamt(); excPayLoan =
					 * exc.doubleValue();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); //
					 * mstEmployeeService.saveEmpLoanData(mstEmployeeEntity2.getSevaarthId(),
					 * excPayLoan, // mon, curryear); }
					 * paybillGenerationTrnDetails.setExcPayRc(excPayLoan); excPayRc = (double)
					 * (Math.round(excPayLoan));
					 * 
					 * } else { paybillGenerationTrnDetails.setExcPayRc(0d); excPayRc = (double) 0;
					 * 
					 * } } }
					 */

					/// House Advance
					/*
					 * else if (str.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_HBA_HOUSE)) {
					 * 
					 * LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity =
					 * mstEmployeeService .findHBADetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
					 * 
					 * 
					 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * 
					 * if (lnaHBAEmployeeRequestMstEntity != null &&
					 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { ///
					 * HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth(); HouseAdv =
					 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
					 * 
					 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
					 * Integer sancHbaInst =
					 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
					 * hbai.toString() + "/" + sancHbaInst.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setHbaHouseInst(hbaHouseInst);
					 * paybillGenerationTrnDetails.setHbaHouse(HouseAdv); ///
					 * paybillGenerationTrnDetails.setHbaHouseIntAmt(HouseAdvInstAmt);; HouseAdv =
					 * (double) (Math.round(HouseAdv));
					 * 
					 * } else { paybillGenerationTrnDetails.setHbaHouse(0d); HouseAdv = (double) 0;
					 * 
					 * } }
					 */

					/// Motor Vehical Advance
					/*
					 * else if (str .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_OTHER_VEH_ADV)) {
					 * 
					 * LNAVAEmployeeRequestMstEntity lnaVAEmployeeRequestMstEntity =
					 * mstEmployeeService .findVADetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code);
					 * 
					 * System.out .println("mstEmployeeEntity2.getEmployeeId()" +
					 * mstEmployeeEntity2.getEmployeeId());
					 * 
					 * if (lnaVAEmployeeRequestMstEntity != null &&
					 * lnaVAEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt() != null) { vehAdv =
					 * lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt();
					 * 
					 * Integer vi = lnaVAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
					 * Integer sancVehInst =
					 * lnaVAEmployeeRequestMstEntity.getSancPrincipalInstallMCA(); otherVehAdvInst =
					 * vi.toString() + "/" + sancVehInst.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setOthrVehAdvInst(otherVehAdvInst);
					 * paybillGenerationTrnDetails.setOtherRec(vehAdv); vehAdv = (double)
					 * (Math.round(vehAdv));
					 * 
					 * } else { paybillGenerationTrnDetails.setOtherRec(0d); vehAdv = (double) 0;
					 * 
					 * } }
					 */

					// Excess_Pay_Rec
					/*
					 * else if (str .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
					 * COMMONCODE_COMPONENT_Excess_Pay_Rec)) {
					 * 
					 * ExcessPayRecoveryEntity excessPayRecoveryEntity = mstEmployeeService
					 * .findExcPayRec(mstEmployeeEntity2.getSevaarthId());
					 * 
					 * if (excessPayRecoveryEntity != null && excessPayRecoveryEntity.getIsActive()
					 * == '1') { if (excessPayRecoveryEntity.getPrinEmiAmt() != null) { excessPayrec
					 * = excessPayRecoveryEntity.getPrinEmiAmt();
					 * 
					 * Integer excInst = excessPayRecoveryEntity.getLoanPrinInstNo() + 1; Integer
					 * sancExcInst = excessPayRecoveryEntity.getTotalInstNo(); excessPayrecInst =
					 * excInst.toString() + "/" + sancExcInst.toString(); }
					 * paybillGenerationTrnDetails.setExcessPayrecint(excessPayrecInst);
					 * paybillGenerationTrnDetails.setExcessPayrec(excessPayrec); excessPayrec =
					 * (double) (Math.round(excessPayrec));
					 * 
					 * } else { paybillGenerationTrnDetails.setExcessPayrec(0d); excessPayrec =
					 * (double) 0;
					 * 
					 * } }
					 */

					/// Motor Cycle Advance
					/*
					 * else if (str.equalsIgnoreCase(
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE)) {
					 * 
					 * LNAMotorVehicleAdvEmployeeRequestMstEntity
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity = mstEmployeeService
					 * .findmotorCycleAdvDetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code);
					 * 
					 * if (lnaMotorVehicleAdvEmployeeRequestMstEntity != null &&
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaMotorVehicleAdvEmployeeRequestMstEntity.getPrinInstallmentAmountMCA() !=
					 * null) { /// HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth();
					 * motorCycleAdvInst = lnaMotorVehicleAdvEmployeeRequestMstEntity
					 * .getPrinInstallmentAmountMCA(); Integer motorInst =
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity .getNoOfInstallmentsPaid() + 1;
					 * Integer sancmotorCycleInst = lnaMotorVehicleAdvEmployeeRequestMstEntity
					 * .getSancPrincipalInstallMCA(); motorCycleInst = motorInst.toString() + "/" +
					 * sancmotorCycleInst.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); } paybillGenerationTrnDetails.setMotorVehAdvInst(motorCycleInst);
					 * paybillGenerationTrnDetails.setMotorvehicleAdvance(motorCycleAdvInst); ///
					 * paybillGenerationTrnDetails.setHbaHouseIntAmt(HouseAdvInstAmt);;
					 * motorCycleAdv = (double) (Math.round(motorCycleAdvInst));
					 * 
					 * } else { paybillGenerationTrnDetails.setHbaHouse(0d); motorCycleAdv =
					 * (double) 0;
					 * 
					 * } }
					 */

					/// Motor Vehicle Advance Installment Amount
					/*
					 * else if (str.equalsIgnoreCase(
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Motor_Veh_Adv_Inst)) {
					 * 
					 * LNAMotorVehicleAdvEmployeeRequestMstEntity
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity = mstEmployeeService
					 * .findmotorCycleAdvDetails(mstEmployeeEntity2.getEmployeeId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code);
					 * 
					 * if (lnaMotorVehicleAdvEmployeeRequestMstEntity != null &&
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity.getIsActive() == 1) { if
					 * (lnaMotorVehicleAdvEmployeeRequestMstEntity.getInterInstallmentAmountMCA() !=
					 * null) { /// HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth();
					 * motorCycleAdvIntAmt = lnaMotorVehicleAdvEmployeeRequestMstEntity
					 * .getPrinInstallmentAmountMCA();
					 * 
					 * Integer motorInst = lnaMotorVehicleAdvEmployeeRequestMstEntity
					 * .getNoOfInstallmentsPaid() + 1; Integer sancmotorCycleInst =
					 * lnaMotorVehicleAdvEmployeeRequestMstEntity .getSancPrincipalInstallMCA();
					 * motorCycleInst = motorInst.toString() + "/" + sancmotorCycleInst.toString();
					 * 
					 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
					 * gpfAdvD); }
					 * paybillGenerationTrnDetails.setMotorVehAdvInstAmt(motorCycleAdvIntAmt);
					 * paybillGenerationTrnDetails.setMotorVehAdvInst(motorCycleInst);
					 * motorCycleAdvIntAmt = (double) (Math.round(motorCycleAdvIntAmt));
					 * 
					 * } else { paybillGenerationTrnDetails.setMotorVehAdvInstAmt(0d);
					 * motorCycleAdvIntAmt = (double) 0;
					 * 
					 * } }
					 */

					break;
				}
			}
			paybillGenerationTrnDetails.setSevaarthId(mstEmployeeEntity2.getSevaarthId());
			paybillGenerationTrnDetails.setDaPercent(percentage);
			if (percentageHRA != null) {
				paybillGenerationTrnDetails.setHraPercent(Integer.parseInt(percentageHRA));
			}

			Double basic = 0d;
			if (mstEmployeeEntity2.getRevisedBasic() != null) {
				basic = mstEmployeeEntity2.getRevisedBasic();
			} else if (SevenPcBasic != null) {
				basic = SevenPcBasic.doubleValue();
			} else {
				basic = mstEmployeeEntity2.getBasicPay();
			}

			if (mstEmployeeEntity2.getPayCommissionCode() == 700016 && SixPcBasic > 0) {
				basic = SixPcBasic;
			}

			paybillGenerationTrnDetails.setBasicPay(basic);

			double Total = (basic + hra + da + svnDA + dcps_empr + ta + tribalAllow + transAllowArr + DaArr + addHRA
					+ naxalAreaAllow + spclDutyAllow + otherAllow + basicArr + specialPay + personalPay + cla
					+ dearnessPay + conveyAllow + DaOnTA + contriProvFund + excPayRc + npsEmprAllow + emprDcpsDaArr
					+ emprDcpsDelayedRec + emprDcpsPayArr + emprDcpsRegRec + hra6th + hra5th + ta5th + ta6th + wa
					+ deputAllow + ota + hillStatAllow + tracerAllow + naksaliedAllow + GpfArrears + arrears + leavePay
					+ payandallarr);

			paybillGenerationTrnDetails.setGrossTotalAmt((double) Math.round(Total));

			double dedByAG = gpfGrpABC + gpfGrpD + GpfAbcArr + gpfDArr + GpfAdvDLoan + GpfABC + gisAmount ;
			paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));

			double dedByTreasury = GisZp + it + groupAccPolicy + pt + coHsg + GpfAdvDLoan + npsEmpContri
					+ npsEmprContri + cmFund + excessPayment + empDcpsReg + empDcpsPayArr + empDcpsDelay + empDcpsDaArr
					+ revenueStamp + socOrBankLoan + BLWF + NDCPSsubscrp + GpfSpclArrears + hba + beigs + gpfSubscrb
					+ gpfDaSub + rop + payFixDiff + nps + hrr + empContri + lic + gpfInst + recovery + otherRec
					+ accidentPolicy + gpfAdvanceII + ndcpsRec;
			paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

			// double dedByOthr = dedByTreasury + dedByAG ;
			double dedByOthr = CA + FA + HouseAdv + vehAdv + gpfAdvance + alliedSoc + mantralayaSoci + chiplunSoc
					+ ulhasnagarSoc + engrSoc + CreditSoc + CopBank + othrded + RecurringDep + rajashriShahu + license + sataraSoci + Parsik_Janata_Sh_Vasi + payfixAdv + ServCharge
					+ BhagshreeBank + gpfLoanRec + vangaonSoc + panipuravthaKolhapur + rajshriGovbankKolhapur
					+ ahdPariAbhiMarya + HouseAdvInstAmt + mjpSocBeed + salOwnSocSangli + shaskiyPaniPurvSocSatara
					+ hastantritpune + jalbhavanSocSangli + akolaSoc + yavatmalSoc + nagSahaPatSansta + enggSoc
					+ daryapurSoc + pubhealSoc + jalpradayaSoc + zilhaPariKarmPant +   jalnaSoc
					+ amrawatidistEnggCredSoc + puneDistCentCoBnk + bharatratnavishsolapur + motorCycleAdv
					+ motorCycleAdvIntAmt + bhanZillaParWaPanchayatSamitiSanstha;
			paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));

			double Totaldeduc = dedByAG + dedByTreasury + dedByOthr;

			/*
			 * double Totaldeduc = (pt + dcpsDa + dcpsDelay + dcpsPayArr + dcpsReg + dcpsArr
			 * + adjust_dcps_empr + gisAmount + gpfGrpABC + gpfGrpD + GpfAbcArr +
			 * groupAccPolicy + gpfDArr + ptArr + otherDeduc + GisZp + hrr + otherRec +
			 * GpfAdvDLoan + GpfABC + it + ServCharge + revenueStamp + excessPayment +
			 * cmFund + npsEmprContri + npsEmpContri + HouseAdv + CA + FA + vehAdv);
			 */

			paybillGenerationTrnDetails.setTotalDeduction((double) Math.round(Totaldeduc));

			/*
			 * paybillGenerationTrnDetails.setTotalNetAmt(paybillGenerationTrnDetails.
			 * getBasicPay() + da + hra + dcps_empr - pt - adjust_dcps_empr - dcpsReg -
			 * dcpsArr - dcpsDa - dcpsDelay - dcpsPayArr - gisAmount);
			 */
			double Net = Total - Totaldeduc;

			paybillGenerationTrnDetails.setTotalNetAmt((double) Math.round(Net));
			paybillGenerationTrnDetails.setPayCommissionCode(mstEmployeeEntity2.getPayCommissionCode());
			if (mstEmployeeEntity2.getPayCommissionCode() == 700005) {
					paybillGenerationTrnDetails.setSevenPcLevel(mstEmployeeEntity2.getSevenPcLevel());
			} else {
				if (mstEmployeeEntity2.getPayInPayBand() != null)
					paybillGenerationTrnDetails.setPay_band(mstEmployeeEntity2.getPayInPayBand());
			}
			paybillGenerationTrnDetails.setPaybillGenerationTrnId(val);
			Serializable id12 = paybillHeadMpgRepo.saveHrPayPaybill(paybillGenerationTrnDetails);
			/*
			 * grossAmt += paybillGenerationTrnDetails.getBasicPay() + da + hra + dcps_empr;
			 */
			grossAmt += basic + hra + da + svnDA + dcps_empr + ta + tribalAllow + transAllowArr + DaArr + addHRA
					+ naxalAreaAllow + spclDutyAllow + otherAllow + basicArr + specialPay + personalPay + cla
					+ dearnessPay + conveyAllow + DaOnTA + contriProvFund + excPayRc + npsEmprAllow + emprDcpsDaArr
					+ emprDcpsDelayedRec + emprDcpsPayArr + emprDcpsRegRec + hra6th + hra5th + ta5th + ta6th + wa
					+ deputAllow + ota + hillStatAllow + tracerAllow + naksaliedAllow + GpfArrears + arrears + leavePay
					+ payandallarr;
			/*
			 * netAmt += paybillGenerationTrnDetails.getBasicPay() + da + hra + dcps_empr -
			 * pt - adjust_dcps_empr - dcpsReg - dcpsArr - dcpsDa - dcpsDelay - dcpsPayArr -
			 * gisAmount;
			 */
			netAmt += Total - Totaldeduc;
			// grossAmt = 0d;
			// netAmt = 0d;
			da = 0d;
			hra = 0d;
			cityClass = null;
			hra6th = 0d;
			hra5th = 0d;
			pt = 0d;
			dcps_empr = 0d;
			dcps_empr1 = 0d;// Added for testing nps
			grossAdjust = 0d;
			dedAdjust = 0d;
			adjust_dcps_empr = 0d;
			adjust_dcps_empr1 = 0d;// Added for testing nps
			dcpsDelay = 0d;
			dcpsDa = 0d;
			dcpsPayArr = 0d;
			dcpsArr = 0d;
			dcpsReg = 0d;
			gisAmount = 0d;
			gpfGrpD = 0d;
			GpfAbcArr = 0d;
			gpfGrpABC = 0d;
			ta = 0d;
			ta6th = 0d;
			ta5th = 0d;
			tribalAllow = 0d;
			transAllowArr = 0d;
			groupAccPolicy = 0d;
			gpfDArr = 0d;
			DaArr = 0d;
			addHRA = 0d;
			naxalAreaAllow = 0d;
			specialPay = 0d;
			otherAllow = 0d;
			spclDutyAllow = 0d;
			hrr = 0d;
			personalPay = 0d;
			otherDeduc = 0d;
			contriProvFund = 0d;
			basicArr = 0d;
			cla = 0d;
			dearnessPay = 0d;
			conveyAllow = 0d;
			DaOnTA = 0d;
			ptArr = 0d;
			GisZp = 0d;
			otherRec = 0d;
			it = 0d;
			ServCharge = 0d;
			CopBank = 0d;
			RecurringDep = 0d;
			lic = 0d;
			CreditSoc = 0d;
			svnDA = 0d;
			othrded = 0d;
			coHsg = 0d;
			GpfABC = 0d;
			GpfAdvDLoan = 0d;
			misc = 0d;
			excPayRc = 0d;
			SevenPcBasic = 0d;
			SixPcBasic = 0d;
			revenueStamp = 0d;
			excessPayment = 0d;
			cmFund = 0d;
			npsEmprAllow = 0d;
			npsEmprContri = 0d;
			npsEmpContri = 0d;
			HouseAdv = 0d;
			CA = 0d;
			FA = 0d;
			vehAdv = 0d;
			emprDcpsDaArr = 0d;
			emprDcpsDelayedRec = 0d;
			emprdcpsDelay = 0d;
			emprDcpsPayArr = 0d;
			emprDcpsReg = 0d;
			emprDcpsRegRec = 0d;
			empDcpsDaArr = 0d;
			empDcpsDelay = 0d;
			empDcpsPayArr = 0d;
			empDcpsReg = 0d;
			gpfAdvance = 0d;
			deputAllow = 0d;
			ota = 0d;
			hillStatAllow = 0d;
			tracerAllow = 0d;
			naksaliedAllow = 0d;
			wa = 0d;
			gpfSubscrb = 0d;
			hba = 0d;
			socOrBankLoan = 0d;
			BLWF = 0d;
			GpfArrears = 0d;
			GpfSpclArrears = 0d;
			NDCPSsubscrp = 0d;
			beigs = 0d;
			alliedSoc = 0d;
			mantralayaSoci = 0d;
			chiplunSoc = 0d;
			ulhasnagarSoc = 0d;
			engrSoc = 0d;
			gpfDaSub = 0d;
			rop = 0d;
			payFixDiff = 0d;
			nps = 0d;
			empContri = 0d;
			emprContri = 0d;
			gpfInst = 0d;
			arrears = 0d;
			begis = 0d;
			sataraSoci = 0d;
			BhagshreeBank = 0d;
			rajashriShahu = 0d;
			Parsik_Janata_Sh_Vasi = 0d;
			license = 0d;
			payfixAdv = 0d;
			HouseAdvInstAmt = 0d;
			vangaonSoc = 0d;
			accidentPolicy = 0d;
			panipuravthaKolhapur = 0d;
			rajshriGovbankKolhapur = 0d;
			ahdPariAbhiMarya = 0d;
			mjpSocBeed = 0d;
			salOwnSocSangli = 0d;
			shaskiyPaniPurvSocSatara = 0d;
			hastantritpune = 0d;
			jalbhavanSocSangli = 0d;
			leavePay = 0d;
			yavatmalSoc = 0d;
			gpfLoanRec = 0d;
			recovery = 0d;
			akolaSoc = 0d;
			pt = 0d;
			jalnaSoc = 0d;
			amrawatidistEnggCredSoc = 0d;
			gpfAdvanceII = 0d;
			ndcpsRec = 0d;
			puneDistCentCoBnk = 0d;
			payandallarr = 0d;
			bharatratnavishsolapur = 0d;
			motorCycleAdv = 0d;
			motorCycleAdvInst = 0d;
			motorCycleAdvIntAmt = 0d;
			bhanZillaParWaPanchayatSamitiSanstha = 0d;

			}
		}
		objEntity.setBillGrossAmt((double) Math.round(grossAmt));
		objEntity.setBillNetAmount((double) Math.round(netAmt));
		objEntity.setPaybillGenerationTrnId(val);

		Serializable id = paybillHeadMpgRepo.savePaybillHeadMpg(objEntity);
		Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);

		return (Long) id;
	}

	private double valueOf(Object object) {

		return 0;
	}

	@Override
	public PaybillGenerationTrnEntity findForwardChangeStatementById(Long paybillGenerationTrnId, Long userId,
			String ip) {

		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(2); // Change Statement Forwarded
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(2);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);
			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findApproveChangeStatementById(Long paybillGenerationTrnId, Long userId,
			String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(3); // Change Statement Approved
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(3);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findRejectChangeStatementById(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(4); // Change Statement Rejected
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(4);
			//// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findDeleteBillById(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(8); // Deleted
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(8);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity findForwardBillById(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findForwardChangeStatementById(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(5); // Paybill Generated
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(5);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public PaybillGenerationTrnEntity forwardPayBillToLevel2(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.forwardPayBillToLevel2(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();
		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(6); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(6);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	/* Added by Brijoy 24-12-2020 by Generate Paybill */
	@Override
	public PaybillGenerationTrnEntity generatePaybill(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.forwardPayBillToLevel2(paybillGenerationTrnId);

		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(5); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(5);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);

			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public int getpaybilldata(BigInteger billGroup, int paybillMonth, int paybillYear) {
		// TODO Auto-generated method stub
		int isDataExists = paybillHeadMpgRepo.isPaybillExists(billGroup, paybillMonth, paybillYear);
		return isDataExists;
	}

	public List<Object[]> findpaybill(BigInteger billGroup, int paybillMonth, int paybillYear, String ddo) {
		//
		return paybillHeadMpgRepo.findpaybill(billGroup, paybillMonth, paybillYear, ddo);

	}

	@Override
	public List<Object[]> getChangeStatementReport(String paybillGenerationTrnId) {
		List<Object[]> obj = paybillHeadMpgRepo.getChangeStatementReport(paybillGenerationTrnId);
		return obj;
	}

	@Override
	public List<Object[]> getChangeStatementReportFromPreviousMonth(String paybillGenerationTrnId) {
		List<Object[]> obj = paybillHeadMpgRepo.getChangeStatementReportFromPreviousMonth(paybillGenerationTrnId);
		return obj;
	}

	// @Override
	// public int getpaybilldata(String billGroup, String paybillMonth, String
	// paybillYear) {
	// // TODO Auto-generated method stub
	// return 0;
	// }

	public PaybillGenerationTrnEntity consolidatedPaybill(Long paybillGenerationTrnId, Long userId, String ip) {
		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.consolidatedPaybill(paybillGenerationTrnId);
		PaybillStatusEntity paybillStatusEntity = new PaybillStatusEntity();

		if (objPaybillGeberationTrnEntity != null) {
			objPaybillGeberationTrnEntity.setIsActive(9); // Paybill Forwarded To Level 2
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);

			// paybill status updation//
			InetAddress local = null;
			try {
				local = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			paybillStatusEntity.setBillNo(paybillGenerationTrnId);
			paybillStatusEntity.setCreatedDate(new Date());
			paybillStatusEntity.setIsActive(9);
			/// paybillStatusEntity.setUserId(userId);
			paybillStatusEntity.setMacId(ip);
			paybillHeadMpgRepo.updateForwardChangeStatementStatus(objPaybillGeberationTrnEntity);
			Serializable id3 = paybillHeadMpgRepo.savePaybillStatus(paybillStatusEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public List<Object[]> findDDOinfo(String userName) {
		List<Object[]> obj = paybillHeadMpgRepo.findDDOinfo(userName);
		return obj;
	}

	@Override
	public List<Object[]> findregIdinfo(Long regid) {
		List<Object[]> obj = paybillHeadMpgRepo.findregIdinfo(regid);
		return obj;
	}

	@Override
	public List<Object[]> findmonthinfo(BigInteger currmonth) {
		List<Object[]> obj = paybillHeadMpgRepo.findmonthinfo(currmonth);
		return obj;
	}

	@Override
	public List<Object[]> findofcIdinfo(Long ofcid) {
		List<Object[]> obj = paybillHeadMpgRepo.findofcIdinfo(ofcid);
		return obj;
	}

	@Override
	public List<Object[]> getAbstractReport(String paybillGenerationTrnId) {
		List<Object[]> obj = paybillHeadMpgRepo.getAbstractReport(paybillGenerationTrnId);
		return obj;
	}

	@Override
	public List<Object[]> findyearinfo(BigInteger yearcurr) {
		List<Object[]> obj = paybillHeadMpgRepo.findyearinfo(yearcurr);
		return obj;
	}

	// Created by Manikandan:Supplimentaory Paybill Generation Method
	@SuppressWarnings("unused")
	@Override
	public Long saveSupPaybillHeadMpg(PaybillHeadMpgModel paybillHeadMpgModel) {
		// List<Object[]> isBrokenPeriodEmpty(String sevaarthid,String monthid,String
		// yearid )
		PaybillGenerationTrnEntity objEntity = new PaybillGenerationTrnEntity();
		PaybillGenerationTrnDetails hr = new PaybillGenerationTrnDetails();

		double grossAmt = 0;
		double netAmt = 0;
		double da = 0;
		double hra = 0;
		double hra6th = 0;
		double hra5th = 0;
		double pt = 0;
		double dcps_empr = 0;
		double dcps_empr1 = 0;// Added for testing nps
		double grossAdjust = 0;
		double dedAdjust = 0;
		double adjust_dcps_empr = 0;
		double adjust_dcps_empr1 = 0;// Added for testing nps
		double dcpsDelay = 0;
		double dcpsDa = 0;
		double dcpsPayArr = 0;
		double dcpsArr = 0;
		double dcpsReg = 0;
		double gisAmount = 0;
		double gradePay = 0;
		double gpfGrpD = 0;
		double GpfAbcArr = 0;
		double gpfGrpABC = 0;
		double ta = 0;
		double ta6th = 0;
		double ta5th = 0;
		double tribalAllow = 0;
		double transAllowArr = 0;
		double groupAccPolicy = 0;
		double gpfDArr = 0;
		double GisZp = 0;
		double ptArr = 0;
		double otherDeduc = 0;
		double hrr = 0;
		double otherRec = 0;
		double basicArr = 0;
		double DaArr = 0;
		double specialPay = 0;
		double personalPay = 0;
		double dearnessPay = 0;
		double conveyAllow = 0;
		double DaOnTA = 0;
		double cla = 0;
		double svnDA = 0;
		double ServCharge = 0;
		double it = 0;
		double naxalAreaAllow = 0;
		Double npsEmprAllow = 0d;
		Double npsEmprContri = 0d;
		Double npsEmpContri = 0d;
		Double HouseAdv = 0d;
		Double CA = 0d;
		Double FA = 0d;
		Double gpfAdvance = 0d;
		Double vehAdv = 0d;
		Double cmFund = 0d;
		Double emprDcpsDaArr = 0d;
		Double emprdcpsDelay = 0d;
		Double emprDcpsPayArr = 0d;
		Double emprDcpsReg = 0d;
		Double deputAllow = 0d;
		Double ota = 0d;
		Double hillStatAllow = 0d;
		Double tracerAllow = 0d;
		Double naksaliedAllow = 0d;
		Double wa = 0d;
		Double gpfSubscrb = 0d;
		Double hba = 0d;
		Double socOrBankLoan = 0d;
		Double BLWF = 0d;
		Double GpfArrears = 0d;
		Double GpfSpclArrears = 0d;
		Double NDCPSsubscrp = 0d;
		Double alliedSoc = 0d;
		Double mantralayaSoci = 0d;
		Double chiplunSoc = 0d;
		Double ulhasnagarSoc = 0d;
		Double engrSoc = 0d;
		Double gpfDaSub = 0d;
		Double rop = 0d;
		Double payFixDiff = 0d;
		Double nps = 0d;
		Double pubHealWrks = 0d;
		Double sindhuOras = 0d;
		Double jalgaonSoc = 0d;
		Double manaBhaiMehJal = 0d;
		Double akolaPAriAbhi = 0d;
		Double zpKarmPat = 0d;
		Double vidharbhaGramkokBn = 0d;
		Double chandaSoc = 0d;
		Double jalsevaSocNag = 0d;
		Double bhandaraSoc = 0d;
		Double gdccBank = 0d;
		Double gondiaSoc = 0d;
		Double nagpurSoc = 0d;
		Double allahabadSoc = 0d;
		Double bhanDistCenCopBnk = 0d;
		Double bankOfBarora = 0d;
		Double courtComput = 0d;
		Double jalgaonGSSoc = 0d;
		Double jalgaonHandiSoc = 0d;
		Double dhulenandurbarBnk = 0d;
		Double parisarAbhiSocNash = 0d;
		Double sarwAroBanSoc = 0d;
		Double jalSocCL3 = 0d;
		Double panipurvtaSocCL3or4 = 0d;
		Double govBank = 0d;
		Double sangliSalSoc = 0d;
		Double mjpSoc = 0d;
		Double nashikRoadSocCL3or4 = 0d;
		Double jalsevaMalSocCL3 = 0d;
		Double nashikBankSoc = 0d;
		Double mandaNashikSoc = 0d;
		Double ujwalaMahilaPatBhan = 0d;
		Double bcQuar = 0d;
		Double excessPayrec = 0d;
		Double flagDay = 0d;
		Double bhandJilAbhiKarPat = 0d;
		Double jalsevaKarmSahaPath = 0d;
		Double socNanded = 0d;
		Double socAurang = 0d;
		Double socLatur = 0d;
		Double mlwfOnlyMJP = 0d;
		Double mahaLabWelFund = 0d;
		Double mjpLatur = 0d;
		Double jalbhavanSoc = 0d;
		Double mjpSocSolapur = 0d;
		Double begis = 0d;
		Double arrears = 0d;
		String otherVehAdvInst = null;
		String hbaHouseInst = null;

		/*
		 * MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
		 * .findAllMpgSchemeBillGroupbyParameter(paybillHeadMpgModel.
		 * getSchemeBillgroupId());
		 */

		objEntity.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
		objEntity.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
		/*
		 * objEntity.setSchemeBillgroupId(paybillHeadMpgModel.getSchemeBillgroupId());
		 */
		objEntity.setSchemeBillgroupId(paybillHeadMpgModel.getSchemeBillgroupId());
		objEntity.setCreatedDate(new Date());
		objEntity.setIsActive(1);
		objEntity.setDdoCode(paybillHeadMpgModel.getDdoCode().trim());
		objEntity.setNoOfEmployee(paybillHeadMpgModel.getNoOfEmployee());
		// List<MstEmployeeEntity> mstEmployeeEntity=
		// mstEmployeeService.findAllEmployeeByDDOCodeAndBillGroup(paybillHeadMpgModel.getDdoCode(),mpgSchemeBillGroupEntity.getBillGroupId());
		List<MstEmployeeEntity> mstEmployeeEntity = mstEmployeeService.findAllWorkingEmployeeByDDOCodeAndBillGroup(
				paybillHeadMpgModel.getDdoCode(), paybillHeadMpgModel.getSchemeBillgroupId(),
				paybillHeadMpgModel.getPaybillMonth(), paybillHeadMpgModel.getPaybillYear());

		Long val = paybillHeadMpgRepo.getPaybillGenerationTrnId() + 1;

		for (MstEmployeeEntity mstEmployeeEntity2 : mstEmployeeEntity) {

			// To Check Broken Period Exist or not
			int count = paybillHeadMpgRepo.isBrokenPeriodEmpty(mstEmployeeEntity2.getSevaarthId(),
					String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
					String.valueOf(paybillHeadMpgModel.getPaybillYear()));
			Long payCommission = 0l;
			payCommission = mstEmployeeEntity2.getPayCommissionCode();
			int percentage = 0;
			String percentageHRA = null;
			String startDate = null;
			int month2 = 0;
			int year2 = 0;
			if (month2 < 10) {
				startDate = String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
			} else {
				startDate = String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
			}

			if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC, mstEmployeeEntity2.getCityClass());

			} else {
				percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, mstEmployeeEntity2.getCityClass());
			}

			if (count > 0) {

				// ###############################################################################################################################
				// START:Fetch Broken Period Allowance and Deduction Data
				Map hmAllowDeducCodeAndValues = new HashMap();
				int basicpay = 0;
				int netpay = 0;
				int allowdedcode = 0;
				List<Object[]> lstBrokenPeriodData = paybillHeadMpgRepo.getBrokenPeriodData(
						mstEmployeeEntity2.getSevaarthId().toString(),
						String.valueOf(paybillHeadMpgModel.getPaybillMonth()),
						String.valueOf(paybillHeadMpgModel.getPaybillYear()), paybillHeadMpgModel.getDdoCode().trim());
				for (Object[] objects : lstBrokenPeriodData) {
					allowdedcode = (int) objects[4];
					hmAllowDeducCodeAndValues.put(objects[4], objects[5]); // 4 code,5 amount,6 Name
					basicpay = ((BigInteger) objects[2]).intValue();
					netpay = ((BigInteger) objects[3]).intValue();
				}
				// END:Fetch Broken Period Allowance and Deduction Data
				// ###############################################################################################################################

				PaybillGenerationTrnDetails paybillGenerationTrnDetails = new PaybillGenerationTrnDetails();
				// Fetching Broken Period Data

				// MstEmployeeEntity mstEmployeeEntity2 = mstEmployeeEntity.get(i);

				paybillGenerationTrnDetails.setPaybillMonth(paybillHeadMpgModel.getPaybillMonth());
				paybillGenerationTrnDetails.setPaybillYear(paybillHeadMpgModel.getPaybillYear());
				if (paybillHeadMpgModel.getPaybillMonth() == 2) {
					if (basicpay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
					else if (basicpay > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
							&& basicpay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
					else if (basicpay > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
					else
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;

				} else {

					if (basicpay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
					else if (basicpay > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
							&& basicpay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
					else if (basicpay > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
					else
						pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
				}

				/*
				 * hrPayPaybillEntity.setPaybillGenerationTrnId(objEntity.
				 * getPaybillGenerationTrnId());
				 */
				/* hrPayPaybillEntity.setPaybillGenerationTrnId(paybillGenerationTrnId); */

				// broken period HRA start
				/// String cityClass =
				// mstEmployeeService.getCityClassAgainstDDO(paybillHeadMpgModel.getDdoCode());
				String cityClass = mstEmployeeEntity2.getCityClass().toString();

				if (payCommission == 8) {

					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
						if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
								&& basicpay >= 22500)
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
								&& basicpay <= 22500)
							hra = (double) (5400);
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
								&& basicpay >= 22500)
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
								&& basicpay <= 22500)
							hra = (double) (3600);
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
								&& basicpay >= 22500)
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
								&& basicpay <= 22500)
							hra = (double) (1800);
					}

				}

				else if (payCommission == 2) {
					if (hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
						if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							hra = (basicpay * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					}

				}
				paybillGenerationTrnDetails.setSevaarthId(mstEmployeeEntity2.getSevaarthId());
				paybillGenerationTrnDetails.setBasicPay((double) basicpay);

				// Start:Add Broken Period

				double grossAmount = 0;
				double totalDeductionAmount = 0;
				double netAmount = 0;

				// grossAmount += basicpay;

				// Allowances Component Start###########################

				// 7PC DA
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE) != null) {
					paybillGenerationTrnDetails.setSvnDA((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getSvnDA();
					svnDA = paybillGenerationTrnDetails.getSvnDA();
				}
				// Additional Pay
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_PAY_CODE) != null) {
					paybillGenerationTrnDetails.setAddPay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_PAY_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getAddPay();
				}
				// Basic Arrear
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setBasicArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getBasicArr();
					basicArr = paybillGenerationTrnDetails.getBasicArr();
				}
				/*
				 * //Cash Allowance if
				 * (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_SVN_DA_CODE) != null) { paybillGenerationTrnDetails
				 * .setc( (double) ((BigInteger)hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE)).
				 * intValue()); grossAmount+=paybillGenerationTrnDetails.getBasicArr(); }
				 */

				// CLA-5thPay
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) != null) {
					paybillGenerationTrnDetails.setCla((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE)).intValue());
					/// grossAmount+=paybillGenerationTrnDetails.getCla();
					cla = paybillGenerationTrnDetails.getCla();
				}
				// Conveyance Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE) != null) {
					paybillGenerationTrnDetails.setConveyAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getConveyAllow();
					conveyAllow = paybillGenerationTrnDetails.getConveyAllow();
				}
				// DA Arrears
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setDaArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDaArr();
					DaArr = paybillGenerationTrnDetails.getDaArr();
				}
				// DA On TA
				if (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE) != null) {
					paybillGenerationTrnDetails.setDaOnTA((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDaOnTA();
					DaOnTA = paybillGenerationTrnDetails.getDaOnTA();
				}

				// Dearness Allowance (D.A)
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code) != null) {
					paybillGenerationTrnDetails.setHra6th((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDa();
					hra6th = paybillGenerationTrnDetails.getHra6th();
				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_CODE) != null) {
					paybillGenerationTrnDetails.setDa((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDa();
					da = paybillGenerationTrnDetails.getDa();
				}

				// DCPS Employer Contribution
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMPONENT_DCPS_EMPLOYER_CODE) != null) {
					paybillGenerationTrnDetails.setDcpsEmployer((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_DCPS_EMPLOYER_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDcpsEmployer();
				} else {
					if (paybillHeadMpgModel.getPaybillYear() >= 20 && paybillHeadMpgModel.getPaybillMonth() >= 8) {
						paybillGenerationTrnDetails.setDcpsEmployer((double) (basicpay + da + svnDA * 14 / 100));
						// grossAmount+=paybillGenerationTrnDetails.getDcpsEmployer();
					} else {
						paybillGenerationTrnDetails.setDcpsEmployer((double) (basicpay + da + svnDA * 10 / 100));
						// grossAmount+=paybillGenerationTrnDetails.getDcpsEmployer();
					}
				}

				// Dearness Pay
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE) != null) {
					paybillGenerationTrnDetails.setDearnessPay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getDearnessPay();
					dearnessPay = paybillGenerationTrnDetails.getDearnessPay();
				}

				// Family Planning Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE) != null) {
					paybillGenerationTrnDetails.setFamilyPlanAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FPA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getFamilyPlanAllow();
				}
				// Hilly Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HA_CODE) != null) {
					paybillGenerationTrnDetails.setHillStatAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HA_CODE)).intValue());
					grossAmount += paybillGenerationTrnDetails.getHillStatAllow();
				}
				// House Rent Allowance (H.R.A)
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE) != null) {
					paybillGenerationTrnDetails.setHra((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_HRA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getHra();
					hra = paybillGenerationTrnDetails.getHra();
				}
				/*
				 * //License Fee if
				 * (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_SVN_DA_CODE) != null) { paybillGenerationTrnDetails
				 * .set( (double) ((BigInteger)hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA_CODE)).
				 * intValue()); grossAmount+=paybillGenerationTrnDetails.getHra(); }
				 */
				// Non-Computational HRA
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NCA_CODE) != null) {
					paybillGenerationTrnDetails.setNonCompHRA((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NCA_CODE)).intValue());
					grossAmount += paybillGenerationTrnDetails.getNonCompHRA();

				}
				// Overtime Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTA_CODE) != null) {
					paybillGenerationTrnDetails.setOTA((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getOverTime();
				}
				// Non-Practising Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPA_CODE) != null) {
					paybillGenerationTrnDetails.setNonPractAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPA_CODE)).intValue());
					grossAmount += paybillGenerationTrnDetails.getNonPractAllow();
				}
				// Transport Allowance (T.A)
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE_CODE) != null) {
					paybillGenerationTrnDetails.setTa((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE_CODE))
									.intValue());
					// grossAmount+=paybillGenerationTrnDetails.getTa();
					ta = paybillGenerationTrnDetails.getTa();
				}
				// Transport Allowance Arrears
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setTransAllowArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getTransAllowArr();
					transAllowArr = paybillGenerationTrnDetails.getTransAllowArr();
				}
				// Tribal Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) != null) {
					paybillGenerationTrnDetails.setTribalAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getTribalAllow();
					tribalAllow = paybillGenerationTrnDetails.getTribalAllow();
				}
				// Uniform Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_UNIFORM_ALLOWANCE_CODE) != null) {
					paybillGenerationTrnDetails.setUniformAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_UNIFORM_ALLOWANCE_CODE))
									.intValue());
					// grossAmount+=paybillGenerationTrnDetails.getUniformAllow();
				}
				// Washing Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_WASHING_ALLOWANCE_CODE) != null) {
					paybillGenerationTrnDetails.setWa((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_WASHING_ALLOWANCE_CODE))
									.intValue());
					grossAmount += paybillGenerationTrnDetails.getWa();
				}
				// Permanent Travelling Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PTA_CODE) != null) {
					paybillGenerationTrnDetails.setPermanentTravelAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PTA_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getPermanentTravelAllow();

				}
				// Special Pay
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE) != null) {
					paybillGenerationTrnDetails.setSpecialPay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getSpecialPay();
					specialPay = paybillGenerationTrnDetails.getSpecialPay();
				}
				// Personal Pay
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE) != null) {
					paybillGenerationTrnDetails.setPersonalPay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getPersonalPay();
					personalPay = paybillGenerationTrnDetails.getPersonalPay();
				}
				// Naxal Area Allowance
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE) != null) {
					paybillGenerationTrnDetails.setNaxalAreaAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE))
									.intValue());
					grossAmount += paybillGenerationTrnDetails.getNaxalAreaAllow();
					naxalAreaAllow = paybillGenerationTrnDetails.getNaxalAreaAllow();
				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI_Code) != null) {
					paybillGenerationTrnDetails.setNpsEmployeeContri((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI_Code)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getNpsEmployeeContri();
					npsEmpContri = paybillGenerationTrnDetails.getNpsEmployeeContri();
				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT_Code) != null) {
					paybillGenerationTrnDetails.setNpsEmployerDeduct((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT_Code)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getNpsEmployerContri();
					npsEmprContri = paybillGenerationTrnDetails.getNpsEmployerDeduct();
				}

				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW_Code) != null) {
					paybillGenerationTrnDetails.setNpsEmployerAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW_Code)).intValue());
					// grossAmount+=paybillGenerationTrnDetails.getNpsEmployerAllow();
					npsEmprAllow = paybillGenerationTrnDetails.getNpsEmployerAllow();
				}

				// Employer DCPS DA Arrears Recovery

				System.out.println("--------- Employer DCPS DA Arrears Recovery-----------" + hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE));
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsDaArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsDa();
					emprDcpsDaArr = paybillGenerationTrnDetails.getEmpDcpsDaArr();
					System.out.println("---------------" + emprDcpsDaArr);
				}
				// Employer DCPS Delayed Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsDelay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsDelay();
					emprdcpsDelay = paybillGenerationTrnDetails.getEmpDcpsDelay();
				}
				// Employer DCPS Pay Arrears Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsPayArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsPayArr();
					emprDcpsPayArr = paybillGenerationTrnDetails.getEmpDcpsPayArr();
				}
				// Employer DCPS Regular Recovery
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE) != null) {
					paybillGenerationTrnDetails
							.setEmpDcpsRegularRecovery((double) ((BigInteger) hmAllowDeducCodeAndValues.get(
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE))
											.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsRegularRecovery();
					emprDcpsReg = paybillGenerationTrnDetails.getEmpDcpsRegularRecovery();
				}

				// Naxal Area Allowance

				// Allowances Component End#############################

				// Deductions Component Start#############################

				// Adjustable DCPS Employer Contribution
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMPONENT_ADJUST_DCPS_EMPR_CODE) != null) {
					paybillGenerationTrnDetails.setAdjustDcpsEmployer((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_ADJUST_DCPS_EMPR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getAdjustDcpsEmployer();
					adjust_dcps_empr = paybillGenerationTrnDetails.getAdjustDcpsEmployer();
				}
				// DCPS Arr
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setDcpsArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsArr();
					dcpsArr = paybillGenerationTrnDetails.getDcpsArr();
				}
				// DCPS DA Arrears Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsDaArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsDa();
					dcpsDa = paybillGenerationTrnDetails.getEmpDcpsDaArr();
				}
				// DCPS Delayed Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsDelay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsDelay();
					dcpsDelay = paybillGenerationTrnDetails.getEmpDcpsDelay();
				}
				// DCPS Pay Arrears Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setEmpDcpsPayArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsPayArr();
					dcpsPayArr = paybillGenerationTrnDetails.getEmpDcpsPayArr();
				}
				// DCPS Regular Recovery
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE) != null) {
					paybillGenerationTrnDetails
							.setEmpDcpsRegularRecovery((double) ((BigInteger) hmAllowDeducCodeAndValues.get(
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY_CODE))
											.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsRegularRecovery();
					dcpsReg = paybillGenerationTrnDetails.getEmpDcpsRegularRecovery();
				}
				// GIS
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_CODE) != null) {
					paybillGenerationTrnDetails.setGis((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGis();
					gisAmount = paybillGenerationTrnDetails.getGis();
				}
				// GIS(ZP)
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE) != null) {
					paybillGenerationTrnDetails.setGisZp((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGisZp();
					GisZp = paybillGenerationTrnDetails.getGisZp();
				}
				// GPF_ABC Arrears
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setGpfAbcArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfAbcArr();
					GpfAbcArr = paybillGenerationTrnDetails.getGpfAbcArr();
				}
				// GPF_D_Arrears
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setGpfDArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfDArr();
					gpfDArr = paybillGenerationTrnDetails.getGpfDArr();
				}
				// GPF_GRP_ABC
				if (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) != null) {
					paybillGenerationTrnDetails.setGpfGrpABC((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfGrpABC();
					gpfGrpABC = paybillGenerationTrnDetails.getGpfGrpABC();
				}
				// GPF_GRP_D
				if (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) != null) {
					paybillGenerationTrnDetails.setGpfGrpD((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfGrpD();
					gpfGrpD = paybillGenerationTrnDetails.getGpfGrpD();
				}
				// Group Acc. Policy
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) != null) {
					paybillGenerationTrnDetails.setGroupAccPolicy((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGroupAccPolicy();
					groupAccPolicy = paybillGenerationTrnDetails.getGroupAccPolicy();
				}
				// House Rent Recovery ( H R R )
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE) != null) {
					paybillGenerationTrnDetails.setHrr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getHrr();
					hrr = paybillGenerationTrnDetails.getHrr();
				}
				// HRR Arrear
				/*
				 * if (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.
				 * COMPONENT_COMPONENT_PT_CODE) != null) { paybillGenerationTrnDetails
				 * .set((double) ((BigInteger)hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE)).intValue())
				 * ; totalDeductionAmount+=paybillGenerationTrnDetails.getDcpsArr(); }
				 */
				// Income Tax( I .T )
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE) != null) {
					paybillGenerationTrnDetails.setIt((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE)).intValue());
					totalDeductionAmount += paybillGenerationTrnDetails.getIt();
					it = paybillGenerationTrnDetails.getIt();
				}
				// Other Deductions
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE) != null) {
					paybillGenerationTrnDetails.setOtherDeduct((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getOtherDeduct();
					otherDeduc = paybillGenerationTrnDetails.getOtherDeduct();
				}
				// Other Recovery
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) != null) {
					paybillGenerationTrnDetails.setOtherRec((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getOtherRec();
					otherRec = paybillGenerationTrnDetails.getOtherRec();
				}

				// Profession Tax
				if (hmAllowDeducCodeAndValues.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE) != null) {
					paybillGenerationTrnDetails.setPt((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMPONENT_COMPONENT_PT_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getPt();
					pt = paybillGenerationTrnDetails.getPt();
				}

				// PT Arrears
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE) != null) {
					paybillGenerationTrnDetails.setPtArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getPtArr();
					ptArr = paybillGenerationTrnDetails.getPtArr();
				}

				// Service Charge
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE) != null) {
					paybillGenerationTrnDetails.setServCharge((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getServCharge();
					ServCharge = paybillGenerationTrnDetails.getServCharge();
				}
				Double gpfadvGrpABC = 0d;
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE) != null) {

					paybillGenerationTrnDetails.setGpfAdvGrpAbc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfAdvGrpAbc();
					gpfadvGrpABC = paybillGenerationTrnDetails.getGpfAdvGrpAbc();
				}
				Double gpfadvGrpD = 0d;
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) != null) {

					paybillGenerationTrnDetails.setGpfAdvGrpD((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfAdvGrpD();
					gpfadvGrpD = paybillGenerationTrnDetails.getGpfAdvGrpD();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) != null) {

					paybillGenerationTrnDetails.setGpfAdvGrpD((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getGpfAdvGrpD();
					gpfadvGrpD = paybillGenerationTrnDetails.getGpfAdvGrpD();

				}
				/*
				 * if (hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code) !=
				 * null) {
				 * 
				 * paybillGenerationTrnDetails.setHbaHouse((double) ((BigInteger)
				 * hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code)).
				 * intValue()); //
				 * totalDeductionAmount+=paybillGenerationTrnDetails.getHbaHouse(); /// HouseAdv
				 * = paybillGenerationTrnDetails.getHbaHouse();
				 * 
				 * LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity =
				 * mstEmployeeService.findHBADetails( mstEmployeeEntity2.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeEntity2.getEmployeeId());
				 * 
				 * if (lnaHBAEmployeeRequestMstEntity != null &&
				 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { HouseAdv =
				 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
				 * 
				 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancHbaInst =
				 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
				 * hbai.toString() + "/" + sancHbaInst.toString();
				 * 
				 * paybillGenerationTrnDetails.setHbaHouseInst(hbaHouseInst); //
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * } }
				 */
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code) != null) {

					paybillGenerationTrnDetails.setComputerAdv((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getComputerAdv();
					CA = paybillGenerationTrnDetails.getComputerAdv();

				}
				/*
				 * if (hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code)
				 * != null) {
				 * 
				 * paybillGenerationTrnDetails.setOtherVehAdv((double) ((BigInteger)
				 * hmAllowDeducCodeAndValues
				 * .get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code))
				 * .intValue()); //
				 * totalDeductionAmount+=paybillGenerationTrnDetails.getOtherVehAdv(); vehAdv =
				 * paybillGenerationTrnDetails.getOtherVehAdv();
				 * 
				 * LNAVAEmployeeRequestMstEntity lnaVAEmployeeRequestMstEntity =
				 * mstEmployeeService.findVADetails( mstEmployeeEntity2.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeEntity2.getEmployeeId());
				 * 
				 * if (lnaVAEmployeeRequestMstEntity != null &&
				 * lnaVAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt() != null) { vehAdv =
				 * lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt();
				 * 
				 * Integer vi = lnaVAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancVehInst =
				 * lnaVAEmployeeRequestMstEntity.getSancPrincipalInstallMCA(); otherVehAdvInst =
				 * vi.toString() + "/" + sancVehInst.toString();
				 * paybillGenerationTrnDetails.setOthrVehAdvInst(otherVehAdvInst); }
				 * 
				 * } }
				 */
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code) != null) {

					paybillGenerationTrnDetails.setFestivalAdv((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getFestivalAdv();
					FA = paybillGenerationTrnDetails.getFestivalAdv();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code) != null) {

					paybillGenerationTrnDetails.setCmFund((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					cmFund = paybillGenerationTrnDetails.getCmFund();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code) != null) {

					paybillGenerationTrnDetails.setDeputationAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					deputAllow = paybillGenerationTrnDetails.getDeputationAllow();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code) != null) {

					paybillGenerationTrnDetails.setOTA((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					ota = paybillGenerationTrnDetails.getOTA();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code) != null) {

					paybillGenerationTrnDetails.setHillStatAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					hillStatAllow = paybillGenerationTrnDetails.getHillStatAllow();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code) != null) {

					paybillGenerationTrnDetails.setTracerAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					tracerAllow = paybillGenerationTrnDetails.getTracerAllow();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code) != null) {

					paybillGenerationTrnDetails.setNaksaliedAllow((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					naksaliedAllow = paybillGenerationTrnDetails.getNaksaliedAllow();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance_Code) != null) {

					paybillGenerationTrnDetails.setWa((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					wa = (double) 50; /* paybillGenerationTrnDetails.getWa(); */
					paybillGenerationTrnDetails.setWa(wa);

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code) != null) {

					paybillGenerationTrnDetails.setGpfSubscription((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					gpfSubscrb = paybillGenerationTrnDetails.getGpfSubscription();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code) != null) {

					paybillGenerationTrnDetails.setHba((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					hba = paybillGenerationTrnDetails.getHba();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code) != null) {

					paybillGenerationTrnDetails.setSocOrBankLoan((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					socOrBankLoan = paybillGenerationTrnDetails.getSocOrBankLoan();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code) != null) {

					paybillGenerationTrnDetails.setBLWF((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					BLWF = paybillGenerationTrnDetails.getBLWF();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) != null) {

					paybillGenerationTrnDetails.setGpfArrears((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					GpfArrears = paybillGenerationTrnDetails.getGpfArrears();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code) != null) {

					paybillGenerationTrnDetails.setGpfSpecialArr((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					GpfSpclArrears = paybillGenerationTrnDetails.getGpfSpecialArr();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code) != null) {

					paybillGenerationTrnDetails.setNDCPSSubscription((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					NDCPSsubscrp = paybillGenerationTrnDetails.getNDCPSSubscription();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code) != null) {

					paybillGenerationTrnDetails.setAlliedSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					alliedSoc = paybillGenerationTrnDetails.getAlliedSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code) != null) {

					paybillGenerationTrnDetails.setMantralayaSoci((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mantralayaSoci = paybillGenerationTrnDetails.getMantralayaSoci();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code) != null) {

					paybillGenerationTrnDetails.setChiplunSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					chiplunSoc = paybillGenerationTrnDetails.getChiplunSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code) != null) {

					paybillGenerationTrnDetails.setUlhasnagarSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					ulhasnagarSoc = paybillGenerationTrnDetails.getUlhasnagarSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code) != null) {

					paybillGenerationTrnDetails.setEngrSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					engrSoc = paybillGenerationTrnDetails.getEngrSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code) != null) {

					paybillGenerationTrnDetails.setGpfDaSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					gpfDaSub = paybillGenerationTrnDetails.getGpfDaSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) != null) {

					paybillGenerationTrnDetails.setRop((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					rop = paybillGenerationTrnDetails.getRop();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code) != null) {

					paybillGenerationTrnDetails.setPayFixDiff((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					payFixDiff = paybillGenerationTrnDetails.getPayFixDiff();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code) != null) {

					paybillGenerationTrnDetails.setNps((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					nps = paybillGenerationTrnDetails.getNps();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code) != null) {

					paybillGenerationTrnDetails.setPubHealWrks((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					pubHealWrks = paybillGenerationTrnDetails.getPubHealWrks();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code) != null) {

					paybillGenerationTrnDetails.setSindhuOras((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					sindhuOras = paybillGenerationTrnDetails.getSindhuOras();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code) != null) {

					paybillGenerationTrnDetails.setJalgaonSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalgaonSoc = paybillGenerationTrnDetails.getJalgaonSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code) != null) {

					paybillGenerationTrnDetails.setManaBhaiMehJal((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					manaBhaiMehJal = paybillGenerationTrnDetails.getManaBhaiMehJal();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code) != null) {

					paybillGenerationTrnDetails.setAkolaPAriAbhi((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					akolaPAriAbhi = paybillGenerationTrnDetails.getAkolaPAriAbhi();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code) != null) {

					paybillGenerationTrnDetails.setZpKarmPat((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					zpKarmPat = paybillGenerationTrnDetails.getZpKarmPat();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code) != null) {

					paybillGenerationTrnDetails.setVidharbhaGramkokBn((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					vidharbhaGramkokBn = paybillGenerationTrnDetails.getVidharbhaGramkokBn();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code) != null) {

					paybillGenerationTrnDetails.setChandaSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					chandaSoc = paybillGenerationTrnDetails.getChandaSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code) != null) {

					paybillGenerationTrnDetails.setJalsevaSocNag((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalsevaSocNag = paybillGenerationTrnDetails.getJalsevaSocNag();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code) != null) {

					paybillGenerationTrnDetails.setBhandaraSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					bhandaraSoc = paybillGenerationTrnDetails.getBhandaraSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code) != null) {

					paybillGenerationTrnDetails.setGdccBank((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					gdccBank = paybillGenerationTrnDetails.getGdccBank();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code) != null) {

					paybillGenerationTrnDetails.setGondiaSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					gondiaSoc = paybillGenerationTrnDetails.getGondiaSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code) != null) {

					paybillGenerationTrnDetails.setNagpurSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					nagpurSoc = paybillGenerationTrnDetails.getNagpurSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code) != null) {

					paybillGenerationTrnDetails.setAllahabadSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					allahabadSoc = paybillGenerationTrnDetails.getAllahabadSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code) != null) {

					paybillGenerationTrnDetails.setBhanDistCenCopBnk((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					bhanDistCenCopBnk = paybillGenerationTrnDetails.getBhanDistCenCopBnk();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code) != null) {

					paybillGenerationTrnDetails.setBankOfBarora((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					bankOfBarora = paybillGenerationTrnDetails.getBankOfBarora();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code) != null) {

					paybillGenerationTrnDetails.setCourtComput((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					courtComput = paybillGenerationTrnDetails.getCourtComput();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code) != null) {

					paybillGenerationTrnDetails.setJalgaonGSSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalgaonGSSoc = paybillGenerationTrnDetails.getJalgaonGSSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code) != null) {

					paybillGenerationTrnDetails.setJalgaonHandiSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalgaonHandiSoc = paybillGenerationTrnDetails.getJalgaonHandiSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code) != null) {

					paybillGenerationTrnDetails.setDhulenandurbarBnk((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					dhulenandurbarBnk = paybillGenerationTrnDetails.getDhulenandurbarBnk();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code) != null) {

					paybillGenerationTrnDetails.setParisarAbhiSocNash((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					parisarAbhiSocNash = paybillGenerationTrnDetails.getParisarAbhiSocNash();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code) != null) {

					paybillGenerationTrnDetails.setSarwAroBanSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					sarwAroBanSoc = paybillGenerationTrnDetails.getSarwAroBanSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code) != null) {

					paybillGenerationTrnDetails.setJalSocCL3((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalSocCL3 = paybillGenerationTrnDetails.getJalSocCL3();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code) != null) {

					paybillGenerationTrnDetails.setPanipurvtaSocCL3or4((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					panipurvtaSocCL3or4 = paybillGenerationTrnDetails.getPanipurvtaSocCL3or4();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code) != null) {

					paybillGenerationTrnDetails.setGovBank((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					govBank = paybillGenerationTrnDetails.getGovBank();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code) != null) {

					paybillGenerationTrnDetails.setSangliSalSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					sangliSalSoc = paybillGenerationTrnDetails.getSangliSalSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code) != null) {

					paybillGenerationTrnDetails.setMjpSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mjpSoc = paybillGenerationTrnDetails.getMjpSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code) != null) {

					paybillGenerationTrnDetails.setNashikRoadSocCL3or4((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					nashikRoadSocCL3or4 = paybillGenerationTrnDetails.getNashikRoadSocCL3or4();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code) != null) {

					paybillGenerationTrnDetails.setJalsevaMalSocCL3((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalsevaMalSocCL3 = paybillGenerationTrnDetails.getJalsevaMalSocCL3();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code) != null) {

					paybillGenerationTrnDetails.setNashikBankSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					nashikBankSoc = paybillGenerationTrnDetails.getNashikBankSoc();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code) != null) {

					paybillGenerationTrnDetails.setMandaNashikSoc((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mandaNashikSoc = paybillGenerationTrnDetails.getMandaNashikSoc();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code) != null) {

					paybillGenerationTrnDetails.setUjwalaMahilaPatBhan((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					ujwalaMahilaPatBhan = paybillGenerationTrnDetails.getUjwalaMahilaPatBhan();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code) != null) {

					paybillGenerationTrnDetails.setBcQuar((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					bcQuar = paybillGenerationTrnDetails.getBcQuar();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code) != null) {

					paybillGenerationTrnDetails.setExcessPayrec((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					excessPayrec = paybillGenerationTrnDetails.getExcessPayrec();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code) != null) {

					paybillGenerationTrnDetails.setFlagDay((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					flagDay = paybillGenerationTrnDetails.getFlagDay();

				}
				if (hmAllowDeducCodeAndValues.get(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) != null) {

					paybillGenerationTrnDetails.setBhandJilAbhiKarPat((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					bhandJilAbhiKarPat = paybillGenerationTrnDetails.getBhandJilAbhiKarPat();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code) != null) {

					paybillGenerationTrnDetails.setJalsevaKarmSahaPath((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					jalsevaKarmSahaPath = paybillGenerationTrnDetails.getJalsevaKarmSahaPath();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code) != null) {

					paybillGenerationTrnDetails.setSocNanded((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					socNanded = paybillGenerationTrnDetails.getSocNanded();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code) != null) {

					paybillGenerationTrnDetails.setSocAurang((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					socAurang = paybillGenerationTrnDetails.getSocAurang();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code) != null) {

					paybillGenerationTrnDetails.setSocLatur((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					socLatur = paybillGenerationTrnDetails.getSocLatur();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code) != null) {

					paybillGenerationTrnDetails.setMlwfonlyMJP((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mlwfOnlyMJP = paybillGenerationTrnDetails.getMlwfonlyMJP();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code) != null) {

					paybillGenerationTrnDetails.setMahaLabWelfareFund((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mahaLabWelFund = paybillGenerationTrnDetails.getMahaLabWelfareFund();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code) != null) {

					paybillGenerationTrnDetails.setMjpSocLatur((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mjpLatur = paybillGenerationTrnDetails.getMjpSocLatur();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code) != null) {

					paybillGenerationTrnDetails.setMjpSocSolapur((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					mjpSocSolapur = paybillGenerationTrnDetails.getMjpSocSolapur();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BEGIS_Code) != null) {

					paybillGenerationTrnDetails.setBegis((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BEGIS_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					begis = paybillGenerationTrnDetails.getBegis();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code) != null) {

					paybillGenerationTrnDetails.setTa5th((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th_Code))
									.intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					ta5th = paybillGenerationTrnDetails.getTa5th();

				}
				if (hmAllowDeducCodeAndValues
						.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code) != null) {

					paybillGenerationTrnDetails.setArrears((double) ((BigInteger) hmAllowDeducCodeAndValues
							.get(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code)).intValue());
					// totalDeductionAmount+=paybillGenerationTrnDetails.getCmFund();
					arrears = paybillGenerationTrnDetails.getArrears();

				}

				// Deductions Component End#############################

				// End:Add Broken Period

				System.out.println("-----------checking data--------------" + DaOnTA + emprDcpsDaArr + emprdcpsDelay
						+ emprDcpsPayArr + emprDcpsReg);

				/*
				 * grossAmount = (basicpay + hra + da + svnDA + dcps_empr + ta + tribalAllow +
				 * transAllowArr + DaArr + naxalAreaAllow + basicArr + specialPay + personalPay
				 * + cla + dearnessPay + conveyAllow + DaOnTA + npsEmprAllow + wa + deputAllow +
				 * ota + hillStatAllow + tracerAllow + naksaliedAllow + GpfArrears + hra6th +
				 * ta5th + arrears);
				 */

				grossAmount = (basicpay + hra + da + svnDA + dcps_empr + ta + tribalAllow + transAllowArr + DaArr
						+ naxalAreaAllow + basicArr + specialPay + personalPay + cla + dearnessPay + conveyAllow
						+ DaOnTA + npsEmprAllow + emprDcpsDaArr + emprDcpsPayArr + hra6th + hra5th + ta5th + ta6th + wa
						+ deputAllow + ota + hillStatAllow + tracerAllow + naksaliedAllow + GpfArrears + arrears);

				paybillGenerationTrnDetails.setGrossTotalAmt((double) Math.round(grossAmount));

				double dedByAG = gpfGrpABC + gpfGrpD + GpfAbcArr + gpfDArr;
				paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));

				double dedByTreasury = GisZp + it + groupAccPolicy + pt + gisAmount + npsEmpContri + npsEmprContri
						+ cmFund + socOrBankLoan + BLWF + NDCPSsubscrp + GpfSpclArrears + hba + begis + gpfSubscrb
						+ gpfDaSub + rop + payFixDiff + nps + hrr + otherRec;
				paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

				// double dedByOthr = dedByTreasury + dedByAG ;
				double dedByOthr = CA + FA + HouseAdv + vehAdv + gpfAdvance + alliedSoc + mantralayaSoci + chiplunSoc
						+ ulhasnagarSoc + engrSoc + pubHealWrks + sindhuOras + jalgaonSoc + manaBhaiMehJal
						+ akolaPAriAbhi + zpKarmPat + vidharbhaGramkokBn + chandaSoc + jalsevaSocNag + bhandaraSoc
						+ gdccBank + gondiaSoc + nagpurSoc + allahabadSoc + bhanDistCenCopBnk + bankOfBarora
						+ courtComput + jalgaonGSSoc + jalgaonHandiSoc + dhulenandurbarBnk + parisarAbhiSocNash
						+ sarwAroBanSoc + jalSocCL3 + panipurvtaSocCL3or4 + govBank + sangliSalSoc + mjpSoc
						+ nashikRoadSocCL3or4 + jalsevaMalSocCL3 + nashikBankSoc + mandaNashikSoc + ujwalaMahilaPatBhan
						+ bcQuar + excessPayrec + flagDay + bhandJilAbhiKarPat + jalsevaKarmSahaPath + socNanded
						+ socAurang + socLatur + mlwfOnlyMJP + mahaLabWelFund + mjpLatur + jalbhavanSoc + mjpSocSolapur;
				paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));

				/*
				 * double NonGovDed = pubHealWrks + sindhuOras + jalgaonSoc + manaBhaiMehJal +
				 * akolaPAriAbhi + zpKarmPat + vidharbhaGramkokBn + chandaSoc + jalsevaSocNag +
				 * bhandaraSoc + gdccBank + gondiaSoc + nagpurSoc + allahabadSoc +
				 * bhanDistCenCopBnk + bankOfBarora + courtComput + jalgaonGSSoc +
				 * jalgaonHandiSoc + dhulenandurbarBnk + parisarAbhiSocNash + sarwAroBanSoc +
				 * jalSocCL3 + panipurvtaSocCL3or4 + govBank + sangliSalSoc + + mjpSoc +
				 * nashikRoadSocCL3or4 + jalsevaMalSocCL3 + nashikBankSoc + mandaNashikSoc +
				 * ujwalaMahilaPatBhan + bcQuar + excessPayrec + flagDay + bhandJilAbhiKarPat +
				 * jalsevaKarmSahaPath + socNanded + socAurang + socLatur + mlwfOnlyMJP +
				 * mahaLabWelFund + mjpLatur + jalbhavanSoc + mjpSocSolapur;
				 */

				double Totaldeduc = dedByAG + dedByTreasury + dedByOthr;

				/*
				 * double Totaldeduc = (pt + dcpsDa + dcpsDelay + dcpsPayArr + dcpsReg + dcpsArr
				 * + adjust_dcps_empr + gisAmount + gpfGrpABC + gpfGrpD + GpfAbcArr +
				 * groupAccPolicy + gpfDArr + ptArr + otherDeduc + GisZp + hrr + otherRec +
				 * GpfAdvDLoan + GpfABC + it + ServCharge + revenueStamp + excessPayment +
				 * cmFund + npsEmprContri + npsEmpContri + HouseAdv + CA + FA + vehAdv);
				 */

				paybillGenerationTrnDetails.setTotalDeduction((double) Math.round(Totaldeduc));

				/*
				 * paybillGenerationTrnDetails.setTotalNetAmt(paybillGenerationTrnDetails.
				 * getBasicPay() + da + hra + dcps_empr - pt - adjust_dcps_empr - dcpsReg -
				 * dcpsArr - dcpsDa - dcpsDelay - dcpsPayArr - gisAmount);
				 */

				// paybillGenerationTrnDetails.setGrossTotalAmt((double) Math.round(Total));

				// paybillGenerationTrnDetails.setTotalDeduction((double)
				// Math.round(Totaldeduc));

				/*
				 * paybillGenerationTrnDetails.setTotalNetAmt(paybillGenerationTrnDetails.
				 * getBasicPay() + da + hra + dcps_empr - pt - adjust_dcps_empr - dcpsReg -
				 * dcpsArr - dcpsDa - dcpsDelay - dcpsPayArr - gisAmount);
				 */

				/*
				 * double dedByAG = gpfGrpABC + gpfGrpD + GpfAbcArr + gpfDArr; //
				 * paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));
				 * 
				 * double dedByTreasury = GisZp + it + groupAccPolicy + pt + dcpsReg + gisAmount
				 * + dcpsDa + +cmFund + npsEmpContri + npsEmprContri + dcpsPayArr + begis + BLWF
				 * + gpfSubscrb;
				 * 
				 * double Totaldeduc = (pt + dcpsDa + dcpsDelay + dcpsPayArr + dcpsReg + dcpsArr
				 * + adjust_dcps_empr + gisAmount + gpfGrpABC + gpfGrpD + GpfAbcArr +
				 * groupAccPolicy + gpfDArr + ptArr + otherDeduc + GisZp + hrr + otherRec + it +
				 * ServCharge + cmFund + npsEmprContri + npsEmpContri + HouseAdv + CA + FA +
				 * vehAdv + gpfAdvance + socOrBankLoan + BLWF + NDCPSsubscrp + GpfSpclArrears +
				 * hba + gpfDaSub + rop + payFixDiff + nps + mantralayaSoci + begis);
				 */
				// paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

				double Net = grossAmount - Totaldeduc;
				// double dedByOthr = dedByTreasury + dedByAG ;
				/*
				 * double dedByOthr = CA + FA + HouseAdv + vehAdv + otherRec + gpfAdvance +
				 * alliedSoc + mantralayaSoci + chiplunSoc + ulhasnagarSoc + engrSoc + gpfDaSub
				 * + rop + payFixDiff + nps ;
				 */
				// paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));
				/*
				 * double NonGovDed = dedByAG + dedByTreasury + dedByOthr;
				 */

				/*
				 * grossAmount = (basicpay + hra + svnDA + npsEmprAllow + da + dcps_empr + ta +
				 * tribalAllow + transAllowArr + DaArr + naxalAreaAllow + basicArr + specialPay
				 * + personalPay + cla + dearnessPay + conveyAllow + DaOnTA + emprDcpsDaArr +
				 * emprdcpsDelay + emprDcpsPayArr + emprDcpsReg);
				 */

				/*
				 * double dedByAG = gpfadvGrpD + gpfadvGrpABC + gpfGrpABC + gpfGrpD + GpfAbcArr
				 * + gpfDArr;
				 */
				paybillGenerationTrnDetails.setDedAdjAg((double) Math.round(dedByAG));

				/*
				 * double dedByTreasury = GisZp + it + groupAccPolicy + pt + dcpsReg + gisAmount
				 * + dcpsDa + dcpsDelay + cmFund + npsEmpContri + npsEmprContri + dcpsPayArr;
				 */
				paybillGenerationTrnDetails.setDedAdjTry((double) Math.round(dedByTreasury));

				// double dedByOthr =totalDeductionAmount ;
				/* double dedByOthr = CA + FA + HouseAdv + vehAdv + otherRec; */
				paybillGenerationTrnDetails.setDedAdjOtr((double) Math.round(dedByOthr));

				/*
				 * double Totaldeduc=(pt + dcpsDa + dcpsDelay + dcpsPayArr + dcpsReg + dcpsArr +
				 * adjust_dcps_empr + gisAmount + gpfGrpABC + gpfGrpD + GpfAbcArr +
				 * groupAccPolicy + gpfDArr + ptArr + otherDeduc + GisZp + hrr + otherRec
				 * +gpfadvGrpD +gpfadvGrpABC + it + ServCharge +npsEmprContri +
				 * npsEmpContri+FA+vehAdv+CA+HouseAdv+cmFund+dcpsPayArr);
				 */
				// double Totaldeduc = dedByAG + dedByTreasury + dedByOthr;

				paybillGenerationTrnDetails.setTotalDeduction((double) Math.round(Totaldeduc));
				// set by zero after addition all component
				// grossAmt = 0;
				// netAmt = 0;
				da = 0;
				hra = 0;
				cityClass = null;
				hra6th = 0;
				hra5th = 0;
				pt = 0;
				dcps_empr = 0;
				dcps_empr1 = 0;// Added for testing nps
				grossAdjust = 0;
				dedAdjust = 0;
				adjust_dcps_empr = 0;
				adjust_dcps_empr1 = 0;// Added for testing nps
				dcpsDelay = 0;
				dcpsDa = 0;
				dcpsPayArr = 0;
				dcpsArr = 0;
				dcpsReg = 0;
				gisAmount = 0;
				gradePay = 0;
				gpfGrpD = 0;
				GpfAbcArr = 0;
				gpfGrpABC = 0;
				ta = 0;
				ta6th = 0;
				ta5th = 0;
				tribalAllow = 0;
				transAllowArr = 0;
				groupAccPolicy = 0;
				gpfDArr = 0;
				GisZp = 0;
				ptArr = 0;
				otherDeduc = 0;
				hrr = 0;
				otherRec = 0;
				basicArr = 0;
				DaArr = 0;
				specialPay = 0;
				personalPay = 0;
				dearnessPay = 0;
				conveyAllow = 0;
				DaOnTA = 0;
				cla = 0;
				svnDA = 0;
				ServCharge = 0;
				it = 0;
				naxalAreaAllow = 0;
				npsEmprAllow = 0d;
				npsEmprContri = 0d;
				npsEmpContri = 0d;
				HouseAdv = 0d;
				CA = 0d;
				FA = 0d;
				gpfAdvance = 0d;
				vehAdv = 0d;
				cmFund = 0d;
				emprDcpsDaArr = 0d;
				emprdcpsDelay = 0d;
				emprDcpsPayArr = 0d;
				emprDcpsReg = 0d;
				deputAllow = 0d;
				ota = 0d;
				hillStatAllow = 0d;
				tracerAllow = 0d;
				naksaliedAllow = 0d;
				wa = 0d;
				gpfSubscrb = 0d;
				hba = 0d;
				socOrBankLoan = 0d;
				BLWF = 0d;
				GpfArrears = 0d;
				GpfSpclArrears = 0d;
				NDCPSsubscrp = 0d;
				alliedSoc = 0d;
				mantralayaSoci = 0d;
				chiplunSoc = 0d;
				ulhasnagarSoc = 0d;
				engrSoc = 0d;
				gpfDaSub = 0d;
				rop = 0d;
				payFixDiff = 0d;
				nps = 0d;
				pubHealWrks = 0d;
				sindhuOras = 0d;
				jalgaonSoc = 0d;
				manaBhaiMehJal = 0d;
				akolaPAriAbhi = 0d;
				zpKarmPat = 0d;
				vidharbhaGramkokBn = 0d;
				chandaSoc = 0d;
				jalsevaSocNag = 0d;
				bhandaraSoc = 0d;
				gdccBank = 0d;
				gondiaSoc = 0d;
				nagpurSoc = 0d;
				allahabadSoc = 0d;
				bhanDistCenCopBnk = 0d;
				bankOfBarora = 0d;
				courtComput = 0d;
				jalgaonGSSoc = 0d;
				jalgaonHandiSoc = 0d;
				dhulenandurbarBnk = 0d;
				parisarAbhiSocNash = 0d;
				sarwAroBanSoc = 0d;
				jalSocCL3 = 0d;
				panipurvtaSocCL3or4 = 0d;
				govBank = 0d;
				sangliSalSoc = 0d;
				mjpSoc = 0d;
				nashikRoadSocCL3or4 = 0d;
				jalsevaMalSocCL3 = 0d;
				nashikBankSoc = 0d;
				mandaNashikSoc = 0d;
				ujwalaMahilaPatBhan = 0d;
				bcQuar = 0d;
				excessPayrec = 0d;
				flagDay = 0d;
				bhandJilAbhiKarPat = 0d;
				jalsevaKarmSahaPath = 0d;
				socNanded = 0d;
				socAurang = 0d;
				socLatur = 0d;
				mlwfOnlyMJP = 0d;
				mahaLabWelFund = 0d;
				mjpLatur = 0d;
				jalbhavanSoc = 0d;
				mjpSocSolapur = 0d;
				begis = 0d;
				arrears = 0d;

				paybillGenerationTrnDetails.setGrossTotalAmt(grossAmount);
				/*
				 * paybillGenerationTrnDetails .setTotalDeduction(totalDeductionAmount);
				 */
				paybillGenerationTrnDetails.setTotalDeduction(Totaldeduc);

				// paybillGenerationTrnDetails.setTotalNetAmt(grossAmount -
				// totalDeductionAmount);
				paybillGenerationTrnDetails.setTotalNetAmt(grossAmount - Totaldeduc);
				paybillGenerationTrnDetails.setPaybillGenerationTrnId(val);
				Serializable id12 = paybillHeadMpgRepo.saveHrPayPaybill(paybillGenerationTrnDetails);
				grossAmt += grossAmount;

				netAmt += grossAmount - Totaldeduc;
			}

			else {
				// return 1;

				return 2L;
			}
		}
		objEntity.setBillGrossAmt((double) Math.round(grossAmt));
		objEntity.setBillNetAmount((double) Math.round(netAmt));
		objEntity.setPaybillGenerationTrnId(val);
		/*
		 * objEntity.setBillGrossAmt(grossAmt); objEntity.setBillNetAmount(netAmt);
		 * objEntity.setPaybillGenerationTrnId(val);
		 */

		Serializable id = paybillHeadMpgRepo.savePaybillHeadMpg(objEntity);

		return (Long) id;

	}

	@Override
	public List<Object[]> getViewDetialsReport(Integer consolidatedId) {
		List<Object[]> obj = paybillHeadMpgRepo.getViewDetialsReport(consolidatedId);
		return obj;
	}

	@Override
	public Object[] getSevaarthIdMappedWithBill(String ddocode, int noofemp, Long schemeBillGroupId, int month,
			int year) {
		boolean flag = false;
		/*
		 * MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
		 * .findAllMpgSchemeBillGroupbyParameter(schemeBillGroupId);
		 */
		List<Object[]> obj = new ArrayList<Object[]>();
		List<MstEmployeeEntity> mstEmployeeEntity = mstEmployeeService
				.findAllWorkingEmployeeByDDOCodeAndBillGroup(ddocode, schemeBillGroupId, month, year);

		Long val = paybillHeadMpgRepo.getPaybillGenerationTrnId() + 1;
		StringBuffer sb = new StringBuffer();
		for (MstEmployeeEntity mstEmployeeEntity2 : mstEmployeeEntity) {

			// To Check Broken Period Exist or not
			int count = paybillHeadMpgRepo.isBrokenPeriodEmpty(mstEmployeeEntity2.getSevaarthId(),
					String.valueOf(month), String.valueOf(year));
			if (count == 0) {
				sb.append(mstEmployeeEntity2.getSevaarthId() + ",");
				flag = true;
			}
		}
		Object[] res = new Object[2];
		res[0] = flag;
		if (sb.length() != 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		res[1] = sb.toString().trim();
		return res;
	}

	@Override
	public Integer isPaybillExists(Long schemeBillgroupId, int paybillMonth, int paybillYear) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.isPaybillExistsForCurrentMonth(schemeBillgroupId, paybillMonth, paybillYear);
	}

	@Override
	public int getCheckIsBillInProcess(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.getCheckIsBillInProcess(monthName, yearName, schemeBillGroupId, paybillType);
	}

	@Override
	public String isEmpRetired(int monthName, int yearName, BigInteger schemeBillGroupId, int paybillType,
			String userName) {
		// TODO Auto-generated method stub
		return paybillHeadMpgRepo.isEmpRetired(monthName, yearName, schemeBillGroupId, paybillType, userName);
	}

	@Override
	public PaybillGenerationTrnEntity findPaybillById(Long paybillGenerationTrnId, String voucherNo, Date voucherDate,
			String chqNo, Date chequeDate, String accNo, String ifscCode, Long userId) {
		// TODO Auto-generated method stub

		PaybillGenerationTrnEntity objPaybillGeberationTrnEntity = paybillHeadMpgRepo
				.findPaybillById(paybillGenerationTrnId);
		if (objPaybillGeberationTrnEntity != null) {
			/*
			 * String Date=voucherDate; SimpleDateFormat formatter1=new
			 * SimpleDateFormat("dd/MM/yyyy"); Date date1=null; try { date1 =
			 * formatter1.parse(Date); } catch (ParseException e) { // TODO Auto-generated
			 * catch block e.printStackTrace(); }
			 */
			objPaybillGeberationTrnEntity.setVoucherNo(voucherNo);
			objPaybillGeberationTrnEntity.setVoucherDate(voucherDate);
			objPaybillGeberationTrnEntity.setChequeDate(chequeDate);
			objPaybillGeberationTrnEntity.setChequeNo(chqNo);
			objPaybillGeberationTrnEntity.setAccountNo(accNo);
			objPaybillGeberationTrnEntity.setIfscCode(ifscCode);
			objPaybillGeberationTrnEntity.setIsActive(14);
			objPaybillGeberationTrnEntity.setUpdatedDate(new Date());
			objPaybillGeberationTrnEntity.setUpdatedUserId(userId);
			paybillHeadMpgRepo.updateVoucherEntry(objPaybillGeberationTrnEntity);
		}
		return objPaybillGeberationTrnEntity;
	}

	@Override
	public List<MstEmployeeEntity> checkedBgisAndGisCatNull(String schemeBillGroupId, String userName) {
		// TODO Auto-generated method stub
		List<MstEmployeeEntity> lstMstEmployeeEntity = paybillHeadMpgRepo.checkedBgisAndGisCatNull(schemeBillGroupId,
				userName);
		return lstMstEmployeeEntity;
	}

}
