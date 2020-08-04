package com.ssafy.cookblog.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeUpdateRequestDto {
	private long recipeId;
	private String recipeName;
	private String recipeDetail;
	private String recipeThumbnail;
	private int[] categories;
	private int[] ingredientPk;
	private int[] ingredientAmount;
	private String[] description;
	private String[] photo;
	
	private String level;
	private String cookTime;
	
	private long userId;
}
