package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;

public interface VersusService {
	int registerVersus(VersusDto versusDto);
	int winVersus(VersusPointDto versusPoint);
	List<VersusResponseDto> getAllVersus(int startIndex);
	VersusViewResponseDto getOneVersus(long versusId);
}
