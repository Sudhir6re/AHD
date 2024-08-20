package com.mahait.gov.in.configuration;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.service.CommonHomeMethodsService;
import com.mahait.gov.in.service.UserLoginHistryService;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Autowired
	CommonHomeMethodsService commonHomeMethodsService;

	@Autowired
	UserLoginHistryService userLoginHistryService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomAuthenticationFailureHandler() {
		super();
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String ip = InetAddress.getLocalHost().getHostAddress();

		UserLoginHistryEntity UserLoginHistryEntity = new UserLoginHistryEntity();

		String username = request.getParameter("username");

		UserLoginHistryEntity.setUsername(username);
		UserLoginHistryEntity.setLoginIp(ip);
		UserLoginHistryEntity.setCreatedDate(new Date());
		UserLoginHistryEntity.setLoginDate(new Date());
		UserLoginHistryEntity.setCreatedUserId(1);
		UserLoginHistryEntity.setIsActive(2);
		UserLoginHistryEntity.setStatus("Login failure");
		userLoginHistryService.saveLoginDtls(UserLoginHistryEntity);

		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

		redirectStrategy.sendRedirect(request, response, "/user/login?error=true");
		// return new SimpleUrlAuthenticationFailureHandler("/user/login?error=true") ;
	}

}
