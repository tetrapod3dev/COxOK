package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.ReportRecipeDto;

@Repository
public class ReportDaoImpl implements ReportDao {
	
	@Autowired
	SqlSession session;

	@Override
	public int insertReportRecipe(ReportRecipeDto reportDto) {
		return session.insert("report.insert",reportDto);
	}

	@Override
	public List<ReportRecipeDto> selectAllReport() {
		return session.selectList("report.selectAll");
	}

}
