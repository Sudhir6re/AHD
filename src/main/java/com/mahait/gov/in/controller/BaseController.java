package com.mahait.gov.in.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;

public abstract class BaseController {

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	private HttpServletRequest request; // Inject HttpServletRequest

	protected void addMenuAndSubMenu(ModelAndView modelAndView, OrgUserMst messages) {
		Integer levelRoleVal = messages.getMstRoleEntity().getRoleId();
		if (levelRoleVal != null && levelRoleVal != 0) {
			List<TopicModel> menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, "en");
			List<TopicModel> subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, "en");

			if (modelAndView != null) {
				modelAndView.addObject("menuList", menuList);
				modelAndView.addObject("subMenuList", subMenuList);
			}
		}
		modelAndView.addObject("levelRoleVal", levelRoleVal);
		modelAndView.addObject("today", new Date());
		modelAndView.addObject("appRootPath", getAppRootPath());
	}

	protected void addMenuAndSubMenu(Model model, OrgUserMst messages) {
		Integer levelRoleVal = messages.getMstRoleEntity().getRoleId();
		if (levelRoleVal != null && levelRoleVal != 0) {
			List<TopicModel> menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, "en");
			List<TopicModel> subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, "en");
			model.addAttribute("menuList", menuList);
			model.addAttribute("subMenuList", subMenuList);
		}
		model.addAttribute("levelRoleVal", levelRoleVal);
		model.addAttribute("today", new Date());
		model.addAttribute("appRootPath", getAppRootPath());
	}

	private String getAppRootPath() {
		ServletContext servletContext = request.getServletContext();
		String appRootPath = servletContext.getContextPath();
		// Determine the scheme (http or https)
		String scheme = request.getScheme(); // "http" or "https"
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		// If context path is empty, build the app root path
		if (appRootPath.isEmpty()) {
			appRootPath = scheme + "://" + serverName + (serverPort == 80 || serverPort == 443 ? "" : ":" + serverPort);
		}
		return appRootPath;
	}
}
