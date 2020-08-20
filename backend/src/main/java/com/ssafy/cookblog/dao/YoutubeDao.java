package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.YoutubeDto;

public interface YoutubeDao {
	public int insert(YoutubeDto youtubeDto);
	public List<YoutubeDto> select(long recipeId);
}
