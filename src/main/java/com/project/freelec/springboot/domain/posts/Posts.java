package com.project.freelec.springboot.domain.posts;

import com.project.freelec.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter // 클래스 내 모든 필드의 Getter 메소드 자동 생성
@NoArgsConstructor // 기본 생성자 자동 추가
@Entity // 테이블과 링크될 클래스임 카멜케이스->언더스코어 네이밍으로 매칭됨
public class Posts extends BaseTimeEntity {


    @Id // PK 필드 -- Entity의 PK는 Long 타입의 auto_increment를 추천ㄴ함.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment를 하기위한 스프링부트2.0 옵션
    private Long id;

    /*
     * @Column 어노테이션은 기본 값 외에 추가로 변경이 필요한 옵션이 있을 때 사용
     */
    @Column(length = 500, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    private String author;

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

    @Builder // 해당 클래스의 빌더 패턴 클래스를 생성 . 생성자 상단에 선언시 생성자에 포함된 필드만 빌더에 포함
    public Posts(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }
}
