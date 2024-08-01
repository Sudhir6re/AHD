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
public class SchoolDistWiseValidationReportController   extends BaseController{

	@GetMapping("/SchoolDistWiseSearch")
	public String schoolDistWiseSearch(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/school-dist-wise-val-search";
	}
	@GetMapping("/schoolDistWiseValReport")
	public String schoolDistWiseValReport(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/reports/school-dist-wise-val-report";
	}
}
