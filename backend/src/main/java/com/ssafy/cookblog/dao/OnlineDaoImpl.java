package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.cookblog.dto.OnlineDto;

public class OnlineDaoImpl implements OnlineDao {
	
	@Autowired
	SqlSession session;
	
	@Override
	public int insert(OnlineDto onlineDto) {
		return session.insert("online.insert",onlineDto);
	}
	@Override
	public int update(OnlineDto onlineDto) {
		return session.update("online.update",onlineDto);
	}
	
	@Override
	public List<OnlineDto> selectAll(int startIndex) {
		return session.selectList("online.selectAll",startIndex);
	}
	
}
