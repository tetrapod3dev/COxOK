package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.request.UserModifyRequestDto;

public interface UserDao {
	//Create
	public int insert(UserDto user);
	
	//Read
	public UserDto select(String email);
	public UserDto selectNickname(String nickname);
	public List<UserDto> selectAll();
	public UserDto login(UserDto user);
	public long userIdByEmail(String email);
	public List<Long> likeRecipe(long userId);
	
	//Update
	public int update(UserModifyRequestDto userModifyRequestDto);
	public int updateEmail(UserDto user);
	
	//Delete
	public int delete(String email);
}
