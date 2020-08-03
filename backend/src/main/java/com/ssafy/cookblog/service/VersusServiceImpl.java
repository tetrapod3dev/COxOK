package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.RecipeDao;
import com.ssafy.cookblog.dto.RecipeDto;

@Service
public class VersusServiceImpl implements VersusService {
	
	@Autowired
	RecipeDao recipeDao;

	@Override
	public List<RecipeDto> get16Recipe() {
		return recipeDao.select16Recipe();
	}

}
