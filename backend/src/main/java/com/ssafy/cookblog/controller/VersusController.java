package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.service.VersusService;
import com.ssafy.cookblog.util.JwtService;

@RestController
@CrossOrigin("*")
@RequestMapping("/versus")
public class VersusController {
	
	@Autowired
	VersusService versusService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	//요리대전 작성
	@PostMapping("/register")
	public Object registerVersus(@RequestBody VersusDto versusDto, HttpServletRequest request) {
	
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		versusDto.setUserId(userService.findUserByEmail(email).getUserId());
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		System.out.println(versusDto.getRecipeIdList());

		int count = versusService.registerVersus(versusDto);
		if(count!=0) {
			map.put("msg", "요리대전 작성에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "요리대전 작성에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@PostMapping("/win")
	public Object winVersus(@RequestBody VersusPointDto versusPoint) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		int count = versusService.winVersus(versusPoint);
		if(count!=0) {
			map.put("msg", "승점 저장에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "승점 저장에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
}


