package com.ssafy.cookblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientResponseDto {	
	private long ingredientId;
	private String name;
	private int amount;
	private String unit;
}