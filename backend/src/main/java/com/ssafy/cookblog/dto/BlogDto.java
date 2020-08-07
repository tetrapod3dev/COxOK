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
	private long blogId;
	private long userId;
	private long recipeId;
	private String title;
	private String content;
	private LocalDateTime regTime;
	private String tag1;
	private String tag2;
	private String tag3;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BlogDto [blogId=");
		builder.append(blogId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", recipeId=");
		builder.append(recipeId);
		builder.append(", title=");
		builder.append(title);
		builder.append(", content=");
		builder.append(content);
		builder.append(", regTime=");
		builder.append(regTime);
		builder.append(", tag1=");
		builder.append(tag1);
		builder.append(", tag2=");
		builder.append(tag2);
		builder.append(", tag3=");
		builder.append(tag3);
		builder.append("]");
		return builder.toString();
	}
	
	
}
