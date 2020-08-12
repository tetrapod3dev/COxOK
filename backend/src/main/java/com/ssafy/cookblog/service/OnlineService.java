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
	
	public List<OnlineDto> getOnlineByType(OnlineDto onlineDto);
	public long getOnlineTotalByType(String type);
	
	public List<Long> getOnlineJoinList(long onlineId);
	
	public List<OnlineDto> getOnlineByUserid(long userId);
	public List<OnlineDto> getOnlineByMeetJoinUserid(long userId);
}
