package com.example.interceptor;


import com.example.util.JwtUtils;
import com.example.util.StringUtil;
import io.jsonwebtoken.Claims;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 * 鉴权拦截器
 *
 * @author java1234_小锋
 * @site www.java1234.com
 * @company Java知识分享网
 * @create 2021-01-29 14:11
 */
public class SysInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (handler instanceof HandlerMethod) {
            String token = request.getHeader("token");
//            System.out.println("token:" + token);
            if (StringUtil.isEmpty(token)) {
//                System.out.println("token为空！");
                throw new RuntimeException("签名验证不存在");
            } else {
                Claims claims = JwtUtils.validateJWT(token).getClaims();
//                System.out.println("claims: "+claims);
                // 管理员 admin开头
                if (path.startsWith("/admin")) {
                    if (claims == null || !claims.getSubject().equals("admin") || !claims.getId().equals("-1")) {
                        throw new RuntimeException("鉴权失败！");
                    } else {
//                        System.out.println("验证成功");
                        return true;
                    }
                } else { // 普通用户 鉴权
                    if (claims == null) {
                        throw new RuntimeException("鉴权失败！");
                    } else {
                        HttpSession session=request.getSession();
                        session.setAttribute("claims",claims);
                        session.setAttribute("openId",claims.getId());
                        System.out.println("验证成功");
                        return true;
                    }
                }

            }
        } else {
            return true;
        }
    }


}
