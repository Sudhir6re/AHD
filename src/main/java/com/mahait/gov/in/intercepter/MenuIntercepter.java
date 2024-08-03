package com.mahait.gov.in.intercepter;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mahait.gov.in.entity.OrgUserMst;
import com.mahait.gov.in.model.TopicModel;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;
import com.mahait.gov.in.service.WelcomeService;

//@Order(Ordered.LOWEST_PRECEDENCE)
//@Component
public class MenuIntercepter implements HandlerInterceptor {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final CommonHomeMethodsService commonHomeMethodsService;
	private final HttpSession session;
	
	
	@Autowired
	WelcomeService welcomeService;

	@Autowired
	public MenuIntercepter(UserDetailsServiceImpl userDetailsServiceImpl,
			CommonHomeMethodsService commonHomeMethodsService, HttpSession session) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.commonHomeMethodsService = commonHomeMethodsService;
		this.session = session;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		  if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
	            return;
	        }
		
		  welcomeService.idleTerminateConnectivity();
		  
		  // Retrieve the attribute and handle null case
		    Boolean alreadyProcessed = (Boolean) request.getAttribute("menuIntercepterProcessed");
		    if (alreadyProcessed != null && alreadyProcessed) {
		        return;  // Skip processing if already processed
		    }
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && !authentication.getPrincipal().equals("anonymousUser") ) {
			List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			if (authorities.size() > 0) {
				User u = (User) authentication.getPrincipal();
				OrgUserMst messages = userDetailsServiceImpl.getUserIdbyUserName(u.getUsername());
				
				Integer levelRoleVal = messages.getMstRoleEntity().getRoleId();
				if (levelRoleVal != null && levelRoleVal != 0) {
					List<TopicModel> menuList = commonHomeMethodsService.findMenuNameByRoleID(levelRoleVal, "en");
					List<TopicModel> subMenuList = commonHomeMethodsService.findSubMenuByRoleID(levelRoleVal, "en");
					session.setAttribute("menuList", menuList);
					session.setAttribute("subMenuList", subMenuList);
					session.setAttribute("levelRoleVal", levelRoleVal);

					if (modelAndView != null) {
						modelAndView.addObject("menuList", menuList);
						modelAndView.addObject("subMenuList", subMenuList);
						modelAndView.addObject("levelRoleVal", levelRoleVal);
					}
					
					request.setAttribute("menuIntercepterProcessed", true);
				}
			}
		}
	}

}