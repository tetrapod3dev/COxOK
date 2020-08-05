package com.ssafy.cookblog.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.cookblog.dto.MeetJoinDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetViewResponseDto {
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
	private List<MeetJoinDto> meetJoinList;
}
