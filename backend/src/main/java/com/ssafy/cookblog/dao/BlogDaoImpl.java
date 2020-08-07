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
	
	public List<BlogDto> selectMyBlog(long userId) {
		return session.selectList("blog.selectMyBlog",userId);
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
}
