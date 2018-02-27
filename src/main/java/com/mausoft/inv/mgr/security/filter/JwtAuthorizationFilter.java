package com.mausoft.inv.mgr.security.filter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mausoft.common.model.ErrorAjaxResponse;
import com.mausoft.inv.mgr.security.exception.JwtSecurityException;
import com.mausoft.inv.mgr.security.exception.JwtSecurityException.ErrorCode;
import com.mausoft.inv.mgr.util.GlobalParameters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
	public JwtAuthorizationFilter(AuthenticationManager aAuthenticationManager) {
		super(aAuthenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String header = request.getHeader((String) GlobalParameters.getInstance().getParam("security.jwt.token.header"));
		
        if (header == null || !header.startsWith((String) GlobalParameters.getInstance().getParam("security.jwt.token.prefix"))) {
            chain.doFilter(request, response);
            return;
        }
        
        try {
        		
        		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        		
        		SecurityContextHolder.getContext().setAuthentication(authentication);
                
        		chain.doFilter(request, response);
        } catch(AuthenticationException ae) {
        		onUnsuccessfulAuthentication(request, response, ae);
        }
	}
	
	@SuppressWarnings("unchecked")
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String user = null;
        String token = request.getHeader((String) GlobalParameters.getInstance().getParam("security.jwt.token.header"));
        Claims jwtPayload = null;
        UsernamePasswordAuthenticationToken authenticatedUser = null;
        
        try {
        	
        		if (token != null) {
                // parse the token.
            		jwtPayload = Jwts.parser()
            				.setSigningKey((String) GlobalParameters.getInstance().getParam("security.jwt.settings.passphrase"))
            				.parseClaimsJws(token.replace((String) GlobalParameters.getInstance().getParam("security.jwt.token.prefix"), ""))
            				.getBody();
            		
                user = jwtPayload.getSubject();
                

                if (user != null) {
                    authenticatedUser = new UsernamePasswordAuthenticationToken(user, null, AuthorityUtils.createAuthorityList(Optional.ofNullable((List<String>) jwtPayload.get("roles")).map(roles ->roles.stream().map(role -> "ROLE_".concat(role)).toArray(size -> new String[size])).orElse(new String[0])));
                }
            }
        } catch(ExpiredJwtException ejwte) {
        		throw new JwtSecurityException(ErrorCode.EXPIRED_JWT_EXCEPTION_CODE, ejwte);
        } catch(JwtException jwte) {
        		throw new JwtSecurityException(ErrorCode.JWT_UNKNOWN_EXCEPTION_CODE, jwte);
        }
        
        return authenticatedUser;
    }

	@Override
	protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		ObjectMapper objectMapper = null;
		
		objectMapper = new ObjectMapper();
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        if (authException instanceof JwtSecurityException) {
        		objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse(((JwtSecurityException) authException).getErrorCode().getShortErrorCode(), ((JwtSecurityException) authException).getErrorCode().getErrorCode(), HttpStatus.UNAUTHORIZED));
        } else {
        		objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse(JwtSecurityException.ErrorCode.JWT_UNKNOWN_EXCEPTION_CODE.getShortErrorCode(), JwtSecurityException.ErrorCode.JWT_UNKNOWN_EXCEPTION_CODE.getErrorCode(), HttpStatus.UNAUTHORIZED));
        }

        //objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse("Authentication Failed", ErrorCode.AUTHENTICATION, HttpStatus.UNAUTHORIZED));
	}
	
}