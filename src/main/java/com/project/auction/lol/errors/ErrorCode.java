package com.project.auction.lol.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //Common error
    DUPLICATE_SUMMONER_NAME(HttpStatus.CONFLICT,"C001", "DUPLICATE_SUMMONER_NAME"),
    POSITION_NOT_FOUND(HttpStatus.NOT_FOUND,"C002","POSITION_NOT_FOUND"),
    EXIST_TEAM(HttpStatus.CONFLICT,"C003","EXIST_TEAM");

    private final HttpStatus status;
    private final String code;
    private final String message;
}
