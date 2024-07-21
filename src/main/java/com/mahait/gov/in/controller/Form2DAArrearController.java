package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddoast")
@Controller
public class Form2DAArrearController {
	@GetMapping("/form2DAArrear")
	public String form2DAArrear( Model model, Locale locale,
			HttpSession session) {
		return "/views/form2-daarrear";
	}
	@GetMapping("/form2DAArrearReport")
	public String form2DAArrearReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-daarrear-report";
	}

}
