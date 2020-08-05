package com.ssafy.cookblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.MeetDto;

@Repository
public class MeetDaoImpl implements MeetDao {

	@Autowired
	SqlSession session;
	
	@Override
	public int insert(MeetDto meetDto) {
		return session.insert("meet.insert",meetDto);
	}
	
	@Override
	public int update(MeetDto meetDto) {
		return session.update("meet.update",meetDto);
	}
}
