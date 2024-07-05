package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminOfficeMst;
import com.mahait.gov.in.model.ZpAdminOfficeMstModel;
import com.mahait.gov.in.response.MessageResponse;
import com.mahait.gov.in.service.AdminOfficeMasterService;

@RequestMapping("/mdc")
@Controller
public class AdminOfficeMasterController {

	@Autowired
	AdminOfficeMasterService adminOfficeMasterService;

	@GetMapping("/adminOfficeMaster")
	public String adminOfficeMaster(Model model, Locale locale, HttpSession session,
			@ModelAttribute("zpAdminOfficeMstModel") ZpAdminOfficeMstModel zpAdminOfficeMstModel) {

		String officeCode = adminOfficeMasterService.generateOfficeCode();
		zpAdminOfficeMstModel.setOfficeCode(officeCode);
		model.addAttribute("zpAdminOfficeMst", zpAdminOfficeMstModel);

		MessageResponse messageResponse = (MessageResponse) model.asMap().get("messageResponse");
		if (messageResponse != null) {
			model.addAttribute("messageResponse", messageResponse);
		}
		
		
		String uniqueId= (String) model.asMap().get("uniqueId");
		model.addAttribute("uniqueId", uniqueId);
		
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<ZpAdminOfficeMstModel> lstZpAdminOfficeMstModel = adminOfficeMasterService.findAllZpAdminOfficeMstModel();

		model.addAttribute("lstZpAdminOfficeMstModel", lstZpAdminOfficeMstModel);

		return "/views/admin-office-master";
	}

	/*
	 * @GetMapping("/adminOffice") public String adminOffice(Model model, Locale
	 * locale, HttpSession session) { adminOfficeMasterService.sa return
	 * "/views/admin-office"; }
	 */

	@PostMapping("/createOfficeMaster")
	public String createOfficeMaster(HttpSession session,
			@ModelAttribute("zpAdminOfficeMstModel") ZpAdminOfficeMstModel zpAdminOfficeMstModel,
			RedirectAttributes redirectAttributes) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		zpAdminOfficeMstModel.setCreatedBy(messages.getUserId());
		ZpAdminOfficeMst zpAdminOfficeMst = adminOfficeMasterService.createOffice(zpAdminOfficeMstModel);

		MessageResponse messageResponse = new MessageResponse();
		if (zpAdminOfficeMst != null) {
			messageResponse.setResponse("Office Created Successfully");
			messageResponse.setStyle("alert alert-success");
			messageResponse.setStatusCode(200);
		} else {
			messageResponse.setResponse("Something went wrong");
			messageResponse.setStyle("alert alert-error");
			messageResponse.setStatusCode(200);
		}
		redirectAttributes.addFlashAttribute("messageResponse", messageResponse);
		return "redirect:/mdc/adminOfficeMaster";
	}

	@GetMapping("/findOfficebyId/{officeId}")
	public String findOfficebyId(HttpSession session, @PathVariable Long officeId) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		ZpAdminOfficeMstModel zpAdminOfficeMstModel = adminOfficeMasterService.findOfficebyId(officeId);
		return "redirect:/mdc/adminOfficeMaster";
	}

	@GetMapping("/deleteOfficeById/{officeId}")
	public String deleteOfficeById(HttpSession session, @PathVariable Long officeId) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		Integer isDeleted = adminOfficeMasterService.deleteOfficeById(officeId, messages);
		return "redirect:/mdc/adminOfficeMaster";
	}

}
