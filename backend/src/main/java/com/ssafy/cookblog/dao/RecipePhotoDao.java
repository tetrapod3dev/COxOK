package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.RecipePhotoDto;

public interface RecipePhotoDao {
	//Create
	public int insert(RecipePhotoDto recipePhoto);
	
	//Read
	public List<RecipePhotoDto> selectAll(long recipeId);
	
	//Update
	
	//Delete
	public int delete(long recipeId);
}
