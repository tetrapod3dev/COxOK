package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRecipeDto {
	private long reportRecipeId;
	private long recipeId;
	private long userId;
	private String nickname;
	private String reason;
}
