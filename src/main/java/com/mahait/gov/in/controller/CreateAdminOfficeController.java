package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/master")
@Controller
public class CreateAdminOfficeController {
	@GetMapping("/createAdminOffice")
	public String CreateAdminOffice( Model model, Locale locale,
			HttpSession session) {
		return "/views/create-admin-office";
	}
	@GetMapping("/createOffice")
	public String CreateOffice( Model model, Locale locale,
			HttpSession session) {
		return "/views/create-office";
	}

}
