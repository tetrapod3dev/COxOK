package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeDto {
	private long youtubeId;
	private long recipeId;
	private String videoId;
	private String thumbnailSrc;
}
