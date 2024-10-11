package com.mahait.gov.in.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.common.CommonUtils;
import com.mahait.gov.in.common.CommonConstants.STATUS;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.MstNomineeDetailsEntity;
import com.mahait.gov.in.entity.MstPayCommissionEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.entity.ReligionMstEntity;
import com.mahait.gov.in.model.DDOScreenModel;
import com.mahait.gov.in.model.MstDistrictModel;
import com.mahait.gov.in.model.MstEmployeeModel;
import com.mahait.gov.in.model.MstNomineeDetailsModel;
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

@RequestMapping(value = { "/ddoast", "/ddo" })
@PropertySource(value = { "classpath:application.properties" })
public class ChangeDetailsController extends BaseController{
	
	@Autowired
	CreateAdminOfficeService createAdminOfficeService;

	// @Autowired
	// MstCadreService mstCadreService;

	@Autowired
	MstEmployeeService mstEmployeeService;

	@Autowired
	MstDesignationService mstDesignationService;

	@Autowired
	MstBankService mstBankService;

	// @Autowired
	// MstBankBranchService mstBankBranchService;

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	LocationMasterService locationMasterService;

	@Autowired
	MstDepartmentService mstDepartmentService;

	// @Autowired
	// MstSubDepartmentService mstSubDepartmentService;

	@Autowired
	private Environment environment;

	@Autowired
	EmpChangeDetailsService empChangeDetailsService;

	// @Autowired
	// MstSubCorporationService mstSubCorporationService;

	@Autowired
	DDOScreenService dDOScreenService;

	@RequestMapping(value = "/changeDetails", method = { RequestMethod.GET })
	public String changeDetails(@ModelAttribute("mstEmployeeModel") MstEmployeeModel mstEmployeeModel, Model model,
			Locale locale, HttpSession session) {

		String message = (String) model.asMap().get("message");

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

		if (message != null && message.equals("DRAFTCASE")) {
			if (locale != null && locale.getLanguage().equalsIgnoreCase("en")) {
				model = CommonUtils.initModel(CommonConstants.Message.DRAFTCASE, STATUS.SUCCESS, model);
			} else {
				model = CommonUtils.initModel(CommonConstants.Message.DRAFTCASE, STATUS.SUCCESS, model);
			}
		}

		mstEmployeeModel.setAction("Save");

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		addMenuAndSubMenu(model, messages);
		Long CASE_STATUS = 0l;

		List<MstEmployeeModel> employeeDraftCaseLst = mstEmployeeService.findDraftCaseList(messages, CASE_STATUS);

		model.addAttribute("employeeDraftCaseLst", employeeDraftCaseLst);

		return "/views/employee-draft-case-list";
	}

