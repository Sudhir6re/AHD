package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.UpdateDOBModel;
import com.mahait.gov.in.model.UpdatePanNoModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UpdateDOBService;

@Controller
@RequestMapping("/mdc")
public class UpdateDOBController  extends BaseController {
	
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	UpdateDOBService updateDOBService;
	
	List<UpdateDOBModel> emplist = new ArrayList<>();
	
	@GetMapping("/updateDOB")
	public String updateDOB(@ModelAttribute("updateDOBModel") UpdateDOBModel updateDOBModel, Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		String message=(String) model.asMap().get("message");
		
		
		//model.addAttribute("", getDesignation())
    	emplist = updateDOBService.findAllEmployee(messages.getDdoCode());
    	updateDOBModel.setEmplist(emplist);
	
		
    	addMenuAndSubMenu(model,messages);		
		model.addAttribute("updateDOBModel", updateDOBModel);
		model.addAttribute("message", message);
		
		
		
		return "/views/updateDob";
	}
	
	@PostMapping("/saveDOB")
	public String saveDOB(@ModelAttribute("updateDOBModel") @Valid UpdateDOBModel updateDOBModel,HttpSession session,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale) {
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
	
				int afterSaveId = updateDOBService.saveupdatedob(updateDOBModel,messages);
		
				if(afterSaveId > 0) {
					redirectAttributes.addFlashAttribute("message","Record Updated Successfully");
				}
				else
				{
					redirectAttributes.addFlashAttribute("messages","Record Updated Successfully");
				}
				
				return "redirect:/ddoast/updateDOB";
	}

}
