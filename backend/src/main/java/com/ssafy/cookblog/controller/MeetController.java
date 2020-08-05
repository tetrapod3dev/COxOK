package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;
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
	@GetMapping("/all/{startIndex}")
	public Object getAllMeet(@PathVariable("startIndex") int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<MeetDto> list = meetService.getAllMeet(6*startIndex);
		if(list!=null) {
			map.put("msg", "소모임 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "소모임 조회를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	//소모임 조회
	@GetMapping("/view/{meetId}")
	public Object getOneMeet(@PathVariable("meetId") long meetId) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		MeetViewResponseDto meet = meetService.getOneMeet(meetId);
		
		if(meet != null) {
			map.put("msg", "소모임 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("meet", meet);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "소모임을 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	//소모임 삭제
	@DeleteMapping("/delete/{meetId}")
	public Object deleteMeet(@PathVariable long meetId) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = meetService.deleteMeet(meetId);
		if(count != 0) {
			map.put("msg", "소모임이 삭제되었습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "소모임이 삭제하지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@PostMapping("/")
	public Object register(@ModelAttribute MeetRegisterRequestDto meetRegisterRequestDto, HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		meetRegisterRequestDto.setUserId(userId);
		
		int count = meetService.register(meetRegisterRequestDto);
		if(count!=0) {
			map.put("msg", "소모임 등록을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "소모임 등록을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}

		return response;
		
	}
	
	@PutMapping("/")
	public Object update(@RequestBody MeetDto meetDto) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		
		int count = meetService.modify(meetDto);
		if(count!=0) {
			map.put("msg", "소모임 등록을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "소모임 등록을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	
}
