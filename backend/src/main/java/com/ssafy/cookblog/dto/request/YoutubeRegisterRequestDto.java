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
public class YoutubeRegisterRequestDto {
	private long youtubeId;
	private long recipeId;
	private List<String> videoId;
	private List<String> thumbnailSrc;
}
