package com.project.auction.lol.errors.exception;

import com.project.auction.lol.errors.ErrorCode;

public class KakaoException extends RuntimeException{
    private final ErrorCode code;

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorCode getCode() {
        return this.code;
    }

    public KakaoException(ErrorCode code) {
        super(code.getMessage());
        this.code = code;
    }

    public KakaoException(ErrorCode code, String message) {
        super(code.getMessage() + ": " + message);
        this.code = code;
    }
}
