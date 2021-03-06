package com.douzone.jblog.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.jblog.repository.CategoryRepository;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Service
public class CategoryService {
	@Autowired
	private CategoryRepository categoryRepository;

	public void join(UserVo vo) {
		categoryRepository.insert(vo);
	}

	public List<CategoryVo> getCategoryList(String id) {
		return categoryRepository.findAll(id);
	}

	public void insertCategory(CategoryVo categoryVo) {
		categoryRepository.insertCategory(categoryVo);
	}

	public void deleteCategory(Long no) {
		categoryRepository.deleteCategory( no);
	}



}
