package com.example.mybatis.web;

import com.example.mybatis.common.base.BaseMsg;
import com.example.mybatis.common.validator.Validate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.FileNotFoundException;

@Api(tags = "异常处理测试类")
@Controller
public class ValidateController {

    @Autowired
    Validate validate;

    Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "自定义异常")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "data", value = "数据，验证是否为空")
    })
    @PostMapping(value = {"/validate"})
    @ResponseBody
    public BaseMsg validate(String data) {
        validate.isNotEmpty(data, "数据为空");
        return BaseMsg.success();
    }

    @ApiOperation(value = "try catch , 捕获数值转换异常")
    @PostMapping(value = {"/tryCatch"})
    @ResponseBody
    public BaseMsg tryCatch() {
        try {
            String str = "ss";
            int i = Integer.parseInt(str);
        } catch (Exception e) {
            logger.error("try catch, 捕获数值转换异常",e);
        }
        return BaseMsg.success();
    }

    @ApiOperation(value = "throw Exception，抛出FileNotFoundException")
    @PostMapping(value = {"/throwException"})
    @ResponseBody
    public BaseMsg throwException() throws FileNotFoundException {
        throw new FileNotFoundException();
    }

    @ApiOperation(value = "throws Exception，抛出NumberFormatException")
    @PostMapping(value = {"/throwsException"})
    @ResponseBody
    public BaseMsg throwsException() throws NumberFormatException {
        String str = "ss";
        int i = Integer.parseInt(str);
        return BaseMsg.success();
    }
}
