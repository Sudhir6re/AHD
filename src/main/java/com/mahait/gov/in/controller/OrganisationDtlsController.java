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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OrganisationDtlsModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.OrganisationDtlsService;


@Controller
@RequestMapping("/ddoast")
public class OrganisationDtlsController {
	
	@Autowired
	OrganisationDtlsService organisationDtlsService;
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@RequestMapping("/ddoOfficeDetails")
	public ModelAndView login(Locale locale,HttpSession session,Model model,@ModelAttribute("organisationDtlsModel") @Valid OrganisationDtlsModel organisationDtlsModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		    ModelAndView modelAndView = new ModelAndView();
		    
		    OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
//		    logger.info("For Testing Logger *****");
			List<TopicModel> menuList = new ArrayList<>();
			List<TopicModel> subMenuList = new ArrayList<>();
			
			
			//lstOfficeDetails = commonHomeMethodsService.lstOfficeDetails(messages.getUserName());
			
/*
			model.addAttribute("lstDeptDataTable", mstDepartmentService.findAllDepartment().stream()
					.filter(p -> p.getIsActive() == '1')
					.collect(Collectors.toList()));
			List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(),
					messages.getUserName().toString());
			for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
				DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
				officeMasterSaveModel.setParentFieldDepartmentId(ddoScreenModel.getSubDepartmentId());

			}
			model.addAttribute("lstSubDepartment", lstDepartment);
			
			
			
			officeMaster = commonHomeMethodsService.lstGetOfficeMaster();*/
			
			
			modelAndView.addObject("menuList", menuList);
			modelAndView.addObject("subMenuList", subMenuList);
			//modelAndView.addObject("lstDistrict", commonHomeMethodsService.lstGetAllDistrict());
			//modelAndView.addObject("lstTaluka", commonHomeMethodsService.lstGetAllTaluka());
			//modelAndView.addObject("lstcity", commonHomeMethodsService.lstGetAllCity());
			//modelAndView.addObject("lstOfficeDetails", ddoOfficeService.lstGetOfficeDtls(messages.getUserName()));
		    
		    modelAndView.setViewName("/views/organization-details");
		    modelAndView.addObject("organisationDtlsModel", organisationDtlsModel);
		    modelAndView.addObject("language", locale.getLanguage());
		    //model.addAttribute("lstStates", commonHomeMethodsService.lstGetAllState());
		    return modelAndView;
    }
	
	@PostMapping("/saveddoOfficeDetails")
	public String SaveddoOfficeDetails(@ModelAttribute("organisationDtlsModel") @Valid OrganisationDtlsModel organisationDtlsModel,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()) {
			return "/views/DdoOffice"; 
		} 
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		organisationDtlsModel.setDdoCode(messages.getUserName());
		int afterSaveId = organisationDtlsService.SaveorgInstituteInfo(organisationDtlsModel);
		if(afterSaveId > 0) {
			redirectAttributes.addFlashAttribute("message","SUCCESS");
		}
		return "redirect:/ddoast/orgInstituteInfo"; 
	}
	


}
