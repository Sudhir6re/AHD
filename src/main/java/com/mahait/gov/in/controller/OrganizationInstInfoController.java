package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.InstituteType;
import com.mahait.gov.in.entity.MstBankPay;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.MstDesnModel;
import com.mahait.gov.in.model.OrgDdoMstModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.OrganizationInstInfoService;
@Controller
@RequestMapping("/level1")
public class OrganizationInstInfoController {
//	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	OrganizationInstInfoService organizationInstInfoService;
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	/*@Autowired
	MstBankBranchService mstBankBranchService;
	

	@Autowired
	DDOScreenService dDOScreenService;
	
	@Autowired
	MstDesignationService mstDesignationService;

	@Autowired
	MstBankService mstBankService;
	
	@Autowired
	MstDepartmentService mstDepartmentService;

	@Autowired
	MstSubDepartmentService mstSubDepartmentService;

	@Autowired
	MstSubCorporationService mstSubCorporationService;
	
	@Autowired
	MstEmployeeService mstEmployeeService;
*/

	
	
	@GetMapping("/orgInstituteInfo")
	/*@GetMapping(value="/orgInstituteInfo/{remoteUser}")*/
	public String adminOfficeMaster( @ModelAttribute("orgDdoMstModel") OrgDdoMstModel orgDdoMstModel,
											Model model,Locale locale,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		String message = (String)model.asMap().get("message");
		modelAndView.addObject("organizationDdoMstModel", new OrgDdoMstModel());
		
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		model.addAttribute("ddoCode", messages.getUserName());
		
		//List<DDOScreenEntity> lstDDO=dDOScreenService.findDDOByUsername(messages.getUserName());
		
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		List<MstBankPay> bankName = new ArrayList<>();
		List<MstDesnModel> lstdesgination = new ArrayList<>();
		
		//bankName = commonHomeMethodsService.findBankName();
		lstdesgination = commonHomeMethodsService.findDesignation(messages.getUserName());
	//	orgInstInfoModel = commonHomeMethodsService.findInstData(messages.getUserName());
		
		//List<OrgDdoMst> lstDDOInfo=organizationInstInfoService.findDDOInfo(messages.getUserName());
		
		//List<InstituteType> lstInstituteType = organizationInstInfoService.lstInstType();
		
		/*model.addAttribute("lstAllBankBranchList",
				organizationInstInfoService.getBankBranch(String.valueOf(orgDdoMstModel.getBankId().toString())));*/
		if(message != null && message.equals("SUCCESS")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("lstdesgination", lstdesgination);
		//model.addAttribute("lstDDOInfo", lstDDOInfo);
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("bankName", bankName);
		model.addAttribute("menuList", menuList); 
		//model.addAttribute("lstInstituteType", lstInstituteType);
		//model.addAttribute("lstAllBankBranchList", lstAllBankBranchList);
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("orgDdoMstModel", orgDdoMstModel);
		return "views/hte-organization-institution-info";
    }	
	
	@PostMapping("/saveorgInstituteInfo")
	public String SaveorgInstituteInfo(@ModelAttribute("orgDdoMstModel") @Valid OrgDdoMstModel orgDdoMstModel,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		if(bindingResult.hasErrors()) {
			return "/views/hte-organization-institution-info"; 
		} 
		OrgUserMst messages  = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		orgDdoMstModel.setDdoCode(messages.getUserName());
		int afterSaveId = organizationInstInfoService.SaveorgInstituteInfo(orgDdoMstModel);
		if(afterSaveId > 0) {
			redirectAttributes.addFlashAttribute("message","SUCCESS");
		}
		return "redirect:/level1/orgInstituteInfo"; 
	}
	
	
/*	@GetMapping(value = "/fetchBranchByBank/{bankCode}")
	public @ResponseBody List<Object[]> fetchBranchByBank(@PathVariable int bankCode )
	{
		
		List<Object[]> saveid=commonHomeMethodsService.getBranchByBank(bankCode);
		return saveid;
	}
	
	@RequestMapping(value="/editInstituteData")	// , method = RequestMethod.POST
    public String editInstituteData ( @ModelAttribute("orgInstInfoModel") OrganizationInstInfoModel orgInstInfoModel,RedirectAttributes redirectAttributes, Model model,Locale locale,HttpSession session)
	{
		
		
		
		
		

		ModelAndView modelAndView = new ModelAndView();
		String message = (String)model.asMap().get("message");
		modelAndView.addObject("orgInstInfoModel", new OrganizationInstInfoModel());
		
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		model.addAttribute("ddoCode", messages.getUserName());
		
		List<DDOScreenEntity> lstDDO=dDOScreenService.findDDOByUsername(messages.getUserName());
		
		
		
		
		
		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		List<MstBankEntity> bankName = new ArrayList<>();
		//List<OrganizationInstInfoModel> desgination = new ArrayList<>();
		OrganizationInstInfoModel instdata = new OrganizationInstInfoModel();
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(),locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(),locale.getLanguage());
		bankName = commonHomeMethodsService.findBankName();
		//desgination = commonHomeMethodsService.findDesignation(messages.getUserName());
	//	orgInstInfoModel = commonHomeMethodsService.findInstData(messages.getUserName());
		
		orgInstInfoModel=commonHomeMethodsService.findDDOInfo(messages.getUserName());
		
		model.addAttribute("mstBankBranchList",mstBankBranchService.findAllBankBranchList());
		model.addAttribute("menuList", menuList);
		model.addAttribute("bankBranchCode", orgInstInfoModel.getBankBranchCode());
		model.addAttribute("menuList", menuList);
		
		model.addAttribute("departmentNameEn", lstDDO.get(0).getDepartmentCode());
		model.addAttribute("fieldHODDept", lstDDO.get(0).getSubDepartmentCode());
		
		model.addAttribute("lstDeptDataTable", mstDepartmentService.findAllDepartment().stream()
				.filter(p -> p.getIsActive() == '1')
				//.filter(p ->p.getDepartmentCode()==lstDDO.get(0).getDepartmentCode()) 
				.collect(Collectors.toList()));
		Integer corno=null;
		List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(),
				messages.getUserName().toString());
		if(lstDepartment.size()>0) {
			for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
				DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
				orgInstInfoModel.setParentAdminDepartmentId(ddoScreenModel.getDepartmentId());
				orgInstInfoModel.setParentFieldDepartmentId(ddoScreenModel.getSubDepartmentId());
				corno=ddoScreenModel.getSubDepartmentId();
			}
		}
		
		
		if(orgInstInfoModel==null) {
			model.addAttribute("isDataPresent", "1");
		for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
			DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
			orgInstInfoModel.setParentAdminDepartmentId(ddoScreenModel.getDepartmentId());
			orgInstInfoModel.setParentFieldDepartmentId(ddoScreenModel.getSubDepartmentId());
			corno=ddoScreenModel.getSubDepartmentId();
		}
		}else {
			model.addAttribute("isDataPresent", "0");
		}
		System.out.println("hhhhhhhhhh"+corno);
		
		List<MstSubDepartmentModel> con=mstEmployeeService.findfycorparationname(corno);
		
		System.out.println("-----"+con);
		
		model.addAttribute("lstAdminOfficeMst", con);
		model.addAttribute("lstDesignation", mstDesignationService.getDesignationMstData(locale.getLanguage()));
//		model.addAttribute("lstAdminOfficeMst", lstDepartment);
		model.addAttribute("corno", corno);
		model.addAttribute("lstAllBankList", mstBankService.lstAllBank());
		if(orgInstInfoModel.getBankId().toString()!=null)
		model.addAttribute("lstAllBankBranchList",
				mstEmployeeService.getBankBranch(String.valueOf(orgInstInfoModel.getBankId().toString())));
		
//		mstEmployeeModel.setParentAdminDepartmentId(BigInteger.valueOf(lstDDO.get(0).getDepartmentCode()));
//		mstEmployeeModel.setParentFieldDepartmentId((BigInteger.valueOf(lstDDO.get(0).getSubDepartmentCode())));
		
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("bankName", bankName);
		//model.addAttribute("desgination", desgination);
		//model.addAttribute("instdata", instdata);
		
		if(message != null && message.equals("SUCCESS")) {
			if(locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("orgInstInfoModel", orgInstInfoModel);
		return "views/edit-organization-institution-info";
		
		model.addAttribute("language", locale.getLanguage());
		UserInfo messages  = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
		model.addAttribute("ddoCode", messages.getUserName());

		List<TopicModel> menuList = new ArrayList<>();
		List<TopicModel> subMenuList = new ArrayList<>();
		List<MstBankEntity> bankName = new ArrayList<>();
		
		OrganizationInstInfoModel instdata = new OrganizationInstInfoModel();
		List<DDOScreenEntity> lstDDO=dDOScreenService.findDDOByUsername(messages.getUserName());
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(),locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(),locale.getLanguage());
		orgInstInfoModel = commonHomeMethodsService.findInstData(messages.getUserName());
		bankName = commonHomeMethodsService.findBankName();
	//	model.addAttribute("mstBankBranchList",mstBankBranchService.findAllBankBranchList());
		model.addAttribute("bankBranchCode", orgInstInfoModel.getBankBranchCode());
		model.addAttribute("menuList", menuList);
		model.addAttribute("subMenuList", subMenuList);
		model.addAttribute("bankName", bankName);
		model.addAttribute("orgInstInfoModel", orgInstInfoModel);
		
		
		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(),locale.getLanguage());
		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(),locale.getLanguage());
		bankName = commonHomeMethodsService.findBankName();
		//desgination = commonHomeMethodsService.findDesignation(messages.getUserName());
	//	orgInstInfoModel = commonHomeMethodsService.findInstData(messages.getUserName());
		
		orgInstInfoModel=commonHomeMethodsService.findDDOInfo(messages.getUserName());
		
		model.addAttribute("mstBankBranchList",mstBankBranchService.findAllBankBranchList());
		model.addAttribute("menuList", menuList);
		model.addAttribute("bankBranchCode", orgInstInfoModel.getBankBranchCode());
		model.addAttribute("menuList", menuList);
		
		model.addAttribute("departmentNameEn", lstDDO.get(0).getDepartmentCode());
		model.addAttribute("fieldHODDept", lstDDO.get(0).getSubDepartmentCode());
		
		model.addAttribute("lstDeptDataTable", mstDepartmentService.findAllDepartment().stream()
				.filter(p -> p.getIsActive() == '1')
				//.filter(p ->p.getDepartmentCode()==lstDDO.get(0).getDepartmentCode()) 
				.collect(Collectors.toList()));
		
		List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(),
				messages.getUserName().toString());
		return "/views/edit-organization-institution-info";
    }
	
	@PostMapping("/saveEditOrgInstInfo")
	public String saveEditOrgInstInfo(@ModelAttribute("orgInstInfoModel") OrganizationInstInfoModel orgInstInfoModel,
									BindingResult bindingResult,RedirectAttributes redirectAttributes,Model model,Locale locale) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("language", locale.getLanguage());
			return "/views/edit-mst-department"; 
			return "redirect:/master/editInstituteData";
		} 
		
		int message = commonHomeMethodsService.saveEditOrgInstInfo(orgInstInfoModel);
		if(message>0) {
				redirectAttributes.addFlashAttribute("message","Record Updated Successfully");
			
		}
		
		return "redirect:/level1/orgInstituteInfo"; redirects to controller URL
	}*/
}
