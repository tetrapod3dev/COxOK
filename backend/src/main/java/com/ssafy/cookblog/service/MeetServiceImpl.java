package com.ssafy.cookblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.MeetDao;

@Service
public class MeetServiceImpl implements MeetService {

	@Autowired
	private MeetDao meetDao;
	
}
