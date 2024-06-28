package com.mahait.gov.in.controller;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mahait.gov.in.entity.Topic;
import com.mahait.gov.in.entity.UserInfo;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.WelcomeService;

@RestController
@EnableJdbcHttpSession
@RequestMapping("/user")
public class TopicController {
	
	/*protected final Log logger = LogFactory.getLog(getClass()); */
//	private final Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	WelcomeService welcomeService;
	
	@Autowired
    private Environment environment;
	
	@RequestMapping("/login")
	/*public ModelAndView login(Locale locale,@PathVariable int id) {*/
		public ModelAndView login(Locale locale,HttpServletRequest request) throws UnknownHostException {
		/*model.addAttribute("lstApplicationAdminTable",welcomeService.findApplicationOnebyId(applicationCode));*/
		int id = 1 ;
//		logger.info("Login-Page");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("lstApplicationAdminTable",welcomeService.findApplicationOnebyId(id));
		modelAndView.setViewName("custom-login");
		
		// Port
        String portNumber = environment.getProperty("server.port");
        // Local address
        String hostAddress = InetAddress.getLocalHost().getHostAddress();
        String hostName = InetAddress.getLocalHost().getHostName();
//        logger.info("Host address:"+hostAddress+" Host name:"+hostName+" Port number:"+portNumber);
        // Remote address
        InetAddress.getLoopbackAddress().getHostAddress();
        InetAddress.getLoopbackAddress().getHostName();
        /*logger.info("Host address:"+hostAddress1+" Host name:"+hostName1+" Port number:"+portNumber);*/
        
		
        
		return modelAndView;
    }	
	
	@RequestMapping("/home")
	public ModelAndView getAllUserTopics(HttpServletRequest request,Model model, HttpServletResponse response, Locale locale, HttpSession session) {
		
		
		
		
		ModelAndView modelAndView = new ModelAndView();
		/*modelAndView.addObject("usertopics", topicService.getAllTopics());*/
		modelAndView.addObject("language", locale.getLanguage());
		
		/*Setting User Session in an application*/
		/*logger.info(">>>>>ApppCode : " +request.getSession().getAttribute("appCode") ); */
		request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser()));
		/*request.getSession().setAttribute("MY_SESSION_MESSAGES",userDetailsServiceImpl.getUserIdbyUserName(request.getRemoteUser(),applicationAdminMstEntity.getApplicationCode()));*/
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		
		
		session.setAttribute("levelRoleVal", messages.getRole_id());
		
		

		modelAndView.addObject("sessionMessages", messages.getUser_id());
