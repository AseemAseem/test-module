package com.example.rpc.consumer.common.oauth2;


import com.example.rpc.consumer.common.base.BaseMsg;
import com.example.rpc.consumer.common.utils.JsonUtils;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2Filter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse servletResponse) throws Exception {
        System.out.println("进入方法：AuthenticationToken");
        //获取请求token
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            return new OAuth2Token(null);
        }
        return new OAuth2Token(token);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("进入方法：isAccessAllowed");
        String token = RequestUtils.getRequestToken();
        if(StringUtils.isBlank(token)){
            System.out.println("\tisAccessAllowed --> token为空");
            return false;
        }
        if (request instanceof HttpServletRequest) {
            if ("OPTIONS".equals(((HttpServletRequest) request).getMethod().toUpperCase())) {
                return true;
            }
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        System.out.println("进入方法：onAccessDenied");
        //获取请求token，如果token不存在，直接返回401
        String token = getRequestToken((HttpServletRequest) request);
        if (StringUtils.isBlank(token)) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().print("token为空");
            return false;
        }
        return executeLogin(request, response);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        System.out.println("进入方法：onLoginFailure");
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setContentType("application/json;charset=utf-8");
        try {
            String json = JsonUtils.objectToJson(BaseMsg.errorMsg("登录失败!token不正确或者已过期！请重新登录，获取token！"));
            httpResponse.getWriter().print(json);
        } catch (IOException e1) {

        }
        return false;
    }

    /**
     * 获取请求的token
     */
    private String getRequestToken(HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("token");//从header中获取token
        if (StringUtils.isBlank(token)) {//如果header中不存在token，则从参数中获取token
            token = httpRequest.getParameter("token");
        }
        return token;
    }
}
