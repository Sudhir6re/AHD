package com.mahait.gov.in.customfilter;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.mahait.gov.in.entity.UserLoginHistryEntity;
import com.mahait.gov.in.service.UserLoginHistryService;

/* Added by Sudhir */
public class CustomSimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	@Autowired
	UserLoginHistryService userLoginHistryService;

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	public CustomSimpleUrlAuthenticationSuccessHandler() {
		super();
	}

	// API

	public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		handle(request, response, authentication);
		String ip = InetAddress.getLocalHost().getHostAddress();
		User user = (User) authentication.getPrincipal();
		UserLoginHistryEntity userLoginHistryEntity = new UserLoginHistryEntity();
		userLoginHistryEntity.setUsername(user.getUsername());
		userLoginHistryEntity.setLoginIp(ip);
		userLoginHistryEntity.setCreatedDate(new Date());
		userLoginHistryEntity.setLoginDate(new Date());
		userLoginHistryEntity.setCreatedUserId(1);
		userLoginHistryEntity.setIsActive(1);
		userLoginHistryEntity.setStatus("Login successful");
		userLoginHistryService.saveLoginDtls(userLoginHistryEntity);

		clearAuthenticationAttributes(request);
	}

	// IMPL

	protected void handle(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		final String targetUrl = determineTargetUrl(authentication);

		if (response.isCommitted()) {
			// logger.debug("Response has already been committed. Unable to redirect to " +
			// targetUrl);
			return;
		}

		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(final Authentication authentication) {

		Map<String, String> roleTargetUrlMap = new HashMap<>();

		roleTargetUrlMap.put("ROLE_USER", "/user/home");
		roleTargetUrlMap.put("ROLE_DDO_AST", "/ddoast/home");
		roleTargetUrlMap.put("ROLE_DDO", "/ddo/home");
		roleTargetUrlMap.put("ROLE_SBI_CMP", "/cmp/home");
		roleTargetUrlMap.put("ROLE_MDC", "/mdc/home");
		roleTargetUrlMap.put("ROLE_SUPER", "/super/home"); // developer

		final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (final GrantedAuthority grantedAuthority : authorities) {
			String authorityName = grantedAuthority.getAuthority();
			if (roleTargetUrlMap.containsKey(authorityName)) {
				return roleTargetUrlMap.get(authorityName);
			}
		}
		throw new IllegalStateException();
	}

	protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
		final HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		}
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}

}