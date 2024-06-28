package com.mahait.gov.in.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.mahait.gov.in.entity.UserInfo;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.WelcomeService;

@RestController
@EnableJdbcHttpSession
public class LoginController {
	
//	private final Logger logger=LoggerFactory.getLogger(getClass());
//	private static final Logger logger = (Logger) LogManager.getLogger(LoginController.class);
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	WelcomeService welcomeService;
	
	
	/* Operator Home Page */
	@RequestMapping("/ddoast/home")
	public ModelAndView getAllUserTopics(HttpServletRequest request,Model model, HttpServletResponse response, Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/*modelAndView.addObject("usertopics", topicService.getAllTopics());*/
		modelAndView.addObject("language", locale.getLanguage());
		
		/*Setting User Session in an application*/
		/*logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode") ); */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),applicationAdminMstEntity.getApplicationCode()));*/
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		session.setAttribute("roleId", messages.getRole_id());
		modelAndView.addObject("sessionMessages", messages.getUser_id());
//		logger.info(""+messages.getFullName());
		modelAndView.addObject("userName", messages.getFullName());
		int levelRoleVal = messages.getRole_id();
		
		
		session.setAttribute("levelRoleVal", messages.getRole_id());
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal,locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal,locale.getLanguage());
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		
		modelAndView.addObject("levelRoleVal", levelRoleVal);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		SimpleDateFormat sd =new SimpleDateFormat("MM");
//		modelAndView.setViewName("topics");
		
		Date dat =new Date();
		
		String mon =sd.format(dat);
		String year =sdf.format(dat);
		Long mont =Long.parseLong(mon);
		Long curyear =Long.parseLong(year);
		Long prmonth =0l;
		Long pryer =0l;
		Long monid =0l;
		Long yerid =0l;
		if(mont.equals(1l)) {
			 prmonth=12l;
			 pryer= curyear-1;
			 
		}else {
			prmonth=mont-1;
			pryer=curyear;
		}
		
		 List<Object[]>  monthd = commonHomeMethodsService.findbymonthid(prmonth);
		 for (Object[] objects : monthd) {
			 monid=Long.parseLong(objects[0].toString());
		}
		 
		 List<Object[]> yeard = commonHomeMethodsService.findbyyearid(pryer);
		for (Object[] objects : yeard) {
			 yerid=Long.parseLong(objects[0].toString());
		}
		
		//Total Employees   
		 List<Object[]>  totalemp = commonHomeMethodsService.findtotalemployees(messages.getUserName(),messages.getRole_id());
		 Long Totalem =(long) totalemp.size();
			
		//Actives Employees
		 List<Object[]>  activeemp = commonHomeMethodsService.findactiveemployees(messages.getUserName(),messages.getRole_id());
		 Long activeeml =(long) activeemp.size();
		
				 
		 //Total DDOs
		 List<Object[]>  totalDDOs = commonHomeMethodsService.findtotalDDOs(messages.getUserName(),messages.getRole_id());
		 Long totalDDOS=0l;
