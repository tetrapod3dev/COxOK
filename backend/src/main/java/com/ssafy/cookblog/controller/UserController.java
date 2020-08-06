package com.ssafy.cookblog.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.ssafy.cookblog.dto.FoodCategoryDto;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.request.UserModifyRequestDto;
import com.ssafy.cookblog.service.RecipeService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.Base64Service;
import com.ssafy.cookblog.util.EmailService;
import com.ssafy.cookblog.util.JwtService;


@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private RecipeService recipeService;
	
	@Autowired
	private Base64Service base64Service;

	// 로그인
	@PostMapping("/login")
	public Object login(@RequestBody UserDto userRequest) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = userRequest.getEmail();
		String password = userRequest.getPassword();

		UserDto client = new UserDto(email, password);

		UserDto user = userService.login(client);

		if (user != null) {
			if (user.getAuth() == 0) {
				map.put("msg","이메일 인증 미완료");
				response = new ResponseEntity(map,HttpStatus.BAD_REQUEST);
				return response;
			}
			map.put("msg","로그인 성공");
			map.put("status", "success");
			map.put("email", email);
			map.put("nickname", user.getNickname());
			String token = jwtService.generateToken(email);
			// 위 토큰담아주기
			map.put("token", token);

			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("status", "fail");
			map.put("msg","로그인 실패");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	// 토큰 유효성 체크
	@PostMapping("/check")
	public Object isValid(String email, String token) throws Exception {
		Map<String,Object> map = new HashMap<String, Object>();
		ResponseEntity response = null;
		
		if (jwtService.isValid(email, token) == false) {
			map.put("msg","로그인 정보가 유효하지 않음");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
			return response;
		}
		map.put("msg","성공");
		map.put("status", "fail");
		response = new ResponseEntity(map, HttpStatus.OK);
		return response;
	}

	// 회원가입
	@PostMapping("/signup")
	public Object signup(@RequestBody UserDto request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = request.getEmail();

		userService.register(request);
		map.put("msg","회원가입 성공");
		map.put("status", "success");
		response = new ResponseEntity(map, HttpStatus.OK);
		
		String encoding = base64Service.encode(email);
		String link="http://i3a104.p.ssafy.io/user/auth/"+encoding;
		
		emailService.sendSimpleMessage(email, "CO-OK 가입 인증메일 입니다.", 
						email + "님 가입을 환영합니다.<br>" +
						"가입을 인증하기 위해서 아래의 링크를 클릭해주십시오.<br> "+
						"<strong>아래 링크를 클릭하면 인증이 완료됩니다.</strong><br> "+
						"<a href='"+link+"'>링크</a>"
				);

		return response;
	}
	
	// 이메일 인증
	@GetMapping("/auth/{code}")
	public Object authEmail(@PathVariable("code") String code,HttpServletResponse res) throws Exception {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = base64Service.decode(code);
		System.out.println("code : " + code);
		System.out.println("email : " + email);
		
		UserDto user=new UserDto();
		user.setEmail(email);
		
		int count = userService.authEmail(user);
		if(count==1) {
			map.put("msg","인증 성공");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
			res.sendRedirect("/");
		}else {
			map.put("msg","이메일 인증 실패");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	// 회원정보
	@GetMapping("/mypage")
	public Object userInfo(HttpServletRequest request) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		ResponseEntity response = null;
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		UserDto user = userService.findUserByEmail(email);
		List<FoodCategoryDto> categories = recipeService.selectAllFoodCategory();
		
		if(user != null) {
			map.put("msg", "회원 정보 성공");
			map.put("status", "success");
			map.put("user", user);
			map.put("categories", categories);
			response = new ResponseEntity(map,HttpStatus.OK);
			return response;
		} else {
			map.put("msg", "회원 정보 로딩 실패");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
			return response;
		}
		
	}
	
	// 회원이 좋아하는 레시피 목록
	@GetMapping("/likeRecipe")
	public Object userLikeRecipe(HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		Long userId = (userService.findUserByEmail(email).getUserId());
		
		List<RecipeDto> likeRecipe = userService.likeRecipe(userId);
		map.put("msg", "유저 좋아요 레시피 목록 불러오기 성공");
		map.put("userLikeRecipe", likeRecipe);
		map.put("status", "success");
		
		response = new ResponseEntity(map, HttpStatus.OK);
		return response;
	}

	// 회원정보수정
	@PutMapping("/modify")
	public Object modifyMember(@RequestBody UserModifyRequestDto userModifyRequestDto) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		int count = userService.modify(userModifyRequestDto);
		
		if (count == 1) {
			map.put("msg", "회원 정보 수정 성공");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "회원 정보 수정 실패");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	// 회원탈퇴
	@DeleteMapping("/withdrawal")
	public Object delete(HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		
		int count = userService.remove(email);
		if (count == 1) {
			map.put("msg", "회원 탈퇴 성공");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "회원 탈퇴 실패");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}

	// 이메일 중복 체크
	@GetMapping("/email/{email}")
	public Object findEmail(@PathVariable("email") String email) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		UserDto user = userService.findUserByEmail(email);

		if (user == null) {
			map.put("msg", "성공");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "이메일 중복");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}

	// 닉네임 중복 체크
	@GetMapping("/nickname/{nickname}")
	public Object findNickname(@PathVariable("nickname") String nickname) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		UserDto user = userService.findUserByNickname(nickname);

		if (user == null) {
			map.put("msg", "성공");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "이메일 중복");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
