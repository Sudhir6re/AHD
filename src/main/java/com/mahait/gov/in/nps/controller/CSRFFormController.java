package com.mahait.gov.in.nps.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.controller.BaseController;
import com.mahait.gov.in.entity.EmployeeDetailEntity;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstEmployeeEntity;
import com.mahait.gov.in.entity.MstRoleEntity;
//import com.mahait.gov.in.entity.MstEmployeeNPSEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.MstDistrictModel;
import com.mahait.gov.in.model.MstStateModel;
import com.mahait.gov.in.nps.entity.FormS1DetailsEntity;
import com.mahait.gov.in.nps.model.CSRFFormModel;
import com.mahait.gov.in.nps.sevice.CSRFFormService;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.LocationMasterService;
import com.mahait.gov.in.service.MstBankService;
import com.mahait.gov.in.service.MstEmployeeService;

@Controller
@RequestMapping("/ddo")
@PropertySource(value = { "classpath:application.properties" })
public class CSRFFormController extends BaseController {
	// protected final Log logger = LogFactory.getLog(getClass());
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	CSRFFormService csrfFormService;

	@Autowired
	LocationMasterService locationMasterService;

	@Autowired
	MstBankService mstBankService;

	@Autowired
	private Environment environment;

	@Autowired
	MstEmployeeService mstEmployeeService;

//	@Autowired
//	private NSDLIntegration nSDLIntegration;

	String ddoCode;
	String batchId;
	String url;
	String flag;
	String refCode;
	String dtoUserId;

	private static String OUTPUT_ZIP_FILE = "D:/output/MJP/";
	private static String OUTPUT_ZIP_Contri_FILE = "D:/output/MJP/Contribution/";

