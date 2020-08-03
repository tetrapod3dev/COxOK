package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.RecipeFoodCategoryDto;

public interface CategoryDao {
	
	public int insertRecipeCategory(RecipeFoodCategoryDto recipeFoodCategoryDto);
	public List<String> selectRecipeCategoryName(long recipeId);
}
