package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class boardController {

	
	//application.yml에 설정한 context-path: /blog를 참조하여 http://localhost/8088/blog로 붙게된다
		@GetMapping({"/",""})
		public String index() {
			
			// application.yml 파일에 설정한 위치로 파일을 찾아감
			// test
			// test2
			return "index";
		}
		
}
