package com.project.auction.lol.oauth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.auction.lol.errors.ErrorCode;
import com.project.auction.lol.errors.MayoException;
import com.project.auction.lol.errors.exception.KakaoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthService {

    @Value("${oauth.kakao.client-id}")
    private String clientId;
    @Value("${oauth.kakao.redirect-uri}")
    private String redirectUri;
    @Value("${oauth.kakao.token-uri}")
    private String tokenUri;

    private final ObjectMapper objectMapper;
    private static final String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    private static final String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    private static final String BEARER_PREFIX = "Bearer ";

    public OAuthToken getToken(String code) {
        // param으로 전달할 때 HashMap을 지원하는 메시지 컨버터가 없을 수 있으므로 MultiValueMap사용
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("grant_type", GRANT_TYPE_AUTHORIZATION_CODE);
        param.add("client_id", clientId);
        param.add("redirect_uri",redirectUri);
        param.add("code",code);

        HttpHeaders headersForAccessToken = new HttpHeaders();
        headersForAccessToken.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(param, headersForAccessToken);

        RestTemplate rt = new RestTemplate();

        try {
        ResponseEntity<String> accessTokenResponse = rt.exchange(tokenUri, HttpMethod.POST, kakaoTokenRequest, String.class);
            if(accessTokenResponse.getStatusCode() == HttpStatus.OK)
                return objectMapper.readValue(accessTokenResponse.getBody(), OAuthToken.class);
            // 요청 실패시
            throw new KakaoException(ErrorCode.FAIL_KAKAO_API);
        } catch (JsonProcessingException e) {
            throw new MayoException(ErrorCode.FAIL_GET_TOKEN);
        } catch ( Exception e) {
            throw new KakaoException(ErrorCode.FAIL_KAKAO_API, e.getMessage());
        }
    }
}
