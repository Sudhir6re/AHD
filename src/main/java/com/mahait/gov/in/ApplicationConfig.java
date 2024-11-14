package com.mahait.gov.in;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;



@Configuration
public class ApplicationConfig  extends WebMvcConfigurerAdapter {

	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageResource= new ReloadableResourceBundleMessageSource();
        messageResource.setBasename("classpath:i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        return messageResource;
    }
    @Bean
    public LocaleResolver localeResolver() {
    	SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    	localeResolver.setDefaultLocale(Locale.US);
    	return localeResolver;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    	LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
    	localeChangeInterceptor.setParamName("lang");
    	registry.addInterceptor(localeChangeInterceptor);
    }
    
    @Bean
   	public CookieSerializer cookieSerializer() {
   		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
   		serializer.setCookieName("JSESSIONID");
   		serializer.setSameSite("Strict");
   		serializer.setUseSecureCookie(true);
   		serializer.setCookiePath("/");
   		return serializer;
   	}
       
    
    
    @Profile("dev")
 		@Bean
 		public String devBean() {
 			return "dev";
 		}

 		@Profile("qa")
 		@Bean
 		public String qaBean() {
 			return "qa";
 		}

 		@Profile("prod")
 		@Bean
 		public String prodBean() {
 			return "prod";
 		}
 		
 		@Profile("uat")
 		@Bean
 		public String uatBean() {
 			return "uat";
 		}
 		
 		@Profile("test")
 		@Bean
 		public String testBean() {
 			return "test";
 		}
 		
 		
 		@Profile("local")
 		@Bean
 		public String localBean() {
 			return "local";
 		}
 	  
    
    /*@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID"); 
		serializer.setCookiePath("/"); 
		serializer.setSameSite("Strict");
		serializer.setUseHttpOnlyCookie(true);
	//	serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
		return serializer;
	}
    */
}
