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
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.OnlineDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;
import com.ssafy.cookblog.service.OnlineService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/online")
public class OnlineController {

	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	OnlineService onlineService;
	
	@ApiOperation("온라인 모임 등록")
	@PostMapping("/")
	public Object register(@RequestBody OnlineDto onlineDto, HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		
		onlineDto.setUserId(userId);
		
		int count = onlineService.register(onlineDto);
		if(count!=0) {
			map.put("msg", "온라인 모임 등록을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 등록을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}

		return response;
	}
	
	@ApiOperation("온라임 모임 수정")
	@PutMapping("/")
	public Object register(@RequestBody OnlineDto onlineDto) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		
		int count = onlineService.modify(onlineDto);
		if(count!=0) {
			map.put("msg", "온라인 모임 수정을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 수정을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@ApiOperation("온라인 모임 전체 조회")
	@GetMapping("/all/{startIndex}")
	public Object getOnlineList(@PathVariable("startIndex")int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<OnlineDto> list = onlineService.getAllOnline(6*startIndex);
		if(list!=null) {
			map.put("msg", "온라인 모임 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 조회를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@ApiOperation("온라인 모임 한개 조회")
	@GetMapping("/{onlineId}")
	public Object getOnline(@PathVariable("onlineId")long onlineId,HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		
		OnlineDto onlineDto = onlineService.getOnline(onlineId);
		if(onlineDto!=null) {
			map.put("msg", "온라인 모임 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("online",onlineDto);
			map.put("userId",userId);
			map.put("writerNickname", userService.findUserByUserId(onlineDto.getUserId()).getNickname());
			
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 조회를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@ApiOperation("온라인 모임 한개 조회")
	@DeleteMapping("/{onlineId}")
	public Object deleteOnline(@PathVariable("onlineId")long onlineId) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		int count = onlineService.remove(onlineId);
		if(count!=0) {
			map.put("msg", "온라인 모임 삭제를 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 삭제를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	
	@ApiOperation("온라인 모임 참석하기")
	@PostMapping("/join")
	public Object registerOnlineJoin(@RequestBody MeetJoinDto meetJoinDto,HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		meetJoinDto.setUserId(userId);
		
		int count = onlineService.registerOnlineJoin(meetJoinDto);
		if(count!=0) {
			map.put("msg", "온라인 모임 참석을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 참석을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@ApiOperation("온라인 모임 참석 취소하기")
	@DeleteMapping("/join/{onlineId}")
	public Object deleteOnlineJoin(@PathVariable("onlineId") long meetId,HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		
		MeetJoinDto meetJoinDto =new MeetJoinDto();
		meetJoinDto.setMeetId(meetId);
		meetJoinDto.setUserId(userId);
		
		int count = onlineService.removeOnlineJoin(meetJoinDto);
		if(count!=0) {
			map.put("msg", "온라인 모임 참석 삭제를 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인 모임 참석 삭제를 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@ApiOperation("온라인 타입으로 조회하기")
	@GetMapping("/type/{type}/{startIndex}")
	public Object getType(@PathVariable("type")String type,@PathVariable("startIndex")int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		OnlineDto onlineDto=new OnlineDto();
		onlineDto.setType(type); 
		onlineDto.setStartIndex(startIndex*12);
		
		List<OnlineDto> list = onlineService.getOnlineByType(onlineDto);
		if(list!=null) {
			map.put("msg", "온라인으로 타입 조회 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			map.put("total",onlineService.getOnlineTotalByType(type));
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "온라인으로 타입 조회 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
