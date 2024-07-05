package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/ddoast")



public class DdoBillGroupAttachDetachController {
	
	
	@RequestMapping(value = "/loadAttachBillGroup", method = { RequestMethod.GET })
	public String loadAttachBillGroup(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {

		String message = (String) model.asMap().get("message");
		

		model.addAttribute("language", locale.getLanguage());
		return "/views/attach_dettach_employee";
	}
	

}