//		 System.out.println("-------messages.getRole_id()------"+messages.getRole_id());
		 if(messages.getRole_id()==1) {
			 totalDDOS=1l;
		 }else {
			  totalDDOS =(long) totalDDOs.size(); 	
		 }
		 
		 //Total OFC
		 List<Object[]>  totalOFC = commonHomeMethodsService.findtotalOFCcount(messages.getUserName(),messages.getRole_id());
		 Long totalOFCcount=0l;
		 if(messages.getRole_id()==1) {
			 totalOFCcount=1l;
		 }else {
			 totalOFCcount =(long) totalOFC.size(); 	
		 } 	
		 
		 Long chagestategenerated=0l;
		 Long chagestatefor=0l;
		 Long chagestateapp=0l;
		 Long paybillgenerate=0l;
		 Long forwordedtolevel2=0l;
		 Long consolatedbylevel2=0l;
		 Long forwordedBEAMS=0l;
		 Long acknowledgedBEAMS=0l;
		 Long salarycrediteds=0l;
		 Long appBEAMS=0l;
		 List<Object[]> dashborad = commonHomeMethodsService.finddashbard(messages.getUserName(),messages.getRole_id(),monid,yerid);
			for (Object[] objects : dashborad) {
				 if(objects[0]!=null) {
					 chagestategenerated=Long.parseLong(objects[0].toString());
				 }else {
					 chagestategenerated=0l;
				 }
				 if(objects[1]!=null) {
					 chagestatefor=Long.parseLong(objects[1].toString());
				 }else {
					 chagestatefor=0l;
				 }
				 if(objects[2]!=null) {
						chagestateapp=Long.parseLong(objects[2].toString());
				 }else {
						chagestateapp=0l;
				 }
				 if(objects[3]!=null) {
					 paybillgenerate=Long.parseLong(objects[3].toString());
				 }else {
					 paybillgenerate=0l;
				 }
				 if(objects[4]!=null) {
					 forwordedtolevel2=Long.parseLong(objects[4].toString());
				 }else {
					 forwordedtolevel2=0l;
				 }
				 if(objects[5]!=null) {
					 consolatedbylevel2=Long.parseLong(objects[5].toString());
				 }else {
					 consolatedbylevel2=0l;
				 }
				 if(objects[6]!=null) {
					 forwordedBEAMS=Long.parseLong(objects[6].toString());
				 }else {
					 forwordedBEAMS=0l;
				 }
				 if(objects[7]!=null) {
					 appBEAMS=Long.parseLong(objects[7].toString());
				 }else {
					 appBEAMS=0l;
				 }
				 if(objects[8]!=null) {
					 acknowledgedBEAMS=Long.parseLong(objects[8].toString());
				 }else {
					 acknowledgedBEAMS=0l;
				 }
				 if(objects[9]!=null) {
					 salarycrediteds=Long.parseLong(objects[9].toString());
				 }else {
					 salarycrediteds=0l;
				 }
		 }
		 
			acknowledgedBEAMS=acknowledgedBEAMS+salarycrediteds;
			appBEAMS=appBEAMS+acknowledgedBEAMS;
			forwordedBEAMS=forwordedBEAMS+appBEAMS;
			consolatedbylevel2=consolatedbylevel2+forwordedBEAMS;
			forwordedtolevel2=forwordedtolevel2+consolatedbylevel2;
			paybillgenerate=paybillgenerate+forwordedtolevel2;
			chagestateapp=chagestateapp+paybillgenerate;
			chagestatefor=chagestatefor+chagestateapp;
			chagestategenerated=chagestategenerated+chagestatefor;
		 model.addAttribute("Totalemp", Totalem);
		 model.addAttribute("activeeml", activeeml);
		 model.addAttribute("chagestatefor", chagestatefor);
		 model.addAttribute("changestatementgenerated", chagestategenerated);
		 model.addAttribute("chagestateapp", chagestateapp);
		 model.addAttribute("paybillgenerate", paybillgenerate);
		 model.addAttribute("forwordedtolevel2", forwordedtolevel2);
		 model.addAttribute("consolatedbylevel2", consolatedbylevel2);
		 model.addAttribute("forwordedBEAMS", forwordedBEAMS);
		 model.addAttribute("acknowledgedBEAMS", acknowledgedBEAMS);
		 model.addAttribute("salarycrediteds", salarycrediteds);
		 model.addAttribute("appBEAMS", appBEAMS);
		 model.addAttribute("totalDDOS", totalDDOS);
		 model.addAttribute("totalOFCcount", totalOFCcount);
 if(messages.getUpdatedDate()==null) {
			 
	 modelAndView.setViewName("redirect:/master/changePassword");
		 }else {
			 
			 modelAndView.setViewName("topics");
		 }
	
	return modelAndView;
		
		
    }
	
	/*Moderator Home Page */
	
	@RequestMapping("/ddo/home")
	public ModelAndView getModeratorLogin(HttpServletRequest request,Model model, HttpServletResponse response, Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/*modelAndView.addObject("usertopics", topicService.getAllTopics());*/
		modelAndView.addObject("language", locale.getLanguage());
		
		/*Setting User Session in an application*/
		/*logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode") ); */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),applicationAdminMstEntity.getApplicationCode()));*/
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		
		modelAndView.addObject("sessionMessages", messages.getUser_id());
		modelAndView.addObject("userName", messages.getFullName());
		int levelRoleVal = messages.getRole_id();
		
		
		session.setAttribute("levelRoleVal", messages.getRole_id());
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal,locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal,locale.getLanguage());
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		
		modelAndView.addObject("levelRoleVal", levelRoleVal);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		SimpleDateFormat sd =new SimpleDateFormat("MM");
