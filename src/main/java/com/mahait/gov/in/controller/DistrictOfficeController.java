package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mdc")
@Controller
public class DistrictOfficeController {
	@GetMapping("/districtOffice")
	public String districtOffice(Model model, Locale locale, HttpSession session) {
		return "/views/district-office";
	}

	@GetMapping("/districtOfficeMaster")
	public String districtOfficeMaster(Model model, Locale locale, HttpSession session) {
		return "/views/district-office-master";
	}

}
