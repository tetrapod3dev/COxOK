package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;
import com.ssafy.cookblog.dto.response.VersusResponseDto;
import com.ssafy.cookblog.dto.response.VersusViewResponseDto;

@Repository
public class VersusDaoImpl implements VersusDao {

	@Autowired
	SqlSession session;
	
	//Create
	@Override
	public int registerVersus(VersusDto versusDto) {
		return session.insert("versus.insert",versusDto);
	}

	//Insert Versus-point
	@Override
	public int winVersus(VersusPointDto versusPoint) {
		return session.insert("versus.insertVersusPoint",versusPoint);
	}

	@Override
	public List<VersusResponseDto> selectAllVersus(int startIndex) {
		return session.selectList("versus.selectAllVersus", startIndex);
	}

	@Override
	public VersusViewResponseDto selectVersus(long versusId) {
		return session.selectOne("versus.selectVersus", versusId);
	}

	@Override
	public List<RecipeDto> selectVersusRecipe(VersusViewResponseDto versusView) {
		return session.selectList("versus.selectVersusRecipe", versusView);
	}

	@Override
	public int delete(long versusId) {
		return session.delete("versus.delete", versusId);
	}

	@Override
	public List<VersusPointDto> selectVersusResult(long versusId) {
		return session.selectList("versus.selectVersusResult", versusId);
	}

	@Override
	public List<VersusPointDto> selectTop3VersusResult() {
		return session.selectList("versus.selectTop3VersusResult");
	}

}
