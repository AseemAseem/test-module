package com.example.mybatis.web;


import com.baomidou.mybatisplus.plugins.Page;
import com.example.mybatis.common.base.BaseController;
import com.example.mybatis.common.base.BaseMsg;
import com.example.mybatis.common.utils.PageUtils;
import com.example.mybatis.entity.UserEntity;
import com.example.mybatis.service.IUserService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2019-06-06
 */
@Api(tags = "用户")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private IUserService userService;


    @ApiOperation(value = "获取列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isPage", value = "是否分页,默认分页,false关闭分页", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页码", dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页数量", dataType = "Integer", paramType = "query")})
    @GetMapping("/viewList")
    public BaseMsg list(HttpServletRequest request) {
        Page buildPage = PageUtils.buildPage(request);
        Page resultPage = userService.selectPage(buildPage, null);
        return BaseMsg.successData(resultPage);
    }

    @ApiOperation(value = "根据id获取信息")
    @GetMapping("/view")
    public BaseMsg getInfo(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
        UserEntity entity = userService.selectById(id);
        return BaseMsg.successData(entity);
    }

    @ApiOperation(value = "根据id删除信息")
    @GetMapping("/delete")
    public BaseMsg del(@RequestParam @ApiParam(name = "id", value = "id", required = true) long id) {
        if (userService.deleteById(id))
            return BaseMsg.successMsg("删除成功!");
        return BaseMsg.errorMsg("删除失败!");
    }


    @ApiOperation(value = "保存")
    @PostMapping("/save")
    public BaseMsg del(@RequestBody UserEntity entity) {
        userService.insert(entity);
        return BaseMsg.success();
    }

}
