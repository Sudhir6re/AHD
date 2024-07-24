package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddoast")
@Controller
public class EmpDeatailReportController {
	@GetMapping("/empDeatail")
	public String empDeatail(Model model, Locale locale, HttpSession session) {
		return "/views/reports/employee-details";
	}
	@GetMapping("/empDeatailReport")
	public String empDeatailReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/employee-details-report";
	}
}
