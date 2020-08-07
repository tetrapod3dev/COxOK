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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.BlogDto;
import com.ssafy.cookblog.service.BlogService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

@RestController
@CrossOrigin("*")
@RequestMapping("/blog")
public class BlogController {
	
	@Autowired
	BlogService blogService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	// Create
	@PostMapping("/")
	public Object register(@RequestBody BlogDto blog,HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		String token = request.getHeader("Authorization");
		String email = jwtService.getEmailFromToken(token.substring(7));
		long userId = userService.userIdByEmail(email);
		blog.setUserId(userId);

		int cnt = blogService.register(blog);
		
		
		if(cnt != 0) {
			map.put("msg", "블로그 포스트 작성에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 포스트 작성에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	// Read(전체)
	@GetMapping("/all/{startIndex}")
	public Object getAllRecipe(@PathVariable int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<BlogDto> list = blogService.getAllBlog(10*startIndex);
		
		if(list!=null) {
			map.put("msg", "블로그 전체 목록 조회에 성공했습니다.");
			map.put("status", "success");
			map.put("list", list);
			map.put("total", blogService.getTotalNum());
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 전체 목록을 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	// Read(상세)
	@GetMapping("/{id}")
	public Object readBlog(@PathVariable long id,HttpServletRequest request) {
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		BlogDto blog = blogService.get(id);
		
		String token = request.getHeader("Authorization");
		String email = null;
		long userId = -1;
		
		if(blog != null) {
			map.put("msg", "블로그 포스트 읽기에 성공했습니다.");
			map.put("status", "success");
			map.put("blog", blog);
			if(token != null) {
				email = jwtService.getEmailFromToken(token.substring(7));
				userId = userService.userIdByEmail(email);
			}
			map.put("userId", userId);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 포스트 읽기에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	// Read(상세)
	@GetMapping("/my/{id}")
	public Object getMyBlog(@PathVariable long id,HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<BlogDto> list = blogService.getMyBlog(id);
		
		String token = request.getHeader("Authorization");
		String email = null;
		long userId = -1;
		
		if(list != null) {
			map.put("msg", "블로그 포스트 읽기에 성공했습니다.");
			map.put("status", "success");
			map.put("blog", list);
			if(token != null) {
				email = jwtService.getEmailFromToken(token.substring(7));
				userId = userService.userIdByEmail(email);
			}
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 포스트 읽기에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	// Update
	@PutMapping("/")
	public Object updateBlog(@RequestBody BlogDto blog) {
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int cnt = blogService.modify(blog);
		
		if(cnt != 0) {
			map.put("msg", "블로그 포스트 수정에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 포스트 수정에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	// Delete
	@DeleteMapping("/{id}")
	public Object deleteBlog(@PathVariable long id) {
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int cnt = blogService.remove(id);
		
		if(cnt != 0) {
			map.put("msg", "블로그 포스트 삭제에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "블로그 포스트 삭제에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
