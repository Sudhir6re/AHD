package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddoast")
@Controller
public class ProfTaxReportController {
	@GetMapping("/profTax")
	public String profTax(Model model, Locale locale, HttpSession session) {
		return "/views/reports/professional-tax";
	}

	@GetMapping("/profTaxReport")
	public String profTaxReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/professional-tax-report";
	}
}
