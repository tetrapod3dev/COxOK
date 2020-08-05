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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.cookblog.dto.FoodCategoryDto;
import com.ssafy.cookblog.dto.IngredientDto;
import com.ssafy.cookblog.dto.LikeDto;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.request.RecipeRequestDto;
import com.ssafy.cookblog.dto.request.RecipeSearchRequestDto;
import com.ssafy.cookblog.dto.request.RecipeUpdateRequestDto;
import com.ssafy.cookblog.dto.response.RecipeResponseDto;
import com.ssafy.cookblog.service.RecipeService;
import com.ssafy.cookblog.service.UserService;
import com.ssafy.cookblog.util.JwtService;

@RestController
@CrossOrigin("*")
@RequestMapping("/recipe")
public class RecipeController {
	
	@Autowired
	RecipeService recipeService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	UserService userService;
	
	// 레시피 상세 조회
	@GetMapping("/view/{id}")
	public Object getOneRecipe(@PathVariable("id")int id, HttpServletRequest request ) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		RecipeResponseDto recipe = recipeService.getOneRecipe(id);
		int likeCnt = recipeService.recipeLikeCount(id);
		
		String token = request.getHeader("Authorization");
		String email = null;
		long userId = -1;
		
		if(recipe!=null) {
			recipe.setRecipeId(id);
			map.put("msg", "레시피 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("recipe", recipe);
			map.put("likeCnt", likeCnt);
			boolean userLike = false;
			if(token != null) {
				email = jwtService.getEmailFromToken(token.substring(7));
				userId = userService.userIdByEmail(email);
				LikeDto like = new LikeDto();
				like.setRecipeId(id);
				like.setUserId(userId);
				userLike = recipeService.reipceUserLike(like);
			}
			map.put("userLike", userLike);
			map.put("userId", userId);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	// 레시피 전체 조회
	@GetMapping("/all/{startIndex}")
	public Object getAllRecipe(@PathVariable("startIndex") int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		List<RecipeDto> list = recipeService.selectAll(6*startIndex);
		if(list!=null) {
			map.put("msg", "레시피 조회를 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			map.put("total",recipeService.getTotalRecipeNum());
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 찾지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@GetMapping("/goRegister")		//모든 재료 받기
	public Object getAllIngredient() {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		List<IngredientDto> ingredients = recipeService.selectAllIngredient();
		List<FoodCategoryDto> catogories = recipeService.selectAllFoodCategory();
		
		map.put("msg", "성공");
		map.put("status", "success");
		map.put("ingredients",ingredients);
		map.put("catogories",catogories);
		
		response = new ResponseEntity(map, HttpStatus.OK);
		return response;
	}
	
	// 레시피 등록
	@PostMapping("/register")
	public Object registerRecipe(@ModelAttribute RecipeRequestDto recipe, HttpServletRequest request) {
	
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		recipe.setUserId(userService.findUserByEmail(email).getUserId());
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		int count = recipeService.registerRecipe(recipe);
		if(count!=0) {
			map.put("msg", "레시피 등록에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피 등록에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	// 레시피 수정 버튼 클릭 시 기존 정보
	@GetMapping("/modifyInfo/{id}")
	public Object modifyInfoRecipe(@PathVariable int id) {
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		RecipeResponseDto recipe = recipeService.getOneRecipe(id);
		
		if(recipe != null) {
			recipe.setRecipeId(id);
			map.put("msg", "레시피 정보 조회 성공");
			map.put("status", "success");
			map.put("recipe", recipe);
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "레시피 정보 조회 실패");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
		
	}
	
	// 레시피 수정
	@PostMapping("/modify")
	public Object modifyRecipe(@RequestBody RecipeUpdateRequestDto recipe, HttpServletRequest request) {
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		recipe.setUserId(userService.findUserByEmail(email).getUserId());
		
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();

		int count = recipeService.updateRecipe(recipe);
		
		if(count != 0) {
			map.put("msg", "레시피 수정에 성공했습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("msg", "레시피 수정에 실패했습니다.");
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
		
		int count = recipeService.deleteRecipe(id);
		if(count != 0) {
			map.put("msg", "레시피가 삭제되었습니다.");
			map.put("status", "success");
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 삭제하지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@PostMapping("/search/{startIndex}")
	public Object search(@ModelAttribute RecipeSearchRequestDto recipeSearchRequestDto,
			@PathVariable("startIndex")int startIndex) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();		
		
		recipeSearchRequestDto.setStartIndex(startIndex*6);
		List<RecipeDto> list = recipeService.search(recipeSearchRequestDto);
		
		if(list!=null) {
			map.put("msg", "레시피 검색을 성공했습니다.");
			map.put("status", "success");
			map.put("list",list);
			map.put("total",recipeService.searchTotal(recipeSearchRequestDto));
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "레시피를 검색을 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		return response;
	}
	
	@PostMapping("/like")
	public Object like(@RequestBody LikeDto like, HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		like.setUserId(userService.findUserByEmail(email).getUserId());
		
		int count = recipeService.insertRecipeLike(like);
		int likeCnt = recipeService.recipeLikeCount(like.getRecipeId());
		
		if(count != 0) {
			map.put("msg", "좋아요가 반영되었습니다.");
			map.put("status", "success");
			map.put("likeCnt", likeCnt);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "좋아요를 반영하지 못했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
	
	@DeleteMapping("/likeCancel")
	public Object likeCancel(@RequestBody LikeDto like, HttpServletRequest request) {
		ResponseEntity response = null;
		Map<String,Object> map = new HashMap<String, Object>();
		
		String email = jwtService.getEmailFromToken(request.getHeader("Authorization").substring(7));
		like.setUserId(userService.findUserByEmail(email).getUserId());
		
		int count = recipeService.cancelRecipeLike(like);
		int likeCnt = recipeService.recipeLikeCount(like.getRecipeId());
		
		if(count != 0) {
			map.put("msg", "좋아요가 취소되었습니다.");
			map.put("status", "success");
			map.put("likeCnt", likeCnt);
			response = new ResponseEntity(map, HttpStatus.OK);
		}else {
			map.put("msg", "좋아요 취소에 실패했습니다.");
			map.put("status", "fail");
			response = new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
		
		return response;
	}
}
