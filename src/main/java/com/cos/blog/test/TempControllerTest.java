package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 해당경로 이하의 있는 파일을 리턴해주는것.
@Controller
public class TempControllerTest {
	
	//http://localhost:8088/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("temphome");
		// spring 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /home.html 로 설정해줘야 리턴이 가능  *만약 리턴명앞에 /가 빠져있을경우 src/main/resources/statichome.html을 찾게된다.
		
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempimg() {
		
		return "/a.png";
	}
	

	// jsp파일을 사용을 하려면 pom.xml에서 jsp템플릿 엔진을 의존성 설정을 해주어야함.
	// static 경로에 jsp가 있다면 잘 읽지 못함 static 경로는 정적파일(브라우저가 인식할수있는 파일)
	// jsp는 동적인 파일 (컴파일이 필요한 파일)
 	@GetMapping("/temp/1jsp")
	public String tempjsp1() {
		
		return "/test1.jsp";
	}
 	
 	//만약 jsp를 사용할경우는 yml 파일에서 prefix와 suffix를 설정해줘야함.
 	@GetMapping("/temp/jsp")
	public String tempjsp() {
		
 		//prifix: /WEB-INF/views/
 		//suffix: .jsp
 		// 리턴값이 /test.jsp일경우
 		// 위처럼 yml에서 설정해줬을 경우 풀 경로는 /WEB-INF/views//test.jsp.jps를 찾게됨.
 		
		return "test";
	}
}
