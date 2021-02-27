package com.jun.seeyouagain.common.model.exception;

public class BusinessException extends RuntimeException {

    public BusinessException(){
        super();
    }
    public BusinessException(String msg){
        super(msg);
    }

    public BusinessException(String message, Throwable cause){
        super(message,cause);
    }
}
