package com.ssafy.cookblog.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
	private long reviewId;
	private long userId;
	private long recipeId;
	private double rating;
	private String content;
	private LocalDateTime regTime;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Review [reviewId=");
		builder.append(reviewId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", recipeId=");
		builder.append(recipeId);
		builder.append(", rating=");
		builder.append(rating);
		builder.append(", content=");
		builder.append(content);
		builder.append(", regTime=");
		builder.append(regTime);
		builder.append("]");
		return builder.toString();
	}
}
