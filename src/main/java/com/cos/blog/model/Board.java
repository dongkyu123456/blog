package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder // 빌더 패턴
@Entity // User클래스가 자동으로 heidiSQL에 테이블 생성함
public class Board {
    // User클래스가 자동으로 heidiSQL에 테이블 생성함

    @Id//pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)//프로젝트에서 연결된 DB의 넘버링 전략을 따라감
    private int id;//시퀸스, 오토 인크리먼트

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;
    
    @ColumnDefault("0")
    private int count; // 조회수

    // Many = 보드, 유저 = 원// 한 유저는 여러 게시글을 쓸 수 있다
    @ManyToOne(fetch = FetchType.EAGER) //만약 네가 테이블을 Select한다면 User정보를 가져가겠다.
    @JoinColumn(name="userId")//BD에 만들어질때는 userId로 인식될 것이다.
    private User user; // DB는 오브젝트를 저장할 수 없다.fk,자바는 오브젝트를 저장할 수 있다.

    // 하나의 게시글은 많은 댓글을 가질 수 있다
    @OneToMany(mappedBy = "board",fetch = FetchType.EAGER) 
    // 맵바이 - reply는 연관관계의 주인이 아님(FK가 아니니 BD에 칼럼을 만들지 마시오)
    // 레이지 전략 - 필요하면 들고오고 아니면 말고(리플이 굉장히 많기 때문)
    // @JoinColumn(name="replyId") 필요하지 않음 
    private List<Reply> reply; // 리플은 굉장히 많음, 하나이면 안되기 때문에 리스트-컬렉션이 될 필요가 있음

    @CreationTimestamp //시간 자동 입력
    private Timestamp createDate;

}


