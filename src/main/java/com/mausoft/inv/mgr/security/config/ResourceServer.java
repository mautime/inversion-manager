package com.mausoft.inv.mgr.security.config;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
@EnableResourceServer
public class ResourceServer extends ResourceServerConfigurerAdapter {
	
	/*@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}*/
	
	/*@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
	}*/
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
		System.out.println("ResourceServer#configure(HttpSecurity)");
		http
            //.cors().and()
            .csrf().disable()
            .requestMatcher(new OAuthRequestedMatcher())
            .authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            // when restricting access to 'Roles' you must remove the "ROLE_" part role
            // for "ROLE_USER" use only "USER"
            /*.antMatchers("/api/data/exchange/symbols").access("hasAnyRole('USER')")
            .antMatchers("/api/me").hasAnyRole("USER", "ADMIN")
            .antMatchers("/api/admin").hasRole("ADMIN")
            // use the full name when specifying authority access
            .antMatchers("/api/registerUser").hasAuthority("ROLE_REGISTER")*/
            // restricting all access to /api/** to authenticated users
            .antMatchers(HttpMethod.POST, "/api/profile").anonymous()
            .antMatchers(HttpMethod.GET, "/api/profile/check/*").anonymous()
            .antMatchers("/api/inversion/**").authenticated();
    }
	
    @Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    		System.out.println("ResourceServer#configure(ResourceServerSecurityConfigurer)");
		resources.resourceId("resource");
	}

	private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            // Determine if the resource called is "/api/**"
            String path = request.getServletPath();
            if ( path.length() >= 4 ) {
                path = path.substring(0, 4);
                boolean isApi = path.equals("/api");
                return isApi;
            } else return false;
        }
    }
	
	@Bean
    public FilterRegistrationBean corsFilterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean(corsFilter());
        
        bean.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER);
        
        return bean;
    }
	
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}