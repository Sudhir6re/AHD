package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
@RequestMapping("/mdc")
@Controller
public class EmpDistWiseValidationReportController  extends BaseController{

	@GetMapping("/empDistWiseSearch")
	public String empDistWiseSearch(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/emp-dist-wise-val-search";
	}
	@GetMapping("/empDistWiseValReport")
	public String empDistWiseValReport(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/reports/emp-dist-wise-val-report";
	}
}
