package com.project.auction.lol.config.auth.dto;

import com.project.auction.lol.domain.user.User;
import lombok.Getter;

/*
인증된 사용자 정보 -> User클래스를 사용하며 직렬화를 구현하는것보다 따로 이렇게 Dto를 추가하는것이 낫다.
 */
@Getter
public class SessionUser {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
