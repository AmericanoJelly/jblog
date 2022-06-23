package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class BlogService {
	@Autowired
	private BlogRepository blogRepository;
	
	public void join(UserVo vo) {
		blogRepository.insert(vo);
	}
	
	
	public BlogVo getBlogInfo(String id) {
		return blogRepository.getBlogInfo(id);
	}


	public void updateBlog(BlogVo vo) {
		blogRepository.updateBlog(vo);
	}


	public void write(PostVo vo) {
		blogRepository.write(vo);
	}


	public List<PostVo> getPost(Long category_no, String id) {
		return blogRepository.getPost(category_no, id);
	}


	public PostVo findByNo(Long category_no, Long postNo, String id) {
		return blogRepository.findByNo(category_no, postNo, id);
	}


}
