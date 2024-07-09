package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/mdc")
@Controller
public class PaybillGenerationReportController {

	@GetMapping("/paybillGeneration")
	public String paybillGeneration(Model model, Locale locale, HttpSession session) {
		return "/views/paybill-generation-report";
	}

	@GetMapping("/treasuryDDOWiseReport")
	public String treasuryDDOWiseReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/treasury-ddo-wise-report";
	}
	
}
