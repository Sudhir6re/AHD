package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/master")
@Controller
public class AdminOfficeMasterController {
	@GetMapping("/adminOfficeMaster")
	public String adminOfficeMaster( Model model, Locale locale,
			HttpSession session) {
		return "/views/admin-office-master";
	}
	@GetMapping("/adminOffice")
	public String adminOffice( Model model, Locale locale,
			HttpSession session) {
		return "/views/admin-office";
	}

}
