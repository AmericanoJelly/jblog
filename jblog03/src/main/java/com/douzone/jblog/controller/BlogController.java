package com.douzone.jblog.controller;

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
		
		Long categoryNo = 0L;
		Long postNo = 0L;
		BlogVo vo = blogService.getBlogInfo(id);
		model.addAttribute("blogVo", vo);
		
		if(pathNo2.isPresent()) {
			categoryNo = pathNo1.get();
			postNo = pathNo2.get();
		} else if(pathNo1.isPresent()) {
			categoryNo = pathNo1.get();
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
	public String category(@PathVariable("id") String id) {
		return "blog//admin/category";
	}
	
	@Auth
	@RequestMapping("/admin/write")
	public String write(@PathVariable("id") String id) {
		return "blog//admin/write";
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
}