package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

@Repository
public class MeetDaoImpl implements MeetDao {

	@Autowired
	SqlSession session;

	@Override
	public List<MeetDto> selectAllMeet(int startIndex) {
		return session.selectList("meet.selectAllMeet", startIndex);
	}

	@Override
	public MeetViewResponseDto selectMeet(long meetId) {
		return session.selectOne("meet.selectMeet", meetId);
	}

	@Override
	public List<MeetJoinDto> selectMeetJoinList(long meetId) {
		return session.selectList("meet.selectMeetJoinList", meetId);
	}

	@Override
	public int delete(long meetId) {
		return session.delete("meet.delete", meetId);
	}
	
	@Override
	public int insert(MeetDto meetDto) {
		return session.insert("meet.insert",meetDto);
	}
	
	@Override
	public int update(MeetDto meetDto) {
		return session.update("meet.update",meetDto);
	}
	
	@Override
	public int insertMeetJoin(MeetJoinDto meetJoinDto) {
		return session.insert("meet.insertMeetJoin",meetJoinDto);
	}
	
	@Override
	public int deleteMeetJoin(long meetJoinId) {
		return session.delete("meet.deleteMeetJoin",meetJoinId);
	}
	
	@Override
	public List<MeetDto> selectMeetByType(String type) {
		return session.selectList("meet.selectMeetByType",type);
	}
}
