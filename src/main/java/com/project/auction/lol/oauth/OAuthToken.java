package com.project.auction.lol.oauth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OAuthToken {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("expries_in")
    private int expiresIn;
    @JsonProperty("scope")
    private String scope;
    @JsonProperty("refreshTokenExpriesIn")
    private int refreshTokenExpiresIn;
}