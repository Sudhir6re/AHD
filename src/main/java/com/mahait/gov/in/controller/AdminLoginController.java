package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.WelcomeService;

@RestController
@EnableJdbcHttpSession
@RequestMapping("/mdc")
public class AdminLoginController {

	// private final Logger logger=LoggerFactory.getLogger(getClass());

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	WelcomeService welcomeService;

	@RequestMapping("/home")
	public ModelAndView getAllUserTopics(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("language", locale.getLanguage());
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		modelAndView.addObject("sessionMessages", messages.getUserId());
		modelAndView.addObject("userName", messages.getUserName());
		Integer levelRoleVal = messages.getMstRoleEntity().getRoleId();

		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());

		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);

		model.addAttribute("levelRoleVal", levelRoleVal);

		modelAndView.setViewName("topics");

		return modelAndView;

	}
}