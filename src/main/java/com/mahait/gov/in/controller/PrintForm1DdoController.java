package com.mahait.gov.in.controller;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@RequestMapping("/ddo")
@Controller
public class PrintForm1DdoController {
	@GetMapping("/printForm1")
	public String printForm1( Model model, Locale locale,
			HttpSession session) {
		return "/views/print-form1-ddo";
	}

}
