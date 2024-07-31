/**
 * 
 */
package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.common.StringHelperUtils;
import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
import com.mahait.gov.in.model.ConsolidatePayBillModel;
import com.mahait.gov.in.model.LstConsolidatedPayBillModel;
import com.mahait.gov.in.model.TopicModel;
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
		model.addAttribute("consolidatePayBillModel", consolidatePayBillModel);
		
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
	
		
		if(message != null && message.equals("SUCCESS")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		if(message != null && message.equals("UPDATED")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
		model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
		model.addAttribute("lstSchemeCode", mstSchemeService.findAllScheme(messages.getDdoCode()));
//		model.addAttribute("lstBillStatus", commonHomeMethodsService.lstGetAllBillStatus());
		model.addAttribute("lstBillStatus", commonHomeMethodsService.lstGetAllBillStatusForConsolidatePaybill());
		//model.addAttribute("lstGenerateBillDetails", payBillViewApprDelBill.findAllPayBillsForConsolidatedAgainstDDO(messages.getUserName(),messages.getRole_id()));
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("userId", messages.getUserId());
		addMenuAndSubMenu(model,messages);	
		return "/views/paybill/consolidate-paybill";
    }
	
//	@RequestMapping(value = "/getBillsForConsolidation/{schemeCode}/{billStatus}/{yearName}/{monthName}", headers = "Accept=application/json", produces = "application/json;charset=UTF-8") // ,  method = RequestMethod.POST
	@RequestMapping(value = "/getBillsForConsolidation/{billStatus}/{yearName}/{monthName}", headers = "Accept=application/json", produces = "application/json;charset=UTF-8") // ,  method = RequestMethod.POST
	public ResponseEntity<String> getBillsForConsolidation(@RequestHeader HttpHeaders headers,@PathVariable String billStatus ,@PathVariable String yearName,@PathVariable String monthName,HttpSession session,
														UriComponentsBuilder ucBuilder,Locale locale) {
		
		
		try {
			String resJson = "";
			OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
//			resJson = commonHomeMethodsService.getBillsForConsolidation(schemeCode,billStatus,messages.getRole_id(),messages.getUserName(),Integer.parseInt(yearName),Integer.parseInt(monthName));
			resJson = commonHomeMethodsService.getBillsForConsolidation(billStatus,messages.getMstRoleEntity().getRoleId(),messages.getUserName(),Integer.parseInt(yearName),Integer.parseInt(monthName));
			return ResponseEntity.ok(resJson);
		} catch (Exception e) {
			return ResponseEntity.ok(e.getMessage());
		}
	}
	
	@PostMapping(value = "/getConsolidated")
	public String getConsolidation(@RequestHeader HttpHeaders headers, HttpSession session,
														UriComponentsBuilder ucBuilder,Locale locale) {
		try {
			String resJson = "";
			OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
			
			return "redirect:/ddoast/paybillGenerate";
		} catch (Exception e) {
			return "redirect:/ddoast/paybillGenerate";
		}
	}
	
//	@RequestMapping(value ="/saveConsolidated/{schemeCodeArr}/{schemeNameArr}/{statusArr}/{monthName}/{yearName}/{paybillGenerationTransactionIdArr}/{ddoCodeArr}/{grossAmtAr}/{netAmtAr}",headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
	@RequestMapping(value ="/saveConsolidated/{statusArr}/{monthName}/{yearName}/{paybillGenerationTransactionIdArr}/{ddoCodeArr}/{grossAmtAr}/{netAmtAr}/{userId}",headers = "Accept=application/json", produces = "application/json;charset=UTF-8")
	public String saveConsolidated(@PathVariable int statusArr,@PathVariable Integer monthName,@PathVariable Integer yearName,@PathVariable int userId,
			@PathVariable Object[] paybillGenerationTransactionIdArr,@PathVariable Object[] ddoCodeArr,@PathVariable Object[] grossAmtAr,@PathVariable Object[] netAmtAr,@ModelAttribute("consolidatePayBillTrnEntity") ConsolidatePayBillTrnEntity consolidatePayBillTrnEntity,
			Model model,Locale locale,HttpSession session,BindingResult bindingResult, RedirectAttributes redirectAttributes,HttpServletRequest request) {
		
		String clientIP = request.getRemoteAddr();
		
		 String clientHostname = request.getRemoteHost();
		   
		 
		 String namePIp=clientHostname+"/"+clientIP;
			
		 OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		  int paylength = paybillGenerationTransactionIdArr.length;
		  int grossAmtArLength = grossAmtAr.length;
		  int netAmtArLength = grossAmtAr.length;
		  int paybillGenerationTransId= paybillGenerationTransactionIdArr.length;
		  List<Integer> payBillGenerationTransId = new ArrayList<Integer>();
		  
		 Double grossAmt=0.0;
		  for(int i=0; i<grossAmtArLength; i++){
			  //new Double(grossAmtAr[i].toString());
			  grossAmt=grossAmt+new Double(grossAmtAr[i].toString());
		  }
		  Double netAmt=0.0;
		  for(int i=0; i<netAmtArLength; i++){
			  //new Double(grossAmtAr[i].toString());
			  netAmt=netAmt+new Double(netAmtAr[i].toString());
		  }
		 
		  for(int i=0; i<paylength; i++){
	    	 paybillGenerationTransId =Integer.parseInt(paybillGenerationTransactionIdArr[i].toString());
	    	 payBillGenerationTransId.add(paybillGenerationTransId);
		  }
		  
		 
		  
		  
					
			String resJson=null;
	     			
			//consolidatePayBillModel.setYearName(yearName);
	  		//consolidatePayBillModel.setBillStatus(statusArr);
	  	//	consolidatePayBillModel.setSubSchemeName(schemeNameArr);
	  		consolidatePayBillTrnEntity.setSchemeCode("2224A4521");
	  		consolidatePayBillTrnEntity.setPaybillMonth(monthName);
	  		
	  		//consolidatePayBillTrnEntity.setStatus(statusArr);
	  		consolidatePayBillTrnEntity.setPaybillYear(yearName);
	  		consolidatePayBillTrnEntity.setGrossAmt(grossAmt);
	  		consolidatePayBillTrnEntity.setNetAmt(netAmt);
	  		consolidatePayBillTrnEntity.setCreatedUserId(messages.getMstRoleEntity().getRoleId());
	  		consolidatePayBillTrnEntity.setDdoCode(messages.getUserName());
	  		consolidatePayBillTrnEntity.setCreatedDate(new Date());
	  		consolidatePayBillTrnEntity.setStatus(9);
	  		consolidatePayBillTrnEntity.setIsActive(9);
	  		
	  		// here setting the value of component   
	  		List<Object[]> ConsComponents=consolidatePayBillService.addConsComponents(messages.getUserName(),payBillGenerationTransId);
	        if (!ConsComponents.isEmpty()) {
	            for (Object[] objLst : ConsComponents) {
	            	
	            	if(objLst[0]!=null)
	            	consolidatePayBillTrnEntity.setIt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[0].toString())));         //it
	            	if(objLst[1]!=null)
	            	consolidatePayBillTrnEntity.setDcpsArr(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[1].toString())));    //dcps arr
	            	if(objLst[2]!=null)
	            	//consolidatePayBillTrnEntity.setGis(StringHelperUtils.isNullDouble(objLst[2]));    
	            	consolidatePayBillTrnEntity.setGis(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[2].toString()))); //gis 
	            	if(objLst[3]!=null)
	            	consolidatePayBillTrnEntity.setPt(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[3].toString())));        //pt 
	            	if(objLst[4]!=null)
	            	consolidatePayBillTrnEntity.setAccPolicy(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[4].toString()))); //acc policy 
	            	if(objLst[5]!=null)
	            	consolidatePayBillTrnEntity.setHrr(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[5].toString())));  //HRR
	            	if(objLst[6]!=null)
	            	consolidatePayBillTrnEntity.setTotalDeduct(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[6].toString())));  //Total Deduction
	            	if(objLst[7]!=null)
		            	consolidatePayBillTrnEntity.setPf(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[7].toString())));  //prov fund set
	            	if(objLst[8]!=null)
		            	consolidatePayBillTrnEntity.setDcps(StringHelperUtils.isNullDouble(Double.parseDouble(objLst[8].toString())));  //DCPS
   	
	           }
	        }
	      
	  		int afterSaveId = consolidatePayBillService.saveConsolidatePayBill(consolidatePayBillTrnEntity);
	  		Integer existingData = afterSaveId; 
			
	      for(int i=0; i<paylength; i++){
	    	  Long paybillGenerationTransactionId =Long.parseLong(paybillGenerationTransactionIdArr[i].toString());
	    	
	    	 
	    	int saveMpgId =  consolidatePayBillService.saveConsolidatePaybillMpgDetails(paybillGenerationTransactionId,ddoCodeArr[i].toString(),afterSaveId);
	  		PaybillGenerationTrnEntity paybillGenerationTrnEntity = paybillGenerationTrnService.consolidatedPaybill( paybillGenerationTransactionId,messages.getUserId(),namePIp);
	      }
	      
	      int monToint=monthName;
	      int yearToint=yearName;
	      
	      
		  	// resJson = existingData.toString();
		
		  
	      String message = (String)model.asMap().get("message");
			/*model.addAttribute("viewDeleteConsPayBillModel", viewDeleteConsPayBillModel);*/
			
		//	UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
			
			List<TopicModel> menuList = new ArrayList<>();
			List<TopicModel> subMenuList = new ArrayList<>();
			List<LstConsolidatedPayBillModel> lstConsolidatedPayBillModel = new ArrayList<>();
			lstConsolidatedPayBillModel=viewDelConsolidatePayBillService.viewDelconsolidatePayBill(monToint,yearToint,"2224A5214",afterSaveId);
			
			menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getMstRoleEntity().getRoleId(),locale.getLanguage());
			subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getMstRoleEntity().getRoleId(),locale.getLanguage());
			
			model.addAttribute("menuList", menuList);
			model.addAttribute("subMenuList", subMenuList);
			model.addAttribute("LstConsolidatedPayBillModel", lstConsolidatedPayBillModel);
			
			
			if(message != null && message.equals("SUCCESS")) {
				if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
					model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
				} else {
					model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
				}
			}
			if(message != null && message.equals("UPDATED")) {
				if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
					model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
				} else {
					model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
				}
			}
			model.addAttribute("lstMonths", commonHomeMethodsService.lstGetAllMonths());
			model.addAttribute("lstYears", commonHomeMethodsService.lstGetAllYears());
//			model.addAttribute("lstDDOCode", commonHomeMethodsService.findAllScheme());
			model.addAttribute("language", locale.getLanguage());
			
			addMenuAndSubMenu(model,messages);	
			return "/views/paybill/view-delete-consolidated-paybill";
	}
	
	
	
}
