package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.RecipePhotoDto;
import com.ssafy.cookblog.dto.UserDto;

@Repository
public class RecipePhotoDaoImpl implements RecipePhotoDao {
	
	@Autowired
	SqlSession session;

	//Create
	@Override
	public int insert(RecipePhotoDto recipePhoto) {
		return session.insert("recipePhoto.insert", recipePhoto);
	}

	//Read
	@Override
	public List<RecipePhotoDto> selectAll(long recipeId) {
		return session.selectList("recipePhoto.selectAll" ,recipeId);
	}

	//Delete
	@Override
	public int delete(long recipeId) {
		return session.delete("recipePhoto.delete", recipeId);
	}
		

}
