package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FoodCategoryDto {
	private long foodCategoryId;
	private String foodCategoryName;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FoodCategoryDto [foodCategoryId=");
		builder.append(foodCategoryId);
		builder.append(", foodCategoryName=");
		builder.append(foodCategoryName);
		builder.append("]");
		return builder.toString();
	}
}
