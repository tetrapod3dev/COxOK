package com.ssafy.cookblog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MeetJoinDto {
	private long meetJoinId;
	private long meedId;
	private long userId;
	private String nickname;
}
