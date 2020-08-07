package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.BlogDto;

public interface BlogService {
	public BlogDto get(long blogId);
	public List<BlogDto> getMyBlog(long userId);
	public List<BlogDto> getAllBlog(int startIndex);
	public int register(BlogDto blogDto);
	public int modify(BlogDto blogDto);
	public int remove(long blogId);
	public long getTotalNum();
}
