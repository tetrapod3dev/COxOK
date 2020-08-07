package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;

public interface VersusDao {

	public int registerVersus(VersusDto versusDto);
	public int winVersus(VersusPointDto versusPoint);
	public List<VersusResponseDto> selectAllVersus(int startIndex);
	public VersusViewResponseDto selectVersus(long versusId);
	public List<RecipeDto> selectVersusRecipe(VersusViewResponseDto versusView);
	public int delete(long versusId);
	public List<VersusPointDto> selectVersusResult(long versusId);

}
