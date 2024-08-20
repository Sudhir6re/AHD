package com.mahait.gov.in.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.TemplateEngine;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.PayslipModel;
import com.mahait.gov.in.model.PayslipReportModel;
import com.mahait.gov.in.repository.PayslipReportRepo;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DDOCityCategoryService;
import com.mahait.gov.in.service.DisplayInnerReportService;
import com.mahait.gov.in.service.PayslipReportService;
import com.mahait.gov.in.service.RegularReportService;

@Controller
@RequestMapping("/ddoast")
public class PayslipReportController extends BaseController {

	List<PayslipReportModel> listOfPayslipEmp = new ArrayList<>();

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	@Autowired
	DisplayInnerReportService displayInnerReportService;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	PayslipReportRepo payslipReportRepo;

	@Autowired
	PayslipReportService payslipReportService;

	@Autowired
	RegularReportService regularReportService;

	@Autowired
	DDOCityCategoryService dDOCityCategoryService;

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	@GetMapping("/getPayslipReport")
	public String getPayslipReport(@ModelAttribute("PayslipReportModel") PayslipReportModel payslipReportModel,
			Model model, Locale locale, HttpSession session) {

		String message = (String) model.asMap().get("message");
		model.addAttribute("paybillHeadMpgModel", payslipReportModel);
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		/// int isCityCategory =
		/// dDOCityCategoryService.getCityCategoryMappedWithDDo(messages.getUserName());

		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}

