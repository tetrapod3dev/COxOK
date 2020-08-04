package com.ssafy.cookblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.RecipePhotoDto;

@Repository
public class PhotoDaoImpl implements PhotoDao{
	@Autowired
	SqlSession session;
	
	@Override
	public int insertPhoto(RecipePhotoDto dto) {
		return session.insert("recipePhoto.insertPhotoSrc",dto);
	}
}
