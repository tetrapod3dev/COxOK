package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.OnlineDto;

public interface OnlineDao {
	public int update(OnlineDto onlineDto);
	public int insert(OnlineDto onlineDto);
	public List<OnlineDto> selectAll(int startIndex);

}
