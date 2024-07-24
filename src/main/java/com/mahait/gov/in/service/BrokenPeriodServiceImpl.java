package com.mahait.gov.in.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.BrokenPeriodAllowDeducEntity;
import com.mahait.gov.in.entity.BrokenPeriodEntity;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
import com.mahait.gov.in.entity.LoanEmployeeDtlsEntity;
import com.mahait.gov.in.model.BrokenPeriodModel;
import com.mahait.gov.in.model.BrokenPeriodPayCustomModel;
import com.mahait.gov.in.model.BrokenPeriodResponseModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.repository.BrokenPeriodRepo;
import com.mahait.gov.in.repository.MstEmployeeRepo;
import com.mahait.gov.in.repository.PaybillGenerationTrnRepo;

@Service
@Transactional
public class BrokenPeriodServiceImpl implements BrokenPeriodService {

	@Autowired
	private BrokenPeriodRepo brokenPeriodRepo;

	@Autowired
	private MstEmployeeRepo mstEmployeeRepo;

	@Autowired
	MstEmployeeService mstEmployeeService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	PaybillGenerationTrnRepo paybillHeadMpgRepo;

	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<BrokenPeriodResponseModel> brokenPeriod(BrokenPeriodModel brokenPeriodModel,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel) {
		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(brokenPeriodModel.getSevaarthid(),
				brokenPeriodModel.getDdocode());

		/*
		 * logger.info("Employee Name="+mstEmployeeModel.getEmployeeFullNameEn());
		 * logger.info("Sevaarth Id="+mstEmployeeModel.getSevaarthId());
		 * logger.info("Designation Name="+mstEmployeeModel.getDesignationName());
		 * logger.info("Organisation Institution Name=");
		 * logger.info("G.P.F No / DCPS No="+mstEmployeeModel.getDcpsno());
		 * logger.info("Super Annuation Date="+mstEmployeeModel.getSuperAnnDate());
		 */
		// DD/MM/YYYY
		logger.info("mstEmployeeModel.getSuperAnnDate()=" + mstEmployeeModel.getSuperAnnDate());
		String strsuperAnnDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (mstEmployeeModel.getSuperAnnDate() != null)
			strsuperAnnDate = formatter.format(mstEmployeeModel.getSuperAnnDate());

		lstResult.add(mstEmployeeModel.getEmployeeFullNameEn());
		lstResult.add(mstEmployeeModel.getSevaarthId());
		lstResult.add(mstEmployeeModel.getDesignationName());
		lstResult.add("");
		lstResult.add(mstEmployeeModel.getDcpsno());
		lstResult.add(strsuperAnnDate);
		if (mstEmployeeModel.getEmployeeId() != null)
			lstResult.add(mstEmployeeModel.getEmployeeId().toString());

		BrokenPeriodResponseModel bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("EmpDetail");
		bpResponseModel.setData(lstResult);
		brokenPeriodResponseModel.add(bpResponseModel);
		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("Sevaarthid");
		bpResponseModel.setData(brokenPeriodModel.getSevaarthid());
		brokenPeriodResponseModel.add(bpResponseModel);
		/*
		 * bpResponseModel = new BrokenPeriodResponseModel();
		 * bpResponseModel.setStatus("ddoCode");
		 * bpResponseModel.setData(brokenPeriodModel.getDdocode());
		 * brokenPeriodResponseModel.add(bpResponseModel);
		 */
		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData(locId);
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		logger.info("Sevaarth Id=" + brokenPeriodModel.getSevaarthid());
		// List<Object[]> fetchAllowDeducName(String sevaarthid)
		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(brokenPeriodModel.getSevaarthid());
		/* return displayOuterReportRepo.getAllDataForOuternew(ddocode); */

		List<BrokenPeriodModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				BrokenPeriodModel obj = new BrokenPeriodModel();
				obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
				obj.setType(StringHelperUtils.isNullInt(objLst[1]));
				obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
				// obj.setTempvalue(StringHelperUtils.isNullString(0);
				// obj.setTempempty(StringHelperUtils.isNullString('');
				// logger.info("objLst[0]="+objLst[0]+" && objLst[1]="+objLst[1]);
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;
		// List<DeptEligibilityForAllowAndDeductEntity> dptallDeduc =
		// deptEligibilityForAllowAndDeductService.findDeptEligibilityForAllowAndDeductList();
		for (int i = 0; i < allEdpList.size(); i++) {
			// if (allEdpList.get(i).getType() != null) {

			if (allEdpList.get(i).getType() == 1) { // allowance
				allowEdpList.add(allEdpList.get(i));
				allowRuleList.add(allEdpList.get(i));
			} else if (allEdpList.get(i).getType() == 2) {
				if (allEdpList.get(i).getDeptallowdeducid() == 36 || allEdpList.get(i).getDeptallowdeducid() == 37
						|| allEdpList.get(i).getDeptallowdeducid() == 38
						|| allEdpList.get(i).getDeptallowdeducid() == 35)
					dedRuleList.add(allEdpList.get(i)); // Deductions Adj. By CAFO/Supri./Admin.
				else
					dedRuleList.add(allEdpList.get(i));

			} else {
				dedRuleList.add(allEdpList.get(i));// deducOthEdpList.add(allEdpList.get(i));
			}
		}
		// Dynamic Process end
		// BrokenPeriodResponseModel bpResponseMode2 =new BrokenPeriodResponseModel();
		// bpResponseMode2.setStatus("allowEdpList");
		// bpResponseMode2.setData(allowEdpList);
		// brokenPeriodResponseModel.add(bpResponseMode2);
		//
		// BrokenPeriodResponseModel bpResponseMode3 =new BrokenPeriodResponseModel();
		// bpResponseMode3.setStatus("deducAgEdpList");
		// bpResponseMode3.setData(deducAgEdpList);
		// brokenPeriodResponseModel.add(bpResponseMode3);
		//
		// BrokenPeriodResponseModel bpResponseMode4 =new BrokenPeriodResponseModel();
		// bpResponseMode4.setStatus("deducTyEdpList");
		// bpResponseMode4.setData(deducTyEdpList);
		// brokenPeriodResponseModel.add(bpResponseMode4);
		//
		// BrokenPeriodResponseModel bpResponseMode5 =new BrokenPeriodResponseModel();
		// bpResponseMode5.setStatus("deducOthEdpList");
		// bpResponseMode5.setData(deducOthEdpList);
		// brokenPeriodResponseModel.add(bpResponseMode5);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("hidTotalAllowances");
		bpResponseModel.setData(allowRuleList.size());
		brokenPeriodResponseModel.add(bpResponseModel);
		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("hidTotalDeductions");
		bpResponseModel.setData(dedRuleList.size());
		brokenPeriodResponseModel.add(bpResponseModel);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("allowRuleList");
		bpResponseModel.setData(allowRuleList);
		brokenPeriodResponseModel.add(bpResponseModel);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("dedRuleList");
		bpResponseModel.setData(dedRuleList);
		brokenPeriodResponseModel.add(bpResponseModel);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("basicAmt");
		bpResponseModel.setData(mstEmployeeModel.getBasicPay());
		brokenPeriodResponseModel.add(bpResponseModel);

		// Fetch Saved Data from Broken Period
		// start######################################################################################
		if (mstEmployeeModel.getEmployeeId() != null)
			if (!brokenPeriodRepo.checkBrokenPeriodPayExistsOrNot(mstEmployeeModel.getEmployeeId().longValue(),
					Long.valueOf(brokenPeriodModel.getPaybillYear()), Long.valueOf(brokenPeriodModel.getPaybillMonth()),
					brokenPeriodModel.getDdocode())) {
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("PaysAddedBefore");
				bpResponseModel.setData(false);
				brokenPeriodResponseModel.add(bpResponseModel);
				brokenPeriodResponseModel.add(bpResponseModel);
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("hidTotalRows");
				bpResponseModel.setData(1);
				brokenPeriodResponseModel.add(bpResponseModel);
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("BrokenPeriodPayListSize");
				bpResponseModel.setData(0);
				brokenPeriodResponseModel.add(bpResponseModel);

				// logger.info("brokenPeriodResponseModel="+brokenPeriodResponseModel);
			} else {

				List<BrokenPeriodEntity> lListAddedBrokenPeriodPays = brokenPeriodRepo.getAddedBrokenPeriodPaysForEmp(
						mstEmployeeModel.getEmployeeId().longValue(), Long.valueOf(brokenPeriodModel.getPaybillYear()),
						Long.valueOf(brokenPeriodModel.getPaybillMonth()), brokenPeriodModel.getDdocode());
				List DataForDisplayList = new ArrayList();
				for (Integer lInt = 0; lInt < lListAddedBrokenPeriodPays.size(); lInt++) {
					List lListAddedAllowances = new ArrayList();
					List lListAddedAllowancesNew = new ArrayList();
					List lListTempAddedAllowances = new ArrayList();
					List lListAddedDeductions = new ArrayList();
					List lListAddedDeductionsNew = new ArrayList();
					List lListTempAddedDeductions = new ArrayList();
					BrokenPeriodPayCustomModel brokenPeriodPayCustomVO = new BrokenPeriodPayCustomModel();

					BrokenPeriodEntity brokenPeriodPay = lListAddedBrokenPeriodPays.get(lInt);
					brokenPeriodPayCustomVO.setFromDate(brokenPeriodPay.getFromDate());
					brokenPeriodPayCustomVO.setToDate(brokenPeriodPay.getToDate());
					brokenPeriodPayCustomVO.setNoOfDays(brokenPeriodPay.getNoOfDays().longValue());
					brokenPeriodPayCustomVO.setBasicPay(brokenPeriodPay.getBasicPay().longValue());
					brokenPeriodPayCustomVO.setNetPay(brokenPeriodPay.getNetPay().longValue());
					brokenPeriodPayCustomVO.setReason(brokenPeriodPay.getReason());
					brokenPeriodPayCustomVO.setRemarks(brokenPeriodPay.getRemarks());
					brokenPeriodPayCustomVO.setBasicForCalculation(brokenPeriodPay.getBasicForCalculation());

					lListTempAddedAllowances = brokenPeriodRepo.getAddedAllowancesForEmp(
							lListAddedBrokenPeriodPays.get(lInt).getBrokenPeriodId().longValue());
					lListAddedAllowances.addAll(lListTempAddedAllowances);

					/*
					 * for (int i = 0; i < (lListAllowancesForEmp != null ?
					 * lListAllowancesForEmp.size() : 0); i++) { Long allowCode = (Long)
					 * (((Object[]) lListAllowancesForEmp.get(i))[0]); boolean found = false; for
					 * (int j = 0; j < (lListAddedAllowances != null ? lListAddedAllowances.size() :
					 * 0); j++) { Object[] data = (Object[]) lListAddedAllowances.get(j); if
					 * (Arrays.asList(data).contains(allowCode)) {
					 * lListAddedAllowancesNew.add(data); found = true; break; } } if (!found) {
					 * String allowDesc = (String) (((Object[]) lListAllowancesForEmp.get(i))[1]);
					 * Object newData[] = { 0, 0, allowCode, 0, allowDesc };
					 * lListAddedAllowancesNew.add(newData); } }
					 */

					List lListAllowancesForEmp = brokenPeriodRepo
							.getAllowancesListForGivenEmp(brokenPeriodModel.getSevaarthid());
					List lListDeductionsForEmp = brokenPeriodRepo
							.getDeductionsListForGivenEmp(brokenPeriodModel.getSevaarthid());
					for (int i = 0; i < (lListAllowancesForEmp != null ? lListAllowancesForEmp.size() : 0); i++) {
						Long allowCode = (Long) Long.valueOf((((Object[]) lListAllowancesForEmp.get(i))[0]).toString());
						boolean found = false;
						for (int j = 0; j < (lListAddedAllowances != null ? lListAddedAllowances.size() : 0); j++) {
							Object[] data = (Object[]) lListAddedAllowances.get(j);
							if (Arrays.asList(data).contains(allowCode.intValue())) {
								lListAddedAllowancesNew.add(data);
								found = true;
								break;
							}
						}
						if (!found) {
							String allowDesc = (String) (((Object[]) lListAllowancesForEmp.get(i))[1]).toString();
							Object newData[] = { 0, 0, allowCode, 0, allowDesc };
							lListAddedAllowancesNew.add(newData);
						}
					}
					logger.info("lListAddedAllowancesNew=" + lListAddedAllowancesNew);

					brokenPeriodPayCustomVO.setAllowList(lListAddedAllowancesNew);
					// for (Iterator iterator = lListAddedAllowancesNew.iterator();
					// iterator.hasNext();) {
					// Object[] object = (Object[]) iterator.next();
					//
					// logger.info("allowance object[]="+object);
					// }

					/*
					 * bpResponseModel =new BrokenPeriodResponseModel();
					 * bpResponseModel.setStatus("hidTotalAllowances");
					 * bpResponseModel.setData(lListAddedAllowancesNew.size());
					 * brokenPeriodResponseModel.add(bpResponseModel);
					 */

					lListTempAddedDeductions = brokenPeriodRepo.getAddedDeductionsForEmp(
							lListAddedBrokenPeriodPays.get(lInt).getBrokenPeriodId().longValue());
					lListAddedDeductions.addAll(lListTempAddedDeductions);

					for (int i = 0; i < (lListDeductionsForEmp != null ? lListDeductionsForEmp.size() : 0); i++) {
						Long deducCode = (Long) Long.valueOf((((Object[]) lListDeductionsForEmp.get(i))[0]).toString());
						boolean found = false;
						for (int j = 0; j < (lListAddedDeductions != null ? lListAddedDeductions.size() : 0); j++) {
							Object[] data = (Object[]) lListAddedDeductions.get(j);
							if (Arrays.asList(data).contains(deducCode.intValue())) {
								lListAddedDeductionsNew.add(data);
								found = true;
								break;
							}
						}
						if (!found) {
							String deducDesc = (String) (((Object[]) lListDeductionsForEmp.get(i))[1]);
							Object newData[] = { 0, 0, deducCode, 0, deducDesc };
							lListAddedDeductionsNew.add(newData);
						}
					}

					/*
					 * bpResponseModel =new BrokenPeriodResponseModel();
					 * bpResponseModel.setStatus("hidTotalDeductions");
					 * bpResponseModel.setData(lListAddedDeductionsNew.size());
					 * brokenPeriodResponseModel.add(bpResponseModel);
					 */

					// Long eisEmpId = lListAddedBrokenPeriodPays.get(lInt).getEisEmpId();
					// lLongYearId, lLongMonthId

					// generated = lObjBrokenPeriodDAO.isGenerated(lLongHrEisEmpId, lLongMonthId,
					// lLongYearId);
					brokenPeriodPayCustomVO.setDeductList(lListAddedDeductionsNew);
					DataForDisplayList.add(brokenPeriodPayCustomVO);

				}
				// logger.info("Current date is..."+lObjDateFormat.format(gDtCurDate));
				// inputMap.put("yearId", lStrYear);
				// inputMap.put("monthId", lStrMonth);
				// inputMap.put("lDtCurDate", lObjDateFormat.format(gDtCurDate));
				/*
				 * inputMap.put("AddedAllowances", lListAddedAllowancesNew);
				 * inputMap.put("AddedDeductions", lListAddedDeductionsNew);
				 * inputMap.put("BrokenPeriodPayList", lListAddedBrokenPeriodPays);
				 */
				// inputMap.put("DataForDisplayList", DataForDisplayList);
				// inputMap.put("BrokenPeriodPayListSize", lListAddedBrokenPeriodPays.size());
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("hidTotalRows");
				bpResponseModel.setData(lListAddedBrokenPeriodPays.size());
				brokenPeriodResponseModel.add(bpResponseModel);
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("DataForDisplayList");
				bpResponseModel.setData(DataForDisplayList);
				brokenPeriodResponseModel.add(bpResponseModel);
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("BrokenPeriodPayListSize");
				bpResponseModel.setData(lListAddedBrokenPeriodPays.size());
				brokenPeriodResponseModel.add(bpResponseModel);
				bpResponseModel = new BrokenPeriodResponseModel();
				bpResponseModel.setStatus("PaysAddedBefore");
				bpResponseModel.setData(true);
				brokenPeriodResponseModel.add(bpResponseModel);
				// inputMap.put("PaysAddedBefore", "Yes");
				// inputMap.put("Generated", generated); // 0 = generated, 1(gen or apprvd)
				// logger.info("brokenPeriodResponseModel="+brokenPeriodResponseModel);
			}
		// Fetch Saved Data from Broken Period
		// end######################################################################################

		return brokenPeriodResponseModel;
	}

	// public List searchEmployee(BrokenPeriodModel brokenPeriodModel)
	// {
	// BrokenPeriodModel brokenPeriodModel
	// return
	// }

	@Override
	public List<BrokenPeriodResponseModel> calculateEmployeeSalary(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, HashMap hmInputParam) {
		Double grossAmt = 0d;
		Double netAmt = 0d;
		Double da = 0d;
		Double hra = 0d;
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
		Long gradePay = 0l;
		Double basic = 0d;
		Double npsEmprAllow = 0d;
		Double npsEmprContri = 0d;
		Double npsEmpContri = 0d;
		Double cmFund = 0d;
		Double revenueStamp = 0d;
		Double excessPayment = 0d;
		Double HouseAdv = 0d;
		Double CA = 0d;
		Double FA = 0d;
		Double vehAdv = 0d;
		Double excessPayrec = 0d;
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
		Double gpfDaSub = 0d;
		Double rop = 0d;
		Double payFixDiff = 0d;
		Double nps = 0d;
		Double gpfAdvance = 0d;
		Double hra6th = 0d;
		Double ta5th = 0d;
		String hbaHouseInst = null;
		String otherVehAdvInst = null;
		String excessPayrecInst = null;
		Double jalnaSoc = 0d;
		Double amrawatidistEnggCredSoc = 0d;
		Date fromDate = null;
		Date toDate = null;
		Double HouseAdvInstAmt = 0d;
		Double motorCycleAdvInst = 0d;
		Double motorCycleAdv = 0d;
		String motorCycleInst = null;

		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(sevaarthid, hmInputParam.get("ddocode").toString());
		// logger.info("Employee Name="+mstEmployeeModel.getEmployeeFullNameEn());
		// logger.info("Basic Pay="+mstEmployeeModel.getBasicPay());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			fromDate = sdf.parse(hmInputParam.get("fromDate").toString());
			toDate = sdf.parse(hmInputParam.get("toDate").toString());

		} catch (Exception e) {
			System.out.println("" + e);

		}

		String strDate = sdf.format(fromDate);

		System.out.println("--------strDate-----------" + strDate);
		int suspensepercent = 0;
		/*
		 * EmployeeSuspensionEntity
		 * employeeSuspensionEntity=mstEmployeeService.getSuspensionPercentage(
		 * mstEmployeeModel.getSevaarthId(),strDate);
		 * if(employeeSuspensionEntity.getPercentage()!=null) { suspensepercent
		 * =employeeSuspensionEntity.getPercentage(); }
		 */

		// BigInteger empCount =
		// mstEmployeeService.findEmpSuspend(mstEmployeeModel.getSevaarthId());

