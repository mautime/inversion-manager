package com.mausoft.inv.mgr.security.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.auth0.spring.security.api.JwtWebSecurityConfigurer;

@Configuration
@EnableWebSecurity
public class ResourceServer extends WebSecurityConfigurerAdapter {
	@Value(value = "${auth0.apiAudience}")
    private String apiAudience;
    @Value(value = "${auth0.issuer}")
    private String issuer;
	
	@Override
    public void configure(HttpSecurity http) throws Exception {
		System.out.println("ResourceServer#configure(HttpSecurity)");
		
		JwtWebSecurityConfigurer
			.forRS256(apiAudience, issuer)
        		.configure(http)
        		.requestMatcher(new OAuthRequestedMatcher())
        		.authorizeRequests()
                .antMatchers("/api/inversion/**").authenticated();
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