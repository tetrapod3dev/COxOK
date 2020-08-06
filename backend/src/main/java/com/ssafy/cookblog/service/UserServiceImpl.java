package com.ssafy.cookblog.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cookblog.dao.CategoryDao;
import com.ssafy.cookblog.dao.UserDao;
import com.ssafy.cookblog.dto.FavoriteDto;
import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.request.UserModifyRequestDto;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserDao userDao;
	
	@Autowired
	CategoryDao categoryDao;
	
	//회원가입
	@Transactional
	public int register(UserDto user) {
		return userDao.insert(user);
	}
	
	//회원조회
	public UserDto findUserByEmail(String email) {
		return userDao.select(email);
	}
	public UserDto findUserByNickname(String nickname) {
		return userDao.selectNickname(nickname);
	}
	public List<UserDto> findAllUser() {
		return userDao.selectAll();
	}
	public UserDto login(UserDto user) {
		return userDao.login(user);
	}
	public long userIdByEmail(String email) {
		return userDao.userIdByEmail(email);
	}
	
	//회원수정
	@Transactional 
	public int modify(UserModifyRequestDto userModifyRequestDto) {
		long userId = userModifyRequestDto.getUserId();
		categoryDao.deleteUserFavoriteCategory(userId);
		
		List<Long> categories = userModifyRequestDto.getFoodCategoryId();
		
		for(long categoryId : categories) {
			FavoriteDto dto = new FavoriteDto();
			dto.setUserId(userId);
			dto.setFoodCategoryId(categoryId);
			categoryDao.insertUserFavoriteCategory(dto);
		}
		return userDao.update(userModifyRequestDto);
	}
	
	@Transactional
	public int authEmail(UserDto user) {
		return userDao.updateEmail(user);
	}

	//회원삭제
	@Transactional
	public int remove(String email) {
		return userDao.delete(email);
	}

	@Override
	public List<Long> likeRecipe(long userId) {
		return userDao.likeRecipe(userId);
	}
	
}
