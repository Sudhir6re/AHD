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

import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DcpContributionModel;
import com.mahait.gov.in.model.OnlineContributionModel;
import com.mahait.gov.in.model.RegularReportModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.MstDesignationService;
import com.mahait.gov.in.service.OnlineContributionService;
import com.mahait.gov.in.service.RegularReportService;
@RequestMapping("/ddoast")
@Controller
public class OnlineContriEntryController   extends BaseController{
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	RegularReportService regularReportService;
	
	@Autowired
	OnlineContributionService onlineContributionService;
	
	@Autowired
	MstDesignationService mstDesignationService;
	
	@RequestMapping("/onlineContriEntry")
	public String onlineContriEntry( @ModelAttribute("dcpContributionModel") DcpContributionModel dcpContributionModel,
			Model model, Locale locale, HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		
		
		
		dcpContributionModel.setUseType("ViewAll");
		
		dcpContributionModel.setAction("search");
		
		model.addAttribute("dcpContributionModel", dcpContributionModel);
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstBillDesc", regularReportService.lstBillDesc(messages.getDdoCode()));
		model.addAttribute("ddoNameLst", regularReportService.getDDOName(messages.getDdoCode()));
		model.addAttribute("paymentTypeLst", onlineContributionService.getPaymentTypeLst());
		model.addAttribute("payCommisionLst", mstDesignationService.findAllPayCommission());
		
		if(dcpContributionModel.getAction()!=null && dcpContributionModel.getAction().equals("search")) {
			String startDate=null;
			int month2 = dcpContributionModel.getDelayedMonthId();
			int year2 = dcpContributionModel.getDelayedFinYearId();
			if (month2 < 10) {
				startDate = String.valueOf(year2 - 1) + '-' + String.valueOf("0" + month2) + "-01";
			} else {
				startDate = String.valueOf(year2 - 1) + '-' + String.valueOf(month2) + "-01";
			}
			
			
			dcpContributionModel.setUseType("ViewAll");

			
			/*Boolean isPaybillGenerated = false;
			
			if (messages.getMstRoleEntity().getRoleId()==3) 
			{
				isPaybillGenerated = onlineContributionService.checkIfBillAlreadyGenerated(dcpContributionModel.getBillGroupId(), dcpContributionModel.getMonthId(),dcpContributionModel.getFinYearId());
			}
			model.addAttribute("isPaybillGenerated", isPaybillGenerated);
*/
			
			List<DcpContributionModel> dcpContributionModelLst=onlineContributionService.getEmpListForContribution(dcpContributionModel,messages,startDate);
			
			model.addAttribute("dcpContributionModelLst",dcpContributionModelLst);
			
		}
		
		
	
		
		
		return "/views/online-contri-entry";
	}

	@GetMapping("/viewRejectedContri")
	public String viewRejectedContri( Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model,messages);
		return "/views/view-rejected-contri";
	}

}
