package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;

public interface VersusDao {

	int registerVersus(VersusDto versusDto);
	int winVersus(VersusPointDto versusPoint);
	List<VersusResponseDto> selectAllVersus(int startIndex);

}
