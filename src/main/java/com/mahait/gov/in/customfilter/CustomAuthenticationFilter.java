package com.mahait.gov.in.customfilter;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mahait.gov.in.crypto.AESUtil;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public static final String SPRING_SECURITY_FORM_DOMAIN_KEY = "appCode";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
        throws AuthenticationException {

        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " 
              + request.getMethod());
        }

        CustomAuthenticationToken authRequest = getAuthRequest(request);
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
        
    }

    private CustomAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        
       // HttpSession session = request.getSession();
        //String token = (String) request.getSession().getAttribute("secretKey");
        
        String appCode = obtainDomain(request);
        int appCode1 = Integer.parseInt(obtainDomain(request)) ;

        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if (appCode == null) {
            appCode1 = 0;
        }
        
        AESUtil aesUtil = new AESUtil();

		password = aesUtil.decrypt("MESSAGE", password);

        return new CustomAuthenticationToken(username, password, appCode1);
    }

    private String obtainDomain(HttpServletRequest request) {
        return request.getParameter(SPRING_SECURITY_FORM_DOMAIN_KEY);
    }
}
