package com.mahait.gov.in.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.HrPayOfficepostMpg;
import com.mahait.gov.in.entity.HrPayOrderMst;
import com.mahait.gov.in.entity.MstDesignationEntity;
import com.mahait.gov.in.entity.OrgDdoMst;
import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgPostMst;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.PostEntryModel;
import com.mahait.gov.in.repository.OrgDdoMstRepository;
import com.mahait.gov.in.repository.OrgPostDetailsRltRepository;
import com.mahait.gov.in.service.EntryOfPostsService;

@RequestMapping("/ddo")
@Controller
public class EntryOfPostsController {

	@Autowired
	OrgPostDetailsRltRepository orgPostDetailsRltRepository;

	@Autowired
	OrgDdoMstRepository orgDdoMstRepository;

	@Autowired
	EntryOfPostsService entryOfPostsService;
	
	

	@GetMapping("/entryOfPosts")
	public String entryOfPosts(Model model, Locale locale, HttpSession session) {

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
		
		List<OrgPostDetailsRlt> lst = orgPostDetailsRltRepository.findByOrgPostMst(messages.getCreatedByPost());

		List<OrgDdoMst> lstOrgDdoMst = new ArrayList<>();

		if (lst.size() > 0) {
			lstOrgDdoMst = orgDdoMstRepository.findByLocationCode(lst.get(0).getCmnLocationMst().getLocId().toString());

			Long lLngFieldDept = Long.parseLong(lstOrgDdoMst.get(0).getHodLocCode());
			List<MstDesignationEntity> desgList = entryOfPostsService.getActiveDesig(lLngFieldDept);

			model.addAttribute("Designation", desgList);

			List billList = entryOfPostsService.getAllBillsFromLocation(lst.get(0).getCmnLocationMst().getLocId());
			model.addAttribute("billList", billList);

			String ddoCode = null;
			if (lstOrgDdoMst.size() > 0)
				ddoCode = lstOrgDdoMst.get(0).getDdoCode();

			model.addAttribute("ddoCode", ddoCode);
		}
		return "/views/entry-of-posts";
	}

	@GetMapping("/addPosts")
	public String addPosts(Model model, Locale locale, HttpSession session,
			@ModelAttribute("postEntryModel") PostEntryModel postEntryModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		long langId = 0l;
		long locId = 0l;
		long loggedInPostId = 0l;
		OrgDdoMst ddoMst = null;
		String ddoCode = null;
		if (session.getAttribute("locationId")!=null) {
			langId = 1l;
			 locId = Long.parseLong((String) session.getAttribute("locationId"));
			 loggedInPostId = Long.parseLong((String) session.getAttribute("loggedInPost")); 

			List<OrgDdoMst> ddoCodeList = entryOfPostsService.getDDOCodeByLoggedInlocId(locId);

			if (ddoCodeList != null)

				if (ddoCodeList != null && ddoCodeList.size() > 0) {
					ddoMst = ddoCodeList.get(0);
				}

			if (ddoMst != null) {
				ddoCode = ddoMst.getDdoCode();
			}

			Long lLngFieldDept = Long.parseLong(ddoMst.getHodLocCode());

			List<MstDesignationEntity> desgList = entryOfPostsService.getActiveDesig(lLngFieldDept);
			List branchList_en = entryOfPostsService.getAllBranchList(1L);
			model.addAttribute("Branch", branchList_en);

			List<HrPayOrderMst> orderList = entryOfPostsService.getAllOrderData(locId);

			List billList = entryOfPostsService.getAllBillsFromLocation(locId);
			List officeList = entryOfPostsService.getAllOfficesFromDDO(ddoCode);

			List subOfficeList = entryOfPostsService.getSubOfficesFromDDONew(messages.getCreatedByPost().getPostId());

			// code to find the district
			String districtID = entryOfPostsService.districtName(ddoCode);
			// code to find the taluka
			List talukaList = entryOfPostsService.allTaluka(districtID);
			String talukaId = null;
			String ddoSelected = null;

			List DDOdtls = entryOfPostsService.getSubDDOsOffc(loggedInPostId, talukaId, ddoSelected);

			int i = 0;
			while (i < DDOdtls.size()) {
				i++;
			}
			model.addAttribute("DDOlist", DDOdtls);
			List<Object[]> subList = entryOfPostsService.getSubjectList();
			model.addAttribute("SubjectList", subList);
			model.addAttribute("subOfficeList", subOfficeList);
			model.addAttribute("orderList", orderList);
			model.addAttribute("billList", billList);
			model.addAttribute("langId", Long.valueOf(langId));
			model.addAttribute("officeList", officeList);
			model.addAttribute("flag", "add");
			model.addAttribute("talukaList", talukaList);
			model.addAttribute("talukaId", talukaId);
			model.addAttribute("ddoSelected", ddoSelected);
		}

		model.addAttribute("postEntryModel", postEntryModel);

		return "/views/add-posts";
	}

	@GetMapping("/updatePosts")
	public String updatePosts(Model model, Locale locale, HttpSession session) {
		return "/views/update-posts";
	}
	
	@GetMapping("/savePostEntry")
	public String savePostEntry(Model model, Locale locale, HttpSession session,@ModelAttribute("postEntryModel") PostEntryModel postEntryModel) {
		
		long langId = 0l;
		long locId = 0l;
		long loggedInPostId = 0l;
		OrgDdoMst ddoMst = null;
		String ddoCode = null;
		Long lLngFieldDept =0l;
		if (session.getAttribute("locationId")!=null) {
			langId = 1l;
			 locId = Long.parseLong((String) session.getAttribute("locationId"));
			 loggedInPostId = Long.parseLong((String) session.getAttribute("loggedInPost")); 

			List<OrgDdoMst> ddoCodeList = entryOfPostsService.getDDOCodeByLoggedInlocId(locId);

			if (ddoCodeList != null)

				if (ddoCodeList != null && ddoCodeList.size() > 0) {
					ddoMst = ddoCodeList.get(0);
				}

			if (ddoMst != null) {
				ddoCode = ddoMst.getDdoCode();
			}

			 lLngFieldDept = Long.parseLong(ddoMst.getHodLocCode());
		}
		
		
		OrgPostMst orgPostMst=new OrgPostMst();
		OrgPostDetailsRlt orgPostDetailsRlt=new OrgPostDetailsRlt();
		HrPayOfficepostMpg hrPayOfficepostMpg=new HrPayOfficepostMpg();
		
		Long postId=entryOfPostsService.savePost(orgPostMst);
		Long postDetailsId=entryOfPostsService.savePostDetails(orgPostDetailsRlt);
		
		Long officePostId=entryOfPostsService.saveHrPayOfficepostMpg(hrPayOfficepostMpg);
		
		
		return "/views/update-posts";
	}
}
