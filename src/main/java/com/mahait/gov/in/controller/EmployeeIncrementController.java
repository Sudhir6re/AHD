package com.mahait.gov.in.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.model.AnnualIncrementModel;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.EmployeeIncrementService;

@Controller
@RequestMapping("/ddo")
public class EmployeeIncrementController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	EmployeeIncrementService employeeIncrementService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/getIncrementDataForReptDDO")
	public String getIncrementDataForReptDDO(
			@ModelAttribute("annualIncrementModel") AnnualIncrementModel annualIncrementModel, Model model, Locale locale,
			HttpSession session) {
		String message = (String) model.asMap().get("message");
		/*UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		List<PayCommssionEntity> payCommssionEntityList = new ArrayList<>();
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
		try {
			payCommssionEntityList = employeeIncrementService.findAppPayCommssion();
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("payCommssionEntityList", payCommssionEntityList);

		model.addAttribute("lstAllBillGroup", employeeIncrementService
				.findAllMpgSchemeBillGroupByDDOCode(messages.getUserName(), messages.getRole_id()));*/
		model.addAttribute("language", locale.getLanguage());
		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		///model.addAttribute("lstEmp", employeeIncrementService.getEmpDataForIncrementApproval(messages.getUserName()));
		model.addAttribute("language", locale.getLanguage());
		///model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		///model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
		return "/views/approvalAnnual-increment";
	}
	
	@GetMapping("/MTR21/{orderNo}/{incrementOrderDate}")
	public String MTR21(@ModelAttribute("annualIncrementModel") AnnualIncrementModel annualIncrementModel, Model model,
			Locale locale, HttpSession session, @PathVariable String orderNo,@PathVariable  String incrementOrderDate) {
		///UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		///int levelRoleVal = messages.getRole_id();
		/*List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();

		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());*/

		model.addAttribute("systemdate", sdf.format(new Date()));
		///model.addAttribute("officeName", employeeIncrementService.officeName(messages.getUserName()));
		
		///model.addAttribute("lstEmp", employeeIncrementService.lstEmpforMTR21(orderNo,incrementOrderDate,levelRoleVal,messages.getUserName()));
		///model.addAttribute("menuList", menuList);
		///model.addAttribute("subMenuList", subMenuList);
		return "/views/report/MTR21Report";
	}

	
	}