	@GetMapping("/mstCSRFForm")
	public String mstCSRFForm(@ModelAttribute("csrfFormModel") CSRFFormModel csrfFormModel, Model model, Locale locale,
			HttpSession session) {

		String message = (String) model.asMap().get("message");

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
	
		addMenuAndSubMenu(model, messages);

		model.addAttribute("lstRelation", mstEmployeeService.getRelation());

		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		if (message != null && message.equals("UPDATED")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.UPDATED_ENGLSH, STATUS.SUCCESS, model);
			}
		}
		model.addAttribute("language", locale.getLanguage());
		model.addAttribute("lstEmpList", csrfFormService.findAllEmployees(messages.getUserName()));
		return "/views/nps/CSRF-form";
	}

	@GetMapping("/updateCSRFForm/{empId}")
	public ModelAndView updateCSRFForm(HttpServletRequest request, Model model, HttpServletResponse response,
			Locale locale, HttpSession session,
			@ModelAttribute("formS1DetailsEntity") FormS1DetailsEntity formS1DetailsEntity, @PathVariable int empId) {
		
		String message = (String) model.asMap().get("message");
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("language", locale.getLanguage());
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		modelAndView.addObject("sessionMessages", messages.getUserId());
		modelAndView.addObject("userName", messages.getUserName());
		modelAndView.addObject("context", request.getContextPath());
		MstRoleEntity levelRoleVal = messages.getMstRoleEntity();
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
//		menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, locale.getLanguage());
//		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, locale.getLanguage());
//		modelAndView.addObject("menuList", menuList);
//		modelAndView.addObject("subMenuList", subMenuList);
//		modelAndView.addObject("message", message);
		addMenuAndSubMenu(model, messages);

		
		List<MstCommonEntity> cityClassList = new ArrayList<>();
		cityClassList = commonHomeMethodsService.findCommonMstByCommonCode("CITYCLASS");
		model.addAttribute("cityClassList", cityClassList);

		model.addAttribute("lstGISGroup", mstEmployeeService.getGISGroup());

		List<MstStateModel> listStatemdl = new ArrayList<MstStateModel>();
		List<Object[]> listState = locationMasterService.findAllStates(1);
		for (Iterator iterator = listState.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			MstStateModel mstStateModel = new MstStateModel();
			if (objects[3] != null)
				mstStateModel.setStateCode(Integer.parseInt(String.valueOf(objects[3])));
			mstStateModel.setStateNameEn(String.valueOf(objects[2]));
			listStatemdl.add(mstStateModel);
		}

		model.addAttribute("lstAllState", listStatemdl);
		MstEmployeeEntity mstEmployeeEntity = csrfFormService.findEmployeeDtlsBySevaarthId(empId);
		System.out.println("--------------" + mstEmployeeEntity);
		
		model.addAttribute("lstRelation", mstEmployeeService.getRelation());
		List<MstDistrictModel> listDistrictemdl = new ArrayList<MstDistrictModel>();

		List<Object[]> listDistrict = locationMasterService.findAllDistricts(27);
		// logger.info("distric code list size="+listDistrict.size());
		for (Iterator iterator = listDistrict.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			MstDistrictModel mstDistrictModel = new MstDistrictModel();
	//	mstDistrictModel.setState(String.valueOf(objects[3]));
		//mstDistrictModel.setStateName(String.valueOf(objects[4]));
			listDistrictemdl.add(mstDistrictModel);
			// logger.info("distric code list object4="+String.valueOf(objects[4]));
		}
		model.addAttribute("lstAllDistrict", listDistrictemdl);
		model.addAttribute("lstAllBankList", mstBankService.lstAllBank());


		if (mstEmployeeEntity != null) {
			
			 formS1DetailsEntity.setDcpsId(mstEmployeeEntity.getDcpsNo());
			    formS1DetailsEntity.setSevarthId(mstEmployeeEntity.getSevaarthId());
			   // formS1DetailsEntity.setDesignation(mstEmployeeEntity.getDesignationCode());
			    formS1DetailsEntity.setTitle(String.valueOf(mstEmployeeEntity.getSalutation()));
			    formS1DetailsEntity.setEmpName(mstEmployeeEntity.getEmployeeFullNameEn());
			    formS1DetailsEntity.setEmpFirstName(mstEmployeeEntity.getEmployeeFNameEn());
			    formS1DetailsEntity.setEmpMiddleName(mstEmployeeEntity.getEmployeeMNameEn());
			    formS1DetailsEntity.setEmpLastName(mstEmployeeEntity.getEmployeeLNameEn());
			    
			    String fullName = mstEmployeeEntity.getEmployeeFatherHubandName(); 
			    if (fullName != null && !fullName.trim().isEmpty()) {
			        String[] nameParts = fullName.trim().split("\\s+");
			        String firstName = nameParts.length > 0 ? nameParts[0] : "";  
			        String middleName = nameParts.length > 2 ? nameParts[1] : "";
			        String lastName = nameParts.length > 1 ? nameParts[nameParts.length - 1] : "";
			        formS1DetailsEntity.setFatherNameFirst(firstName);
			        formS1DetailsEntity.setFatherNameMiddle(middleName);
			        formS1DetailsEntity.setFatherNameLast(lastName);
			    } else {
			        formS1DetailsEntity.setFatherNameFirst(null);
			        formS1DetailsEntity.setFatherNameMiddle(null);
			        formS1DetailsEntity.setFatherNameLast(null);
			    }
			    formS1DetailsEntity.setMotherNameFirst(mstEmployeeEntity.getEmployeeMotherName());
			    
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			    Date empDob = (Date) mstEmployeeEntity.getDob();
			    formS1DetailsEntity.setEmpDob(dateFormat.format(empDob));
			    
			    formS1DetailsEntity.setGender(String.valueOf(mstEmployeeEntity.getGender()));
			    
			    //formS1DetailsEntity.setDtoCode(mstEmployeeEntity.getD);
			    formS1DetailsEntity.setDdoCode(mstEmployeeEntity.getDdoCode());
			    formS1DetailsEntity.setPanNo(mstEmployeeEntity.getPanNo());
			   // formS1DetailsEntity.setAadharNo(mstEmployeeEntity.getaa);
			    formS1DetailsEntity.setPresentAddressFlatNo(mstEmployeeEntity.getFlatUnitNo());
			    formS1DetailsEntity.setPresentAddressBuilding(mstEmployeeEntity.getBuildingName());
			    
			    formS1DetailsEntity.setPresentAddressTaluka(String.valueOf(mstEmployeeEntity.getTalukaCode()));
			    
			    //formS1DetailsEntity.setPresentAddressLandmark(mstEmployeeEntity.getLandmark());
			    formS1DetailsEntity.setPresentAddressDistrict(mstEmployeeEntity.getDistrict());
			    formS1DetailsEntity.setPresentAddressState(mstEmployeeEntity.getState());
			    formS1DetailsEntity.setPresentAddressCountry(mstEmployeeEntity.getCountry());
			    
			    formS1DetailsEntity.setPresentAddressPincode(String.valueOf(mstEmployeeEntity.getPinCode()));
			    formS1DetailsEntity.setPermanentAddressFlatNo(mstEmployeeEntity.getEmpPermanentFlatUnitNo());
			    formS1DetailsEntity.setPermanentAddressBuilding(mstEmployeeEntity.getEmpPermanentBuildingName());
			    formS1DetailsEntity.setPermanentAddressTaluka(mstEmployeeEntity.getEmpPermanentLocality());
			    //formS1DetailsEntity.setPermanentAddressLandmark(mstEmployeeEntity.getPermanentAddressLandmark());
			    formS1DetailsEntity.setPermanentAddressDistrict(mstEmployeeEntity.getEmpPermanentDistrict());
			    formS1DetailsEntity.setPermanentAddressState(mstEmployeeEntity.getEmpPermanentState());
			    formS1DetailsEntity.setPermanentAddressCountry(mstEmployeeEntity.getEmpPermanentCountry());
			    formS1DetailsEntity.setPermanentAddressPincode(mstEmployeeEntity.getEmpPermanentPinCode());
			  //  formS1DetailsEntity.setPhoneNo(mstEmployeeEntity.getLineNo());
			    
			    formS1DetailsEntity.setMobileNo(String.valueOf(mstEmployeeEntity.getMobileNo1()));
			    
			    formS1DetailsEntity.setEmailId(mstEmployeeEntity.getEmailId());
			   // formS1DetailsEntity.setSmsSubFlag(mstEmployeeEntity.getSmsSubFlag());
			   // formS1DetailsEntity.setEmailSubFlag(mstEmployeeEntity.getEmailSubFlag());
			  //  formS1DetailsEntity.setHindiSubFlag(mstEmployeeEntity.getHindiSubFlag());
			    
			    SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd-MM-yyyy"); // or any desired format
			    Date empDoj = (Date) mstEmployeeEntity.getDoj();
			    formS1DetailsEntity.setEmpDoj(dateFormat1.format(empDoj));
			    
			    SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd-MM-yyyy"); 
			    Date empDor = (Date) mstEmployeeEntity.getEmpServiceEndDate();
			    formS1DetailsEntity.setEmpDor(dateFormat1.format(empDor));
			    
			    formS1DetailsEntity.setEmpClass(String.valueOf(mstEmployeeEntity.getEmpClass()));
			    formS1DetailsEntity.setEmpDept(mstEmployeeEntity.getDepartmentNameEn());
			   // formS1DetailsEntity.setEmpMinistry(mstEmployeeEntity.getempMinistry());
			    formS1DetailsEntity.setEmpDdo(mstEmployeeEntity.getDdoCode());
			    formS1DetailsEntity.setPayScale(mstEmployeeEntity.getPayScaleDesc());
			    formS1DetailsEntity.setBasicSalary(String.valueOf(mstEmployeeEntity.getBasicPay()));
			    formS1DetailsEntity.setPpan(mstEmployeeEntity.getPpanNo());
			  //  formS1DetailsEntity.setBankDetailsFlag(mstEmployeeEntity.getisFlag());
			    //formS1DetailsEntity.setBankDetailsType(mstEmployeeEntity.getBankType());
			    formS1DetailsEntity.setBankAccountNo(mstEmployeeEntity.getBankAcntNo());
			    formS1DetailsEntity.setBankName(mstEmployeeEntity.getEmployeeBankName());
			    formS1DetailsEntity.setBankBranch(mstEmployeeEntity.getEmployeeBankBranchName());
			    formS1DetailsEntity.setBankAddress(mstEmployeeEntity.getEmployeeBankBankAddress());
			   // formS1DetailsEntity.setBankState(mstEmployeeEntity.getEmplyeeBankState());
			    //formS1DetailsEntity.setBankCountry(mstEmployeeEntity.getEmplyeeBankCountry());
			    formS1DetailsEntity.setBankPin(mstEmployeeEntity.getEmployeeBankPinCode());
			   // formS1DetailsEntity.setEmpNumberNominee(mstEmployeeEntity.getNumberNominee());
			    //formS1DetailsEntity.setNomineeName1(mstEmployeeEntity.getEmpNomineeName1());
			   // formS1DetailsEntity.setNomineeDob1(mstEmployeeEntity.getNomineeDob1());
			    //formS1DetailsEntity.setNomineeRelation1(mstEmployeeEntity.getNomineeRelation1());
			    //formS1DetailsEntity.setNomineePercentShare1(mstEmployeeEntity.getNomineePercentShare1());
			    formS1DetailsEntity.setNomineeGuardianName1(mstEmployeeEntity.getEmpNominee1GuardName());
			    formS1DetailsEntity.setNomineeRenderCondition1(mstEmployeeEntity.getEmpNominee1InvalidCondn());
			   // formS1DetailsEntity.setNomineeName2(mstEmployeeEntity.getNomineeName2());
			   // formS1DetailsEntity.setNomineeDob2(mstEmployeeEntity.getNomineeDob2());
			   // formS1DetailsEntity.setNomineeRelation2(mstEmployeeEntity.getNomineeRelation2());
			   // formS1DetailsEntity.setNomineePercentShare2(mstEmployeeEntity.getNomineePercentShare2());
			    formS1DetailsEntity.setNomineeGuardianName2(mstEmployeeEntity.getEmpNominee2GuardName());
			    formS1DetailsEntity.setNomineeRenderCondition2(mstEmployeeEntity.getEmpNominee2InvalidCondn());
			  //  formS1DetailsEntity.setNomineeName3(mstEmployeeEntity.getNomineeName3());
			   // formS1DetailsEntity.setNomineeDob3(mstEmployeeEntity.getNomineeDob3());
			   // formS1DetailsEntity.setNomineeRelation3(mstEmployeeEntity.getNomineeRelation3());
			   // formS1DetailsEntity.setNomineePercentShare3(mstEmployeeEntity.getNomineePercentShare3());
			    formS1DetailsEntity.setNomineeGuardianName3(mstEmployeeEntity.getEmpNominee2GuardName());
			    formS1DetailsEntity.setNomineeRenderCondition3(mstEmployeeEntity.getEmpNominee3InvalidCondn());
			    formS1DetailsEntity.setPranNo(mstEmployeeEntity.getPranNo());
			    
			    Date createdDate = (Date) mstEmployeeEntity.getCreatedDate();
			    LocalDateTime localDateTime = createdDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
			    formS1DetailsEntity.setCreatedDate(localDateTime);
			    
			    formS1DetailsEntity.setBankIfscCode(mstEmployeeEntity.getIfscCode());
			    formS1DetailsEntity.setEmpMaritalStatus(String.valueOf(mstEmployeeEntity.getMaritalStatus()));
			    //formS1DetailsEntity.setDdoRegNo(mstEmployeeEntity.getreg());
			   // formS1DetailsEntity.setFileVerifyStatus(mstEmployeeEntity.getFileVerifyStatus());
			 //   formS1DetailsEntity.setNsdlStatus(mstEmployeeEntity.getNsdlStatus());
			 //   formS1DetailsEntity.setBatchId(mstEmployeeEntity.getBatchId());
			 //   formS1DetailsEntity.setPhotoAttachmentName(mstEmployeeEntity.getPhotoAttachmentName());
			   // formS1DetailsEntity.setSignAttachmentName(mstEmployeeEntity.getSignAttachmentName());
			  //  formS1DetailsEntity.setImagesLocationPath(mstEmployeeEntity.getImagesLocationPath());
			  //  formS1DetailsEntity.setPranStatus(mstEmployeeEntity.getPranStatus());
			 //   formS1DetailsEntity.setNomineeAddress1(mstEmployeeEntity.getNomineeAddress1());
			//    formS1DetailsEntity.setNomineeAddress2(mstEmployeeEntity.getNomineeAddress2());
			 //   formS1DetailsEntity.setNomineeAddress3(mstEmployeeEntity.getNomineeAddress3());
			 //   formS1DetailsEntity.setStatusFlag(mstEmployeeEntity.getStatusFlag());
			             
			         
		model.addAttribute("lstAllDistrict", listDistrictemdl);
	//	model.addAttribute("paycomission", formS1DetailsEntity.getPayCommissionCode());
		modelAndView.addObject("formS1DetailsEntity", formS1DetailsEntity);
		modelAndView.setViewName("/views/UpdateCSRF-Form");
		
	}
		return modelAndView;
	}}
	