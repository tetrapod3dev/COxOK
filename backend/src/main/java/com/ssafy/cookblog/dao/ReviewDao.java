package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.ReviewDto;
import com.ssafy.cookblog.dto.response.ReviewResponseDto;

public interface ReviewDao {
	//Create
	public int insert(ReviewDto review);
	
	//Read
	public ReviewResponseDto select(long reviewId);
	public List<ReviewResponseDto> selectAll(long recipeId);
	public int reviewCount(long recipeId);
	public long recipeIdByrevieId(long reviewId);
	
	//Update
	public int update(ReviewDto review);
	public int updateRecipeRating(ReviewDto review);
	public int updateRecipeRatingZero(long recipeId);
	
	//Delete
	public int delete(long reviewId);
	public int deleteByRecipe(long recipeId);
}
