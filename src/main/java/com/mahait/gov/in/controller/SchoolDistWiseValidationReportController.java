package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/mdc")
@Controller
public class SchoolDistWiseValidationReportController {

	@GetMapping("/SchoolDistWiseSearch")
	public String schoolDistWiseSearch(Model model, Locale locale, HttpSession session) {
		return "/views/school-dist-wise-val-search";
	}
	@GetMapping("/schoolDistWiseValReport")
	public String schoolDistWiseValReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/school-dist-wise-val-report";
	}
}
