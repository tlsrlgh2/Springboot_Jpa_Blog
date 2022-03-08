package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.user;

import lombok.Data;
import lombok.Getter;


// 스프링 시큐리티가 로그인 요청을 가로채서 로그인을 진행하고 완료가 되면 userDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다
@Data
public class principalDetail implements UserDetails{
	
	private static final long serialVersionUID = 3318549922418797863L;
	
	private user user;	// 컴포지션( 객체를 품고있다) 

	public principalDetail(user user) {
		this.user = user;
	}

	@Override
	public String getPassword() {
		System.out.println("user get password 함수.........."+user.getPassword());
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return user.getUsername();
	}
	//계정이 만료되지 않았는지를 리턴한다(true:만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	//계정이 잠겨있는지 않았는지를 리턴한다(true:잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	//비밀번호가 만료되었는지 않았는지를 리턴한다(true:만료 안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정이 활성화되었는지 않았는지를 리턴한다(true:활성화)
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	//계정이 갖고있는 권한목록을 리턴한다(권한이 여러개 있을수 있어서 루프를 돌려야하는데 여기서는 하나만 사용함)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> collectors = new ArrayList<>();
//		collectors.add(new GrantedAuthority() {
//
//			@Override
//			public String getAuthority() {
//					// spring 에서 ROLE을 리턴받을땐 ROLE_을 꼭붙여야한다
//				return "ROLE_"+user.getRole();	// ROLE_USER
//			}
//			
//		});
		
//		윗 코드와 동일한 기능을한다 (람다식 표현 코드)
		collectors.add(()->{return  "ROLE_"+user.getRole();});
		
		
		return collectors;
	}
}
