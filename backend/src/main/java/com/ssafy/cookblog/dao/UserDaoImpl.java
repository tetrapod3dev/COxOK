package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.RecipeDto;
import com.ssafy.cookblog.dto.UserDto;
import com.ssafy.cookblog.dto.request.UserModifyRequestDto;

@Repository
public class UserDaoImpl implements UserDao{
	
	@Autowired
	SqlSession session;
	
	//Create
	@Override
	public int insert(UserDto user) {
		return session.insert("user.insert",user);
	}
	
	//Read
	@Override
	public UserDto select(String email) {
		return session.selectOne("user.select",email);
	}
	
	@Override
	public UserDto selectNickname(String nickname) {
		return session.selectOne("user.selectNickname", nickname);
	}
	
	@Override
	public List<UserDto> selectAll() {
		return session.selectList("user.selectAll");
	}
	
	public UserDto login(UserDto user) {
		return session.selectOne("user.login",user);
	}
	
	@Override
	public int selectTotalRecipe(long userId) {
		return session.selectOne("user.selectTotalRecipe",userId);
	}
	
	@Override
	public int selectTotalLike(long userId) {
		return session.selectOne("user.selectTotalLike",userId);
	}
	
	@Override
	public int selectTotalMeet(long userId) {
		return session.selectOne("user.selectTotalMeet",userId);
	}
	
	//Update
	@Override
	public int update(UserModifyRequestDto userModifyRequestDto) {
		return session.update("user.update", userModifyRequestDto);
	}
	@Override
	public int updateEmail(UserDto user) {
		return session.update("user.updateAuth",user);
	}
	
	//Delete
	@Override
	public int delete(String email) {
		return session.delete("user.delete",email);
	}

	@Override
	public long userIdByEmail(String email) {
		return session.selectOne("user.selectUseridByEmail", email);
	}

	@Override
	public List<RecipeDto> likeRecipe(long userId) {
		return session.selectList("likeRecipe.userRecipeLike", userId);
	}

}
