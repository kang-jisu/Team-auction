package com.project.auction.lol.errors;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common error
    INTERNAL_SERVER_ERROR(500,"C001","INTERNAL_SERVER_ERROR"),
    INVALID_INPUT_VALUE(400,"C002","INVALID_INPUT_VALUE"),

    //  Member Error
    DUPLICATE_SUMMONER_NAME(409,"M001", "DUPLICATE_SUMMONER_NAME"),
    POSITION_INVALID(400,"M002","POSITION_INVALID"),

    // Team error
    EXIST_TEAM(409,"T001","EXIST_TEAM"),
    ;
    private final int status;
    private final String code;
    private final String message;
}
