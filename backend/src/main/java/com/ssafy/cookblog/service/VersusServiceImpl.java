package com.ssafy.cookblog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.VersusDao;
import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;

@Service
public class VersusServiceImpl implements VersusService {
	
	@Autowired
	VersusDao versusDao;

	@Override
	public int registerVersus(VersusDto versusDto) {
		return versusDao.registerVersus(versusDto);
	}

	@Override
	public int winVersus(VersusPointDto versusPoint) {
		return versusDao.winVersus(versusPoint);
	}


}
