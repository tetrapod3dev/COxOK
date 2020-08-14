package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.ReportRecipeDto;

public interface ReportDao {

	public int insertReportRecipe(ReportRecipeDto reportDto);
	public List<ReportRecipeDto> selectAllReport();
	public int deleteReportRecipe(ReportRecipeDto reportDto);
	public boolean selectReportRecipe(ReportRecipeDto reportDto);

}
