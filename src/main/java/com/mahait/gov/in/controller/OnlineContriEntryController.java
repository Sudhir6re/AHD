package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OnlineContributionModel;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.OnlineContributionService;
import com.mahait.gov.in.service.RegularReportService;
@RequestMapping("/ddoast")
@Controller
public class OnlineContriEntryController   extends BaseController{
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	RegularReportService regularReportService;
	
	@Autowired
	OnlineContributionService onlineContributionService;
	
	@GetMapping("/onlineContriEntry")
	public String onlineContriEntry( @ModelAttribute("onlineContributionModel") OnlineContributionModel onlineContributionModel,
			Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		model.addAttribute("onlineContributionModel", onlineContributionModel);
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));
		model.addAttribute("ddoNameLst", regularReportService.getDDOName(messages.getDdoCode()));
		model.addAttribute("paymentTypeLst", onlineContributionService.getPaymentTypeLst());
		
		
		return "/views/online-contri-entry";
	}

	@GetMapping("/viewRejectedContri")
	public String viewRejectedContri( Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/view-rejected-contri";
	}

}
