package com.mahait.gov.in.customfilter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.RedirectView;

import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.service.UserLoginHistryService;

public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	UserLoginHistryService userLoginHistryService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		User user = (User) authentication.getPrincipal();

		ServerHttpRequest request1 = (ServerHttpRequest) request;
		String latestIp = request1.getRemoteAddress().toString();

		String ip = InetAddress.getLocalHost().getHostAddress();

		System.out.println("latestIp" + latestIp);
		UserLoginHistryEntity userLoginHistryEntity = new UserLoginHistryEntity();
		userLoginHistryEntity.setUsername(user.getUsername());
		userLoginHistryEntity.setLoginIp(ip);
		userLoginHistryEntity.setCreatedDate(new Date());
		userLoginHistryEntity.setLoginDate(new Date());
		userLoginHistryEntity.setCreatedUserId(1);
		userLoginHistryEntity.setIsActive(1);
		userLoginHistryEntity.setStatus("Logout successful");
		userLoginHistryService.saveLoginDtls(userLoginHistryEntity);

		HttpSession session = request.getSession(false);


		if (session != null) {
			session.invalidate();
		}

		//return new RedirectView("redirect:/user/login?logout");
		redirectStrategy.sendRedirect(request, response, "/user/login?logout");

		// super.onLogoutSuccess(request, response, authentication);
	}
}