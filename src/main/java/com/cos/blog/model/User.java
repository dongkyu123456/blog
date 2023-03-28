package com.cos.blog.model;
import java.sql.Timestamp;

// import org.hibernate.annotations.ColumnDefault;
// import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
// import jakarta.persistence.EnumType;
// import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> Java(타언어) Object -> 테이블로 매핑해주는 기술
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User클래스가 자동으로 heidiSQL에 테이블 생성함
// @DynamicInsert // 인서트시에 null인 필드를 제외시켜준다
public class User {

    @Id//pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라감
    private int id;//시퀸스, 오토 인크리먼트

    @Column(nullable = false, length = 30,unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100)//1234 해시 변경후 암호화를 위해 길이를 넉넉하게
    private String password;

    @Column(nullable = false, length = 50)
    private String email;
    
    // @ColumnDefault("'user'") // 홑따옴표 주의 ,후에 디폴트값이 테이블에 들어가지 않도록 주석 처리했음
    //BD는 RoleType이 없기 때문에 이너멀레이티드 어노테이션을 붙여줌
    // @Enumerated(EnumType.STRING)
    private String role;//Enum을 쓰는 것이 좋음. 이넘은 도메인을 만들어줄 수 있다. // ADMIN,USER

    @CreationTimestamp //시간 자동 입력
    private Timestamp createDate;
}
