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
public class MeetDto {
	private long meetId;
	private long userId;
	private long recipeId;
	private String thumbnailSrc;
	private String type;
	private int joinLimit;
	private int price;
	private String title;
	private String content;
	private LocalDateTime date;
	private String address;
	private double lat;
	private double lng;
}
