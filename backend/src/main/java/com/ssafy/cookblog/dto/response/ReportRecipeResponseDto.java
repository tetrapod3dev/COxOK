package com.ssafy.cookblog.dto.response;

import com.ssafy.cookblog.dto.ReportRecipeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReportRecipeResponseDto {

	private ReportRecipeDto reportRecipeDto;
	private RecipeResponseDto recipeResponseDto;

}
