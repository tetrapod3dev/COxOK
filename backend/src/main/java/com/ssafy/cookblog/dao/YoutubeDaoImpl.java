package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.YoutubeDto;

@Repository
public class YoutubeDaoImpl implements YoutubeDao{

	@Autowired
	SqlSession session;
	
	@Override
	public int insert(YoutubeDto youtubeDto) {
		return session.insert("youtube.insert",youtubeDto);
	}
	
	@Override
	public List<YoutubeDto> select(long recipeId) {
		return session.selectList("youtube.select",recipeId);
	}
}
