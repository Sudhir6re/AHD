package com.mahait.gov.in.intercepter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;

public class MenuIntercepter implements HandlerInterceptor {

	@Autowired
	private HttpSession session;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	private CommonHomeMethodsService commonHomeMethodsService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	     authentication.getAuthorities().stream()
	            .map(GrantedAuthority::getAuthority)
	            .collect(Collectors.toList());
	     
		
	     if(authentication.isAuthenticated() && authentication!=null) {
	    	 OrgUserMst messages = (OrgUserMst) session.getAttribute("MY_SESSION_MESSAGES");
	 		List<TopicModel> menuList = new ArrayList<>();
	 		List<TopicModel> subMenuList = new ArrayList<>();

	 		Integer levelRoleVal = messages.getMstRoleEntity().getRoleId();
	 		menuList = commonHomeMethodsService.findMenuNameByRoleID(messages.getMstRoleEntity().getRoleId(), "en");
	 		subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, "en");

	 		session.setAttribute("menuList", menuList);
	 		session.setAttribute("subMenuList", subMenuList);

	 		session.setAttribute("levelRoleVal", levelRoleVal);
	     }
	     
		

		return true;
	}

}