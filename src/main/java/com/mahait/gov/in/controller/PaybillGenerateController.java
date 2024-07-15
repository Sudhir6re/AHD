package com.mahait.gov.in.controller;

//import java.math.BigInteger;
//import java.net.InetAddress;
//import java.net.UnknownHostException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.validation.Valid;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.mahait.gov.in.common.CommonConstants;
//import com.mahait.gov.in.entity.ConsolidatePayBillTrnEntity;
//import com.mahait.gov.in.entity.MpgSchemeBillGroupEntity;
//import com.mahait.gov.in.entity.MstEmployeeEntity;
//import com.mahait.gov.in.entity.MstSchemeEntity;
//import com.mahait.gov.in.entity.PaybillGenerationTrnEntity;
//import com.mahait.gov.in.entity.UserInfo;
//import com.mahait.gov.in.model.PaybillHeadMpgModel;
//import com.mahait.gov.in.model.TopicModel;
//import com.mahait.gov.in.service.ArrearsBillGenerationTrnService;
//import com.mahait.gov.in.service.BDSIntegrationService;
//import com.mahait.gov.in.service.CommonHomeMethodsService;
//import com.mahait.gov.in.service.DDOCityCategoryService;
//import com.mahait.gov.in.service.MpgSchemeBillGroupService;
//import com.mahait.gov.in.service.MstEmployeeService;
//import com.mahait.gov.in.service.MstSchemeService;
//import com.mahait.gov.in.service.NillBillGenerationTrnService;
//import com.mahait.gov.in.service.PaybillGenerationTrnService;
//import com.mahait.gov.in.service.PaybillGenerationpayForContractTrnService;
//import com.mahait.gov.in.service.SuspensionBrokenPaybillGenerationTrnService;
//import com.mahait.gov.in.service.SuspensionPaybillGenerationTrnService;

