package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.mahait.gov.in.model.OrgUserPostEndDateModel;
import com.mahait.gov.in.service.OrgUserPostEndDateService;

@Controller
public class OrgUserPostEndDateController {

	@Autowired
	OrgUserPostEndDateService orgUserPostEndDateService;
	
	
	@GetMapping("/orgUserPostEndDate")
	public String OrgUserPostEndDate(@ModelAttribute("orgUserPostEndDateModel") OrgUserPostEndDateModel orgUserPostEndDateModel,
			 Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");
		model.addAttribute("orgUserPostEndDateModel", orgUserPostEndDateModel);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("language", locale.getLanguage());
		return "/views/mst-OrgUserPostEndDate";
	}
}
