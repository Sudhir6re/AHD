package com.mahait.gov.in.nps.controller;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.controller.BaseController;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;
import com.mahait.gov.in.nps.service.LegacyValidationService;
import com.mahait.gov.in.nps.service.NSDLDetailsService;
import com.mahait.gov.in.service.CommonHomeMethodsService;

@RequestMapping("/ddo")
@Controller
public class LegacyValidationController extends BaseController {

	@Autowired
	LegacyValidationService legacyValidationService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	NSDLDetailsService nsdlDetailsService;

	@GetMapping("/legacyValidation")
	public String legacyValidation(Model model, Locale locale, HttpSession session,
			@ModelAttribute("dcpsLegacyModel") DcpsLegacyModel dcpsLegacyModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);

		Calendar cal = Calendar.getInstance();
		Integer currmonth = new Integer(cal.get(2) + 1);
		Integer curryear = new Integer(cal.get(1));

		dcpsLegacyModel.setMonth(currmonth);
		dcpsLegacyModel.setYear(curryear);

		List<DcpsLegacyModel> lstDcpsLegacyModel = legacyValidationService.findNsdlLegacyList(dcpsLegacyModel,
				messages);
		dcpsLegacyModel.setLstDcpsLegacyModel(lstDcpsLegacyModel);


		model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
		return "/views/nps/legacy-validation";
	}

	@PostMapping("/searchLegacyDataByYearAndMonth")
	public ResponseEntity<List<DcpsLegacyModel>> searchLegacyDataByYearAndMonth(Model model, Locale locale,
			HttpSession session, @RequestBody DcpsLegacyModel dcpsLegacyModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<DcpsLegacyModel> lstDcpsLegacyModel = legacyValidationService.findNsdlLegacyList(dcpsLegacyModel,
				messages);
		return ResponseEntity.ok(lstDcpsLegacyModel);
	}

}
