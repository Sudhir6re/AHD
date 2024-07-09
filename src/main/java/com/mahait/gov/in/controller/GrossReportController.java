package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/mdc")
@Controller
public class GrossReportController {

	@GetMapping("/grossReportsearch")
	public String grossReportsearch(Model model, Locale locale, HttpSession session) {
		return "/views/gross-report-search";
	}
	@GetMapping("/grossReport")
	public String grossReport(Model model, Locale locale, HttpSession session) {
		return "/views/reports/gross-report";
	}
}
