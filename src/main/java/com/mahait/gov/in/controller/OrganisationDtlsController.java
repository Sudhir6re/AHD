package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.model.OrganisationDtlsModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.OrganisationDtlsService;

@Controller
@RequestMapping(value= {"/ddoast","/ddo"})
public class OrganisationDtlsController {

	@Autowired
	OrganisationDtlsService organisationDtlsService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@RequestMapping("/ddoOfficeDetails")
	public ModelAndView login(Locale locale, HttpSession session, Model model,
			@ModelAttribute("organisationDtlsModel") @Valid OrganisationDtlsModel organisationDtlsModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		// logger.info("For Testing Logger *****");
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		String[] ddo = messages.getUserName().split("_");

		String ddoCode = ddo[0];

		organisationDtlsModel = organisationDtlsService.lstOfficeDetails(ddoCode);

		/*
		 * 
		 * officeMaster = commonHomeMethodsService.lstGetOfficeMaster();
		 */
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		modelAndView.addObject("lstDistrict", commonHomeMethodsService.lstGetAllDistrict());
		// modelAndView.addObject("lstTaluka",
		// commonHomeMethodsService.lstGetAllTaluka());
		// modelAndView.addObject("lstcity", commonHomeMethodsService.lstGetAllCity());
		Map<String, Object> response1 = organisationDtlsService.findDataByDistrict(organisationDtlsModel.getDistrictId());
		if(response1.size()>0) {
			modelAndView.addObject("lsttaluka", response1.get("talukaList"));
			modelAndView.addObject("lstcity", response1.get("cityList"));
		}
		modelAndView.setViewName("/views/organization-details");
		modelAndView.addObject("organisationDtlsModel", organisationDtlsModel);
		modelAndView.addObject("language", locale.getLanguage());
		// model.addAttribute("lstStates", commonHomeMethodsService.lstGetAllState());
		return modelAndView;
	}

	@PostMapping("/saveddoOfficeDetails")
	public String SaveddoOfficeDetails(
			@ModelAttribute("organisationDtlsModel") @Valid OrganisationDtlsModel organisationDtlsModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		
		
		
		if (bindingResult.hasErrors()) {
			return "/views/DdoOffice";
		}
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		Long afterSaveId = organisationDtlsService.SaveorgInstituteInfo(organisationDtlsModel,messages);
		if (afterSaveId > 0) {
			redirectAttributes.addFlashAttribute("message", "SUCCESS");
		}
		return "redirect:/ddoast/ddoOfficeDetails";
	}
	
	

	@RequestMapping(value = "/findDataByDistrict/{districtId}", consumes = {
			"application/json" }, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, Object>> findDataByDistrict(@PathVariable String districtId) {
		Map<String, Object> response1 = organisationDtlsService.findDataByDistrict(districtId);
		return ResponseEntity.ok(response1);
	}
	
	//@{/ddoast/editddoOfficeDetails}

	@RequestMapping("/editddoOfficeDetails")
	public ModelAndView editddoOfficeDetails(Locale locale, HttpSession session, Model model,
			@ModelAttribute("organisationDtlsModel") @Valid OrganisationDtlsModel organisationDtlsModel,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		ModelAndView modelAndView = new ModelAndView();

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		// logger.info("For Testing Logger *****");
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		String[] ddo = messages.getUserName().split("_");

		String ddoCode = ddo[0];

		organisationDtlsModel = organisationDtlsService.lstOfficeDetails(ddoCode);

		/*
		 * 
		 * officeMaster = commonHomeMethodsService.lstGetOfficeMaster();
		 */

		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		modelAndView.addObject("lstDistrict", commonHomeMethodsService.lstGetAllDistrict());
		// modelAndView.addObject("lstTaluka",
		// commonHomeMethodsService.lstGetAllTaluka());
		// modelAndView.addObject("lstcity", commonHomeMethodsService.lstGetAllCity());

		modelAndView.setViewName("/views/edit-organization-details");
		modelAndView.addObject("organisationDtlsModel", organisationDtlsModel);
		modelAndView.addObject("language", locale.getLanguage());
		// model.addAttribute("lstStates", commonHomeMethodsService.lstGetAllState());
		return modelAndView;
	}
	
	@PostMapping("/updateddoOfficeDetails")
	public String updateddoOfficeDetails(@ModelAttribute("organisationDtlsModel") OrganisationDtlsModel organisationDtlsModel, HttpSession session,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale) {
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		organisationDtlsModel.setDdoCode(messages.getUserName());
		int message = organisationDtlsService.updateddoOfficeDetails(organisationDtlsModel, messages);
		if(message>0) {
				redirectAttributes.addFlashAttribute("message","Record Updated Successfully");
			
		}
		
		return "redirect:/ddoast/ddoOfficeDetails"; /*redirects to controller URL*/
	}
}
