package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;

public interface VersusService {
	public int registerVersus(VersusDto versusDto);
	public int winVersus(VersusPointDto versusPoint);
	public List<VersusResponseDto> getAllVersus(int startIndex);
	public VersusViewResponseDto getOneVersus(long versusId);
	public int removeVersus(long versusId);
	public List<VersusPointDto> getVersusResult(long versusId);
}
