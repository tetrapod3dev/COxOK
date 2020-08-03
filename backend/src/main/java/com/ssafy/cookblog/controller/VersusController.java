package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.RecipeDto;
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
	
	//레시피번호에 따른 리뷰 조회
	@GetMapping("/random")
	public Object get16Recipe() {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<RecipeDto> list = versusService.get16Recipe();
		if(list!=null) {
			map.put("msg", "레시피 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피 조회를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
}


