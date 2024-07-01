package com.mahait.gov.in.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.mahait.gov.in.intercepter.MenuIntercepter;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	private final MenuIntercepter menuIntercepter;

	public WebMvcConfig(MenuIntercepter menuIntercepter) {
		this.menuIntercepter = menuIntercepter;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(menuIntercepter);
	}

}