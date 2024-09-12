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

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdateDOBModel;
import com.mahait.gov.in.model.UpdateServiceEndDateModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UpdateDOBService;

@Controller
@RequestMapping("/ddoast")
public class UpdateServiceEndDateController extends BaseController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	UpdateDOBService updateDOBService;
	
	List<UpdateDOBModel> emplist = new ArrayList<>();
	
	@GetMapping("/updateSED")
	public String updateDOB(@ModelAttribute("updateServiceEndDateModel") UpdateServiceEndDateModel updateServiceEndDateModel, Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		String message=(String) model.asMap().get("message");
		
		
		//model.addAttribute("", getDesignation())
    	emplist = updateDOBService.findAllEmployee(messages.getUserName());
    	updateServiceEndDateModel.setEmplist(emplist);
	
		
    	addMenuAndSubMenu(model,messages);		
		model.addAttribute("updateServiceEndDateModel", updateServiceEndDateModel);
		model.addAttribute("message", message);
		
		
		
		return "/views/updateServiceEnd";
	}
	
	
	
	

}
