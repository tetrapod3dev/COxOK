package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.MeetDao;
import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

@Service
public class MeetServiceImpl implements MeetService {

	@Autowired
	private MeetDao meetDao;

	@Override
	public List<MeetDto> getAllMeet(int startIndex) {
		return meetDao.selectAllMeet(startIndex);
	}

	@Override
	public MeetViewResponseDto getOneMeet(long meetId) {
		MeetViewResponseDto meetViewResponse = meetDao.selectMeet(meetId);
		meetViewResponse.setMeetJoinList(meetDao.selectMeetJoinList(meetId));
		return meetViewResponse;
	}

	@Override
	public int deleteMeet(long meetId) {
		return meetDao.delete(meetId);
	}
	
	
}
