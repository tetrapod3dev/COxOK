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
public class BlogDto {
	private long userId;
	private long recipeId;
	private String title;
	private String content;
	private LocalDateTime regTime;
	private String tag1;
	private String tag2;
	private String tag3;
}
