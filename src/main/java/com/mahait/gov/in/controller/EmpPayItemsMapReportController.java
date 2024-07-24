package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddoast")
@Controller
public class EmpPayItemsMapReportController {
	@GetMapping("/empPayItemsMap")
	public String empPayItemsMap(Model model, Locale locale, HttpSession session) {
		return "/views/reports/emp-pay-items-map";
	}
	@GetMapping("/empPayItemsMapReport")
	public String empPayItemsMapReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/emp-pay-items-map-report";
	}
}
