package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

public interface MeetService {

	List<MeetDto> getAllMeet(int startIndex);
	MeetViewResponseDto getOneMeet(long meetId);
	int deleteMeet(long meetId);

}
