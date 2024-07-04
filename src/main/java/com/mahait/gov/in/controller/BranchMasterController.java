package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mdc")
@Controller
public class BranchMasterController {
	@GetMapping("/branchMaster")
	public String branchMaster(Model model, Locale locale, HttpSession session) {
		return "/views/branch-master";
	}

	@GetMapping("/addBranch")
	public String addBranch(Model model, Locale locale, HttpSession session) {
		return "/views/add-branch";
	}

}
