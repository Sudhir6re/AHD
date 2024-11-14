package com.mahait.gov.in.nps.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.controller.BaseController;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.nps.model.DcpsLegacyModel;
import com.mahait.gov.in.nps.service.EntryDcpsLegacyService;
import com.mahait.gov.in.service.CommonHomeMethodsService;

@RequestMapping("/ddo")
@Controller
public class EntryDcpsLegacyController extends BaseController {

	@Autowired
	EntryDcpsLegacyService entryDcpsLegacyService;
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@RequestMapping("/entryDcpsLegacy")
	public String entryDcpsLegacy(Model model, Locale locale, HttpSession session,
			@ModelAttribute("dcpsLegacyModel") DcpsLegacyModel dcpsLegacyModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

	     List<CmnLookupMst> lLstLegValidatePeriod = commonHomeMethodsService.getLookupValues("validatePeriod",CommonConstants.Languages.english);
	     model.addAttribute("lLstLegValidatePeriod", lLstLegValidatePeriod);
		
		if (dcpsLegacyModel.getAction() != null && dcpsLegacyModel.getAction().equals("search")) {
			List<DcpsLegacyModel> lstDcpsLegacyModel = entryDcpsLegacyService.findDcpsEmployeeDetails(dcpsLegacyModel,
					messages);
			dcpsLegacyModel.setLstDcpsLegacyModel(lstDcpsLegacyModel);
		}
		addMenuAndSubMenu(model, messages);

		String message = (String) model.asMap().get("message");
		if (message != null) {
			model.addAttribute("message", message);
		}
		model.addAttribute("dcpsLegacyModel", dcpsLegacyModel);
		return "/views/nps/entry-dcps-legacy";
	}

	@RequestMapping("/saveDcpsLegacyData")
	public String saveDcpsLegacyData(Model model, Locale locale, HttpSession session,
			@ModelAttribute("dcpsLegacyModel") DcpsLegacyModel dcpsLegacyModel, RedirectAttributes redirectAttributes) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		Long saveid = entryDcpsLegacyService.saveDcpsLegacyData(dcpsLegacyModel, messages);
		redirectAttributes.addFlashAttribute("message", "Legacy Data Saved Successfully !!!");
		model.addAttribute("dcpsLegacyModel", dcpsLegacyModel);
		return "/views/nps/entry-dcps-legacy";
	}

	@RequestMapping("/checkDataExistsForPeriod/{sevaarthId}/{period}")
	public ResponseEntity<Integer> checkDataExistsForPeriod(@RequestBody Map<String, String> formData, Model model,
			HttpSession session, Locale locale) {
		String sevaarthId = formData.get("sevaarthId");
		String period = formData.get("period");
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		Integer status = entryDcpsLegacyService.checkDataExistsForPeriod(messages, period, sevaarthId);
		return ResponseEntity.ok(status);
	}

}
