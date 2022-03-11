package com.cos.blog.service;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.replySaveRequsetDto;
import com.cos.blog.model.Reply;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.board;
import com.cos.blog.model.user;
import com.cos.blog.repository.boardRepository;
import com.cos.blog.repository.replyRepository;
import com.cos.blog.repository.userRepository;

import lombok.RequiredArgsConstructor;

@Service 
@RequiredArgsConstructor
public class boardService {
	
	private final boardRepository boardRepository;
	private final replyRepository replyrepository;
	
	
//	Autowired의 원리. 
	
//	public boardService(boardRepository bRepo, replyRepository rRepo) {
//		this.boardRepository = bRepo;
//		this.replyrepository = rRepo;
//	}
	
//	
//	@Autowired
//	private  boardRepository boardRepository;
//	
//	@Autowired
//	private replyRepository replyrepository;
//	
//	@Autowired
//	private userRepository userrepository;
	
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

	@Transactional
	public void replywrite(replySaveRequsetDto replysaverequestDto) throws Exception{
//		board board = null;
//		user user = null;
		
//		// 영속화 방법
//		Optional<board> boardchk = boardRepository.findById(replysaverequestDto.getBoardid());
//		if(boardchk.isPresent()) {
//			board = boardchk.get();
//		}else {
//			throw new Exception("댓글 쓰기 실패 : 게시글 id를 찾을수 없습니다");
//		}
//		// 영속화 방법
//		Optional<user> userchk = userrepository.findById(replysaverequestDto.getUserid());
//		if(userchk.isPresent()) {
//			user = userchk.get();
//		}else {
//			throw new Exception("댓글 쓰기 실패 : 유저 id를 찾을수 없습니다");
//		}
		
////		Reply reply = Reply.builder()
////					.user(user)
////					.board(board)
////					.content(replysaverequestDto.getContent())
////					.build();
		
// 		윗 방법도 있고  아래처럼 DTO 생성해서 만드는 망법도 있다
//		Reply reply = new Reply();
//		reply.update(user, board, replysaverequestDto.getContent());
		
		
		// 영속화를 생략하는 방법이다
		int result = replyrepository.mSave(replysaverequestDto.getUserid(),replysaverequestDto.getBoardid(),replysaverequestDto.getContent());
		System.out.println(result); // 오브젝트를 출력하게되면 자동으로 toString() 이 호출됨.
	}

	public void board_reply_delete(int replyid) {
		replyrepository.deleteById(replyid);
	}
}
