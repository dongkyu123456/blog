package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // file을 리턴
public class TempControllerTest {

    //http://localhost8088/blog/temp/home
    @GetMapping("/temp/home")
    public String tempHome() {
        System.out.println("/temp/home");
        // controller의파일 리턴 기본 경로 : src/main/resources/static경로 이하의 파일을 리턴하라
        return "/home.html";
    }

    @GetMapping("/temp/img")
    public String tempImg(){
        return "/a.png";
    }

    @GetMapping("/temp/jsp")
    public String tempJsp() {
        //prefix: /WEB-INF/views/
        //suffix:.jsp
        //리턴 값 : /WEB-INF/views/"리턴값".jsp
        return "test";
    }
}
