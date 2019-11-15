package com.example.mybatis.common.exception;

import com.example.mybatis.common.base.BaseMsg;
import com.example.mybatis.common.utils.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomException.class)
    public BaseMsg handleCustomException(CustomException e) {
        //输出到控制台和日志文件
        logger.error(ExceptionUtils.getStackTrace(e));
        return BaseMsg.errorMsg(e.getMsg());
    }

    @ExceptionHandler(Throwable.class)
    public BaseMsg handleDefaultException(Throwable e) {
        //输出到控制台和日志文件
        logger.error(ExceptionUtils.getStackTrace(e));
        return BaseMsg.error();
    }


}
