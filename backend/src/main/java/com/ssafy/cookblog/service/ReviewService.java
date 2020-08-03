package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.ReviewDto;
import com.ssafy.cookblog.dto.response.ReviewResponseDto;

public interface ReviewService {
	public List<ReviewResponseDto> getAllReview(int recipeId);
	
	public int register(ReviewDto reviewDto);
	
	public int modify(ReviewDto reviewDto);
	
	public int remove(int reviewId);
}
