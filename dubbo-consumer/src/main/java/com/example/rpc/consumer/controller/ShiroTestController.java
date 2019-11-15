package com.example.rpc.consumer.controller;

import com.example.rpc.consumer.common.oauth2.RequestUtils;
import com.example.rpc.consumer.common.utils.JwtUtil;
import com.example.rpc.consumer.common.utils.PropertiesUtil;
import com.example.rpc.consumer.common.validator.Validate;
import com.example.rpc.consumer.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ShiroTestController {

    @Autowired
    Validate validate;

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PostMapping(value = "/index")
    public String loginUser(HttpServletRequest request, HttpSession session, Model model, User user) {
        try {
            //假设账号密码与数据库比对成功
            user.setUuid(PropertiesUtil.getProperty("TEMP_UUID"));//使用临时uuid，测试用
            //使用用户id，生成jwt，放入session中
            String jwt = JwtUtil.createJWT(user.getUuid(), 0, Long.valueOf(PropertiesUtil.getProperty("LIMIT_SECOND")));
            session.setAttribute("token", jwt);
            session.setAttribute("currentUser", user);

            //获取session的Id，看下是否改变而已
            String sessionId = session.getId();
            if (session.isNew()) {
                System.out.println("session创建成功，session的id是：" + sessionId);
            } else {
                System.out.println("服务器已经存在该session了，session的id是：" + sessionId);
            }
            return "index";
        } catch (Exception e) {
            //从request中获取shiro处理的异常信息 shiroLoginFailure:就是shiro异常类的全类名
            String exception = (String) request.getAttribute("shiroLoginFailure");
            model.addAttribute("msg", e.getMessage());
            //返回登录页面
            return "login";
        }
    }

    @GetMapping(value = "/admin")
    public String shiro() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Object currentUser = session.getAttribute("currentUser");
        Object currentUser1 = RequestUtils.getObjByAttributeName("currentUser");
        return "admin";
    }

    @GetMapping(value = "/requiresPermission")
    @RequiresPermissions(value = {"userInfo:add"})
    public String RequiresPermissions() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        User currentUser = (User)session.getAttribute("currentUser");
        return "requiresPermission";
    }
}
