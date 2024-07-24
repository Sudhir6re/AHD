package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.common.CommonConstants;
import com.mahait.gov.in.entity.CmnDistrictMst;
import com.mahait.gov.in.entity.CmnLookupMst;
import com.mahait.gov.in.entity.CmnStateMst;
import com.mahait.gov.in.entity.CmnTalukaMst;
import com.mahait.gov.in.entity.DdoOffice;
import com.mahait.gov.in.entity.MstCommonEntity;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DDOInfoService;

@Controller
@RequestMapping("/ddo")
public class DDOOfficeController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	DDOInfoService ddoInfoService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/loadApproveDdoOffice")
	public String loadApproveDdoOffice(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel, Model model, Locale locale,
			HttpSession session) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		String ddoCode=messages.getUserName();
		String lStrDdoOffice = "Yes";
		List<CmnStateMst> lstState = ddoInfoService.getStateLst(1L);
		List<CmnDistrictMst> lstDistrict=  ddoInfoService.getDistrictlst(15L);
		
		List<CmnLookupMst> dcpsOfficeClassId=commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.DCPS_OFFICE_CLASS);
		List<CmnLookupMst>ddoOffClass=ddoInfoService.findDDOOffClass(dcpsOfficeClassId.get(0).getLookupId());
		///model.addAttribute("lstAdvance", commonHomeMethodsService.findCommonMstByCommonCode(CommonConstants.COMMONMSTTABLE.DCPS_OFFICE_CLASS));
		List<DdoOffice> lLstSavedOffices = ddoInfoService.getAllOffices(ddoCode);
		if(lLstSavedOffices!=null) {
			if(lLstSavedOffices.get(0).getDcpsDdoOfficeDdoFlag()=="Yes") {
				lStrDdoOffice = "No";
			}
		}
		String districtID=ddoInfoService.getDistrictId(ddoCode);
		List<CmnTalukaMst> lstTaluka=  ddoInfoService.getTalukalst();
		/*String message=(String) model.asMap().get("message");
		*/
		/*List<ZpRltDdoMap> zpDDOOfficelst = zpDDOOfficeService
				.getAllDDOOfficeDtlsDataByPostID(messages.getCreatedByPost().getCreatedByPost().getPostId());
		
		
	///	model.addAttribute("message", message);
	///	
		model.addAttribute("zpDDOOfficelst", zpDDOOfficelst);*/
		model.addAttribute("lstState", lstState);
		model.addAttribute("newRegDDOModel", newRegDDOModel);
		model.addAttribute("ddoOffClass", ddoOffClass);
		model.addAttribute("lstDistrict", lstDistrict);
		model.addAttribute("lstTaluka", lstTaluka);
		model.addAttribute("lstTown", ddoInfoService.getLstTown());
		model.addAttribute("lstApprDdoOffice", ddoInfoService.getDDOOffForApproval(messages.getUserName()));
		
		return "/views/ApproveDDOOffice";
	}
/*	
	@RequestMapping(value = "/getTalukabyDistrictId/{distId}", consumes = {"application/json" }, headers = "Accept=application/json",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<CmnTalukaMst>> getIfscCodeByBranchId(@PathVariable String distId) {
			return ResponseEntity.ok(ddoInfoService.getTalukalst(distId));
}*/
	@GetMapping(value="/getAlreadySavedDataforDDO/{ddoCode}")
	public @ResponseBody List<Object[]> getAlreadySavedDataforDDO(@PathVariable String ddoCode,Model model,Locale locale)
	{
		List<Object[]> lst =  ddoInfoService.getAlreadySavedDataforDDO(ddoCode);
		return lst;
		
		
    }
	
	@GetMapping("/updateApproveRejectStatus/{ddoCode}/{flag}/{cityClass}")
	public String updateApproveRejectStatus(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,@PathVariable String ddoCode,
			@PathVariable int flag,@PathVariable String cityClass,Model model,Locale locale,HttpSession session,HttpServletRequest request, Object paybillHeadMpgRepo) {
		
		DdoOffice orgUserMst  =  ddoInfoService.updateApproveRejectStatus(ddoCode,flag,cityClass);
		
		if(orgUserMst!=null)
			model.addAttribute("message","Approved Successfully");
			return "/views/approveDDOOfficeView";
		///}
	} 
	
	}

