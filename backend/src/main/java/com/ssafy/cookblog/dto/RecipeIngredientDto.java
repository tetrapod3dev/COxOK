package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIngredientDto {
	private long recipeIngredientId;
	private long recipeId;
	private long ingredientId;
	private int amount;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecipeIngredientDto [recipeIngredientId=");
		builder.append(recipeIngredientId);
		builder.append(", recipeId=");
		builder.append(recipeId);
		builder.append(", ingredientId=");
		builder.append(ingredientId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append("]");
		return builder.toString();
	}
}
