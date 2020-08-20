package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.YoutubeDao;
import com.ssafy.cookblog.dto.YoutubeDto;
import com.ssafy.cookblog.dto.request.YoutubeRegisterRequestDto;

@Service
public class YoutubeServiceImpl implements YoutubeService {
	
	@Autowired
	YoutubeDao youtubeDao;
	
	
	@Override
	public int register(YoutubeRegisterRequestDto dto) {
		
		int count = 0;
		
		for(int i=0;i<3;i++) {
			YoutubeDto youtubeDto = new YoutubeDto();
			youtubeDto.setRecipeId(dto.getRecipeId());
			youtubeDto.setVideoId(dto.getVideoId().get(i));
			youtubeDto.setThumbnailSrc(dto.getThumbnailSrc().get(i));
			
			count +=youtubeDao.insert(youtubeDto);
		}
		
		return count;
	}
	
	@Override
	public List<YoutubeDto> get(long recipeId) {
		return youtubeDao.select(recipeId);
	}
	
}
