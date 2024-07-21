package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddoast")
@Controller
public class Form2RegularController {
	@GetMapping("/form2Regular")
	public String form2Regular( Model model, Locale locale,
			HttpSession session) {
		return "/views/form2-regular";
	}
	@GetMapping("/form2RegularReport")
	public String form2RegularReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-regular-report";
	}

}
