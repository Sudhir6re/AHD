package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.EmpLoanModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.EmployeeLoanDetailsService;

@RequestMapping("/ddoast")
@Controller


public class EmployeeLoanDetailsController  extends BaseController{
	

@Autowired
CommonHomeMethodsService commonHomeMethodsService;

@Autowired
EmployeeLoanDetailsService employeeLoanDetailsService;
	
	@GetMapping("employeeLoanDetails")
	public String addNewEmpDetails(@ModelAttribute("empLoanModel") EmpLoanModel empLoanModel,Model model, Locale locale, HttpSession session){
		String message = (String) model.asMap().get("message");{
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		
		addMenuAndSubMenu(model,messages);
		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
	
		
//		model.addAttribute("lstCommonMstLoanAndAdvance",
//				commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.GPFLOAN_ADVANCE));
				
		List<EmpLoanModel> lstEmpLoanModel=new ArrayList<>();
		
	 	lstEmpLoanModel=employeeLoanDetailsService.findAllEmpLoanDtls(messages.getDdoCode());
		
		model.addAttribute("lstEmpLoanModel", lstEmpLoanModel);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("standardDate", new Date());
		
		return "/views/emp-loan-details";
	}
	}
	@GetMapping("addLoan")
	public String addLoan(@ModelAttribute("empLoanModel") EmpLoanModel empLoanModel,Model model, Locale locale, HttpSession session){
		String message = (String) model.asMap().get("message");
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
		
		List<EmpLoanModel> lstEmpLoanModel=new ArrayList<>();
		
		
		lstEmpLoanModel=employeeLoanDetailsService.findAllEmpLoanDtls(messages.getUserName());
		
		model.addAttribute("lstEmpLoanModel", lstEmpLoanModel);
		
		model.addAttribute("language", locale.getLanguage());
		
		model.addAttribute("standardDate", new Date());
		return "/views/add-loan";
	
	}
}