	@RequestMapping(value = "/viewDraftCaseByEmployeeId/{employeeId}", method = { RequestMethod.GET })
	public String viewDraftCaseByEmployeeId(@PathVariable Long employeeId,
			@ModelAttribute("mstEmployeeModel") MstEmployeeModel mstEmployeeModel, Model model, Locale locale,
			HttpSession session) {

		String message = (String) model.asMap().get("message");

		mstEmployeeModel = mstEmployeeService.getEmployeeinfo(mstEmployeeModel.getEmployeeId());
		mstEmployeeModel.setAction("updateDraftCase");

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		mstEmployeeModel.setDdoCode(messages.getDdoCode());
		long locId = Long.parseLong((String) session.getAttribute("locationId"));

		List<OrgDdoMst> lstDDO = dDOScreenService.findDDOByUsername(messages.getUserName());
		model.addAttribute("lstGISGroup", mstEmployeeService.getGISGroup());

		List<DDOScreenModel> lstDepartment = mstEmployeeService.findDDOScreenDataTable(locale.getLanguage(), locId);

		List<Object[]> districtListAgainstStateId = locationMasterService.findAllDistricts(15);
		model.addAttribute("districtListAgainstStateId", districtListAgainstStateId);
		mstEmployeeModel.setStateCode(15l); // Default state maharashtra
		model.addAttribute("mstEmployeeModel", mstEmployeeModel);

		List<ReligionMstEntity> mstReligionLst = new ArrayList<>();
		mstReligionLst = commonHomeMethodsService.fetchAllReligions();
		model.addAttribute("mstReligionLst", mstReligionLst);

		List<CmnLookupMst> lookupLst = new ArrayList<>();
		lookupLst = commonHomeMethodsService
				.findCommonMstByLookupname(CommonConstants.COMMONMSTTABLE.COMMONCODE_SALUTATIONS);
		model.addAttribute("lstCommonMstSalutation", lookupLst);

		model.addAttribute("lstAdminOfficeMst", lstDepartment);

		model.addAttribute("lstCadreMst", mstEmployeeService.getCadreMstData(locale.getLanguage(), locId));
		model.addAttribute("language", locale.getLanguage());

		List<MstPayCommissionEntity> lstddcPayCommission = mstDesignationService.findAllPayCommission();
		model.addAttribute("lstddcPayCommission", lstddcPayCommission);
		model.addAttribute("lstDesignation", mstEmployeeService.getDesignationMstData(locale.getLanguage(), locId));
		model.addAttribute("lstAppointment", mstEmployeeService.getAppoitnment(locale.getLanguage()));
		model.addAttribute("lstQualification", mstEmployeeService.getQualification(locale.getLanguage()));

		List<CmnLookupMst> lstDcpccnMaintainby = new ArrayList<>();
		lstDcpccnMaintainby = commonHomeMethodsService
				.findCommonMstByLookupname(CommonConstants.COMMONMSTTABLE.COMMONCODE_DCPSACCMAINTAINEDBY);
		model.addAttribute("lstDcpsAccnMaintainby", lstDcpccnMaintainby);

		List<CmnLookupMst> lstAccnMaintainby = new ArrayList<>();
		lstAccnMaintainby = commonHomeMethodsService
				.findCommonMstByLookupname(CommonConstants.COMMONMSTTABLE.COMMONCODE_GPFACCMAINTAINEDBY);
		model.addAttribute("lstAccountMaintainby", lstAccnMaintainby);
		model.addAttribute("lstCurntDepartment", lstDepartment);

		List<CmnLookupMst> lstgisapplicable = new ArrayList<>();
		lstgisapplicable = commonHomeMethodsService
				.findCommonMstByLookupname(CommonConstants.COMMONMSTTABLE.COMMONCODE_GISAPPLICABLE);
		model.addAttribute("lstGISApplicable", lstgisapplicable);

		model.addAttribute("lstGISGroup", mstEmployeeService.getGISGroup());

		model.addAttribute("lstRelation", mstEmployeeService.getRelation());
		model.addAttribute("lstAllBankList", mstBankService.lstAllBank());
		model.addAttribute("lstCommonMstGIS",
				commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.COMMONCODE_GIS));
		List<MstStateModel> listStatemdl = new ArrayList<MstStateModel>();
		List<Object[]> listState = locationMasterService.findAllStates(1);
		for (Iterator iterator = listState.iterator(); iterator.hasNext();) {
			Object[] objects = (Object[]) iterator.next();
			MstStateModel mstStateModel = new MstStateModel();
			mstStateModel.setStateCode(Integer.parseInt(String.valueOf(objects[0])));
			mstStateModel.setStateNameEn(String.valueOf(objects[1]));
			listStatemdl.add(mstStateModel);
		}

		model.addAttribute("lstAllState", listStatemdl);

		model.addAttribute("language", locale.getLanguage());
		LocalDate date = LocalDate.now();
		model.addAttribute("currentDate", date);

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		List<MstNomineeDetailsModel> lstNmnDtls = new ArrayList<MstNomineeDetailsModel>();
		List<MstNomineeDetailsEntity> lstNmnDtlsEntity = null;
		if (mstEmployeeModel.getEmployeeId() != null) {
			lstNmnDtlsEntity = mstEmployeeService.getNominees(mstEmployeeModel.getEmployeeId().toString());
		}
		if (lstNmnDtlsEntity != null)
			for (Iterator iterator = lstNmnDtlsEntity.iterator(); iterator.hasNext();) {
				MstNomineeDetailsEntity mstNomineeDetailsEntity = (MstNomineeDetailsEntity) iterator.next();
				MstNomineeDetailsModel mstNomineeDetailsModel = new MstNomineeDetailsModel();
				mstNomineeDetailsModel.setNomineename(mstNomineeDetailsEntity.getNomineename());
				mstNomineeDetailsModel.setPercent_share(mstNomineeDetailsEntity.getPercent_share());
				mstNomineeDetailsModel.setRelation(mstNomineeDetailsEntity.getRelation());
				mstNomineeDetailsModel.setDob(formatter.format(mstNomineeDetailsEntity.getDob()));
				mstNomineeDetailsModel.setNomineeaddress(mstNomineeDetailsEntity.getNomineeaddress());
				lstNmnDtls.add(mstNomineeDetailsModel);

			}
		model.addAttribute("lstNmnDtls", lstNmnDtls);

