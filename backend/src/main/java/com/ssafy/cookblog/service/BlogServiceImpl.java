package com.ssafy.cookblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dao.BlogDao;
import com.ssafy.cookblog.dto.BlogDto;

@Service
public class BlogServiceImpl implements BlogService{
	
	@Autowired
	private BlogDao blogDao;
	
	public BlogDto get(long blogId) {
		return blogDao.select(blogId);
	}
	public List<BlogDto> getAllBlog(int startIndex) {
		return blogDao.selectAll(startIndex);
	}
	public int register(BlogDto blogDto) {
		return blogDao.insert(blogDto);
	}
	public int modify(BlogDto blogDto) {
		return blogDao.update(blogDto);
	}
	
	public int remove(long blogId) {
		return blogDao.delete(blogId);
	}
	public long getTotalNum() {
		return blogDao.selectTotal();
	}
}
