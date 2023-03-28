package com.cos.blog.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import jakarta.transaction.Transactional;

@RestController // 리턴값이 html이 아니라 data를 리턴함
public class DummyControllerTests { // 오토 와이어드로 해당 클래스가 메모리에 뜰때 오토와이어드 대상또한 함께 메모리에 뜬다
    // 스프링이 컴포넌트 스캔할 때 유저 리포지토리 안에 있는 유저 인터페이스 레포지토리의 메모리를 띄워준다. 해당 메모리가 떠있기때문에
    // 사용하기만 하면 된다.(의존성 주입)
    //
    @Autowired
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제 실패. id가 존재하지 않음";
        }
        return "삭제 완료 : id" + id;

    }

    // email, password
    @Transactional // 함수 종료시 자동 커밋
    @PutMapping("/dummy/user/{id}") // 주소가 아래와 같아도 get와 put의 차이가 있기 때문에 알아서 구분함
    // Json 데이터를 받아서 테스트, JsonData를 받기 위해서는 리퀘스트 바디가 필요
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 ->
        // MessageConverter의 Jackson라이브러리가 Java Object로 변환해서 받아줌
        System.out.println("Id : " + id);
        System.out.println("pw : " + requestUser.getPassword());
        System.out.println("em : " + requestUser.getEmail());

        // User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("수정에 실패했음"));

        requestUser.setPassword(requestUser.getPassword());
        requestUser.setEmail(requestUser.getEmail());
        // userRepository.save(requestUser); //
        return null;
    }

    @GetMapping("/dummy/user")
    public List<User> list() {
        return userRepository.findAll(); // 전체가 리턴됨
    }

    // 페이지당 2건의 데이터를 리턴
    @GetMapping("dummy/user/page")
    public List<User> pageList(
            @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) { // 2건씩, id정렬,
                                                                                                          // 최신순으로 정렬
        Page<User> pagingUser = userRepository.findAll(pageable);
        if (pagingUser.isLast()) {
        }
        List<User> users = pagingUser.getContent();
        return users;
    }

    // {id}주소로 파라메타를 전달 받을 수 있음
    // http://localhost:8088/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // user/4를 데이터베이스에서 못 찾아오게 되면 user가 null이 되는데 문제가 있지 않겠니?
        // @Optional로 유저 객체를 감싸서 가져올테니 null인지 아닌지 판단해서 return해

        // 1) 없을때 에러메세지를 반환
        // User user = userRepository.findById(id).orElseThrow(new
        // Supplier<IllegalArgumentException>(){
        // @Override
        // public IllegalArgumentException get() {
        // return new IllegalArgumentException("해당 유저는 없습니다. id: "+id);
        // }
        // });
        // return user;
        // }

        // 1-1) 람다식
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 없습니다. id: " + id);
        });
        // 요청 : 웹브라우저
        // 유저객체 : 자바 오브젝트
        // -> 웹브라우저가 이해할 수 있는 데이터로 변환해야함 -> json
        // 스프링부트 = MessageConverter라는 친구가 응답시 자동 작동하여 자바 오브젝트를 리턴한다면
        // 이친구가 Jackson 라이브러리를 호출하여 유저 오브젝트를 Json변환후 브라우저에 가져다줌
        return user;
    }

    // 2) 없다면 모든 것을 null처리 하고 싶을때
    // User user = userRepository.findById(id).orElseGet(new Supplier<User>(){
    // @Override
    // public User get() {
    // return new User();
    // }
    // });
    // return user;
    // }

    // 3) 빈 객체를 return하여 최소 null만큼은 피하고자 할때
    // User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
    // @Override
    // public User get(){
    // return new User();
    // }
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
// 더티 체킹(dirty checking)은 ORM(Object-Relational Mapping) 기술에서 사용되는 개념으로, 객체의 상태
// 변경을 감지하여 자동으로 데이터베이스에 반영하는 기능을 말합니다.

// 영속성 컨텍스트(persistence context)라는 객체 관리 영역에서 관리되는 엔티티(Entity) 객체의 변경 사항을 추적하고,
// 데이터베이스와의 동기화 작업을 수행하는 것이 더티 체킹의 핵심 개념입니다.

// 더티 체킹은 일반적으로 다음과 같은 과정을 거칩니다.

// 엔티티 객체의 변경이 일어납니다.
// 엔티티 매니저(Entity Manager)는 변경된 엔티티 객체를 영속성 컨텍스트에 등록합니다. //영속성 컨텍스트는 JPA의 캐시창고이다
// 영속성 컨텍스트는 변경된 엔티티 객체의 상태를 추적합니다.
// 트랜잭션(Transaction)이 커밋되는 시점에, 영속성 컨텍스트는 변경된 엔티티 객체의 상태 변화를 감지하여, 자동으로 데이터베이스에
// 변경 내용을 반영합니다.
// 즉, 더티 체킹은 개발자가 별도의 SQL 쿼리를 작성하지 않아도, 객체의 상태 변화가 데이터베이스에 자동으로 반영되는 것을 가능하게
// 합니다. 이를 통해 생산성을 높일 수 있고, 코드의 가독성도 향상시킬 수 있습니다.