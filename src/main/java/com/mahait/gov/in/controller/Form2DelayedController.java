package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddoast")
@Controller
public class Form2DelayedController {
	@GetMapping("/form2Delayed")
	public String form2Delayed( Model model, Locale locale,
			HttpSession session) {
		return "/views/form2-delayed";
	}
	@GetMapping("/form2DelayedReport")
	public String form2DelayedReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-delayed-report";
	}

}
