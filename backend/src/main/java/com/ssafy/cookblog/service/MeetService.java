package com.ssafy.cookblog.service;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;
import java.util.List;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

public interface MeetService {

	public int register(MeetRegisterRequestDto meetRegisterReqeustDto);
	public int modify(MeetDto meetDto);
	List<MeetDto> getAllMeet(int startIndex);
	MeetViewResponseDto getOneMeet(long meetId);
	int deleteMeet(long meetId);

}
