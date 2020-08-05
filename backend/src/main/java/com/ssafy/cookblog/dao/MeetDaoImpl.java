package com.ssafy.cookblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MeetDaoImpl implements MeetDao {

	@Autowired
	SqlSession session;
	
}
