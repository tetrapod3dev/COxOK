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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;
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
	
	@GetMapping("/all/{startIndex}")
	public Object getAllVersus(@PathVariable("startIndex") int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<VersusResponseDto> list = versusService.getAllVersus(6*startIndex);
		if(list!=null) {
			map.put("msg", "요리대전 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
		
	
	// 레시피 상세 조회
	@GetMapping("/view/{id}")
	public Object getOneRecipe(@PathVariable("id") long id) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		VersusViewResponseDto versus = versusService.getOneVersus(id);
		
		if(versus!=null) {
			map.put("msg", "레시피 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("versus", versus);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	// 레시피 삭제
	@DeleteMapping("/delete/{id}")
	public Object deleteRecipe(@PathVariable long id) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = versusService.deleteVersus(id);
		if(count != 0) {
			map.put("msg", "요리대전이 삭제되었습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "요리대전을 삭제하지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
}


