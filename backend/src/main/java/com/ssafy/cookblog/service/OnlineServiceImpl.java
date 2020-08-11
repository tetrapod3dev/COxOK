package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.OnlineDao;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.OnlineDto;

@Service
public class OnlineServiceImpl implements OnlineService {
	
	@Autowired
	OnlineDao onlineDao;
	
	@Override
	public int register(OnlineDto onlineDto) {
		return onlineDao.insert(onlineDto);
	}
	@Override
	public int modify(OnlineDto onlineDto) {
		return onlineDao.update(onlineDto);
	}
	@Override
	public List<OnlineDto> getAllOnline(int startIndex) {
		return onlineDao.selectAll(startIndex);
	}
	@Override
	public OnlineDto getOnline(long onlineId) {
		return onlineDao.selectOnline(onlineId);
	}
	
	@Override
	public int remove(long onlineId) {
		return onlineDao.delete(onlineId);
	}
	@Override
	public int registerOnlineJoin(MeetJoinDto meetJoinDto) {
		return onlineDao.insertOnlineJoin(meetJoinDto);
	}
	@Override
	public int removeOnlineJoin(MeetJoinDto meetJoinDto) {
		return onlineDao.deleteOnlineJoin(meetJoinDto);
	}
}