//		logger.info(""+messages.getFullName());
		modelAndView.addObject("userName", messages.getFullName());
		int levelRoleVal = messages.getRole_id();
		if(commonHomeMethodsService.findRole(messages.getRole_id()).getRoleName().equalsIgnoreCase("ROLE_ADMIN")) {
		return new ModelAndView("redirect:/admin/home");
		}
		else if (commonHomeMethodsService.findRole(messages.getRole_id()).getRoleName().equalsIgnoreCase("ROLE_OPERATOR")) {
		return new ModelAndView("redirect:/level1/home");
		}else if (commonHomeMethodsService.findRole(messages.getRole_id()).getRoleName().equalsIgnoreCase("ROLE_MODERATOR")) {
		return new ModelAndView("redirect:/moderator/home");
		}else if (commonHomeMethodsService.findRole(messages.getRole_id()).getRoleName().equalsIgnoreCase("ROLE_SUPERADMIN")) {
		return new ModelAndView("redirect:/super/home");
		}else if (commonHomeMethodsService.findRole(messages.getRole_id()).getRoleName().equalsIgnoreCase("ROLE_HOD")) {
		return new ModelAndView("redirect:/hod/home");
		}
		else {
		
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
			 pryer= curyear-1l;
			 
		}else {
			prmonth=mont-1l;
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
			
			/* //Change Statement Generated
			 List<Object[]>  changestatementgenerated = commonHomeMethodsService.findchagestategenerated(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long chagestategenerated =(long) changestatementgenerated.size(); 
			 
			 //Change Statement Forwarded
			 List<Object[]>  changestatementforword = commonHomeMethodsService.findchangestatementforword(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long chagestatefor =(long) changestatementforword.size(); 
			 
			 //Change Statement Appore
			 List<Object[]>  changestatementappore = commonHomeMethodsService.findchangestatementappore(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long chagestateapp =(long) changestatementappore.size(); 
			 
			//Paybill Generated
			 List<Object[]>  paybillgenerated = commonHomeMethodsService.findpaybillgenerated(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long paybillgenerate =(long) paybillgenerated.size(); 	
			
			 //Paybill Forwarded
			 List<Object[]>  forwordedtolevel = commonHomeMethodsService.findforwordedtolevel(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long forwordedtolevel2 =(long) forwordedtolevel.size(); 	
			 
			 //Consolidated Paybill
			 List<Object[]>  consolatedbylevel = commonHomeMethodsService.findconsolatedbylevel(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long consolatedbylevel2 =(long) consolatedbylevel.size(); 	
			 
			 //Consolidated Paybill  Forward to BEAMS
			 List<Object[]>  forwordedtoBEAMS = commonHomeMethodsService.findforwordedtoBEAMS(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long forwordedBEAMS =(long) forwordedtoBEAMS.size(); 	
			 
			 //Acknowledged by BEAMS
			 List<Object[]>  acknowledgedbyBEAMS = commonHomeMethodsService.findacknowledgedbyBEAMS(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long acknowledgedBEAMS =(long) acknowledgedbyBEAMS.size(); 	
			 
			 //Salary Credited
			 List<Object[]>  salarycredited = commonHomeMethodsService.findsalarycredited(messages.getUserName(),messages.getRole_id(),monid,yerid);
			 Long salarycrediteds =(long) salarycredited.size(); */	
			 
			 //Total DDOs
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
				                if(objects[0]!=null)
					chagestategenerated=Long.parseLong(objects[0].toString());
				                if(objects[1]!=null)   
					chagestatefor=Long.parseLong(objects[1].toString());
				                if(objects[2]!=null)
					chagestateapp=Long.parseLong(objects[2].toString());
				                if(objects[3]!=null)
					paybillgenerate=Long.parseLong(objects[3].toString());
				                if(objects[4]!=null)
					forwordedtolevel2=Long.parseLong(objects[4].toString());
				                if(objects[5]!=null)
					consolatedbylevel2=Long.parseLong(objects[5].toString());
				                if(objects[6]!=null)
					forwordedBEAMS=Long.parseLong(objects[6].toString());
				                if(objects[7]!=null)
					appBEAMS=Long.parseLong(objects[7].toString());
				                if(objects[8]!=null)
					acknowledgedBEAMS=Long.parseLong(objects[8].toString());
				                if(objects[9]!=null)
					salarycrediteds=Long.parseLong(objects[9].toString());
					
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
			modelAndView.setViewName("homepageLevel");
		}
		return modelAndView;
		
		
    }
	@RequestMapping("/logOut")
	public RedirectView  logOut(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    return new RedirectView("redirect:/user/login?logout");
	    
	}
	
	@RequestMapping("/secure/addTopics")
	public ModelAndView addTopics() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("modelAttrAddTopics", new Topic());
		modelAndView.setViewName("add-topics");
		return modelAndView;
	}
	@RequestMapping("/secure/saveTopics")
	public ModelAndView saveTopics(@ModelAttribute ("modelAttrAddTopics") Topic objTopics) {
		ModelAndView modelAndView = new ModelAndView();
		/*Save Code Here*/
		
		modelAndView.setViewName("topics");
		return modelAndView;
	}
	@RequestMapping("/error")
	public ModelAndView error() {
		    ModelAndView modelAndView = new ModelAndView();
		    String errorMessage= "You are not authorized for the requested data.";
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