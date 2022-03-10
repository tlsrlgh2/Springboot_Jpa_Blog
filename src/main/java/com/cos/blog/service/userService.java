package com.cos.blog.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	@Transactional(readOnly = true)
	public user finduser(String username) {
															//orElseGet()은 만약 회원을 찾았는데 없다면 빈 객체를 호출해라 라는 뜻
		user user = userRepository.findByUsername(username).orElseGet(()->{
			return new user();
		});
		
		return user;
	}
	
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

	@Transactional
	public void usermodify(user user) {
		System.out.println("유저 modify 실행");
		// 수정시에는 영속성 컨텍스트 user 오브젝트를 영속화를 시키고, 영속화된 user 오브젝트를 수정.
		// select를 해서 user오브젝트를 DB로부터 가져오는 이유는 영속화를 하기 위해서
		// 영속화를 하게되면 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려줌.
		user persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		
		// validate 체크 => oauth에 값이 없으면 수정 가능
		if(persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String rawPassword = user.getPassword();
			String encPassword = encoder.encode(rawPassword);
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}
		
		// 회원수정 함수 종료시 = 서비스 종료 = 트랜잭션 종료 = commit이 자동으로 됩니다
		// 영속화된 persistance 객체의 변화가 감지되면 더치체킹이 되어 update문을 날려줌
		
	}
	
}
