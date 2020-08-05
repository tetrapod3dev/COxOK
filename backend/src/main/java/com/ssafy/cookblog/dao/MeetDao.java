package com.ssafy.cookblog.dao;

import com.ssafy.cookblog.dto.MeetDto;

public interface MeetDao {
	public int insert(MeetDto meetDto);
	public int update(MeetDto meetDto);
}
