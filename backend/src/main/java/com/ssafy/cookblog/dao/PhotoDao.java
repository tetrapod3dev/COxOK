package com.ssafy.cookblog.dao;

import com.ssafy.cookblog.dto.RecipePhotoDto;
import com.ssafy.cookblog.dto.request.PhotoRequestDto;

public interface PhotoDao {
	int insertPhoto(RecipePhotoDto dto);
}
