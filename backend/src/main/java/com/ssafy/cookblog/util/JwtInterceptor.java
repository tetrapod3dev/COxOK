package com.ssafy.cookblog.util;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
			throws Exception {
		Enumeration headers = request.getHeaderNames();
		String token = request.getHeader(HEADER_AUTH);
		if(token != null && jwtService.isValid(token)){
			return true;
		}else{
			response.setStatus(403);
			return false;
		}

	}
	
	
	
}