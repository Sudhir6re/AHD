package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.MstMenuModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.MstMenuRoleMappingService;
import com.mahait.gov.in.service.MstMenuService;
import com.mahait.gov.in.service.MstSubMenuService;

@Controller
@RequestMapping("/mdc")
public class ActiveInactivePostController extends BaseController {

	@Autowired
	MstMenuService mstMenuService;

	@Autowired
	MstSubMenuService mstSubMenuService;

	@Autowired
	MstMenuRoleMappingService mstMenuRoleMappingService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@GetMapping("/showPostList")
	public String showPostList(@ModelAttribute("mstMenuModel") MstMenuModel mstMenuModel, Model model, Locale locale,
			HttpSession session) {

		String message = (String) model.asMap().get("message");
		model.addAttribute("mstMenuModel", mstMenuModel);

		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstMenu", commonHomeMethodsService.findAllMenu(locale.getLanguage()));

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);
		return "/views/active-inactive-post";
	}

}
