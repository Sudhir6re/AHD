/*package com.mahait.gov.in.controller;

import java.time.LocalDate;
import java.util.List;
>>>>>>> 37dd139dd11988863a82f8d88e4284992037cf71
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
>>>>>>> 37dd139dd11988863a82f8d88e4284992037cf71
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.PathVariable;
>>>>>>> 37dd139dd11988863a82f8d88e4284992037cf71
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.DeptEligibilityForAllowAndDeductEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.DeptEligibilityForAllowAndDeductModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.service.AllowDeduBulkEmpService;
import com.mahait.gov.in.service.AllowanceDeductionWiseMstService;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.CreateAdminOfficeService;
import com.mahait.gov.in.service.DeptEligibilityForAllowAndDeductService;
import com.mahait.gov.in.service.MstEmployeeService;
<<<<<<< HEAD
=======
import com.sun.el.parser.ParseException;
>>>>>>> 37dd139dd11988863a82f8d88e4284992037cf71

@Controller
@RequestMapping("/ddoast")
public class AllowDeduBulkEmpController {
	
	@Autowired
	DeptEligibilityForAllowAndDeductService deptEligibilityForAllowAndDeductService;
	
	@Autowired
	CreateAdminOfficeService createAdminOfficeService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	MstEmployeeService mstEmployeeService;
	
	/*@Autowired
	MpgSchemeBillGroupService mpgSchemeBillGroupService;
	
	@Autowired
	
	/*@Autowired
	MpgSchemeBillGroupService mpgSchemeBillGroupService;
	
	
	@Autowired
	AllowanceDeductionWiseMstService allowanceDeductionWiseMstService;
	
	@Autowired
	MstEmployeeService mstEmployeeService;
	
	@Autowired
	AllowDeduBulkEmpService allowDeduBulkEmpService;
	
	
	@GetMapping("/loadAllowDeduBulkEmpPage")
 	public String loadAllowDeduBulkEmpPage(HttpServletRequest request,Locale locale,HttpSession session,Model model,@ModelAttribute("deptEligibilityForAllowAndDeductModel") DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel){
 		
 		//model.addAttribute("lstDDOCode", createAdminOfficeService.lstAllDDO());
		model.addAttribute("lstAllDepartment", createAdminOfficeService.lstAllDepartment());
		
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
	    List<Object[]> deptEligibilityForAllowAndDeductEntity =  createAdminOfficeService.employeeMappingList(messages.getUserName());
		model.addAttribute("language", locale.getLanguage());
	//	model.addAttribute("lstDDOWiseEmployee", mstEmployeeService.findAllEmployeeByddoCode(messages.getUserName()));
		
	//	model.addAttribute("lstDeptDataTable", mstEmployeeService.findAllEmployees());
		//model.addAttribute("lstDeptDataTable", mstEmployeeService.findAllEmployeesByDDOName(messages.getUserName()));
		
		model.addAttribute("lstdeptEligibilityForAllowAndDeduct", deptEligibilityForAllowAndDeductService.findDeptNonGovDeductList());
		model.addAttribute("context", request.getContextPath());

		model.addAttribute("lstSchemeBillGroup", mpgSchemeBillGroupService
				.findAllMpgSchemeBillGroupByDDOCode(messages.getUserName()));
		
		model.addAttribute("lstSchemeBillGroup", mpgSchemeBillGroupService
				.findAllMpgSchemeBillGroupByDDOCode(messages.getUserName()));
		
		
		model.addAttribute("testObj",deptEligibilityForAllowAndDeductEntity);
		LocalDate now = LocalDate.now();
		model.addAttribute("now", now);
		return "views/allow-dedu-bulk-emp";
	}
	
	@GetMapping(value = "/getListEmpBySchemBillGroup/{schemeBillGrpId}/{departmentAllowdeducCode}")
	public ResponseEntity<List<MstEmployeeModel>> getListEmpBySchemBillGroup(@PathVariable Integer schemeBillGrpId,@PathVariable Integer departmentAllowdeducCode
			,HttpSession session) {
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<MstEmployeeModel> lstMstEmployeeModel = allowDeduBulkEmpService
				.getListEmpBySchemBillGroup(messages.getUserName(), schemeBillGrpId,departmentAllowdeducCode);
		return ResponseEntity.ok(lstMstEmployeeModel);
	}
	
	@RequestMapping(value = "/allowancDeductionByType/{isType}", consumes = {
	"application/json" }, headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
     public ResponseEntity<List<DeptEligibilityForAllowAndDeductEntity>> allowancDeductionByType(
	 @PathVariable int isType) {
     return ResponseEntity
		.ok(allowanceDeductionWiseMstService.fetchLstDeptEligibilityForAllowAndDeductEntityByType(isType));
   }
	
	@RequestMapping("/saveAllowDeduBulkForEmp")
	public ModelAndView saveAllowDeduBulkForEmp(HttpServletRequest request, Model model,
			HttpServletResponse response, Locale locale, HttpSession session,
			@ModelAttribute("deptEligibilityForAllowAndDeductModel") DeptEligibilityForAllowAndDeductModel deptEligibilityForAllowAndDeductModel,RedirectAttributes redirectAttributes) throws ParseException {
		
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		for(int i=0;i<deptEligibilityForAllowAndDeductModel.getLstDeptEligibilityForAllowAndDeductModel().size();i++) {
			 allowDeduBulkEmpService.checkComponentAlreadyPresent(deptEligibilityForAllowAndDeductModel.getLstDeptEligibilityForAllowAndDeductModel().get(i),messages.getUserName());
		}
       
		
		int isSave=0;
		
		for(int i=0;i<deptEligibilityForAllowAndDeductModel.getLstDeptEligibilityForAllowAndDeductModel().size();i++) {
			if(deptEligibilityForAllowAndDeductModel.getLstDeptEligibilityForAllowAndDeductModel().get(i).isCheckBox()==true) {
				isSave=allowDeduBulkEmpService.updateAllowDeductBulkEmplComp(deptEligibilityForAllowAndDeductModel.getLstDeptEligibilityForAllowAndDeductModel().get(i),messages.getUserName());
			}
		}
		if(isSave>0) {
			redirectAttributes.addFlashAttribute("message", "Data Saved successfully !!!");
		}else {
			redirectAttributes.addFlashAttribute("message", "Data Saved successfully !!!");
		}
		return new ModelAndView("redirect:/level1/loadAllowDeduBulkEmpPage");
	}
	
	@GetMapping(value = "/PaybillInBulkProcess/{billNumber}")
	public ResponseEntity<String> PaybillInBulkProcess(@PathVariable int billNumber,HttpSession session) {
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		List<Object[]> paybillgen = allowDeduBulkEmpService.findpaybill(billNumber,messages.getUserName());
		Integer existingData = paybillgen.size();
		String resJson = existingData.toString();
		return ResponseEntity.ok(resJson);
	}

*/
