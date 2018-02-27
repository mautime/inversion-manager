package com.mausoft.inv.mgr.security.filter;

import java.io.IOException;
import java.util.Date;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mausoft.common.model.ErrorAjaxResponse;
import com.mausoft.inv.mgr.security.exception.AuthenticationSecurityException;
import com.mausoft.inv.mgr.security.model.AuthenticationUser;
import com.mausoft.inv.mgr.security.model.AuthenticationUserSecurityDetails;
import com.mausoft.inv.mgr.util.GlobalParameters;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authManager;
	
	public JwtAuthenticationFilter(AuthenticationManager aAuthManager) {
		authManager = aAuthManager;
		setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher((String) GlobalParameters.getInstance().getParam("security.login.url"), HttpMethod.POST.name()));
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
		AuthenticationUser authUser = null;
		Authentication auth = null;
		
		try {
			
			authUser = new ObjectMapper().readValue(request.getInputStream(), AuthenticationUser.class);
			auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword()));
			
		} catch(IOException ioe) {
			throw new InternalAuthenticationServiceException("loginError", ioe);
		}
				
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
		AuthenticationUserSecurityDetails userDetails = (AuthenticationUserSecurityDetails) auth.getPrincipal();
		
		String token = Jwts.builder().setSubject(userDetails.getUsername())
			.setExpiration(new Date(System.currentTimeMillis() + (Long.parseLong((String) GlobalParameters.getInstance().getParam("security.jwt.settings.expiration")))))
			.signWith(SignatureAlgorithm.valueOf((String) GlobalParameters.getInstance().getParam("security.jwt.settings.sign.algorithm")), (String) GlobalParameters.getInstance().getParam("security.jwt.settings.passphrase"))
			.setSubject(userDetails.getUid())
			.claim("name", userDetails.getDisplayName())
			.claim("roles", userDetails.getAuthorities().stream().map(e -> e.getAuthority()).collect(Collectors.toList()))
			.compact();
		
		response.addHeader((String) GlobalParameters.getInstance().getParam("security.jwt.token.header"), (String) GlobalParameters.getInstance().getParam("security.jwt.token.prefix") + token);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		ObjectMapper objectMapper = null;
		
		objectMapper = new ObjectMapper();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        
        if (exception instanceof BadCredentialsException) {
        		objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse(AuthenticationSecurityException.ErrorCode.INVALID_CREDENTIALS_EXCEPTION_CODE.getShortErrorCode(), AuthenticationSecurityException.ErrorCode.INVALID_CREDENTIALS_EXCEPTION_CODE.getErrorCode(), HttpStatus.BAD_REQUEST));
        }/* else if (exception instanceof JwtSecurityException) {
        		objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse(((JwtSecurityException) exception).getErrorCode().getShortErrorCode(), ((JwtSecurityException) exception).getErrorCode().getErrorCode(), HttpStatus.UNAUTHORIZED));
        }*/ else {
        		objectMapper.writeValue(response.getWriter(), new ErrorAjaxResponse(AuthenticationSecurityException.ErrorCode.UNKNOWN_AUTHENTICATION_EXCEPTION_CODE.getShortErrorCode(), AuthenticationSecurityException.ErrorCode.UNKNOWN_AUTHENTICATION_EXCEPTION_CODE.getErrorCode(), HttpStatus.UNAUTHORIZED));
        }
	}
}