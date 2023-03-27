package com.cos.blog.test;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController // 스프링이 특정 어노테이션이 붙어있는 클래스 파일들을 new해서 IoC 스프링 컨테이너에 관리해줌
public class BlogControllerTests {
	//http://localhost:8088/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>hello spring boot</h1>";
	}

}
