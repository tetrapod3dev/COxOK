package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.request.PhotoRegisterRequestDto;
import com.ssafy.cookblog.service.PhotoService;

import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin("*")
@RequestMapping("/photo")
public class PhotoController {
	
	@Autowired
	PhotoService photoService;

	@ApiOperation("레시피 사진 등록")
	@PostMapping("/register")
	public Object registerRecipe(@ModelAttribute PhotoRegisterRequestDto photo, HttpServletRequest request) {
	
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<String> list =photoService.registerPhoto(photo);
		if(list.size()!=0) {
			map.put("msg", "레시피 등록에 성공했습니다.");
			map.put("status", "success");
			map.put("photo",list);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피 등록에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
