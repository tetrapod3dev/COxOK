package com.ssafy.cookblog.dto.response;

import java.util.List;

import com.ssafy.cookblog.dto.RecipeDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersusViewResponseDto {
	private long versusId;
	private String title;
	private String content;
	private long userId;
	private List<Long> recipeListId;
	private List<RecipeDto> recipeList;
	
	private long recipe_id_1;
	private long recipe_id_2;
	private long recipe_id_3;
	private long recipe_id_4;
	private long recipe_id_5;
	private long recipe_id_6;
	private long recipe_id_7;
	private long recipe_id_8;
	private long recipe_id_9;
	private long recipe_id_10;
	private long recipe_id_11;
	private long recipe_id_12;
	private long recipe_id_13;
	private long recipe_id_14;
	private long recipe_id_15;
	private long recipe_id_16;
	public void change() {
		
	}
}
	