package com.mausoft.inv.mgr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.DispatcherServlet;

import com.mausoft.common.repository.impl.BaseRepository;
import com.mausoft.common.service.ISpringSecurityService;
import com.mausoft.common.service.impl.SpringSecurityService;
import com.mausoft.inv.mgr.entity.User;
import com.mausoft.inv.mgr.security.config.ResourceServer;
import com.mausoft.inv.mgr.util.GlobalParameters;

@SpringBootApplication(scanBasePackages={"com.mausoft.inv.mgr.controller", "com.mausoft.inv.mgr.service", "com.mausoft.inv.mgr.repository.impl"}, scanBasePackageClasses= {ResourceServer.class, GlobalParameters.class})
@EntityScan(basePackages="com.mausoft.inv.mgr.entity")
@EnableJpaRepositories(basePackages={"com.mausoft.inv.mgr.repository"}, repositoryBaseClass=BaseRepository.class)
@EnableJpaAuditing(auditorAwareRef="jpaAuditorAwareProvider")
public class Application extends SpringBootServletInitializer {
	
	@Autowired
	private Environment env;
	
	public static void main(String... args) {
		System.setProperty("spring.config.location", "classpath:./application.properties, file:///Mau/apps/inversion-manager/application.properties");
		SpringApplication.run(Application.class, args);
	}
	
	/*@Bean
	public PropertyPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		PropertyPlaceholderConfigurer conf = new PropertyPlaceholderConfigurer();
		conf.setLocation(new FileSystemResource("/Mau/apps/inversion-manager/application.properties"));
		return conf;
	}*/
	
	@Bean("passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		System.out.println(env.getProperty("test.config.prop"));
		return new BCryptPasswordEncoder(12);
	}
	
	@Bean
	public ISpringSecurityService springSecurityService() {
		return new SpringSecurityService();
	}
	
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, "/api/*");
		return registration;
	}
	
	@Bean("jpaAuditorAwareProvider")
	public AuditorAware<User> jpaAuditorAwareProvider(){
		
		return () -> {
			User user = null;
			Authentication authentication = null;
			
			authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication != null && authentication.isAuthenticated()) {
				user = new User((String) authentication.getPrincipal());
			}
			
			return user;
		};
	}
	
	/*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }*/
}