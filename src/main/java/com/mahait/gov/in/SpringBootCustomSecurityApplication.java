package com.mahait.gov.in;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.WebApplicationInitializer;


@EnableAutoConfiguration
@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass=true)  

/*@ComponentScan(basePackages = {"com.mahait.gov.in"})*/

/*@EnableAutoConfiguration(exclude = HibernateJpaAutoConfiguration.class)
@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
@Configuration*/
@ComponentScan("com.mahait.gov.in")
@EntityScan(basePackages = { "com.mahait.gov.in.entity","com.mahait.gov.in.nps.entity" })
public class SpringBootCustomSecurityApplication extends SpringBootServletInitializer implements WebApplicationInitializer { 
	
//	private static final Logger logger=LoggerFactory.getLogger(SpringBootCustomSecurityApplication.class);
	/*protected final Log logger = LogFactory.getLog(getClass());*/

	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		logger.info("Welcome to MJP Payroll Application");
	
		return application.sources(SpringBootCustomSecurityApplication.class);
	}
	public static void main(String[] args) {
//		 ApplicationContext ctx = SpringApplication.run(SpringBootCustomSecurityApplication.class, args);
		/*System.setProperty("server.servlet.context-path", "/MahaSevaarth");*/
		SpringApplication.run(SpringBootCustomSecurityApplication.class, args);
//		logger.info("Welcome to MJP Application");
	}
	
	
	    @Bean
	    public HttpMessageConverters customConverters() {
	        ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
	        return new HttpMessageConverters(arrayHttpMessageConverter);
	    }
	  
	  
	 
	  
}            