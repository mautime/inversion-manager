package com.mausoft.inv.mgr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.DispatcherServlet;

import com.mausoft.common.repository.impl.BaseRepository;
import com.mausoft.inv.mgr.security.config.ResourceServer;
import com.mausoft.inv.mgr.util.GlobalParameters;

@SpringBootApplication(scanBasePackages={"com.mausoft.inv.mgr.controller", "com.mausoft.inv.mgr.service", "com.mausoft.inv.mgr.repository.impl"}, scanBasePackageClasses= {ResourceServer.class, GlobalParameters.class})
@EntityScan(basePackages="com.mausoft.inv.mgr.entity")
@EnableJpaRepositories(basePackages={"com.mausoft.inv.mgr.repository"}, repositoryBaseClass=BaseRepository.class)
@EnableJpaAuditing
public class Application extends SpringBootServletInitializer {
	
	public static void main(String... args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public ServletRegistrationBean dispatcherRegistration(DispatcherServlet dispatcherServlet){
		ServletRegistrationBean registration = new ServletRegistrationBean(dispatcherServlet, "/api/*");
		return registration;
	}
	
	/*@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }*/
}