//		modelAndView.setViewName("topics");
		
		Date dat =new Date();
		
		String mon =sd.format(dat);
		String year =sdf.format(dat);
		Long mont =Long.parseLong(mon);
		Long curyear =Long.parseLong(year);
		Long prmonth =0l;
		Long pryer =0l;
		Long monid =0l;
		Long yerid =0l;
		if(mont.equals(1l)) {
			 prmonth=12l;
			 pryer= curyear-1;
			 
		}else {
			prmonth=mont-1;
			pryer=curyear;
		}
		
		 List<Object[]>  monthd = commonHomeMethodsService.findbymonthid(prmonth);
		 for (Object[] objects : monthd) {
			 monid=Long.parseLong(objects[0].toString());
		}
		 List<Object[]> yeard = commonHomeMethodsService.findbyyearid(pryer);
		for (Object[] objects : yeard) {
			 yerid=Long.parseLong(objects[0].toString());
		}
		
		//Total Employees   
		 List<Object[]>  totalemp = commonHomeMethodsService.findtotalemployees(messages.getUserName(),messages.getRole_id());
		 Long Totalem =(long) totalemp.size();
			
		//Actives Employees
		 List<Object[]>  activeemp = commonHomeMethodsService.findactiveemployees(messages.getUserName(),messages.getRole_id());
		 Long activeeml =(long) activeemp.size();
				 
		 List<Object[]>  totalDDOs = commonHomeMethodsService.findtotalDDOs(messages.getUserName(),messages.getRole_id());
		 Long totalDDOS=0l;
		 if(messages.getRole_id()==1) {
			 totalDDOS=1l;
		 }else {
			  totalDDOS =(long) totalDDOs.size(); 	
		 }
		 
		 //Total OFC
		 List<Object[]>  totalOFC = commonHomeMethodsService.findtotalOFCcount(messages.getUserName(),messages.getRole_id());
		 Long totalOFCcount=0l;
		 if(messages.getRole_id()==1) {
			 totalOFCcount=1l;
		 }else {
			 totalOFCcount =(long) totalOFC.size(); 	
		 } 	
		 
		 Long chagestategenerated=0l;
		 Long chagestatefor=0l;
		 Long chagestateapp=0l;
		 Long paybillgenerate=0l;
		 Long forwordedtolevel2=0l;
		 Long consolatedbylevel2=0l;
		 Long forwordedBEAMS=0l;
		 Long acknowledgedBEAMS=0l;
		 Long salarycrediteds=0l;
		 Long appBEAMS=0l;
		 List<Object[]> dashborad = commonHomeMethodsService.finddashbard(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 for (Object[] objects : dashborad) {
				 if(objects[0]!=null) {
					 chagestategenerated=Long.parseLong(objects[0].toString());
				 }else {
					 chagestategenerated=0l;
				 }
				 if(objects[1]!=null) {
					 chagestatefor=Long.parseLong(objects[1].toString());
				 }else {
					 chagestatefor=0l;
				 }
				 if(objects[2]!=null) {
						chagestateapp=Long.parseLong(objects[2].toString());
				 }else {
						chagestateapp=0l;
				 }
				 if(objects[3]!=null) {
					 paybillgenerate=Long.parseLong(objects[3].toString());
				 }else {
					 paybillgenerate=0l;
				 }
				 if(objects[4]!=null) {
					 forwordedtolevel2=Long.parseLong(objects[4].toString());
				 }else {
					 forwordedtolevel2=0l;
				 }
				 if(objects[5]!=null) {
					 consolatedbylevel2=Long.parseLong(objects[5].toString());
				 }else {
					 consolatedbylevel2=0l;
				 }
				 if(objects[6]!=null) {
					 forwordedBEAMS=Long.parseLong(objects[6].toString());
				 }else {
					 forwordedBEAMS=0l;
				 }
				 if(objects[7]!=null) {
					 appBEAMS=Long.parseLong(objects[7].toString());
				 }else {
					 appBEAMS=0l;
				 }
				 if(objects[8]!=null) {
					 acknowledgedBEAMS=Long.parseLong(objects[8].toString());
				 }else {
					 acknowledgedBEAMS=0l;
				 }
				 if(objects[9]!=null) {
					 salarycrediteds=Long.parseLong(objects[9].toString());
				 }else {
					 salarycrediteds=0l;
				 }
		 }
			
			acknowledgedBEAMS=acknowledgedBEAMS+salarycrediteds;
			appBEAMS=appBEAMS+acknowledgedBEAMS;
			forwordedBEAMS=forwordedBEAMS+appBEAMS;
			consolatedbylevel2=consolatedbylevel2+forwordedBEAMS;
			forwordedtolevel2=forwordedtolevel2+consolatedbylevel2;
			paybillgenerate=paybillgenerate+forwordedtolevel2;
			chagestateapp=chagestateapp+paybillgenerate;
			chagestatefor=chagestatefor+chagestateapp;
			chagestategenerated=chagestategenerated+chagestatefor;
		 model.addAttribute("Totalemp", Totalem);
		 model.addAttribute("activeeml", activeeml);
		 model.addAttribute("chagestatefor", chagestatefor);
		 model.addAttribute("changestatementgenerated", chagestategenerated);
		 model.addAttribute("chagestateapp", chagestateapp);
		 model.addAttribute("paybillgenerate", paybillgenerate);
		 model.addAttribute("forwordedtolevel2", forwordedtolevel2);
		 model.addAttribute("consolatedbylevel2", consolatedbylevel2);
		 model.addAttribute("forwordedBEAMS", forwordedBEAMS);
		 model.addAttribute("acknowledgedBEAMS", acknowledgedBEAMS);
		 model.addAttribute("salarycrediteds", salarycrediteds);
		 model.addAttribute("appBEAMS", appBEAMS);
		 model.addAttribute("totalDDOS", totalDDOS);
		 model.addAttribute("totalOFCcount", totalOFCcount);
		
		 if(messages.getUpdatedDate()==null) {
			 
			 modelAndView.setViewName("redirect:/master/changePassword");
		 }else {
			 
			 modelAndView.setViewName("topics");
		 }
	
	return modelAndView;
		
		
    }
	
	/* Super Admin Home Page*/
	@RequestMapping("/super/home")
	public ModelAndView getSuperHomePage(HttpServletRequest request,Model model, HttpServletResponse response, Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/*modelAndView.addObject("usertopics", topicService.getAllTopics());*/
		modelAndView.addObject("language", locale.getLanguage());
		
		/*Setting User Session in an application*/
		/*logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode") ); */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),applicationAdminMstEntity.getApplicationCode()));*/
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");

		modelAndView.addObject("sessionMessages", messages.getUser_id());
		modelAndView.addObject("userName", messages.getFullName());
		int levelRoleVal = messages.getRole_id();
		
		session.setAttribute("levelRoleVal", messages.getRole_id());
		
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal,locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal,locale.getLanguage());
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		
		modelAndView.addObject("levelRoleVal", levelRoleVal);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		SimpleDateFormat sd =new SimpleDateFormat("MM");
