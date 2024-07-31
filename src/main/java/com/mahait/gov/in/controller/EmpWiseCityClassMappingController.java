package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddoast")
@Controller
public class EmpWiseCityClassMappingController {
	@GetMapping("/empWiseCityClassMapping")
	public String empWiseCityClassMapping(Model model, Locale locale, HttpSession session) {
		return "/views/emp-wise-city-class-mapping";
	}
}
