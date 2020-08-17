package com.ssafy.cookblog.dao;

import java.util.List;

import com.ssafy.cookblog.dto.BlogDto;

public interface BlogDao {
	
	public BlogDto select(long blogId);
	public List<BlogDto> selectMyBlog(BlogDto blogDto);
	public long selectMyTotal(long userId);
	public List<BlogDto> selectAll(int startIndex);
	public int insert(BlogDto blogDto);
	public int update(BlogDto blogDto);
	public int delete(long blogId);
	public long selectTotal();
}
