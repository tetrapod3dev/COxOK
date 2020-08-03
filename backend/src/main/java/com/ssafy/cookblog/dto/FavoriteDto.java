package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDto {
	private long favoriteId;
	private long userId;
	private long foodCategoryId;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Favorite [favoriteId=");
		builder.append(favoriteId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", foodCategoryId=");
		builder.append(foodCategoryId);
		builder.append("]");
		return builder.toString();
	}
}
