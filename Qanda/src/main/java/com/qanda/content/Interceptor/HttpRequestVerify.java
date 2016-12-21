package com.qanda.content.Interceptor;

import com.qanda.content.functionKit.EasyCookie;
import com.qanda.content.functionKit.ServerNotice;
import com.qanda.content.service.UserServiceImp;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by huangrui on 2016/12/20.
 */

public class HttpRequestVerify extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Cookie sessionCookie = EasyCookie.getCookieByName(request, "sessionId");
        if (sessionCookie != null) {
            String session = sessionCookie.getValue();
            if (UserServiceImp.userServiceImp().verifyUserState(session)) {
                ServerNotice.active();
                return true;
            }
        }
        ServerNotice.inactive();
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        ServerNotice.reset();
    }
}
