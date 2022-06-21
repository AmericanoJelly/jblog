package com.douzone.jblog.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class BlogRepository {
	@Autowired
	private SqlSession sqlSession;
	
	public boolean insert(UserVo vo) {
		return sqlSession.insert("blog.insert", vo) == 1;
	}
	
	public BlogVo getBlogInfo(String id) {
		return sqlSession.selectOne("blog.getBlogInfo",id);
	}

	public void updateBlog(BlogVo vo) {
		sqlSession.update("blog.updateBlog", vo);
	}

}
