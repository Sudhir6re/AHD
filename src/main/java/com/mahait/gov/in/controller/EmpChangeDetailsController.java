package com.mahait.gov.in.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ReligionMstEntity;

import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.EmpChangeDetailsModel;
import com.mahait.gov.in.model.MstDistrictModel;
import com.mahait.gov.in.model.MstStateModel;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.EmpChangeDetailsService;
import com.mahait.gov.in.service.LocationMasterService;
import com.mahait.gov.in.service.MstBankService;
import com.mahait.gov.in.service.MstDesignationService;
import com.mahait.gov.in.service.MstEmployeeService;



@Controller
@RequestMapping("/level1")
public class EmpChangeDetailsController {
	
//	@Autowired
//	CommonHomeMethodsService commonHomeMethodsService;
//	
//	@Autowired
//	EmpChangeDetailsService empChangeDetailsService;
//	
//
//	@Autowired
//	LocationMasterService locationMasterService;
//	
//	@Autowired
//	MstEmployeeService mstEmployeeService;
//	
//	@Autowired
//	MstDesignationService mstDesignationService;
//	
//	@Autowired
//	MstBankService mstBankService;
//
//
//	
//	List<EmpChangeDetailsModel> EmpChangeDetailsModelList = new ArrayList<>();
//	
//	
//	@GetMapping("/changeDetails")
//	public String changeDetails(  @ModelAttribute("mstEmployeeEntity") MstEmployeeEntity mstEmployeeEntity,
//			Model model, Locale locale, HttpSession session) throws ParseException {
//
//		String message = (String) model.asMap().get("message");
//
//		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
//		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
//		//List<ChangeBasicDtlsModel> lstChangeBasic = new ArrayList<>();
//
////		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
////		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
//		//lstChangeBasic= empchangeBasicDtls.findEmpChangeBasicDtls(messages.getUserName());
//		model.addAttribute("menuList", menuList);
//		model.addAttribute("subMenuList", subMenuList);
//		//model.addAttribute("lstChangeBasic", lstChangeBasic);
//
//
//		if (message != null && message.equals("SUCCESS")) {
//			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
//				model = CommonUtils.initModel(CommonConstants.Message.SAVEDRAFT, STATUS.SUCCESS, model);
//			} else {
//				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
//			}
//		}
//		model.addAttribute("language", locale.getLanguage());
//		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
//		
//		List<MstEmployeeEntity> empLst =empChangeDetailsService.findEmpLst(messages.getUserName());
//	
//		
//		model.addAttribute("empLst", empLst);
//		
//		model.addAttribute("mstEmployeeEntity", mstEmployeeEntity);
//		model.addAttribute("roleId", messages.getRole_id());
//		
//		return "/views/employee/changeDetails";
//	}
//	
//
//	
//	@RequestMapping(value = "/changeEmpDtls/{empId}", method = { RequestMethod.GET, RequestMethod.POST })
//	public String approveEmployeeConfiguration(@ModelAttribute("mstEmployeeEntity") MstEmployeeEntity mstEmployeeEntity,
//			Model model, Locale locale, HttpSession session,@PathVariable int empId) {
//		String message = (String) model.asMap().get("message");
//
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
//		//List<ChangeBasicDtlsModel> lstChangeBasic = new ArrayList<>();
//
//		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
//		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
//		//lstChangeBasic= empchangeBasicDtls.findEmpChangeBasicDtls(messages.getUserName());
//		model.addAttribute("menuList", menuList);
//		model.addAttribute("subMenuList", subMenuList);
//		//model.addAttribute("lstChangeBasic", lstChangeBasic);
//
//
//		if (message != null && message.equals("SUCCESS")) {
//			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
//				model = CommonUtils.initModel(CommonConstants.Message.SAVEDRAFT, STATUS.SUCCESS, model);
//			} else {
//				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
//			}
//		}
//		model.addAttribute("language", locale.getLanguage());
//		mstEmployeeEntity = empChangeDetailsService.getEmployeeData(empId);
//		
//		if(mstEmployeeEntity.getGender()!=null) {
//			if(mstEmployeeEntity.getGender()=='M') {
//				mstEmployeeEntity.setGender('1');
//			}else if(mstEmployeeEntity.getGender()=='F') {
//				mstEmployeeEntity.setGender('2');
//			}else if(mstEmployeeEntity.getGender()=='T') {
//				mstEmployeeEntity.setGender('3');
//			}else {
//				mstEmployeeEntity.setGender(mstEmployeeEntity.getGender());
//			}
//		}
//		
//		/*if(mstEmployeeEntity.getBasicPay()!=null) {
//			mstEmployeeEntity.setBasicPay(mstEmployeeEntity.getBasicPay());
//		}
//		if(mstEmployeeEntity.getSevenPcBasic()!=null)
//			mstEmployeeEntity.setBasicPay(mstEmployeeEntity.getSevenPcBasic().doubleValue());*/
//		
//		if(mstEmployeeEntity.getPayCommissionCode()==8) {
//			mstEmployeeEntity.setBasicPay(mstEmployeeEntity.getSevenPcBasic().doubleValue());
//		}
//		if(mstEmployeeEntity.getPayCommissionCode()==2) {
//			mstEmployeeEntity.setBasicPay(mstEmployeeEntity.getBasicPay());
//		}
//		/*if(mstEmployeeEntity.getUidNo()!=null) {
//
//			mstEmployeeEntity.setUidNo1(Long.valueOf(mstEmployeeEntity.getUidNo().toString().substring(0,3)));
//			mstEmployeeEntity.setUidNo2(Long.valueOf(mstEmployeeEntity.getUidNo().toString().substring(4,7)));
//			mstEmployeeEntity.setUidNo3(Long.valueOf(mstEmployeeEntity.getUidNo().toString().substring(8,11)));
//		}*/
//		if (mstEmployeeEntity.getUidNo() != null) {
//			String uidNoArray = Long.valueOf(mstEmployeeEntity.getUidNo()).toString();
//			
//			System.out.println("---------UID-----------"+uidNoArray);
//			if (uidNoArray.length() == 12) {
//				
//				System.out.println("test uid"+uidNoArray.substring(8,12));
//				
//			
//
//				Long u1=Long.valueOf(uidNoArray.substring(0,4));
//			    Long u2=Long.valueOf(uidNoArray.substring(4,8));
//				Long u3=Long.valueOf(uidNoArray.substring(8,12));
//				
//				if(u1.toString().length()<=4) {
//					 String u11 = String.format("%04d", u1); // Use %0nd where n is the total width, adjust as needed.
//				}
//				
//				if(u2.toString().length()<=4) {
//					 String formattedResult = String.format("%012d", u2); // Use %0nd where n is the total width, adjust as needed.
//				}
//				
//				
//				if(u3.toString().length()<=4) {
//					 String formattedResult = String.format("%012d", u3); // Use %0nd where n is the total width, adjust as needed.
//				}
//				 
//			
//				mstEmployeeEntity.setUidNo1(uidNoArray.substring(0,4));
//				mstEmployeeEntity.setUidNo2(uidNoArray.substring(4,8));
//				mstEmployeeEntity.setUidNo3(uidNoArray.substring(8,12));
//
//			}
//		}
//		BigInteger b= mstEmployeeEntity.getCadreCode();
//		Integer cadreCode=b.intValue();
//		
//		mstEmployeeEntity.setPostdetailid(mstEmployeeEntity.getPostdetailid());
//		
//		List<Object[]> employeeConfigurationService = mstEmployeeService.findAllGroupAndSuperAnnuationAge(cadreCode);
//		
//		int superAnnAge=0;
//		for (Object[] objects : employeeConfigurationService) {
//			
//			BigDecimal age = (BigDecimal) objects[6];
//			
//			superAnnAge =age.intValue();
//		}
//		mstEmployeeEntity.setSuperAnnAge(superAnnAge);
//		
//		List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(),
//				messages.getUserName().toString());
//
//		List<MstPayCommissionEntity> lstddcPayCommission = mstDesignationService.findAllPayCommission();
//		List<Object[]> payscalelevel = new ArrayList<Object[]>();
//		List<Object[]> lstsvnbasicpay = new ArrayList<Object[]>();
//		List<Object[]> listPfSeries = new ArrayList<Object[]>();
//		
//		if(mstEmployeeEntity.getMstGpfDetailsEntity()!=null)
//		listPfSeries= mstEmployeeService.getPfSeries(mstEmployeeEntity.getMstGpfDetailsEntity().getAccountmaintainby());
//		
//		if (mstEmployeeEntity.getPayCommissionCode() != null && !mstEmployeeEntity.getPayCommissionCode().equals(8))
//			payscalelevel = mstEmployeeService.findEmployeeConfigurationGetSixPayScale(2500341);
//		else
//			payscalelevel = mstEmployeeService.findEmployeeConfigurationGetpayscale(mstEmployeeEntity.getPayCommissionCode());
//		
//		if(mstEmployeeEntity.getPayCommissionCode().equals(8)) {
//			payscalelevel = mstEmployeeService.findEmployeeConfigurationGetpayscale(8);
//		}
////		if (mstEmployeeEntity.getPayCommissionCode() != null)
////			payscalelevel = mstEmployeeService.findEmployeeConfigurationGetpayscale(mstEmployeeEntity.getPayCommissionCode().intValue());
//		
//		
//		/*if(mstEmployeeEntity.getPayScaleCode()!=null && mstEmployeeEntity.getPayScaleCode().intValue()!=0) {
//			lstsvnbasicpay = mstEmployeeService
//					.findEmployeeConfigurationGetsvnbasicpayChangedetails(mstEmployeeEntity.getPayScaleCode().toString(),mstEmployeeEntity.getSevenPcBasic());
//		}*/
//		
//		
//		if(mstEmployeeEntity.getPayCommissionCode()==8 && mstEmployeeEntity.getSevenPcLevel()!=null && mstEmployeeEntity.getSevenPcLevel().intValue()!=0) {
//			lstsvnbasicpay = mstEmployeeService
//					.findEmployeeConfigurationGetsvnbasicpayChangedetails(mstEmployeeEntity.getSevenPcLevel().toString(),mstEmployeeEntity.getSevenPcBasic());
//		}
//		
//		model.addAttribute("lstCurrentPost", empChangeDetailsService.GetCurrentPostDesigation(mstEmployeeEntity.getPostdetailid()));
//		
//		
//		
//		model.addAttribute("lstddcPayCommission", lstddcPayCommission);
//		
//		
//		model.addAttribute("lstpfSeries", listPfSeries);
//		
//		
//		
//		model.addAttribute("lstCadreMst", mstEmployeeService.getCadreMstData(locale.getLanguage()));
//		model.addAttribute("lstGISGroup", mstEmployeeService.getGISGroup());
//		model.addAttribute("lstCommonMstSalutation", commonHomeMethodsService
//				.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_SALUTATION));
//		model.addAttribute("lstCommonMstGIS", commonHomeMethodsService
//				.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_GIS));
//		
//		
//		List<MstStateModel> listStatemdl = new ArrayList<MstStateModel>();
//		List<Object[]> listState = locationMasterService.findAllStates(1);
//		for (Iterator iterator = listState.iterator(); iterator.hasNext();) {
//			Object[] objects = (Object[]) iterator.next();
//			MstStateModel mstStateModel = new MstStateModel();
//			mstStateModel.setStateCode(Integer.parseInt(String.valueOf(objects[1])));
//			mstStateModel.setStateNameEn(String.valueOf(objects[2]));
//			listStatemdl.add(mstStateModel);
//		}
//		
//		List<MstDistrictModel> listDistrictemdl = new ArrayList<MstDistrictModel>();
//
//		try {
//			List<Object[]> listDistrict = locationMasterService.findAllDistricts(mstEmployeeEntity.getStateCode());
//			// logger.info("distric code list size="+listDistrict.size());
//			for (Iterator iterator = listDistrict.iterator(); iterator.hasNext();) {
//				Object[] objects = (Object[]) iterator.next();
//				MstDistrictModel mstDistrictModel = new MstDistrictModel();
//				mstDistrictModel.setState(String.valueOf(objects[3]));
//				mstDistrictModel.setStateName(String.valueOf(objects[4]));
//				listDistrictemdl.add(mstDistrictModel);
//				// logger.info("distric code list object4="+String.valueOf(objects[4]));
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			// e.printStackTrace();
//		}
//		List<MstCommonEntity> cityClassList = new ArrayList<>();
//		cityClassList = commonHomeMethodsService.findCommonMstByCommonCode("CITYCLASS");
//		model.addAttribute("cityClassList", cityClassList);
//		
//		model.addAttribute("lstAllDistrict", listDistrictemdl);
//		model.addAttribute("lstAllState", listStatemdl);
//		model.addAttribute("lstCurntDepartment", lstDepartment);
//		model.addAttribute("lstDesignation", mstDesignationService.getDesignationMstData(locale.getLanguage()));
//		model.addAttribute("lstAdminOfficeMst", lstDepartment);
//		model.addAttribute("mstEmployeeEntity", mstEmployeeEntity);
//		model.addAttribute("payscalelevel", payscalelevel);
//		model.addAttribute("lstsvnbasicpay", lstsvnbasicpay);
//		model.addAttribute("lstAllBankList", mstBankService.lstAllBank());
//		model.addAttribute("lstAllBankBranchList",
//				mstEmployeeService.getBankBranch(String.valueOf(mstEmployeeEntity.getBankCode().toString())));
//		model.addAttribute("lstCommonMstGender",
//				commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_GENDER));
//		model.addAttribute("lstDcpsAccnMaintainby", mstEmployeeService.getDcpsAccnMaintainby());
//		model.addAttribute("lstAccountMaintainby", mstEmployeeService.getAccountMaintainby());
//		model.addAttribute("lstGISApplicable", mstEmployeeService.getGISApplicable());
//		model.addAttribute("lstRelation", mstEmployeeService.getRelation());
//		List<ReligionMstEntity> mstReligionLst = new ArrayList<>();
//		mstReligionLst = commonHomeMethodsService.fetchAllReligions();
//		model.addAttribute("mstReligionLst", mstReligionLst);
//		model.addAttribute("roleId", messages.getRole_id());
//		model.addAttribute("readonly",true);
//		model.addAttribute("empId", empId);
//
//		if(messages.getRole_id()==1) {
//			return "/views/employee/Emp-Change-Dtls";
//		}else {
//			return "/views/employee/Approve-Emp-Change-Dtls";
//			
//		}
//	}
//	
//	@PostMapping("/updateChangeEmpDtls")
//	public String updateChangeEmpDtls(@ModelAttribute("mstEmployeeEntity") @Valid MstEmployeeEntity mstEmployeeEntity,HttpSession session,
//									RedirectAttributes redirectAttributes,Model model,Locale locale,@RequestParam("files") MultipartFile[] files) {
//	
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		
//		if(mstEmployeeEntity.getGender()=='1'){
//			mstEmployeeEntity.setGender('M');
//		}else if(mstEmployeeEntity.getGender()=='2'){
//			mstEmployeeEntity.setGender('F');
//		}else if(mstEmployeeEntity.getGender()=='3') {
//			mstEmployeeEntity.setGender('T');
//		}
//
//		int roleid= messages.getRole_id();
//		int  mstEmployeeEntityResponse = empChangeDetailsService.updateChangeEmpDtls(mstEmployeeEntity,files,roleid,messages);
//		
//			if(mstEmployeeEntityResponse > 0) {
//			redirectAttributes.addFlashAttribute("message","SUCCESS");
//		}
//		return "redirect:/level1/changeDetails"; /*redirects to controller URL*/
//	}
//	
//	/*@GetMapping("/ApprovOrRejectChngdtls")
//	public String ApprovOrRejectChngdtls(  @ModelAttribute("mstEmployeeEntity") MstEmployeeEntity mstEmployeeEntity,
//			Model model, Locale locale, HttpSession session) throws ParseException {
//
//		String message = (String) model.asMap().get("message");
//
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		// model.addAttribute("brokenPeriodModel", brokenPeriodModel);
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
//		//List<ChangeBasicDtlsModel> lstChangeBasic = new ArrayList<>();
//
//		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
//		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
//		//lstChangeBasic= empchangeBasicDtls.findEmpChangeBasicDtls(messages.getUserName());
//		model.addAttribute("menuList", menuList);
//		model.addAttribute("subMenuList", subMenuList);
//		//model.addAttribute("lstChangeBasic", lstChangeBasic);
//
//
//		if (message != null && message.equals("SUCCESS")) {
//			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
//				model = CommonUtils.initModel(CommonConstants.Message.SAVEDRAFT, STATUS.SUCCESS, model);
//			} else {
//				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
//			}
//		}
//		model.addAttribute("language", locale.getLanguage());
//		SimpleDateFormat sdf =new SimpleDateFormat("dd/MM/yyyy");
//		
//		List<MstEmployeeEntity> empLst =empChangeDetailsService.findEmpLstforApprovChngDtls();
//		
//		model.addAttribute("empLst", empLst);
//		model.addAttribute("roleId", messages.getRole_id());
//		model.addAttribute("readonly","readonly");
//		model.addAttribute("mstEmployeeEntity", mstEmployeeEntity);
//		
//		return "/views/employee/changeDetails";
//	}
//	
//	@PostMapping("/approveRejectChangeEmpDtls")
//	public String approveRejectChangeEmpDtls(@ModelAttribute("mstEmployeeEntity") @Valid MstEmployeeEntity mstEmployeeEntity,HttpSession session,
//									RedirectAttributes redirectAttributes,Model model,Locale locale,@RequestParam("files") MultipartFile[] files) {
//	
//		UserInfo messages = (UserInfo) session.getAttribute("MY_SESSION_MESSAGES");
//		
//		
//		//mstEmployeeEntity.setDdoCode(ddoCode);
//		
//		
//		int roleid= messages.getRole_id();
//		int  mstEmployeeEntityResponse = empChangeDetailsService.updateChangeEmpDtls(mstEmployeeEntity,files,roleid,messages);
//		
//			if(mstEmployeeEntityResponse > 0) {
//			redirectAttributes.addFlashAttribute("message","SUCCESS");
//		}
//		return "redirect:/level1/ApprovOrRejectChngdtls"; redirects to controller URL
//	}*/
//	
	
}
	
		
	


