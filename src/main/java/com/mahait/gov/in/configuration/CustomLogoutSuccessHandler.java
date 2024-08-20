package com.mahait.gov.in.configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.service.UserLoginHistryService;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

	@Autowired
	UserLoginHistryService userLoginHistryService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {

		User user = (User) authentication.getPrincipal();

		String ip = InetAddress.getLocalHost().getHostAddress();

		UserLoginHistryEntity UserLoginHistryEntity = new UserLoginHistryEntity();
		UserLoginHistryEntity.setUsername(user.getUsername());
		UserLoginHistryEntity.setLoginIp(ip);
		UserLoginHistryEntity.setCreatedDate(new Date());
		UserLoginHistryEntity.setLoginDate(new Date());
		UserLoginHistryEntity.setCreatedUserId(1);
		UserLoginHistryEntity.setIsActive(3);
		UserLoginHistryEntity.setStatus("Logout Successful");
		userLoginHistryService.saveLoginDtls(UserLoginHistryEntity);

		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}

		redirectStrategy.sendRedirect(request, response, "/MJP/user/login?logout");

	}
}