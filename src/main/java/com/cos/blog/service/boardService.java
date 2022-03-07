package com.cos.blog.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.board;
import com.cos.blog.model.user;
import com.cos.blog.repository.boardRepository;
import com.cos.blog.repository.userRepository;

@Service 
public class boardService {
	
	@Autowired
	private boardRepository boardRepository;
	
	@Transactional	
	public void writesave(board board,user user) {	// title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
		
	}
	
	@Transactional(readOnly = true)
	public Page<board> writelist(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
	
	// select 시 readonly= true를 붙여준다
	@Transactional(readOnly = true)
	public board writedetailboard(int id) throws Exception {
		
		Optional<board> boardchk = boardRepository.findById(id);
		if(boardchk.isPresent()) {
			board board = boardchk.get();
			return board;
		} else {
			throw new Exception("글상세보기 실패 : 아이디를 찾을수없습니다");
		}
		
		
	}
	
	@Transactional
	public void writedelete(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void modify(int id, board requestboard) throws Exception {
		Optional<board> boardchk = boardRepository.findById(id);
		
		if(boardchk.isPresent()) {
			board board = boardchk.get();
			
			board.setTitle(requestboard.getTitle());
			board.setContent(requestboard.getContent());
			// 해당 함수 종료시에 ( service가 종료될때) 트랜잭션이 종료됩니다. 이때 더티 체킹이 일어남 - 자동 업데이트가 됨  flush
		}else {
			throw new Exception("글 찾기 실패 : 아이디를 찾을수없습니다");
		}
		
	}
}
