package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.mahait.gov.in.common.UserSessionObject;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.ChangePasswordModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.WelcomeService;

@RestController
@EnableJdbcHttpSession
public class LoginController extends BaseController{

	// private final Logger logger=LoggerFactory.getLogger(getClass());
	// private static final Logger logger = (Logger)
	// LogManager.getLogger(LoginController.class);
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	WelcomeService welcomeService;
	
	
	//@Autowired
	//UserSessionObject  userSessionObject;

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
		List<Object[]> retriveUserdetails = commonHomeMethodsService.retriveUserdetails(messages.getUserId());
		if (retriveUserdetails.size() > 0) {
			for (Object[] obj : retriveUserdetails) {
				session.setAttribute("ddoCode", obj[0]);
				session.setAttribute("locationId", obj[1]);
				session.setAttribute("loggedInPost", obj[2]);
				
				messages.setLocId(Long.valueOf(obj[1].toString()));
				messages.setPostId(Long.valueOf(obj[2].toString()));
				
				request.getSession().setAttribute("MY_SESSION_MESSAGES",messages);
				
			}
		}
		
		addMenuAndSubMenu(modelAndView,messages);
		
		
		modelAndView.addObject("sessionMessages", messages.getMstRoleEntity().getRoleId());
		// logger.info(""+messages.getFullName());
		modelAndView.addObject("userName", messages.getUserName());
		int levelRoleVal = messages.getMstRoleEntity().getRoleId();

		//userSessionObject.setSession(messages.getUserId(),session);
		session.setAttribute("levelRoleVal", messages.getMstRoleEntity().getRoleId());
/*
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());

		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
*/
		modelAndView.addObject("levelRoleVal", levelRoleVal);

		if (messages.getUpdatedDate() == null) {
			modelAndView.addObject("changePasswordModel", new ChangePasswordModel());
			modelAndView.setViewName("redirect:/ddoast/changePassword");
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
		
		List<Object[]> retriveUserdetails = commonHomeMethodsService.retriveUserdetails(messages.getUserId());
		if (retriveUserdetails.size() > 0) {
			for (Object[] obj : retriveUserdetails) {
				session.setAttribute("ddoCode", obj[0]);
				session.setAttribute("locationId", obj[1]);
				session.setAttribute("loggedInPost", obj[2]);
				
				messages.setLocId(Long.valueOf(obj[1].toString()));
				messages.setPostId(Long.valueOf(obj[2].toString()));
				
				request.getSession().setAttribute("MY_SESSION_MESSAGES",messages);
			}
		}
		
		addMenuAndSubMenu(modelAndView,messages);
		
		//userSessionObject.setSession(messages.getUserId(),session);
		if (messages.getUpdatedDate() == null) {
			modelAndView.setViewName("redirect:/ddo/changePassword");
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
		
		request.getSession().setAttribute("MY_SESSION_MESSAGES",
				userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		
//		List<Object[]> retriveUserdetails = commonHomeMethodsService.retriveUserdetails(messages.getUserId());
//		if (retriveUserdetails.size() > 0) {
//			for (Object[] obj : retriveUserdetails) {
//				session.setAttribute("ddoCode", obj[0]);
//				session.setAttribute("locationId", obj[1]);
//				session.setAttribute("loggedInPost", obj[2]);
//			}
//		}
		
		addMenuAndSubMenu(modelAndView,messages);

	//	userSessionObject.setSession(messages.getUserId(),session);
		modelAndView.setViewName("topics");

		return modelAndView;

	}

	
	
	//	List<Object[]> lstObject=commonHomeMethodsService.retriveUserdetails(userId);
		
		//if(lstObject) {
			
	//	}
		
		
/*		select a.ddo_code,a.location_code,a.post_id,d.post_detail_id
		from org_ddo_mst a inner join org_post_mst b on a.location_code=b.location_code
		inner join org_user_mst c on c.user_name=a.ddo_code inner join org_post_details_rlt d
		 on d.post_id=b.post_id*/
		
		
	/*	departmentVO: sudhircom.tcs.sgv.ess.valueobject.OrgDepartmentMst@5790ce02
		empId: sudhir881309061
		loggedInUserStateName: sudhirMaharashtra
		lastLoginDateTime: sudhir06 Jul, 24 00:00
		empFname: sudhirDr. RAAMAA A. BHOSLAY
		loginId: sudhir99100002889123
		loginVO: sudhircom.tcs.sgv.acl.login.valueobject.LoginDetails@245d8614
		departmentCode: sudhir100007
		locationVO: sudhircom.tcs.sgv.common.valueobject.CmnLocationMst@5fc98f5a
		loggedinPostDetailsRlt: sudhircom.tcs.sgv.ess.valueobject.OrgPostDetailsRlt@65e06c9a
		loggedInPostVO: sudhircom.tcs.sgv.ess.valueobject.OrgPostMst@1ffd32a9
		localeObj: sudhiren_US
		ClientIPAddress: sudhir127.0.0.1
		adminLoginFlag: sudhirfalse
		empLname: sudhir
		primaryPostDetailsRlt: sudhircom.tcs.sgv.ess.valueobject.OrgPostDetailsRlt@65e06c9a
		currLoginDateTime: sudhir07 Jul, 24 00:00
		locationId: sudhir2001416
		dbId: sudhir99
		loginName: sudhir1309002205
		activeDesignations: sudhirnull
		loggedInUserCountryCode: sudhir1
		postDtlsList: sudhir[com.tcs.sgv.ess.valueobject.OrgPostDetailsRlt@3630bad2]
		primaryPostId: sudhir881309061
		designationVO: sudhirnull
		postIdList: sudhir[com.tcs.sgv.ess.valueobject.OrgPostMst@2b5f4397]
		empPrefix: sudhir
		userId: sudhir881309061
		loggedInUserStateCode: sudhir15
		userRoles: sudhir[]
		postDtlsListSize: sudhirnot_allow
		minDsgnForLoggedInPost: sudhircom.tcs.sgv.ess.valueobject.OrgDesignationMst@7f1e70b6
		loggedInPost: sudhir881309061
		langId: sudhir1
		loggedInUserCountryName: sudhirIndia*/

}