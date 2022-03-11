package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.cos.blog.dto.replySaveRequsetDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {

	@Id // 프라이머리키
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false,length = 200)
	private String content;
	
	@ManyToOne // 여러개의 답변은 하나의 게시글에 존재할수있음. OneToOne일경우 하나의 게시글엔 하나의 답변만 입력가능
	@JoinColumn(name = "boardid")
	private board board;
	
	@ManyToOne
	@JoinColumn(name = "userid")
	private user user;
	
	@CreationTimestamp
	private Timestamp createdate;
	
	public void update(user user,board board, String content) {
		setUser(user);
		setBoard(board);
		setContent(content);
	}

	@Override
	public String toString() {
		return "Reply [id=" + id + ", content=" + content + ", board=" + board + ", user=" + user + ", createdate="
				+ createdate + "]";
	}
	
	
}
