package com.cos.blog.controller.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.user;

@RestController //데이터만 리턴해줄경우 @RestController를 사용함
//@RequestMapping("blog")
public class userApiController {
	
	@PostMapping("/api/user")
	public int save(@RequestBody user user) {
		
		System.out.println("userApiController save 호출확인");
		return 1;
	}	

}
