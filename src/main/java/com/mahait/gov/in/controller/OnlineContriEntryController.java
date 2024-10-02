package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mahait.gov.in.entity.CmnLocationMst;
import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;
import com.mahait.gov.in.model.MstSchemeModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.MstDesignationService;
import com.mahait.gov.in.service.OnlineContributionService;
import com.mahait.gov.in.service.RegularReportService;

@RequestMapping(value= {"/ddoast","/ddo"})
@Controller
public class OnlineContriEntryController extends BaseController {

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	RegularReportService regularReportService;

	@Autowired
	OnlineContributionService onlineContributionService;

	@Autowired
	MstDesignationService mstDesignationService;

	@RequestMapping("/onlineContriEntry")
	public String onlineContriEntry(@ModelAttribute("dcpContributionModel") DcpContributionModel dcpContributionModel,
			Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);

		dcpContributionModel.setUseType("ViewAll");

		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));

		model.addAttribute("ddoNameLst", regularReportService.getDDOName(messages.getDdoCode()));

		model.addAttribute("paymentTypeLst", onlineContributionService.getPaymentTypeLst());
		model.addAttribute("payCommisionLst", mstDesignationService.findAllPayCommission());

		String messageResponse = (String) model.asMap().get("message");
		if (messageResponse != null && messageResponse.equals("SUCCESS")) {
			model.addAttribute("message", "Contribution saved successfully !!!");
		}

		if (dcpContributionModel.getAction() != null && dcpContributionModel.getAction().equals("SEARCH_EMP")) {
			String startDate = null;

			int month2 = dcpContributionModel.getDelayedMonthId();
			int year2 = dcpContributionModel.getDelayedFinYearId();
			if (month2 < 10) {
				startDate = 20 + String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
			} else {
				startDate = 20 + String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
			}

			dcpContributionModel.setUseType("ViewAll");
			
			Boolean isPaybillGenerated = onlineContributionService.checkIfBillAlreadyGenerated(dcpContributionModel.getBillGroupId(),
					dcpContributionModel.getMonthId(),dcpContributionModel.getFinYearId());
			
			model.addAttribute("isPaybillGenerated", isPaybillGenerated);
			

			List<DcpContributionModel> dcpContributionModelLst = onlineContributionService
					.getEmpListForContribution(dcpContributionModel, messages, startDate);
			
			
			
			
					
			
			
			

			dcpContributionModel.setLstDcpContributionModel(dcpContributionModelLst);
			model.addAttribute("dcpContributionModelLst", dcpContributionModelLst);

		}

		List<MstPayCommissionEntity> lstddcPayCommission = mstDesignationService.findAllPayCommission();
		model.addAttribute("lstddcPayCommission", lstddcPayCommission);

		dcpContributionModel.setAction("search");
		model.addAttribute("dcpContributionModel", dcpContributionModel);
		List<CmnLocationMst> lstTreasury=onlineContributionService.findTreasuryList(messages);
		model.addAttribute("lstTreasury", lstTreasury);

		return "/views/online-contri-entry";
	}

	@GetMapping("/viewRejectedContri")
	public String viewRejectedContri(Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);
		return "/views/view-rejected-contri";
	}

	@RequestMapping("/saveOnlineContriEntry")
	public String saveOnlineContriEntry(
			@ModelAttribute("dcpContributionModel") DcpContributionModel dcpContributionModel, Model model,
			Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		dcpContributionModel.setCreatedPostId(messages.getUserId());
		Long saveId = onlineContributionService.saveOrUpdate(dcpContributionModel);
		model.addAttribute("message", "SUCCESS");
		return "redirect:/ddoast/onlineContriEntry";
	}
	
	

	@RequestMapping("/getSchemeCodeByBillGroupId/{billGroupId}")
	public @ResponseBody List<MstSchemeModel> getSchemeCodeByBillGroupId(@PathVariable String billGroupId, Model model,
			Locale locale) {
		List<MstSchemeModel> status = onlineContributionService.getSchemeCodeByBillGroupId(billGroupId);
		return status;
	}
	
	
	@PostMapping("/calculateDcpsArrear")
	public ResponseEntity<DcpContributionModel> calculateDcpsArrear(@RequestBody Map<String, String> formData) {
		DcpContributionModel lstDcpContributionModel = onlineContributionService.calculateDcpsArrear(formData);
		return ResponseEntity.ok(lstDcpContributionModel);
	}
	
	@PostMapping("/findDcpsContribution")
	public ResponseEntity<List<Object[]>> findSumContribution(@PathVariable String sevaarthId,String paymentType,Integer monthId,Integer yearId) {
		List<Object[]> lst= onlineContributionService.findSumContribution(sevaarthId,paymentType,monthId,yearId);
		return ResponseEntity.ok(lst);
	}
	
	
	

}
