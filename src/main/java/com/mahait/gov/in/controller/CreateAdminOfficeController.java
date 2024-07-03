package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ZpAdminNameMst;
import com.mahait.gov.in.model.ZpRltDdoMapModel;
import com.mahait.gov.in.service.CreateAdminOfficeService;

@RequestMapping("/mdc")
@Controller
public class CreateAdminOfficeController {

	@Autowired
	CreateAdminOfficeService createAdminOfficeService;

	@GetMapping("/createAdminOffice")
	public String CreateAdminOffice(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
	//	List<ZpRltDdoMapModel> lstZpRltDdoMapModel = createAdminOfficeService.findAllDdoMappedlist(messages);
		List<ZpAdminNameMst> lstZpAdminNameMst = createAdminOfficeService.fetchAllOfficeList(messages);
		
		
		List<CmnTalukaMst> lstCmnTalukaMst=createAdminOfficeService.findAllTalukaList(messages);
		List<CmnDistrictMst> lstCmnDistrctMst=createAdminOfficeService.findAllDistrictList(messages);
		
		//model.addAttribute("lstZpRltDdoMapModel", lstZpRltDdoMapModel);
	
		String districtName=null;
		String talukaNametName=null;
		String adminType=null;
		
		List<Object[]> lstZpRltDdoMapRlt=createAdminOfficeService.findZpRltDtls(messages,districtName,talukaNametName,adminType);
		model.addAttribute("lstZpAdminNameMst", lstZpAdminNameMst);
		model.addAttribute("lstZpRltDdoMapRlt", lstZpRltDdoMapRlt);
		model.addAttribute("lstCmnTalukaMst", lstCmnTalukaMst);
		model.addAttribute("lstCmnDistrctMst", lstCmnDistrctMst);
		return "/views/create-admin-office";
	}

	@GetMapping("/createOffice")
	public String CreateOffice(Model model, Locale locale, HttpSession session,@ModelAttribute("zpRltDdoMapModel") ZpRltDdoMapModel zpRltDdoMapModel) {
		return "/views/create-office";
	}
	
	
	@GetMapping("/saveCreateAdminOffice")
	public String saveCreateAdminOffice(Model model, Locale locale, HttpSession session,@ModelAttribute("zpRltDdoMapModel") ZpRltDdoMapModel zpRltDdoMapModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		createAdminOfficeService.saveCreateAdminOffice(zpRltDdoMapModel,messages);
		return "/views/create-office";
	}

}
