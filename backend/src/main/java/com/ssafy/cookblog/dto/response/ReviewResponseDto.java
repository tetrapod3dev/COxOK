package com.ssafy.cookblog.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
	private int reviewId;
	private int userId;
	private int recipeId;
	private double rating;
	private String content;
	private LocalDateTime regTime;
	
	private String nickname;
}
