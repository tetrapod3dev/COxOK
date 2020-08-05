package com.ssafy.cookblog.dao;

import java.util.List;
import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

public interface MeetDao {
	public int update(MeetDto meetDto);
	public int insert(MeetDto meetDto);

	List<MeetDto> selectAllMeet(int startIndex);
	MeetViewResponseDto selectMeet(long meetId);
	List<MeetJoinDto> selectMeetJoinList(long meetId);
	int delete(long meetId);

}
