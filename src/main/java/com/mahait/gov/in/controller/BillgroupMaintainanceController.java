package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.MstDcpsBillGroup;
import com.mahait.gov.in.entity.MstScheme;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.BillgroupMaintainanceModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DdoBillGroupService;
import com.mahait.gov.in.service.DdoBillGroupServiceImpl;
import com.mahait.gov.in.service.MstSchemeService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;

@Controller
@RequestMapping("/ddoast")

public class BillgroupMaintainanceController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	DdoBillGroupService ddoBillGroupService;
	
	@Autowired
	MstSchemeService mstSchemeService;
	

//	@Autowired
//	MstBillGroupService mstBillGroupService;
//	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@RequestMapping(value = "/billgroupMaintainance") 
	public String billgroupMaintainance(@ModelAttribute("billgroupMaintainanceModel") BillgroupMaintainanceModel billgroupMaintainanceModel,
			Model model, Locale locale, HttpSession session) {

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");	
		Long Postid = messages.getCreatedByPost().getPostId();
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstAllScheme", mstSchemeService.findAllScheme());
		model.addAttribute("lstBillName", ddoBillGroupService.lstBillName());

		return "/views/billgroupMaintainance";
	}

	
	@PostMapping("/saveBillgroupMaintainance")
	public String saveBillgroupMaintainance(@ModelAttribute("billgroupMaintainanceModel") BillgroupMaintainanceModel billgroupMaintainanceModel,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale,HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		int afterSaveId = ddoBillGroupService.saveBillGroupMaintainance(billgroupMaintainanceModel,messages);
		if(afterSaveId > 0) {
			redirectAttributes.addFlashAttribute("message","SUCCESS");
		}
		//model.addAttribute("lstDeptDataTable", mstDepartmentService.findAllDepartment());
		return "redirect:/ddoast/billgroupMaintainance"; /*redirects to controller URL*/
	}
	
	@GetMapping(value="/getSchemeCodeAgainstName/{schemeId}")	// , method = RequestMethod.POST
	public @ResponseBody List<MstScheme> getSchemeCodeAgainstName (@PathVariable  String schemeId , Model model,Locale locale) {
		
		List<MstScheme> mstscheme =  ddoBillGroupService.getSchemeCodeAgainstName(schemeId);
		return mstscheme;
	}
	
	
}
