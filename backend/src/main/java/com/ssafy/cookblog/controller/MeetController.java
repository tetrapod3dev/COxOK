package com.ssafy.cookblog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.service.MeetService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

@RestController
@CrossOrigin("*")
@RequestMapping("/meet")
public class MeetController {
	
	@Autowired
	MeetService meetService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;

}
