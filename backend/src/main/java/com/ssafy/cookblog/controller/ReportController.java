package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.ReportRecipeDto;
import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.response.ReportRecipeResponseDto;
import com.ssafy.cookblog.service.ReportService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
	ReportService reportService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;
	
	@ApiOperation("레시피 신고")
	@PostMapping("")
	public Object reportRecipe(@RequestBody ReportRecipeDto reportDto, HttpServletRequest request) {
	
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		reportDto.setUserId(userService.findUserByEmail(email).getUserId());
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = reportService.reportRecipe(reportDto);
		if(count!=0) {
			map.put("msg", "레시피 신고에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피 신고에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@ApiOperation("신고 레시피 전체 조회")
	@GetMapping("/admin")
	public Object getAllReport(HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		System.out.println(">>>>>>>>>>>>>>"+email);
		if(!email.equals("admin@co-ok.com")) {	//관리자인 경우
			List<ReportRecipeResponseDto> list = reportService.getAllReport();
			map.put("msg", "신고 레시피 조회에 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "관리자가 아닙니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	

}
