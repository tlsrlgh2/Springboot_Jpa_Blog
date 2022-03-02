package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.jmx.snmp.Timestamp;

// 사용자가 요청 -> 응답(HTML 파일)
//@controller

// 컨트롤러란 사용자가 요청 -> 응답(data)

@RestController
public class HttpContorollerTest {

	
	private static final String tag = "HttpContorollerTest : ";
	
	@GetMapping("/http/lombok")
	public String lomboktest() {
		
		Member m = Member.builder().username("ssar").password("1234").email("sar").build();
		System.out.println(tag+"getter : "+m.getUsername());
		m.setUsername("cos");
		System.out.println(tag+"setter : "+m.getUsername());
		
		return "lombok test 완료";
	}
	
	// 인터넷 브라우저 요청은 무조건 get 요청 밖에 사용할수 없다.
	//http://localhost:8088/http/get (select)
	@GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		
		return "get 요청" + id+username;
	}
	// memeber로 한번에 받을경우
	@GetMapping("/http/get1")
	public String getTest1(Member m) {
		
		return "get 요청" + m.getId()+m.getUsername()+m.getPassword()+m.getEmail();
	}
	
	//http://localhost:8088/http/post (insert)
	@PostMapping("/http/post")
	public String postTest(Member m) {
		
		return "post 요청"+ m.getId()+m.getUsername()+m.getPassword()+m.getEmail();
	}
	
	//http://localhost:8088/http/post (insert)
		@PostMapping("/http/post1") // row 데이터는 test/plain 보낸것. / application/json 데이터
		public String postTest1(@RequestBody Member m) {// messageConverter (스프링부트)
			
			return "post 요청"+ m.getId()+m.getUsername()+m.getPassword()+m.getEmail();
		}
	
	//http://localhost:8088/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		
		return "put 요청"+ m.getId()+m.getUsername()+m.getPassword()+m.getEmail();
	}
	
	//http://localhost:8088/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		
		return "delete 요청";
	}
}
