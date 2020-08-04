package com.ssafy.cookblog.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersusDto {
	private long versusId;
	private String title;
	private String content;
	private long userId;
	private List<Long> recipeIdList;

}


	
