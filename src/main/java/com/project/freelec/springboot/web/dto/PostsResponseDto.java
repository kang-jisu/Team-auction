package com.project.freelec.springboot.web.dto;

import com.project.freelec.springboot.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {
    private Long id;
    private String title;
    private String content;
    private String author;


    // 굳이 모든 필드를 받은 생성자가 필요하지 않으므로 엔티티를 받아서 처리
    public PostsResponseDto(Posts entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }
}
