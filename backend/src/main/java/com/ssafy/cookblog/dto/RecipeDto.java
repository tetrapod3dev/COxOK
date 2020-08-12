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
public class RecipeDto {
	private long recipeId;
	private String recipeName;
	private String recipeThumbnailSrc;
	private String recipeDetail;
	private long userId;
	private LocalDateTime regTime;
	private int cookTime;
	private int level;
	private int calorie;
	private int carbon;
	private int protein;
	private int fat;
	private int sugar;
	private int natrium;
	private double avgRating;

	private int startIndex;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecipeDto [recipeId=");
		builder.append(recipeId);
		builder.append(", recipeName=");
		builder.append(recipeName);
		builder.append(", recipeThumbnailSrc=");
		builder.append(recipeThumbnailSrc);
		builder.append(", recipeDetail=");
		builder.append(recipeDetail);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", regTime=");
		builder.append(regTime);
		builder.append(", cookTime=");
		builder.append(cookTime);
		builder.append(", level=");
		builder.append(level);
		builder.append(", calorie=");
		builder.append(calorie);
		builder.append(", carbon=");
		builder.append(carbon);
		builder.append(", protein=");
		builder.append(protein);
		builder.append(", fat=");
		builder.append(fat);
		builder.append(", sugar=");
		builder.append(sugar);
		builder.append(", natrium=");
		builder.append(natrium);
		builder.append(", avgRating=");
		builder.append(avgRating);
		builder.append("]");
		return builder.toString();
	}
}
