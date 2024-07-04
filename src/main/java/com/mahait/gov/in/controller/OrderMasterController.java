package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.OrderMasterService;

@Controller
@RequestMapping("/ddo")
public class OrderMasterController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	OrderMasterService orderMasterService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/getOrderData")
	public String getOrderData(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,
			 Model model, Locale locale,
			HttpSession session) {
		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES"); 
	/*	UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		int levelRoleVal = messages.getRole_id();
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal,locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal,locale.getLanguage());
		
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		
		String message=(String) model.asMap().get("message");*/
		return null;
		
		
		//model.addAttribute("", getDesignation())
    	/*emplist = empWiseCityClassService.findAllEmployee(messages.getUserName());
    	newRegDDOModel.setEmplist(emplist);
	*/
		
    	/*model.addAttribute("lstDDO",newRegDDOService.findLvl1DDOCode(messages.getUserName()));	
    	model.addAttribute("empLst",newRegDDOService.findEmpLst(messages.getUserName()));	*/
    	/*model.addAttribute("lstDistrictLst",empWiseCityClassService.lstGetAllDistrict());		
    	model.addAttribute("lstTaluka",empWiseCityClassService.lstGetAllTaluka());		
    	///model.addAttribute("lstTalukaLst",empWiseCityClassService.findCityClasssLst());		
				model.addAttribute("newRegDDOModel", newRegDDOModel);
	///	model.addAttribute("message", message);
		
		
		
		return "/views/OrderMasterView";
		
		
	}
	
	@RequestMapping(value = "/addNewEntry", method = { RequestMethod.GET, RequestMethod.POST })
	public String addNewEntry(/*@ModelAttribute("mstEmployeeEntity") MstEmployeeEntity mstEmployeeEntity,
			Model model, Locale locale, HttpSession session) {

		String message = (String) model.asMap().get("message");

		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		//List<ChangeBasicDtlsModel> lstChangeBasic = new ArrayList<>();

		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
		//lstChangeBasic= empchangeBasicDtls.findEmpChangeBasicDtls(messages.getUserName());
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		//model.addAttribute("lstChangeBasic", lstChangeBasic);


		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.SAVEDRAFT, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("language", locale.getLanguage());
		return "/views/OrderMasterAddNewEntry";
			
		}*/
	}
	}

