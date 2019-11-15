package com.example.rpc.consumer.common.oauth2;


import com.example.rpc.consumer.common.exception.CustomException;
import com.example.rpc.consumer.common.utils.JwtUtil;
import com.example.rpc.consumer.entity.User;
import io.jsonwebtoken.Claims;
import io.swagger.models.auth.In;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;

@Component
public class OAuth2Realm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof OAuth2Token;
    }

    /**
     * 授权(验证权限时调用)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("进入授权方法：doGetAuthorizationInfo");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //注：正常业务可用UserUtils.getCurrentUser();从缓存中获取当前用户，不用根据principals获取。
        User currentUser = (User) principals.getPrimaryPrincipal();
        String token = RequestUtils.getRequestToken();
        System.out.println("\t当前用户：" + currentUser.toString() + " token:" + token);
        //正常业务从数据库获取权限信息，这里写死
        Set<String> userPermissions = new HashSet<>();
        userPermissions.add("userInfo:add");
        info.setStringPermissions(userPermissions);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("进入登录方法：doGetAuthenticationInfo");
        String accessToken = (String) token.getPrincipal();
        System.out.println("\t传过来的token是" + accessToken);
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(accessToken);
        } catch (Exception e) {
            throw new CustomException("token不正确或已过期，请重新登录", e);
        }
        String userUuId = claims.getSubject();//这里是用户uuid，配置文件写死0
        Integer version = (Integer) claims.get("userVersion");//用户版本号，写死0
        System.out.println("\t登录的用户uuid为：" + userUuId + " 版本号：" + version);

        User user = new User();//假设根据userUuId从数据库查到的是这个用户
        user.setName("LuFei");
        user.setPassword("123");
        user.setVersion("0");
        //比对版本号
        if (version != Integer.parseInt(user.getVersion())) {
            throw new CustomException("token版本不一致，请重新登录!");
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user, accessToken, getName());
        return authenticationInfo;
    }

}
