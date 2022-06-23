package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.security.Auth;
import com.douzone.jblog.security.AuthUser;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.service.CategoryService;
import com.douzone.jblog.service.FileUploadService;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	@Autowired
	ServletContext servletContext;
	
	@Autowired
	private BlogService blogService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private FileUploadService fileUploadService;
	
	
	@RequestMapping({"", "/{pathNo1}", "/{pathNo1}/{pathNo2}"})
	public String index(
		@PathVariable("id") String id,
		@PathVariable("pathNo1") Optional<Long> pathNo1,
		@PathVariable("pathNo2") Optional<Long> pathNo2,
		 Model model) {
		
		Long category_no = 0L;
		Long postNo = 0L;
		
		if(pathNo2.isPresent()) {
			category_no = pathNo1.get();
			postNo = pathNo2.get();
		} else if(pathNo1.isPresent()) {
			category_no = pathNo1.get();
		}
		
		BlogVo blogVo = blogService.getBlogInfo(id);
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo>list = categoryService.getCategoryList(id);
		model.addAttribute("list", list);
		
		List<PostVo>postVo = blogService.getPost(category_no, id);
		model.addAttribute("postVo", postVo);
		
		if(postNo == 0L) {
			model.addAttribute("postvo", postVo.get(0));	
		}else {
		PostVo postvo = blogService.findByNo( category_no, postNo, id);
		model.addAttribute("postvo", postvo);
		}
	
		return "blog/main";
	}
	
	@Auth
	@RequestMapping("/admin/basic")
	public String adminBasic(@PathVariable("id") String id, 
			@AuthUser UserVo authUser, 
			Model model) {
		
		BlogVo vo = blogService.getBlogInfo(id);
		model.addAttribute("blogVo", vo);
		
		if(!authUser.getId().equals(id)) {
			return "redirect:/";
		}
		
		return "/blog/admin/basic";
	}
	
	@Auth
	@RequestMapping("/admin/category")
	public String category(@PathVariable("id") String id, Model model) {
		List<CategoryVo>list = categoryService.getCategoryList(id);
		model.addAttribute("list", list);
		return "blog/admin/category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String write(@PathVariable("id") String id, Model model) {
		List<CategoryVo>list = categoryService.getCategoryList(id);
		model.addAttribute("list", list);
		
		return "blog/admin/write";
	}
	
	@Auth
	@RequestMapping(value = "/admin/category", method = RequestMethod.POST)
	public String insertCategory(
			@PathVariable("id") String id, 
			@AuthUser UserVo authUser, 
			CategoryVo categoryVo) {
		categoryVo.setBlog_id(id);
		categoryService.insertCategory(categoryVo);
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping("/admin/category/delete/{no}")
	public String deleteCategory(
			@PathVariable("id") String id, 
			@PathVariable("no") Long no) {
		
		categoryService.deleteCategory(no);
		return "redirect:/" + id + "/admin/category";
	}
	
	@Auth
	@RequestMapping(value="/admin/update", method=RequestMethod.POST)
	public String update(
			@PathVariable("id") String id,
			BlogVo vo, 
			@RequestParam("file") MultipartFile file) {
			String logo = fileUploadService.restoreImage(file);
		if(logo != null) {
			vo.setLogo(logo);
		}
		blogService.updateBlog(vo);
		servletContext.setAttribute("blogVo", vo);
	
		return "redirect:/" + id + "/admin/basic";
	}
	
	@RequestMapping(value="/admin/write", method=RequestMethod.POST)
	public String write(@PathVariable("id") String id,
			@RequestParam(value="category") String category, PostVo postVo) {
		// 포스트 하기
		postVo.setCategory_no(Long.parseLong(category));
		blogService.write(postVo);
		
		return "redirect:/"+id+"/"+postVo.getCategory_no();
	}
}