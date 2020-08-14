package com.ssafy.cookblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cookblog.dao.RecipeDao;
import com.ssafy.cookblog.dao.ReportDao;
import com.ssafy.cookblog.dto.ReportRecipeDto;
import com.ssafy.cookblog.dto.response.ReportRecipeResponseDto;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private RecipeDao recipeDao;

	@Override
	@Transactional 
	public int reportRecipe(ReportRecipeDto reportDto) {
		return reportDao.insertReportRecipe(reportDto);
	}

	@Override
	public List<ReportRecipeResponseDto> getAllReport() {
		List<ReportRecipeResponseDto> result = new ArrayList<>();
		
		List<ReportRecipeDto> temp = reportDao.selectAllReport();
		
		for(ReportRecipeDto d : temp) {
			ReportRecipeResponseDto dto =new ReportRecipeResponseDto();
			dto.setReportRecipeDto(d);
			dto.setRecipeResponseDto(recipeDao.select(d.getRecipeId()));
			result.add(dto);
		}
		
		return result;
	}

	@Override
	@Transactional 
	public int cancelReportRecipe(long recipeId, long userId) {
		ReportRecipeDto reportDto = new ReportRecipeDto();
		reportDto.setUserId(userId);
		reportDto.setRecipeId(recipeId);
		return reportDao.deleteReportRecipe(reportDto);
	}

	@Override
	@Transactional 
	public boolean getReportRecipe(long recipeId, long userId) {
		ReportRecipeDto reportDto = new ReportRecipeDto();
		reportDto.setUserId(userId);
		reportDto.setRecipeId(recipeId);
		return reportDao.selectReportRecipe(reportDto);
	}

}
