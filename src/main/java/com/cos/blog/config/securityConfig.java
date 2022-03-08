package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.blog.config.auth.principalDetailService;

//bean 등록이란 의미는 스프링 컨테이너에서 객체를 관리할수 있게 하는것. (ioc관리)
@Configuration
@EnableWebSecurity	// Security 필터가 등록이 된다
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을하면 권한 및 인증을 미리 체크하겠다는 뜻. 위 셋 어노테이션은 시큐리티 사용시 셋트이다.
public class securityConfig extends WebSecurityConfigurerAdapter{
		
	@Autowired
	private principalDetailService principalDetailService;
	
	
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

	// IOC가 됨
	@Bean
	public BCryptPasswordEncoder encodePWD() {
		
		return new BCryptPasswordEncoder(); 
	}
	
	// 시큐리티가 대신 로그인을 해주는데 password를 가로채기 해주는데
	// 해당 password가 어떤걸로 해쉬가 되어서 회원가입 되었는지 알아야
	// 같은 해쉬로 암호화 해서 DB에 있는 해쉬랑 비교를 할수있다.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println("principalDetailService의 정보호출............"+principalDetailService);
		auth.userDetailsService(principalDetailService).passwordEncoder(encodePWD());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("configure 함수 호출.................");
		http
			.csrf().disable() // csrf토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()
				.antMatchers("/","/auth/**","/js/**","/css/**","/image/**","/dummy/**") // 이페이지는 인증없어도 사용이 가능
				.permitAll()
				.anyRequest()	// 인증체크
				.authenticated()// 인증체크
			.and()
				.formLogin()
				.loginPage("/auth/loginform")	// 인증 없을시 이 코드
				.loginProcessingUrl("/auth/loginProc")// 시큐리티가 해당 주소로 요청오는 login을 가로채서 대신 로그인을 해준다 
				.defaultSuccessUrl("/"); // 로그인이 정상적으로 완료될경우 home으로 이동
				
	}
	
}
