package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//jpa란 orm인데 orm이란 -> java(다른언어) Object -> 테이블로 매핑해주는 기술


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
//테이블화 시키기위한 어노테이션
//user 클래스가 읽히면서 Mysql에 테이블이 생성된다.
@Entity
// @DynamicInsert	// null값이 있는경우 그 필드를 제외하고 insert해준다
public class user {
	
	//primary key 라는걸 나타내주기 위한 어노테이션
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY사용시 프로젝트에서 연결된  DB의 넘버링 전략을 따라간다는 뜻.											// 오라클 사용시 시퀀스 를 사용, mysql사용시 auto_increment를 따라감
	private int id; // 오라클에서는 시퀀스 로 부르고, mysql에서는 auto_increment로 부름
	
	@Column(nullable=false,length = 100, unique = true) // 널값이 될수 없다는 뜻
	private String username; // 아이디
	
	@Column(nullable=false,length = 100) // 123456 => 해쉬(암호화) 하기위해 넉넉하게 잡음.
	private String password;
	
	@Column(nullable=false,length = 50)
	private String email;
	
//	@ColumnDefault("'user'") // 컬럼디폴트는 홀따움표를  '꼭' 사용해야함 
	// db는 RoleType이라는게 어벗어서 
	@Enumerated(EnumType.STRING)
	private RoleType role; // String 보다 Enum을 쓰는게 좋다   //Enum을 사용시 admin, user. manager 이런식으로 3가지중 하나의 권한을 부여할수있다
	
	private String oauth;	// 카카오 로그인은 kaka, 구글 로그인은 google
	
	
	@CreationTimestamp // 시간이 자동으로 입려됨  *오라클일경우 sysdate()를 자동으로 사용 , mysql일경우 now()를 자동으로 사용
	private Timestamp createDate;
	
}
