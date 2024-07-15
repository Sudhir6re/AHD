package com.mahait.gov.in.controller;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ReligionMstEntity;
import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.model.MstStateModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.CreateAdminOfficeService;
import com.mahait.gov.in.service.DDOScreenService;
import com.mahait.gov.in.service.EmpChangeDetailsService;
import com.mahait.gov.in.service.LocationMasterService;
import com.mahait.gov.in.service.MstBankService;
import com.mahait.gov.in.service.MstDepartmentService;
import com.mahait.gov.in.service.MstDesignationService;
import com.mahait.gov.in.service.MstEmployeeService;


@Controller
@RequestMapping("/ddoast")
@PropertySource(value = { "classpath:application.properties" })
public class EmployeeConfigurationController {

	@Autowired
	CreateAdminOfficeService createAdminOfficeService;

//	@Autowired
//	MstCadreService mstCadreService;


	@Autowired
	MstEmployeeService mstEmployeeService;

	@Autowired
	MstDesignationService mstDesignationService;

	@Autowired
	MstBankService mstBankService;
	
//	@Autowired
//	MstBankBranchService mstBankBranchService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	LocationMasterService locationMasterService;

	@Autowired
	MstDepartmentService mstDepartmentService;

//	@Autowired
//	MstSubDepartmentService mstSubDepartmentService;

	@Autowired
	private Environment environment;

	@Autowired
	EmpChangeDetailsService empChangeDetailsService;
	
//	@Autowired
//	MstSubCorporationService mstSubCorporationService;
	
	
	@Autowired
	DDOScreenService dDOScreenService;
	

	

//	protected final Log logger = LogFactory.getLog(getClass());
	// @PostMapping("/employeeConfiguration")
	@RequestMapping(value = "/employeeConfiguration", method = { RequestMethod.GET })
	public String employeeConfiguration(@ModelAttribute("mstEmployeeModel") MstEmployeeModel mstEmployeeModel,
			Model model, Locale locale, HttpSession session) {

		String message = (String) model.asMap().get("message");
		mstEmployeeModel.setAction("Save");

		
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		mstEmployeeModel.setDdoCode(messages.getUserName());
		
		
		List<OrgDdoMst> lstDDO=dDOScreenService.findDDOByUsername(messages.getUserName());
		
		
//		model.addAttribute("lstDeptDataTable", mstDepartmentService.findAllDepartment());
//				.filter(p -> p.getIsActive() == '1')
//				//.filter(p ->p.getDepartmentCode()==lstDDO.get(0).getDepartmentCode()) 
//				.collect(Collectors.toList()));
		
		
		
		
	//	model.addAttribute("lstSubDepartment", mstSubCorporationService.findAllSubCorporation());
		
		
		/*List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(),
				messages.getUserName().toString());
		for (Iterator iterator = lstDepartment.iterator(); iterator.hasNext();) {
			DDOScreenModel ddoScreenModel = (DDOScreenModel) iterator.next();
//			mstEmployeeModel.setParentAdminDepartmentId(BigInteger.valueOf(ddoScreenModel.getDepartmentId()));
			mstEmployeeModel.setParentFieldDepartmentId(BigInteger.valueOf(ddoScreenModel.getSubDepartmentId()));

		}*/
		/*List<Object[]> lstInstitueDtls = mstEmployeeService.getInstitueDtls(messages.getUserName());
		if (!lstInstitueDtls.isEmpty()) {
			for (Object[] objLst : lstInstitueDtls) {
				mstEmployeeModel.setInstName(objLst[0].toString());
				mstEmployeeModel.setInsttelnoone(objLst[1].toString());
				mstEmployeeModel.setInsttelnotwo(objLst[2].toString());
				mstEmployeeModel.setInstemail(objLst[3].toString());
			}
		}*/
		
		
		
//		model.addAttribute("parentAdminDepartmentId", lstDDO.get(0).getDepartmentCode());
//		model.addAttribute("parentFieldDepartmentId", lstDDO.get(0).getSubDepartmentCode());
//		
//		mstEmployeeModel.setParentAdminDepartmentId(BigInteger.valueOf(lstDDO.get(0).getDepartmentCode()));
//		mstEmployeeModel.setParentFieldDepartmentId((BigInteger.valueOf(lstDDO.get(0).getSubDepartmentCode())));
//		
		
		
		model.addAttribute("mstEmployeeModel", mstEmployeeModel);
//		List<TopicModel> menuList = new ArrayList<>();
//		List<TopicModel> subMenuList = new ArrayList<>();
		

//		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getRole_id(), locale.getLanguage());
//		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(messages.getRole_id(), locale.getLanguage());
		
		List<ReligionMstEntity> mstReligionLst = new ArrayList<>();
		mstReligionLst = commonHomeMethodsService.fetchAllReligions();
		model.addAttribute("mstReligionLst", mstReligionLst);
//		
//		model.addAttribute("menuList", menuList);
//		model.addAttribute("subMenuList", subMenuList);
		


		if (message != null && message.equals("SUCCESS")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.SAVEDRAFT, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		if (message != null && message.equals("FRWDDDO")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.FRWDDDO, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.ADDED_MARATHI, STATUS.SUCCESS, model);
			}
		}
		
		
		List<MstCommonEntity> cityClassList = new ArrayList<>();
		//cityClassList = commonHomeMethodsService.findCommonMstByCommonCode("CITYCLASS");
		model.addAttribute("cityClassList", cityClassList);
		
	

