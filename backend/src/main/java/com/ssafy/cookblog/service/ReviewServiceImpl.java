package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cookblog.dao.ReviewDao;
import com.ssafy.cookblog.dto.ReviewDto;
import com.ssafy.cookblog.dto.response.ReviewResponseDto;

@Service
public class ReviewServiceImpl implements ReviewService{

	@Autowired
	private ReviewDao reviewDao;
	
	public List<ReviewResponseDto> getAllReview(int recipeId) {
		List<ReviewResponseDto> list = reviewDao.selectAll(recipeId);
		return list;
	}
	
	@Transactional 
	public int register(ReviewDto reviewDto) {
		int count = reviewDao.insert(reviewDto);
		
		if(count == 1) {
			reviewDao.updateRecipeRating(reviewDto);
		}
		return count;
	}
	
	@Transactional 
	public int modify(ReviewDto reviewDto) {
		int count = reviewDao.update(reviewDto);
		
		if(count == 1) {
			reviewDao.updateRecipeRating(reviewDto);
		}
		
		return count;
	}
	
	@Transactional 
	public int remove(int reviewId) {
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewId(reviewId);
		long recipeId = reviewDao.recipeIdByrevieId(reviewId);
		int count = reviewDao.delete(reviewId);
		
		int cnt = reviewDao.reviewCount(recipeId);
		if(count == 1) {
			if(cnt == 0)
				reviewDao.updateRecipeRatingZero(recipeId);
			else	
				reviewDao.updateRecipeRating(reviewDto);
		}
		
		return count;
	}

}