//		modelAndView.setViewName("topics");
		
		Date dat =new Date();
		
		String mon =sd.format(dat);
		String year =sdf.format(dat);
		Long mont =Long.parseLong(mon);
		Long curyear =Long.parseLong(year);
		Long prmonth =0l;
		Long pryer =0l;
		Long monid =0l;
		Long yerid =0l;
		if(mont.equals(1l)) {
			 prmonth=12l;
			 pryer= curyear-1;
			 
		}else {
			prmonth=mont-1;
			pryer=curyear;
		}
		
		 List<Object[]>  monthd = commonHomeMethodsService.findbymonthid(prmonth);
		 for (Object[] objects : monthd) {
			 monid=Long.parseLong(objects[0].toString());
		}
		 List<Object[]> yeard = commonHomeMethodsService.findbyyearid(pryer);
		for (Object[] objects : yeard) {
			 yerid=Long.parseLong(objects[0].toString());
		}
		
		//Total Employees   
		 List<Object[]>  totalemp = commonHomeMethodsService.findtotalemployees(messages.getUserName(),messages.getRole_id());
		 Long Totalem =(long) totalemp.size();
			
		//Actives Employees
		 List<Object[]>  activeemp = commonHomeMethodsService.findactiveemployees(messages.getUserName(),messages.getRole_id());
		 Long activeeml =(long) activeemp.size();
		
		 List<Object[]>  totalDDOs = commonHomeMethodsService.findtotalDDOs(messages.getUserName(),messages.getRole_id());
		 Long totalDDOS=0l;
		 if(messages.getRole_id()==1) {
			 totalDDOS=1l;
		 }else {
			  totalDDOS =(long) totalDDOs.size(); 	
		 }
		 
		 //Total OFC
		 List<Object[]>  totalOFC = commonHomeMethodsService.findtotalOFCcount(messages.getUserName(),messages.getRole_id());
		 Long totalOFCcount=0l;
		 if(messages.getRole_id()==1) {
			 totalOFCcount=1l;
		 }else {
			 totalOFCcount =(long) totalDDOs.size(); 	
		 } 	
		 
		 Long chagestategenerated=0l;
		 Long chagestatefor=0l;
		 Long chagestateapp=0l;
		 Long paybillgenerate=0l;
		 Long forwordedtolevel2=0l;
		 Long consolatedbylevel2=0l;
		 Long forwordedBEAMS=0l;
		 Long acknowledgedBEAMS=0l;
		 Long salarycrediteds=0l;
		 Long appBEAMS=0l;
		 List<Object[]> dashborad = commonHomeMethodsService.finddashbard(messages.getUserName(),messages.getRole_id(),monid,yerid);
			for (Object[] objects : dashborad) {
				 if(objects[0]!=null) {
					 chagestategenerated=Long.parseLong(objects[0].toString());
				 }else {
					 chagestategenerated=0l;
				 }
				 if(objects[1]!=null) {
					 chagestatefor=Long.parseLong(objects[1].toString());
				 }else {
					 chagestatefor=0l;
				 }
				 if(objects[2]!=null) {
						chagestateapp=Long.parseLong(objects[2].toString());
				 }else {
						chagestateapp=0l;
				 }
				 if(objects[3]!=null) {
					 paybillgenerate=Long.parseLong(objects[3].toString());
				 }else {
					 paybillgenerate=0l;
				 }
				 if(objects[4]!=null) {
					 forwordedtolevel2=Long.parseLong(objects[4].toString());
				 }else {
					 forwordedtolevel2=0l;
				 }
				 if(objects[5]!=null) {
					 consolatedbylevel2=Long.parseLong(objects[5].toString());
				 }else {
					 consolatedbylevel2=0l;
				 }
				 if(objects[6]!=null) {
					 forwordedBEAMS=Long.parseLong(objects[6].toString());
				 }else {
					 forwordedBEAMS=0l;
				 }
				 if(objects[7]!=null) {
					 appBEAMS=Long.parseLong(objects[7].toString());
				 }else {
					 appBEAMS=0l;
				 }
				 if(objects[8]!=null) {
					 acknowledgedBEAMS=Long.parseLong(objects[8].toString());
				 }else {
					 acknowledgedBEAMS=0l;
				 }
				 if(objects[9]!=null) {
					 salarycrediteds=Long.parseLong(objects[9].toString());
				 }else {
					 salarycrediteds=0l;
				 }
		 }
			
			acknowledgedBEAMS=acknowledgedBEAMS+salarycrediteds;
			appBEAMS=appBEAMS+acknowledgedBEAMS;
			forwordedBEAMS=forwordedBEAMS+appBEAMS;
			consolatedbylevel2=consolatedbylevel2+forwordedBEAMS;
			forwordedtolevel2=forwordedtolevel2+consolatedbylevel2;
			paybillgenerate=paybillgenerate+forwordedtolevel2;
			chagestateapp=chagestateapp+paybillgenerate;
			chagestatefor=chagestatefor+chagestateapp;
			chagestategenerated=chagestategenerated+chagestatefor;
		 
		 model.addAttribute("Totalemp", Totalem);
		 model.addAttribute("activeeml", activeeml);
		 model.addAttribute("chagestatefor", chagestatefor);
		 model.addAttribute("changestatementgenerated", chagestategenerated);
		 model.addAttribute("chagestateapp", chagestateapp);
		 model.addAttribute("paybillgenerate", paybillgenerate);
		 model.addAttribute("forwordedtolevel2", forwordedtolevel2);
		 model.addAttribute("consolatedbylevel2", consolatedbylevel2);
		 model.addAttribute("forwordedBEAMS", forwordedBEAMS);
		 model.addAttribute("acknowledgedBEAMS", acknowledgedBEAMS);
		 model.addAttribute("salarycrediteds", salarycrediteds);
		 model.addAttribute("appBEAMS", appBEAMS);
		 model.addAttribute("totalDDOS", totalDDOS);
		 model.addAttribute("totalOFCcount", totalOFCcount);
		modelAndView.setViewName("topics");
	
	return modelAndView;
		
		
    }
	
	
	
	/* Super Admin Home Page*/
	@RequestMapping("/user/home")
	public ModelAndView mdcHomePage(HttpServletRequest request,Model model, HttpServletResponse response, Locale locale, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		/*modelAndView.addObject("usertopics", topicService.getAllTopics());*/
		modelAndView.addObject("language", locale.getLanguage());
		
		/*Setting User Session in an application*/
		/*logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode") ); */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),applicationAdminMstEntity.getApplicationCode()));*/
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");

		modelAndView.addObject("sessionMessages", messages.getUser_id());
		modelAndView.addObject("userName", messages.getFullName());
		int levelRoleVal = messages.getRole_id();
		
		session.setAttribute("levelRoleVal", messages.getRole_id());
		
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal,locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal,locale.getLanguage());
		
		modelAndView.addObject("menuList", menuList);
		modelAndView.addObject("subMenuList", subMenuList);
		
		modelAndView.addObject("levelRoleVal", levelRoleVal);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy");
		SimpleDateFormat sd =new SimpleDateFormat("MM");
