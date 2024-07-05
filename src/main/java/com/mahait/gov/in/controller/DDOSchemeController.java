package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.MstDcpsBillGroup;
import com.mahait.gov.in.model.NewRegDDOModel;
import com.mahait.gov.in.model.OrgDdoMst;
import com.mahait.gov.in.model.RltDcpsDdoScheme;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.DDOSchemeService;

@Controller
@RequestMapping("/ddo")
public class DDOSchemeController {
	
	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;
	
	@Autowired
	DDOSchemeService ddoSchemeService;
	
	
	List<NewRegDDOModel> emplist = new ArrayList<>();
	
	@GetMapping("/loadDdoSchemesAndBillGroups")
	public String loadDdoSchemesAndBillGroups(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,
			 Model model, Locale locale,
			HttpSession session) {
		
		
		String message=(String) model.asMap().get("message");
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		//model.addAttribute("", getDesignation())
    	/*emplist = empWiseCityClassService.findAllEmployee(messages.getUserName());
    	newRegDDOModel.setEmplist(emplist);
	*/
		
    	/*model.addAttribute("lstDDO",newRegDDOService.findLvl1DDOCode(messages.getUserName()));	
    	model.addAttribute("empLst",newRegDDOService.findEmpLst(messages.getUserName()));	*/
    	/*model.addAttribute("lstDistrictLst",empWiseCityClassService.lstGetAllDistrict());		
    	model.addAttribute("lstTaluka",empWiseCityClassService.lstGetAllTaluka());		
    	///model.addAttribute("lstTalukaLst",empWiseCityClassService.findCityClasssLst());		
*/		
		String lStrDDOCode = ddoSchemeService.getDdoCodeForDDO(messages.getCreatedByPost());
		
		List<OrgDdoMst> ddoListForScheme = ddoSchemeService.getDDOCodeByLoggedInlocId(1L);
		OrgDdoMst ddoMst  = null;
	   	
		if(ddoListForScheme!=null && ddoListForScheme.size()>0) {
		 ddoMst = ddoListForScheme.get(0);
   		}
		String ddoCode = null;
   		if(ddoMst!=null)
   		{
   			ddoCode = ddoMst.getDdoCode();
   		}
   		
   		String Type =ddoCode.substring(0,2);
		Long TypeOfSchool=Long.valueOf(Type);
		String typeOfOffice=null;
		if(TypeOfSchool !=2)
   		{
   			typeOfOffice="otherThanZp";
   		}
   		else 
   		 {
   			typeOfOffice="ZP";
   		}
		List DcpsDdoSchemeList = null;
		DcpsDdoSchemeList = ddoSchemeService.getAllSchemesForDDO(lStrDDOCode);
		String districtID=ddoSchemeService.districtName(ddoCode);
		List talukaList=ddoSchemeService.allTaluka(districtID);
   		String talukaId=null;
    	   String ddoSelected=null;
    	  /// List ddoList=lObjDcpsCommonDAO.getSubDDOs(SessionHelper.getPostId(inputMap),talukaId,ddoSelected);
    	   
    		Integer totalRecords = DcpsDdoSchemeList.size();
    		List role=ddoSchemeService.getpostRole(messages.getCreatedByPost());
			Iterator IT = role.iterator();
			Integer o= null;
			String isLvl2= "no";
			while(IT.hasNext()){
				o= (Integer)IT.next();
				if(o.toString().equals("700017") || o.toString().equals("700002"))
					isLvl2="yes";
				
			}
			
			model.addAttribute("talukaList", talukaList);
	   		model.addAttribute("talukaId", talukaId);
	   		model.addAttribute("ddoSelected", ddoSelected);
	   		//model.addAttribute("displayAddNewEntry", displayAddNewEntry);
			
		//	model.addAttribute("DDOlist", ddoList);
			model.addAttribute("schemelist", DcpsDdoSchemeList);
			model.addAttribute("totalRecords", totalRecords);
			model.addAttribute("isLvl2", isLvl2);
			
		model.addAttribute("newRegDDOModel", newRegDDOModel);
		model.addAttribute("message", message);
		
		return "/views/DDOScheme";
		
		
	}
	
	@PostMapping("/addSchemesAndBillGroupsToDdo")
	public String addSchemesAndBillGroupsToDdo(@ModelAttribute("newRegDDOModel") NewRegDDOModel newRegDDOModel,HttpSession session,
	RedirectAttributes redirectAttributes,Model model,Locale locale) {
		return null;

	///	DcpsCommonDAO lObjDcpsCommonDAO = new DcpsCommonDAOImpl(null, serv.getSessionFactory());
		//RltDcpsDdoScheme lObjDcpsDdoSchemeVO = (RltDcpsDdoScheme) inputMap.get("DcpsDdoScheme");
		///MstDcpsBillGroup lObjMstDcpsBillGroupVO = (MstDcpsBillGroup) inputMap.get("dcpsddobillgroup");

}
}