package com.mahait.gov.in.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.UserLoginHistryService;
import com.mahait.gov.in.service.WelcomeService;

@RestController
@EnableJdbcHttpSession
@RequestMapping("/user")
public class TopicController extends BaseController {

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	WelcomeService welcomeService;

	@Autowired
	private Environment environment;

	@Autowired
	UserLoginHistryService userLoginHistryService;

	@RequestMapping("/login")
	public ModelAndView login(Locale locale, HttpServletRequest request) throws UnknownHostException {
		int id = 1;
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("lstApplicationAdminTable", welcomeService.findApplicationOnebyId(id));
		modelAndView.setViewName("custom-login");

		// Port
		String portNumber = environment.getProperty("server.port");
		String hostAddress = InetAddress.getLocalHost().getHostAddress();
		String hostName = InetAddress.getLocalHost().getHostName();
		InetAddress.getLoopbackAddress().getHostAddress();
		InetAddress.getLoopbackAddress().getHostName();

		return modelAndView;
	}

	@RequestMapping("/home")
	public ModelAndView getAllUserTopics(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("language", locale.getLanguage());
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		session.setAttribute("levelRoleVal", messages.getMstRoleEntity().getRoleId());
		modelAndView.addObject("sessionMessages", messages.getUserId());
		// logger.info(""+messages.getFullName());
		modelAndView.addObject("userName", messages.getUserName());
		int levelRoleVal = messages.getMstRoleEntity().getRoleId();
		if (commonHomeMethodsService.findRole(levelRoleVal).getRoleName().equalsIgnoreCase("ROLE_MDC")) {
			return new ModelAndView("redirect:/admin/home");
		} else if (commonHomeMethodsService.findRole(levelRoleVal).getRoleName().equalsIgnoreCase("ROLE_DDO_AST")) {
			return new ModelAndView("redirect:/ddoast/home");
		} else if (commonHomeMethodsService.findRole(levelRoleVal).getRoleName().equalsIgnoreCase("ROLE_DDO")) {
			return new ModelAndView("redirect:/ddo/home");
		} else if (commonHomeMethodsService.findRole(levelRoleVal).getRoleName().equalsIgnoreCase("ROLE_SUPERADMIN")) {
			return new ModelAndView("redirect:/super/home");
		} else {

			List<TopicModel> menuList = new ArrayList<>();
			List<TopicModel> subMenuList = new ArrayList<>();

			menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
			subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());

			modelAndView.addObject("menuList", menuList);
			modelAndView.addObject("subMenuList", subMenuList);

			modelAndView.addObject("levelRoleVal", levelRoleVal);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			SimpleDateFormat sd = new SimpleDateFormat("MM");

			addMenuAndSubMenu(modelAndView, messages);

			modelAndView.setViewName("homepageLevel");
		}
		return modelAndView;

	}

	@RequestMapping("/logOut")
	public RedirectView logOut(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession(false);
		String ip = null;
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
			UserLoginHistryEntity userLoginHistry = new UserLoginHistryEntity();
			userLoginHistry.setUsername(messages.getUserName());
			userLoginHistry.setLoginIp(ip);
			userLoginHistry.setCreatedDate(new Date());
			userLoginHistry.setLoginDate(new Date());
			userLoginHistry.setCreatedUserId(1);
			userLoginHistry.setIsActive(3);
			userLoginHistry.setStatus("Logout Successful");
			userLoginHistryService.saveLoginDtls(userLoginHistry);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (session != null) {
			session.invalidate();
		}

		return new RedirectView("redirect:/user/login?logout");

	}

	@RequestMapping("/error")
	public ModelAndView error() {
		ModelAndView modelAndView = new ModelAndView();
		String errorMessage = "You are not authorized for the requested data.";
		modelAndView.addObject("errorMsg", errorMessage);
		String sessionExpMessage = "Session Expired, Please click below link to login again !!!";
		modelAndView.addObject("sessionExpMessageMsg", sessionExpMessage);
		modelAndView.setViewName("error");
		return modelAndView;
	}

	@RequestMapping("/underConstruction")
	public ModelAndView underConstruction() {
		ModelAndView modelAndView = new ModelAndView();
		String underConstMessage = "This Page Is Under Constuction !!!";
		modelAndView.addObject("underConstructionMsg", underConstMessage);
		modelAndView.setViewName("under-construction");
		return modelAndView;
	}

	@RequestMapping("/invalidsession")
	public ModelAndView invalidsession() {
		ModelAndView modelAndView = new ModelAndView();
		String sessionExpMessage = "Session Expired, Please click below link to login again !!!";
		modelAndView.addObject("sessionExpMessageMsg", sessionExpMessage);
		modelAndView.setViewName("invalid-session");
		return modelAndView;
	}
}