//		modelAndView.setViewName("topics");
		
		Date dat =new Date();
		
		String mon =sd.format(dat);
		String year =sdf.format(dat);
		Long mont =Long.parseLong(mon);
		Long curyear =Long.parseLong(year);
		Long prmonth =0l;
		Long pryer =0l;
		Long monid =0l;
		Long yerid =0l;
		if(mont.equals(1l)) {
			 prmonth=12l;
			 pryer= curyear-1;
			 
		}else {
			prmonth=mont-1;
			pryer=curyear;
		}
		
		 List<Object[]>  monthd = commonHomeMethodsService.findbymonthid(prmonth);
		 for (Object[] objects : monthd) {
			 monid=Long.parseLong(objects[0].toString());
		}
		 List<Object[]> yeard = commonHomeMethodsService.findbyyearid(pryer);
		for (Object[] objects : yeard) {
			 yerid=Long.parseLong(objects[0].toString());
		}
		
		//Total Employees   
		 List<Object[]>  totalemp = commonHomeMethodsService.findtotalemployees(messages.getUserName(),messages.getRole_id());
		 Long Totalem =(long) totalemp.size();
			
		//Actives Employees
		 List<Object[]>  activeemp = commonHomeMethodsService.findactiveemployees(messages.getUserName(),messages.getRole_id());
		 Long activeeml =(long) activeemp.size();
		
		 List<Object[]>  totalDDOs = commonHomeMethodsService.findtotalDDOs(messages.getUserName(),messages.getRole_id());
		 Long totalDDOS=0l;
		 if(messages.getRole_id()==1) {
			 totalDDOS=1l;
		 }else {
			  totalDDOS =(long) totalDDOs.size(); 	
		 }
		 
		 //Total OFC
		 List<Object[]>  totalOFC = commonHomeMethodsService.findtotalOFCcount(messages.getUserName(),messages.getRole_id());
		 Long totalOFCcount=0l;
		 if(messages.getRole_id()==1) {
			 totalOFCcount=1l;
		 }else {
			 totalOFCcount =(long) totalDDOs.size(); 	
		 } 	
		 
		 Long chagestategenerated=0l;
		 Long chagestatefor=0l;
		 Long chagestateapp=0l;
		 Long paybillgenerate=0l;
		 Long forwordedtolevel2=0l;
		 Long consolatedbylevel2=0l;
		 Long forwordedBEAMS=0l;
		 Long acknowledgedBEAMS=0l;
		 Long salarycrediteds=0l;
		 Long appBEAMS=0l;
		 List<Object[]> dashborad = commonHomeMethodsService.finddashbard(messages.getUserName(),messages.getRole_id(),monid,yerid);
			for (Object[] objects : dashborad) {
				 if(objects[0]!=null) {
					 chagestategenerated=Long.parseLong(objects[0].toString());
				 }else {
					 chagestategenerated=0l;
				 }
				 if(objects[1]!=null) {
					 chagestatefor=Long.parseLong(objects[1].toString());
				 }else {
					 chagestatefor=0l;
				 }
				 if(objects[2]!=null) {
						chagestateapp=Long.parseLong(objects[2].toString());
				 }else {
						chagestateapp=0l;
				 }
				 if(objects[3]!=null) {
					 paybillgenerate=Long.parseLong(objects[3].toString());
				 }else {
					 paybillgenerate=0l;
				 }
				 if(objects[4]!=null) {
					 forwordedtolevel2=Long.parseLong(objects[4].toString());
				 }else {
					 forwordedtolevel2=0l;
				 }
				 if(objects[5]!=null) {
					 consolatedbylevel2=Long.parseLong(objects[5].toString());
				 }else {
					 consolatedbylevel2=0l;
				 }
				 if(objects[6]!=null) {
					 forwordedBEAMS=Long.parseLong(objects[6].toString());
				 }else {
					 forwordedBEAMS=0l;
				 }
				 if(objects[7]!=null) {
					 appBEAMS=Long.parseLong(objects[7].toString());
				 }else {
					 appBEAMS=0l;
				 }
				 if(objects[8]!=null) {
					 acknowledgedBEAMS=Long.parseLong(objects[8].toString());
				 }else {
					 acknowledgedBEAMS=0l;
				 }
				 if(objects[9]!=null) {
					 salarycrediteds=Long.parseLong(objects[9].toString());
				 }else {
					 salarycrediteds=0l;
				 }
		 }
			
			acknowledgedBEAMS=acknowledgedBEAMS+salarycrediteds;
			appBEAMS=appBEAMS+acknowledgedBEAMS;
			forwordedBEAMS=forwordedBEAMS+appBEAMS;
			consolatedbylevel2=consolatedbylevel2+forwordedBEAMS;
			forwordedtolevel2=forwordedtolevel2+consolatedbylevel2;
			paybillgenerate=paybillgenerate+forwordedtolevel2;
			chagestateapp=chagestateapp+paybillgenerate;
			chagestatefor=chagestatefor+chagestateapp;
			chagestategenerated=chagestategenerated+chagestatefor;
		 
		 model.addAttribute("Totalemp", Totalem);
		 model.addAttribute("activeeml", activeeml);
		 model.addAttribute("chagestatefor", chagestatefor);
		 model.addAttribute("changestatementgenerated", chagestategenerated);
		 model.addAttribute("chagestateapp", chagestateapp);
		 model.addAttribute("paybillgenerate", paybillgenerate);
		 model.addAttribute("forwordedtolevel2", forwordedtolevel2);
		 model.addAttribute("consolatedbylevel2", consolatedbylevel2);
		 model.addAttribute("forwordedBEAMS", forwordedBEAMS);
		 model.addAttribute("acknowledgedBEAMS", acknowledgedBEAMS);
		 model.addAttribute("salarycrediteds", salarycrediteds);
		 model.addAttribute("appBEAMS", appBEAMS);
		 model.addAttribute("totalDDOS", totalDDOS);
		 model.addAttribute("totalOFCcount", totalOFCcount);
		modelAndView.setViewName("topics");
	
	return modelAndView;
		
		
    }
	
	
	
	
	
}