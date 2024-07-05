package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.model.HTEEmpLoanModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.HTEEmpLoanService;

@Controller
@RequestMapping("/ddoast")
public class HTEEmpLoanController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	HTEEmpLoanService hTEEmpLoanService;
	
	@GetMapping("/hTEEmpLoan")
	public String hTEEmpLoan(@ModelAttribute("hTEEmpLoanModel") HTEEmpLoanModel hTEEmpLoanModel,
			 Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");
		model.addAttribute("hTEEmpLoanModel", hTEEmpLoanModel);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("language", locale.getLanguage());
		return "/views/mst-EmpLoan";
	}
	@GetMapping("/hTEupdateLoan")
	public String hTEupdateLoan(@ModelAttribute("hTEEmpLoanModel") HTEEmpLoanModel hTEEmpLoanModel,
			Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");
		model.addAttribute("hTEEmpLoanModel", hTEEmpLoanModel);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("language", locale.getLanguage());
		return "/views/mst-EmpwiseLoanDtls";
	}
	@GetMapping("/hTEnewEmpLoan")
	public String hTEnewEmpLoan(@ModelAttribute("hTEEmpLoanModel") HTEEmpLoanModel hTEEmpLoanModel,
			Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");
		model.addAttribute("hTEEmpLoanModel", hTEEmpLoanModel);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("language", locale.getLanguage());
		return "/views/mst-NewEmpLoan";
	}

}
