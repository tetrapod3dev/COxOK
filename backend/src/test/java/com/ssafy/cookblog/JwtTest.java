package com.ssafy.cookblog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.cookblog.util.JwtService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTest {

	@Autowired
	JwtService service;
	
	@Test
	public void jwtTest() throws Exception{
		String email="email@email.com";
		
		String token =service.generateToken(email);
		System.out.println(token);
		
		System.out.println(service.getEmailFromToken(token));
		System.out.println(">>>>"+service.isValid("asdasd"));
	}
}
