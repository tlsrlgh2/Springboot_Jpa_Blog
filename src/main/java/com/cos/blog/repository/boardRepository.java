package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.board;
import com.cos.blog.model.user;

public interface boardRepository extends JpaRepository<board, Integer> {
	
	
}
