package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.Reply;
import com.cos.blog.model.board;
import com.cos.blog.repository.boardRepository;
import com.cos.blog.repository.replyRepository;

@RestController
public class replycontrollertest {

	@Autowired
	private boardRepository boardRepository;
	
	@Autowired
	private replyRepository replyRepository;
	
	@GetMapping("/test/board/{id}")
	public board getBoard(@PathVariable int id) {
		return boardRepository.findById(id).get();	// jackson 라이브러리 (오브젝트를 json 으로 리턴) => 모델의 getter을 호출함.
	}
	@GetMapping("/test/reply")
	public List<Reply> getreply() {
		return replyRepository.findAll();	// jackson 라이브러리 (오브젝트를 json 으로 리턴) => 모델의 getter을 호출함.
	}
}
