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

import com.ssafy.cookblog.dto.ReviewDto;
import com.ssafy.cookblog.dto.request.RecipeRequestDto;
import com.ssafy.cookblog.dto.response.ReviewResponseDto;
import com.ssafy.cookblog.service.ReviewService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

@RestController
@CrossOrigin("*")
@RequestMapping("/review")
public class ReviewController {
	
	
	@Autowired
	ReviewService reviewService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	JwtService jwtService;

	//레시피번호에 따른 리뷰 조회
	@GetMapping("/view/{recipeId}")
	public Object getAllReview(@PathVariable("recipeId") int recipeId ) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<ReviewResponseDto> list = reviewService.getAllReview(recipeId);
		map.put("msg", "리뷰 조회를 성공했습니다.");
		map.put("status", "success");
		map.put("list",list);
		response = new ResponseEntity(map, HttpStatus.OK);
		return response;
	}

	//레시피번호에 따른 리뷰 작성
	@PostMapping("/")
	public Object registerReview(@RequestBody ReviewDto reviewDto,HttpServletRequest request ) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		long userId=userService.findUserByEmail(email).getUserId();
		reviewDto.setUserId(userId);
		
		int count = reviewService.register(reviewDto);
		if(count !=0 ) {
			map.put("msg", "리뷰 등록을 성공하였습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "리뷰 등록을 실패하였습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	//리뷰 번호에 따른 리뷰 삭제
	@DeleteMapping("/{reviewid}")
	public Object removeReview(@PathVariable("reviewid")int reviewId) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = reviewService.remove(reviewId);
		if(count !=0 ) {
			map.put("msg", "리뷰 삭제를 성공하였습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "리뷰 삭제를 실패하였습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
}
