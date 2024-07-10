package com.mahait.gov.in.controller;

import java.math.BigInteger;
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

		long langId = 0l;
		long locId = 0l;
		BigInteger loggedInPostId = null;
		OrgDdoMst ddoMst = null;
		String ddoCode = null;
		List locationList = null;

		if (session.getAttribute("locationId") != null) {
			langId = 1l;
			locId = Long.parseLong((String) session.getAttribute("locationId"));
			loggedInPostId = (BigInteger) session.getAttribute("loggedInPost");

			List<OrgDdoMst> ddoCodeList = entryOfPostsService.getDDOCodeByLoggedInlocId(locId);

			model.addAttribute("filterDdoCodes", ddoCodeList);

			Long lLngFieldDept = Long.parseLong(ddoCodeList.get(0).getHodLocCode());
			List<MstDesignationEntity> desgList = entryOfPostsService.getActiveDesig(lLngFieldDept);

			model.addAttribute("Designation", desgList);

			List billList = entryOfPostsService.getAllBillsFromLocation(locId);
			model.addAttribute("billList", billList);

			if (ddoCodeList.size() > 0)
				ddoCode = ddoCodeList.get(0).getDdoCode();

			locationList = entryOfPostsService.getSubLocationDDOs(loggedInPostId);
			String locationcodeArray = "'";
			if (locationList != null && locationList.size() > 0) {
				for (int i = 0; i < locationList.size(); i++) {
					if (i == 0)
						locationcodeArray += locationList.get(i).toString() + '\'';
					else {
						locationcodeArray += ",'" + locationList.get(i).toString() + "'";
					}
				}
			}

			List filterDdoCode = entryOfPostsService.getFilterDdoCode(locationcodeArray);
			model.addAttribute("ddoCode", ddoCode);
			model.addAttribute("filterDdoCode", filterDdoCode);
		}
		return "/views/entry-of-posts";
	}

	@GetMapping("/addPosts")
	public String addPosts(Model model, Locale locale, HttpSession session,
			@ModelAttribute("postEntryModel") PostEntryModel postEntryModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		long langId = 0l;
		long locId = 0l;
		BigInteger loggedInPostId = null;
		OrgDdoMst ddoMst = null;
		String ddoCode = null;
		List locationList = null;

		if (session.getAttribute("locationId") != null) {

			langId = 1l;
			locId = Long.parseLong((String) session.getAttribute("locationId"));
			loggedInPostId = (BigInteger) session.getAttribute("loggedInPost");

			model.addAttribute("ddoCode", ddoCode);

			String talukaId = "";
			String ddoSelected = "";
			List DDOdtls = entryOfPostsService.getSubDDOsOffc(loggedInPostId, talukaId, ddoSelected);

			model.addAttribute("DDOlist", DDOdtls);

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

			List<OrgDdoMst> ddoCodeList = entryOfPostsService.getDDOCodeByLoggedInlocId(locId);

			model.addAttribute("filterDdoCodes", ddoCodeList);

			Long lLngFieldDept = Long.parseLong(ddoCodeList.get(0).getHodLocCode());

			List<MstDesignationEntity> desgList = entryOfPostsService.getActiveDesig(lLngFieldDept);

			model.addAttribute("Designation", desgList);

			// List DDOdtls = entryOfPostsService.getSubDDOsOffc(loggedInPostId, talukaId,
			// ddoSelected);

			// int i = 0;
			// while (i < DDOdtls.size()) {
			// i++;
			// }
			// model.addAttribute("DDOlist", DDOdtls);
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
			// model.addAttribute("ddoSelected", ddoSelected);
		}

		model.addAttribute("postEntryModel", postEntryModel);

		return "/views/add-posts";
	}

	@GetMapping("/updatePosts")
	public String updatePosts(Model model, Locale locale, HttpSession session) {
		return "/views/update-posts";
	}

	@PostMapping("/savePostEntry")
	public String savePostEntry(Model model, Locale locale, HttpSession session,
			@ModelAttribute("postEntryModel") PostEntryModel postEntryModel) {
		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		long langId = 0l;
		long locId = 0l;
		BigInteger loggedInPostId = null;
		OrgDdoMst ddoMst = null;
		String ddoCode = null;
		List locationList = null;

		if (session.getAttribute("locationId") != null) {
			langId = 1l;
			locId = Long.parseLong((String) session.getAttribute("locationId"));
			loggedInPostId = (BigInteger) session.getAttribute("loggedInPost");

			List<OrgDdoMst> ddoCodeList = entryOfPostsService.getDDOCodeByLoggedInlocId(locId);

			entryOfPostsService.savePostEntryDtl(postEntryModel,locId,loggedInPostId,messages);
			
			
		}
		

		return "/views/update-posts";
	}
}
