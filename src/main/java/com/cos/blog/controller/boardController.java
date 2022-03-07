package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.config.auth.principalDetail;
import com.cos.blog.model.user;
import com.cos.blog.repository.userRepository;
import com.cos.blog.service.boardService;

@Controller
public class boardController {

	@Autowired
	private boardService boardService;
	
	
	@GetMapping("/board/{id}")
	public String findByid(@PathVariable("id") int id, Model model) throws Exception {
		model.addAttribute("board", boardService.writedetailboard(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateform")
	public String updateform(@PathVariable int id, Model model) throws Exception {
		System.out.println("테스트코드");
		model.addAttribute("board",boardService.writedetailboard(id));
		return "board/updateform";
	}
	
	
	@GetMapping({ "/", "" })	// db에 있는 데이터를 가져갈때는 model 이 필요하다
	public String index(Model model,@PageableDefault(size=3,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
//		Page<user> paginguser = userRepository.
		
		model.addAttribute("boards", boardService.writelist(pageable));
		return "index";	// viewResolver 작동함
	}
	
	@GetMapping("/board/saveform")
	public String saveform() {
		
		return "board/saveform";
	}

}
