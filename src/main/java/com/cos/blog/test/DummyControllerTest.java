package com.cos.blog.test;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.user;
import com.cos.blog.repository.userRepository;

// html 파일이 아니라 data를 리턴해주는 controller = @RestController
@RestController
public class DummyControllerTest {
	
	@Autowired	//의존성 주입
	private userRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		
		try {
			userRepository.deleteById(id);
		} catch (Exception e) {
			return "삭제에 실패하였습니다 해당아이디 " + id+"는 없습니다";
		}
		
		
		return "삭제되었습니다 id : " + id;
	}
	
	// save 함수는 id를 전달하지 않으면 insert를 해주고 
	// save 함수는 id를 전달해주면 해당 id에 대한 데이터가 있으면 update를 해주고 
	// save 함수는 id를 전달해주면 해당 id에 대한 데이트가 없으면 insert를 해줌
	
	// email, passowrd 받아야함
	// @Transactional 어노테이션은 save를 하지 않아도 update가 가능하다
	@Transactional
	@PutMapping("/dummy/user/{id}")				// @RequestBody = json데이터를 변환해서 받을경우 사용하는 어노테이션
	public user updateuser(@PathVariable int id, @RequestBody user requestUser) { //json 데이터를 요청 => java object로 변환(massageconverter의 jackson라이브러리가)해서 받음
		System.out.println("id:"+id);
		System.out.println("password"+requestUser.getPassword());
		System.out.println("email"+requestUser.getEmail());
		
		user user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패하였습니다");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
//		userRepository.save(user);	 save를 사용하는 방법
		
		// 더티 체킹
		return user;
	}
	
	
	@GetMapping("/dummy/users")
	public List<user> list() {
		
		return userRepository.findAll();
	}
	
	// 한 페이지당 2건의 데이터 리턴받기
	@GetMapping("/dummy/user")
	public List<user> pagelist(@PageableDefault(size=2,sort = "id",direction = Sort.Direction.DESC) Pageable pageable) {
		Page<user> paginguser = userRepository.findAll(pageable);
		
		if(paginguser.isFirst()) {
			
		}
		List<user> users =paginguser.getContent();
		
		
		return users;
	}
	
	
	// {id} 주소로 파라미터를 전달받을수 있음
	// http://localhost:8088/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public user detail(@PathVariable int id) {
		// user/4을 찾으면 내가 데이터베이스에서 못찾아오게되면 user 가 null 이 되므로
		// return 할떄 null 값이 들어오면서 프로그램에 문제가 생길수있다.
		// 그래서 optional로 user 객체를 감싸서 가져오면 null인이 아닌지 판단하여 return 을 해줌
		user user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다 id"+id);
			}
		});
		
//		람다식 으로 사용할경우
//		user user1 = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당유저가 없습니다");
//		});
		
		// 요청은 웹브라우저
		// user 객체 = java object
		// json으로 변환이 필요함
		// spring boot 는 messageconverter 라는 애가 응답시 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 messageconverter가 jackson 라이브러리를 호출해서
		// user오브젝트를 json으로 변환해서 브라우저에 던저준다
		return user;
	}
	
	// http://localhost:8088/blog/dummy/join(요청)
	// http의 body에 username,password,email 데이터 가지고 (오청)
	@PostMapping("/dummy/join")
	public String join(user user) { // key = value 형태로 값을 받음.
		System.out.println("id : " + user.getId());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		System.out.println("role : " + user.getRole());
		System.out.println("createDate : " + user.getCreateDate());
		
		
		user.setRole(RoleType.USER);
		// save 함수는 insert 할경우에만 사용한다
		userRepository.save(user);
		return "회원가입이 완료 되었습니다";
	}
}
