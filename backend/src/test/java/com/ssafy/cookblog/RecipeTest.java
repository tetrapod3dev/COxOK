package com.ssafy.cookblog;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.cookblog.dao.RecipeDao;
import com.ssafy.cookblog.dao.RecipeDaoImpl;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.request.RecipeSearchRequestDto;
import com.ssafy.cookblog.dto.response.RecipeResponseDto;
import com.ssafy.cookblog.service.RecipeService;
import com.ssafy.cookblog.util.JwtService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeTest {

	@Autowired
	RecipeService recipeService;
	
	@Test
	public void RecipeTest() {
		
		List<String> list1 = new ArrayList<String>();
		List<String> list2 = new ArrayList<String>();

		list1.add("1"); 
		list1.add("8"); 
		
		RecipeSearchRequestDto dto =new RecipeSearchRequestDto();
//		dto.setSelectedIngredients(list1);
		dto.setSelectedCategory(list1);
		
		List<RecipeDto> result = recipeService.search(dto);
		for(RecipeDto d : result) {
			System.out.println(d);
		}
		
		Long count = recipeService.searchTotal(dto);
		System.out.println(count);
	}
}
