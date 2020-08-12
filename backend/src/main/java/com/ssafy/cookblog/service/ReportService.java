package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.ReportRecipeDto;

public interface ReportService {

	public int reportRecipe(ReportRecipeDto reportDto);
	public List<ReportRecipeDto> getAllReport();

}