	///	model.addAttribute("lstAdminOfficeMst", lstDepartment);
	
		
		
		
		// findDDOScreenDataTable(locale.getLanguage()),messages.getUserName());
		///.addAttribute("lstCadreMst", mstEmployeeService.getCadreMstData(locale.getLanguage()));
		model.addAttribute("language", locale.getLanguage());

		List<MstPayCommissionEntity> lstddcPayCommission = mstDesignationService.findAllPayCommission();
		model.addAttribute("lstddcPayCommission", lstddcPayCommission);
		model.addAttribute("lstDesignation", mstDesignationService.getDesignationMstData(locale.getLanguage()));
		
		/*model.addAttribute("lstDcpsAccnMaintainby", mstEmployeeService.getDcpsAccnMaintainby());
		
		
		model.addAttribute("lstAccountMaintainby", mstEmployeeService.getAccountMaintainby());
		// model.addAttribute("lstCurntDepartment",
		// mstDepartmentService.findMstDeptByDeptId(15));
		model.addAttribute("lstCurntDepartment", lstDepartment);
		model.addAttribute("lstGISGroup", mstEmployeeService.getGISGroup());
		model.addAttribute("lstGISApplicable", mstEmployeeService.getGISApplicable());
		model.addAttribute("lstRelation", mstEmployeeService.getRelation());*/
		model.addAttribute("lstAllBankList", mstBankService.lstAllBank());
		model.addAttribute("lstCommonMstSalutation", commonHomeMethodsService
				.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_SALUTATION));
		model.addAttribute("lstCommonMstGIS", commonHomeMethodsService
				.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_GIS));
		model.addAttribute("lstCommonMstGender",
				commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_GENDER));
		List<MstStateModel> listStatemdl = new ArrayList<MstStateModel>();
		List<Object[]> listState = locationMasterService.findAllStates(1);
		for (Iterator iterator = listState.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			MstStateModel mstStateModel = new MstStateModel();
			mstStateModel.setStateCode(Integer.parseInt(String.valueOf(objects[1])));
			mstStateModel.setStateNameEn(String.valueOf(objects[2]));
			listStatemdl.add(mstStateModel);
		}

		model.addAttribute("lstAllState", listStatemdl);

		model.addAttribute("language", locale.getLanguage());
		LocalDate date=LocalDate.now();
		model.addAttribute("currentDate",date);

		return "/views/employee-configuration";
	}

	/* @GetMapping(value="/employeeConfiguration/{cadreId}") */

}