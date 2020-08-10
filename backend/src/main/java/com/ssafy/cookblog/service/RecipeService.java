package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.FoodCategoryDto;
import com.ssafy.cookblog.dto.IngredientDto;
import com.ssafy.cookblog.dto.LikeDto;
import com.ssafy.cookblog.dto.request.RecipeRegisterRequestDto;
import com.ssafy.cookblog.dto.request.RecipeSearchRequestDto;
import com.ssafy.cookblog.dto.request.RecipeUpdateRequestDto;
import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.response.RecipeIngredientResponseDto;
import com.ssafy.cookblog.dto.response.RecipeResponseDto;

public interface RecipeService {
	public RecipeResponseDto getOneRecipe(long recipeId);
	public List<RecipeDto> selectAll(int startIndex);
	public int registerRecipe(RecipeRegisterRequestDto recipeRequestDto);
	public int updateRecipe(RecipeUpdateRequestDto recipeUpdateRequestDto);
	public int deleteRecipe(long recipeId);
	public List<FoodCategoryDto> selectAllFoodCategory();
	public List<IngredientDto> selectAllIngredient();
	public List<IngredientDto> selectAllIngredientAdmin(int startIndex);
	public List<RecipeIngredientResponseDto> selectRecipeIngredient(long recipeId);
	public long getRecipeId();
	public long getTotalRecipeNum();
	public List<RecipeDto> search(RecipeSearchRequestDto recipeSearchRequestDto);
	public Long searchTotal(RecipeSearchRequestDto recipeSearchRequestDto);
	public int insertRecipeLike(LikeDto like);
	public int cancelRecipeLike(LikeDto like);
	public int recipeLikeCount(long recipeId);
	public boolean reipceUserLike(LikeDto likeDto);
	public List<LikeDto> allUserLike(long recipeId);
	
	public FoodCategoryDto getRandomCategoryByUserId(long userId);
	public List<RecipeDto> getRecipeListByCategoryId(long foodCategoryId);
}
