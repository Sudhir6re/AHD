package com.mahait.gov.in.controller;

import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/ddo")
@Controller
public class EntryOfPostsController {
	@GetMapping("/entryOfPosts")
	public String entryOfPosts(Model model, Locale locale, HttpSession session) {
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
