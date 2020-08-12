package com.ssafy.cookblog.service;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;
import java.util.List;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

public interface MeetService {

	public int register(MeetRegisterRequestDto meetRegisterReqeustDto);
	public int modify(MeetDto meetDto);
	public List<MeetDto> getAllMeet(int startIndex);
	public MeetViewResponseDto getOneMeet(long meetId);
	public int deleteMeet(long meetId);

	public int registerMeetJoin(MeetJoinDto meetJoinDto);
	public int removeMeetJoin(MeetJoinDto meetJoinDto);
	
	public List<MeetDto> getMeetByType(MeetDto meetDto);
	public long getMeetTotalByType(String type);
}
