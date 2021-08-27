package com.project.auction.lol.errors;

public class MayoException extends RuntimeException{

    private final ErrorCode code;

    public MayoException(ErrorCode code, String message){
        super(code.getMessage() + ": "+message);
        this.code = code ;
    }
    public MayoException(ErrorCode code){
        super(code.getMessage());
        this.code = code;
    }
    @Override
    public String getMessage() {
        return super.getMessage();
    }

    public ErrorCode getCode(){
        return this.code;
    }
}
