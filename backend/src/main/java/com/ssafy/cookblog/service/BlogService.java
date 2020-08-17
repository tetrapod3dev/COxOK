package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.BlogDto;

public interface BlogService {
	public BlogDto get(long blogId);
	public List<BlogDto> getMyBlog(BlogDto blogDto);
	public long getMyTotal(long userId);
	public List<BlogDto> getAllBlog(int startIndex);
	public int register(BlogDto blogDto);
	public int modify(BlogDto blogDto);
	public int remove(long blogId);
	public long getTotalNum();
	
	public List<BlogDto> getByTitle(String title);
	public List<BlogDto> getByTag(String tag);
}
