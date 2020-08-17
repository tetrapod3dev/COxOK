package com.ssafy.cookblog.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.cookblog.dto.BlogDto;

@Repository
public class BlogDaoImpl implements BlogDao{
	
	@Autowired
	SqlSession session;
	
	public BlogDto select(long blogId) {
		return session.selectOne("blog.select",blogId);
	}
	
	public List<BlogDto> selectMyBlog(BlogDto blogDto) {
		return session.selectList("blog.selectMyBlog",blogDto);
	}
	
	@Override
	public long selectMyTotal(long userId) {
		return session.selectOne("blog.selectMyTotal",userId);
	}
	
	public List<BlogDto> selectAll(int startIndex) {
		return session.selectList("blog.selectAll",startIndex);
	}

	public int insert(BlogDto blogDto) {
		return session.insert("blog.insert",blogDto);
	}
	
	public int delete(long blogId) {
		return session.delete("blog.delete",blogId);
	}
	
	public int update(BlogDto blogDto) {
		return session.update("blog.update",blogDto);
	}
	
	public long selectTotal() {
		return session.selectOne("blog.selectTotalNum");
	}
	
	@Override
	public List<BlogDto> selectByTitle(String title) {
		return session.selectList("blog.selectByTitle",title);
	}
	@Override
	public List<BlogDto> selectByTag(String tag) {
		return session.selectList("blog.selectByTag",tag);
	}
}
