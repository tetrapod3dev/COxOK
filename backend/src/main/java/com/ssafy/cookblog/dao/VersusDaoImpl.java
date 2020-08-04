package com.ssafy.cookblog.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.VersusDto;
import com.ssafy.cookblog.dto.VersusPointDto;

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

}
