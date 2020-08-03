package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeFoodCategoryDto {
	private long recipefoodCategoryId;
	private long recipeId;
	private long foodCategoryId;
}
