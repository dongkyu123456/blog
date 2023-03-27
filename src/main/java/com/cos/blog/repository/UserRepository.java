package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

//자동 빈 등록, 리포지토리 어노테이션 생략 가능
public interface UserRepository extends JpaRepository<User,Integer>{ // JPA 레파지토리는 유저 테이블을 관리하고, PK는 인티저이다.
    
}