		model.addAttribute("lstAllState", listStatemdl);
		List<MstDistrictModel> listDistrictemdl = new ArrayList<MstDistrictModel>();
		try {
			List<Object[]> listDistrict = locationMasterService.findAllDistricts(mstEmployeeModel.getStateCode());
			// logger.info("distric code list size="+listDistrict.size());
			for (Iterator iterator = listDistrict.iterator(); iterator.hasNext();) {
				Object[] objects = (Object[]) iterator.next();
				MstDistrictModel mstDistrictModel = new MstDistrictModel();
				mstDistrictModel.setDistrictId(String.valueOf(objects[0]));
				mstDistrictModel.setDistrictName(String.valueOf(objects[1]));
				listDistrictemdl.add(mstDistrictModel);
				// logger.info("distric code list object4="+String.valueOf(objects[4]));
			}

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
		}

		try {

			model.addAttribute("lstAllBankBranchList",
					mstEmployeeService.getBankBranch(String.valueOf(mstEmployeeModel.getBankId().toString())));
			model.addAttribute("lstCurrentPost", mstEmployeeService
					.GetCurrentPostByLvlTwo(mstEmployeeModel.getDesignationId(), mstEmployeeModel.getDdoCode(), locId));

		} catch (Exception e) {
			// TODO: handle exception
			// e.printStackTrace();
		}

		model.addAttribute("lstAllDistrict", listDistrictemdl);

		List<Object[]> payscalelevel = new ArrayList<Object[]>();
		List<Object[]> lstsixpayscalelevel = new ArrayList<Object[]>();
		List<Object[]> lstsvnbasicpay = new ArrayList<Object[]>();
		List<Object[]> lstpfSeries = new ArrayList<Object[]>();

		if (mstEmployeeModel.getPayCommissionCode() != null && mstEmployeeModel.getPayCommissionCode()!=700005l)
			lstsixpayscalelevel = mstEmployeeService.findEmployeeConfigurationGetSixPayScale(2500341);
		if (mstEmployeeModel.getPayCommissionCode()==700005l) {
			lstsixpayscalelevel = mstEmployeeService.findEmployeeConfigurationGetSixPayScale(2500341);
		}
		if (mstEmployeeModel.getPayCommissionCode()==700005) {
			payscalelevel = mstEmployeeService.findEmployeeConfigurationGetpayscale(8);
		}

		if (mstEmployeeModel.getPayscalelevelId() != null)
			if (!mstEmployeeModel.getPayscalelevelId().equals("") && !mstEmployeeModel.getPayscalelevelId().equals("0"))
				lstsvnbasicpay = mstEmployeeService
						.findEmployeeConfigurationGetsvnbasicpay(mstEmployeeModel.getPayscalelevelId());
		if (mstEmployeeModel.getAccountmaintainby() != null)
			if (!mstEmployeeModel.getAccountmaintainby().equals("")
					&& !mstEmployeeModel.getAccountmaintainby().equals("0")
					&& !(mstEmployeeModel.getAccountmaintainby() != null))
				lstpfSeries = mstEmployeeService.getPfSeries(mstEmployeeModel.getAccountmaintainby());

		model.addAttribute("lstsvnbasicpay", lstsvnbasicpay);
		model.addAttribute("lstpayscalelevel", payscalelevel);
		model.addAttribute("lstsixpayscalelevel", lstsixpayscalelevel);
		model.addAttribute("lstpfSeries", lstpfSeries);
		model.addAttribute("language", locale.getLanguage());

		addMenuAndSubMenu(model, messages);
		return "/views/employee-configuration-draft-form";
	}


	
}
