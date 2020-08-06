package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public int register(ReviewDto reviewDto) {
		int count = reviewDao.insert(reviewDto);
		
		if(count == 1) {
			reviewDao.updateRecipeRating(reviewDto);
		}
		return count;
	}
	
	public int modify(ReviewDto reviewDto) {
		int count = reviewDao.update(reviewDto);
		
		if(count == 1) {
			reviewDao.updateRecipeRating(reviewDto);
		}
		
		return count;
	}
	
	public int remove(int reviewId) {
		int count = reviewDao.delete(reviewId);
		
		ReviewDto reviewDto = new ReviewDto();
		reviewDto.setReviewId(reviewId);

		if(count == 1) {
			reviewDao.updateRecipeRating(reviewDto);
		}
		
		return count;
	}

}
