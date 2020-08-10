package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.ReportDao;
import com.ssafy.cookblog.dto.ReportRecipeDto;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDao reportDao;

	@Override
	public int reportRecipe(ReportRecipeDto reportDto) {
		return reportDao.insertReportRecipe(reportDto);
	}

	@Override
	public List<ReportRecipeDto> getAllReport() {
		return reportDao.selectAllReport();
	}

}
