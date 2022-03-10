package com.cos.blog.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
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
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.user;
import com.cos.blog.service.userService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


// 인증이 안된 사용자들이 출입할수 있는 경로를 /auth로 지정
// 그냥 주소가 / 이면 index.jsp 허용
// static 이하에 있는 /js, /css, /image는 허용

@Controller
//@RequestMapping("blog")
public class userController {

	@Value("${cos.key}")
	private String coskey;
	
	@Autowired
	private userService userService;

	@Autowired
	private AuthenticationManager authenticationManager;
	
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
	public String kakaoCallback(String code) {	//@ResponseBody data를 리턴해주는 컨트롤러 함수
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
//		System.out.println(response.getBody()); 토큰요청에 대한 응답값 확인 .getBody
		
		// json데이터를 오브젝트로 담는 방법
		// 오브젝트로 담는 라이브러리 종류
		// Gsom,json simple,objectMapper 이 있다
		ObjectMapper objectmapper = new ObjectMapper();
		OAuthToken oauthtoken = null;
		try {
			oauthtoken = objectmapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthtoken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " +oauthtoken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		
		
		// HttpHeaders 와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		// Http 요청하기 - post방식으로 - 그리고 respose 변수의 응답 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		
		// response2.getBody() 를 한 데이터를 오브젝트로 감싸는 작업
		ObjectMapper objectmapper2 = new ObjectMapper();
		KakaoProfile kakaoprofile = null;
		try {
			kakaoprofile = objectmapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//user 오브젝트에는  username, password, email 이 필요함.
		System.out.println("카카오 아이디(번호) : "+ kakaoprofile.getId());
		System.out.println("카카오 이메일 : "+ kakaoprofile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : "+ kakaoprofile.getKakao_account().getEmail()+"_"+kakaoprofile.getId());
		System.out.println("블로그서버 이메일 : "+kakaoprofile.getKakao_account().getEmail());
		
		// uuid란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘이다.
		System.out.println("블로그 서버 패스워드 : " +coskey);
		
		user kakaouser = user.builder()
				.username(kakaoprofile.getKakao_account().getEmail()+"_"+kakaoprofile.getId())
				.password(coskey)
				.email(kakaoprofile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		// 가입자 혹은 비가입자 체크 해서 처리 해야함. (분기해야함)
		user originuser = userService.finduser(kakaouser.getUsername());
		
		if(originuser.getUsername() == null) {
			System.out.println("기존회원이 아닙니다..........");
			System.out.println("회원 가입을 진행합니다");
			userService.usersave(kakaouser);
		}
		
		System.out.println("자동로그인 진행");
		System.out.println("yml 코스키 호출"+coskey);
		// 자동로그인을 진행 (로그인 처리)
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(kakaouser.getUsername(), coskey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		
		return "redirect:/";
	}
	
	
}
