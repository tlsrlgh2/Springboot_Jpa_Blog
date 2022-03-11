package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.user;

// jsp로 따지면 DAO를 뜻한다
//@Repository // 생략가능하다
public interface userRepository extends JpaRepository<user, Integer> {
	// jpa naming 전략 (쿼리)
	// 함수명을 findByUsernameAndPassword 으로 할경우 
	//	select * from user where username = ?1 and password = ?2; 라는 쿼리문이 동작함
	// 스프링 시큐리티 사용전 코드
	//	user findByUsernameAndPassword(String username,String password);
	
		
	//	윗 코드와 동일한 동작을 하는코드
	//	@Query(value="select * from user where username = ?1 and password = ?2",nativeQuery = true)
	//	user login(String username,String password);
	
	// select * from user where username = ?; 으로 실행된다
	Optional<user> findByUsername(String username);
	
}
