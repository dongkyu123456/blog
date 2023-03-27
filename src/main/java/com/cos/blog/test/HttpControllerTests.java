package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.bind.annotation.RequestParam;

// 사용자가 요청 -> 응답(HTML을 응답)을 원하면 일반 콘트롤러 어노테이션을 사용한다
// 사용자가 요청 -> 응답(Data를 응답)

    //rest
@RestController
public class HttpControllerTests {
    //http://localhost:8088/blog/http/get
    //(select)
    //브라우저에서는 겟요청만 할 수 있다
    //GET 요청에서는 요청할 데이터를 전달할 방법이 쿼리스트링 밖에 없다
    @GetMapping("/http/get")
    public String getTest(){
    return "get요청";
    }
    // public String getTest(@RequestParam int id, @RequestParam String username){ //요청한 id값과 username을 받아볼 수 있음
    // return "get요청 : "+ id +"," + username;
    // }
    
    // public String getTest(Member m){
    // return "get요청 : "+ m.getId() +"," + m.getUsername(); // 메서드로 생성 주의!!
    // }

    //http://localhost:8088/blog/http/post
    //(insert)
    @PostMapping("blog/http/post")
    // public String postTest(){
    //     return "post요청";
    // }

    //raw방식(Body로 요청) , text plain
    // public String postTest(@RequestBody String text){ //Body로 보낸 메세지를 반환받음
    //     return "post요청"+text;
    // }

    //json방식(Body로 요청) , application/json
    public String postTest(@RequestBody Member m){ //MessageConverter라는 스프링부트 기능이 작동함(자동으로 파싱하여 오브젝트에 넣어줌)
        return "post요청 : "+ m.getId() +"," + m.getUsername();
    }


    //http://localhost:8088/blog/http/put(update)
    @PutMapping("/blog/http/put")
    public String putTest(@RequestBody Member m){
        return "put요청 : "+ m.getId() +"," + m.getUsername();
    }
    //http://localhost:8088/makeblog/http/delete(delete)
    @DeleteMapping("/blog/http/delete")
    public String deleteTest(){
        return "delete요청";
    }
    
}


// HTTP POST 요청에서 요청이 Header로 전송되었는지, Body로 전송되었는지는 요청 헤더(Headers)의 Content-Type 필드를 통해 판별할 수 있습니다.

// Content-Type 필드는 HTTP 요청 본문의 데이터 유형을 명시합니다. 만약 Content-Type 필드가 설정되어 있다면, 
// 해당 필드에 명시된 데이터 유형으로 요청 본문이 전송된 것이며, 이는 Body로 요청이 전송되었음을 의미합니다.

// 일반적으로 Content-Type 필드의 값으로 application/x-www-form-urlencoded, multipart/form-data, 
// application/json 등이 사용됩니다. 각각의 값은 요청 본문의 데이터 유형을 명시하며, 이를 기반으로 서버에서 요청을 처리합니다.

// 만약 Content-Type 필드가 설정되어 있지 않은 경우, 요청은 Header로 전송된 것으로 간주됩니다.