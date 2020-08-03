package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipePhotoDto {
	private long recipePhotoId;
	private long recipeId;
	private String photoSrc;
	private String photoDetail;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RecipePhotoDto [recipePhotoId=");
		builder.append(recipePhotoId);
		builder.append(", recipeId=");
		builder.append(recipeId);
		builder.append(", photoSrc=");
		builder.append(photoSrc);
		builder.append(", photoDetail=");
		builder.append(photoDetail);
		builder.append("]");
		return builder.toString();
	}
}
