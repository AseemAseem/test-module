package com.example.mybatis.common.validator;

import com.example.mybatis.common.exception.CustomException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

@Component
public class Validate {

    public Validate falseThrow(boolean bool, String message) {
        if (!bool)
            throw new CustomException(message);
        return this;
    }

    public Validate trueThrow(boolean bool, String message) {
        if (bool)
            throw new CustomException(message);
        return this;
    }

    public Validate isNotEmpty(String str, String message) {
        if (StringUtils.isEmpty(str))
            throw new CustomException(message);
        return this;
    }

    public Validate isNotEmpty(Object str, String message) {
        if (null == str)
            throw new CustomException(message);
        return this;
    }

    public Validate isNotEmpty(Collection<?> collec, String message) {
        if (CollectionUtils.isEmpty(collec))
            throw new CustomException(message);
        return this;
    }

    public void error(String message) {
        throw new CustomException(message);
    }

    public void error(Exception e) {
        if (e instanceof RuntimeException) {
            throw (RuntimeException) e;
        } else {
            throw new CustomException(e);
        }
    }

    public void error(String message, Exception e) {
        throw new CustomException(message, e);
    }

}
