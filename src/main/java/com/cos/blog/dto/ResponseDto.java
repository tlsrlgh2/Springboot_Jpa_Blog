package com.cos.blog.dto;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class ResponseDto<T> {
	final int status;
	final T data;
}
