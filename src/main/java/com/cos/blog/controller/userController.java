package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cos.blog.model.user;

@Controller
//@RequestMapping("blog")
public class userController {

	@GetMapping("/user/joinform")
	public String joinform() {
		
		return "user/joinform";
	}
	
	@GetMapping("/user/loginform")
	public String loginform() {
		
		return "user/loginform";
	}
	
}
