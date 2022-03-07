package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.user;
import com.cos.blog.repository.userRepository;

// @Service을 사용해야 스프링이 컴포넌트 스캔을 통해 bean에 등록을 해줌(ioc)
// 서비스를 사용하는 이유는 트랜젝션을 관리하기 위함
@Service 
public class userService {
	
	@Autowired
	private userRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	// @Transactional 은 CRUD를 처리할떄 정합성을 위해 꼭 붙여준다.
	@Transactional	// 여러개의 서비스를 하나의 트랜젝션으로 모으기 위한 어노테이션
	public int usersave(user user) {
		
		try {
			String rawPassword = user.getPassword(); // 원문
			String encPassword = encoder.encode(rawPassword); // hash 값으로 변경
			user.setPassword(encPassword);	// db에 해쉬데이터 넣는 문
			user.setRole(RoleType.USER);
			userRepository.save(user);
			
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("userservice : 회원가입() :" + e.getMessage());
		
			return -1;
		}
	}
	// select인경우에도 @Transactional을 붙여준다
	// readOnly = true 를 붙일경우 select할때 트랜잭션이 시작되고 해당 서비스 종료시 트랜잭션 종료(정합성 유지)
	// 스프링 시큐리티 사용 전 코드
//	@Transactional(readOnly = true)
//	public user userlogin(user user) {
//		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//	}
	
}
