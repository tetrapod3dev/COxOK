package com.ssafy.cookblog.dao;

import java.util.List;
import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

public interface MeetDao {
	public int update(MeetDto meetDto);
	public int insert(MeetDto meetDto);

	public List<MeetDto> selectAllMeet(int startIndex);
	public MeetViewResponseDto selectMeet(long meetId);
	public List<MeetJoinDto> selectMeetJoinList(long meetId);
	public int delete(long meetId);

	public int insertMeetJoin(MeetJoinDto meetJoinDto);
	public int deleteMeetJoin(MeetJoinDto meetJoinDto);

	public List<MeetDto> selectMeetByType(String type);
	
}
