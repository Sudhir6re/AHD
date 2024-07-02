package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mdc")
@Controller
public class ReportingDDOOfficController {
	@GetMapping("/reportingDDOOffice")
	public String reportingDDOOffice(Model model, Locale locale, HttpSession session) {
		return "/views/reporting-ddo-offic";
	}

	@GetMapping("/definingDdoOffices")
	public String definingDdoOffices(Model model, Locale locale, HttpSession session) {
		return "/views/defining-level2-ddo-offices";
	}

}
