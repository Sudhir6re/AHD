package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddoast")
@Controller
public class Form2PayArrearController {
	@GetMapping("/form2PayArrear")
	public String form2PayArrear( Model model, Locale locale,
			HttpSession session) {
		return "/views/form2-payArrear";
	}
	@GetMapping("/form2PayArrearReport")
	public String form2PayArrearReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-payArrear-report";
	}

}
