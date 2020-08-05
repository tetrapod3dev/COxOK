package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.MeetDto;
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
	
	//소모임 리스트
//	@GetMapping("/all/{startIndex}")
//	public Object getAllMeet(@PathVariable("meetId") int meetId) {
//		ResponseEntity response = null;
//		Map<String,Object> map = new HashMap<String, Object>();
//
//		List<MeetDto> list = meetService.getAllMeet(meetId);
//		map.put("msg", "소모임 조회를 성공했습니다.");
//		map.put("status", "success");
//		map.put("list",list);
//		response = new ResponseEntity(map, HttpStatus.OK);
//		return response;
//	}
	
	//소모임 조회
//	@GetMapping("/view/{meetId}")
//	public Object getOneMeet(@PathVariable("meetId") int meetId) {
//		ResponseEntity response = null;
//		Map<String,Object> map = new HashMap<String, Object>();
//
//		List<MeetDto> list = meetService.getOneMeet(meetId);
//		map.put("msg", "소모임 조회를 성공했습니다.");
//		map.put("status", "success");
//		map.put("list",list);
//		response = new ResponseEntity(map, HttpStatus.OK);
//		return response;
//	}
	
	//소모임 삭제
	

}
