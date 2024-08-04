/**
 * 
 */
package com.mahait.gov.in.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.ConsolidatePayBillModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.ConsolidatePayBillService;
import com.mahait.gov.in.service.MstSchemeService;
import com.mahait.gov.in.service.PayBillViewApprDelBillService;
import com.mahait.gov.in.service.PaybillGenerationTrnService;
import com.mahait.gov.in.service.ViewDelConsolidatePayBillService;

@Controller
@RequestMapping("/ddo")
public class ConsolidatePayBillController  extends BaseController{
//	protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	MstSchemeService mstSchemeService;
	
	@Autowired
	PayBillViewApprDelBillService payBillViewApprDelBill;
	
	@Autowired
	PaybillGenerationTrnService paybillGenerationTrnService;
	
	@Autowired
	ConsolidatePayBillService consolidatePayBillService;
	
	@Autowired
	ViewDelConsolidatePayBillService viewDelConsolidatePayBillService;
	
	
	
	@GetMapping("/consolidatePayBill")
	public String consolidatePayBill(@ModelAttribute("consolidatePayBillModel") ConsolidatePayBillModel consolidatePayBillModel,
										Model model,Locale locale,HttpSession session) {
		
		String message = (String)model.asMap().get("message");
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstSchemeCode", mstSchemeService.findAllScheme(messages.getDdoCode()));
		model.addAttribute("lstBillStatus", commonHomeMethodsService.lstGetAllBillStatusForConsolidatePaybill());
		
		consolidatePayBillModel.setLstCons(consolidatePayBillService.fetchDDOLst(messages.getDdoCode()));
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("userId", messages.getUserId());
		model.addAttribute("consolidatePayBillModel", consolidatePayBillModel);
		addMenuAndSubMenu(model,messages);	
		return "/views/paybill/consolidate-paybill";
    }


	@PostMapping(value = "/searchConsolidatedPaybill")
	public String searchConsolidatedPaybill(@ModelAttribute("consolidatePayBillModel") ConsolidatePayBillModel consolidatePayBillModel, 
			BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale,HttpSession session) {
			
		
			OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
			consolidatePayBillModel.setLstCons(consolidatePayBillService.searchConsolidatedPaybill(consolidatePayBillModel,messages));
			model.addAttribute("consolidatePayBillModel", consolidatePayBillModel);
				
				return "/views/paybill/consolidate-paybill-details";
			}
	
	
	@RequestMapping(value = "/saveConsolidatePayBill")
	public String saveConsolidatePayBill(@ModelAttribute("consolidatePayBillModel") ConsolidatePayBillModel consolidatePayBillModel, 
			BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale,HttpSession session) {
			
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
				int afterSaveId = consolidatePayBillService.saveConsolidatePayBill(consolidatePayBillModel,messages);
		
				if(afterSaveId > 0) {
					redirectAttributes.addFlashAttribute("message","SUCCESS");
				}
				else
				{
					redirectAttributes.addFlashAttribute("messages","SUCCESS");
				}
				
				return "/views/paybill/view-delete-consolidated-paybill";
			}
	
	
}
