package com.ssafy.cookblog.dto.request;

import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeSearchRequestDto {
	private List<String> selectedCategory;
	private List<String> selectedIngredients;
	
	private List<Integer> categoryId;
	private List<Integer> ingredientId;
	
	private int level;
	private int cookTime;
	
	private int startIndex;
}
