package com.cos.blog.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.blog.model.user;
import com.cos.blog.repository.userRepository;

@Service // bean 등록
public class principalDetailService implements UserDetailsService {

	@Autowired
	private userRepository userrepository;
	
	// 스프링 시큐리티가 로그인 요청을 가로채서 username, password 변수 2개를 가로채는데
	// password 부분 처리는 알아서 함.
	// 해당 username이 DB에 있는지 확인해서 return 해주면됨.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		user principal = userrepository.findByUsername(username)
						.orElseThrow(()->{
							return new UsernameNotFoundException("해당 사용자를 찾을수 없습니다" + username);
						});
		return new principalDetail(principal); // 시큐리티의 세션에 유저 정보가 저장됨
	}

	
}
