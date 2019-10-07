package com.cy.xunwu.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 基于角色的登陆入口控制器
 *
 * 这个类的功能，就是在请求路径为/user/**的情况下都会将页面跳转到用户登陆页面，管理员用户也是同样的道理
 */

public class LoginUrlEntryPoint extends LoginUrlAuthenticationEntryPoint {

    private PathMatcher pathMatcher = new AntPathMatcher();

    private Map<String, String> authEntryPointMap;

    public LoginUrlEntryPoint(String loginFormUrl) {
        super(loginFormUrl);

        authEntryPointMap = new HashMap<>();

        //普通用户登陆入口映射
        authEntryPointMap.put("/user/**", "/user/login");

        //管理员登陆用户入口映射
        authEntryPointMap.put("/admin/**", "/admin/login");
    }

    @Override
    protected String determineUrlToUseForThisRequest(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

        //request.getRequestURI() 返回除去host（域名或者ip）部分的路径
        //request.getContextPath() 返回工程名部分，如果工程映射为/，此处返回则为空
        String uri = request.getRequestURI().replace(request.getContextPath(), "");

        //获得请求的uri路径，然后去上面定义的map中寻找，如果key相匹配，那么跳转到该key对应的value页面
        for (Map.Entry<String, String> authEntry: this.authEntryPointMap.entrySet()){

            if (this.pathMatcher.match(authEntry.getKey(), uri)){

                return authEntry.getValue();
            }
        }

        return super.determineUrlToUseForThisRequest(request, response, exception);
    }
}
