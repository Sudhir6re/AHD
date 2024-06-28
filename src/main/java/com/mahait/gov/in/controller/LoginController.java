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
public class LoginController {

	// private final Logger logger=LoggerFactory.getLogger(getClass());
	// private static final Logger logger = (Logger)
	// LogManager.getLogger(LoginController.class);
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	WelcomeService welcomeService;

	/* Operator Home Page */
	@RequestMapping("/ddoast/home")
	public ModelAndView getAllUserTopics(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/* modelAndView.addObject("usertopics", topicService.getAllTopics()); */
		modelAndView.addObject("language", locale.getLanguage());

		/* Setting User Session in an application */
		/*
		 * logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode")
		 * );
		 */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*
		 * request.getSession().setAttribute("MY_SESSION_MESSAGES",
		 * userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),
		 * applicationAdminMstEntity.getApplicationCode()));
		 */
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		session.setAttribute("roleId", messages.getMstRoleEntity().getRoleId());
		modelAndView.addObject("sessionMessages", messages.getMstRoleEntity().getRoleId());
		// logger.info(""+messages.getFullName());
		modelAndView.addObject("userName", messages.getUserName());
		int levelRoleVal = messages.getMstRoleEntity().getRoleId();

		session.setAttribute("levelRoleVal", messages.getMstRoleEntity().getRoleId());

		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());

		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);

		modelAndView.addObject("levelRoleVal", levelRoleVal);

		if (messages.getUpdatedDate() == null) {

			modelAndView.setViewName("redirect:/master/changePassword");
		} else {

			modelAndView.setViewName("topics");
		}

		return modelAndView;

	}

	/* Moderator Home Page */

	@RequestMapping("/ddo/home")
	public ModelAndView getModeratorLogin(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/* modelAndView.addObject("usertopics", topicService.getAllTopics()); */
		modelAndView.addObject("language", locale.getLanguage());

		/* Setting User Session in an application */
		/*
		 * logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode")
		 * );
		 */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		if (messages.getUpdatedDate() == null) {

			modelAndView.setViewName("redirect:/master/changePassword");
		} else {

			modelAndView.setViewName("topics");
		}

		return modelAndView;

	}

	/* Super Admin Home Page */
	@RequestMapping("/super/home")
	public ModelAndView getSuperHomePage(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/* modelAndView.addObject("usertopics", topicService.getAllTopics()); */
		modelAndView.addObject("language", locale.getLanguage());

		/* Setting User Session in an application */
		/*
		 * logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode")
		 * );
		 */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));

		modelAndView.setViewName("topics");

		return modelAndView;

	}

	/* Super Admin Home Page */
	@RequestMapping("/user/home")
	public ModelAndView mdcHomePage(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/* modelAndView.addObject("usertopics", topicService.getAllTopics()); */
		modelAndView.addObject("language", locale.getLanguage());

		
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		modelAndView.setViewName("topics");

		return modelAndView;

	}

}