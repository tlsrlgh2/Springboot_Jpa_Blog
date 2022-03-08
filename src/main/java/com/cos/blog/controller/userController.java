package com.cos.blog.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.principalDetail;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.user;
import com.cos.blog.service.userService;

// 인증이 안된 사용자들이 출입할수 있는 경로를 /auth로 지정
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js, /css, /image는 허용

@Controller
//@RequestMapping("blog")
public class userController {

	private userService userService;
	
	
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
	
	@GetMapping("/auth/kakao/callback")	
	public @ResponseBody String kakaoCallback(String code) {	//@ResponseBody data를 리턴해주는 컨트롤러 함수
		// 카카오 인증 체크,토큰 요청 하는곳
		// a태크 요청방식은 get이다
		// post 방식으로 key=value 데이터를 요청(카카오쪽으로)
		// 요청하는 라이브러리는  , RestTemplate , Retrofit2 (안드로이드에서 자주사용함), okHttp가 있다
		
//		System.out.println(code); 코드값 확인
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		String grant_type = "authorization_code";
		String client_id = "f0f9cc647bd88d476b7ac45e72e4c6f7";
		String redirect_uri = "http://localhost:8088/auth/kakao/callback";
		
		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", grant_type);
		params.add("client_id", client_id);
		params.add("redirect_uri", redirect_uri);
		params.add("code", code);
		
		// HttpHeaders 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = 
				new HttpEntity<>(params,headers);
		
		// Http 요청하기 - post방식으로 - 그리고 respose 변수의 응답 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
//		System.out.println(response); 토큰요청에 대한 응답값 확인
		
		return "카카오 토큰 요청 완료 : 토큰요청에 대한 응답" + response;
	}
	
	
}
