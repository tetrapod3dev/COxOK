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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.YoutubeDto;
import com.ssafy.cookblog.dto.request.YoutubeRegisterRequestDto;
import com.ssafy.cookblog.service.YoutubeService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/youtube")
public class YoutubeController {

	
	@Autowired
	YoutubeService youtubeService;
	
	@ApiOperation("관련 유튜브 영상 등록")
	@PostMapping("/")
	public Object registerYoutube(@RequestBody YoutubeRegisterRequestDto dto) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = youtubeService.register(dto);
		if(count!=0) {
			map.put("msg", "관련 유튜브 영상 등록을 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "관련 유튜브 영상 등록을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@ApiOperation("유튜브 영상 조회")
	@GetMapping("/{recipeId}")
	public Object getYoutube(@PathVariable("recipeId")long recipeId) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<YoutubeDto> list = youtubeService.get(recipeId);
		if(list!=null) {
			map.put("msg", "관련 유튜브 영상 등록을 성공했습니다.");
			map.put("status", "success");
			map.put("list", list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "관련 유튜브 영상 등록을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
}
