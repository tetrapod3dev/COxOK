package com.ssafy.cookblog.dto.request;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor	
public class MeetRegisterRequestDto {
	private long meetId;
	private long userId;
	private long recipeId;
	private String thumbnailSrc;
	private String type;
	private int joinLimit;
	private int price;
	private String title;
	private String content;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime date;
	private String address;
	private double lat;
	private double lng;
	
	private MultipartFile photo;
}
