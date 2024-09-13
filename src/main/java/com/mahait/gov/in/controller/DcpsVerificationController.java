package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgUserMst;
@RequestMapping("/ddoast")
@Controller
public class DcpsVerificationController extends BaseController {
	@GetMapping("dcpsVerification")
	public String dcpsVerification( Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		addMenuAndSubMenu(model,messages);
		model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
		return "/views/dcps-verifivcation";
	}

}
