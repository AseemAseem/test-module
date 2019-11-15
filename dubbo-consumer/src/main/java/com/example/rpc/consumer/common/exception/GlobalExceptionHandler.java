package com.example.rpc.consumer.common.exception;

import com.example.rpc.consumer.common.base.BaseMsg;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public BaseMsg handleCustomException(CustomException e) {
        e.printStackTrace();
        return BaseMsg.errorMsg(e.getMsg());
    }
}
