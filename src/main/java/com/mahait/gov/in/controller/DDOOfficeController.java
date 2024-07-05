package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.model.AnnualIncrementModel;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DDOInfoService;

@Controller
@RequestMapping("/ddo")
public class DDOOfficeController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	DDOInfoService ddoInfoService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/loadApproveDdoOffice")
	public String loadApproveDdoOffice(
			@ModelAttribute("annualIncrementModel") AnnualIncrementModel annualIncrementModel, Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");

		model.addAttribute("language", locale.getLanguage());
		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("language", locale.getLanguage());
		//model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		//model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
		return "/views/ApproveDDOOffice";
	}
	
	}

