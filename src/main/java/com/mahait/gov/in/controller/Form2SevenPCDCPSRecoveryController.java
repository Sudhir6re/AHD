package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddoast")
@Controller
public class Form2SevenPCDCPSRecoveryController {
	@GetMapping("/form2SevenPCDCPSRecovery")
	public String form2SevenPCDCPSRecovery( Model model, Locale locale,
			HttpSession session) {
		return "/views/form2-SevenPC-DCPS-Recovery";
	}
	@GetMapping("/form2SevenPCDCPSRecoveryReport")
	public String form2SevenPCDCPSRecoveryReport( Model model, Locale locale,
			HttpSession session) {
		return "/views/reports/form2-SevenPC-DCPS-Recovery-report";
	}

}
