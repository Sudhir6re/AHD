package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.service.GPFAbstractReportService;
import com.mahait.gov.in.service.RegularReportService;

@RequestMapping("/ddoast")
@Controller
public class GPFAbstractReportController  extends BaseController {
	
	@Autowired
	RegularReportService regularReportService;
	
	@Autowired
	GPFAbstractReportService gpfAbstractReportService;
	
	@GetMapping("/gpfAbstract")
	public String gpfAbstract(@ModelAttribute("regularReportModel") RegularReportModel regularReportModel,
			Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));
		
		return "/views/reports/gpf-abstract";
	}
	@GetMapping("/gpfAbstractReport")
	public String gpfAbstractReport(@ModelAttribute("regularReportModel") RegularReportModel regularReportModel,
			Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		
		
		
		
		List<RegularReportModel> gpfRptDtls = gpfAbstractReportService.findgpfRptDtls(regularReportModel.getMonthId(),regularReportModel.getYearId(),regularReportModel.getBillGroup());
		
		model.addAttribute("gpfRptDtls",gpfRptDtls);
		
		return "/views/reports/gpf-abstract-report";
	}
}
