package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpRltDdoMap;
import com.mahait.gov.in.model.MstDcpsBillGroup;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.OrgDdoMst;
import com.mahait.gov.in.model.RltDcpsDdoScheme;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DDOSchemeService;
import com.mahait.gov.in.service.ZpDDOOfficeService;

@Controller
@RequestMapping("/ddo")
public class ZpDDOOfficeController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	ZpDDOOfficeService zpDDOOfficeService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/approveDdoOfficeDataList")
	public String approveDdoOfficeDataList(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,
			 Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		/*String message=(String) model.asMap().get("message");
		*/
		/*List<ZpRltDdoMap> zpDDOOfficelst = zpDDOOfficeService
				.getAllDDOOfficeDtlsDataByPostID(messages.getCreatedByPost().getCreatedByPost().getPostId());
		
		
	///	model.addAttribute("message", message);
	///	
		model.addAttribute("zpDDOOfficelst", zpDDOOfficelst);*/
		
		return "/views/approveDDOOfficeView";
		
	}
	
	@GetMapping("/approveRejectDtls/{zpDdoCode}")
	public String approveRejectDtls(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel, Model model,
			Locale locale, HttpSession session, @PathVariable String zpDdoCode) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		
		newRegDDOModel = zpDDOOfficeService.getDDOinfo(zpDdoCode);
	    String officeName = zpDDOOfficeService.getOfficeName(zpDdoCode);
		model.addAttribute("newRegDDOModel", newRegDDOModel);
		model.addAttribute("zpDdoCode", zpDdoCode);
		model.addAttribute("officeName", officeName);
		
		
		
		return "/views/approveRejectDDOOfficeView";
	}
	
/*	@RequestMapping(value = "/updateApproveStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateApproveStatus(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,
			Model model, Locale locale, HttpSession session) {
		return "/views/approveDDOOfficeView";
		
	}
	*/
	
	
	
	@GetMapping("/updateApproveStatus/{zpDdoCode}/{flag}")
	public String updateApproveStatus(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,@PathVariable String zpDdoCode,
			@PathVariable int flag,Model model,Locale locale,HttpSession session,HttpServletRequest request, Object paybillHeadMpgRepo) {
		
			OrgUserMst orgUserMst  =  zpDDOOfficeService.approveddoDtls(zpDdoCode,flag);
		
		if(orgUserMst!=null)
			model.addAttribute("message","Approved Successfully");
			return "/views/approveDDOOfficeView";
		///}
	} 
	
/*	@RequestMapping(value = "/updateRejectStatus", method = { RequestMethod.GET, RequestMethod.POST })
	public String updateRejectStatus(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,
			Model model, Locale locale, HttpSession session) {
		return "/views/approveDDOOfficeView";
		
	}*/
	
	/*
	@PostMapping("/addSchemesAndBillGroupsToDdo")
	public String addSchemesAndBillGroupsToDdo(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,HttpSession session,
	RedirectAttributes redirectAttributes,Model model,Locale locale) {
		return null;

	///	DcpsCommonDAO lObjDcpsCommonDAO = new DcpsCommonDAOImpl(null, serv.getSessionFactory());
		//RltDcpsDdoScheme lObjDcpsDdoSchemeVO = (RltDcpsDdoScheme) inputMap.get("DcpsDdoScheme");
		///MstDcpsBillGroup lObjMstDcpsBillGroupVO = (MstDcpsBillGroup) inputMap.get("dcpsddobillgroup");

}*/
}