package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddoast")
@Controller
public class EmpEligibilityforAllowancesDeductionsController {
	@GetMapping("empEligibilityforAllowancesDeductions")
	public String empEligibilityforAllowancesDeductions(Model model, Locale locale, HttpSession session) {
		return "/views/emp-eligibility-for-allow-deduct";
	}
	@GetMapping("allowanDeductBulkEmp")
	public String allowanDeductBulkEmp(Model model, Locale locale, HttpSession session) {
		return "/views/allowance-deduction-to-bulk-emp";
	}
}
