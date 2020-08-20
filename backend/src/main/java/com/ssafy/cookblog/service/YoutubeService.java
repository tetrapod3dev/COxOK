package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.YoutubeDto;
import com.ssafy.cookblog.dto.request.YoutubeRegisterRequestDto;

public interface YoutubeService {
	public int register(YoutubeRegisterRequestDto dto);
	public List<YoutubeDto> get(long recipeId);
}