		if (suspensepercent != 0) {
			if (mstEmployeeModel.getBasicPay() != null && mstEmployeeModel.getBasicPay() > 0
					&& mstEmployeeModel.getPayCommissionCode() != 700005) {
				/// basic = mstEmployeeModel.getBasicPay()*suspensepercent;
				basic = (double) (Math.round((mstEmployeeModel.getBasicPay() * suspensepercent)
						/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
			} else {
				basic = (double) (Math.round((mstEmployeeModel.getSevenPcBasic() * suspensepercent)
						/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
			}

		} else {
			if (mstEmployeeModel.getBasicPay() != null && mstEmployeeModel.getBasicPay() > 0
					&& mstEmployeeModel.getPayCommissionCode() != 700005) {
				basic = mstEmployeeModel.getBasicPay();
			} else {
				basic = mstEmployeeModel.getSevenPcBasic();
			}

		}

		if (mstEmployeeModel.getGradePay() != null)
			gradePay = mstEmployeeModel.getGradePay();
		// Total number of days and Fractionofdays
		int year = Integer.parseInt(hmInputParam.get("year").toString());
		int month = Integer.parseInt(hmInputParam.get("month").toString());

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fromDate);
		/*
		 * cal2.set(Calendar.YEAR, year);
		 * 
		 * // cal2.set(Calendar.MONTH, month-1); cal2.set(Calendar.MONTH, month);
		 */
		// java.util.Date finYrDate = cal2.getTime();
		int totalNoOfDays = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);

		int noOfDays = Integer.parseInt(hmInputParam.get("noOfDays").toString());
		gradePay = (long) Math.round((gradePay * noOfDays) / totalNoOfDays);
		basic = (double) Math.round((basic * noOfDays) / totalNoOfDays);
		mstEmployeeModel.setBasicPay(basic);
		/*
		 * logger.info("basic Day wise= "+basic); logger.info("month="+month);
		 * logger.info("year="+year); logger.info("totalNoOfDays="+totalNoOfDays);
		 * logger.info("noOfDays="+noOfDays);
		 * logger.info("Paycommission id="+mstEmployeeModel.getPayCommissionCode());
		 */

		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData();
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData();
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData();
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		logger.info("Sevaarth Id=" + sevaarthid);
		// List<Object[]> fetchAllowDeducName(String sevaarthid)

		String cityClass = mstEmployeeModel.getCityClass().toString();
		mstEmployeeModel.setCityClass(cityClass);

		int payCommission = mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
		logger.info(" payCommission " + payCommission + "cityClass " + cityClass);

		// basicAmount=mstEmployeeModel.getBasicPay();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(sevaarthid);
		/* return displayOuterReportRepo.getAllDataForOuternew(ddocode); */

		List<BrokenPeriodModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				BrokenPeriodModel obj = new BrokenPeriodModel();
				obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
				obj.setType(StringHelperUtils.isNullInt(objLst[1]));
				obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
				obj.setGroupNm(StringHelperUtils.isNullString(objLst[3]));
				obj.setGisAmount(StringHelperUtils.isNullDouble(objLst[4]));
				// obj.setTempvalue(StringHelperUtils.isNullString(0);
				// obj.setTempempty(StringHelperUtils.isNullString('');
				// logger.info("objLst[0]="+objLst[0]+" && objLst[1]="+objLst[1]);
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;
		// List<DeptEligibilityForAllowAndDeductEntity> dptallDeduc =
		// deptEligibilityForAllowAndDeductService.findDeptEligibilityForAllowAndDeductList();
		for (int i = 0; i < allEdpList.size(); i++) {
			// if (allEdpList.get(i).getType() != null) {

			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			// logger.info("COMMONCODE_COMPONENT_GRP_ACC_POLICY="+CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY);
			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptallowdeducid()=" + allEdpList.get(i).getDeptallowdeducid());
			logger.info("svnDAloop=" + svnDA);
			String name = allEdpList.get(i).getDeptalldetNm();

			String temp = name;
			// allEdpList.get(i).getType()
			int percentage = 0;
			String percentageHRA = null;
			String startDate = null;
			String citygroup = null;
			int month2 = month + 1;
			int year2 = year;
			if (month2 < 10) {
				startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf("0" + month2) + "-01";
			} else {
				startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf(month2) + "-01";
			}

			DdoOffice ddoScreenEntity = mstEmployeeRepo.findAllGroup(mstEmployeeModel.getDdoCode().trim());

			String spilt[] = ddoScreenEntity.getDcpsDdoOfficeCityClass().split("-");

			citygroup = spilt[1];

			if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				if (payCommission == 700005) {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
							mstEmployeeModel.getCityClass());
				}/* else {
					percentage = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
				}*/
			} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
				percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, mstEmployeeModel.getCityClass());
			}
			if (allEdpList.get(i).getType() == 1) {
				// allowance
				if ((allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA))) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					// Start : 7 pc Calculation--> 17% of basic
					svnDA = (double) (Math.round((mstEmployeeModel.getBasicPay() * percentage)
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// End : 7 pc Calculation
					logger.info("svnDA component3=" + svnDA);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(svnDA));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
					logger.info("svnDA component4=" + svnDA);
				} else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
						&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
						&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_SIXTH_PAY_DA
								|| payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					da = (double) (Math.round((mstEmployeeModel.getBasicPay() * percentage)
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// paybillGenerationTrnDetails.setDa( (double) Math.round((da)));
					brokenPeriodModel.setDeptalldetValue(String.valueOf((da)));
					logger.info("da Component=" + da);
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
					logger.info("DA componentexe=" + svnDA);

				}
				// End for 6PC and 7PC DA

				/* HRA component */
				else if (allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					// Start:HRA Calculation --> for 7 pc 8 % of basic for Z class / 16% of basic
					// for Y class / 24% of basic for X class ( for 6 pc --> 10% 20% 30%)
					if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

						if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
								&& mstEmployeeModel.getBasicPay() >= 22500)
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							hra = (double) 1800;
					}

					else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
						if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					}
					// logger.info(" HRA = "+ hra);
					// hra=(hra * noOfDays) / totalNoOfDays ;
					// End: HRA Calculation
					logger.info("hra Component=" + hra);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}

				// HRA 6th

				else if (allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code);

					if (employeeAllowDeducComponentAmtEntity != null) {

						hra6th = (double) (Math
								.round((employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
					}
					brokenPeriodModel.setDeptalldetValue(String.valueOf(hra6th));
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DEARNESS_PAY)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DP_CODE);
					if (employeeAllowDeducComponentAmtEntity != null) {
						
						dearnessPay = (double) (Math
								.round((employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
					}else {

						brokenPeriodModel = allEdpList.get(i);
						dearnessPay = 0d;
						brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
					}
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);

				}

				// Start Travels Allowances for 6PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC && allEdpList
								.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							ta = (double) 2400;
						} else {
							ta = (double) 1200;
						}
					} else if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
							&& gradePay < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							ta = (double) 1200;
						} else {
							ta = (double) 600;
						}
					} else if (gradePay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
						ta = (double) 400;
					}
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
					ta = (ta * noOfDays) / totalNoOfDays;
					logger.info("ta Component=" + ta);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);

				}
				// End Travels Allowances for 6PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradelevel >= 20) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 3200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((3200 * noOfDays) / totalNoOfDays);
							} else {
								ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								// ta5th = (double) ((1600) / totalNoOfDays);
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((800 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradelevel >= 15 && gradelevel <= 19) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);

							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);

							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 400d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((400 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradelevel >= 1 && gradelevel <= 14) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((200 * noOfDays) / totalNoOfDays);

								// brokenPeriodModel.setDeptalldetValue(String.valueOf((200 * noOfDays) /
								// totalNoOfDays) );
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 150d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((150 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((150 * noOfDays) /
								// totalNoOfDays) );
							}
						}
						if (mstEmployeeModel.getSevaarthId().equals("MJPDMGM6601")) {
							// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
							Double taAmt = 400d;
							ta5th = ((taAmt * noOfDays) / totalNoOfDays);

						}

					}
					// ta5th=(ta5th * noOfDays) / totalNoOfDays ;
					ta5th = (double) Math.round(ta5th);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta5th));
					logger.info("ta Component=" + ta5th);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);

				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 3200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((3200 * noOfDays) / totalNoOfDays);
							} else {
								ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								// ta5th = (double) ((1600) / totalNoOfDays);
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((800 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
							&& gradePay < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);

							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);

							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 400d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((400 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradePay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((200 * noOfDays) / totalNoOfDays);

								// brokenPeriodModel.setDeptalldetValue(String.valueOf((200 * noOfDays) /
								// totalNoOfDays) );
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 150d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((150 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((150 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					}
					ta5th = (double) Math.round(ta5th);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta5th));
					logger.info("ta Component=" + ta5th);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}
				// Start Travels Allowances for 7PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC && allEdpList
								.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					String gradePay7PC = paybillHeadMpgRepo.getgradePay7PC(gradelevel);
					Integer grade7PC = Integer.parseInt(gradePay7PC);

					if ((month2 + 1 >= 4 && year >= 2022) || year <= 2023) {

						if (gradelevel >= 20) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 10800;
								} else {
									ta = (double) 5400;
								}
							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 5400;
								} else {
									ta = (double) 2700;
								}
							}
						} else if (gradelevel >= 7 && gradelevel <= 19) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 5400;
								} else {
									ta = (double) 2700;
								}
							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 2700;
								} else {
									ta = (double) 1350;
								}
							}
						} else if (gradelevel >= 1 && gradelevel <= 6) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									if (mstEmployeeModel.getSevenPcBasic() >= 24200) {
										ta = (double) 5400;
									} else {
										ta = (double) 2700;
									}
								} else if (mstEmployeeModel.getSevenPcBasic() >= 24200 && cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									ta = (double) 2700;
								} else {
									ta = (double) 1000;
								}

							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									if (mstEmployeeModel.getSevenPcBasic() >= 24200) {
										ta = (double) 2700;
									} else {
										ta = (double) 2250;
									}
								} else if (mstEmployeeModel.getSevenPcBasic() >= 24200 && !cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									ta = (double) 1350;
								} else {
									ta = (double) 675;
								}

							}
						}
					} else {
						if (grade7PC >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (cityClass.equals("X")) {
								ta = (double) 2400;
							} else {
								ta = (double) 1200;

							}
						} else if (grade7PC >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
								&& grade7PC <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								ta = (double) 2000;
							} else if (cityClass.equals("X")) {
								ta = (double) 1200;
							} else {
								ta = (double) 600;
							}
						} else if (grade7PC >= 0
								&& grade7PC <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								ta = (double) 2000;
							} else {
								ta = (double) 400;
							}
						}
					}
					ta = ((ta * noOfDays) / totalNoOfDays);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
					logger.info("ta Component=" + ta);
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}

				// End Travels Allowances for 7PC
				// Start Tribal Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) {
						logger.info(" Inside iff for COMMONCODE_COMPONENT_TRIBAL_ALLOW ");
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Tribal Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** "
								+ employeeAllowDeducComponentAmtEntity.getNetAmt());
					} else {
						brokenPeriodModel = allEdpList.get(i);
						/*
						 * tribalAllow = (double) Math.round((mstEmployeeModel.getBasicPay()) * 6 /
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						 */
						tribalAllow = 0d;
						logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** " + tribalAllow);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(tribalAllow));
						logger.info("Tribal Allow Component=" + String.valueOf(tribalAllow));
					}

					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}
				// transport Allowance Arr
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) {
						logger.info(" Inside iff for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR ");
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Trans Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info(" for setGpfGrpD********** " + employeeAllowDeducComponentAmtEntity.getNetAmt());
					} else {
						brokenPeriodModel = allEdpList.get(i);
						/*
						 * transAllowArr = (double) Math.round((mstEmployeeModel.getBasicPay()) * 6 /
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						 */
						transAllowArr = 0d;
						logger.info(" for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR********** " + transAllowArr);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(transAllowArr));
						logger.info("Trans Allow Component=" + String.valueOf(transAllowArr));
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// DA Arr
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(
								(employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
						DaArr = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays;
						logger.info(
								"Da Arr Component=" + String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						/*
						 * paybillGenerationTrnDetails .setDaArr((double)
						 * (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt())))); DaArr+=
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						 */
					}

					else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
						// paybillGenerationTrnDetails
						// .setDaArr((double) 0);
						// DaArr+=0;

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// Additional HRA
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE) {

						/*
						 * paybillGenerationTrnDetails .setAddHRA((double)
						 * (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt()))));
						 * addHRA+=Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Add hra Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setAddHRA((double) 0); addHRA+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// Naxal Area Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails .setNaxalAreaAllow((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * naxalAreaAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())
						 * );
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("naxal area  Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setNaxalAreaAllow((double) 0);
						 * naxalAreaAllow+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Special Duty Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails .setSpclDutyAllow((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * spclDutyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))
						 * ;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Special Duty Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setSpclDutyAllow((double) 0); spclDutyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Other Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE) {
						/*
						 * paybillGenerationTrnDetails .setOtherAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * otherAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Other  Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherAllow((double) 0); otherAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// Basic Arr //
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails .setBasicArr((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * basicArr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("basic arr Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setBasicArr((double) 0); basicArr+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Special Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE) {

						/*
						 * paybillGenerationTrnDetails.setSpecialPay((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * logger.info("Inside special pay"+employeeAllowDeducComponentAmtEntity.
						 * getNetAmt());
						 * specialPay+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("special pay Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setSpecialPay((double) 0); specialPay+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// COMMONCODE_COMPONENT_LeavePay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LeavePay_Code) {

						/*
						 * paybillGenerationTrnDetails.setSpecialPay((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * logger.info("Inside special pay"+employeeAllowDeducComponentAmtEntity.
						 * getNetAmt());
						 * specialPay+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("special pay Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setSpecialPay((double) 0); specialPay+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Personal Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE) {

						/*
						 * paybillGenerationTrnDetails.setPersonalPay((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * personalPay+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("personal pay Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setPersonalPay((double) 0); personalPay+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// CLA 5th Pay Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null
							&& employeeAllowDeducComponentAmtEntity.getNetAmt() > 0d && allEdpList.get(i)
									.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) {
						Double claaa = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays;
						claaa = (double) Math.round(claaa);
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(claaa));
						logger.info("DP=" + String.valueOf(claaa));
					} else {
						/*
						 * paybillGenerationTrnDetails .setDearnessPay((double) 0); dearnessPay+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);

						Double claamt = 0d;
						if (payCommission == 8) {
							if (basic < 3000) {
								if (citygroup.equals("A+")) {
									claamt = 90d;
								} else if (citygroup.equals("A")) {
									claamt = 65d;
								} else if (citygroup.equals("B+")) {
									claamt = 45d;
								} else if (citygroup.equals("B")) {
									claamt = 25d;
								}
							} else if (basic >= 3000 && basic < 4499) {
								if (citygroup.equals("A+")) {
									claamt = 125d;
								} else if (citygroup.equals("A")) {
									claamt = 95d;
								} else if (citygroup.equals("B+")) {
									claamt = 65d;
								} else if (citygroup.equals("B")) {
									claamt = 35d;
								}
							} else if (basic >= 4500 && basic < 5999) {
								if (citygroup.equals("A+")) {
									claamt = 200d;
								} else if (citygroup.equals("A")) {
									claamt = 150d;
								} else if (citygroup.equals("B+")) {
									claamt = 100d;
								} else if (citygroup.equals("B")) {
									claamt = 65d;
								}
							} else {
								if (citygroup.equals("A+")) {
									claamt = 300d;
								} else if (citygroup.equals("A")) {
									claamt = 240d;
								} else if (citygroup.equals("B+")) {
									claamt = 180d;
								} else if (citygroup.equals("B")) {
									claamt = 120d;
								}
							}
						} else {
							if (basic < 3000) {
								if (citygroup.equals("A+")) {
									claamt = 90d;
								} else if (citygroup.equals("A")) {
									claamt = 65d;
								} else if (citygroup.equals("B+")) {
									claamt = 45d;
								} else if (citygroup.equals("B")) {
									claamt = 25d;
								}
							} else if (basic >= 3000 && basic < 4499) {
								if (citygroup.equals("A+")) {
									claamt = 125d;
								} else if (citygroup.equals("A")) {
									claamt = 95d;
								} else if (citygroup.equals("B+")) {
									claamt = 65d;
								} else if (citygroup.equals("B")) {
									claamt = 35d;
								}
							} else if (basic >= 4500 && basic < 5999) {
								if (citygroup.equals("A+")) {
									claamt = 200d;
								} else if (citygroup.equals("A")) {
									claamt = 150d;
								} else if (citygroup.equals("B+")) {
									claamt = 100d;
								} else if (citygroup.equals("B")) {
									claamt = 65d;
								}
							} else {
								if (citygroup.equals("A+")) {
									claamt = 300d;
								} else if (citygroup.equals("A")) {
									claamt = 240d;
								} else if (citygroup.equals("B+")) {
									claamt = 180d;
								} else if (citygroup.equals("B")) {
									claamt = 120d;
								}
							}
						}
						Double clanew = (claamt * noOfDays) / totalNoOfDays;
						clanew = (double) Math.round(clanew);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(clanew));
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Conveyance Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails.setConveyAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * conveyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("convey Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setConveyAllow((double) 0); conveyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// NPS Empr Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)) {

					BrokenPeriodModel brokenPeriodModel = null;
					brokenPeriodModel = allEdpList.get(i);
					///if (mstEmployeeModel.getGiscatagory().equals(1)) {
						if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {

							Double DaArr1 = 0d;
							Double DaArr2 = 0d;
							Double DaArrtenpersent = 0d;
							Double DaArrforteenpersent = 0d;
							Double totalDaArr = 0d;
							if (month2 == 11 && year == 2023) {
								DaArr1 = DaArr / 4;
								DaArr2 = DaArr - DaArr1;
								DaArrtenpersent = (DaArr1 * 10 / 100);
								DaArrforteenpersent = (DaArr2 * 14 / 100);
								totalDaArr = DaArrtenpersent + DaArrforteenpersent;
								npsEmprAllow = (double) (Math
										.round((((mstEmployeeModel.getBasicPay() + svnDA) * 14) / 100) + totalDaArr));
							} else if (month == 12 && year == 2023) {
								DaArr1 = DaArr / 5;
								DaArr2 = DaArr - DaArr1;
								DaArrtenpersent = (DaArr1 * 10 / 100);
								DaArrforteenpersent = (DaArr2 * 14 / 100);
								totalDaArr = DaArrtenpersent + DaArrforteenpersent;
								npsEmprAllow = (double) (Math
										.round((((mstEmployeeModel.getBasicPay() + svnDA) * 14) / 100) + totalDaArr));
							} else {
								npsEmprAllow = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr)
										* 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							}

						} else {
							npsEmprAllow = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}

					/*} else {
						npsEmprAllow = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}*/

					/*
					 * if (year >= 20 && month >= 8) { if (mstEmployeeModel.getBasicPay() != 0) {
					 * npsEmprAllow += (double) (Math.round((mstEmployeeModel.getBasicPay() + da +
					 * svnDA) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * brokenPeriodModel = allEdpList.get(i); }
					 * 
					 * } else { npsEmprAllow += (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + da + svnDA) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); brokenPeriodModel
					 * = allEdpList.get(i);
					 * 
					 * }
					 */
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprAllow));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_on_TA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE);

					BrokenPeriodModel brokenPeriodModel = null;
					/*
					 * DaOnTA+= (Math.round((mstEmployeeModel.getBasicPay()) * 17 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * paybillGenerationTrnDetails.setDaOnTA((double) (Math.round(DaOnTA)));
					 */
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE) {

						/*
						 * paybillGenerationTrnDetails.setConveyAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * conveyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Da on TA Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setConveyAllow((double) 0); conveyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Deputation_Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// OverTime Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Hill_Station_Allowances
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Tracer_Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Naksalied_Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);

						Double naksaliseallow = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays)
								/ totalNoOfDays;
						naksaliseallow = (double) Math.round(naksaliseallow);
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(naksaliseallow));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}

					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// Washing_Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance)) {

					BrokenPeriodModel brokenPeriodModel = null;
					logger.info(" Inside iff for COMMONCODE_COMPONENT_TRIBAL_ALLOW ");
					brokenPeriodModel = allEdpList.get(i);

					Double consVal = 50d;
					wa = ((consVal * noOfDays) / totalNoOfDays);

					wa = (double) Math.round(wa);

					brokenPeriodModel.setDeptalldetValue(String.valueOf(wa));
					System.out.println("Tribal Allow Component=" + String.valueOf(wa));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}

				// NDCPS_REC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_REC_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}

					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// End For Allowances//

				else {
					allEdpList.get(i).setDeptalldetValue("0");
					allowRuleList.add(allEdpList.get(i));
				}

			} else if (allEdpList.get(i).getType() == 2) {

				// ###################################################################
				// Professional Tax//
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (month2 == 2) {
						// logger.info("Inside checking paybillMonth" +
						// paybillHeadMpgModel.getPaybillMonth());
						if (mstEmployeeModel
								.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
								&& mstEmployeeModel
										.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
						else
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;

					} else {

						if (mstEmployeeModel
								.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
								&& mstEmployeeModel
										.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
						else
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
					}
					// paybillGenerationTrnDetails.setPt(pt);
					logger.info("pt deduc Component=" + String.valueOf(pt));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
					deducTyEdpList.add(brokenPeriodModel); // Adjust by Treasury
					// dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
					dedRuleList.add(brokenPeriodModel);

				}
				// DCPS DA Arrear recovery//
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_DA_ARR_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsDa =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsDa =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else { dcpsDa =
				 * 0d; } // paybillGenerationTrnDetails.setDcpsDa((double) Math.round(dcpsDa));
				 * logger.info("dcpsDa deduc Component="+String.valueOf(dcpsDa));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDa));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails.setDcpsDa((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						dcpsDa += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDa((double) 0);
						 */
						dcpsDa += 0;
					}
					logger.info("dcpsDa deduc Component=" + String.valueOf(dcpsDa));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDa));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// DCPS Delay recovery
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_DCPS_DELAY_RECOVERY) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsDelay =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsDelay =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * dcpsDelay = 0d; } // paybillGenerationTrnDetails.setDcpsDelay((double)
				 * Math.round(dcpsDelay));
				 * logger.info("dcpsDelay deduc Component="+String.valueOf(dcpsDelay));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsDelay += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						dcpsDelay += 0;
					}
					logger.info("dcpsDelay deduc Component=" + String.valueOf(dcpsDelay));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NPS Emp Contri
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					/*
					 * if (year >= 20 && month >= 8) { if (mstEmployeeModel.getBasicPay() != 0) {
					 * npsEmpContri += (double) (Math.round((mstEmployeeModel.getBasicPay() + da +
					 * svnDA) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 * 
					 * } else { npsEmpContri += (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + da + svnDA) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * 
					 * }
					 */
					if (mstEmployeeModel.getGiscatagory().equals(1)) {
						npsEmpContri = (double) (Math.ceil((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						npsEmpContri = (double) (Math.ceil((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}
					/*
					 * if (mstEmployeeModel.getGiscatagory().equals(1)) { if (year == 2023 && month2
					 * >= 8 || year >= 2024 && month2 >= 1) { npsEmpContri = (double)
					 * (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }else {
					 * npsEmpContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA +
					 * DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 * 
					 * } else { npsEmpContri = (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + svnDA + DaArr) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 */

					logger.info("NPS EMP Contri=" + String.valueOf(npsEmpContri));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmpContri));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Revenue Stamp
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Revenue_Stamp)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					revenueStamp = 1d;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(revenueStamp));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);

				}

				// CM Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						cmFund += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						cmFund += 0;
					}
					logger.info("cmFund deduc Component=" + String.valueOf(cmFund));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(cmFund));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Excess Payment
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						excessPayment += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						excessPayment += 0;
					}
					logger.info("cmFund deduc Component=" + String.valueOf(excessPayment));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayment));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// NPS Employer Contri
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}
					/*if (mstEmployeeModel.getGiscatagory().equals(1)) {

						if (month2 >= 8 && year == 2023 || month >= 1 && year == 2024) {

							Double DaArr1 = 0d;
							Double DaArr2 = 0d;
							Double DaArrtenpersent = 0d;
							Double DaArrforteenpersent = 0d;
							Double totalDaArr = 0d;
							if (month2 == 11 && year == 24) {
								DaArr1 = DaArr / 4;
								DaArr2 = DaArr - DaArr1;
								DaArrtenpersent = (DaArr1 * 10 / 100);
								DaArrforteenpersent = (DaArr2 * 14 / 100);
								totalDaArr = DaArrtenpersent + DaArrforteenpersent;
								npsEmprContri = (double) (Math
										.round((((mstEmployeeModel.getBasicPay() + svnDA) * 14) / 100) + totalDaArr));
							} else if (month2 == 12 && year == 24) {
								DaArr1 = DaArr / 5;
								DaArr2 = DaArr - DaArr1;
								DaArrtenpersent = (DaArr1 * 10 / 100);
								DaArrforteenpersent = (DaArr2 * 14 / 100);
								totalDaArr = DaArrtenpersent + DaArrforteenpersent;
								npsEmprContri = (double) (Math
										.round((((mstEmployeeModel.getBasicPay() + svnDA) * 14) / 100) + totalDaArr));
							} else {
								npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr)
										* 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
							}
						} else {
							npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}

					} else {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}*/

					logger.info("NPS EMP Contri=" + String.valueOf(npsEmprContri));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprContri));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// DCPS pay Arr recovery
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_PAY_ARR_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsPayArr =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsPayArr =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * dcpsPayArr = 0d; } // paybillGenerationTrnDetails.setDcpsPayArr((double)
				 * Math.round(dcpsPayArr));
				 * logger.info("dcpspayarr deduc Component="+String.valueOf(dcpsPayArr));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) {

						// paybillGenerationTrnDetails.setDcpsPayArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsPayArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						// paybillGenerationTrnDetails
						// .setDcpsPayArr((double) 0);
						dcpsPayArr += 0;
					}
					logger.info("dcpspayarr deduc Component=" + String.valueOf(dcpsPayArr));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// DCPS regular recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
						dcpsReg = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
						dcpsReg = (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						dcpsReg = 0d;
					}

					// paybillGenerationTrnDetails.setDcpsRegularRecovery((double)
					// Math.round(dcpsReg));
					logger.info("dcpsRegrec deduc Component=" + String.valueOf(dcpsReg));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsReg));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// DCPS Arr
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				 * logger.info("svnpc1"); dcpsArr = (double)
				 * (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
				 * logger.info("svnpc2"); dcpsArr = (double)
				 * (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * logger.info("svnpc3"); dcpsArr = 0d; }
				 * 
				 * logger.info("dcpsArr="+dcpsArr);
				 * logger.info("mstEmployeeModel.getBasicPay()="+mstEmployeeModel.getBasicPay())
				 * ; logger.info("svnDA="+svnDA); //
				 * paybillGenerationTrnDetails.setDcpsArr((double) Math.round(dcpsArr));
				 * logger.info("dcpsarr deduc Component="+String.valueOf(dcpsArr));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE) {

						// paybillGenerationTrnDetails.setDcpsArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						// paybillGenerationTrnDetails
						// .setDcpsArr((double) 0);
						dcpsArr += 0;
					}
					logger.info("dcpsarr deduc Component=" + String.valueOf(dcpsArr));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Adjustable DCPS Employer Contribution//
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR)
						&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																														// contribution
				{
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					adjust_dcps_empr = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 14
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// paybillGenerationTrnDetails
					// .setAdjustDcpsEmployer((double) Math.round(adjust_dcps_empr + dcpsDa +
					// dcpsDelay + dcpsPayArr + dcpsArr));
					logger.info("adjust dcps empr deduc Component=" + String.valueOf(
							(double) Math.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr)));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(
							(double) Math.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr)));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Start GIS Component
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					logger.info("Gis mmmm### Component=");
					gisAmount = allEdpList.get(i).getGisAmount();
					if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_A)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));

					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_B)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_BNGZ)
							&& gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_C)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_D)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// End GIS Component

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_ABC)) {

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) {

						// paybillGenerationTrnDetails.setGpfGrpABC((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfGrpABC += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC));
						logger.info("gpf grp abc deduc Component=" + String.valueOf(gpfGrpABC));

					} else {

						gpfGrpABC = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfGrpABC((double) Math.round(gpfGrpABC));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC));
						logger.info("gpf grp abc deduc Component=" + String.valueOf(gpfGrpABC));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
					// GPF_GRP_D
				} else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_D)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) {
						// paybillGenerationTrnDetails.setGpfGrpD((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfGrpD += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpD));
						logger.info("GPF_GRP_D deduc Component=" + String.valueOf(gpfGrpD));
					} else {
						gpfGrpD = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfGrpD((double) Math.round(gpfGrpD));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfGrpD)));
						logger.info("GPF_GRP_D deduc Component=" + String.valueOf(gpfGrpD));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_ABC_ARR
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) {

						// paybillGenerationTrnDetails.setGpfAbcArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						GpfAbcArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(GpfAbcArr)));
						logger.info("GPF_ABC_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(GpfAbcArr))));
					} else {
						GpfAbcArr = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfAbcArr((double) Math.round(GpfAbcArr));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(GpfAbcArr)));
						logger.info("GPF_ABC_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(GpfAbcArr))));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Group_Acc_policy
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) {

						// paybillGenerationTrnDetails.setGroupAccPolicy((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						groupAccPolicy += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(groupAccPolicy));
						logger.info("GRP_ACC_POLICY deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						groupAccPolicy = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGroupAccPolicy((double)
						// Math.round(groupAccPolicy));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(groupAccPolicy)));
						logger.info("GRP_ACC_POLICY deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(groupAccPolicy))));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_D_ARR
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) {
						// paybillGenerationTrnDetails.setGpfDArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfDArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfDArr));
						logger.info("GPF_D_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(gpfDArr))));

					} else {
						gpfDArr = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						// paybillGenerationTrnDetails.setGpfDArr((double) Math.round(gpfDArr));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfDArr)));
						logger.info("GPF_D_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(gpfDArr))));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Contributory Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE) {

						/*
						 * paybillGenerationTrnDetails.setContriProvFund((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * contriProvFund+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())
						 * );
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CONTRI_PROV_FUND deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						// paybillGenerationTrnDetails
						// .setContriProvFund((double) 0);
						// contriProvFund+=0;
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				/// PT Arr

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails.setPtArr((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * ptArr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COMPONENT_PT_ARR deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));

					} else {
						/*
						 * paybillGenerationTrnDetails .setPtArr((double) 0); ptArr+=0;
						 */

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Other Deduction
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE) {

						/*
						 * paybillGenerationTrnDetails.setOtherDeduct((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * otherDeduc+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COMPONENT_OHTER deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDeduct((double) 0); otherDeduc+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GIS ZP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE) {

						/*
						 * paybillGenerationTrnDetails.setGisZp((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * GisZp+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("GIS_ZP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setGisZp((double) 0); GisZp+=0;
						 */

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// House Rent Recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE) {

						/*
						 * paybillGenerationTrnDetails .setHrr((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * hrr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("HRR deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setHrr((double) 0); hrr+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Other Recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

						/*
						 * paybillGenerationTrnDetails.setOtherRec((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * otherRec+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("OTHER_REC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherRec((double) 0); otherRec+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Income Tax
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE) {

						/*
						 * paybillGenerationTrnDetails.setIt((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * it+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("INCOME_TAX deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setIt((double) 0); it+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Service Charge
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE) {

						/*
						 * paybillGenerationTrnDetails.setServCharge((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * ServCharge+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("SERVICE_CHARGE deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setServCharge((double) 0); ServCharge+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Subscription
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// HBA
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// FA
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)) {
				 * 
				 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
				 * mstEmployeeService.findFADetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); if
				 * (lnaFAEmployeeRequestMstEntity != null &&
				 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
				 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount();
				 * 
				 * }
				 * 
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double)
				 * Math.round(FA))); } else {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				/*
				 * // COMP_ADV else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_COMP_ADV)) { LNACAEmployeeRequestMstEntity
				 * lnaCAEmployeeRequestMstEntity = mstEmployeeService.findCADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * 
				 * if (lnaCAEmployeeRequestMstEntity != null &&
				 * lnaCAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaCAEmployeeRequestMstEntity.getInstallmentAmount() != null) { CA =
				 * lnaCAEmployeeRequestMstEntity.getInstallmentAmount(); //
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double)
				 * Math.round(CA))); } else {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// GPF Advance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFAdvance)) {
					LoanEmployeeDtlsEntity loandetails = mstEmployeeService.findGPFADetails(
							mstEmployeeModel.getSevaarthId(),
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code);

					/*
					 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
					 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
					 */
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					if (loandetails != null && loandetails.getLoanactivateflag() == 1) {
						if (loandetails.getLoanprinemiamt() != null) {
							gpfAdvance = loandetails.getLoanprinemiamt().doubleValue();
							// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
						}

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfAdvance)));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// LIC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// OTHER_REC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Or_Bank_Loan
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// BLWF
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NDCPS_Subscription
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Special_Arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_DA_Sub
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// ROP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) {

						/*
						 * paybillGenerationTrnDetails.setOtherDed((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * othrded+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDed((double) 0); othrded+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Pay_Fix_Diff
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code) {

						/*
						 * paybillGenerationTrnDetails.setOtherDed((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * othrded+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDed((double) 0); othrded+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NPS
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// BEGIS
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BEGIS))
				 * { BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * OtherAllowanceEntity otherAllowanceEntity = null; if
				 * (mstEmployeeModel.getGiscatagory() != null) { if
				 * (mstEmployeeModel.getGiscatagory().equals(1) ||
				 * mstEmployeeModel.getGiscatagory().equals(2)) { otherAllowanceEntity =
				 * mstEmployeeService.findBEGISAmt(mstEmployeeModel.getGiscatagory(),
				 * mstEmployeeModel.getBegisCatg()); } else { otherAllowanceEntity =
				 * mstEmployeeService .findBEGISAmtwith(mstEmployeeModel.getGiscatagory()); } }
				 * if (otherAllowanceEntity != null) {
				 * 
				 * brokenPeriodModel
				 * .setDeptalldetValue(String.valueOf(Math.round(otherAllowanceEntity.getAmount(
				 * )))); } else { brokenPeriodModel.setDeptalldetValue("0"); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				/// House Advance
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT)) {
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * LNAHBAEmployeeRequestMstEntity lnaHBAEmployeeRequestMstEntity =
				 * mstEmployeeService .findHBADetails(mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_INT_AMT_Code);
				 * 
				 * 
				 * if (lnaHBAEmployeeRequestMstEntity != null &&
				 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { //HouseAdv
				 * = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth(); HouseAdvInstAmt =
				 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
				 * 
				 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancHbaInst =
				 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
				 * hbai.toString() + "/" + sancHbaInst.toString();
				 * 
				 * } brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdvInstAmt)); //
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(hbaHouseInst));
				 * 
				 * 
				 * } else { HouseAdvInstAmt=0d;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdvInstAmt)); //
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(hbaHouseInst)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */else {
					allEdpList.get(i).setDeptalldetValue("0");
					dedRuleList.add(allEdpList.get(i));
					// dedRuleList.add("0");
				}

				// }
			}

			// type 3 and 4 ###########################################
			else if (allEdpList.get(i).getType() == 3) {
				// Credit Soc
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CREDIT_SOC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// LIC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("LIC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Recurring Dep
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Cop_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COPBANK deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Other Deduction
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("OTHER_DEDUCTION deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// alliedSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// mantralayaSoci
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// chiplunSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// ulhasnagarSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// engrSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Public_Health_Works
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sindhudurg_Oras
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_Society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Manahar_bhai_Meh_Jal
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Akola_Pari_Abhiyani
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// ZP_Karmchari_Pat
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Vidharbha_Gramin_Kokan_Bn
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Chanda_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalseva_Soc_Nag
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhandara_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GDCC_BANK
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Gondia_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nagpur_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Allahabad_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhan_Dist_Cent_Cop_bnk
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bank_of_Barora
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Court_Computation
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_GS_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_Handicap_Soci
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Dhule_Nandurbar_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Parisar_Abhi_Soc_Nashik
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sarw_Aroy_Ban_Soci_Dhule
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jaldhara_Soc_CL3
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Panipurvtha_Soc_Cl3Or4
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Govt_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sangli_Sal_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nashik_Road_Soc_CL3Or4
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalseva_MAlegaon_Soc_CL3
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nashik_Bank_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Manda_Nashik_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Ujwala_Mahila_Pat_Bhand
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// BC_Quarter
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Flag_Day
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhand_Jil_Abhi_Karm_Pat
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Jalseva_karm_saha_Path
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Nanded
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Aurangabad
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Latur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MLWF_OnlyMJP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Maha_Lab_Welfare_Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc_Latur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// JalBhavan_Society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc_Solapur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// VangaonSociety
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_VangaonSociety_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));

					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// panipuravtha_kolhapur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));

					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// rajashrishahu_govbank_kolhapur
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_panipuravtha_kolhapur_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));

					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Ahmednagar_pari_Abhiseva_Marya
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ahmednagar_pari_Abhiseva_Marya_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_rajashrishahu_govbank_kolhapur_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Beed_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sal_owner_soc_Sangli
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sal_owner_soc_Sangli_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Shaskiy_panipuravtha_soc_satara
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Shaskiy_panipuravtha_soc_satara_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// jalbhavan_soc_sangli
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalbhavan_soc_sangli_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// akola_society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_akola_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// yavatmal_society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_yavatmal_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// nagari_sahakar_path_sansta
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_nagari_sahakar_path_sansta_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// engineering_society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_engineering_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// daryapur_society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_daryapur_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// public_health_society_Code
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_public_health_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// jalpradaya_society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalpradaya_society_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// zilha_pari_karmachari_pantsanstha_buldhana
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_zilha_pari_karmachari_pantsanstha_buldhana_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalna_soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_jalna_Soc_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// amrawati_dist_engg_credit_soc
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_amrawati_dist_engg_credit_soc_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// PUNE_DIST_CENTRAL_COP_BANK
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK_Code);

					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PUNE_DIST_CENTRAL_COP_BANK_Code) {
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// END BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA

				else {
					allEdpList.get(i).setDeptalldetValue("0");
					dedRuleList.add(allEdpList.get(i));
					// dedRuleList.add("0");
				}
			}

			else if (allEdpList.get(i).getType() == 4) {
				// Credit Soc
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE) {

						/*
						 * paybillGenerationTrnDetails.setCreditSoc((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * CreditSoc+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CO_HSG_SOC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setCreditSoc((double) 0); CreditSoc+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				/*
				 * // GPF_ADV_GRP_ABC else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC)) {
				 * 
				 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
				 * mstEmployeeService.findLoanDetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); BigInteger gpfabc =
				 * null; Double gpfd = 0d; if (loanEmployeeDtlsEntity != null &&
				 * allEdpList.get(i) .getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE) {
				 * 
				 * gpfabc = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfd =
				 * gpfabc.doubleValue(); // paybillGenerationTrnDetails.setCoHsgSoc((double) //
				 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeModel.getEmployeeId(),
				 * gpfabc); brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfd));
				 * logger.info("GPF_ADV_GRP_ABC deduc Component=" +
				 * String.valueOf(String.valueOf(gpfd)));
				 * 
				 * } else {
				 * 
				 * paybillGenerationTrnDetails .setLIC((double) 0); lic+=0;
				 * 
				 * brokenPeriodModel.setDeptalldetValue("0");
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */
				// GPF_ADV_GRP_D
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_GPF_ADV_GRP_D)) {
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
				 * 
				 * 
				 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
				 * mstEmployeeService.findLoanDetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); BigInteger gpfadvd =
				 * null; Double gpfadvgrpd = 0d; if (loanEmployeeDtlsEntity != null &&
				 * allEdpList.get(i) .getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) {
				 * 
				 * gpfadvd = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfadvgrpd =
				 * gpfadvd.doubleValue();
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeModel.getEmployeeId(),
				 * gpfadvd); // paybillGenerationTrnDetails.setCoHsgSoc((double) //
				 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfadvgrpd));
				 * logger.info("GPF_ADV_GRP_ABC deduc Component=" +
				 * String.valueOf(String.valueOf(gpfadvgrpd)));
				 * 
				 * } else {
				 * 
				 * paybillGenerationTrnDetails .setLIC((double) 0); lic+=0;
				 * 
				 * brokenPeriodModel.setDeptalldetValue("0");
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

				// House Adv
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_HBA_HOUSE)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNAHBAEmployeeRequestMstEntity
				 * lnaHBAEmployeeRequestMstEntity = mstEmployeeService.findHBADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaHBAEmployeeRequestMstEntity != null &&
				 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { HouseAdv =
				 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdv));
				 * 
				 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancHbaInst =
				 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
				 * hbai.toString() + "/" + sancHbaInst.toString();
				 * 
				 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * } else { HouseAdv = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdv));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(hbaHouseInst));
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

				// Computer Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_COMP_ADV)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNACAEmployeeRequestMstEntity
				 * lnaCAEmployeeRequestMstEntity = mstEmployeeService.findCADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaCAEmployeeRequestMstEntity != null &&
				 * lnaCAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaCAEmployeeRequestMstEntity.getInstallmentAmount() != null) { CA =
				 * lnaCAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(CA)); }
				 * 
				 * } else { CA = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(CA)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				// Festival Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)) {
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
				 * mstEmployeeService.findFADetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaFAEmployeeRequestMstEntity != null &&
				 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
				 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(FA));
				 * 
				 * }
				 * 
				 * } else { FA = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(FA)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				// Vehical Adv
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_OTHER_VEH_ADV)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNAVAEmployeeRequestMstEntity
				 * lnaVAEmployeeRequestMstEntity = mstEmployeeService.findVADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaVAEmployeeRequestMstEntity != null &&
				 * lnaVAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaVAEmployeeRequestMstEntity.getPrinInstallmentAmountMCA() != null) {
				 * vehAdv = lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(vehAdv)); }
				 * 
				 * } else { vehAdv = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(vehAdv));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(otherVehAdvInst)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				/*
				 * // Excess_Pay_Rec else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_Excess_Pay_Rec)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); ExcessPayRecoveryEntity excessPayRecoveryEntity =
				 * mstEmployeeService .findExcPayRec(mstEmployeeModel.getSevaarthId());
				 * 
				 * 
				 * if (excessPayRecoveryEntity != null && excessPayRecoveryEntity.getIsActive()
				 * == '1') { if (excessPayRecoveryEntity.getPrinEmiAmt() != null) { excessPayrec
				 * = excessPayRecoveryEntity.getPrinEmiAmt();
				 * 
				 * Integer excInst = excessPayRecoveryEntity.getLoanPrinInstNo() + 1; Integer
				 * sancExcInst = excessPayRecoveryEntity.getTotalInstNo(); excessPayrecInst =
				 * excInst.toString() + "/" + sancExcInst.toString(); }
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrec));
				 * //brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrecInst));
				 * 
				 * 
				 * } else { excessPayrec=0d;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrec)); //
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrecInst));
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

				// House Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE)) { BrokenPeriodModel
				 * brokenPeriodModel = allEdpList.get(i);
				 * LNAMotorVehicleAdvEmployeeRequestMstEntity
				 * lnaMotorVehicleAdvEmployeeRequestMstEntity = mstEmployeeService
				 * .findmotorCycleAdvDetails(mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MOTORCYCLE_ADVANCE_Code);
				 * 
				 * if (lnaMotorVehicleAdvEmployeeRequestMstEntity != null &&
				 * lnaMotorVehicleAdvEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaMotorVehicleAdvEmployeeRequestMstEntity.getPrinInstallmentAmountMCA() !=
				 * null) { /// HouseAdv = lnaHBAEmployeeRequestMstEntity.getPrinAmtPerMonth();
				 * motorCycleAdvInst =
				 * lnaMotorVehicleAdvEmployeeRequestMstEntity.getPrinInstallmentAmountMCA();
				 * Integer motorInst =
				 * lnaMotorVehicleAdvEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancmotorCycleInst = lnaMotorVehicleAdvEmployeeRequestMstEntity
				 * .getSancPrincipalInstallMCA(); motorCycleInst = motorInst.toString() + "/" +
				 * sancmotorCycleInst.toString();
				 * 
				 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * 
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(motorCycleInst));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(motorCycleAdvInst));
				 * 
				 * 
				 * paybillGenerationTrnDetails.setMotorVehAdvInst(motorCycleInst);
				 * paybillGenerationTrnDetails.setMotorvehicleAdvance(motorCycleAdvInst); ///
				 * paybillGenerationTrnDetails.setHbaHouseIntAmt(HouseAdvInstAmt);;
				 * motorCycleAdv = (double) (Math.round(motorCycleAdvInst));
				 * 
				 * }else { motorCycleAdv = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(motorCycleInst));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(motorCycleAdvInst));
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

			}

			// ########################################################################################

			else {
				// if (allEdpList.get(i).getLOOKUP_ID().equals("2500383")) { //Deduction like
				// fax
				deducOthEdpList.add(allEdpList.get(i));
				allEdpList.get(i).setDeptalldetValue("0");
				dedRuleList.add(allEdpList.get(i));
				// dedRuleList.add("0");
			}
			// if (allEdpList.get(i).getLOOKUP_ID().equals("2500381")) {
			// deducTyEdpList.add(allEdpList.get(i));
			// }
			// }
		}
		// Dynamic Process end

		BrokenPeriodResponseModel bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("allowRuleList");
		bpResponseMode.setData(allowRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("dedRuleList");
		bpResponseMode.setData(dedRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("basicAmt");
		bpResponseMode.setData(mstEmployeeModel.getBasicPay());
		brokenPeriodResponseModel.add(bpResponseMode);
		String month1 = String.valueOf(Integer.parseInt(hmInputParam.get("month").toString().trim()) + 1);

		String monthyear = month1.length() == 1 ? "0" + month1 + "-" + hmInputParam.get("year").toString().trim()
				: month1 + "-" + hmInputParam.get("year").toString().trim();

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("status");
		// bpResponseMode.setData(brokenPeriodRepo.CheckBrkPrdMonthExitOrNot(monthyear,
		// sevaarthid));

		bpResponseMode.setData(brokenPeriodRepo.CheckBrkPrdMonthExitOrNot(monthyear, sevaarthid, fromDate, toDate));

		brokenPeriodResponseModel.add(bpResponseMode);

		return brokenPeriodResponseModel;
	}

	/*
	 * @Override public List<BrokenPeriodResponseModel>
	 * calculateEmployeeSalary(String sevaarthid,List<BrokenPeriodResponseModel>
	 * brokenPeriodResponseModel,HashMap hmInputParam) { double hra = 0; double pt =
	 * 0; double sevenpcda = 0; Integer basic=0; double da=0; double
	 * dcpsempcontribution=0; double adjdcpsempcontr=0; double dcpsregrecovery=0;
	 * double daonta=0;
	 * 
	 * double grossAmt = 0; double netAmt = 0; // double da = 0; // double hra = 0;
	 * // double pt = 0; double dcps_empr = 0; double dcps_empr1 = 0; double
	 * grossAdjust = 0; double dedAdjust = 0; double adjust_dcps_empr = 0; double
	 * adjust_dcps_empr1 = 0; double dcpsDelay = 0; double dcpsDa = 0; double
	 * dcpsPayArr = 0; double dcpsArr = 0; double dcpsReg = 0; double gisAmount = 0;
	 * double gradePay = 0; double gpfGrpD = 0; double gpfGrpABC = 0; double ta = 0;
	 * double basicAmount=0;
	 * 
	 * MstEmployeeModel mstEmployeeModel = new MstEmployeeModel(); List<String>
	 * lstResult = new ArrayList<String>(); mstEmployeeModel =
	 * brokenPeriodRepo.getEmployeeinfo(sevaarthid); //
	 * logger.info("Employee Name="+mstEmployeeModel.getEmployeeFullNameEn()); //
	 * logger.info("Basic Pay="+mstEmployeeModel.getBasicPay());
	 * basic=mstEmployeeModel.getBasicPay(); //Total number of days and
	 * Fractionofdays int
	 * year=Integer.parseInt(hmInputParam.get("year").toString()); int
	 * month=Integer.parseInt(hmInputParam.get("month").toString()); Calendar cal2 =
	 * Calendar.getInstance(); cal2.set(Calendar.YEAR, year); //
	 * cal2.set(Calendar.MONTH, month-1); cal2.set(Calendar.MONTH, month); //
	 * java.util.Date finYrDate = cal2.getTime(); int totalNoOfDays =
	 * cal2.getActualMaximum(Calendar.DAY_OF_MONTH); int noOfDays =
	 * Integer.parseInt(hmInputParam.get("noOfDays").toString()); basic=(basic *
	 * noOfDays) / totalNoOfDays ; mstEmployeeModel.setBasicPay(basic);
	 * logger.info("basic Day wise= "+basic); logger.info("month="+month);
	 * logger.info("year="+year); logger.info("totalNoOfDays="+totalNoOfDays);
	 * logger.info("noOfDays="+noOfDays);
	 * logger.info("Paycommission id="+mstEmployeeModel.getPayCommissionCode());
	 * 
	 * //Broken Period Pay start List<BrokenPeriodModel> allowEdpList = new
	 * ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData();
	 * List<BrokenPeriodModel> deducAgEdpList = new
	 * ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData();
	 * List<BrokenPeriodModel> deducTyEdpList = new
	 * ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData();
	 * List<BrokenPeriodModel> deducOthEdpList = new
	 * ArrayList<BrokenPeriodModel>();// changes for other (nps) //Broken Period Pay
	 * End
	 * 
	 * List allowRuleList=new ArrayList(); List dedRuleList=new ArrayList();
	 * 
	 * logger.info("Sevaarth Id="+sevaarthid); //List<Object[]>
	 * fetchAllowDeducName(String sevaarthid) String cityClass =
	 * mstEmployeeRepo.getCityClassAgainstDDO(mstEmployeeModel.getDdoCode().toString
	 * ());
	 * 
	 * int payCommission =
	 * mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
	 * logger.info(" payCommission "+ payCommission+ "cityClass "+cityClass);
	 * 
	 * basicAmount=mstEmployeeModel.getBasicPay();
	 * 
	 * List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(sevaarthid);
	 * return displayOuterReportRepo.getAllDataForOuternew(ddocode);
	 * 
	 * List<BrokenPeriodModel> lstObj = new ArrayList<>(); if (!lstprop.isEmpty()) {
	 * for (Object[] objLst : lstprop) { BrokenPeriodModel obj = new
	 * BrokenPeriodModel();
	 * obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
	 * obj.setType(StringHelperUtils.isNullInt(objLst[1]));
	 * obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
	 * obj.setGroupNm(StringHelperUtils.isNullString(objLst[3]));
	 * obj.setGisAmount(StringHelperUtils.isNullDouble(objLst[4]));
	 * //obj.setTempvalue(StringHelperUtils.isNullString(0);
	 * //obj.setTempempty(StringHelperUtils.isNullString(''); //
	 * logger.info("objLst[0]="+objLst[0]+" &&  objLst[1]="+objLst[1]);
	 * lstObj.add(obj); } } //Dynamic process start List<BrokenPeriodModel>
	 * allEdpList = lstObj; // List<DeptEligibilityForAllowAndDeductEntity>
	 * dptallDeduc = deptEligibilityForAllowAndDeductService.
	 * findDeptEligibilityForAllowAndDeductList(); for (int i = 0; i <
	 * allEdpList.size(); i++) { // if (allEdpList.get(i).getType() != null) {
	 * 
	 * logger.info("allEdpList.get(i).getDeptalldetNm()="+allEdpList.get(i).
	 * getDeptalldetNm());
	 * logger.info("COMMONCODE_COMPONENT_GRP_ACC_POLICY="+CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY);
	 * logger.info("allEdpList.get(i).getDeptalldetNm()="+allEdpList.get(i).
	 * getDeptalldetNm());
	 * logger.info("allEdpList.get(i).getDeptallowdeducid()="+allEdpList.get(i).
	 * getDeptallowdeducid()); String name=allEdpList.get(i).getDeptalldetNm();
	 * 
	 * String temp=name;
	 * 
	 * if (allEdpList.get(i).getType() == 1) { // allowance
	 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
	 * .COMMONCODE_COMPONENT_SVN_DA))) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start : 7 pc Calculation--> 17% of
	 * basic if(payCommission ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { sevenpcda=
	 * (mstEmployeeModel.getBasicPay() * 17) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; } //
	 * logger.info("sevenpcDA "+sevenpcda); // sevenpcda=(sevenpcda * noOfDays) /
	 * totalNoOfDays ; // logger.info("sevenpcDA Day wise "+sevenpcda); //End : 7 pc
	 * Calculation
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf((sevenpcda)));
	 * allowEdpList.add(brokenPeriodModel); allowRuleList.add(brokenPeriodModel); }
	 * 
	 * 
	 * 
	 * else
	 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
	 * .COMMONCODE_COMPONENT_DCPS_EMPLOYER))) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start: DCPS Employer Contribution -->
	 * 14 % of basic+DA dcpsempcontribution=
	 * ((mstEmployeeModel.getBasicPay()+sevenpcda) * 14) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; //
	 * dcpsempcontribution=(dcpsempcontribution * noOfDays) / totalNoOfDays ; //End:
	 * DCPS Employer Contribution
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsempcontribution));
	 * allowEdpList.add(brokenPeriodModel); //
	 * allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
	 * allowRuleList.add(brokenPeriodModel); }
	 * 
	 * else
	 * if(allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_COMPONENT_HRA)) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start:HRA Calculation --> for 7 pc 8 %
	 * of basic for Z class / 16% of basic for Y class / 24% of basic for X class (
	 * for 6 pc --> 10% 20% 30%) if(payCommission ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
	 * 
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_X)) hra = (mstEmployeeModel.getBasicPay() * 24) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; else
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_Y)) hra = (mstEmployeeModel.getBasicPay() * 16) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; else
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_Z)) hra = (mstEmployeeModel.getBasicPay() * 8) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; }
	 * 
	 * else if(payCommission ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC ) {
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_X)) hra = (mstEmployeeModel.getBasicPay() * 30) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; else
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_Y)) hra = (mstEmployeeModel.getBasicPay() * 20) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; else
	 * if(cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_CITY_CLASS_Z)) hra = (mstEmployeeModel.getBasicPay() * 10) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; } //
	 * logger.info(" HRA  = "+ hra); // hra=(hra * noOfDays) / totalNoOfDays ;
	 * //End: HRA Calculation
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
	 * allowEdpList.add(brokenPeriodModel); //
	 * allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
	 * allowRuleList.add(brokenPeriodModel); }
	 * 
	 * else
	 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
	 * .COMMONCODE_COMPONENT_DA_on_TA))) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start: DA On TA --> 17% of basic
	 * daonta= (mstEmployeeModel.getBasicPay() * 17) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; // daonta=(daonta *
	 * noOfDays) / totalNoOfDays ; //End: DA On TA
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(daonta));
	 * allowEdpList.add(brokenPeriodModel); //
	 * allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
	 * allowRuleList.add(brokenPeriodModel); }
	 * 
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW)) {
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE);
	 * BrokenPeriodModel brokenPeriodModel=null; if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) {
	 * logger.info(" Inside iff for COMMONCODE_COMPONENT_TRIBAL_ALLOW ");
	 * brokenPeriodModel=allEdpList.get(i);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
	 * logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else {
	 * brokenPeriodModel=allEdpList.get(i); double tribalAllow =
	 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** " +
	 * tribalAllow);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(tribalAllow)); }
	 * 
	 * allowEdpList.add(brokenPeriodModel); //
	 * allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
	 * allowRuleList.add(brokenPeriodModel); }
	 * 
	 * else if (allEdpList.get(i).getDeptalldetNm()
	 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_COMPONENT_TRANS_ALLOW_ARR)) { EmployeeAllowDeducComponentAmtEntity
	 * employeeAllowDeducComponentAmtEntity = mstEmployeeService
	 * .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE);
	 * BrokenPeriodModel brokenPeriodModel=null; if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) {
	 * logger.info(" Inside iff for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR ");
	 * brokenPeriodModel=allEdpList.get(i);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt())); logger.info(
	 * " for setGpfGrpD********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else {
	 * brokenPeriodModel=allEdpList.get(i); double transAllowArr =
	 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR********** " +
	 * transAllowArr);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(transAllowArr)); }
	 * allowEdpList.add(brokenPeriodModel); allowRuleList.add(brokenPeriodModel); }
	 * 
	 * else { allEdpList.get(i).setDeptalldetValue("0");
	 * allowRuleList.add(allEdpList.get(i)); }
	 * 
	 * 
	 * 
	 * }else if (allEdpList.get(i).getType() == 2) {
	 * 
	 * if(allEdpList.get(i).getDeptallowdeducid()==36||allEdpList.get(i).
	 * getDeptallowdeducid()==37||allEdpList.get(i).getDeptallowdeducid()==38) {
	 * 
	 * 
	 * deducAgEdpList.add(allEdpList.get(i));
	 * allEdpList.get(i).setDeptalldetValue("0");
	 * dedRuleList.add(allEdpList.get(i));
	 * 
	 * 
	 * }//Deductions Adj. By CAFO/Supri./Admin. else {
	 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
	 * .COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR))) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start:Adjustable DCPS Employer
	 * Contribution --> 14 % of basic+DA adjdcpsempcontr=
	 * ((mstEmployeeModel.getBasicPay()+sevenpcda) * 14) /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; //
	 * adjdcpsempcontr=(adjdcpsempcontr * noOfDays) / totalNoOfDays ; //End:HRA
	 * Adjustable DCPS Employer Contribution
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(adjdcpsempcontr));
	 * deducTyEdpList.add(brokenPeriodModel); //Adjust by Treasury //
	 * dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
	 * dedRuleList.add(brokenPeriodModel); } else if
	 * (allEdpList.get(i).getDeptalldetNm()
	 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
	 * COMMONCODE_COMPONENT_DCPS_DELAY_RECOVERY) &&
	 * allEdpList.get(i).getDeptalldetNm() !=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); dcpsDelay =
	 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_PAY_ARR_REC) &&
	 * allEdpList.get(i).getDeptalldetNm() !=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); dcpsPayArr =
	 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_REG_REC) &&
	 * allEdpList.get(i).getDeptalldetNm() !=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); dcpsReg =
	 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsReg));
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR) &&
	 * allEdpList.get(i).getDeptalldetNm() !=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); dcpsArr =
	 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * 
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_ABC)) {
	 * 
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE);
	 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) {
	 * logger.info(" Inside iff for employeeAllowDeducComponentAmtEntity != null");
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
	 * logger.info(" for gpfGrpABC********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else {
	 * logger.info(" Inside else for employeeAllowDeducComponentAmtEntity != null");
	 * gpfGrpABC = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for gpfGrpABC********** " + gpfGrpABC);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC)); }
	 * 
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
	 * 
	 * } else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_D)) {
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE); BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); if (employeeAllowDeducComponentAmtEntity
	 * != null && allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) {
	 * logger.info(" Inside iff for employeeAllowDeducComponentAmtEntity != null");
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt())); logger.info(
	 * " for setGpfGrpD********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { gpfGrpD =
	 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for gpfGrpD********** " + gpfGrpD);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpD)); }
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * 
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR)) {
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE);
	 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) {
	 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GPF_ABC_ARR ");
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
	 * logger.info(" for COMMONCODE_COMPONENT_GPF_ABC_ARR********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double GpfAbcArr
	 * = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for gpfGrpD********** " + gpfGrpD);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(GpfAbcArr)); }
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * //Group_Acc_policy else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY)) {
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE);
	 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) {
	 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE ");
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
	 * logger.info(" for COMMONCODE_COMPONENT_GPF_ABC_ARR********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double
	 * groupAccPolicy = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for gpfGrpD********** " + gpfGrpD);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(groupAccPolicy)); }
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR)) {
	 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
	 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE);
	 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
	 * (employeeAllowDeducComponentAmtEntity != null &&
	 * allEdpList.get(i).getDeptallowdeducid() ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) {
	 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE ");
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
	 * logger.info(" for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE********** " +
	 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double gpfDArr =
	 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
	 * logger.info(" for gpfGrpD********** " + gpfGrpD);
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfDArr)); }
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * else
	 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
	 * .COMMONCODE_COMPONENT_PT))) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i); //Start: profession tax --> PT = 175
	 * when basic in between 4999 to 9999 or PT = 200 when basic above 9999
	 * if(mstEmployeeModel.getBasicPay() <=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4999) pt =
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0; else if
	 * (mstEmployeeModel.getBasicPay() >
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4999 &&
	 * mstEmployeeModel.getBasicPay() <=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_9999) pt =
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175; else if
	 * (mstEmployeeModel.getBasicPay() >
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_9999) { if
	 * (month == 1) pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
	 * else pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200; }
	 * 
	 * else pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
	 * 
	 * // pt=(pt * noOfDays) / totalNoOfDays ; //End: profession tax
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
	 * deducTyEdpList.add(brokenPeriodModel); //Adjust by Treasury //
	 * dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
	 * dedRuleList.add(brokenPeriodModel); } // Start GIS Component else if
	 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS) &&
	 * allEdpList.get(i).getDeptalldetNm() !=
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
	 * brokenPeriodModel=allEdpList.get(i);
	 * gisAmount=allEdpList.get(i).getGisAmount(); if
	 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_A) && gisAmount ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A) {
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
	 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_B) && gisAmount ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B) {
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
	 * (allEdpList.get(i).getGroupNm()
	 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_BNGZ)
	 * && gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ) {
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
	 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_C) && gisAmount ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C) {
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
	 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
	 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_D) && gisAmount ==
	 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D) {
	 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); }
	 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
	 * // End GIS Component else { allEdpList.get(i).setDeptalldetValue("0");
	 * dedRuleList.add(allEdpList.get(i)); // dedRuleList.add("0"); }
	 * 
	 * 
	 * // } }else { // if (allEdpList.get(i).getLOOKUP_ID().equals("2500383")) {
	 * //Deduction like fax deducOthEdpList.add(allEdpList.get(i));
	 * allEdpList.get(i).setDeptalldetValue("0");
	 * dedRuleList.add(allEdpList.get(i)); // dedRuleList.add("0"); } // if
	 * (allEdpList.get(i).getLOOKUP_ID().equals("2500381")) { //
	 * deducTyEdpList.add(allEdpList.get(i)); // } // } } // Dynamic Process end
	 * 
	 * 
	 * BrokenPeriodResponseModel bpResponseMode =new BrokenPeriodResponseModel();
	 * bpResponseMode.setStatus("allowRuleList");
	 * bpResponseMode.setData(allowRuleList);
	 * brokenPeriodResponseModel.add(bpResponseMode);
	 * 
	 * bpResponseMode =new BrokenPeriodResponseModel();
	 * bpResponseMode.setStatus("dedRuleList"); bpResponseMode.setData(dedRuleList);
	 * brokenPeriodResponseModel.add(bpResponseMode);
	 * 
	 * bpResponseMode =new BrokenPeriodResponseModel();
	 * bpResponseMode.setStatus("basicAmt");
	 * bpResponseMode.setData(mstEmployeeModel.getBasicPay());
	 * brokenPeriodResponseModel.add(bpResponseMode); String
	 * month1=String.valueOf(Integer.parseInt(hmInputParam.get("month").toString().
	 * trim())+1); String
	 * monthyear=month1.length()==1?"0"+month1+"-"+hmInputParam.get("year").toString
	 * ().trim():month1+"-"+hmInputParam.get("year").toString().trim();
	 * bpResponseMode =new BrokenPeriodResponseModel();
	 * bpResponseMode.setStatus("status");
	 * bpResponseMode.setData(brokenPeriodRepo.CheckBrkPrdMonthExitOrNot(monthyear,
	 * sevaarthid)); brokenPeriodResponseModel.add(bpResponseMode);
	 * 
	 * 
	 * return brokenPeriodResponseModel; }
	 */

	private double valueOf(Object object) {

		return 0;
	}

	@Override
	public List<BrokenPeriodResponseModel> saveBrokenPeriodPay(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, Map hmInputParam) {

		/*
		 * HashMap hmInputParam=new HashMap(); hmInputParam.put("sevaarthid",
		 * sevaarthid); hmInputParam.put("fromDates", fromDates);
		 * hmInputParam.put("toDates", toDates); hmInputParam.put("empId", empId);
		 * hmInputParam.put("paybillMonth", paybillMonth);
		 * hmInputParam.put("paybillYear", paybillYear); hmInputParam.put("noOfDays",
		 * noOfDays); hmInputParam.put("bacispay", bacispay);
		 * hmInputParam.put("allowancesCodes", allowancesCodes);
		 * hmInputParam.put("allowancesValues", allowancesValues);
		 * hmInputParam.put("deductionCodes", deductionCodes);
		 * hmInputParam.put("deductionValues", deductionValues);
		 * hmInputParam.put("netPays", netPays); hmInputParam.put("reasons", reasons);
		 * hmInputParam.put("remarks", remarks);
		 */
		// ResultObject resObj = new ResultObject(ErrorConstants.SUCCESS, "FAIL");
		Boolean lBlFlag = false;
		Boolean lBlFirstTimeSave = null;
		Long lLongBrknPrdIdForDelete = null;

		try {
			// setSessionInfo(inputMap);
			// BrokenPeriodDAO lObjBrokenPeriodDAO = new
			// BrokenPeriodDAOImpl(MstBrokenPeriodPay.class, serv.getSessionFactory());
			Map inputMap = generateMap(hmInputParam);
			BrokenPeriodEntity[] lArrMstBrokenPeriodPay = (BrokenPeriodEntity[]) inputMap.get("lArrMstBrokenPeriodPay");
			List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodAllow = (List<BrokenPeriodAllowDeducEntity>) inputMap
					.get("lListBrokenPeriodAllows");
			List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodDeduc = (List<BrokenPeriodAllowDeducEntity>) inputMap
					.get("lListBrokenPeriodDeducs");

			Long lLongYear = Long.valueOf(inputMap.get("paybillYear").toString());
			;// Long.valueOf(StringUtility.getParameter("year", request).trim());
			Long lLongMonth = Long.valueOf(inputMap.get("paybillMonth").toString());// Long.valueOf(StringUtility.getParameter("month",
																					// request).trim());
			Long lLongEisEmpId = Long.valueOf(inputMap.get("empId").toString());// Long.valueOf(StringUtility.getParameter("eisEmpId",
																				// request).trim());
			String ddoCode = inputMap.get("ddoCode").toString();
			lBlFirstTimeSave = (!brokenPeriodRepo.checkBrokenPeriodPayExistsOrNot(lLongEisEmpId, lLongYear, lLongMonth,
					ddoCode));
			if (!lBlFirstTimeSave) {
				List<BrokenPeriodEntity> lListBrokenPeriodPayList = brokenPeriodRepo
						.getAddedBrokenPeriodPaysForEmp(lLongEisEmpId, lLongYear, lLongMonth, ddoCode);
				for (Integer lInt = 0; lInt < lListBrokenPeriodPayList.size(); lInt++) {
					lLongBrknPrdIdForDelete = lListBrokenPeriodPayList.get(lInt).getBrokenPeriodId();
					brokenPeriodRepo.deleteAllBrokenPeriodAllowancesForBrknPrdId(lLongBrknPrdIdForDelete);
					brokenPeriodRepo.deleteAllBrokenPeriodDeductionsForBrknPrdId(lLongBrknPrdIdForDelete);
					brokenPeriodRepo.deleteAllBrokenPeriodPaysForPk(lLongBrknPrdIdForDelete);
				}
			}

			// for (Integer lInt = 0; lInt < lArrMstBrokenPeriodPay.length; lInt++)
			// {
			// lObjBrokenPeriodDAO.create(lArrMstBrokenPeriodPay[lInt]);
			// }
			//
			// for (Integer lInt = 0; lInt < lListRltBrokenPeriodAllow.size(); lInt++)
			// {
			// lObjBrokenPeriodDAO.create(lListRltBrokenPeriodAllow.get(lInt));
			//
			// }
			//
			// for (Integer lInt = 0; lInt < lListRltBrokenPeriodDeduc.size(); lInt++)
			// {
			// lObjBrokenPeriodDAO.create(lListRltBrokenPeriodDeduc.get(lInt));
			//
			// }
			/*
			 * logger.info("lArrMstBrokenPeriodPay="+lArrMstBrokenPeriodPay[0]);
			 * logger.info("lListRltBrokenPeriodAllow="+lListRltBrokenPeriodAllow);
			 * logger.info("lListRltBrokenPeriodDeduc="+lListRltBrokenPeriodDeduc);
			 */
			List result = new ArrayList();
			result = brokenPeriodRepo.saveBrokenPeriodPay(lArrMstBrokenPeriodPay, lListRltBrokenPeriodAllow,
					lListRltBrokenPeriodDeduc);
			BrokenPeriodResponseModel brokenPeriodResponseModel2 = new BrokenPeriodResponseModel();
			brokenPeriodResponseModel2.setStatus("status");
			brokenPeriodResponseModel2.setData(result.get(0));
			brokenPeriodResponseModel.add(brokenPeriodResponseModel2);

			lBlFlag = true;

		} catch (Exception ex) {
			// resObj.setResultValue(null);
			// logger.error(" Error is : " + ex, ex);
			// resObj.setThrowable(ex);
			// resObj.setResultCode(ErrorConstants.ERROR);
			// resObj.setViewName("errorPage");
			ex.printStackTrace();
			return brokenPeriodResponseModel;
		}

		// String lSBStatus = getResponseXMLDoc(lBlFlag).toString();
		// String lStrResult = new AjaxXmlBuilder().addItem("ajax_key",
		// lSBStatus).toString();
		//
		// inputMap.put("ajaxKey", lStrResult);
		// resObj.setResultValue(inputMap);
		// resObj.setViewName("ajaxData");
		// return resObj;

		return brokenPeriodResponseModel;
	}

	@Override
	public Map generateMap(Map inputMap) {

		// ResultObject objRes = new ResultObject(ErrorConstants.SUCCESS, "FAIL");
		// HttpServletRequest request = (HttpServletRequest) inputMap.get("requestObj");

		// Long gLngPostId = SessionHelper.getPostId(inputMap);
		// Long gLngUserId = SessionHelper.getUserId(inputMap);
		// Long lLngDbId = SessionHelper.getDbId(inputMap);
		// Long lLngLocId = SessionHelper.getLocationId(inputMap);
		// Long lLngLangId = SessionHelper.getLangId(inputMap);
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate currDt = LocalDate.now();
		Date gDtCurrDt = Date.from(currDt.atStartOfDay(defaultZoneId).toInstant());
		logger.info("gDtCurrDt=" + gDtCurrDt);

		String ddoCode = inputMap.get("ddoCode").toString();

		BrokenPeriodEntity[] lArrMstBrokenPeriodPay = null;
		List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodAllow = new ArrayList<BrokenPeriodAllowDeducEntity>();
		List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodDeduc = new ArrayList<BrokenPeriodAllowDeducEntity>();
		BrokenPeriodEntity lObjMstBrokenPeriodPay = null;
		BrokenPeriodAllowDeducEntity lObjRltBrokenPeriodAllow = null;
		BrokenPeriodAllowDeducEntity lObjRltBrokenPeriodDeduc = null;
		Long lLongMstBrokenPrdId;
		Long lLongRltBrokenPrdAllowId = null;
		Long lLongRltBrokenPrdDeducId = null;
		String[] lStrArrAllowancesCodes = null;
		String[] lStrArrAllowancesValues = null;
		Long[] lLongArrAllowancesCodesFinal = null;
		Long[] lLongArrAllowancesValuesFinal = null;
		String[] lStrArrAllowancesCodesFinal = null;
		String[] lStrArrAllowancesValuesFinal = null;
		String[] lStrArrDeductionsCodes = null;
		String[] lStrArrDeductionsValues = null;
		Long[] lLongArrDeductionsCodesFinal = null;
		Long[] lLongArrDeductionsValuesFinal = null;
		String[] lStrArrDeductionsCodesFinal = null;
		String[] lStrArrDeductionsValuesFinal = null;

		try {

			lArrMstBrokenPeriodPay = generateBrokenPeriodPayVOList(inputMap);

			String lStrAllowancesCodes = inputMap.get("allowancesCodes").toString();// StringUtility.getParameter("allowancesCodes",
																					// request);
			String lStrAllowancesValues = inputMap.get("allowancesValues").toString();// StringUtility.getParameter("allowancesValues",
																						// request);
			lStrArrAllowancesCodes = lStrAllowancesCodes.split("~");
			lStrArrAllowancesValues = lStrAllowancesValues.split("~");

			if (inputMap.get("deductionCodes") != null) {
				try {
					String lStrDeductionCodes = inputMap.get("deductionCodes").toString();// StringUtility.getParameter("deductionCodes",
					// request);
					String lStrDeductionValues = inputMap.get("deductionValues").toString();// StringUtility.getParameter("deductionValues",
					// request);
					lStrArrDeductionsCodes = lStrDeductionCodes.split("~");
					lStrArrDeductionsValues = lStrDeductionValues.split("~");
				} catch (Exception e) {

				}
			}

			for (Integer lInt = 0; lInt < lArrMstBrokenPeriodPay.length; lInt++) {
				lObjMstBrokenPeriodPay = lArrMstBrokenPeriodPay[lInt];
				// lLongMstBrokenPrdId =
				// IFMSCommonServiceImpl.getNextSeqNum("mst_dcps_broken_period_pay",inputMap);
				lLongMstBrokenPrdId = brokenPeriodRepo.getBrokenPeriodNextID();
				lObjMstBrokenPeriodPay.setBrokenPeriodId(lLongMstBrokenPrdId);

				// Generates VO List for Allowances

				lStrArrAllowancesCodesFinal = lStrArrAllowancesCodes[lInt].split(":");
				lStrArrAllowancesValuesFinal = lStrArrAllowancesValues[lInt].split(":");

				lLongArrAllowancesCodesFinal = new Long[lStrArrAllowancesCodesFinal.length];
				lLongArrAllowancesValuesFinal = new Long[lStrArrAllowancesCodesFinal.length];

				for (Integer lIntInner = 0; lIntInner < lStrArrAllowancesCodesFinal.length; lIntInner++) {
					// lLongArrAllowancesCodesFinal[lIntInner] =
					// Long.valueOf(lStrArrAllowancesCodesFinal[lIntInner].trim());
					lLongArrAllowancesCodesFinal[lIntInner] = Long
							.valueOf((lStrArrAllowancesCodesFinal[lIntInner].trim() != null
									&& !(lStrArrAllowancesCodesFinal[lIntInner].trim().equals(""))
											? lStrArrAllowancesCodesFinal[lIntInner].trim()
											: "0"));

				}

				for (Integer lIntInner = 0; lIntInner < lStrArrAllowancesValuesFinal.length; lIntInner++) {
					// lLongArrAllowancesValuesFinal[lIntInner] =
					// Long.valueOf(lStrArrAllowancesValuesFinal[lIntInner].trim());
					lLongArrAllowancesValuesFinal[lIntInner] = Long
							.valueOf((lStrArrAllowancesValuesFinal[lIntInner].trim() != null
									&& !(lStrArrAllowancesValuesFinal[lIntInner].trim().equals(""))
											? lStrArrAllowancesValuesFinal[lIntInner].trim()
											: "0"));
				}

				for (Integer lIntInner = 0; lIntInner < lStrArrAllowancesCodesFinal.length; lIntInner++) {
					lObjRltBrokenPeriodAllow = new BrokenPeriodAllowDeducEntity();
					// lLongRltBrokenPrdAllowId =
					// IFMSCommonServiceImpl.getNextSeqNum("rlt_dcps_broken_period_allow",inputMap);
					// lObjRltBrokenPeriodAllow.setBrokenPeriodAllowId(BigInteger.valueOf(lLongRltBrokenPrdAllowId));
					lObjRltBrokenPeriodAllow.setBrokenPeriodEntity(lObjMstBrokenPeriodPay);
					lObjRltBrokenPeriodAllow.setBrokenPeriodId(lObjMstBrokenPeriodPay.getBrokenPeriodId());
					lObjRltBrokenPeriodAllow
							.setAllowDeducCode(Integer.valueOf(lLongArrAllowancesCodesFinal[lIntInner].intValue()));
					lObjRltBrokenPeriodAllow
							.setAllowDeducAmt(lLongArrAllowancesValuesFinal[lIntInner].doubleValue());
					lObjRltBrokenPeriodAllow.setIstype(1);
					// lObjRltBrokenPeriodAllow.setCreatedUserId(gLngUserId);
					lObjRltBrokenPeriodAllow.setCreatedDate(gDtCurrDt);

					lListRltBrokenPeriodAllow.add(lObjRltBrokenPeriodAllow);
				}

				// Generates VO List for Deductions

				if (inputMap.get("deductionCodes") != null) {
					lStrArrDeductionsCodesFinal = lStrArrDeductionsCodes[lInt].split(":");
					lStrArrDeductionsValuesFinal = lStrArrDeductionsValues[lInt].split(":");

					lLongArrDeductionsCodesFinal = new Long[lStrArrDeductionsCodesFinal.length];
					lLongArrDeductionsValuesFinal = new Long[lStrArrDeductionsCodesFinal.length];

					for (Integer lIntInner = 0; lIntInner < lStrArrDeductionsCodesFinal.length; lIntInner++) {
						// lLongArrDeductionsCodesFinal[lIntInner] =
						// Long.valueOf(lStrArrDeductionsCodesFinal[lIntInner].trim());
						lLongArrDeductionsCodesFinal[lIntInner] = Long
								.valueOf((lStrArrDeductionsCodesFinal[lIntInner].trim() != null
										&& !(lStrArrDeductionsCodesFinal[lIntInner].trim().equals(""))
												? lStrArrDeductionsCodesFinal[lIntInner].trim()
												: "0"));
					}

					for (Integer lIntInner = 0; lIntInner < lStrArrDeductionsValuesFinal.length; lIntInner++) {
						// lLongArrDeductionsValuesFinal[lIntInner] =
						// Long.valueOf(lStrArrDeductionsValuesFinal[lIntInner].trim());
						String[] amt = lStrArrDeductionsValuesFinal[lIntInner].trim().toString().split("\\.");
						lLongArrDeductionsValuesFinal[lIntInner] = Long
								.valueOf((amt[0].trim() != null && !(amt[0].trim().equals("")) ? amt[0].trim() : "0"));
						// lLongArrDeductionsValuesFinal[lIntInner]
						// =Long.valueOf((lStrArrDeductionsValuesFinal[lIntInner].trim()!=null &&
						// !(lStrArrDeductionsValuesFinal[lIntInner].trim().equals(""))?
						// lStrArrDeductionsValuesFinal[lIntInner].trim():"0"));
					}

					for (Integer lIntInner = 0; lIntInner < lStrArrDeductionsCodesFinal.length; lIntInner++) {
						lObjRltBrokenPeriodDeduc = new BrokenPeriodAllowDeducEntity();
						// lLongRltBrokenPrdDeducId =
						// IFMSCommonServiceImpl.getNextSeqNum("rlt_dcps_broken_period_deduc",inputMap);
						// lObjRltBrokenPeriodDeduc.setBrokenPeriodDeducId(lLongRltBrokenPrdDeducId);
						lObjRltBrokenPeriodDeduc.setBrokenPeriodEntity(lObjMstBrokenPeriodPay);
						lObjRltBrokenPeriodDeduc.setBrokenPeriodId(lObjMstBrokenPeriodPay.getBrokenPeriodId());
						lObjRltBrokenPeriodDeduc
								.setAllowDeducCode(Integer.valueOf(lLongArrDeductionsCodesFinal[lIntInner].intValue()));
						lObjRltBrokenPeriodDeduc
								.setAllowDeducAmt(lLongArrDeductionsValuesFinal[lIntInner].doubleValue());
						lObjRltBrokenPeriodDeduc.setIstype(2);
						// lObjRltBrokenPeriodDeduc.setCreatedUserId(gLngUserId);
						lObjRltBrokenPeriodDeduc.setCreatedDate(gDtCurrDt);

						lListRltBrokenPeriodDeduc.add(lObjRltBrokenPeriodDeduc);
					}

				}
			}

			inputMap.put("lArrMstBrokenPeriodPay", lArrMstBrokenPeriodPay);
			inputMap.put("lListBrokenPeriodAllows", lListRltBrokenPeriodAllow);
			inputMap.put("lListBrokenPeriodDeducs", lListRltBrokenPeriodDeduc);

			// objRes.setResultValue(inputMap);

		} catch (Exception ex) {
			// objRes.setResultValue(null);
			// objRes.setThrowable(ex);
			// objRes.setResultCode(ErrorConstants.ERROR);
			// objRes.setViewName("errorPage");
			ex.printStackTrace();
		}
		return inputMap;
	}

	@Override
	public BrokenPeriodEntity[] generateBrokenPeriodPayVOList(Map inputMap) throws Exception {

		// HttpServletRequest request = (HttpServletRequest) inputMap
		// .get("requestObj");

		// Long gLngPostId = SessionHelper.getPostId(inputMap);
		// Long gLngUserId = SessionHelper.getUserId(inputMap);
		// Long lLngDbId = SessionHelper.getDbId(inputMap);
		// Long lLngLocId = SessionHelper.getLocationId(inputMap);
		// Long lLngLangId = SessionHelper.getLangId(inputMap);
		// ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
		// default time zone
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate currDt = LocalDate.now();
		Date gDtCurrDt = Date.from(currDt.atStartOfDay(defaultZoneId).toInstant());
		logger.info("gDtCurrDt=" + gDtCurrDt);
		Long lLongYear = null;
		Long lLongMonth = null;
		Long lLongEisEmpId = null;

		String ddoCode = inputMap.get("ddoCode").toString();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		lLongYear = Long.valueOf(inputMap.get("paybillYear").toString()); // Long.valueOf(StringUtility.getParameter("year",
																			// request).trim());
		lLongMonth = Long.valueOf(inputMap.get("paybillMonth").toString());// Long.valueOf(StringUtility.getParameter("month",
																			// request).trim());
		lLongEisEmpId = Long.valueOf(inputMap.get("empId").toString()); // Long.valueOf(StringUtility.getParameter("eisEmpId",

		String lStrFromDates = inputMap.get("fromDates").toString(); // StringUtility.getParameter("fromDates",
																		// request);
		String[] lStrArrFromDate = lStrFromDates.split("~");
		// DateFormat parseFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date[] lDateArrFromDate = new Date[lStrArrFromDate.length];
		for (Integer lInt = 0; lInt < lStrArrFromDate.length; lInt++) {
			if (lStrArrFromDate[lInt] != null && !"".equals(lStrArrFromDate[lInt].trim())) {
				lDateArrFromDate[lInt] = sdf.parse(lStrArrFromDate[lInt].trim());
			}
		}

		String lStrToDates = inputMap.get("toDates").toString(); // StringUtility.getParameter("toDates", request);
		String[] lStrArrToDate = lStrToDates.split("~");
		Date[] lDateArrToDate = new Date[lStrArrToDate.length];
		for (Integer lInt = 0; lInt < lStrArrToDate.length; lInt++) {
			if (lStrArrToDate[lInt] != null && !"".equals(lStrArrToDate[lInt].trim())) {
				lDateArrToDate[lInt] = sdf.parse(lStrArrToDate[lInt].trim());
			}
		}

		String lStrNoOfDays = inputMap.get("noOfDays").toString(); // StringUtility.getParameter("noOfDays", request);
		String[] lStrArrNoOfDays = lStrNoOfDays.split("~");
		Long[] lLongArrNoOfDays = new Long[lStrArrNoOfDays.length];
		for (Integer lInt = 0; lInt < lStrArrNoOfDays.length; lInt++) {
			if (lStrArrNoOfDays[lInt] != null && !"".equals(lStrArrNoOfDays[lInt].trim())) {
				lLongArrNoOfDays[lInt] = Long.valueOf(lStrArrNoOfDays[lInt].trim());
			}
		}

		String lStrBasicPays = inputMap.get("bacispay").toString(); // StringUtility.getParameter("basicPays", request);
		String[] lStrArrBasicPays = lStrBasicPays.split("~");
		Long[] lLongArrBasicPays = new Long[lStrArrBasicPays.length];
		for (Integer lInt = 0; lInt < lStrArrBasicPays.length; lInt++) {
			if (lStrArrBasicPays[lInt] != null && !"".equals(lStrArrBasicPays[lInt].trim())) {
				lLongArrBasicPays[lInt] = Long.valueOf(lStrArrBasicPays[lInt].trim());
			}
		}
		String sevaarthid = inputMap.get("sevaarthid").toString();
		// String ddoCode = inputMap.get("ddoCode").toString();
		String lStrNetPays = inputMap.get("netPays").toString(); // StringUtility.getParameter("netPays", request);
		String[] lStrArrNetPays = lStrNetPays.split("~");
		Long[] lLongArrNetPays = new Long[lStrArrNetPays.length];
		for (Integer lInt = 0; lInt < lStrArrNetPays.length; lInt++) {
			if (lStrArrNetPays[lInt] != null && !"".equals(lStrArrNetPays[lInt].trim())) {
				lLongArrNetPays[lInt] = Long.valueOf(lStrArrNetPays[lInt].trim());
			}
		}

		String lStrReasons = inputMap.get("reasons").toString(); // StringUtility.getParameter("reasons", request);
		String[] lStrArrReasons = lStrReasons.split("~");

		String lStrRemarks = inputMap.get("remarks").toString();// StringUtility.getParameter("remarks",
																// request).trim();
		if (lStrRemarks.contains("~~")) {
			lStrRemarks = lStrRemarks.replace("~~", "~###~"); // When old value is null but new value is there.
		}

		String[] lStrArrRemarks = lStrRemarks.split("~");

		String lstStrbasicForCalculation = inputMap.get("basicForCalculation").toString();// StringUtility.getParameter("remarks",

		String[] lstArrbasicForCalculation = lstStrbasicForCalculation.split("~");

		BrokenPeriodEntity[] lArrMstBrokenPeriodPay = new BrokenPeriodEntity[lStrArrFromDate.length];

		for (Integer lInt = 0; lInt < lStrArrFromDate.length; lInt++) {

			BrokenPeriodEntity lObjMstBrokenPeriodPay = new BrokenPeriodEntity();
			//
			lObjMstBrokenPeriodPay.setEmpId(Integer.valueOf(lLongEisEmpId.intValue()));
			lObjMstBrokenPeriodPay.setSevaarthid(sevaarthid);
			lObjMstBrokenPeriodPay.setMonthId(Integer.valueOf(lLongMonth.intValue()));
			lObjMstBrokenPeriodPay.setYearId(Integer.valueOf(lLongYear.intValue()));
			lObjMstBrokenPeriodPay.setFromDate(sdf.parse(lStrArrFromDate[lInt].trim()));
			lObjMstBrokenPeriodPay.setToDate(sdf.parse(lStrArrToDate[lInt].trim()));
			lObjMstBrokenPeriodPay.setNoOfDays(Integer.valueOf(lLongArrNoOfDays[lInt].intValue()));

			lObjMstBrokenPeriodPay.setBasicPay(Integer.valueOf(lLongArrBasicPays[lInt].intValue()));

			lObjMstBrokenPeriodPay.setNetPay(Integer.valueOf(lLongArrNetPays[lInt].intValue()));
			lObjMstBrokenPeriodPay.setReason(lStrArrReasons[lInt].trim());
			lObjMstBrokenPeriodPay.setDdoCode(ddoCode);

			if (lStrArrRemarks.length == 0) {
				lObjMstBrokenPeriodPay.setRemarks(null);
			} else if (!lStrArrRemarks[lInt].trim().equals("###")) {
				lObjMstBrokenPeriodPay.setRemarks(lStrArrRemarks[lInt].trim());
			}
			// lObjMstBrokenPeriodPay.setCreatedUserId(gLngUserId);
			lObjMstBrokenPeriodPay.setCreatedDate(gDtCurrDt);

			lObjMstBrokenPeriodPay.setBasicForCalculation(lstArrbasicForCalculation[lInt].trim());

			lArrMstBrokenPeriodPay[lInt] = lObjMstBrokenPeriodPay;
		}
		return lArrMstBrokenPeriodPay;
	}

	@Override
	public String isEmpMappedWithBillGroup(String sevaarthId) {
		return brokenPeriodRepo.isEmpMappedWithBillGroup(sevaarthId);
	}

	@Override
	public int getSevaarthIdMappedWithBill(String sevaarthid, int month, int year, String ddoName) {
		int paybillStatus = 0;
		int processedStatus = brokenPeriodRepo.getSevaarthIdMappedWithPayBillProcessed(sevaarthid, month, year,
				ddoName);
		int inprogressStatus = brokenPeriodRepo.getSevaarthIdMappedWithPayBillInprogress(sevaarthid, month, year,
				ddoName);
		if (processedStatus != 0) {
			paybillStatus = 1;
		} else if (inprogressStatus != 0) {
			paybillStatus = 2;
		}
		return paybillStatus;
	}

	@Override
	public List<BrokenPeriodResponseModel> calculateEmployeeSalaryBasedofBasic(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, HashMap hmInputParam) {
		// TODO Auto-generated method stub
		Double excessPayrec = 0d;
		Double grossAmt = 0d;
		Double netAmt = 0d;
		Double da = 0d;
		Double hra = 0d;
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
		Long gradePay = 0l;
		Double basic = 0d;
		Double npsEmprAllow = 0d;
		Double npsEmprContri = 0d;
		Double npsEmpContri = 0d;
		Double cmFund = 0d;
		Double revenueStamp = 0d;
		Double excessPayment = 0d;
		Double HouseAdv = 0d;
		Double CA = 0d;
		Double FA = 0d;
		Double vehAdv = 0d;
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
		Double gpfDaSub = 0d;
		Double rop = 0d;
		Double payFixDiff = 0d;
		Double nps = 0d;
		Double gpfAdvance = 0d;
		Double hra6th = 0d;
		Double ta5th = 0d;
		String hbaHouseInst = null;
		String otherVehAdvInst = null;

		Date fromDate = null;
		Date toDate = null;

		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();

		String excessPayrecInst = null;
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(sevaarthid, hmInputParam.get("ddocode").toString());

		String startDate = null;
		int year = Integer.parseInt(hmInputParam.get("year").toString());
		int month = Integer.parseInt(hmInputParam.get("month").toString());

		int month2 = month + 1;
		int year2 = year;
		if (month2 < 10) {
			startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf("0" + month2) + "-01";
		} else {
			startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf(month2) + "-01";
		}

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			fromDate = sdf.parse(hmInputParam.get("fromDate").toString());
			toDate = sdf.parse(hmInputParam.get("toDate").toString());

		} catch (Exception e) {
			System.out.println("" + e);
		}

		if (hmInputParam.get("basicForCalculation").toString().equals("newBasic")) {

			// logger.info("Employee Name="+mstEmployeeModel.getEmployeeFullNameEn());
			// logger.info("Basic Pay="+mstEmployeeModel.getBasicPay());
			if (mstEmployeeModel.getBasicPay() != null && mstEmployeeModel.getBasicPay() > 0
					&& mstEmployeeModel.getPayCommissionCode() != 700005) {
				basic = mstEmployeeModel.getBasicPay();
			} else {
				basic = mstEmployeeModel.getSevenPcBasic();
			}
		} else if (hmInputParam.get("basicForCalculation").toString().equals("oldBasic")) {
			Integer amount = 0;
			Integer amount1 = 0;
			amount = paybillHeadMpgRepo.getamtbeforeannualincment(mstEmployeeModel.getSevaarthId(), startDate);
			basic = amount.doubleValue();
		}

		if (mstEmployeeModel.getGradePay() != null)
			gradePay = mstEmployeeModel.getGradePay();
		// Total number of days and Fractionofdays

		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, year);
		// cal2.set(Calendar.MONTH, month-1);
		cal2.set(Calendar.MONTH, month);

		Calendar toDate22 = Calendar.getInstance();
		toDate22.setTime(toDate);

		// java.util.Date finYrDate = cal2.getTime();
		int totalNoOfDays = toDate22.getActualMaximum(Calendar.DAY_OF_MONTH);
		int noOfDays = Integer.parseInt(hmInputParam.get("noOfDays").toString());
		gradePay = (long) Math.round((gradePay * noOfDays) / totalNoOfDays);
		basic = (double) Math.round((basic * noOfDays) / totalNoOfDays);
		mstEmployeeModel.setBasicPay(basic);
		/*
		 * logger.info("basic Day wise= "+basic); logger.info("month="+month);
		 * logger.info("year="+year); logger.info("totalNoOfDays="+totalNoOfDays);
		 * logger.info("noOfDays="+noOfDays);
		 * logger.info("Paycommission id="+mstEmployeeModel.getPayCommissionCode());
		 */

		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData();
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData();
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData();
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		logger.info("Sevaarth Id=" + sevaarthid);
		// List<Object[]> fetchAllowDeducName(String sevaarthid)
		String cityClass = String.valueOf(mstEmployeeModel.getCityClass());
		// mstEmployeeModel.setCityClass(cityClass.charAt(0));
		int payCommission = mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
		logger.info(" payCommission " + payCommission + "cityClass " + cityClass);

		// basicAmount=mstEmployeeModel.getBasicPay();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(sevaarthid);
		/* return displayOuterReportRepo.getAllDataForOuternew(ddocode); */

		List<BrokenPeriodModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				BrokenPeriodModel obj = new BrokenPeriodModel();
				obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
				obj.setType(StringHelperUtils.isNullInt(objLst[1]));
				obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
				obj.setGroupNm(StringHelperUtils.isNullString(objLst[3]));
				obj.setGisAmount(StringHelperUtils.isNullDouble(objLst[4]));
				// obj.setTempvalue(StringHelperUtils.isNullString(0);
				// obj.setTempempty(StringHelperUtils.isNullString('');
				// logger.info("objLst[0]="+objLst[0]+" && objLst[1]="+objLst[1]);
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;
		// List<DeptEligibilityForAllowAndDeductEntity> dptallDeduc =
		// deptEligibilityForAllowAndDeductService.findDeptEligibilityForAllowAndDeductList();
		for (int i = 0; i < allEdpList.size(); i++) {
			// if (allEdpList.get(i).getType() != null) {

			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			// logger.info("COMMONCODE_COMPONENT_GRP_ACC_POLICY="+CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY);
			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptallowdeducid()=" + allEdpList.get(i).getDeptallowdeducid());
			logger.info("svnDAloop=" + svnDA);
			String name = allEdpList.get(i).getDeptalldetNm();

			String temp = name;
			// allEdpList.get(i).getType()
			int percentage = 0;
			String percentageHRA = null;
			String citygroup = null;

			DdoOffice ddoScreenEntity = mstEmployeeRepo.findAllGroup(mstEmployeeModel.getDdoCode().trim());
			String spilt[] = ddoScreenEntity.getDcpsDdoOfficeCityClass().split("-");

			citygroup = spilt[1];

			if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				if (payCommission == 8 && !mstEmployeeModel.getGiscatagory().equals(3)) {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
							mstEmployeeModel.getCityClass());
				} else {
					percentage = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
					percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
							mstEmployeeModel.getCityClass());
				}
			} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
				percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, mstEmployeeModel.getCityClass());
			}
			if (allEdpList.get(i).getType() == 1) { // allowance
				if ((allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA))) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					// Start : 7 pc Calculation--> 17% of basic
					svnDA = (double) (Math.round((mstEmployeeModel.getBasicPay() * percentage)
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// logger.info("sevenpcDA "+sevenpcda);
					// sevenpcda=(sevenpcda * noOfDays) / totalNoOfDays ;
					// logger.info("sevenpcDA Day wise "+sevenpcda);
					// End : 7 pc Calculation
					logger.info("svnDA component3=" + svnDA);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(svnDA));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
					logger.info("svnDA component4=" + svnDA);
				} else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
						&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
						&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_SIXTH_PAY_DA
								|| payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					da = (double) (Math.round((mstEmployeeModel.getBasicPay() * percentage)
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// paybillGenerationTrnDetails.setDa( (double) Math.round((da)));
					brokenPeriodModel.setDeptalldetValue(String.valueOf((da)));
					logger.info("da Component=" + da);
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
					logger.info("DA componentexe=" + svnDA);

				}
				// End for 6PC and 7PC DA

				/*
				 * else
				 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
				 * .COMMONCODE_COMPONENT_DCPS_EMPLOYER))) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); //Start: DCPS Employer Contribution -->
				 * 14 % of basic+DA dcpsempcontribution=
				 * ((mstEmployeeModel.getBasicPay()+sevenpcda) * 14) /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; //
				 * dcpsempcontribution=(dcpsempcontribution * noOfDays) / totalNoOfDays ; //End:
				 * DCPS Employer Contribution
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsempcontribution));
				 * allowEdpList.add(brokenPeriodModel); //
				 * allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
				 * allowRuleList.add(brokenPeriodModel); }
				 */
				/* HRA component */
				else if (allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					// Start:HRA Calculation --> for 7 pc 8 % of basic for Z class / 16% of basic
					// for Y class / 24% of basic for X class ( for 6 pc --> 10% 20% 30%)
					if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

						if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
								&& mstEmployeeModel.getBasicPay() >= 22500)
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							hra = (double) 1800;
					}

					else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
						if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
						else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							hra = (mstEmployeeModel.getBasicPay() * Integer.parseInt(percentageHRA))
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					}
					// logger.info(" HRA = "+ hra);
					// hra=(hra * noOfDays) / totalNoOfDays ;
					// End: HRA Calculation
					logger.info("hra Component=" + hra);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}

				// HRA 6th

				else if (allEdpList.get(i).getDeptalldetNm()
						.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA6th_Code);

					if (employeeAllowDeducComponentAmtEntity != null) {

						hra6th = (double) (Math
								.round((employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
					}
					brokenPeriodModel.setDeptalldetValue(String.valueOf(hra6th));
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}

				// Start Travels Allowances for 6PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC && allEdpList
								.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							ta = (double) 2400;
						} else {
							ta = (double) 1200;
						}
					} else if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
							&& gradePay < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							ta = (double) 1200;
						} else {
							ta = (double) 600;
						}
					} else if (gradePay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
						ta = (double) 400;
					}
					ta = (ta * noOfDays) / totalNoOfDays;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
					logger.info("ta Component=" + ta);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);

				}
				// End Travels Allowances for 6PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradelevel >= 20) {
						if (citygroup.equals("A")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 3200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((3200 * noOfDays) / totalNoOfDays);
							} else {
								ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								// ta5th = (double) ((1600) / totalNoOfDays);
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((800 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradelevel >= 15 && gradelevel <= 19) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);

							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);

							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 400d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((400 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradelevel >= 1 && gradelevel <= 15) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((200 * noOfDays) / totalNoOfDays);

								// brokenPeriodModel.setDeptalldetValue(String.valueOf((200 * noOfDays) /
								// totalNoOfDays) );
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 150d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((150 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((150 * noOfDays) /
								// totalNoOfDays) );
							}
						}
						if (mstEmployeeModel.getSevaarthId().equals("MJPDMGM6601")) {
							// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
							Double taAmt = 400d;
							ta5th = ((taAmt * noOfDays) / totalNoOfDays);

						}

					}
					ta5th = (double) Math.round(ta5th);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta5th));
					logger.info("ta Component=" + ta5th);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);

				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE5th)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 3200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((3200 * noOfDays) / totalNoOfDays);
							} else {
								ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								// ta5th = (double) ((1600) / totalNoOfDays);
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((800 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradePay >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
							&& gradePay < CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1600d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1600 * noOfDays) / totalNoOfDays);

							} else {
								Double taAmt = 800d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((800 * noOfDays) / totalNoOfDays);

							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
							} else {
								Double taAmt = 400d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((400 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((400 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					} else if (gradePay <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
						if (citygroup.equals("A") || citygroup.equals("A+")) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 200d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);
								// ta5th = (double) ((200 * noOfDays) / totalNoOfDays);

								// brokenPeriodModel.setDeptalldetValue(String.valueOf((200 * noOfDays) /
								// totalNoOfDays) );
							}
						} else {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								Double taAmt = 1000d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((1000 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((1000 * noOfDays) /
								// totalNoOfDays) );
							} else {
								Double taAmt = 150d;
								ta5th = ((taAmt * noOfDays) / totalNoOfDays);

								// ta5th = (double) ((150 * noOfDays) / totalNoOfDays);
								// brokenPeriodModel.setDeptalldetValue(String.valueOf((150 * noOfDays) /
								// totalNoOfDays) );
							}
						}
					}
					ta5th = (double) Math.round(ta5th);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta5th));
					logger.info("ta Component=" + ta5th);
					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}
				// Start Travels Allowances for 7PC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
						&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC && allEdpList
								.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					Long gradelevel = mstEmployeeModel.getSevenPCLevel();
					String gradePay7PC = paybillHeadMpgRepo.getgradePay7PC(gradelevel);
					Integer grade7PC = Integer.parseInt(gradePay7PC);

					if ((month + 1 >= 4 && year >= 2022) || year <= 2023) {

						if (gradelevel >= 20) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 10800;
								} else {
									ta = (double) 5400;
								}
							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 5400;
								} else {
									ta = (double) 2700;
								}
							}
						} else if (gradelevel >= 7 && gradelevel <= 19) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 5400;
								} else {
									ta = (double) 2700;
								}
							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									ta = (double) 2700;
								} else {
									ta = (double) 1350;
								}
							}
						} else if (gradelevel >= 1 && gradelevel <= 6) {
							if (citygroup.equals("A") || citygroup.equals("A+")) {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									if (mstEmployeeModel.getSevenPcBasic() >= 24200) {
										ta = (double) 5400;
									} else {
										ta = (double) 2700;
									}
								} else if (mstEmployeeModel.getSevenPcBasic() >= 24200 && cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									ta = (double) 2700;
								} else {
									ta = (double) 1000;
								}

							} else {
								if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
									if (mstEmployeeModel.getSevenPcBasic() >= 24200) {
										ta = (double) 2700;
									} else {
										ta = (double) 2250;
									}
								} else if (mstEmployeeModel.getSevenPcBasic() >= 24200 && !cityClass
										.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X)) {
									ta = (double) 1350;
								} else {
									ta = (double) 675;
								}

							}
						}
					} else {
						if (grade7PC >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (cityClass.equals("X")) {
								ta = (double) 2400;
							} else {
								ta = (double) 1200;

							}
						} else if (grade7PC >= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400
								&& grade7PC <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_5400) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								ta = (double) 2000;
							} else if (cityClass.equals("X")) {
								ta = (double) 1200;
							} else {
								ta = (double) 600;
							}
						} else if (grade7PC >= 0
								&& grade7PC <= CommonConstants.PAYBILLDETAILS.COMMONCODE_GRADE_PAY_AMOUNT_4400) {
							if (mstEmployeeModel.getPhysicallyHandicapped().equals("Y")) {
								ta = (double) 2000;
							} else {
								ta = (double) 400;
							}
						}
					}
					ta = (ta * noOfDays) / totalNoOfDays;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
					logger.info("ta Component=" + ta);
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// End Travels Allowances for 7PC
				// Start Tribal Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRIBAL_ALLOW_CODE) {
						logger.info(" Inside iff for COMMONCODE_COMPONENT_TRIBAL_ALLOW ");
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Tribal Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** "
								+ employeeAllowDeducComponentAmtEntity.getNetAmt());
					} else {
						brokenPeriodModel = allEdpList.get(i);
						/*
						 * tribalAllow = (double) Math.round((mstEmployeeModel.getBasicPay()) * 6 /
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						 */
						tribalAllow = 0d;
						logger.info(" for COMMONCODE_COMPONENT_TRIBAL_ALLOW********** " + tribalAllow);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(tribalAllow));
						logger.info("Tribal Allow Component=" + String.valueOf(tribalAllow));
					}

					allowEdpList.add(brokenPeriodModel);
					// allowRuleList.add(brokenPeriodModel.getDeptallowdeducid());
					allowRuleList.add(brokenPeriodModel);
				}
				// transport Allowance Arr
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANS_ALLOW_ARR_CODE) {
						logger.info(" Inside iff for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR ");
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Trans Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info(" for setGpfGrpD********** " + employeeAllowDeducComponentAmtEntity.getNetAmt());
					} else {
						brokenPeriodModel = allEdpList.get(i);
						/*
						 * transAllowArr = (double) Math.round((mstEmployeeModel.getBasicPay()) * 6 /
						 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						 */
						transAllowArr = 0d;
						logger.info(" for COMMONCODE_COMPONENT_TRANS_ALLOW_ARR********** " + transAllowArr);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(transAllowArr));
						logger.info("Trans Allow Component=" + String.valueOf(transAllowArr));
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// DA Arr
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(
								(employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
						DaArr = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays;
						logger.info(
								"Da Arr Component=" + String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						/*
						 * paybillGenerationTrnDetails .setDaArr((double)
						 * (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt())))); DaArr+=
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						 */
					}

					else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
						// paybillGenerationTrnDetails
						// .setDaArr((double) 0);
						// DaArr+=0;

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// Additional HRA
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADD_HRA_CODE) {

						/*
						 * paybillGenerationTrnDetails .setAddHRA((double)
						 * (Math.round((employeeAllowDeducComponentAmtEntity.getNetAmt()))));
						 * addHRA+=Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt());
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Add hra Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setAddHRA((double) 0); addHRA+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}
				// Naxal Area Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NAXAL_AREA_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails .setNaxalAreaAllow((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * naxalAreaAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())
						 * );
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("naxal area  Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setNaxalAreaAllow((double) 0);
						 * naxalAreaAllow+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Special Duty Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPCL_DUTY_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails .setSpclDutyAllow((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * spclDutyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))
						 * ;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Special Duty Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setSpclDutyAllow((double) 0); spclDutyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Other Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_ALLOW_CODE) {
						/*
						 * paybillGenerationTrnDetails .setOtherAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * otherAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Other  Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherAllow((double) 0); otherAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// Basic Arr //
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BASIC_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails .setBasicArr((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * basicArr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("basic arr Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setBasicArr((double) 0); basicArr+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Special Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SPECIAL_PAY_CODE) {

						/*
						 * paybillGenerationTrnDetails.setSpecialPay((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * logger.info("Inside special pay"+employeeAllowDeducComponentAmtEntity.
						 * getNetAmt());
						 * specialPay+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("special pay Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setSpecialPay((double) 0); specialPay+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");

					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Personal Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PERSONAL_PAY_CODE) {

						/*
						 * paybillGenerationTrnDetails.setPersonalPay((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * personalPay+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("personal pay Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setPersonalPay((double) 0); personalPay+=0;
						 */

						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// CLA 5th Pay Pay

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity.getNetAmt() > 0d && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CLA_CODE) {
						Double claaa = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays;
						claaa = (double) Math.round(claaa);
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(claaa));
						logger.info("DP=" + String.valueOf(claaa));
					} else {
						/*
						 * paybillGenerationTrnDetails .setDearnessPay((double) 0); dearnessPay+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);

						Double claamt = 0d;
						if (payCommission == 8) {
							if (basic < 3000) {
								if (citygroup.equals("A+")) {
									claamt = 90d;
								} else if (citygroup.equals("A")) {
									claamt = 65d;
								} else if (citygroup.equals("B+")) {
									claamt = 45d;
								} else if (citygroup.equals("B")) {
									claamt = 25d;
								}
							} else if (basic >= 3000 && basic < 4499) {
								if (citygroup.equals("A+")) {
									claamt = 125d;
								} else if (citygroup.equals("A")) {
									claamt = 95d;
								} else if (citygroup.equals("B+")) {
									claamt = 65d;
								} else if (citygroup.equals("B")) {
									claamt = 35d;
								}
							} else if (basic >= 4500 && basic < 5999) {
								if (citygroup.equals("A+")) {
									claamt = 200d;
								} else if (citygroup.equals("A")) {
									claamt = 150d;
								} else if (citygroup.equals("B+")) {
									claamt = 100d;
								} else if (citygroup.equals("B")) {
									claamt = 65d;
								}
							} else {
								if (citygroup.equals("A+")) {
									claamt = 300d;
								} else if (citygroup.equals("A")) {
									claamt = 240d;
								} else if (citygroup.equals("B+")) {
									claamt = 180d;
								} else if (citygroup.equals("B")) {
									claamt = 120d;
								}
							}
						} else {
							if (basic < 3000) {
								if (citygroup.equals("A+")) {
									claamt = 90d;
								} else if (citygroup.equals("A")) {
									claamt = 65d;
								} else if (citygroup.equals("B+")) {
									claamt = 45d;
								} else if (citygroup.equals("B")) {
									claamt = 25d;
								}
							} else if (basic >= 3000 && basic < 4499) {
								if (citygroup.equals("A+")) {
									claamt = 125d;
								} else if (citygroup.equals("A")) {
									claamt = 95d;
								} else if (citygroup.equals("B+")) {
									claamt = 65d;
								} else if (citygroup.equals("B")) {
									claamt = 35d;
								}
							} else if (basic >= 4500 && basic < 5999) {
								if (citygroup.equals("A+")) {
									claamt = 200d;
								} else if (citygroup.equals("A")) {
									claamt = 150d;
								} else if (citygroup.equals("B+")) {
									claamt = 100d;
								} else if (citygroup.equals("B")) {
									claamt = 65d;
								}
							} else {
								if (citygroup.equals("A+")) {
									claamt = 300d;
								} else if (citygroup.equals("A")) {
									claamt = 240d;
								} else if (citygroup.equals("B+")) {
									claamt = 180d;
								} else if (citygroup.equals("B")) {
									claamt = 120d;
								}
							}
						}

						Double clanew = (claamt * noOfDays) / totalNoOfDays;
						clanew = (double) Math.round(clanew);
						brokenPeriodModel.setDeptalldetValue(String.valueOf(clanew));
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Conveyance Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONVEY_ALLOW_CODE) {

						/*
						 * paybillGenerationTrnDetails.setConveyAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * conveyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("convey Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setConveyAllow((double) 0); conveyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}

				// NPS Empr Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)) {

					BrokenPeriodModel brokenPeriodModel = null;
					brokenPeriodModel = allEdpList.get(i);
						if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
							npsEmprAllow = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						} else {
							npsEmprAllow = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}


					/*
					 * if (year >= 20 && month >= 8) { if (mstEmployeeModel.getBasicPay() != 0) {
					 * npsEmprAllow += (double) (Math.round((mstEmployeeModel.getBasicPay() + da +
					 * svnDA) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * brokenPeriodModel = allEdpList.get(i); }
					 * 
					 * } else { npsEmprAllow += (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + da + svnDA) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); brokenPeriodModel
					 * = allEdpList.get(i);
					 * 
					 * }
					 */
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprAllow));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_on_TA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE);

					BrokenPeriodModel brokenPeriodModel = null;
					/*
					 * DaOnTA+= (Math.round((mstEmployeeModel.getBasicPay()) * 17 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * paybillGenerationTrnDetails.setDaOnTA((double) (Math.round(DaOnTA)));
					 */
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_DA_on_TA_CODE) {

						/*
						 * paybillGenerationTrnDetails.setConveyAllow((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * conveyAllow+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						logger.info("Da on TA Allow Component="
								+ String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						/*
						 * paybillGenerationTrnDetails .setConveyAllow((double) 0); conveyAllow+=0;
						 */
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Deputation_Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Deputation_Allow_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// OverTime Allow
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Overtime_Allowance_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Hill_Station_Allowances
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hill_Station_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Tracer_Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Tracer_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Naksalied_Allowances
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Naksalied_Allowances_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = null;
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Arrears_Code) {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel
								.setDeptalldetValue(String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));
					} else {
						brokenPeriodModel = allEdpList.get(i);
						brokenPeriodModel.setDeptalldetValue("0");
					}
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);

				}
				// Washing_Allowance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Washing_Allowance)) {

					BrokenPeriodModel brokenPeriodModel = null;
					logger.info(" Inside iff for COMMONCODE_COMPONENT_TRIBAL_ALLOW ");
					brokenPeriodModel = allEdpList.get(i);

					wa = (double) ((50 * noOfDays) / totalNoOfDays);
					wa = (double) Math.round(wa);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(wa));
					System.out.println("Tribal Allow Component=" + String.valueOf(wa));
					allowEdpList.add(brokenPeriodModel);
					allowRuleList.add(brokenPeriodModel);
				}

				// End For Allowances//

				else {
					allEdpList.get(i).setDeptalldetValue("0");
					allowRuleList.add(allEdpList.get(i));
				}

			} else if (allEdpList.get(i).getType() == 2) {

				// ###################################################################
				// Professional Tax//
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (month2 == 2) {
						// logger.info("Inside checking paybillMonth" +
						// paybillHeadMpgModel.getPaybillMonth());
						if (mstEmployeeModel
								.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
								&& mstEmployeeModel
										.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
						else
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;

					} else {

						if (mstEmployeeModel
								.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4500
								&& mstEmployeeModel
										.getBasicPay() <= CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175;
						else if (mstEmployeeModel
								.getBasicPay() > CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_5500)
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200;
						else
							pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
					}
					// paybillGenerationTrnDetails.setPt(pt);
					logger.info("pt deduc Component=" + String.valueOf(pt));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
					deducTyEdpList.add(brokenPeriodModel); // Adjust by Treasury
					// dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
					dedRuleList.add(brokenPeriodModel);

				}
				// DCPS DA Arrear recovery//
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_DA_ARR_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsDa =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsDa =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else { dcpsDa =
				 * 0d; } // paybillGenerationTrnDetails.setDcpsDa((double) Math.round(dcpsDa));
				 * logger.info("dcpsDa deduc Component="+String.valueOf(dcpsDa));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDa));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DA_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails.setDcpsDa((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						dcpsDa += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDa((double) 0);
						 */
						dcpsDa += 0;
					}
					logger.info("dcpsDa deduc Component=" + String.valueOf(dcpsDa));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDa));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// DCPS Delay recovery
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_DCPS_DELAY_RECOVERY) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsDelay =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsDelay =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * dcpsDelay = 0d; } // paybillGenerationTrnDetails.setDcpsDelay((double)
				 * Math.round(dcpsDelay));
				 * logger.info("dcpsDelay deduc Component="+String.valueOf(dcpsDelay));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_DELAY_CODE) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsDelay += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						dcpsDelay += 0;
					}
					logger.info("dcpsDelay deduc Component=" + String.valueOf(dcpsDelay));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NPS Emp Contri
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					/*
					 * if (year >= 20 && month >= 8) { if (mstEmployeeModel.getBasicPay() != 0) {
					 * npsEmpContri += (double) (Math.round((mstEmployeeModel.getBasicPay() + da +
					 * svnDA) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 * 
					 * } else { npsEmpContri += (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + da + svnDA) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * 
					 * }
					 */
					if (mstEmployeeModel.getGiscatagory().equals(1)) {
						npsEmpContri = (double) (Math.ceil((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						npsEmpContri = (double) (Math.ceil((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}
					/*
					 * if (mstEmployeeModel.getGiscatagory().equals(1)) { if (year == 2023 && month2
					 * >= 8 || year >= 2024 && month2 >= 1) { npsEmpContri = (double)
					 * (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }else {
					 * npsEmpContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA +
					 * DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 * 
					 * } else { npsEmpContri = (double) (Math.round((mstEmployeeModel.getBasicPay()
					 * + svnDA + DaArr) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 */
					logger.info("NPS EMP Contri=" + String.valueOf(npsEmpContri));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmpContri));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Revenue Stamp
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Revenue_Stamp)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					revenueStamp = 1d;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(revenueStamp));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);

				}

				// CM Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CM_Fund_AC_INS_Code) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						cmFund += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						cmFund += 0;
					}
					logger.info("cmFund deduc Component=" + String.valueOf(cmFund));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(cmFund));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Excess Payment
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_payment_Code) {

						// paybillGenerationTrnDetails.setDcpsDelay((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						excessPayment += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						/*
						 * paybillGenerationTrnDetails .setDcpsDelay((double) 0);
						 */
						excessPayment += 0;
					}
					logger.info("cmFund deduc Component=" + String.valueOf(excessPayment));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayment));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// NPS Employer Contri
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)) {

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					/*
					 * if (year >= 20 && month >= 8) { if (mstEmployeeModel.getBasicPay() != 0) {
					 * npsEmprContri += (double) (Math.round((mstEmployeeModel.getBasicPay() + da +
					 * svnDA) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 * 
					 * } else { npsEmprContri += (double)
					 * (Math.round((mstEmployeeModel.getBasicPay() + da + svnDA) * 14 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					 * 
					 * }
					 */
					if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}
					/*if (mstEmployeeModel.getGiscatagory().equals(1)) {
						if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
							npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						} else {
							npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10
									/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						}

					} else {
						npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}*/

					/*
					 * if (mstEmployeeModel.getGiscatagory().equals(1)) { npsEmprContri = (double)
					 * (Math.round((mstEmployeeModel.getBasicPay() + svnDA + DaArr) * 10 /
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
					 * npsEmprContri = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA
					 * + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
					 */

					logger.info("NPS EMP Contri=" + String.valueOf(npsEmprContri));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprContri));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// DCPS pay Arr recovery
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_PAY_ARR_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) { dcpsPayArr =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) { dcpsPayArr =
				 * (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * dcpsPayArr = 0d; } // paybillGenerationTrnDetails.setDcpsPayArr((double)
				 * Math.round(dcpsPayArr));
				 * logger.info("dcpspayarr deduc Component="+String.valueOf(dcpsPayArr));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_PAY_ARR_CODE) {

						// paybillGenerationTrnDetails.setDcpsPayArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsPayArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						// paybillGenerationTrnDetails
						// .setDcpsPayArr((double) 0);
						dcpsPayArr += 0;
					}
					logger.info("dcpspayarr deduc Component=" + String.valueOf(dcpsPayArr));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// DCPS regular recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Emp_DCPS_REGULAR_RECOVERY)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
						dcpsReg = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
						dcpsReg = (double) (Math.round((mstEmployeeModel.getBasicPay() + da) * 10
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					} else {
						dcpsReg = 0d;
					}

					// paybillGenerationTrnDetails.setDcpsRegularRecovery((double)
					// Math.round(dcpsReg));
					logger.info("dcpsRegrec deduc Component=" + String.valueOf(dcpsReg));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsReg));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// DCPS Arr
				/*
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if(payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				 * logger.info("svnpc1"); dcpsArr = (double)
				 * (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else if
				 * (payCommission ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
				 * logger.info("svnpc2"); dcpsArr = (double)
				 * (Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
				 * logger.info("svnpc3"); dcpsArr = 0d; }
				 * 
				 * logger.info("dcpsArr="+dcpsArr);
				 * logger.info("mstEmployeeModel.getBasicPay()="+mstEmployeeModel.getBasicPay())
				 * ; logger.info("svnDA="+svnDA); //
				 * paybillGenerationTrnDetails.setDcpsArr((double) Math.round(dcpsArr));
				 * logger.info("dcpsarr deduc Component="+String.valueOf(dcpsArr));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR_CODE) {

						// paybillGenerationTrnDetails.setDcpsArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						dcpsArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					} else {
						// paybillGenerationTrnDetails
						// .setDcpsArr((double) 0);
						dcpsArr += 0;
					}
					logger.info("dcpsarr deduc Component=" + String.valueOf(dcpsArr));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Adjustable DCPS Employer Contribution//
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR)
						&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL)// employeer
																														// contribution
				{
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					adjust_dcps_empr = (double) (Math.round((mstEmployeeModel.getBasicPay() + svnDA) * 14
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					// paybillGenerationTrnDetails
					// .setAdjustDcpsEmployer((double) Math.round(adjust_dcps_empr + dcpsDa +
					// dcpsDelay + dcpsPayArr + dcpsArr));
					logger.info("adjust dcps empr deduc Component=" + String.valueOf(
							(double) Math.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr)));
					brokenPeriodModel.setDeptalldetValue(String.valueOf(
							(double) Math.round(adjust_dcps_empr + dcpsDa + dcpsDelay + dcpsPayArr + dcpsArr)));
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Start GIS Component
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
						&& allEdpList.get(i)
								.getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					logger.info("Gis mmmm### Component=");
					gisAmount = allEdpList.get(i).getGisAmount();
					if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_A)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));

					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_B)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_BNGZ)
							&& gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_C)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					} else if (allEdpList.get(i).getGroupNm()
							.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_D)
							&& (double) Math.round(gisAmount) == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D) {
						// paybillGenerationTrnDetails.setGis((double) Math.round(gisAmount));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
						logger.info("Gis deduc Component=" + String.valueOf((double) Math.round(gisAmount)));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// End GIS Component

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_ABC)) {

					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) {

						// paybillGenerationTrnDetails.setGpfGrpABC((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfGrpABC += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));

						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC));
						logger.info("gpf grp abc deduc Component=" + String.valueOf(gpfGrpABC));

					} else {

						gpfGrpABC = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfGrpABC((double) Math.round(gpfGrpABC));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC));
						logger.info("gpf grp abc deduc Component=" + String.valueOf(gpfGrpABC));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
					// GPF_GRP_D
				} else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_D)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) {
						// paybillGenerationTrnDetails.setGpfGrpD((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfGrpD += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpD));
						logger.info("GPF_GRP_D deduc Component=" + String.valueOf(gpfGrpD));
					} else {
						gpfGrpD = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfGrpD((double) Math.round(gpfGrpD));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfGrpD)));
						logger.info("GPF_GRP_D deduc Component=" + String.valueOf(gpfGrpD));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_ABC_ARR
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) {

						// paybillGenerationTrnDetails.setGpfAbcArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						GpfAbcArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(GpfAbcArr)));
						logger.info("GPF_ABC_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(GpfAbcArr))));
					} else {
						GpfAbcArr = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGpfAbcArr((double) Math.round(GpfAbcArr));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(GpfAbcArr)));
						logger.info("GPF_ABC_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(GpfAbcArr))));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Group_Acc_policy
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) {

						// paybillGenerationTrnDetails.setGroupAccPolicy((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						groupAccPolicy += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(groupAccPolicy));
						logger.info("GRP_ACC_POLICY deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						groupAccPolicy = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);

						// paybillGenerationTrnDetails.setGroupAccPolicy((double)
						// Math.round(groupAccPolicy));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(groupAccPolicy)));
						logger.info("GRP_ACC_POLICY deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(groupAccPolicy))));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_D_ARR
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) {
						// paybillGenerationTrnDetails.setGpfDArr((double)
						// Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						gpfDArr += (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfDArr));
						logger.info("GPF_D_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(gpfDArr))));

					} else {
						gpfDArr = (double) (Math.round(mstEmployeeModel.getBasicPay()) * 6
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
						// paybillGenerationTrnDetails.setGpfDArr((double) Math.round(gpfDArr));
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfDArr)));
						logger.info("GPF_D_ARR deduc Component="
								+ String.valueOf(String.valueOf((double) Math.round(gpfDArr))));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Contributory Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CONTRI_PROV_FUND_CODE) {

						/*
						 * paybillGenerationTrnDetails.setContriProvFund((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * contriProvFund+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())
						 * );
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CONTRI_PROV_FUND deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						// paybillGenerationTrnDetails
						// .setContriProvFund((double) 0);
						// contriProvFund+=0;
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				/// PT Arr

				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT_ARR_CODE) {

						/*
						 * paybillGenerationTrnDetails.setPtArr((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * ptArr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COMPONENT_PT_ARR deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));

					} else {
						/*
						 * paybillGenerationTrnDetails .setPtArr((double) 0); ptArr+=0;
						 */

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}

					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Other Deduction
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCT_CODE) {

						/*
						 * paybillGenerationTrnDetails.setOtherDeduct((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * otherDeduc+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COMPONENT_OHTER deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDeduct((double) 0); otherDeduc+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GIS ZP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS_ZP_CODE) {

						/*
						 * paybillGenerationTrnDetails.setGisZp((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * GisZp+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("GIS_ZP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setGisZp((double) 0); GisZp+=0;
						 */

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// House Rent Recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRR_CODE) {

						/*
						 * paybillGenerationTrnDetails .setHrr((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * hrr+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("HRR deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setHrr((double) 0); hrr+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Other Recovery
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

						/*
						 * paybillGenerationTrnDetails.setOtherRec((double)
						 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 * otherRec+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("OTHER_REC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherRec((double) 0); otherRec+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Income Tax
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_INCOME_TAX_CODE) {

						/*
						 * paybillGenerationTrnDetails.setIt((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * it+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("INCOME_TAX deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setIt((double) 0); it+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Service Charge
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SERVICE_CHARGE_CODE) {

						/*
						 * paybillGenerationTrnDetails.setServCharge((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * ServCharge+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("SERVICE_CHARGE deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setServCharge((double) 0); ServCharge+=0;
						 */
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Subscription
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Subscription_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// HBA
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// FA
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)) {
				 * 
				 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
				 * mstEmployeeService.findFADetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); if
				 * (lnaFAEmployeeRequestMstEntity != null &&
				 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
				 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount();
				 * 
				 * }
				 * 
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double)
				 * Math.round(FA))); } else {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// COMP_ADV
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_COMP_ADV)) { LNACAEmployeeRequestMstEntity
				 * lnaCAEmployeeRequestMstEntity = mstEmployeeService.findCADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * 
				 * if (lnaCAEmployeeRequestMstEntity != null &&
				 * lnaCAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaCAEmployeeRequestMstEntity.getInstallmentAmount() != null) { CA =
				 * lnaCAEmployeeRequestMstEntity.getInstallmentAmount(); //
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double)
				 * Math.round(CA))); } else {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// GPF Advance
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFAdvance)) {
					LoanEmployeeDtlsEntity loandetails = mstEmployeeService.findGPFADetails(
							mstEmployeeModel.getSevaarthId(),
							CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPFA_Code);

					/*
					 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
					 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
					 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
					 */
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

					if (loandetails != null && loandetails.getLoanactivateflag() == 1) {
						if (loandetails.getLoanprinemiamt() != null) {
							gpfAdvance = loandetails.getLoanprinemiamt().doubleValue();
							// mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),gpfAdvD);
						}

						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gpfAdvance)));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// LIC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// OTHER_REC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_REC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Or_Bank_Loan
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Or_Bank_Loan_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// BLWF
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BLWF_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NDCPS_Subscription
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NDCPS_Subscription_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Arrears_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_Special_Arrears
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_Special_Arrears_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue(String.valueOf((double) 0));
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GPF_DA_Sub
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_DA_Sub_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// ROP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ROP_Code) {

						/*
						 * paybillGenerationTrnDetails.setOtherDed((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * othrded+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDed((double) 0); othrded+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Pay_Fix_Diff
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Pay_Fix_Diff_Code) {

						/*
						 * paybillGenerationTrnDetails.setOtherDed((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * othrded+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						/*
						 * paybillGenerationTrnDetails .setOtherDed((double) 0); othrded+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// NPS
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				/*
				 * // BEGIS else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BEGIS))
				 * { BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * OtherAllowanceEntity otherAllowanceEntity = null; if
				 * (mstEmployeeModel.getGiscatagory() != null) { if
				 * (mstEmployeeModel.getGiscatagory().equals(1) ||
				 * mstEmployeeModel.getGiscatagory().equals(2)) { otherAllowanceEntity =
				 * mstEmployeeService.findBEGISAmt(mstEmployeeModel.getGiscatagory(),
				 * mstEmployeeModel.getBegisCatg()); } else { otherAllowanceEntity =
				 * mstEmployeeService .findBEGISAmtwith(mstEmployeeModel.getGiscatagory()); } }
				 * if (otherAllowanceEntity != null) {
				 * 
				 * brokenPeriodModel
				 * .setDeptalldetValue(String.valueOf(Math.round(otherAllowanceEntity.getAmount(
				 * )))); } else { brokenPeriodModel.setDeptalldetValue("0"); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				// ##################################################################

				/*
				 * if(allEdpList.get(i).getDeptallowdeducid()==36||allEdpList.get(i).
				 * getDeptallowdeducid()==37||allEdpList.get(i).getDeptallowdeducid()==38) {
				 * 
				 * 
				 * deducAgEdpList.add(allEdpList.get(i));
				 * allEdpList.get(i).setDeptalldetValue("0");
				 * dedRuleList.add(allEdpList.get(i));
				 * 
				 * 
				 * }//Deductions Adj. By CAFO/Supri./Admin. else {
				 */
				/*
				 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
				 * .COMMONCODE_COMPONENT_ADJUST_DCPS_EMPR))) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); //Start:Adjustable DCPS Employer
				 * Contribution --> 14 % of basic+DA adjdcpsempcontr=
				 * ((mstEmployeeModel.getBasicPay()+sevenpcda) * 14) /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100; //
				 * adjdcpsempcontr=(adjdcpsempcontr * noOfDays) / totalNoOfDays ; //End:HRA
				 * Adjustable DCPS Employer Contribution
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(adjdcpsempcontr));
				 * deducTyEdpList.add(brokenPeriodModel); //Adjust by Treasury //
				 * dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
				 * dedRuleList.add(brokenPeriodModel); } else if
				 * (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_DCPS_DELAY_RECOVERY) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); dcpsDelay =
				 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsDelay));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_PAY_ARR_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); dcpsPayArr =
				 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsPayArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_REG_REC) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); dcpsReg =
				 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsReg));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_ARR) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); dcpsArr =
				 * Math.round((mstEmployeeModel.getBasicPay() + da) * 10 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsArr));
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * 
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_ABC)) {
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE);
				 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
				 * (employeeAllowDeducComponentAmtEntity != null &&
				 * allEdpList.get(i).getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_ABC_CODE) {
				 * logger.info(" Inside iff for employeeAllowDeducComponentAmtEntity != null");
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * logger.info(" for gpfGrpABC********** " +
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else {
				 * logger.info(" Inside else for employeeAllowDeducComponentAmtEntity != null");
				 * gpfGrpABC = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * logger.info(" for gpfGrpABC********** " + gpfGrpABC);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpABC)); }
				 * 
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * 
				 * } else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_GRP_D)) {
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE); BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); if (employeeAllowDeducComponentAmtEntity
				 * != null && allEdpList.get(i).getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GPF_GRP_D_CODE) {
				 * logger.info(" Inside iff for employeeAllowDeducComponentAmtEntity != null");
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
				 * employeeAllowDeducComponentAmtEntity.getNetAmt())); logger.info(
				 * " for setGpfGrpD********** " +
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { gpfGrpD =
				 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * logger.info(" for gpfGrpD********** " + gpfGrpD);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfGrpD)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * 
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR)) {
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE);
				 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
				 * (employeeAllowDeducComponentAmtEntity != null &&
				 * allEdpList.get(i).getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ABC_ARR_CODE) {
				 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GPF_ABC_ARR ");
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * logger.info(" for COMMONCODE_COMPONENT_GPF_ABC_ARR********** " +
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double GpfAbcArr
				 * = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * logger.info(" for gpfGrpD********** " + gpfGrpD);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(GpfAbcArr)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * //Group_Acc_policy else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY)) {
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE);
				 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
				 * (employeeAllowDeducComponentAmtEntity != null &&
				 * allEdpList.get(i).getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE) {
				 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE ");
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * logger.info(" for COMMONCODE_COMPONENT_GPF_ABC_ARR********** " +
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double
				 * groupAccPolicy = Math.round((mstEmployeeModel.getBasicPay()) * 6 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * logger.info(" for gpfGrpD********** " + gpfGrpD);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(groupAccPolicy)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR)) {
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE);
				 * BrokenPeriodModel brokenPeriodModel=allEdpList.get(i); if
				 * (employeeAllowDeducComponentAmtEntity != null &&
				 * allEdpList.get(i).getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_D_ARR_CODE) {
				 * logger.info(" Inside iff for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE ");
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * logger.info(" for COMMONCODE_COMPONENT_GRP_ACC_POLICY_CODE********** " +
				 * employeeAllowDeducComponentAmtEntity.getNetAmt()); } else { double gpfDArr =
				 * Math.round((mstEmployeeModel.getBasicPay()) * 6 /
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100);
				 * logger.info(" for gpfGrpD********** " + gpfGrpD);
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfDArr)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 * else
				 * if((allEdpList.get(i).getDeptalldetNm().equals(CommonConstants.PAYBILLDETAILS
				 * .COMMONCODE_COMPONENT_PT))) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i); //Start: profession tax --> PT = 175
				 * when basic in between 4999 to 9999 or PT = 200 when basic above 9999
				 * if(mstEmployeeModel.getBasicPay() <=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4999) pt =
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0; else if
				 * (mstEmployeeModel.getBasicPay() >
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_4999 &&
				 * mstEmployeeModel.getBasicPay() <=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_LESS_THAN_9999) pt =
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_175; else if
				 * (mstEmployeeModel.getBasicPay() >
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_GREATER_THAN_9999) { if
				 * (month == 1) pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_300;
				 * else pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_200; }
				 * 
				 * else pt = CommonConstants.PAYBILLDETAILS.COMMONCODE_PT_AMOUNT_0;
				 * 
				 * // pt=(pt * noOfDays) / totalNoOfDays ; //End: profession tax
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
				 * deducTyEdpList.add(brokenPeriodModel); //Adjust by Treasury //
				 * dedRuleList.add(allEdpList.get(i).getDeptallowdeducid());
				 * dedRuleList.add(brokenPeriodModel); } // Start GIS Component else if
				 * (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS) &&
				 * allEdpList.get(i).getDeptalldetNm() !=
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) { BrokenPeriodModel
				 * brokenPeriodModel=allEdpList.get(i);
				 * gisAmount=allEdpList.get(i).getGisAmount(); if
				 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_A) && gisAmount ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_A) {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
				 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_B) && gisAmount ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_B) {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
				 * (allEdpList.get(i).getGroupNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_BNGZ)
				 * && gisAmount == CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_BNGZ) {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
				 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_C) && gisAmount ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_C) {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); } else if
				 * (allEdpList.get(i).getGroupNm().equalsIgnoreCase(CommonConstants.
				 * PAYBILLDETAILS.COMMONCODE_GROUP_GROUP_D) && gisAmount ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_GROUP_D) {
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gisAmount)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// End GIS Component
				else {
					allEdpList.get(i).setDeptalldetValue("0");
					dedRuleList.add(allEdpList.get(i));
					// dedRuleList.add("0");
				}

				// }
			}

			// type 3 and 4 ###########################################
			else if (allEdpList.get(i).getType() == 3) {
				// Credit Soc
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CREDIT_SOC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CREDIT_SOC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// LIC
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_LIC_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("LIC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Recurring Dep
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_RECURRING_DEP_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Cop_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COP_Bank_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("COPBANK deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Other Deduction
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_DEDUCTION_CODE) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("OTHER_DEDUCTION deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// alliedSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allied_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// mantralayaSoci
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Mantralaya_Soci_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// chiplunSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chiplun_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// ulhasnagarSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ulhasnagar_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// engrSoc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Engr_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Public_Health_Works
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Public_Health_Works_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sindhudurg_Oras
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sindhudurg_Oras_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_Society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Society_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Manahar_bhai_Meh_Jal
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manahar_bhai_Meh_Jal_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Akola_Pari_Abhiyani
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Akola_Pari_Abhiyani_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// ZP_Karmchari_Pat
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_ZP_Karmchari_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Vidharbha_Gramin_Kokan_Bn
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Vidharbha_Gramin_Kokan_Bn_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Chanda_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Chanda_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalseva_Soc_Nag
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_Soc_Nag_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhandara_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhandara_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// GDCC_BANK
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GDCC_BANK_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Gondia_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Gondia_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nagpur_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nagpur_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Allahabad_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Allahabad_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhan_Dist_Cent_Cop_bnk
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhan_Dist_Cent_Cop_bnk_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bank_of_Barora
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bank_of_Barora_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Court_Computation
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Court_Computation_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_GS_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_GS_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalgaon_Handicap_Soci
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalgaon_Handicap_Soci_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Dhule_Nandurbar_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Dhule_Nandurbar_Bank_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Parisar_Abhi_Soc_Nashik
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Parisar_Abhi_Soc_Nashik_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sarw_Aroy_Ban_Soci_Dhule
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sarw_Aroy_Ban_Soci_Dhule_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jaldhara_Soc_CL3
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jaldhara_Soc_CL3_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Panipurvtha_Soc_Cl3Or4
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Panipurvtha_Soc_Cl3Or4_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Govt_Bank
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Govt_Bank_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Sangli_Sal_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Sangli_Sal_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nashik_Road_Soc_CL3Or4
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Road_Soc_CL3Or4_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Jalseva_MAlegaon_Soc_CL3
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_MAlegaon_Soc_CL3_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Nashik_Bank_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Nashik_Bank_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Manda_Nashik_Soc
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Manda_Nashik_Soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Ujwala_Mahila_Pat_Bhand
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Ujwala_Mahila_Pat_Bhand_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// BC_Quarter
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BC_Quarter_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Excess_Pay_Rec
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Excess_Pay_Rec_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Flag_Day
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Flag_Day_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Bhand_Jil_Abhi_Karm_Pat
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Jalseva_karm_saha_Path
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Jalseva_karm_saha_Path_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Nanded
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Nanded_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Aurangabad
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Aurangabad_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Society_Latur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Society_Latur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MLWF_OnlyMJP
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MLWF_OnlyMJP_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Maha_Lab_Welfare_Fund
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Maha_Lab_Welfare_Fund_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc_Latur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Latur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// JalBhavan_Society
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_JalBhavan_Society_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// MJP_Soc_Solapur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_MJP_Soc_Solapur_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// MJP_Soc_Solapur
				else if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Satara_Society_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				// Added by Vaibhav

				// BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// END BHARATRATNA_VISHWESH_ABHI_SAH_PAT_MARYA

				// COMMONCODE_COMPONENT_Bhandara_Zilla_Parishad_Wa_Panchayat_Samiti_Karamachari_Sahakari_Sanstha_Bhandara
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Bhand_Jil_Abhi_Karm_Pat_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("RECURRING_DEP deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {

						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}

				// Hastantrit_pune_Mahan_soc
				else if (allEdpList.get(i).getDeptalldetNm().equalsIgnoreCase(
						CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc_Code);

					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Hastantrit_pune_Mahan_soc_Code) {

						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					} else {
						brokenPeriodModel.setDeptalldetValue("0");
					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				} else {
					allEdpList.get(i).setDeptalldetValue("0");
					dedRuleList.add(allEdpList.get(i));
					// dedRuleList.add("0");
				}

			}

			else if (allEdpList.get(i).getType() == 4) {
				// Credit Soc
				if (allEdpList.get(i).getDeptalldetNm()
						.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC)) {
					EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
							.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
									CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE);
					BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
					if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
							.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_CO_HSG_SOC_CODE) {

						/*
						 * paybillGenerationTrnDetails.setCreditSoc((double)
						 * (Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						 * CreditSoc+=(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
						 */
						brokenPeriodModel.setDeptalldetValue(
								String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
						logger.info("CO_HSG_SOC deduc Component=" + String.valueOf(
								String.valueOf((double) Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()))));
					} else {
						/*
						 * paybillGenerationTrnDetails .setCreditSoc((double) 0); CreditSoc+=0;
						 */
						brokenPeriodModel.setDeptalldetValue("0");

					}
					deducTyEdpList.add(brokenPeriodModel);
					dedRuleList.add(brokenPeriodModel);
				}
				/*
				 * // GPF_ADV_GRP_ABC else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC)) {
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
				 * 
				 * 
				 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
				 * mstEmployeeService.findLoanDetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); BigInteger gpfabc =
				 * null; Double gpfd = 0d; if (loanEmployeeDtlsEntity != null &&
				 * allEdpList.get(i) .getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE) {
				 * 
				 * gpfabc = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfd =
				 * gpfabc.doubleValue(); // paybillGenerationTrnDetails.setCoHsgSoc((double) //
				 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeModel.getEmployeeId(),
				 * gpfabc); brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfd));
				 * logger.info("GPF_ADV_GRP_ABC deduc Component=" +
				 * String.valueOf(String.valueOf(gpfd)));
				 * 
				 * } else {
				 * 
				 * paybillGenerationTrnDetails .setLIC((double) 0); lic+=0;
				 * 
				 * brokenPeriodModel.setDeptalldetValue("0");
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */
				// GPF_ADV_GRP_D
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_GPF_ADV_GRP_D)) {
				 * 
				 * EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity =
				 * mstEmployeeService .findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_ABC_CODE);
				 * 
				 * 
				 * LoanEmployeeDtlsEntity loanEmployeeDtlsEntity =
				 * mstEmployeeService.findLoanDetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE);
				 * 
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); BigInteger gpfadvd =
				 * null; Double gpfadvgrpd = 0d; if (loanEmployeeDtlsEntity != null &&
				 * allEdpList.get(i) .getDeptallowdeducid() ==
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GPF_ADV_GRP_D_CODE) {
				 * 
				 * gpfadvd = loanEmployeeDtlsEntity.getLoanprinemiamt(); gpfadvgrpd =
				 * gpfadvd.doubleValue();
				 * mstEmployeeService.updateEmpLoanAmt(mstEmployeeModel.getEmployeeId(),
				 * gpfadvd); // paybillGenerationTrnDetails.setCoHsgSoc((double) //
				 * Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt()));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(gpfadvgrpd));
				 * logger.info("GPF_ADV_GRP_ABC deduc Component=" +
				 * String.valueOf(String.valueOf(gpfadvgrpd)));
				 * 
				 * } else {
				 * 
				 * paybillGenerationTrnDetails .setLIC((double) 0); lic+=0;
				 * 
				 * brokenPeriodModel.setDeptalldetValue("0");
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

				// House Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_HBA_HOUSE)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNAHBAEmployeeRequestMstEntity
				 * lnaHBAEmployeeRequestMstEntity = mstEmployeeService.findHBADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HBA_HOUSE_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaHBAEmployeeRequestMstEntity != null &&
				 * lnaHBAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaHBAEmployeeRequestMstEntity.getInstallmentAmount() != null) { HouseAdv =
				 * lnaHBAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdv));
				 * 
				 * Integer hbai = lnaHBAEmployeeRequestMstEntity.getNoOfInstallmentsPaid() + 1;
				 * Integer sancHbaInst =
				 * lnaHBAEmployeeRequestMstEntity.getSanctionedNoOfInstallment(); hbaHouseInst =
				 * hbai.toString() + "/" + sancHbaInst.toString();
				 * 
				 * // mstEmployeeService.updateEmpLoanAmt(mstEmployeeEntity2.getEmployeeId(),
				 * gpfAdvD); }
				 * 
				 * } else { HouseAdv = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(HouseAdv));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(hbaHouseInst));
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 */

				// Computer Adv
				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_COMP_ADV)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNACAEmployeeRequestMstEntity
				 * lnaCAEmployeeRequestMstEntity = mstEmployeeService.findCADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_COMP_ADV_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaCAEmployeeRequestMstEntity != null &&
				 * lnaCAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaCAEmployeeRequestMstEntity.getInstallmentAmount() != null) { CA =
				 * lnaCAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(CA)); }
				 * 
				 * } else { CA = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(CA)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// Festival Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA)) {
				 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				 * LNAFAEmployeeRequestMstEntity lnaFAEmployeeRequestMstEntity =
				 * mstEmployeeService.findFADetails( mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_FA_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaFAEmployeeRequestMstEntity != null &&
				 * lnaFAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaFAEmployeeRequestMstEntity.getInstallmentAmount() != null) { FA =
				 * lnaFAEmployeeRequestMstEntity.getInstallmentAmount();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(FA));
				 * 
				 * }
				 * 
				 * } else { FA = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(FA)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */
				// Vehical Adv

				/*
				 * else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_OTHER_VEH_ADV)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); LNAVAEmployeeRequestMstEntity
				 * lnaVAEmployeeRequestMstEntity = mstEmployeeService.findVADetails(
				 * mstEmployeeModel.getEmployeeId(),
				 * CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_OTHER_VEH_ADV_Code);
				 * 
				 * System.out.println("mstEmployeeEntity2.getEmployeeId()" +
				 * mstEmployeeModel.getEmployeeId());
				 * 
				 * if (lnaVAEmployeeRequestMstEntity != null &&
				 * lnaVAEmployeeRequestMstEntity.getIsActive() == 1) { if
				 * (lnaVAEmployeeRequestMstEntity.getPrinInstallmentAmountMCA() != null) {
				 * vehAdv = lnaVAEmployeeRequestMstEntity.getOddPrinAmtPlusPrinAmt();
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(vehAdv)); }
				 * 
				 * } else { vehAdv = (double) 0;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(vehAdv));
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(otherVehAdvInst)); }
				 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
				 */

				/*
				 * // Excess_Pay_Rec else if (allEdpList.get(i).getDeptalldetNm()
				 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
				 * COMMONCODE_COMPONENT_Excess_Pay_Rec)) { BrokenPeriodModel brokenPeriodModel =
				 * allEdpList.get(i); ExcessPayRecoveryEntity excessPayRecoveryEntity =
				 * mstEmployeeService .findExcPayRec(mstEmployeeModel.getSevaarthId());
				 * 
				 * 
				 * if (excessPayRecoveryEntity != null && excessPayRecoveryEntity.getIsActive()
				 * == '1') { if (excessPayRecoveryEntity.getPrinEmiAmt() != null) { excessPayrec
				 * = excessPayRecoveryEntity.getPrinEmiAmt();
				 * 
				 * Integer excInst = excessPayRecoveryEntity.getLoanPrinInstNo() + 1; Integer
				 * sancExcInst = excessPayRecoveryEntity.getTotalInstNo(); excessPayrecInst =
				 * excInst.toString() + "/" + sancExcInst.toString(); }
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrec));
				 * //brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrecInst));
				 * 
				 * 
				 * } else { excessPayrec=0d;
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrec)); //
				 * brokenPeriodModel.setDeptalldetValue(String.valueOf(excessPayrecInst));
				 * 
				 * } deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel);
				 * }
				 * 
				 * 
				 * }
				 */

				/*
				 * Double alltotal=basic+da+hra; Double dedtotal=pt+hrr+cla+lic; double
				 * tota=alltotal-dedtotal; if(tota>=gpfadvgrpd) {
				 * 
				 * }
				 */

				// ########################################################################################

				else {
					// if (allEdpList.get(i).getLOOKUP_ID().equals("2500383")) { //Deduction like
					// fax
					deducOthEdpList.add(allEdpList.get(i));
					allEdpList.get(i).setDeptalldetValue("0");
					dedRuleList.add(allEdpList.get(i));
					// dedRuleList.add("0");
				}
				// if (allEdpList.get(i).getLOOKUP_ID().equals("2500381")) {
				// deducTyEdpList.add(allEdpList.get(i));
				// }
				// }
			}
			// Dynamic Process end
		}
		BrokenPeriodResponseModel bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("allowRuleList");
		bpResponseMode.setData(allowRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("dedRuleList");
		bpResponseMode.setData(dedRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("basicAmt");
		bpResponseMode.setData(mstEmployeeModel.getBasicPay());
		brokenPeriodResponseModel.add(bpResponseMode);
		String month1 = String.valueOf(Integer.parseInt(hmInputParam.get("month").toString().trim()) + 1);
		String monthyear = month1.length() == 1 ? "0" + month1 + "-" + hmInputParam.get("year").toString().trim()
				: month1 + "-" + hmInputParam.get("year").toString().trim();
		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("status");
		bpResponseMode.setData(brokenPeriodRepo.CheckBrkPrdMonthExitOrNot(monthyear, sevaarthid, fromDate, toDate));
		brokenPeriodResponseModel.add(bpResponseMode);

		return brokenPeriodResponseModel;

	}

	@Override
	public List<BrokenPeriodResponseModel> saveBrokenPeriodDAArrayPay(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, HashMap<String, String> hmInputParam) {

		Boolean lBlFlag = false;
		Boolean lBlFirstTimeSave = null;
		Long lLongBrknPrdIdForDelete = null;

		try {

			Map inputMap = generateMap(hmInputParam);
			BrokenPeriodEntity[] lArrMstBrokenPeriodPay = (BrokenPeriodEntity[]) inputMap.get("lArrMstBrokenPeriodPay");
			List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodAllow = (List<BrokenPeriodAllowDeducEntity>) inputMap
					.get("lListBrokenPeriodAllows");
			List<BrokenPeriodAllowDeducEntity> lListRltBrokenPeriodDeduc = (List<BrokenPeriodAllowDeducEntity>) inputMap
					.get("lListBrokenPeriodDeducs");

			Long lLongYear = Long.valueOf(inputMap.get("paybillYear").toString());
			;// Long.valueOf(StringUtility.getParameter("year", request).trim());
			Long lLongMonth = Long.valueOf(inputMap.get("paybillMonth").toString());// Long.valueOf(StringUtility.getParameter("month",
																					// request).trim());
			Long lLongEisEmpId = Long.valueOf(inputMap.get("empId").toString());// Long.valueOf(StringUtility.getParameter("eisEmpId",
																				// request).trim());
			String ddoCode = inputMap.get("ddoCode").toString();
			lBlFirstTimeSave = (!brokenPeriodRepo.checkBrokenPeriodPayExistsOrNot(lLongEisEmpId, lLongYear, lLongMonth,
					ddoCode));
			if (!lBlFirstTimeSave) {
				List<BrokenPeriodEntity> lListBrokenPeriodPayList = brokenPeriodRepo
						.getAddedBrokenPeriodPaysForEmp(lLongEisEmpId, lLongYear, lLongMonth, ddoCode);
				for (Integer lInt = 0; lInt < lListBrokenPeriodPayList.size(); lInt++) {
					lLongBrknPrdIdForDelete = lListBrokenPeriodPayList.get(lInt).getBrokenPeriodId();
					brokenPeriodRepo.deleteAllBrokenPeriodAllowancesForBrknPrdId(lLongBrknPrdIdForDelete);
					brokenPeriodRepo.deleteAllBrokenPeriodDeductionsForBrknPrdId(lLongBrknPrdIdForDelete);
					brokenPeriodRepo.deleteAllBrokenPeriodPaysForPk(lLongBrknPrdIdForDelete);
				}
			}

			List result = new ArrayList();
			result = brokenPeriodRepo.saveBrokenPeriodDAArrayPay(lArrMstBrokenPeriodPay, lListRltBrokenPeriodAllow,
					lListRltBrokenPeriodDeduc);
			BrokenPeriodResponseModel brokenPeriodResponseModel2 = new BrokenPeriodResponseModel();
			brokenPeriodResponseModel2.setStatus("status");
			brokenPeriodResponseModel2.setData(result.get(0));
			brokenPeriodResponseModel.add(brokenPeriodResponseModel2);

			lBlFlag = true;

		} catch (Exception ex) {

			ex.printStackTrace();
			return brokenPeriodResponseModel;
		}

		return brokenPeriodResponseModel;
	}

	@Override
	public List<BrokenPeriodResponseModel> brokenPeriodDAArray(BrokenPeriodModel brokenPeriodModel,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel) {
		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(brokenPeriodModel.getSevaarthid(),
				brokenPeriodModel.getDdocode());

		logger.info("mstEmployeeModel.getSuperAnnDate()=" + mstEmployeeModel.getSuperAnnDate());
		String strsuperAnnDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (mstEmployeeModel.getSuperAnnDate() != null)
			strsuperAnnDate = formatter.format(mstEmployeeModel.getSuperAnnDate());

		lstResult.add(mstEmployeeModel.getEmployeeFullNameEn());
		lstResult.add(mstEmployeeModel.getSevaarthId());
		lstResult.add(mstEmployeeModel.getDesignationName());
		lstResult.add("");
		lstResult.add(mstEmployeeModel.getDcpsno());
		lstResult.add(strsuperAnnDate);
		if (mstEmployeeModel.getEmployeeId() != null)
			lstResult.add(mstEmployeeModel.getEmployeeId().toString());

		BrokenPeriodResponseModel bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("EmpDetail");
		bpResponseModel.setData(lstResult);
		brokenPeriodResponseModel.add(bpResponseModel);
		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("Sevaarthid");
		bpResponseModel.setData(brokenPeriodModel.getSevaarthid());
		brokenPeriodResponseModel.add(bpResponseModel);
		/*
		 * bpResponseModel = new BrokenPeriodResponseModel();
		 * bpResponseModel.setStatus("ddoCode");
		 * bpResponseModel.setData(brokenPeriodModel.getDdocode());
		 * brokenPeriodResponseModel.add(bpResponseModel);
		 */
		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData(locId);
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		logger.info("Sevaarth Id=" + brokenPeriodModel.getSevaarthid());
		// List<Object[]> fetchAllowDeducName(String sevaarthid)
		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducNameDaArray(brokenPeriodModel.getSevaarthid());
		// List<Object[]> lstprop =
		// brokenPeriodRepo.fetchAllowDeducName(brokenPeriodModel.getSevaarthid());
		/* return displayOuterReportRepo.getAllDataForOuternew(ddocode); */

		List<BrokenPeriodModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				BrokenPeriodModel obj = new BrokenPeriodModel();
				obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
				obj.setType(StringHelperUtils.isNullInt(objLst[1]));
				obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;
		for (int i = 0; i < allEdpList.size(); i++) {
			if (allEdpList.get(i).getType() == 1) { // allowance
				allowEdpList.add(allEdpList.get(i));
				allowRuleList.add(allEdpList.get(i));
			} else if (allEdpList.get(i).getType() == 2) {
				if (allEdpList.get(i).getDeptallowdeducid() == 82 || allEdpList.get(i).getDeptallowdeducid() == 83)
					dedRuleList.add(allEdpList.get(i)); // Deductions Adj. By CAFO/Supri./Admin.
				/*
				 * else dedRuleList.add(allEdpList.get(i));
				 */

			} /*
				 * else {
				 * 
				 * dedRuleList.add(allEdpList.get(i));// deducOthEdpList.add(allEdpList.get(i));
				 * }
				 */
		}

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("hidTotalAllowances");
		bpResponseModel.setData(allowRuleList.size());
		brokenPeriodResponseModel.add(bpResponseModel);
		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("hidTotalDeductions");
		bpResponseModel.setData(dedRuleList.size());
		brokenPeriodResponseModel.add(bpResponseModel);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("allowRuleList");
		bpResponseModel.setData(allowRuleList);
		brokenPeriodResponseModel.add(bpResponseModel);

		bpResponseModel = new BrokenPeriodResponseModel();
		bpResponseModel.setStatus("dedRuleList");
		bpResponseModel.setData(dedRuleList);
		brokenPeriodResponseModel.add(bpResponseModel);

		/*
		 * bpResponseModel = new BrokenPeriodResponseModel();
		 * bpResponseModel.setStatus("basicAmt");
		 * bpResponseModel.setData(mstEmployeeModel.getBasicPay());
		 * brokenPeriodResponseModel.add(bpResponseModel);
		 */

		// Fetch Saved Data from Broken Period
		// start######################################################################################
		if (!brokenPeriodRepo.checkBrokenPeriodPayExistsOrNot(mstEmployeeModel.getEmployeeId().longValue(),
				Long.valueOf(brokenPeriodModel.getPaybillYear()), Long.valueOf(brokenPeriodModel.getPaybillMonth()),
				brokenPeriodModel.getDdocode())) {
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("PaysAddedBefore");
			bpResponseModel.setData(false);
			brokenPeriodResponseModel.add(bpResponseModel);
			brokenPeriodResponseModel.add(bpResponseModel);
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("hidTotalRows");
			bpResponseModel.setData(1);
			brokenPeriodResponseModel.add(bpResponseModel);
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("BrokenPeriodPayListSize");
			bpResponseModel.setData(0);
			brokenPeriodResponseModel.add(bpResponseModel);

			// logger.info("brokenPeriodResponseModel="+brokenPeriodResponseModel);
		} else {

			List<BrokenPeriodEntity> lListAddedBrokenPeriodPays = brokenPeriodRepo.getAddedBrokenPeriodPaysForEmp(
					mstEmployeeModel.getEmployeeId().longValue(), Long.valueOf(brokenPeriodModel.getPaybillYear()),
					Long.valueOf(brokenPeriodModel.getPaybillMonth()), brokenPeriodModel.getDdocode());
			List DataForDisplayList = new ArrayList();
			for (Integer lInt = 0; lInt < lListAddedBrokenPeriodPays.size(); lInt++) {
				List lListAddedAllowances = new ArrayList();
				List lListAddedAllowancesNew = new ArrayList();
				List lListTempAddedAllowances = new ArrayList();
				List lListAddedDeductions = new ArrayList();
				List lListAddedDeductionsNew = new ArrayList();
				List lListTempAddedDeductions = new ArrayList();
				BrokenPeriodPayCustomModel brokenPeriodPayCustomVO = new BrokenPeriodPayCustomModel();

				BrokenPeriodEntity brokenPeriodPay = lListAddedBrokenPeriodPays.get(lInt);
				brokenPeriodPayCustomVO.setFromDate(brokenPeriodPay.getFromDate());
				brokenPeriodPayCustomVO.setToDate(brokenPeriodPay.getToDate());
				brokenPeriodPayCustomVO.setNoOfDays(brokenPeriodPay.getNoOfDays().longValue());
				// brokenPeriodPayCustomVO.setBasicPay(brokenPeriodPay.getBasicPay().longValue());
				brokenPeriodPayCustomVO.setNetPay(brokenPeriodPay.getNetPay().longValue());
				brokenPeriodPayCustomVO.setReason(brokenPeriodPay.getReason());
				brokenPeriodPayCustomVO.setRemarks(brokenPeriodPay.getRemarks());
				brokenPeriodPayCustomVO.setBasicForCalculation(brokenPeriodPay.getBasicForCalculation());

				lListTempAddedAllowances = brokenPeriodRepo
						.getAddedAllowancesForEmp(lListAddedBrokenPeriodPays.get(lInt).getBrokenPeriodId().longValue());
				lListAddedAllowances.addAll(lListTempAddedAllowances);

				/*
				 * for (int i = 0; i < (lListAllowancesForEmp != null ?
				 * lListAllowancesForEmp.size() : 0); i++) { Long allowCode = (Long)
				 * (((Object[]) lListAllowancesForEmp.get(i))[0]); boolean found = false; for
				 * (int j = 0; j < (lListAddedAllowances != null ? lListAddedAllowances.size() :
				 * 0); j++) { Object[] data = (Object[]) lListAddedAllowances.get(j); if
				 * (Arrays.asList(data).contains(allowCode)) {
				 * lListAddedAllowancesNew.add(data); found = true; break; } } if (!found) {
				 * String allowDesc = (String) (((Object[]) lListAllowancesForEmp.get(i))[1]);
				 * Object newData[] = { 0, 0, allowCode, 0, allowDesc };
				 * lListAddedAllowancesNew.add(newData); } }
				 */

				List lListAllowancesForEmp = brokenPeriodRepo
						.getAllowancesListForGivenEmpDAArray(brokenPeriodModel.getSevaarthid());
				// List lListAllowancesForEmp =
				// brokenPeriodRepo.getAllowancesListForGivenEmp(brokenPeriodModel.getSevaarthid());
				List lListDeductionsForEmp = brokenPeriodRepo
						.getDeductionsListForGivenEmpDAArray(brokenPeriodModel.getSevaarthid());
				// List lListDeductionsForEmp =
				// brokenPeriodRepo.getDeductionsListForGivenEmp(brokenPeriodModel.getSevaarthid());
				for (int i = 0; i < (lListAllowancesForEmp != null ? lListAllowancesForEmp.size() : 0); i++) {
					Long allowCode = (Long) Long.valueOf((((Object[]) lListAllowancesForEmp.get(i))[0]).toString());
					boolean found = false;
					for (int j = 0; j < (lListAddedAllowances != null ? lListAddedAllowances.size() : 0); j++) {
						Object[] data = (Object[]) lListAddedAllowances.get(j);
						if (Arrays.asList(data).contains(allowCode.intValue())) {
							lListAddedAllowancesNew.add(data);
							found = true;
							break;
						}
					}
					if (!found) {
						String allowDesc = (String) (((Object[]) lListAllowancesForEmp.get(i))[1]).toString();
						Object newData[] = { 0, 0, allowCode, 0, allowDesc };
						lListAddedAllowancesNew.add(newData);
					}
				}
				logger.info("lListAddedAllowancesNew=" + lListAddedAllowancesNew);

				brokenPeriodPayCustomVO.setAllowList(lListAddedAllowancesNew);
				// for (Iterator iterator = lListAddedAllowancesNew.iterator();
				// iterator.hasNext();) {
				// Object[] object = (Object[]) iterator.next();
				//
				// logger.info("allowance object[]="+object);
				// }

				/*
				 * bpResponseModel =new BrokenPeriodResponseModel();
				 * bpResponseModel.setStatus("hidTotalAllowances");
				 * bpResponseModel.setData(lListAddedAllowancesNew.size());
				 * brokenPeriodResponseModel.add(bpResponseModel);
				 */

				lListTempAddedDeductions = brokenPeriodRepo
						.getAddedDeductionsForEmp(lListAddedBrokenPeriodPays.get(lInt).getBrokenPeriodId().longValue());
				lListAddedDeductions.addAll(lListTempAddedDeductions);

				for (int i = 0; i < (lListDeductionsForEmp != null ? lListDeductionsForEmp.size() : 0); i++) {
					Long deducCode = (Long) Long.valueOf((((Object[]) lListDeductionsForEmp.get(i))[0]).toString());
					boolean found = false;
					for (int j = 0; j < (lListAddedDeductions != null ? lListAddedDeductions.size() : 0); j++) {
						Object[] data = (Object[]) lListAddedDeductions.get(j);
						if (Arrays.asList(data).contains(deducCode.intValue())) {
							lListAddedDeductionsNew.add(data);
							found = true;
							break;
						}
					}
					if (!found) {
						String deducDesc = (String) (((Object[]) lListDeductionsForEmp.get(i))[1]);
						Object newData[] = { 0, 0, deducCode, 0, deducDesc };
						lListAddedDeductionsNew.add(newData);
					}
				}

				/*
				 * bpResponseModel =new BrokenPeriodResponseModel();
				 * bpResponseModel.setStatus("hidTotalDeductions");
				 * bpResponseModel.setData(lListAddedDeductionsNew.size());
				 * brokenPeriodResponseModel.add(bpResponseModel);
				 */

				// Long eisEmpId = lListAddedBrokenPeriodPays.get(lInt).getEisEmpId();
				// lLongYearId, lLongMonthId

				// generated = lObjBrokenPeriodDAO.isGenerated(lLongHrEisEmpId, lLongMonthId,
				// lLongYearId);
				brokenPeriodPayCustomVO.setDeductList(lListAddedDeductionsNew);
				DataForDisplayList.add(brokenPeriodPayCustomVO);

			}
			// logger.info("Current date is..."+lObjDateFormat.format(gDtCurDate));
			// inputMap.put("yearId", lStrYear);
			// inputMap.put("monthId", lStrMonth);
			// inputMap.put("lDtCurDate", lObjDateFormat.format(gDtCurDate));
			/*
			 * inputMap.put("AddedAllowances", lListAddedAllowancesNew);
			 * inputMap.put("AddedDeductions", lListAddedDeductionsNew);
			 * inputMap.put("BrokenPeriodPayList", lListAddedBrokenPeriodPays);
			 */
			// inputMap.put("DataForDisplayList", DataForDisplayList);
			// inputMap.put("BrokenPeriodPayListSize", lListAddedBrokenPeriodPays.size());
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("hidTotalRows");
			bpResponseModel.setData(lListAddedBrokenPeriodPays.size());
			brokenPeriodResponseModel.add(bpResponseModel);
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("DataForDisplayList");
			bpResponseModel.setData(DataForDisplayList);
			brokenPeriodResponseModel.add(bpResponseModel);
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("BrokenPeriodPayListSize");
			bpResponseModel.setData(lListAddedBrokenPeriodPays.size());
			brokenPeriodResponseModel.add(bpResponseModel);
			bpResponseModel = new BrokenPeriodResponseModel();
			bpResponseModel.setStatus("PaysAddedBefore");
			bpResponseModel.setData(true);
			brokenPeriodResponseModel.add(bpResponseModel);
			// inputMap.put("PaysAddedBefore", "Yes");
			// inputMap.put("Generated", generated); // 0 = generated, 1(gen or apprvd)
			// logger.info("brokenPeriodResponseModel="+brokenPeriodResponseModel);
		}
		// Fetch Saved Data from Broken Period
		// end######################################################################################

		return brokenPeriodResponseModel;
	}

	public List<BrokenPeriodResponseModel> calculateEmployeeDAArrears(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, HashMap hmInputParam) {
		Double grossAmt = 0d;
		Double DaArr = 0d;
		Double empContri = 0d;
		Double emprallow = 0d;
		Double emprDeduct = 0d;
		Double netAmt = 0d;

		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(sevaarthid, hmInputParam.get("ddocode").toString());

		int year = Integer.parseInt(hmInputParam.get("year").toString());
		int month = Integer.parseInt(hmInputParam.get("month").toString());
		Calendar cal2 = Calendar.getInstance();
		cal2.set(Calendar.YEAR, year);
		// cal2.set(Calendar.MONTH, month-1);
		cal2.set(Calendar.MONTH, month);
		// java.util.Date finYrDate = cal2.getTime();
		int totalNoOfDays = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);
		int noOfDays = Integer.parseInt(hmInputParam.get("noOfDays").toString());
		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData();
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData();
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData();
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		logger.info("Sevaarth Id=" + sevaarthid);
		String cityClass = String.valueOf(mstEmployeeModel.getCityClass());

		mstEmployeeModel.setCityClass(cityClass);
		int payCommission = mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
		logger.info(" payCommission " + payCommission + "cityClass " + cityClass);

		// basicAmount=mstEmployeeModel.getBasicPay();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(sevaarthid);

		List<BrokenPeriodModel> lstObj = new ArrayList<>();
		if (!lstprop.isEmpty()) {
			for (Object[] objLst : lstprop) {
				BrokenPeriodModel obj = new BrokenPeriodModel();
				obj.setDeptalldetNm(StringHelperUtils.isNullString(objLst[0]));
				obj.setType(StringHelperUtils.isNullInt(objLst[1]));
				obj.setDeptallowdeducid(StringHelperUtils.isNullInt(objLst[2]));
				obj.setGroupNm(StringHelperUtils.isNullString(objLst[3]));
				obj.setGisAmount(StringHelperUtils.isNullDouble(objLst[4]));
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;
		for (int i = 0; i < allEdpList.size(); i++) {
			// if (allEdpList.get(i).getType() != null) {
			String name = allEdpList.get(i).getDeptalldetNm();

			String temp = name;
			// allEdpList.get(i).getType()
			int percentage = 0;
			String percentageHRA = null;
			String startDate = null;
			String citygroup = null;
			int month2 = month + 1;
			int year2 = year;
			if (month2 < 10) {
				startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf("0" + month2) + "-01";
			} else {
				startDate = String.valueOf(year2 - 2000) + '-' + String.valueOf(month2) + "-01";
			}

			DdoOffice ddoScreenEntity = mstEmployeeRepo.findAllGroup(mstEmployeeModel.getDdoCode().trim());

			String spilt[] = ddoScreenEntity.getDcpsDdoOfficeCityClass().split("-");

			citygroup = spilt[1];

			if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS)) {
				EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
						.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE);
				BrokenPeriodModel brokenPeriodModel = null;
				if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
						.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAY_AND_ALLOWANCES_ARREARS_CODE) {
					brokenPeriodModel = allEdpList.get(i);
					brokenPeriodModel
							.setDeptalldetValue(String.valueOf((employeeAllowDeducComponentAmtEntity.getNetAmt())));
				} else {
					brokenPeriodModel = allEdpList.get(i);
					brokenPeriodModel.setDeptalldetValue("0");
				}
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR)) {
				EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
						.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
								CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE);
				BrokenPeriodModel brokenPeriodModel = null;
				if (employeeAllowDeducComponentAmtEntity != null && allEdpList.get(i)
						.getDeptallowdeducid() == CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA_ARR_CODE) {
					brokenPeriodModel = allEdpList.get(i);
					brokenPeriodModel.setDeptalldetValue(String
							.valueOf((employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays));
					DaArr = (employeeAllowDeducComponentAmtEntity.getNetAmt() * noOfDays) / totalNoOfDays;
					logger.info("Da Arr Component=" + String.valueOf(employeeAllowDeducComponentAmtEntity.getNetAmt()));

					if (mstEmployeeModel.getGiscatagory().equals(1)) {

						empContri = (double) (Math
								.round((DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						emprallow = (double) (Math
								.round((DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						emprDeduct = (double) (Math
								.round((DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

					} else {
						empContri = (double) (Math
								.round((DaArr) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						emprallow = (double) (Math
								.round((DaArr) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						emprDeduct = (double) (Math
								.round((DaArr) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}

				}

				else {
					brokenPeriodModel = allEdpList.get(i);
					brokenPeriodModel.setDeptalldetValue("0");
					// paybillGenerationTrnDetails
					// .setDaArr((double) 0);
					// DaArr+=0;

				}
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

				BrokenPeriodModel brokenPeriodModel = null;
				brokenPeriodModel = allEdpList.get(i);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(empContri));

				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);

			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

				BrokenPeriodModel brokenPeriodModel = null;
				brokenPeriodModel = allEdpList.get(i);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(emprDeduct));

				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

				BrokenPeriodModel brokenPeriodModel = null;
				brokenPeriodModel = allEdpList.get(i);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(emprallow));

				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}

		}
		// Dynamic Process end

		BrokenPeriodResponseModel bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("allowRuleList");
		bpResponseMode.setData(allowRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("dedRuleList");
		bpResponseMode.setData(dedRuleList);
		brokenPeriodResponseModel.add(bpResponseMode);

		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("basicAmt");
		bpResponseMode.setData(mstEmployeeModel.getBasicPay());
		brokenPeriodResponseModel.add(bpResponseMode);
		String month1 = String.valueOf(Integer.parseInt(hmInputParam.get("month").toString().trim()) + 1);
		String monthyear = month1.length() == 1 ? "0" + month1 + "-" + hmInputParam.get("year").toString().trim()
				: month1 + "-" + hmInputParam.get("year").toString().trim();
		bpResponseMode = new BrokenPeriodResponseModel();
		bpResponseMode.setStatus("status");
		bpResponseMode.setData(brokenPeriodRepo.CheckBrkPrdMonthExitOrNot(monthyear, sevaarthid));
		brokenPeriodResponseModel.add(bpResponseMode);

		return brokenPeriodResponseModel;
	}

}