package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment를 사용
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량 데이터를 사용할때 사용한다
	private String content; // 섬머노트 라이브러리 를 사용 할것임. 섬머노트는 <html>태그가 섞여서 디자인됨
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER)	// Many = board, user = one 연관 관계 생성
	@JoinColumn(name="userid") // userid로 필드명이 생성되고 user을 참조키로 자동으로 연결된다 타입은 user타입을 가져다 쓴다.
	private user user; // db는 오브젝트를 저장할수 없다. fk. 자바는 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board",fetch = FetchType.EAGER) // mappedby 는 연관관계의 주인ㅇ 아니다 (fk가 아니다) db에 컬럼이 생성안됨.
	private List<Reply> reply;
	
	@CreationTimestamp
	private Timestamp createDate; 
}