//@Controller
//@RequestMapping("/level1")
public class PaybillGenerateController {
//	protected final Log logger = LogFactory.getLog(getClass());
//
//	/*
//	 * @Autowired PaybillGenerateService paybillGenerateService;
//	 */
//	@Autowired
//	CommonHomeMethodsService commonHomeMethodsService;
//
//	@Autowired
//	PaybillGenerationTrnService paybillHeadMpgService;
//
//	@Autowired
//	MpgSchemeBillGroupService mpgSchemeBillGroupService;
//
//	@Autowired
//	MstSchemeService mstSchemeService;
//
//	@Autowired
//	DDOCityCategoryService dDOCityCategoryService;
//	
//	@Autowired
//	MstEmployeeService mstEmployeeService;
//	
//	
//	@Autowired
//	BDSIntegrationService bdsintegrationservice;
//	
//	@Autowired
//	SuspensionPaybillGenerationTrnService suspensionPaybillGenerationTrnService;
//	
//	@Autowired
//	NillBillGenerationTrnService nillBillGenerationTrnService;
//	
//	@Autowired
//	ArrearsBillGenerationTrnService arrearsBillGenerationTrnService;
//	
//	@Autowired
//	PaybillGenerationpayForContractTrnService paybillGenerationpayForContractTrnService;
//	
//	@Autowired
//	SuspensionBrokenPaybillGenerationTrnService suspensionBrokenPaybillGenerationTrnService;
//
//
//	@GetMapping("/paybillGenerate")
//	public String paybillGenerate(@ModelAttribute("paybillHeadMpgModel") PaybillHeadMpgModel paybillHeadMpgModel,
//			Model model, Locale locale, HttpSession session) {
//
//		String message = (String) model.asMap().get("message");
//		
//		model.addAttribute("paybillHeadMpgModel", paybillHeadMpgModel);
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
//
//		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
//		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
//		
//		
//		
//		model.addAttribute("lstBillTypes", commonHomeMethodsService
//				.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.BILL_TYPE));
//
//		int isCityCategory = dDOCityCategoryService.getCityCategoryMappedWithDDo(messages.getUserName());
//
//		model.addAttribute("menuList", menuList);
//		model.addAttribute("subMenuList", subMenuList);
//
//	
//		model.addAttribute("isCityCategory", isCityCategory);
//		model.addAttribute("language", locale.getLanguage());
//		model.addAttribute("lstGetAllMonths", commonHomeMethodsService.lstGetAllMonths());
//		model.addAttribute("lstGetAllYear", commonHomeMethodsService.lstGetAllYears());
//
//		
//		model.addAttribute("lstSchemeBillGroup", mpgSchemeBillGroupService
//				.findAllMpgSchemeBillGroupByDDOCode(messages.getUserName(), messages.getRole_id()));
//		return "/views/paybill/paybill-generate";
//	}
//
//	@PostMapping("/savePaybillHeadMpg")
//	public String savePaybillHeadMpg(
//			@ModelAttribute("paybillHeadMpgModel") @Valid PaybillHeadMpgModel paybillHeadMpgModel,
//			BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, Locale locale,
//			HttpSession session,HttpServletRequest request) {
//	
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		paybillHeadMpgModel.setCreatedUserId(messages.getUser_id());
//		
//		
//		
//		 String clientIP = request.getRemoteAddr();
//			
//		 String clientHostname = request.getRemoteHost();
//		   
//		 
//		 String namePIp=clientHostname+"/"+clientIP;
//		 
//		 System.out.println(namePIp);
//		
//		InetAddress local = null;
//		try {
//			local = InetAddress.getLocalHost();
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		paybillHeadMpgModel.setMacId(namePIp);
//		
//		Integer saveid = (Integer) paybillHeadMpgService.isPaybillExists(paybillHeadMpgModel.getSchemeBillgroupId(), paybillHeadMpgModel.getPaybillMonth(),paybillHeadMpgModel.getPaybillYear());
//		int afterSaveId=0;
//		if(saveid==0){
//		if(paybillHeadMpgModel.getBillTypeId()==2) {  // 1-->Regular,2-->Supplimentory
//			afterSaveId = paybillHeadMpgService.saveSupPaybillHeadMpg(paybillHeadMpgModel);
//		}else if(paybillHeadMpgModel.getBillTypeId()==1 || paybillHeadMpgModel.getBillTypeId()==7){  //regular
//			afterSaveId = paybillHeadMpgService.savePaybillHeadMpg(paybillHeadMpgModel);
//		}else if(paybillHeadMpgModel.getBillTypeId()==3){   // suspension bill
//			afterSaveId = suspensionPaybillGenerationTrnService.saveSuspensionBill(paybillHeadMpgModel);
//		}else if(paybillHeadMpgModel.getBillTypeId()==4){   //Nil Bill
//			afterSaveId = nillBillGenerationTrnService.saveNilBill(paybillHeadMpgModel);
//		}else if(paybillHeadMpgModel.getBillTypeId()==6){   //Nil Bill
//			afterSaveId = arrearsBillGenerationTrnService.saveArrearsBill(paybillHeadMpgModel);
//		}else if(paybillHeadMpgModel.getBillTypeId()==8){
//			afterSaveId = suspensionBrokenPaybillGenerationTrnService.saveSuspensionBrokenPaybillBill(paybillHeadMpgModel);
//		}	else {   //Nil Bill
//			afterSaveId = paybillGenerationpayForContractTrnService.savePaybillHeadMpgForContract(paybillHeadMpgModel);
//		}
//		
//		}
//		String success="SUCCESS";
//		if (afterSaveId > 0) {
//			redirectAttributes.addFlashAttribute("message", success);
//			redirectAttributes.addFlashAttribute("returnId", afterSaveId);
//			redirectAttributes.addFlashAttribute("payBillMonth", paybillHeadMpgModel.getPaybillMonth());
//			session.setAttribute("PAY_BILL_MONTH", paybillHeadMpgModel.getPaybillMonth());
//		}else{
//			redirectAttributes.addFlashAttribute("message", "FAILURE");
//		}
//		return "redirect:/level1/paybillGenerate";
//		// return "redirect:/paybill/payBillViewApprDelBill"; 
//	}
//
//	@GetMapping(value = "/listSchemeDetails/{data}", consumes = { "application/json" }, produces = {
//			"application/json" })
//	public @ResponseBody List<MstSchemeEntity> multiply(@PathVariable String data, Model model, Locale locale) {
//
//		List<MstSchemeEntity> mstSchemeEntity = mstSchemeService.findAllSchemeDetails(data);
//		return mstSchemeEntity;
//	}
//
//	@GetMapping(value = "/getNumberOfEmployee/{logUser}/{schemeBillGroupId}/{monthName}/{yearName}/{paybillType}", consumes = {
//			"application/json" }, produces = { "application/json" })
//	public @ResponseBody BigInteger getNumberOfEmployee(@PathVariable String logUser,
//			@PathVariable int schemeBillGroupId,@PathVariable int monthName,@PathVariable int yearName,@PathVariable int paybillType, Model model, Locale locale) {
//
//		MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
//				.findAllMpgSchemeBillGroupbyParameter(schemeBillGroupId);
//
//		
//		BigInteger getNumberofEmployeeInBillGroup = mstSchemeService.findNumberOfEmployeeInBillGroup(logUser,
//				schemeBillGroupId,monthName,yearName,paybillType);
//		return getNumberofEmployeeInBillGroup;
//	}
//
//	
//	@GetMapping(value = "/paybillgenerationIsExist/{billNumber}/{monthName}/{yearName}")
//	public String paybillgenerationIsExist(@PathVariable int billNumber, @PathVariable int monthName,
//			@PathVariable int yearName) {
//
//		Integer saveid = (Integer) paybillHeadMpgService.getpaybilldata(billNumber, monthName, yearName);
//		return saveid.toString();
//	}
//
//	
//	@GetMapping(value = "/PaybillValidation/{billNumber}/{monthName}/{yearName}")
//	public ResponseEntity<String> PaybillValidation(@PathVariable int billNumber, @PathVariable int monthName,
//			@PathVariable int yearName, HttpSession session) {
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		List<Object[]> paybillgen = paybillHeadMpgService.findpaybill(billNumber, monthName, yearName,
//				messages.getUserName());
//		Integer existingData = paybillgen.size();
//		String resJson = existingData.toString();
//		return ResponseEntity.ok(resJson);
//	}
//
//	// check is employee not mapped with allowance deduction
//	@GetMapping(value = "/getEmployeeMappedWithAllowanceDeduction/{schemeBillGroupId}")
//	public @ResponseBody List<Object[]> getEmployeeMappedWithAllowanceDeduction(@PathVariable int schemeBillGroupId,
//			HttpSession session) {
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		return dDOCityCategoryService.getEmployeeMappedWithAllowanceDeduction(schemeBillGroupId,
//				messages.getUserName());
//	}
//
//	/*// check is already bill is generated according to sevaarth id
//	@GetMapping(value = "/getSevaarthIdMappedWithBill/{billNumber}/{monthName}/{yearName}")
//	public @ResponseBody List<Object[]> getSevaarthIdMappedWithBill(@PathVariable int billNumber,@PathVariable int monthName,@PathVariable int yearName,
//			HttpSession session) {
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		return dDOCityCategoryService.getSevaarthIdMappedWithBill(billNumber,monthName,yearName,messages.getUserName());
//	}*/
//	
//	
//	// check is already bill is generated according to sevaarth id
//		@GetMapping(value = "/getSevaarthIdMappedWithBill/{billNumber}/{monthName}/{yearName}")
//		public @ResponseBody List<Object[]> getSevaarthIdMappedWithBillUpdate(@PathVariable int monthName,@PathVariable int yearName,@PathVariable int billNumber,
//				HttpSession session) {
//			UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//			return dDOCityCategoryService.getSevaarthIdMappedWithBillUpdate(monthName,yearName,billNumber);
//		}
//	/*// paybill validation for emp data not null
//	@GetMapping(value = "/PaybillValidationForNotNullColumns/{billNumber}/{monthName}/{yearName}")
//	public ResponseEntity<List<MstEmployeeEntity>> PaybillValidationForNotNullColumns(HttpSession session,@PathVariable int billNumber,@PathVariable int monthName,@PathVariable int yearName) {
//	UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//	MpgSchemeBillGroupEntity mpgSchemeBillGroupEntity = mpgSchemeBillGroupService
//			.findAllMpgSchemeBillGroupbyParameter(billNumber);
//	
//	//String ddoCode,int billGroupId,int month,int year
//	return new ResponseEntity<List<MstEmployeeEntity>>(mstEmployeeService.findAllWorkingEmployeeByDDOCodeAndBillGroup(messages.getUserName(),mpgSchemeBillGroupEntity.getBillGroupId(),monthName,yearName), HttpStatus.OK);
//	
//	//return dDOCityCategoryService.PaybillValidationForNotNullColumns(messages.getUserName(),billGroupId,month,year);
//	}*/
//	
//	// check Broken Period according to sevaarth id
//		@GetMapping(value = "/getSevaarthIdMappedWithBrokenPeriod/{noofemp}/{billNumber}/{monthName}/{yearName}")
//		public @ResponseBody Object[] getSevaarthIdMappedWithBrokenPeriod(@PathVariable int noofemp,@PathVariable int billNumber,@PathVariable int monthName,@PathVariable int yearName,
//				HttpSession session) {
//			UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//			return paybillHeadMpgService.getSevaarthIdMappedWithBill(messages.getUserName(),noofemp,billNumber,monthName,yearName );
//		}
//		
//		@GetMapping(value = "/getCheckIsBillInProcess/{monthName}/{yearName}/{schemeBillGroupId}/{paybillType}")
//		public ResponseEntity<String> getCheckIsBillInProcess(@PathVariable int monthName,
//				@PathVariable int yearName,@PathVariable int schemeBillGroupId,@PathVariable int paybillType, HttpSession session) {
//			UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//			int paybillgen = paybillHeadMpgService.getCheckIsBillInProcess(monthName, yearName,schemeBillGroupId,
//					paybillType);
//			Integer existingData = paybillgen;
//			String resJson = existingData.toString();
//			return ResponseEntity.ok(resJson);
//		}
//	    @GetMapping(value = "/isEmpRetired/{monthName}/{yearName}/{schemeBillGroupId}/{paybillType}")
//		public ResponseEntity<String> isEmpRetired(@PathVariable int monthName,
//				@PathVariable int yearName,@PathVariable int schemeBillGroupId,@PathVariable int paybillType, HttpSession session) {
//			UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//			
//			
//			String existingData = paybillHeadMpgService.isEmpRetired(monthName, yearName,schemeBillGroupId,
//					paybillType,messages.getUserName());
//			
//			System.out.println("response-----"+existingData);
//			return ResponseEntity.ok(existingData);
//		}
//	    
//	    @SuppressWarnings("unchecked")
//		@RequestMapping(value = "/approveBill/{consolidatedId}", method=RequestMethod.POST)
//		public ResponseEntity<String> approveBill(@ModelAttribute("consolidatePayBillTrnEntity") ConsolidatePayBillTrnEntity consolidatePayBillTrnEntity,
//				Model model,Locale locale,HttpSession session,@PathVariable int consolidatedId,HttpServletRequest request) {
//			
//			//   select * from consolidate_paybill_trn 
//	    	consolidatePayBillTrnEntity=bdsintegrationservice.findPayBillInfoById(consolidatedId);
//	    	consolidatePayBillTrnEntity.setStatus(11);
//	    	consolidatePayBillTrnEntity.setIsActive(11);
//			consolidatePayBillTrnEntity.setUpdatedDate(new Date());
//			bdsintegrationservice.approvePayBill(consolidatePayBillTrnEntity);
//			List<Object[]> lst=bdsintegrationservice.findPayBillDetails(consolidatedId);
//			for (Object object[] : lst) {
//				Integer payGenTrnId=Integer.parseInt(object[0].toString());	
//				PaybillGenerationTrnEntity paybillGenerationTrnEntity=bdsintegrationservice.findPaybillObj(payGenTrnId);
//				paybillGenerationTrnEntity.setIsActive(11);
//				paybillGenerationTrnEntity.setUpdatedDate(new Date());
//				bdsintegrationservice.approveBill(paybillGenerationTrnEntity);
//			}
//			String msg="Bill Approved Successfully";
//			return ResponseEntity.ok(msg);
//		}
//	    
//	    @GetMapping(value = "/checkedBgisAndGisCatNull/{schemeBillGroupId}")
//		public ResponseEntity<List<MstEmployeeEntity>> checkedBgisAndGisCatNull(@PathVariable int schemeBillGroupId,HttpSession session) {
//			UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//			List<MstEmployeeEntity> lstMstEmployeeEntity =paybillHeadMpgService.checkedBgisAndGisCatNull(schemeBillGroupId,messages.getUserName());
//			return ResponseEntity.ok(lstMstEmployeeEntity);
	//	}
	    
}