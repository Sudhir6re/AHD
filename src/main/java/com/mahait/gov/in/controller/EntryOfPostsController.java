package com.mahait.gov.in.controller;

import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mahait.gov.in.entity.OrgPostDetailsRlt;
import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.repository.OrgPostDetailsRltRepository;

@RequestMapping("/ddo")
@Controller
public class EntryOfPostsController {

	@Autowired
	OrgPostDetailsRltRepository orgPostDetailsRltRepository;

	@GetMapping("/entryOfPosts")
	public String entryOfPosts(Model model, Locale locale, HttpSession session) {

		OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");

		List<OrgPostDetailsRlt> lst = orgPostDetailsRltRepository.findByOrgPostMst(messages.getCreatedByPost());

		return "/views/entry-of-posts";
	}

	@GetMapping("/addPosts")
	public String addPosts(Model model, Locale locale, HttpSession session) {
		return "/views/add-posts";
	}
	@GetMapping("/updatePosts")
	public String updatePosts(Model model, Locale locale, HttpSession session) {
		return "/views/update-posts";
	}
}
