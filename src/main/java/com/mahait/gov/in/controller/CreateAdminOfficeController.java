package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.model.ZpRltDdoMapModel;
import com.mahait.gov.in.service.CreateAdminOfficeService;

@RequestMapping("/master")
@Controller
public class CreateAdminOfficeController {

	@Autowired
	CreateAdminOfficeService createAdminOfficeService;

	@GetMapping("/createAdminOffice")
	public String CreateAdminOffice(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<ZpRltDdoMapModel> lstZpRltDdoMapModel = createAdminOfficeService.findAllDdoMappedlist(messages);
		List<ZpAdminNameMst> lstZpAdminNameMst = createAdminOfficeService.fetchAllOfficeList(messages);
		model.addAttribute("lstZpRltDdoMapModel", lstZpRltDdoMapModel);
		model.addAttribute("lstZpAdminNameMst", lstZpAdminNameMst);
		return "/views/create-admin-office";
	}

	@GetMapping("/createOffice")
	public String CreateOffice(Model model, Locale locale, HttpSession session) {
		return "/views/create-office";
	}

}
