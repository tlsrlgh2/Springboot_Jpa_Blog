package com.cos.blog.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.cos.blog.dto.replySaveRequsetDto;
import com.cos.blog.model.Reply;

public interface replyRepository extends JpaRepository<Reply, Integer> {

	
	// 인터페이스 내부 에서는 public이 생략이 가능하다
	// 이 방식을 사용할경우 영속화 해주는 작업을 생략이 가능하다
	
	@Modifying	// @query 어노테이션 사용시 @modifying 을 같이 사용해줘야한다
	@Query(value="insert into reply(userid,boardid,content,createDate) values(?1,?2,?3,now())",nativeQuery = true)
	int mSave(int userid, int boardid, String content); // jdbc를 이용해 query문 발생시 리턴값으로 업데이트된 행의 갯수를 리턴해준다.
	

}
