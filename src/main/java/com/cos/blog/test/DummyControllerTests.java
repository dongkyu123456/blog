package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController // 리턴값이 html이 아니라 data를 리턴함
public class DummyControllerTests { // 오토 와이어드로 해당 클래스가 메모리에 뜰때 오토와이어드 대상또한 함께 메모리에 뜬다
    // 스프링이 컴포넌트 스캔할 때 유저 리포지토리 안에 있는 유저 인터페이스 레포지토리의 메모리를 띄워준다. 해당 메모리가 떠있기때문에
    // 사용하기만 하면 된다.(의존성 주입)
    //
    @Autowired
    private UserRepository userRepository;

    // 주소로 파라메타를 전달 받을 수 있음
    // http://localhost:8088/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        //user/4를 데이터베이스에서 못 찾아오게 되면 user가 null이 되는데 문제가 있지 않겠니?
        //@Optional로 유저 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해

    //1) 없을때 에러메세지를 반환
    //     User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>(){
    //     @Override
    //     public IllegalArgumentException get() {
    //         return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
    //     }
    // });
    //     return user;
    // }

    //1-1) 람다식
    User user = userRepository.findById(id).orElseThrow(()->{
        return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
    });
    //요청 : 웹브라우저
    //유저객체 : 자바 오브젝트
    // -> 웹브라우저가 이해할 수 있는 데이터로 변환해야함 -> json
    // 스프링부트 = MessageConverter라는 친구가 응답시 자동 작동하여 자바 오브젝트를 리턴한다면 
    // 이친구가 Jackson 라이브러리를 호출하여 유저 오브젝트를 Json변환후 브라우저에 가져다줌
    return user;
    }

    //2) 없다면 모든 것을 null처리 하고 싶을때
    // User user = userRepository.findById(id).orElseGet(new Supplier<User>(){
    //     @Override
    //     public User get() {
    //         return new User();
    //     }
    // });
    //     return user;
    // }

    //3)  빈 객체를 return하여 최소 null만큼은 피하고자 할때
    //     User user = userRepository.findById(id).orElseGet(new Supplier<User>()  {
    //     @Override
    //     public User get(){
    //         return new User();
    //     }
    // });
    // return null;
    // }

    // http://localhost:8088/blog/dummy/join
    // http의 바디에 유저네임 패스워드 이메일 데이터를 가지고 요청하면 파라미터에 받아짐
    @PostMapping("/dummy/join")
    public String join(User user) { // key = value (약속된 규칙)
        System.out.println("유저네임 : " + user.getUsername());
        System.out.println("패스워드 : " + user.getPassword());
        System.out.println("이메일 : " + user.getEmail());

        // user.setRole(RoleType.USER); 원인불명의 에러
        user.setRole("user");
        userRepository.save(user);
        return "회원가입 완료";
    }
}
