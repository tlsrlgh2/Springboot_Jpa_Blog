package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.http.SecurityHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.principalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.user;
import com.cos.blog.service.userService;

@RestController //데이터만 리턴해줄경우 @RestController를 사용함
//@RequestMapping("blog")
public class userApiController {
	
	@Autowired
	private userService userservice;
	
	
// 스프링 시큐리티 사용전
//	@Autowired
//	private HttpSession session;
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody user user) {
		
		System.out.println("userApiController save 호출확인");
		// 실제로 DB에 insert를 하고 아래에서 return이 되면 정상작동
		userservice.usersave(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);	// java Object를 json으로 변환해서 리턴(jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody user user) {
		userservice.usermodify(user);
		// 이부분에서 트랜잭션이 종료되기때문에 DB에 값은 변경되지만
		// 세션값은 변경되지 않은 상태기 때문에
		
		
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
	}
	
	//스프링 시큐리티를 사용할경우 이런 방식으로 로그인을 하지않음.
	// 스프링 시큐리티 사용전
//	@PostMapping("/api/user/login")
//	public ResponseDto<Integer> login(@RequestBody user user/*, HttpSession seesion 이 방식도 가능하다 */) {
//		System.out.println("userApiController login 호출확인");
//		user principal = userservice.userlogin(user);	// principal(접근주체) 라는 용어
//		
//		// 세션 생성코드
//		if(principal != null) {
//			session.setAttribute("principal", principal);
//		}
//		
//		return new ResponseDto<Integer>(HttpStatus.OK.value(),1);
//	}

}
