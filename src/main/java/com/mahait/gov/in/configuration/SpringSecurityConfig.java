package com.mahait.gov.in.configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.mahait.gov.in.customfilter.CustomAuthenticationFilter;
import com.mahait.gov.in.customfilter.CustomSimpleUrlAuthenticationSuccessHandler;
import com.mahait.gov.in.customfilter.CustomUserDetailsAuthenticationProvider;
import com.mahait.gov.in.customfilter.CustomUserDetailsService;
import com.mahait.gov.in.service.UserDetailsServiceImpl;

@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled=true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;	
	
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
    @Autowired
    private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()/*.antMatchers("/MahaSevaarth").permitAll()*/
		.antMatchers("/mdc/**").hasRole("MDC")
		.antMatchers("/ddoast/**").hasRole("DDO_AST")
		.antMatchers("/ddo/**").hasRole("DDO")
		.antMatchers("/super/**").hasRole("SUPER")  //developer
		.antMatchers("/user/home").authenticated()
		
		/*.anyRequest().authenticated()*/
        .and().formLogin()  
        .loginPage("/user/login").permitAll()
        .successHandler(myAuthenticationSuccessHandler())
        .permitAll()
        .and().logout().and().exceptionHandling().accessDeniedPage("/user/login?unauthorize").and().logout()   
		.logoutUrl("/user/logOut")
		.clearAuthentication(true)
		.invalidateHttpSession(true)
		.deleteCookies("JSESSIONID").and().sessionManagement()
		.invalidSessionUrl("/user/login?expired");
	} 

	public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        return filter;
    }
	
	 @Autowired
	    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	        auth.authenticationProvider(authProvider());
	    }
	
	public AuthenticationProvider authProvider() {
        CustomUserDetailsAuthenticationProvider provider 
            = new CustomUserDetailsAuthenticationProvider(passwordEncoder, userDetailsService);
        return provider;
    }
	
	public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/user/login?error=true") ;
    }
	
	
	@Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler(){
        return new CustomSimpleUrlAuthenticationSuccessHandler();
    }
	
	
	// Work around https://jira.spring.io/browse/SEC-2855
	@Bean
	public SessionRegistry sessionRegistry() {
	    SessionRegistry sessionRegistry = new SessionRegistryImpl();
	    return sessionRegistry;
	}

	// Register HttpSessionEventPublisher
	@Bean
	public static ServletListenerRegistrationBean httpSessionEventPublisher() {
	    return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
	}
}