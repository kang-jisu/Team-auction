package com.project.auction.lol.controller.api;

import com.project.auction.lol.oauth.OAuthService;
import com.project.auction.lol.oauth.OAuthToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/oauth/kakao/callback")
    public ResponseEntity<OAuthToken> authCallback(String code){
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(oAuthService.getToken(code));
    }
}
