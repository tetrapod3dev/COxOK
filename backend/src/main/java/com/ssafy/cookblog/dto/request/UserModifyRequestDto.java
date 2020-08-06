package com.ssafy.cookblog.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserModifyRequestDto {
	private long userId;
	private String email;
	private String nickname;
	private String password;
	private String detail;
	private String profilePhoto;
	private List<Long> foodCategoryId;
}
