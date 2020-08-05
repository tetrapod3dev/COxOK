package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.request.UserModifyRequestDto;

public interface UserService {
	
	//회원가입
	public int register(UserDto user);
	
	
	//회원목록
	public UserDto findUserByEmail(String email);
	public UserDto findUserByNickname(String nickname);
	public List<UserDto> findAllUser();
	public UserDto login(UserDto user);
	public long userIdByEmail(String email);

	//회원정보수정
	public int modify(UserModifyRequestDto userModifyRequestDto);
	public int authEmail(UserDto user);
	
	//회원삭제
	public int remove(String email);
	
}
