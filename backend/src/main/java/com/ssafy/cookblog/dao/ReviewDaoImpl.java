package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.ReviewDto;
import com.ssafy.cookblog.dto.response.ReviewResponseDto;

@Repository
public class ReviewDaoImpl implements ReviewDao {
	
	@Autowired
	SqlSession session;

	//Create
	@Override
	public int insert(ReviewDto review) {
		return session.insert("review.insert",review);
	}

	//Read
	@Override
	public ReviewResponseDto select(long reviewId) {
		return session.selectOne("review.select",reviewId);
	}

	@Override
	public List<ReviewResponseDto> selectAll(long recipeId) {
		return session.selectList("review.selectAll", recipeId);
	}
	
	@Override
	public int reviewCount(long recipeId) {
		return session.selectOne("review.reviewCount", recipeId);
	}
	
	@Override
	public long recipeIdByrevieId(long reviewId) {
		return session.selectOne("review.recipeIdByrevieId", reviewId);
	}

	//Update
	@Override
	public int update(ReviewDto review) {
		return session.update("review.update",review);
	}
	
	@Override
	public int updateRecipeRatingZero(long recipeId) {
		return session.update("review.updateRecipeRatingZero", recipeId);
	}

	//Delete
	@Override
	public int delete(long reviewId) {
		return session.delete("review.delete",reviewId);
	}

	@Override
	public int deleteByRecipe(long recipeId) {
		return session.delete("review.deleteByRecipe",recipeId);
	}

	@Override
	public int updateRecipeRating(ReviewDto review) {
		return session.update("review.updateRecipeRating", review);
	}

}
