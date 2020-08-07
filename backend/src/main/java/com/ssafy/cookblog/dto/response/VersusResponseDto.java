package com.ssafy.cookblog.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VersusResponseDto {
	private long versusId;
	private String title;
	private String recipeThumbnail1;
	private String recipeThumbnail2;
}

