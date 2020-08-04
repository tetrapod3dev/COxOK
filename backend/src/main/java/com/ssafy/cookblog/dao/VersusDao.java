package com.ssafy.cookblog.dao;

import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;

public interface VersusDao {

	int registerVersus(VersusDto versusDto);
	int winVersus(VersusPointDto versusPoint);

}
