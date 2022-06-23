package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PostVo;
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

	public void write(PostVo vo) {
		sqlSession.insert("blog.write", vo);
	}

	public List<PostVo> getPost(Long category_no) {
		return sqlSession.selectList("blog.getPost",category_no);
	}
	
	public PostVo findByNo(Long category_no, Long postNo) {
		PostVo vo = new PostVo();
		vo.setCategory_no(category_no);
		vo.setNo(postNo);
		return  sqlSession.selectOne("blog.findByNo", vo);
	}

}
