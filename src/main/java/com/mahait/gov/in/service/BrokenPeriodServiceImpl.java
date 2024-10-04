package com.mahait.gov.in.service;

import java.lang.reflect.Field;
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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.AllowanceDeductionRuleMstEntity;
import com.mahait.gov.in.entity.BrokenPeriodAllowDeducEntity;
import com.mahait.gov.in.entity.BrokenPeriodEntity;
import com.mahait.gov.in.entity.CLAMstEntity;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.EmployeeAllowDeducComponentAmtEntity;
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
		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData(locId);
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData(locId);
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		logger.info("Sevaarth Id=" + brokenPeriodModel.getSevaarthid());
		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		List<Object[]> lstprop = brokenPeriodRepo.fetchAllowDeducName(brokenPeriodModel.getSevaarthid());

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
					brokenPeriodPayCustomVO.setDeductList(lListAddedDeductionsNew);
					DataForDisplayList.add(brokenPeriodPayCustomVO);

				}
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
			}
		// Fetch Saved Data from Broken Period
		// end######################################################################################

		return brokenPeriodResponseModel;
	}

	@Override
	public List<BrokenPeriodResponseModel> calculateEmployeeSalary(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, HashMap hmInputParam) {
		Double da = 0d;
		Double hra = 0d;
		Double pt = 0d;
		Double gisAmount = 0d;
		Double ta = 0d;
		Double groupAccPolicy = 0d;
		Double DaArr = 0d;
		Double cla = 0d;
		Double dearnessPay = 0d;
		Double svnDA = 0d;
		Long gradePay = 0l;
		Double basic = 0d;
		Double npsEmprAllow = 0d;
		Double npsEmprContri = 0d;
		Double npsEmpContri = 0d;
		Double revenueStamp = 0d;
		Double wa = 0d;
		Double hra6th = 0d;
		Double ta5th = 0d;
		Date fromDate = null;
		Date toDate = null;

		Integer dcps = 0;

		MstEmployeeModel mstEmployeeModel = new MstEmployeeModel();
		List<String> lstResult = new ArrayList<String>();
		mstEmployeeModel = brokenPeriodRepo.getEmployeeinfo(sevaarthid, hmInputParam.get("ddocode").toString());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		try {

			fromDate = sdf.parse(hmInputParam.get("fromDate").toString());
			toDate = sdf.parse(hmInputParam.get("toDate").toString());

		} catch (Exception e) {
			System.out.println("" + e);

		}

		String strDate = sdf.format(fromDate);

		if (mstEmployeeModel.getBasicPay() != null && mstEmployeeModel.getBasicPay() > 0
				&& mstEmployeeModel.getPayCommissionCode() != 700005) {
			basic = mstEmployeeModel.getBasicPay();
		} else {
			basic = mstEmployeeModel.getSevenPcBasic();
		}
		if (mstEmployeeModel.getGradePay() != null)
			gradePay = mstEmployeeModel.getGradePay();
		int year = Integer.parseInt(hmInputParam.get("year").toString());
		int month = Integer.parseInt(hmInputParam.get("month").toString());

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(fromDate);

		cal2.set(Calendar.YEAR, year);

		cal2.set(Calendar.MONTH, month - 1);
		cal2.set(Calendar.MONTH, month);

		java.util.Date finYrDate = cal2.getTime();
		int totalNoOfDays = cal2.getActualMaximum(Calendar.DAY_OF_MONTH);

		int noOfDays = Integer.parseInt(hmInputParam.get("noOfDays").toString());
		gradePay = (long) Math.round((gradePay * noOfDays) / totalNoOfDays);
		basic = (double) Math.round((basic * noOfDays) / totalNoOfDays);
		mstEmployeeModel.setBasicPay(basic);

		// Broken Period Pay start
		List<BrokenPeriodModel> allowEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAllowCompoMpgData();
		List<BrokenPeriodModel> deducAgEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getAGDeducCompoMpgData();
		List<BrokenPeriodModel> deducTyEdpList = new ArrayList<BrokenPeriodModel>();// edpDao.getTRDeducCompoMpgData();
		List<BrokenPeriodModel> deducOthEdpList = new ArrayList<BrokenPeriodModel>();// changes for other (nps)
		// Broken Period Pay End

		List allowRuleList = new ArrayList();
		List dedRuleList = new ArrayList();

		logger.info("Sevaarth Id=" + sevaarthid);
		String cityClass = "";

		if (mstEmployeeModel.getCityClass() != null) {
			String spilt[] = mstEmployeeModel.getCityClass().split("-");
			cityClass = spilt[0];
			mstEmployeeModel.setCityClass(cityClass);
		}

		int payCommission = mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
		logger.info(" payCommission " + payCommission + "cityClass " + cityClass);

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
				obj.setMethodName(StringHelperUtils.isNullString(objLst[5]));
				obj.setFormulas(StringHelperUtils.isNullString(objLst[6]));
				lstObj.add(obj);
			}
		}
		// Dynamic process start
		List<BrokenPeriodModel> allEdpList = lstObj;

		for (int i = 0; i < allEdpList.size(); i++) {

			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptallowdeducid()=" + allEdpList.get(i).getDeptallowdeducid());
			logger.info("svnDAloop=" + svnDA);
			String name = allEdpList.get(i).getDeptalldetNm();

			String temp = name;
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

			int percentageRate[] = new int[3];

			percentageRate[0] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
					CommonConstants.PAYBILLDETAILS.SVNPC_ALLOW_DEDUC_CODE);
			percentageRate[1] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
					CommonConstants.PAYBILLDETAILS.SIXPC_ALLOW_DEDUC_CODE);
			percentageRate[2] = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC, 0);

			int centralpercentageRate[] = new int[3];
			centralpercentageRate[0] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC);
			centralpercentageRate[1] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC);
			centralpercentageRate[2] = paybillHeadMpgRepo.getDaCentralPercentageByMonthYear(startDate,
					CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_5PC);

			if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				percentage = percentageRate[0];
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC, cityClass);
			} else {
				percentage = percentageRate[1];
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, cityClass);
			}

			if (!allEdpList.get(i).getMethodName().equals("")) {
				EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
						.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
								allEdpList.get(i).getDeptallowdeducid());
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (employeeAllowDeducComponentAmtEntity != null) {

					brokenPeriodModel.setDeptalldetNm(allEdpList.get(i).getDeptalldetNm());
					brokenPeriodModel.setDeptalldetValue(
							String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					brokenPeriodModel
							.setTempvalue(String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));

					if (allEdpList.get(i).getType() == 1) {
						allowEdpList.add(brokenPeriodModel);
						allowRuleList.add(brokenPeriodModel);
					} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 3
							|| allEdpList.get(i).getType() == 4) {
						deducTyEdpList.add(brokenPeriodModel);
						dedRuleList.add(brokenPeriodModel);
					}

				}
				List<AllowanceDeductionRuleMstEntity> lst = paybillHeadMpgRepo
						.fetchComponentDtlsByCompoId(allEdpList.get(i).getDeptallowdeducid());

				Double tempVal = 0d;
				if (lst.size() > 0) {

					if (lst.get(0).getPercentage() != null) {

						tempVal = (double) ((Math.round(basic) * lst.get(0).getPercentage()
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						Field fieldName = null;

						brokenPeriodModel.setDeptalldetNm(allEdpList.get(i).getDeptalldetNm());
						brokenPeriodModel.setDeptalldetValue(String.valueOf(Math.round(tempVal)));
						brokenPeriodModel.setType(allEdpList.get(i).getType());

						if (allEdpList.get(i).getType() == 1) {
							allowEdpList.add(brokenPeriodModel);
							allowRuleList.add(brokenPeriodModel);
						} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 3
								|| allEdpList.get(i).getType() == 4) {
							deducTyEdpList.add(brokenPeriodModel);
							dedRuleList.add(brokenPeriodModel);
						}

					} else {
						tempVal = (double) (Math.round(lst.get(0).getAmount()));

						if (allEdpList.get(i).getType() == 1) {
							allowEdpList.add(brokenPeriodModel);
							allowRuleList.add(brokenPeriodModel);
						} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 3
								|| allEdpList.get(i).getType() == 4) {
							deducTyEdpList.add(brokenPeriodModel);
							dedRuleList.add(brokenPeriodModel);
						}

					}
				}
			} else if ((allEdpList.get(i).getDeptalldetNm()
					.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA))) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				svnDA = (double) (Math
						.round((basic * percentageRate[0]) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

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

				da = (double) (Math
						.round((basic * percentageRate[1]) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
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

				hra = paybillHeadMpgRepo.fetchHraDtls(basic, startDate, cityClass,
						allEdpList.get(i).getDeptallowdeducid());

				brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
				allowEdpList.add(brokenPeriodModel);
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
				} else {

					brokenPeriodModel = allEdpList.get(i);
					dearnessPay = 0d;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
				}
				brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}

			// Start Travels Allowances for 6PC
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
					&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				Long lnggradepay = (long) mstEmployeeModel.getGradePay();

				Long longPayComm = new Long(payCommission);
				ta = paybillHeadMpgRepo.fetchtaDtls(basic, longPayComm, allEdpList.get(i).getDeptallowdeducid(),
						lnggradepay, cityClass, mstEmployeeModel.getPhysicallyHandicapped());
				Long percentBasic = mstEmployeeModel.getPercentageOfBasic();
				Double ratio = (double) (percentBasic / 100);
				ta = ratio * ta;
				ta = (ta * noOfDays) / totalNoOfDays;
				brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}
			// Start Travels Allowances for 7PC
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
					&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				Long gradelevel = mstEmployeeModel.getSevenPCLevel();
				Long longPayComm = new Long(payCommission);

				ta = paybillHeadMpgRepo.fetchtaDtls(basic, longPayComm, allEdpList.get(i).getDeptallowdeducid(),
						gradelevel, cityClass, mstEmployeeModel.getPhysicallyHandicapped());
				Long percentBasic = mstEmployeeModel.getPercentageOfBasic();
				Double ratio = (double) (percentBasic / 100);
				ta = ratio * ta;
				ta = ((ta * noOfDays) / totalNoOfDays);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			// End Travels Allowances for 7PC

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
					brokenPeriodModel = allEdpList.get(i);

					List<AllowanceDeductionRuleMstEntity> lstCla = paybillHeadMpgRepo.getClaAmaountDtls(
							mstEmployeeModel.getSevenPCLevel(), basic, citygroup,
							mstEmployeeModel.getPayCommissionCode(), allEdpList.get(i).getDeptallowdeducid());
					if (lstCla.size() > 0)
						cla = lstCla.get(0).getAmount();

					Double clanew = (cla * noOfDays) / totalNoOfDays;
					clanew = (double) Math.round(clanew);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(clanew));
				}
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}

			// NPS Empr Allow
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_ALLOW)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if ((year2 == 24 && month2 >= 8) || (year2 >= 25 && month2 >= 1)) {

					npsEmprAllow = (double) (Math.round((basic + svnDA + DaArr) * 14
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

				} else {
					npsEmprAllow = (double) (Math.round((basic + svnDA + DaArr) * 10
							/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				}

				
				double dcpsEmpr = 0;
				double dcpsdelayed = 0;
				double dcpsda = 0;
				double payArr = 0;
				
				
				dcpsEmpr =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700046",month2,year2,"EMPR");
				dcpsdelayed =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700047",month2,year2,"EMPR");
				dcpsda =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700048",month2,year2,"EMPR");
				payArr =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700049",month2,year2,"EMPR");

				brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprAllow+dcpsEmpr+dcpsdelayed+dcpsda+payArr));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			// Professional Tax//
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				pt = paybillHeadMpgRepo.calculatePt(basic, month);// paybillHeadMpgRepo

				brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
				deducTyEdpList.add(brokenPeriodModel); // Adjust by Treasury
				dedRuleList.add(brokenPeriodModel);

			}

			/*
			 * // NPS Emp Contri else if (allEdpList.get(i).getDeptalldetNm()
			 * .equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.
			 * COMMONCODE_COMPONENT_NPS_EMP_CONTRI)) {
			 * 
			 * BrokenPeriodModel brokenPeriodModel = allEdpList.get(i); if
			 * (mstEmployeeModel.getGiscatagory().equals(1)) { npsEmpContri = (double)
			 * (Math.ceil( (basic + svnDA + DaArr) * 10 /
			 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); } else {
			 * npsEmpContri = (double) (Math.ceil( (basic + svnDA + DaArr) * 10 /
			 * CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100)); }
			 * 
			 * logger.info("NPS EMP Contri=" + String.valueOf(npsEmpContri));
			 * brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmpContri));
			 * deducTyEdpList.add(brokenPeriodModel); dedRuleList.add(brokenPeriodModel); }
			 */

			// NPS Employer Contri
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
					npsEmprContri = (double) (Math.round(
							(basic + svnDA + DaArr) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				} else {
					npsEmprContri = (double) (Math.round(
							(basic + svnDA + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				}
				
				
				double dcpsEmpr = 0;
				double dcpsdelayed = 0;
				double dcpsda = 0;
				double payArr = 0;
				
				
				dcpsEmpr =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700046",month2,year2,"EMPR");
				dcpsdelayed =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700047",month2,year2,"EMPR");
				dcpsda =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700048",month2,year2,"EMPR");
				payArr =paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(),"700049",month2,year2,"EMPR");

				brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprContri+dcpsEmpr+dcpsdelayed+dcpsda+payArr));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			//

			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				dcps = (int) (Math
						.round((basic + svnDA) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

				brokenPeriodModel.setDeptalldetValue(String.valueOf(dcps));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_DELAY)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				double dcpsdelayed = paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(), "700047",
						month2, year2, "EMP");

				brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsdelayed));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_PAY)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				double payArr = paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(), "700049",
						month2, year2, "EMP");
				brokenPeriodModel.setDeptalldetValue(String.valueOf(payArr));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DCPS_DA)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				double dcpsda = paybillHeadMpgRepo.findSumContribution(mstEmployeeModel.getSevaarthId(), "700048",
						month2, year2, "EMP");
				brokenPeriodModel.setDeptalldetValue(String.valueOf(dcpsda));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			// Start GIS Component
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				gisAmount = paybillHeadMpgRepo.findGisComponentValue(allEdpList.get(i).getGroupNm(),
						mstEmployeeModel.getDoj(), startDate, allEdpList.get(i).getDeptallowdeducid());
				brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}
			// End GIS Component

			// Group_Acc_policy
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Accidential_Policy)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				String cadre = paybillHeadMpgRepo.getEmpCadre(mstEmployeeModel.getSevaarthId(),
						mstEmployeeModel.getEmpClass());

				groupAccPolicy = paybillHeadMpgRepo.fetchAccidentialPilocyDtls(startDate, cadre,
						allEdpList.get(i).getDeptallowdeducid());

				brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(groupAccPolicy)));

				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			// ########################################################################################

			else {
				deducOthEdpList.add(allEdpList.get(i));
				allEdpList.get(i).setDeptalldetValue("0");
				dedRuleList.add(allEdpList.get(i));
				// dedRuleList.add("0");
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
		bpResponseMode.setData(basic);
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

	private double valueOf(Object object) {

		return 0;
	}

	@Override
	public List<BrokenPeriodResponseModel> saveBrokenPeriodPay(String sevaarthid,
			List<BrokenPeriodResponseModel> brokenPeriodResponseModel, Map hmInputParam) {
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
			result = brokenPeriodRepo.saveBrokenPeriodPay(lArrMstBrokenPeriodPay, lListRltBrokenPeriodAllow,
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
	public Map generateMap(Map inputMap) {

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
					lObjRltBrokenPeriodAllow.setBrokenPeriodEntity(lObjMstBrokenPeriodPay);
					lObjRltBrokenPeriodAllow.setBrokenPeriodId(lObjMstBrokenPeriodPay.getBrokenPeriodId());
					lObjRltBrokenPeriodAllow
							.setAllowDeducCode(Integer.valueOf(lLongArrAllowancesCodesFinal[lIntInner].intValue()));
					lObjRltBrokenPeriodAllow.setAllowDeducAmt(lLongArrAllowancesValuesFinal[lIntInner].doubleValue());
					lObjRltBrokenPeriodAllow.setIstype(1);
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
						lLongArrDeductionsCodesFinal[lIntInner] = Long
								.valueOf((lStrArrDeductionsCodesFinal[lIntInner].trim() != null
										&& !(lStrArrDeductionsCodesFinal[lIntInner].trim().equals(""))
												? lStrArrDeductionsCodesFinal[lIntInner].trim()
												: "0"));
					}

					for (Integer lIntInner = 0; lIntInner < lStrArrDeductionsValuesFinal.length; lIntInner++) {
						String[] amt = lStrArrDeductionsValuesFinal[lIntInner].trim().toString().split("\\.");
						lLongArrDeductionsValuesFinal[lIntInner] = Long
								.valueOf((amt[0].trim() != null && !(amt[0].trim().equals("")) ? amt[0].trim() : "0"));
					}

					for (Integer lIntInner = 0; lIntInner < lStrArrDeductionsCodesFinal.length; lIntInner++) {
						lObjRltBrokenPeriodDeduc = new BrokenPeriodAllowDeducEntity();
						lObjRltBrokenPeriodDeduc.setBrokenPeriodEntity(lObjMstBrokenPeriodPay);
						lObjRltBrokenPeriodDeduc.setBrokenPeriodId(lObjMstBrokenPeriodPay.getBrokenPeriodId());
						lObjRltBrokenPeriodDeduc
								.setAllowDeducCode(Integer.valueOf(lLongArrDeductionsCodesFinal[lIntInner].intValue()));
						lObjRltBrokenPeriodDeduc
								.setAllowDeducAmt(lLongArrDeductionsValuesFinal[lIntInner].doubleValue());
						lObjRltBrokenPeriodDeduc.setIstype(2);
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
		Double gpfGrpD = 0d;
		Double GpfAbcArr = 0d;
		Double gpfGrpABC = 0d;
		Double ta = 0d;
		Double tribalAllow = 0d;
		Double transAllowArr = 0d;
		Double groupAccPolicy = 0d;
		Double gpfDArr = 0d;
		Double DaArr = 0d;
		Double otherAllow = 0d;
		Double spclDutyAllow = 0d;
		Double hrr = 0d;
		Double otherDeduc = 0d;
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
		Double wa = 0d;
		Double hba = 0d;
		Double BLWF = 0d;
		Double GpfArrears = 0d;
		Double GpfSpclArrears = 0d;
		Double nps = 0d;
		Double gpfAdvance = 0d;
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

			if (basic != null && basic > 0 && mstEmployeeModel.getPayCommissionCode() != 700005) {
				basic = basic;
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
		int payCommission = mstEmployeeRepo.getpayCommissionAgainstEmployee(sevaarthid);
		logger.info(" payCommission " + payCommission + "cityClass " + cityClass);

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

			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptalldetNm()=" + allEdpList.get(i).getDeptalldetNm());
			logger.info("allEdpList.get(i).getDeptallowdeducid()=" + allEdpList.get(i).getDeptallowdeducid());
			logger.info("svnDAloop=" + svnDA);
			String name = allEdpList.get(i).getDeptalldetNm();

			String temp = name;
			int percentage = 0;
			String percentageHRA = null;
			String citygroup = null;

			if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {
				if (payCommission == 8 && !mstEmployeeModel.getGiscatagory().equals(3)) {
					percentage = paybillHeadMpgRepo.getDaPercentageByMonthYear(startDate,
							CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC,
							CommonConstants.PAYBILLDETAILS.SVNPC_ALLOW_DEDUC_CODE);
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
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC,
						CommonConstants.PAYBILLDETAILS.SIXPC_ALLOW_DEDUC_CODE);
				percentageHRA = paybillHeadMpgRepo.getHRAPercentageByMonthYear(startDate,
						CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC, mstEmployeeModel.getCityClass());
			}
			if ((allEdpList.get(i).getDeptalldetNm()
					.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA))) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				svnDA = (double) (Math
						.round((basic * percentage) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				brokenPeriodModel.setDeptalldetValue(String.valueOf(svnDA));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			} else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_DA)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL
					&& (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_SIXTH_PAY_DA
							|| payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				da = (double) (Math
						.round((basic * percentage) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				brokenPeriodModel.setDeptalldetValue(String.valueOf((da)));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}
			/* HRA component */
			else if (allEdpList.get(i).getDeptalldetNm()
					.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_HRA)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC) {

					if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					else if ((cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
							&& basic >= 22500)
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
						hra = (double) 1800;
				}

				else if (payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC) {
					if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_X))
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Y))
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
					else if (cityClass.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_CITY_CLASS_Z))
						hra = (basic * Integer.parseInt(percentageHRA))
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100;
				}
				logger.info("hra Component=" + hra);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			if (!allEdpList.get(i).getMethodName().equals("")) {
				EmployeeAllowDeducComponentAmtEntity employeeAllowDeducComponentAmtEntity = mstEmployeeService
						.findGRPComponentsData(mstEmployeeModel.getSevaarthId(),
								allEdpList.get(i).getDeptallowdeducid());
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (employeeAllowDeducComponentAmtEntity != null) {

					brokenPeriodModel.setDeptalldetNm(allEdpList.get(i).getDeptalldetNm());
					brokenPeriodModel.setDeptalldetValue(
							String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));
					brokenPeriodModel
							.setTempvalue(String.valueOf(Math.round(employeeAllowDeducComponentAmtEntity.getNetAmt())));

					if (allEdpList.get(i).getType() == 1) {
						allowEdpList.add(brokenPeriodModel);
						allowRuleList.add(brokenPeriodModel);
					} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 3
							|| allEdpList.get(i).getType() == 4) {
						deducTyEdpList.add(brokenPeriodModel);
						dedRuleList.add(brokenPeriodModel);
					}

				}
				List<AllowanceDeductionRuleMstEntity> lst = paybillHeadMpgRepo
						.fetchComponentDtlsByCompoId(allEdpList.get(i).getDeptallowdeducid());

				Double tempVal = 0d;
				if (lst.size() > 0) {

					if (lst.get(0).getPercentage() != null) {

						tempVal = (double) ((Math.round(basic) * lst.get(0).getPercentage()
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
						Field fieldName = null;

						brokenPeriodModel.setDeptalldetNm(allEdpList.get(i).getDeptalldetNm());
						brokenPeriodModel.setDeptalldetValue(String.valueOf(Math.round(tempVal)));
						brokenPeriodModel.setType(allEdpList.get(i).getType());

						if (allEdpList.get(i).getType() == 1) {
							allowEdpList.add(brokenPeriodModel);
							allowRuleList.add(brokenPeriodModel);
						} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 3
								|| allEdpList.get(i).getType() == 4) {
							deducTyEdpList.add(brokenPeriodModel);
							dedRuleList.add(brokenPeriodModel);
						}

					}

				}
			} else if ((allEdpList.get(i).getDeptalldetNm()
					.equals(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_SVN_DA))) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				svnDA = (double) (Math
						.round((basic * percentage) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));

				String formula = allEdpList.get(i).getFormulas();
				ScriptEngineManager manager = new ScriptEngineManager();
				ScriptEngine engine = manager.getEngineByName("JavaScript");
				formula = formula.replace("basic", String.valueOf(basic));
				formula = formula.replace("percentage", String.valueOf(percentage));
				Object result = null;
				try {
					result = engine.eval(formula);
				} catch (ScriptException e) {
					e.printStackTrace();
				}

				svnDA = ((Number) result).doubleValue();

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

				da = (double) (Math
						.round((basic * percentage) / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
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

				hra = paybillHeadMpgRepo.fetchHraDtls(basic, startDate, cityClass,
						allEdpList.get(i).getDeptallowdeducid());

				brokenPeriodModel.setDeptalldetValue(String.valueOf(hra));
				allowEdpList.add(brokenPeriodModel);
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
				} else {

					brokenPeriodModel = allEdpList.get(i);
					dearnessPay = 0d;
					brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
				}
				brokenPeriodModel.setDeptalldetValue(String.valueOf(dearnessPay));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}

			// Start Travels Allowances for 6PC
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
					&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_6PC
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				Long lnggradepay = (long) mstEmployeeModel.getGradePay();

				Long longPayComm = new Long(payCommission);
				ta = paybillHeadMpgRepo.fetchtaDtls(basic, longPayComm, allEdpList.get(i).getDeptallowdeducid(),
						lnggradepay, cityClass, mstEmployeeModel.getPhysicallyHandicapped());
				Long percentBasic = mstEmployeeModel.getPercentageOfBasic();
				Double ratio = (double) (percentBasic / 100);
				ta = ratio * ta;
				ta = (ta * noOfDays) / totalNoOfDays;
				brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);

			}
			// Start Travels Allowances for 7PC
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_TRANSPORT_ALLOWANCE)
					&& payCommission == CommonConstants.PAYBILLDETAILS.COMMONCODE_PAYCOMMISSION_7PC
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				Long gradelevel = mstEmployeeModel.getSevenPCLevel();
				Long longPayComm = new Long(payCommission);

				ta = paybillHeadMpgRepo.fetchtaDtls(basic, longPayComm, allEdpList.get(i).getDeptallowdeducid(),
						gradelevel, cityClass, mstEmployeeModel.getPhysicallyHandicapped());
				Long percentBasic = mstEmployeeModel.getPercentageOfBasic();
				Double ratio = (double) (percentBasic / 100);
				ta = ratio * ta;
				ta = ((ta * noOfDays) / totalNoOfDays);
				brokenPeriodModel.setDeptalldetValue(String.valueOf(ta));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			// End Travels Allowances for 7PC

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
					brokenPeriodModel = allEdpList.get(i);

					List<AllowanceDeductionRuleMstEntity> lstCla = paybillHeadMpgRepo.getClaAmaountDtls(
							mstEmployeeModel.getSevenPCLevel(), basic, citygroup,
							mstEmployeeModel.getPayCommissionCode(), allEdpList.get(i).getDeptallowdeducid());
					if (lstCla.size() > 0)
						cla = lstCla.get(0).getAmount();

					Double clanew = (cla * noOfDays) / totalNoOfDays;
					clanew = (double) Math.round(clanew);
					brokenPeriodModel.setDeptalldetValue(String.valueOf(clanew));
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
						npsEmprAllow = (double) (Math.round((((basic + svnDA) * 14) / 100) + totalDaArr));
					} else if (month == 12 && year == 2023) {
						DaArr1 = DaArr / 5;
						DaArr2 = DaArr - DaArr1;
						DaArrtenpersent = (DaArr1 * 10 / 100);
						DaArrforteenpersent = (DaArr2 * 14 / 100);
						totalDaArr = DaArrtenpersent + DaArrforteenpersent;
						npsEmprAllow = (double) (Math.round((((basic + svnDA) * 14) / 100) + totalDaArr));
					} else {
						npsEmprAllow = (double) (Math.round((basic + svnDA + DaArr) * 14
								/ CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
					}

				} else {
					npsEmprAllow = (double) (Math.round(
							(basic + svnDA + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				}

				brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprAllow));
				allowEdpList.add(brokenPeriodModel);
				allowRuleList.add(brokenPeriodModel);
			}

			// Professional Tax//
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_PT)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				pt = paybillHeadMpgRepo.calculatePt(basic, Integer.parseInt(brokenPeriodModel.getPaybillMonth()));// paybillHeadMpgRepo

				brokenPeriodModel.setDeptalldetValue(String.valueOf(pt));
				deducTyEdpList.add(brokenPeriodModel); // Adjust by Treasury
				dedRuleList.add(brokenPeriodModel);

			}

			// NPS Emp Contri
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMP_CONTRI)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (mstEmployeeModel.getGiscatagory().equals(1)) {
					npsEmpContri = (double) (Math.ceil(
							(basic + svnDA + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				} else {
					npsEmpContri = (double) (Math.ceil(
							(basic + svnDA + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				}

				logger.info("NPS EMP Contri=" + String.valueOf(npsEmpContri));
				brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmpContri));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			// NPS Employer Contri
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_NPS_EMPR_DEDUCT)) {

				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);
				if (year == 2023 && month2 >= 8 || year >= 2024 && month2 >= 1) {
					npsEmprContri = (double) (Math.round(
							(basic + svnDA + DaArr) * 14 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				} else {
					npsEmprContri = (double) (Math.round(
							(basic + svnDA + DaArr) * 10 / CommonConstants.PAYBILLDETAILS.COMMONCODE_PERCENTAGE_100));
				}
				brokenPeriodModel.setDeptalldetValue(String.valueOf(npsEmprContri));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			// Start GIS Component
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_GIS)
					&& allEdpList.get(i).getDeptalldetNm() != CommonConstants.PAYBILLDETAILS.COMMONCODE_VALUE_NULL) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				gisAmount = paybillHeadMpgRepo.findGisComponentValue(allEdpList.get(i).getGroupNm(),
						mstEmployeeModel.getDoj(), startDate, allEdpList.get(i).getDeptallowdeducid());
				brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(gisAmount)));
				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}
			// End GIS Component

			// Group_Acc_policy
			else if (allEdpList.get(i).getDeptalldetNm()
					.equalsIgnoreCase(CommonConstants.PAYBILLDETAILS.COMMONCODE_COMPONENT_Accidential_Policy)) {
				BrokenPeriodModel brokenPeriodModel = allEdpList.get(i);

				String cadre = paybillHeadMpgRepo.getEmpCadre(mstEmployeeModel.getSevaarthId(),
						mstEmployeeModel.getEmpClass());

				groupAccPolicy = paybillHeadMpgRepo.fetchAccidentialPilocyDtls(startDate, cadre,
						allEdpList.get(i).getDeptallowdeducid());

				brokenPeriodModel.setDeptalldetValue(String.valueOf((double) Math.round(groupAccPolicy)));

				deducTyEdpList.add(brokenPeriodModel);
				dedRuleList.add(brokenPeriodModel);
			}

			// ########################################################################################

			else {
				deducOthEdpList.add(allEdpList.get(i));
				allEdpList.get(i).setDeptalldetValue("0");
				dedRuleList.add(allEdpList.get(i));
				// dedRuleList.add("0");
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

			}
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

				List lListAllowancesForEmp = brokenPeriodRepo
						.getAllowancesListForGivenEmpDAArray(brokenPeriodModel.getSevaarthid());
				List lListDeductionsForEmp = brokenPeriodRepo
						.getDeductionsListForGivenEmpDAArray(brokenPeriodModel.getSevaarthid());
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
				brokenPeriodPayCustomVO.setDeductList(lListAddedDeductionsNew);
				DataForDisplayList.add(brokenPeriodPayCustomVO);

			}
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
		}
		// Fetch Saved Data from Broken Period
		// end######################################################################################

		return brokenPeriodResponseModel;
	}
}