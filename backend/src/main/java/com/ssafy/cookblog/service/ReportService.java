package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.ReportRecipeDto;
import com.ssafy.cookblog.dto.response.ReportRecipeResponseDto;

public interface ReportService {

	public int reportRecipe(ReportRecipeDto reportDto);
	public List<ReportRecipeResponseDto> getAllReport();
	public int cancelReportRecipe(long recipeId, long userId);
	public boolean getReportRecipe(long recipeId, long userId);

}
