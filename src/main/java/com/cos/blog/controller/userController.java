package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cos.blog.model.user;

// 인증이 안된 사용자들이 출입할수 있는 경로를 /auth로 지정
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js, /css, /image는 허용

@Controller
//@RequestMapping("blog")
public class userController {

	@GetMapping("/auth/joinform")
	public String joinform() {
		
		return "user/joinform";
	}
	
	@GetMapping("/auth/loginform")
	public String loginform() {
		
		return "user/loginform";
	}
	
	@GetMapping("/user/updateform")
	public String updateform() {
		
		return "user/updateform";
	}
	
}
