package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.user;

// jsp로 따지면 DAO를 뜻한다
//@Repository // 생략가능하다
public interface userRepository extends JpaRepository<user, Integer> {

}
