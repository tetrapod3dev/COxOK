package com.ssafy.cookblog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.cookblog.util.JwtInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	private static final String[] INCLUDE_PATHS = {
			"/user/**",
			"/recipe/**",
			"/review/**"
	};
	private static final String[] EXCLUDE_PATHS = {
			"/user/signup/**",
			"/user/login/**",
			"/user/nickname/**",
			"/user/email/**",
			"/user/auth/**",
			
			"/recipe/all/**",
			"/recipe/search/**",
			"/recipe/view/**",
			"/recipe/goRegister/**",
			
			"/review/view/**",
			
			"/swagger-ui.html",
			"/swagger-resources/**",
			"/webjars/**",
			"/v2/**",
	};

	@Autowired
	private JwtInterceptor jwtInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor)
				.addPathPatterns(INCLUDE_PATHS)
				.excludePathPatterns(EXCLUDE_PATHS);
	}
}