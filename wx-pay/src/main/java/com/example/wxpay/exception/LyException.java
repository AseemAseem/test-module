package com.example.wxpay.exception;

import com.example.wxpay.enums.ExceptionEnum;
import lombok.Getter;

/**
 * @author bystander
 * @date 2018/9/15
 *
 * 自定义异常类
 */
@Getter
public class LyException extends RuntimeException {

    private ExceptionEnum exceptionEnum;

    public LyException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }


}
