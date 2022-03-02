package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

//@Getter
//@Setter
@Data // geter,seter을 한번에 생성.
//@AllArgsConstructor // 모든 필드를 사용하는 생성자 를 생성
@NoArgsConstructor // 빈생성자
//@RequiredArgsConstructor // final을 입력한 애들에 한하여 생성자를 생성함.
public class Member {
	private int id;			// final 을 설정하는건 불변성을 위해서 *db의 데이터를 한번만 불러오기때문에 가능.
	private String username;	//만약 변수를 변경할 일이 생길경우 final을 사용하지않음
	private String password;
	private String email;
	
	@Builder // 빌더 패턴 만들때 사용  *생성자의 순서를 지키지 않아도 됨.
	public Member(int id, String username, String password, String email) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
}
