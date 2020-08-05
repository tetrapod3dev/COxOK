package com.ssafy.cookblog.service;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;

public interface MeetService {
	public int register(MeetRegisterRequestDto meetRegisterReqeustDto);
	public int modify(MeetDto meetDto);
}