		model.addAttribute("ddoCode", messages.getUserName());
		/// model.addAttribute("isCityCategory", isCityCategory);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());

		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));
		return "/views/paybill/payslip-generate";
	}

	@PostMapping("/PayslipReport")
	public String PayslipReport(@ModelAttribute("PayslipReportModel") PayslipReportModel payslipReportModel,
			Model model, Locale locale, HttpSession session) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		List<PayslipReportModel> allowEdpList = new ArrayList<PayslipReportModel>();// edpDao.getAllowCompoMpgData(locId);
		List<PayslipReportModel> deducAgEdpList = new ArrayList<PayslipReportModel>();// edpDao.getAGDeducCompoMpgData(locId);
		List<PayslipReportModel> nongovdeducEdpList = new ArrayList<PayslipReportModel>();// edpDao.getnonGovDeducCompoMpgData(locId);

		List<PayslipReportModel> lstAllowPayslipModel = new ArrayList<PayslipReportModel>();// edpDao.getAllowCompoMpgData(locId);
		List<PayslipReportModel> lstDeducPayslipModel = new ArrayList<PayslipReportModel>();// edpDao.getAGDeducCompoMpgData(locId);

		ArrayList row = new ArrayList();
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		//// int isCityCategory =
		//// dDOCityCategoryService.getCityCategoryMappedWithDDo(messages.getUserName());

		String savaarthid = "";
		BigInteger payCommCode = null;
		BigInteger payCommissionCode = null;
		String sevenPCLvl = null;

		allowEdpList.clear();
		deducAgEdpList.clear();
		nongovdeducEdpList.clear();
		List<PayslipReportModel> allEdpList = null;
		List<Map<String, Object>> lstempdetails1 = null;
		ArrayList allowance = null;
		ArrayList lstdedectionag = null;
		ArrayList lstnonGovdedection = null;
		ArrayList dedtotal = null;
		ArrayList algrosstotal = null;
		ArrayList alnetamt = null;
		List<Object[]> lstEmp = payslipReportService.getEmployeeData1(payslipReportModel.getSchemeBillgroupId(),
				payslipReportModel.getPaybillMonth(), payslipReportModel.getPaybillYear(), messages.getDdoCode());

		Integer hraPercent = null;
		Integer daPercent = null;
		allowEdpList.clear();
		deducAgEdpList.clear();
		nongovdeducEdpList.clear();
		List<PayslipModel> ltsPayslipModel = new ArrayList<>();

		if (lstEmp.size() > 0) {
			ltsPayslipModel.clear();
			for (Object object[] : lstEmp) {

				PayslipModel payslipModel = new PayslipModel();
				payslipModel.setSevaarthId(StringHelperUtils.isNullString(object[0]));
				payslipModel.setEmpName(StringHelperUtils.isNullString(object[1]));
				payslipModel.setDesgnName(StringHelperUtils.isNullString(object[2]));

				if (object[3] != null)
					payslipModel.setDoj(StringHelperUtils.isNullDate(object[3]));

				if (object[4] != null)
					payslipModel.setDob(StringHelperUtils.isNullDate(object[4]));

				payslipModel.setMobileNo1(StringHelperUtils.isNullBigDecimal(object[5]));

				payslipModel.setPanNo(StringHelperUtils.isNullString(object[6]));
				BigInteger payComm = (BigInteger) object[7];
				payslipModel.setPayCommisionCode(payComm.longValue());
				payCommissionCode = payComm;

				payslipModel.setUidNo(StringHelperUtils.isNullString(object[8]));
				payslipModel.setIfsc(StringHelperUtils.isNullString(object[9]));

				payslipModel.setBankAccNo(StringHelperUtils.isNullBigInteger(object[10]));
				String finalAccNo = "";

				if (object[10] != null) {

					String lastfourdigit = payslipModel.getBankAccNo().toString()
							.substring((payslipModel.getBankAccNo().toString().length()) - 4);
					String maskAccNo = "";
					for (int i = 0; i < payslipModel.getBankAccNo().toString().length() - 4; i++) {
						maskAccNo = maskAccNo + "x";
					}

					finalAccNo = maskAccNo + lastfourdigit;
					payslipModel.setAccountNo(finalAccNo);
				}
				payslipModel.setDdoCode(StringHelperUtils.isNullString(object[11]));

				payslipModel.setGrossTotalAmt(StringHelperUtils.isNullDouble(object[12]));
				payslipModel.setTotalDeduction(StringHelperUtils.isNullDouble(object[13]));
				payslipModel.setTotalNetAmt(StringHelperUtils.isNullDouble(object[14]));

				if (object[14] != null)
					payslipModel.setNetAmtInWord(convertToIndianCurrency(object[14].toString()));

				payslipModel.setGpfNdcpsNo(StringHelperUtils.isNullString(object[15]));

				payslipModel.setBillGroupName(StringHelperUtils.isNullString(object[16]));
				payslipModel.setDor(StringHelperUtils.isNullDate(object[17]));
				payslipModel.setHraPercent(StringHelperUtils.isNullInt(object[18]));
				/// hraPercent = (Integer) object[20];
				payslipModel.setDaPercent(StringHelperUtils.isNullInt(object[19]));
				//// daPercent = StringHelperUtils.isNullInt(object[21]);
				if (object[20] != null) {
					BigInteger sevenPcLvl=(BigInteger) object[20];
					payslipModel.setSevenPcLvl(sevenPcLvl.longValue());
					payslipModel.setLvl("S_" + object[20]);
				}

				if (payCommissionCode.equals(700005)) {
					payslipModel.setPayComm("7th Pay Commission");
					model.addAttribute("payComm", "7th Pay Commission");
				} else if (payCommissionCode.equals(700016)) {
					payslipModel.setPayComm("6th Pay Commission");
				}
/*
				BigDecimal basic = (BigDecimal) object[21];
				Double basicPay = basic.doubleValue();*/
				payslipModel.setBasic(StringHelperUtils.isNullDouble(object[21]));
				payslipModel.setVoucherNo(StringHelperUtils.isNullString(object[22]));
				payslipModel.setVoucherDate(StringHelperUtils.isNullDate(object[23]));
				BigInteger billNo = (BigInteger)object[24];
				payslipModel.setBillNo(billNo.longValue());
				ltsPayslipModel.add(payslipModel);
			}

		}

		model.addAttribute("ltsPayslipModel", ltsPayslipModel);
		model.addAttribute("ddoCode", messages.getDdoCode());

		for (Object[] objects : lstEmp) {
			savaarthid = objects[0].toString();
			payCommCode = (BigInteger) objects[7];
			daPercent = (Integer) (objects[19]);
			hraPercent = (Integer) (objects[18]);

			allEdpList = payslipReportService.getAllDataForinnernew(savaarthid);
			lstempdetails1 = payslipReportService.getempDetails(payslipReportModel.getSchemeBillgroupId(),
					payslipReportModel.getPaybillYear(), payslipReportModel.getPaybillMonth(), savaarthid);

			// lstEmp[11]=allEdpList;
			allowEdpList.clear();
			deducAgEdpList.clear();
			nongovdeducEdpList.clear();
			PayslipReportModel obj = new PayslipReportModel();
			obj.setDeptalldetNm(StringHelperUtils.isNullString("Basic_Pay"));
			obj.setType(1);
			obj.setDeptallowdeducid(1);
			obj.setTempvalue("0.00");
			obj.setTempempty("0.00");
			allowEdpList.add(obj);

			// for allowances
			allowance = new ArrayList();
			row = new ArrayList();
			for (int i = 1; i <= allowEdpList.size(); i++) {
				row.add(String.valueOf(i));
			}
			Iterator iterator1 = lstempdetails1.iterator();
			Map<String, Double> mapAllowanceSum = new HashMap<>();
			Map<String, Object> map = (Map<String, Object>) iterator1.next();
			row = new ArrayList();
			
			int loopCount = 0;
			for (Iterator iterator = allEdpList.iterator(); iterator.hasNext();) {

				Double amount = 0d;
				PayslipReportModel object = (PayslipReportModel) iterator.next();
				String allname = object.getDeptalldetNm();
				if (object.getDeptalldetNm().toUpperCase().trim() != null) {
					if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
						row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						
						amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
					}
				}

				PayslipReportModel pm = new PayslipReportModel();
				String componentName = object.getDeptalldetNm().toUpperCase().trim().toString();
				if (amount > 0) {
					pm.setSevaarthId(savaarthid);
					pm.setComponentname(object.getDeptalldetNm().replace("_", " "));
					pm.setComponentValue(Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString()));
					pm.setIsType(allEdpList.get(loopCount).getType());
					lstAllowPayslipModel.add(pm);
				} else {
					pm.setSevaarthId(savaarthid);
					pm.setComponentname(allname.replace("_", " "));
					pm.setComponentValue(0d);
					pm.setIsType(allEdpList.get(loopCount).getType());
					lstAllowPayslipModel.add(pm);
				}
				loopCount++;
			
			}
			allowance.add(mapAllowanceSum);

			// for deductions
			lstdedectionag = new ArrayList<>();
			row = new ArrayList();
			for (int i = 1; i <= deducAgEdpList.size(); i++) {
				row.add(String.valueOf(i));
			}
			Iterator iterator2 = lstempdetails1.iterator();
			Map<String, Double> maplstdedectionagSum = new HashMap<>();
			row = new ArrayList();
			for (Iterator iterator = deducAgEdpList.iterator(); iterator.hasNext();) {

				Double amount = 0d;
				PayslipReportModel object = (PayslipReportModel) iterator.next();
				String allname = object.getDeptalldetNm();
				if (object.getDeptalldetNm().toUpperCase().trim() != null) {
					if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
						row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
					}
				}

				
			}
			lstdedectionag.add(maplstdedectionagSum);

			lstnonGovdedection = new ArrayList();

			// for non government deductions

			row = new ArrayList();
			for (int i = 1; i <= nongovdeducEdpList.size(); i++) {
				row.add(String.valueOf(i));
			}

			Iterator iterator3 = lstempdetails1.iterator();

			Map<String, Double> maplstnonGovdedectionSum = new HashMap<>();
			row = new ArrayList();
			for (Iterator iterator = nongovdeducEdpList.iterator(); iterator.hasNext();) {

				Double amount = 0d;
				PayslipReportModel object = (PayslipReportModel) iterator.next();
				String allname = object.getDeptalldetNm();
				if (object.getDeptalldetNm().toUpperCase().trim() != null) {
					if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
						row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
						
						mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
					}
				}

				
			}
			lstnonGovdedection.add(maplstnonGovdedectionSum);

			algrosstotal = new ArrayList<>();
			Iterator iteratorgrosstotal = lstempdetails1.iterator();
			Double algrosstotalSum = 0d;

			Map<String, Object> map2 = (Map<String, Object>) iteratorgrosstotal.next();
			algrosstotal.add(map2.get("gross_total_amt").toString());
			algrosstotalSum += Double.parseDouble(map2.get("gross_total_amt").toString());

			alnetamt = new ArrayList<>();
			Iterator iteratornetamt = lstempdetails1.iterator();
			Double alnetamtSum = 0d;

			Map<String, Object> map3 = (Map<String, Object>) iteratornetamt.next();
			alnetamt.add(map3.get("total_net_amt").toString());
			alnetamtSum += Double.parseDouble(map3.get("total_net_amt").toString());

			dedtotal = new ArrayList<>();
			Iterator iteratordedtotal = lstempdetails1.iterator();
			Double dedtotalSum = 0d;

			Map<String, Object> map4 = (Map<String, Object>) iteratordedtotal.next();
			dedtotal.add(map4.get("total_deduction").toString());
			dedtotalSum += Double.parseDouble(map4.get("total_deduction").toString());

			String SalaryInWords = PayslipReportController.convertToIndianCurrency(alnetamtSum.toString());

			System.out.println(SalaryInWords + " 888888888888866663258");

			model.addAttribute("lstEmp", lstEmp);
			model.addAttribute("algrosstotalSum", algrosstotal);
			model.addAttribute("dedtotalSum", dedtotal);
			model.addAttribute("alnetamtSum", alnetamt);
			model.addAttribute("payCommCode", payCommCode);
			model.addAttribute("lstdedectionag", lstdedectionag);
			model.addAttribute("lstnonGovdedection", lstnonGovdedection);
			model.addAttribute("allowance", allowance);
			model.addAttribute("SalaryInWords", SalaryInWords);

			String monname = "";
			String curryear = "";

			Integer monthId = payslipReportModel.getPaybillMonth();
			Integer yearId = payslipReportModel.getPaybillYear();

			monname = new DateFormatSymbols().getMonths()[monthId - 1];

			model.addAttribute("paySlipHead", "  " + monname + " " + (yearId + 1999));

			alnetamtSum = null;
			SalaryInWords = "";

			payslipReportModel.setLstPayslipModel(lstAllowPayslipModel);

			model.addAttribute("payslipReportModel", payslipReportModel);
		}

		return "views/reports/payslip-report";

	}

	@GetMapping("/viewSingleEmployeePaySlip/{sevaarthId}")
	public String viewSingleEmployeePaySlip(@ModelAttribute("PayslipReportModel") PayslipReportModel payslipReportModel,
			Model model, Locale locale, HttpSession session, @PathVariable String sevaarthId) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
		List<PayslipReportModel> allowEdpList = new ArrayList<PayslipReportModel>();// edpDao.getAllowCompoMpgData(locId);
		List<PayslipReportModel> deducAgEdpList = new ArrayList<PayslipReportModel>();// edpDao.getAGDeducCompoMpgData(locId);
		List<PayslipReportModel> nongovdeducEdpList = new ArrayList<PayslipReportModel>();// edpDao.getnonGovDeducCompoMpgData(locId);

		List<PayslipReportModel> lstAllowPayslipModel = new ArrayList<PayslipReportModel>();// edpDao.getAllowCompoMpgData(locId);
		List<PayslipReportModel> lstDeducPayslipModel = new ArrayList<PayslipReportModel>();// edpDao.getAGDeducCompoMpgData(locId);

		ArrayList row = new ArrayList();
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		Integer payCommCode = 0;

		allowEdpList.clear();
		deducAgEdpList.clear();
		nongovdeducEdpList.clear();
		List<PayslipReportModel> allEdpList = null;
		List<Map<String, Object>> lstempdetails1 = null;
		ArrayList allowance = null;
		ArrayList lstdedectionag = null;
		ArrayList lstnonGovdedection = null;
		ArrayList dedtotal = null;
		ArrayList algrosstotal = null;
		ArrayList alnetamt = null;

		allowEdpList.clear();
		deducAgEdpList.clear();
		nongovdeducEdpList.clear();

		allEdpList = payslipReportService.getAllDataForinnernew(sevaarthId);
		lstempdetails1 = payslipReportService.getempDetails(payslipReportModel.getSchemeBillgroupId(),
				payslipReportModel.getPaybillYear(), payslipReportModel.getPaybillMonth(), sevaarthId);

		List<Object[]> lstEmp = payslipReportService.getEmpDataBySevaarthid(payslipReportModel.getPaybillYear(),
				payslipReportModel.getPaybillMonth(), sevaarthId);

		List<PayslipModel> ltsPayslipModel = new ArrayList<>();

		if (lstEmp.size() > 0) {
			ltsPayslipModel.clear();
			for (Object object[] : lstEmp) {
				PayslipModel payslipModel = new PayslipModel();
				payslipModel.setSevaarthId(StringHelperUtils.isNullString(object[0]));
				payslipModel.setEmpName(StringHelperUtils.isNullString(object[1]));
				payslipModel.setDesgnName(StringHelperUtils.isNullString(object[2]));

				if (object[3] != null)
					payslipModel.setDoj(StringHelperUtils.isNullDate(object[3]));

				if (object[4] != null)
					payslipModel.setDob(StringHelperUtils.isNullDate(object[4]));

				payslipModel.setMobileNo1(StringHelperUtils.isNullBigDecimal(object[5]));

				payslipModel.setPanNo(StringHelperUtils.isNullString(object[6]));
				BigInteger payComm = (BigInteger) object[7];
				payslipModel.setPayCommisionCode(payComm.longValue());
				payslipModel.setUidNo(StringHelperUtils.isNullString(object[8]));
				payslipModel.setIfsc(StringHelperUtils.isNullString(object[9]));

				payslipModel.setBankAccNo(StringHelperUtils.isNullBigInteger(object[10]));
				payslipModel.setDdoCode(StringHelperUtils.isNullString(object[11]));

				payslipModel.setGrossTotalAmt(StringHelperUtils.isNullDouble(object[12]));
				payslipModel.setTotalDeduction(StringHelperUtils.isNullDouble(object[13]));
				payslipModel.setTotalNetAmt(StringHelperUtils.isNullDouble(object[14]));

				if (object[14] != null)
					payslipModel.setNetAmtInWord(convertToIndianCurrency(object[14].toString()));

				payslipModel.setSubDeptNameEn(StringHelperUtils.isNullString(object[16]));

				payslipModel.setGpfNdcpsNo(StringHelperUtils.isNullString(object[17]));

				ltsPayslipModel.add(payslipModel);
			}
		}

		model.addAttribute("ltsPayslipModel", ltsPayslipModel);

		// lstEmp[11]=allEdpList;
		allowEdpList.clear();
		deducAgEdpList.clear();
		nongovdeducEdpList.clear();
		PayslipReportModel obj = new PayslipReportModel();
		obj.setDeptalldetNm(StringHelperUtils.isNullString("Basic_Pay"));
		obj.setType(1);
		obj.setDeptallowdeducid(1);
		obj.setTempvalue("0.00");
		obj.setTempempty("0.00");
		allowEdpList.add(obj);

		for (int i = 0; i < allEdpList.size(); i++) {
			if (allEdpList.get(i).getType() == 1) { // allowance

				allowEdpList.add(allEdpList.get(i));
			} else if (allEdpList.get(i).getType() == 2 || allEdpList.get(i).getType() == 4) {
				deducAgEdpList.add(allEdpList.get(i));
			} else {
				nongovdeducEdpList.add(allEdpList.get(i));
			}
		}

		// for allowances
		allowance = new ArrayList();
		row = new ArrayList();
		for (int i = 1; i <= allowEdpList.size(); i++) {
			row.add(String.valueOf(i));
		}
		Iterator iterator1 = lstempdetails1.iterator();
		Map<String, Double> mapAllowanceSum = new HashMap<>();
		Map<String, Object> map = (Map<String, Object>) iterator1.next();
		row = new ArrayList();
		for (Iterator iterator = allowEdpList.iterator(); iterator.hasNext();) {

			Double amount = 0d;
			PayslipReportModel object = (PayslipReportModel) iterator.next();
			String allname = object.getDeptalldetNm();
			if (object.getDeptalldetNm().toUpperCase().trim() != null) {
				if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
					row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
				}
			}

			/*
			 * PayslipReportModel pm=new PayslipReportModel();
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("SVN_PC_DA")) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(1); }
			 * 
			 * } if(object.getDeptalldetNm().toUpperCase().trim().equals("DA") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(1); }
			 * 
			 * } if(object.getDeptalldetNm().toUpperCase().trim().equals("HRA") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(1); }
			 * 
			 * } lstAllowPayslipModel.add(pm);
			 */

			PayslipReportModel pm = new PayslipReportModel();
			String componentName = object.getDeptalldetNm().toUpperCase().trim().toString();
			switch (componentName) {
			case "BASIC_PAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "WA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DA.DIFF":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CLA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SVN_PC_DA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HRA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NAXAL_AREA_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SPCL_DUTY_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ADD_HRA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BASIC_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "UA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CONTRI_PROV_FUND":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TRANS_ALLOW_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TRIBAL_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "LF":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NCA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PTA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPS_EMPR_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CONVEY_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DA_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DA_ON_TA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "FPA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ADD_PAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HILL_STATION_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HRA5TH":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HRA6TH":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TA6TH":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TA5TH":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ARREARS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DEPUTATION_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "TRACER_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NAKSALIED_ALLOW":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "LEAVE_PAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_ARREARS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SPECIAL_PAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NDCPS_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(1);
					lstAllowPayslipModel.add(pm);
				}
				break;

			}
		}
		allowance.add(mapAllowanceSum);

		// for deductions
		lstdedectionag = new ArrayList<>();
		row = new ArrayList();
		for (int i = 1; i <= deducAgEdpList.size(); i++) {
			row.add(String.valueOf(i));
		}
		Iterator iterator2 = lstempdetails1.iterator();
		Map<String, Double> maplstdedectionagSum = new HashMap<>();
		row = new ArrayList();
		for (Iterator iterator = deducAgEdpList.iterator(); iterator.hasNext();) {

			Double amount = 0d;
			PayslipReportModel object = (PayslipReportModel) iterator.next();
			String allname = object.getDeptalldetNm();
			if (object.getDeptalldetNm().toUpperCase().trim() != null) {
				if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
					row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
				}
			}

			/*
			 * PayslipReportModel pm=new PayslipReportModel();
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("CM_FUND_AC_INS")) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(2); } }
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("EXCESS_PAYMENT") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(2); } }
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("PT") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(2); } }
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("GIS") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(2); } }
			 * if(object.getDeptalldetNm().toUpperCase().trim().equals("INCOME_TAX") ) {
			 * if(amount>0) { pm.setSevaarthId(savaarthid); pm.setComponentname(allname);
			 * pm.setComponentValue(amount); pm.setIsType(2); } }
			 * lstAllowPayslipModel.add(pm);
			 */

			PayslipReportModel pm = new PayslipReportModel();
			String componentName = object.getDeptalldetNm().toUpperCase().trim().toString();
			switch (componentName) {
			case "CM_FUND_AC_INS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "INCOME_TAX":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EXCESS_PAYMENT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GIS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMP_DCPS_DA_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMP_DCPS_REGULAR_RECOVERY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMP_DCPS_DELAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMP_DCPS_PAY_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMPLOYER_DCPS_REGULAR_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMPLOYER_DCPS_PAY_ARREARS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMPLOYER_DCPS_DELAYED_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EMPLOYER_DCPS_DA_ARREARS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPS_EMPR_DEDUCT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_VEH_ADV_INT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "FA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("fa_inst") != null) {
						pm.setInstallment(map.get("fa_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;

			case "CA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HBA_HOUSE_INT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("hba_house_inst") != null) {
						pm.setInstallment(map.get("hba_house_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HBA_HOUSE":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("hba_house_inst") != null) {
						pm.setInstallment(map.get("hba_house_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_VEH_ADV":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("other_veh_adv_inst") != null) {
						pm.setInstallment(map.get("other_veh_adv_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "COMP_ADV":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("comp_adv_inst") != null) {
						pm.setInstallment(map.get("comp_adv_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "Revenue_Stamp":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GIS_ZP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_ABC_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_D_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_GRP_ABC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_GRP_D":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS_DA_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS_DELAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS_PAY_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HRR_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HRR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_DEDUCT_ADD_TREASURY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_DEDUCT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PT_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SERV_CHARG":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS_REGULAR_RECOVERY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GROUP_ACC_POLICY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ADJUST_DCPS_EMPLOYER":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPS_EMP_CONTRI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPS_EMPR_CONTRI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DCPS_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CO_HSG_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ROP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EXC_PAYRC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_ADV_GRP_D":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_ADV_GRP_ABC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(4);
					if (map.get("other_veh_adv_inst") != null) {
						pm.setInstallment(map.get("other_veh_adv_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BEGIS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_SUBSCRIPTION":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_INST":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HBA_HOUSE_INT_AMT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("hba_house_inst") != null) {
						pm.setInstallment(map.get("hba_house_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SOCIETY_OR_BANK_LOAN":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NDCPS_SUBSCRIPTION":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_SPECIAL_ARR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_ADVANCE":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("gpf_advance_inst") != null) {
						pm.setInstallment(map.get("gpf_advance_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BLWF":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "LIC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_DA_SUB":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PAY_FIX_DIFF":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					if (map.get("pay_fixadv_diff_inst") != null) {
						pm.setInstallment(map.get("pay_fixadv_diff_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NPS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ACCIDENTIAL_POLICY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "RECOVERY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(2);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "EXCESS_PAY_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(4);
					if (map.get("excess_pay_rec_int") != null) {
						pm.setInstallment(map.get("excess_pay_rec_int").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			}
		}
		lstdedectionag.add(maplstdedectionagSum);

		lstnonGovdedection = new ArrayList();

		// for non government deductions

		row = new ArrayList();
		for (int i = 1; i <= nongovdeducEdpList.size(); i++) {
			row.add(String.valueOf(i));
		}

		Iterator iterator3 = lstempdetails1.iterator();

		Map<String, Double> maplstnonGovdedectionSum = new HashMap<>();
		row = new ArrayList();
		for (Iterator iterator = nongovdeducEdpList.iterator(); iterator.hasNext();) {

			Double amount = 0d;
			PayslipReportModel object = (PayslipReportModel) iterator.next();
			String allname = object.getDeptalldetNm();
			if (object.getDeptalldetNm().toUpperCase().trim() != null) {
				if (map.get(object.getDeptalldetNm().toLowerCase().trim()) != null) {
					row.add(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					amount = Double.parseDouble(map.get(object.getDeptalldetNm().toLowerCase().trim()).toString());
					mapAllowanceSum.put(object.getDeptalldetNm().toUpperCase().trim(), amount.doubleValue());
				}
			}

			PayslipReportModel pm = new PayslipReportModel();
			String componentName = object.getDeptalldetNm().toUpperCase().trim().toString();
			switch (componentName) {
			case "OTHER_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(4);
					if (map.get("other_veh_adv_inst") != null) {
						pm.setInstallment(map.get("other_veh_adv_inst").toString());
					}
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CREDIT_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "COP_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MISC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "OTHER_DEDUCTION":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "RECURRING_DEP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
			case "LICENSE_FEE":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "AKOLA_PARI_ABHIYANI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ALLIED_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CHIPLUN_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ULHASNAGAR_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ENGR_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PUBLIC_HEALTH_WORKS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SINDHUDURG_ORAS":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALGAON_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALGAON_HANDICAP_SOCI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ZP_KARMCHARI_PAT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "VIDHARBHA_GRAMIN_KOKAN_BN":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "CHANDA_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BHAGSHREE_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALSEVA_SOC_NAG":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BHANDARA_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GDCC_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GONDIA_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ALLAHABAD_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NAGPUR_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BHAN_DIST_CENT_COP_BNK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BANK_OF_BARORA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "COURT_COMPUTATION":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALGAON_GS_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALDHARA_SOC_CL3":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DHULE_NANDURBAR_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PARISAR_ABHI_SOC_NASHIK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SARW_AROY_BAN_SOCI_DHULE":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GOVT_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SANGLI_SAL_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MJP_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NASHIK_BANK_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MANDA_NASHIK_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALSEVA_MALEGAON_SOC_CL3":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "UJWALA_MAHILA_PAT_BHAND":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BC_QUARTER":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;

			case "FLAG_DAY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "BHAND_JIL_ABHI_KARM_PAT":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALSEVA_KARM_SAHA_PATH":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SOCIETY_NANDED":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SOCIETY_AURANGABAD":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SOCIETY_LATUR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MAHA_LAB_WELFARE_FUND":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MJP_SOC_LATUR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALBHAVAN_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MJP_SOC_SOLAPUR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MLWF_ONLYMJP":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PANIPURVTHA_SOC_CL3OR4":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MANTRALAYA_SOCI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SATARA_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NASHIK_ROAD_SOC_CL3OR4":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SERV_CHARG":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "GPF_LOAN_REC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MANAHAR_BHAI_MEH_JAL":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "RAJASHRI_SHAHU":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PUBLIC_HEALTH_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "DARYAPUR_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PARSIK_JANATA_SH_VASI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "VANGAON_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PANIPURAVTHA_KOLHAPUR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ENGINEERING_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "RAJASHRISHAHU_GOVBANK_KOLHAPUR":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "AHMEDNAGAR_PARI_ABHISEVA_MARYA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "MJP_SOC_BEED":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALBHAVAN_SOC_SANGLI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SAL_OWNER_SOC_SANGLI":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "AKOLA_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "SHASKIY_PANIPURAVTHA_SOC_SATARA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "HASTANTRIT_PUNE_MAHAN_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALPRADAYA_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "NAGARI_SAHAKAR_PATH_SANSTA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "YAVATMAL_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "ZILHA_PARI_KARMACHARI_PANTSANSTHA_BULDHANA":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JAL_BHAVAN_SOCIETY":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "JALNA_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "AMRAWATI_DIST_ENGG_CREDIT_SOC":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;
			case "PUNE_DIST_CENTRAL_COP_BANK":
				if (amount > 0) {
					pm.setSevaarthId(sevaarthId);
					pm.setComponentname(allname);
					pm.setComponentValue(amount);
					pm.setIsType(3);
					lstAllowPayslipModel.add(pm);
				}
				break;

			}
		}
		lstnonGovdedection.add(maplstnonGovdedectionSum);

		algrosstotal = new ArrayList<>();
		Iterator iteratorgrosstotal = lstempdetails1.iterator();
		Double algrosstotalSum = 0d;

		Map<String, Object> map2 = (Map<String, Object>) iteratorgrosstotal.next();
		algrosstotal.add(map2.get("gross_total_amt").toString());
		algrosstotalSum += Double.parseDouble(map2.get("gross_total_amt").toString());

		alnetamt = new ArrayList<>();
		Iterator iteratornetamt = lstempdetails1.iterator();
		Double alnetamtSum = 0d;

		Map<String, Object> map3 = (Map<String, Object>) iteratornetamt.next();
		alnetamt.add(map3.get("total_net_amt").toString());
		alnetamtSum += Double.parseDouble(map3.get("total_net_amt").toString());

		dedtotal = new ArrayList<>();
		Iterator iteratordedtotal = lstempdetails1.iterator();
		Double dedtotalSum = 0d;

		Map<String, Object> map4 = (Map<String, Object>) iteratordedtotal.next();
		dedtotal.add(map4.get("total_deduction").toString());
		dedtotalSum += Double.parseDouble(map4.get("total_deduction").toString());
		String SalaryInWords = PayslipReportController.convertToIndianCurrency(alnetamt.toString());

		System.out.println(SalaryInWords + " 888888886666666888888");

		model.addAttribute("algrosstotalSum", algrosstotal);
		model.addAttribute("dedtotalSum", dedtotal);
		model.addAttribute("alnetamtSum", alnetamt);
		model.addAttribute("SalaryInWords", SalaryInWords);
		model.addAttribute("payCommCode", payCommCode);
		model.addAttribute("lstdedectionag", lstdedectionag);
		model.addAttribute("lstnonGovdedection", lstnonGovdedection);
		model.addAttribute("allowance", allowance);

		SalaryInWords = null;

		payslipReportModel.setLstPayslipModel(lstAllowPayslipModel);

		String monname = "";
		String curryear = "";

		Integer monthId = payslipReportModel.getPaybillMonth();
		Integer yearId = payslipReportModel.getPaybillYear();

		monname = new DateFormatSymbols().getMonths()[monthId - 1];

		model.addAttribute("paySlipHead",
				"Maharashtra Jeevan Pradhikaran PaySlip for the Month of " + monname + " " + (yearId + 1999));

		model.addAttribute("payslipReportModel", payslipReportModel);
		// }

		return "views/reports/payslip-report";

	}

	public static String convertToIndianCurrency(String num) {
		BigDecimal bd = new BigDecimal(num);
		long number = bd.longValue();
		long no = bd.longValue();
		int decimal = (int) (bd.remainder(BigDecimal.ONE).doubleValue() * 100);
		int digits_length = String.valueOf(no).length();
		int i = 0;
		ArrayList<String> str = new ArrayList<>();
		HashMap<Integer, String> words = new HashMap<>();
		words.put(0, "");
		words.put(1, "One");
		words.put(2, "Two");
		words.put(3, "Three");
		words.put(4, "Four");
		words.put(5, "Five");
		words.put(6, "Six");
		words.put(7, "Seven");
		words.put(8, "Eight");
		words.put(9, "Nine");
		words.put(10, "Ten");
		words.put(11, "Eleven");
		words.put(12, "Twelve");
		words.put(13, "Thirteen");
		words.put(14, "Fourteen");
		words.put(15, "Fifteen");
		words.put(16, "Sixteen");
		words.put(17, "Seventeen");
		words.put(18, "Eighteen");
		words.put(19, "Nineteen");
		words.put(20, "Twenty");
		words.put(30, "Thirty");
		words.put(40, "Forty");
		words.put(50, "Fifty");
		words.put(60, "Sixty");
		words.put(70, "Seventy");
		words.put(80, "Eighty");
		words.put(90, "Ninety");
		String digits[] = { "", "Hundred", "Thousand", "Lakh", "Crore" };
		while (i < digits_length) {
			int divider = (i == 2) ? 10 : 100;
			number = no % divider;
			no = no / divider;
			i += divider == 10 ? 1 : 2;
			if (number > 0) {
				int counter = str.size();
				String plural = (counter > 0 && number > 9) ? "s" : "";
				String tmp = (number < 21) ? words.get(Integer.valueOf((int) number)) + " " + digits[counter] + plural
						: words.get(Integer.valueOf((int) Math.floor(number / 10) * 10)) + " "
								+ words.get(Integer.valueOf((int) (number % 10))) + " " + digits[counter] + plural;
				str.add(tmp);
			} else {
				str.add("");
			}
		}

		Collections.reverse(str);
		String Rupees = String.join(" ", str).trim();

		// Rupees=capitalizeWord(Rupees.toUpperCase());

		String paise = (decimal) > 0
				? " And Paise " + words.get(Integer.valueOf(decimal - decimal % 10)) + " "
						+ words.get(Integer.valueOf(decimal % 10))
				: "";
		return "In Word :" + Rupees + paise + " Rupees";

	}

	/*
	 * public static String capitalizeWord(String str){ String
	 * words[]=str.split("\\s"); String capitalizeWord=""; for(String w:words){
	 * String first=w.substring(0,1); String afterfirst=w.substring(1);
	 * capitalizeWord+=first.toUpperCase()+afterfirst+" "; } return
	 * capitalizeWord.trim(); }
	 */
	@GetMapping(value = "/CheckPaybill/{billNumber}/{monthName}/{yearName}")
	public ResponseEntity<String> CheckPaybill(@PathVariable Long billNumber, @PathVariable int monthName,
			@PathVariable int yearName, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<Object[]> paybillgen = regularReportService.findpaybill(billNumber, monthName, yearName,
				messages.getDdoCode());
		Integer existingData = paybillgen.size();
		String resJson = existingData.toString();
		return ResponseEntity.ok(resJson);
	}

}
