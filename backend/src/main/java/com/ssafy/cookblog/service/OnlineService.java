package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.OnlineDto;

public interface OnlineService {
	public int register(OnlineDto onlineDto);
	public int modify(OnlineDto onlineDto);
	public List<OnlineDto> getAllOnline(int startIndex);
	public OnlineDto getOnline(long onlineId);
	public int remove(long onlineId);
	
	public int registerOnlineJoin(MeetJoinDto meetJoinDto);
	public int removeOnlineJoin(MeetJoinDto meetJoinDto);
}
